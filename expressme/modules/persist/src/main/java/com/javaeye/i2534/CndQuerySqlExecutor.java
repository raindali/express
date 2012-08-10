package com.javaeye.i2534;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.expressme.persist.JdbcUtils;
import org.expressme.persist.NonUniqueResultException;
import org.expressme.persist.Query;
import org.expressme.persist.ResultSetCallback;
import org.expressme.persist.SqlExecutor;
import org.expressme.persist.TransactionManager;
import org.expressme.persist.Unique;
import org.expressme.persist.dialect.Dialect;
import org.expressme.persist.mapper.ObjectArrayRowMapper;
import org.expressme.persist.mapper.RowMapper;


/**
 * Execute SQL for query.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class CndQuerySqlExecutor extends SqlExecutor {

    final int fetchSize;
    final boolean unique;


    public CndQuerySqlExecutor(Method method, Dialect dialect) {
        Query query = method.getAnnotation(Query.class);
        fetchSize = query.fetchSize();
        this.unique = method.getAnnotation(Unique.class)!=null;
//        String sqlQuery =  JdbcGenerator.query(cnd);
        log.info("Generate method '" + methodToString(method) + "' for prepared query!");
    }

    String buildPreparedQuery(String sqlQuery, boolean hasFirstResult, boolean hasMaxResults, Dialect dialect) {
        String preparedQuery = JdbcUtils.getPreparedSql(sqlQuery).trim();
        if (!hasFirstResult && !hasMaxResults)
            return preparedQuery;
        return dialect.getLimitSQL(preparedQuery, hasFirstResult, hasMaxResults);
    }

    @Override
    public Object execute(TransactionManager txManager, Object... args) {
    	final Cnd cnd = (Cnd) args[0];
    	final RowMapper<?> rowMapper = (RowMapper<?>) args[1];
        final String preparedQuery = JdbcGenerator.query(cnd);

        return new ResultSetCallback<Object>() {
            @Override
            protected Object doInResultSet(ResultSet rs) throws SQLException {
                ResultSetMetaData meta = rs.getMetaData();
                int colCount = meta.getColumnCount();
                String[] colNames = new String[colCount];
                int[] colTypes = new int[colCount];
                for (int i=0; i<colCount; i++) {
                    colNames[i] = meta.getColumnName(i+1);
                    colTypes[i] = meta.getColumnType(i+1);
                }
                if (unique) {
                    if (rs.next()) {
                        Object ret = rowMapper.mapRow(rs, colNames, colTypes);
                        if (rs.next())
                            throw new NonUniqueResultException("Query returns too many results but expect only one.");
                        return ret;
                    }
                    //throw new ResourceNotFoundException("Query returns empty result set.");
                    return null;
                }
                List<Object> list = new ArrayList<Object>(getFetchSize());
                while (rs.next()) {
                    list.add(rowMapper.mapRow(rs, colNames, colTypes));
                }
                return list;
            }

            @Override
            protected int getFetchSize() {
                return unique ? 2 : fetchSize;
            }
        }.execute(txManager, preparedQuery, getSqlParams(this.argIndex, this.argGets, args));
    }

}
