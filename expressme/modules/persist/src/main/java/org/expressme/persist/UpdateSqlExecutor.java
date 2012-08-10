package org.expressme.persist;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Execute SQL for update.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class UpdateSqlExecutor extends SqlExecutor {

    private final String preparedSql;
    private final int[] argIndex;
    final Method[] argGets;
    final boolean returnAsInt;

    public UpdateSqlExecutor(Method method) {
        Update update = method.getAnnotation(Update.class);
        String sqlUpdate = update.value();
        if (sqlUpdate.trim().length()==0)
            throw new DaoConfigException("Missing SQL in @Update on method: " + method.toString());

        // check index of arg of @FirstResult and @MaxResults:
        Annotation[] annos = getAnnotationsOfArgs(method);
        for (int i=0; i<annos.length; i++) {
            Annotation anno = annos[i];
            if (anno.annotationType().equals(FirstResult.class))
                throw new DaoConfigException("Cannot use @FirstResult in update of method: " + method.toString());
            else if (anno.annotationType().equals(MaxResults.class))
                throw new DaoConfigException("Cannot use @MaxResults in update of method: " + method.toString());
        }
        // check:
        Class<?> returnType = method.getReturnType();
        Class<?>[] parameterTypes = method.getParameterTypes();
        this.returnAsInt = isInt(returnType);
        if (!this.returnAsInt && !void.class.equals(returnType))
            throw new DaoConfigException("@Update must return void or int-type in method: " + method.toString());
        // build query:
        String[] argNames = JdbcUtils.getParameterNames(sqlUpdate);
        this.preparedSql = JdbcUtils.getPreparedSql(sqlUpdate);
        this.argIndex = new int[argNames.length];
        this.argGets = new Method[argNames.length];

        // init parameter index:
        for (int i=0; i<argNames.length; i++) {
            String argName = argNames[i];
            int pos = indexOfArgByName(method, annos, argName);
            this.argIndex[i] = pos;
            String prop = getPropertyName(argName);
            if (prop!=null)
                argGets[i] = findGetterByPropertyName(parameterTypes[pos], prop);
        }
        log.info("Generate method '" + methodToString(method) + "' for update: " + preparedSql);
    }

    public PreparedStatement prepareForBatch(TransactionManager txManager, PreparedStatement ps, Object... args) {
        try {
            if (ps==null)
                ps = txManager.getCurrentConnection().prepareStatement(preparedSql);
            JdbcUtils.bindParameters(ps, getSqlParams(this.argIndex, this.argGets, args));
            ps.addBatch();
            return ps;
        }
        catch(SQLException e) {
            throw new BatchUpdateException(e);
        }
    }

    @Override
    public Object execute(TransactionManager txManager, Object... args) {
        return new PreparedStatementCallback<Object>() {
            @Override
            protected Object doInPreparedStatement(PreparedStatement ps) throws SQLException {
                int ret = ps.executeUpdate();
                if (returnAsInt)
                    return ret;
                return null;
            }
        }.execute(txManager, this.preparedSql, getSqlParams(this.argIndex, this.argGets, args));
    }

}
