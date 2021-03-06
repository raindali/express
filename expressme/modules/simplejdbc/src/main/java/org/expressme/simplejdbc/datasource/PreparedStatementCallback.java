package org.expressme.simplejdbc.datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.expressme.simplejdbc.DbException;

/**
 * Simplify the PreparedStatement execution.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 * 
 * @param <T> Object type that expected to return.
 */
public abstract class PreparedStatementCallback<T> {

    public final T execute(TransactionManager txManager, String sql, Object... params) {
        Connection connection = txManager.getCurrentConnection();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            JdbcUtils.bindParameters(ps, params);
            return doInPreparedStatement(ps);
        }
        catch (SQLException e) {
            throw convertSQLException(e);
        }
        finally {
            JdbcUtils.close(ps);
        }
    }

    protected DbException convertSQLException(SQLException e) {
        return new DbException(e);
    }

    protected abstract T doInPreparedStatement(PreparedStatement ps) throws SQLException;
}
