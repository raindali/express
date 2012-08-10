package org.expressme.persist;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Simplify the JDBC operations during connection.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 * 
 * @param <T> Generic callback type.
 */
public abstract class ConnectionCallback<T> {

    public final T execute(TransactionManager txManager) {
        Connection connection = txManager.getCurrentConnection();
        try {
            return doInConnection(connection);
        }
        catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    protected abstract T doInConnection(Connection connection) throws SQLException;
}
