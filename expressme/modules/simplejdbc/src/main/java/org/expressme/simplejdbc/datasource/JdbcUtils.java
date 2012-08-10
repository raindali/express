package org.expressme.simplejdbc.datasource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Utils for JDBC operation.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class JdbcUtils {

    private JdbcUtils() {}

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
