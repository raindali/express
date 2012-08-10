package org.expressme.persist;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.expressme.persist.dialect.Dialect;

/**
 * Generate DAO using JDBC.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class DaoFactory {

    private final Integer INT_ZERO = new Integer(0);
    private final int[] EMPTY_INT_ARRAY = new int[0];
    private final Log log = LogFactory.getLog(getClass());
    private Dialect dialect;

    public DaoFactory(Dialect dialect) {
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
        T t = (T) Proxy.newProxyInstance(
                clazz.getClassLoader(),
                new Class<?>[] { clazz },
                new InvocationHandler() {

                    private ThreadLocal<BatchMode> batchThreadLocal = new ThreadLocal<BatchMode>();

                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        String methodName = method.getName();
                        boolean emptyParamter = method.getParameterTypes().length==0;
                        BatchMode mode = batchThreadLocal.get();
                        if (emptyParamter) {
                            if ("prepareBatch".equals(methodName)) {
                                if (mode!=null)
                                    throw new BatchUpdateException("Cannot start a batch when exist batch is not executed.");
                                batchThreadLocal.set(new BatchMode());
                                return null;
                            }
                            else if ("executeBatch".equals(methodName)) {
                                if (mode==null)
                                    throw new BatchUpdateException("Cannot execute a batch when batch is not exist.");
                                if (mode.isBatchExecuted())
                                    throw new BatchUpdateException("Cannot re-execute a batch.");
                                PreparedStatement ps = mode.getBatchPreparedStatement();
                                try {
                                    if (ps!=null)
                                        return ps.executeBatch();
                                    else
                                        return EMPTY_INT_ARRAY;
                                }
                                catch (SQLException e) {
                                    throw new BatchUpdateException(e);
                                }
                                finally {
                                    mode.setBatchExecuted();
                                }
                            }
                            else if ("closeBatch".equals(methodName)) {
                                if (mode==null)
                                    throw new BatchUpdateException("Cannot close a batch when batch is not exist.");
                                batchThreadLocal.remove();
                                mode.destroy();
                                return null;
                            }
                            // special handle 'toString' to prevent infinite recursive call:
                            else if ("toString".equals(methodName))
                                return proxy.getClass().getName();
                        }
                        SqlExecutor executor = map.get(method);
                        if (executor!=null) {
                            if (mode!=null) {
                                // in batch mode:
                                if (executor instanceof UpdateSqlExecutor) {
                                    if (mode.isBatchExecuted())
                                        throw new BatchUpdateException("Batch is executed but not closed.");
                                    UpdateSqlExecutor updateExecutor = (UpdateSqlExecutor) executor;
                                    if (mode.getBatchUpdateExecutor()==null) {
                                        mode.setBatchUpdateExecutor(updateExecutor);
                                    }
                                    else if (mode.getBatchUpdateExecutor()!=updateExecutor)
                                        throw new BatchUpdateException("Cannot do batch update with method '" + method.toString() + "' because it already associate with another method.");
                                    PreparedStatement ps = updateExecutor.prepareForBatch(txManager, mode.getBatchPreparedStatement(), args);
                                    mode.setBatchPreparedStatement(ps);
                                    return updateExecutor.returnAsInt ? INT_ZERO : null;
                                }
                            }
                            // not in batch mode:
                            return executor.execute(txManager, args);
                        }
                        // normal method like 'hashCode', 'equals':
                        return method.invoke(proxy, args);
                    }
                }
        );
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
