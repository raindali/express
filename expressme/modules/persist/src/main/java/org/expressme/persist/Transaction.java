package org.expressme.persist;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Represent a JDBC Transaction. This object is created by calling 
 * <code>TransactionManager.beginTransaction</code>.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class Transaction {

    private Log log = LogFactory.getLog(getClass());

    private final TransactionManager txManager;
    private final Connection connection;
    private boolean rollbackOnly = false;
    private boolean hasRollback = false;

    Transaction(TransactionManager txManager) {
        this.txManager = txManager;
        this.connection = txManager.getCurrentConnection();
    }

    /**
     * Get flag of rollback.
     * 
     * @return True if rollback flag is set to true.
     */
    public boolean isRollbackOnly() {
        return rollbackOnly;
    }

    /**
     * Set rollback flag of current transaction. Rollback will perform when 
     * <code>commit()</code> calls.
     */
    public void setRollbackOnly() {
        rollbackOnly = true;
    }

    /**
     * Rollback the transaction.
     */
    public void rollback() {
        // prevent rollback more than once:
        if (hasRollback)
            return;
        hasRollback = true;
        // start rollback:
        if (log.isDebugEnabled())
            log.debug("Transaction rollback.");
        try {
            connection.rollback();
        }
        catch(SQLException e) {
            throw new TransactionException("Rollback transaction failed.", e);
        }
        finally {
            txManager.clean();
        }
    }

    /**
     * Commit the transaction, or rollback the transaction if isRollbackOnly() is true.
     */
    public void commit() {
        if (rollbackOnly) {
            rollback();
            return;
        }
        if (log.isDebugEnabled())
            log.debug("Transaction commit.");
        try {
            connection.commit();
        }
        catch(SQLException e) {
            throw new TransactionException("Commit transaction failed.", e);
        }
        finally {
            txManager.clean();
        }
    }

}
