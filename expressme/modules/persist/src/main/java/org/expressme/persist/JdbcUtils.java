package org.expressme.persist;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utils for JDBC operation.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class JdbcUtils {

    private JdbcUtils() {}

    public static String getPreparedSql(String sqlQuery) {
        return sqlQuery.replaceAll("(\\:[a-zA-Z0-9]+(\\.[a-zA-Z\\_]{1}[a-zA-Z0-9\\_]*)?)", "?");
    }

    public static String[] getParameterNames(String sqlQuery) {
        List<String> list = new ArrayList<String>();
        int fromIndex = 0;
        for (;;) {
            int pos = sqlQuery.indexOf(':', fromIndex);
            if (pos==(-1))
                break;
            String name = getParameterName(sqlQuery, pos);
            fromIndex = pos + 1 + name.length();
            list.add(name);
        }
        return list.toArray(new String[list.size()]);
    }

    /**
     * Get parameter name from position of ":". For example, 
     * getParameterName("where id=:name", 9) returns "name",
     * getParameterName("where id=:user.name", 9) returns "user.name".
     */
    public static String getParameterName(String sqlQuery, int pos) {
        StringBuilder name = new StringBuilder(10);
        for (int i=pos+1; i<sqlQuery.length(); i++) {
            char ch = sqlQuery.charAt(i);
            if (
                    ((ch>='0' && ch<='9') || (ch>='a' && ch<='z') || (ch>='A' && ch<='Z'))
                    || ch=='.' || ch=='_')
            {
                name.append(ch);
            }
            else {
                break;
            }
        }
        if (name.length()==0)
            throw new DaoConfigException("Bad parameter ':' declaration in SQL: " + sqlQuery + ", position " + pos);
        String s = name.toString();
        if (!s.matches("[0-9a-zA-Z]+(\\.[a-zA-Z\\_]{1}[a-zA-Z0-9\\_]*)?"))
            throw new DaoConfigException("Bad parameter ':" + s + "' declaration in SQL: " + sqlQuery + ", position " + pos);
        return s;
    }

    public static Class<?> getRowMapperGenericType(Class<?> clazz) {
        Type type = clazz.getGenericSuperclass();
        if (type==null || type instanceof Class<?>)
            return Object.class;
        ParameterizedType pt = (ParameterizedType) type;
        type = pt.getActualTypeArguments()[0];
        if (type instanceof GenericArrayType) {
            type = ((GenericArrayType) type).getGenericComponentType();
            return Array.newInstance((Class<?>)type, 0).getClass();
        }
        if (type instanceof ParameterizedType) {
            pt = (ParameterizedType) type;
            type = pt.getRawType();
        }
        return (Class<?>) type;
    }

    public static Class<?> getRowMapperGenericType(Method method) {
        Type type = method.getGenericReturnType();
//        if (type==null || type instanceof Class<?>)
//            return Object.class;
        if (type instanceof Class<?>)
        	return (Class<?>)type;
        ParameterizedType pt = (ParameterizedType) type;
        type = pt.getActualTypeArguments()[0];
        if (type instanceof GenericArrayType) {
            type = ((GenericArrayType) type).getGenericComponentType();
            return Array.newInstance((Class<?>)type, 0).getClass();
        }
        if (type instanceof ParameterizedType) {
            pt = (ParameterizedType) type;
            type = pt.getRawType();
        }
        return (Class<?>) type;
    }

    public static void close(PreparedStatement ps) {
        if(ps!=null) {
            try {
                ps.close();
            }
            catch(SQLException e) {}
        }
    }

    public static void close(ResultSet rs) {
        if(rs!=null) {
            try {
                rs.close();
            }
            catch(SQLException e) {}
        }
    }

    public static void bindParameters(PreparedStatement ps, Object... params) throws SQLException {
        for(int i=0; i<params.length; i++) {
            ps.setObject(i+1, params[i]);
        }
    }
}
