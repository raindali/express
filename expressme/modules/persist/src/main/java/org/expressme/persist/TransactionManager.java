package org.expressme.persist;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * TransactionManager to manage all transactions and underlying JDBC connections. 
 * NOTE that this lightweight TransactionManager only support local JDBC 
 * transaction, and only support binding current connections via ThreadLocal, 
 * that means, transaction cannot across remote call.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class TransactionManager {

    private Log log = LogFactory.getLog(getClass());

    private static ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<Connection>();

    private static final Class<?>[] PROXY_INTERFACES = new Class<?>[] { Connection.class };

    private DataSource dataSource = null;
    private boolean lazyFetchConnection = true;

    /**
     * Create a JdbcTransactionManager without DataSource, and MUST call 
     * <code>setDataSource(DataSource dataSource)</code> to set a valid 
     * DataSource object.
     */
    public TransactionManager() {
    }

    /**
     * Create a TransactionManager with given DataSource object.
     * 
     * @param dataSource DataSource object.
     */
    public TransactionManager(DataSource dataSource) {
        setDataSource(dataSource);
    }

    /**
     * Set DataSource object, usually get from JNDI.
     */
    public void setDataSource(DataSource dataSource) {
        if (dataSource==null)
            throw new NullPointerException("DataSource object is null.");
        this.dataSource = dataSource;
        log.info("DataSource is set to: " + dataSource);
    }

    /**
     * Set if fetch connection in lazy mode. In lazy mode, connection is not 
     * fetched from data source until the first JDBC request.
     */
    public void setLazyFetchConnection(boolean lazyFetchConnection) {
        this.lazyFetchConnection = lazyFetchConnection;
    }

    /**
     * Get current connection. If transaction is not begin, the current connection 
     * is not exist, thus, DataAccessException will be thrown.
     * 
     * @return Current connection which associate with current transaction.
     */
    public Connection getCurrentConnection() {
        Connection connection = connectionThreadLocal.get();
        if(connection!=null)
            return connection;
        throw new DataAccessException("Cannot get connection because transaction is not begin yet.");
    }

    /**
     * Begin JDBC transaction.
     */
    public Transaction beginTransaction() {
        return beginTransaction(Connection.TRANSACTION_READ_COMMITTED);
    }

    /**
     * Begin JDBC transaction with specified isolation.
     */
    public Transaction beginTransaction(final int isolation) {
        Connection connection = connectionThreadLocal.get();
        if(connection!=null)
            throw new TransactionException("Current transaction exist and not commit or rollback.");
        if (this.lazyFetchConnection) {
            connection = (Connection) Proxy.newProxyInstance(
                    getClass().getClassLoader(),
                    PROXY_INTERFACES,
                    new InvocationHandler() {
                        private Connection target = null;
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            if (target==null) {
                                String n = method.getName();
                                if (n.equals("commit") || n.equals("rollback") || n.equals("close")) {
                                    // since the real connection is not open yet, we ignore the 
                                    // method of "commit", "rollback" and "close":
                                    return null;
                                }
                                try {
                                    target = dataSource.getConnection();
                                    target.setAutoCommit(false);
                                    target.setTransactionIsolation(isolation);
                                }
                                catch(SQLException e) {
                                    log.warn("Start transaction failed.", e);
                                    if (target!=null) {
                                        try {
                                            target.close();
                                        }
                                        catch (SQLException ex) {
                                            log.warn("Close connection failed.", ex);
                                        }
                                    }
                                    throw new TransactionException("Start transaction failed.", e);
                                }
                            }
                            return method.invoke(target, args);
                        }
                    }
            );
        }
        else {
            try {
                connection = dataSource.getConnection();
                connection.setAutoCommit(false);
                connection.setTransactionIsolation(isolation);
            }
            catch (SQLException e) {
                log.warn("Start transaction failed.", e);
                if (connection!=null) {
                    try {
                        connection.close();
                    }
                    catch (SQLException ex) {
                        log.warn("Close connection failed.", ex);
                    }
                }
                throw new TransactionException("Start transaction failed.", e);
            }
        }
        connectionThreadLocal.set(connection);
        return new Transaction(this);
    }

    void clean() {
        Connection connection = connectionThreadLocal.get();
        if(connection==null) {
            log.warn("Cannot close connection.");
            throw new TransactionException("Cannot clean up after end transaction.");
        }
        try {
            connection.close();
            if (log.isDebugEnabled())
                log.debug("Connection closed.");
        }
        catch(SQLException e) {
            log.warn("Close connection failed.", e);
        }
        connectionThreadLocal.remove();
    }

}
