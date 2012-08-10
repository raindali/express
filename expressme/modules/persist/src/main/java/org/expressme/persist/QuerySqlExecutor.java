package org.expressme.persist;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.expressme.persist.dialect.Dialect;
import org.expressme.persist.mapper.DynamicBeanRowMapper;
import org.expressme.persist.mapper.MapperUtils;
import org.expressme.persist.mapper.ObjectArrayRowMapper;
import org.expressme.persist.mapper.RowMapper;

/**
 * Execute SQL for query.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class QuerySqlExecutor extends SqlExecutor {

    final String preparedQuery;
    final int fetchSize;
    final boolean unique;
    final int[] argIndex;
    final Method[] argGets;
    final RowMapper<?> rowMapper;

    public QuerySqlExecutor(Method method, Dialect dialect) {
        Query query = method.getAnnotation(Query.class);
        String sqlQuery = query.value();
        fetchSize = query.fetchSize();
        if (sqlQuery.trim().length()==0)
            throw new DaoConfigException("Missing SQL query in @Query on method: " + method.toString());

        // get index of arg of @FirstResult and @MaxResults:
        Annotation[] annos = getAnnotationsOfArgs(method);
        int indexOfFirstResult = (-1);
        int indexOfMaxResults = (-1);
        for (int i=0; i<annos.length; i++) {
            Annotation anno = annos[i];
            if (anno.annotationType().equals(FirstResult.class)) {
                if (indexOfFirstResult!=(-1))
                    throw new DaoConfigException("Cannot mark more than one @FirstResult in parameters of method: " + method.toString());
                indexOfFirstResult = i;
            }
            else if (anno.annotationType().equals(MaxResults.class)) {
                if (indexOfMaxResults!=(-1))
                    throw new DaoConfigException("Cannot mark more than one @MaxResults in parameters of method: " + method.toString());
                indexOfMaxResults = i;
            }
        }
        // check:
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (indexOfFirstResult!=(-1) && !isInt(parameterTypes[indexOfFirstResult]))
            throw new DaoConfigException("@FirstResult can only apply int-type of argument in method: " + method.toString());
        if (indexOfMaxResults!=(-1) && !isInt(parameterTypes[indexOfMaxResults]))
            throw new DaoConfigException("@MaxResults can only apply int-type of argument in method: " + method.toString());

        // build query:
        String[] argNames = JdbcUtils.getParameterNames(sqlQuery);
        this.preparedQuery = buildPreparedQuery(sqlQuery, indexOfFirstResult!=(-1), indexOfMaxResults!=(-1), dialect);
        this.unique = method.getAnnotation(Unique.class)!=null;

        // set @FirstResult and @MaxResults:
        int[] limitIndex = null;
        if (indexOfFirstResult!=(-1) && indexOfMaxResults!=(-1)) {
            boolean switchLimitParams = dialect.bindLimitParametersInReverseOrder();
            limitIndex = new int[2];
            limitIndex[0] = switchLimitParams ? indexOfMaxResults : indexOfFirstResult;
            limitIndex[1] = switchLimitParams ? indexOfFirstResult : indexOfMaxResults;
        }
        else if (indexOfFirstResult!=(-1)) {
            limitIndex = new int[1];
            limitIndex[0] = indexOfFirstResult;
        }
        else if (indexOfMaxResults!=(-1)) {
            limitIndex = new int[1];
            limitIndex[0] = indexOfMaxResults;
        }

        // init parameter index:
        int[] paramIndex = new int[argNames.length];
        Method[] paramMethods = new Method[argNames.length];
        for (int i=0; i<argNames.length; i++) {
            String argName = argNames[i];
            int pos = indexOfArgByName(method, annos, argName);
            paramIndex[i] = pos;
            String prop = getPropertyName(argName);
            if (prop!=null)
                paramMethods[i] = findGetterByPropertyName(parameterTypes[pos], prop);
        }

        // build all index:
        this.argIndex = new int[paramIndex.length + (limitIndex==null ? 0 : limitIndex.length)];
        this.argGets = new Method[argIndex.length];
        if (limitIndex==null) {
            System.arraycopy(paramIndex, 0, argIndex, 0, paramIndex.length);
            System.arraycopy(paramMethods, 0, argGets, 0, paramMethods.length);
        }
        else {
            if (dialect.bindLimitParametersFirst()) {
                System.arraycopy(limitIndex, 0, this.argIndex, 0, limitIndex.length);
                System.arraycopy(paramIndex, 0, this.argIndex, limitIndex.length, paramIndex.length);
                System.arraycopy(paramMethods, 0, this.argGets, limitIndex.length, paramMethods.length);
            }
            else {
                System.arraycopy(paramIndex, 0, this.argIndex, 0, paramIndex.length);
                System.arraycopy(paramMethods, 0, this.argGets, 0, paramMethods.length);
                System.arraycopy(limitIndex, 0, this.argIndex, paramIndex.length, limitIndex.length);
            }
        }

        // init @MappedBy:
		MappedBy mappedBy = method.getAnnotation(MappedBy.class);
		if (mappedBy != null) {
			try {
				this.rowMapper = mappedBy == null ? new ObjectArrayRowMapper() : mappedBy.value().newInstance();
			} catch (Exception e) {
				throw new DaoConfigException("Cannot create RowMapper instance for method: " + method.toString());
			}
			checkReturnType(method);
		} else {
			try {
				Class<?> genericType = JdbcUtils.getRowMapperGenericType(method);
				Class<? extends RowMapper<?>> mapper = MapperUtils.getRowMapper(genericType);
				this.rowMapper = mapper == null? new DynamicBeanRowMapper(genericType): mapper.newInstance();
			} catch (Exception e) {
				throw new DaoConfigException("Cannot create RowMapper instance for method: " + method.toString());
			}
		}
        log.info("Generate method '" + methodToString(method) + "' for prepared query: " + preparedQuery);
    }

    String buildPreparedQuery(String sqlQuery, boolean hasFirstResult, boolean hasMaxResults, Dialect dialect) {
        String preparedQuery = JdbcUtils.getPreparedSql(sqlQuery).trim();
        if (!hasFirstResult && !hasMaxResults)
            return preparedQuery;
        return dialect.getLimitSQL(preparedQuery, hasFirstResult, hasMaxResults);
    }

    void checkReturnType(Method method) {
        Class<?> returnType = method.getReturnType();
        if (this.unique) {
            Class<?> expectedType = JdbcUtils.getRowMapperGenericType(this.rowMapper.getClass());
            if (returnType.isPrimitive())
                returnType = getWrapperClass(returnType);
            if (! returnType.isAssignableFrom(expectedType))
                throw new DaoConfigException("Return type is expected as '" + expectedType.getName() + "' with @Unique, but actually is '" + returnType.getName() + "' in method: " + method.toString() + ", missing @MappedBy?");
        }
        else {
            if (! returnType.isAssignableFrom(List.class))
                throw new DaoConfigException("Return type is expected as 'java.util.List' without @Unique, but actually is '" + returnType.getName() + "' in method: " + method.toString());
        }
    }

    Class<?> getWrapperClass(Class<?> returnType) {
        if (boolean.class.equals(returnType))
            return Boolean.class;
        if (byte.class.equals(returnType))
            return Byte.class;
        if (char.class.equals(returnType))
            return Character.class;
        if (short.class.equals(returnType))
            return Short.class;
        if (int.class.equals(returnType))
            return Integer.class;
        if (long.class.equals(returnType))
            return Long.class;
        if (float.class.equals(returnType))
            return Float.class;
        if (double.class.equals(returnType))
            return Double.class;
        throw new DaoConfigException("No wrapper class for primitive type: " + returnType);
    }

    @Override
    public Object execute(TransactionManager txManager, Object... args) {
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
                    throw new ResourceNotFoundException("Query returns empty result set.");
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
        }.execute(txManager, this.preparedQuery, getSqlParams(this.argIndex, this.argGets, args));
    }

}
