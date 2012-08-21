package org.expressme.simplejdbc.core;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.expressme.simplejdbc.DbException;
import org.expressme.simplejdbc.datasource.PreparedStatementCallback;
import org.expressme.simplejdbc.datasource.ResultSetCallback;
import org.expressme.simplejdbc.datasource.TransactionManager;

public class JdbcTemplate {
	final Log log = LogFactory.getLog(getClass());
	TransactionManager txManager;
	int fetchSize = 20;

	public JdbcTemplate() {
	}
	public JdbcTemplate(TransactionManager txManager, int fetchSize) {
		this.txManager = txManager;
		this.fetchSize = fetchSize;
	}

	public void setTxManager(TransactionManager txManager) {
		this.txManager = txManager;
	}

	public void setFetchSize(int fetchSize) {
		this.fetchSize = fetchSize;
	}

	public <T> List<T> query(String sql, Object[] args, final RowMapper<T> rowMapper) throws DbException {
		//		log.debug(sql + " - " + Arrays.toString(args));
		System.out.println(sql + " - " + Arrays.toString(args));
		return new ResultSetCallback<List<T>>() {
			@Override
			protected List<T> doInResultSet(ResultSet rs) throws SQLException {
				ResultSetMetaData meta = rs.getMetaData();
				int colCount = meta.getColumnCount();
				String[] colNames = new String[colCount];
				int[] colTypes = new int[colCount];
				for (int i = 0; i < colCount; i++) {
					colNames[i] = meta.getColumnName(i + 1);
					colTypes[i] = meta.getColumnType(i + 1);
				}
				List<T> list = new ArrayList<T>(getFetchSize());
				while (rs.next()) {
					list.add(rowMapper.mapRow(rs, colNames, colTypes));
				}
				return list;
			}

			@Override
			protected int getFetchSize() {
				return fetchSize;
			}
		}.execute(txManager, sql, args);
	}

	public int update(String sql, Object... args) throws DbException {
		//		log.debug(sql + " - " + Arrays.toString(args));
		System.out.println(sql + " - " + Arrays.toString(args));
		return new PreparedStatementCallback<Integer>() {
			@Override
			protected Integer doInPreparedStatement(PreparedStatement ps) throws SQLException {
				int ret = ps.executeUpdate();
				return ret;
			}
		}.execute(txManager, sql, args);
	}
}
