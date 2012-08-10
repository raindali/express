package org.expressme.persist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Simplify the PreparedStatement query.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 * 
 * @param <T> Object type that expected to return.
 */
public abstract class ResultSetCallback<T> {

    public final T execute(TransactionManager txManager, String sql, Object... params) {
        Connection connection = txManager.getCurrentConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);
            JdbcUtils.bindParameters(ps, params);
            rs = ps.executeQuery();
            rs.setFetchSize(getFetchSize());
            return doInResultSet(rs);
        }
        catch (SQLException e) {
            throw new QueryException(e);
        }
        finally {
            JdbcUtils.close(rs);
            JdbcUtils.close(ps);
        }
    }

    protected abstract int getFetchSize();

    protected abstract T doInResultSet(ResultSet rs) throws SQLException;
}
