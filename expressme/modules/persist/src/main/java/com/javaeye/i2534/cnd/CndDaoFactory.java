package com.javaeye.i2534.cnd;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.expressme.persist.BatchUpdateException;
import org.expressme.persist.DaoConfigException;
import org.expressme.persist.JdbcUtils;
import org.expressme.persist.Query;
import org.expressme.persist.QuerySqlExecutor;
import org.expressme.persist.SqlExecutor;
import org.expressme.persist.TransactionManager;
import org.expressme.persist.Update;
import org.expressme.persist.UpdateSqlExecutor;
import org.expressme.persist.dialect.Dialect;

/**
 * Generate DAO using JDBC.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class CndDaoFactory {

    private final Integer INT_ZERO = new Integer(0);
    private final int[] EMPTY_INT_ARRAY = new int[0];
    private final Log log = LogFactory.getLog(getClass());
    private Dialect dialect;

    public CndDaoFactory(Dialect dialect) {
        this.dialect = dialect;
        log.info("Set JDBC dialect to: " + dialect.getClass().getName());
    }

    @SuppressWarnings("unchecked")
    public <T> T createDao(final Class<T> clazz, final TransactionManager txManager) {
        if (! clazz.isInterface()) {
            throw new DaoConfigException("DAO class is not interface: " + clazz.getName());
        }
        final Map<Method, SqlExecutor> map = new HashMap<Method, SqlExecutor>();
        for (Method method : clazz.getMethods()) {
            if (method.getParameterTypes().length==0) {
                // discard method from "BatchSupport":
                String name = method.getName();
                if ("prepareBatch".equals(name) || "executeBatch".equals(name) || "closeBatch".equals(name))
                    continue;
            }
            map.put(method, createSqlExecutor(method));
        }
        T t = null;
        log.info("DAO class '" + clazz.getName() + "' created successfully!");
        return t;
    }

    SqlExecutor createSqlExecutor(Method method) {
        Query query = method.getAnnotation(Query.class);
        Update update = method.getAnnotation(Update.class);
        if (query!=null && update!=null)
            throw new DaoConfigException("Cannot put both @Query and @Update on same method: " + method.toString());
        if (query==null && update==null)
            throw new DaoConfigException("A @Query or @Update must be put on method: " + method.toString());
        return (query!=null) ? new QuerySqlExecutor(method, dialect) : new UpdateSqlExecutor(method);
    }

}

/**
 * Batch information for ThreadLocal.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
class BatchMode {

    private PreparedStatement batchPs = null;
    private UpdateSqlExecutor batchExecutor = null;
    private boolean batchExecuted = false;

    public PreparedStatement getBatchPreparedStatement() {
        return batchPs;
    }

    public void setBatchPreparedStatement(PreparedStatement batchPs) {
        this.batchPs = batchPs;
    }

    public boolean isBatchExecuted() {
        return batchExecuted;
    }

    public void setBatchExecuted() {
        batchExecuted = true;
    }

    public UpdateSqlExecutor getBatchUpdateExecutor() {
        return batchExecutor;
    }

    public void setBatchUpdateExecutor(UpdateSqlExecutor batchExecutor) {
        this.batchExecutor = batchExecutor;
    }

    public void destroy() {
        if (batchPs!=null) {
            JdbcUtils.close(batchPs);
            batchPs = null;
        }
    }
}
