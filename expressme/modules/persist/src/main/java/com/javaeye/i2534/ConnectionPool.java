package com.javaeye.i2534;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class ConnectionPool {

	private static Log log = LogFactory.getLog(ConnectionPool.class);

	static {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage(), e);
		}
	}

	public static Connection getConnection() {
		try {
			return DriverManager
					.getConnection(
							"jdbc:sqlserver://127.0.0.1:1433;DatabaseName=cpcns0203_baobiao",
							"sa", "2534");
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

}
