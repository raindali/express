package org.expressme.persist;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * DataSource implementation by driver manager for test purpose.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class DriverManagerDataSource implements DataSource {

    final String driverClass;
    final String url;
    final String username;
    final String password;

    public DriverManagerDataSource(String driverClass, String url, String username, String password) {
        this.driverClass = driverClass;
        this.url = url;
        this.username = username;
        this.password = password;
        try {
            Class.forName(driverClass);
        }
        catch (ClassNotFoundException e) {
            throw new DataAccessException("Cannot load driver.", e);
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public Connection getConnection(String username, String password) throws SQLException {
        return getConnection();
    }

    private PrintWriter printWriter;
    private int timeout;

    public PrintWriter getLogWriter() throws SQLException {
        return printWriter;
    }

    public void setLogWriter(PrintWriter out) throws SQLException {
        this.printWriter = out;
    }

    public int getLoginTimeout() throws SQLException {
        return timeout;
    }

    public void setLoginTimeout(int seconds) throws SQLException {
        this.timeout = seconds;
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

}
