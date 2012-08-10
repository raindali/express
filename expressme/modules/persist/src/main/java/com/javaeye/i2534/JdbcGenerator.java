package com.javaeye.i2534;

import static com.javaeye.i2534.ClassUtils.findFieldName;
import static com.javaeye.i2534.ClassUtils.findIdName;
import static com.javaeye.i2534.ClassUtils.findTableName;
import static com.javaeye.i2534.ClassUtils.listFieldsWithoutId;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.javaeye.i2534.annotations.Column;

/**
 * 增删改查和获取id常用sql通用生成工具,对于同一个vo类,sql语句会缓存
 */
public final class JdbcGenerator {

	private static Map<String, String> sqlCaches = new ConcurrentHashMap<String, String>();

	private JdbcGenerator() {
	}

	/**
	 * 获取插入vo对象的预编译sql语句,无论如何,id总是排在第一个
	 * 
	 * @param c
	 *            vo类,依照注解进行拼接
	 * @return sql语句
	 */
	public static String insert(Class<?> c) {
		if (c == null)
			return null;
		String key = c.getName() + "#insert";
		String sql = sqlCaches.get(key);
		if (sql != null) {
			return sql;
		}
		StringBuilder sb = new StringBuilder();
		StringBuilder values = new StringBuilder();
		sb.append("insert into ");
		sb.append(findTableName(c));
		sb.append("(");
		sb.append(findIdName(c)).append(",");
		values.append("?,");
		for (Field f : listFieldsWithoutId(c)) {
			Column column = f.getAnnotation(Column.class);
			if (column == null || column.insertable()) {
				sb.append(findFieldName(f)).append(",");
				values.append("?,");
			}
		}
		int index = -1;
		if ((index = sb.lastIndexOf(",")) == sb.length() - 1) {
			sb.deleteCharAt(index);
		}
		if ((index = values.lastIndexOf(",")) == values.length() - 1) {
			values.deleteCharAt(index);
		}
		sb.append(") values (");
		sb.append(values);
		sb.append(")");
		sql = sb.toString();
		sqlCaches.put(key, sql);
		return sql;
	}

	/**
	 * 更新vo所有标识字段的预编译语句,无论如何,id总是排在最后一个.
	 */
	public static String update(Class<?> c) {
		if (c == null) {
			return null;
		}
		String key = c.getName() + "#update";
		String sql = sqlCaches.get(key);
		if (sql != null)
			return sql;
		StringBuilder sb = new StringBuilder();
		sb.append("update ");
		sb.append(findTableName(c));
		sb.append(" set ");
		for (Field f : listFieldsWithoutId(c)) {
			Column column = f.getAnnotation(Column.class);
			if (column == null || column.updatable())
				sb.append(findFieldName(f)).append("=?,");
		}
		int index = -1;
		if ((index = sb.lastIndexOf(",")) == sb.length() - 1) {
			sb.deleteCharAt(index);
		}
		sb.append(" where ");
		sb.append(findIdName(c));
		sb.append("=?");
		sql = sb.toString();
		sqlCaches.put(key, sql);
		return sql;
	}

	public static String update(Cnd cnd) {
		Class<?> c = cnd.getClazz();
		if (c == null)
			return null;
		StringBuilder sb = new StringBuilder();
		sb.append("update ");
		sb.append(findTableName(c));
		sb.append(" set ");

		for (String name : cnd.getColumns()) {
			Field f = ClassUtils.findTableField(c, name);
			Column column = f.getAnnotation(Column.class);
			if (column == null || column.updatable())
				sb.append(findFieldName(f)).append("=?,");
		}
		int index = -1;
		if ((index = sb.lastIndexOf(",")) == sb.length() - 1) {
			sb.deleteCharAt(index);
		}
		sb.append(cnd.toString());
		return sb.toString();
	}

	public static String delete(Class<?> c) {
		if (c == null) {
			return null;
		}
		String key = c.getName() + "#delete";
		String sql = sqlCaches.get(key);
		if (sql != null)
			return sql;
		StringBuilder sb = new StringBuilder();
		sb.append("delete from ");
		sb.append(findTableName(c));
		sb.append(" where ");
		sb.append(findIdName(c)).append("=?");
		sql = sb.toString();
		sqlCaches.put(key, sql);
		return sql;
	}

	/**
	 * 删除预编译语句.
	 */
	public static String delete(Cnd cnd) {
		Class<?> c = cnd.getClazz();
		if (c == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("delete from ");
		sb.append(findTableName(c));
		sb.append(cnd.toString());
		return sb.toString();
	}

	/**
	 * 查询所有的vo的预编译语句.会列出数据表中所有记录.没有参数.
	 */
	public static String query(Class<?> c) {
		if (c == null) {
			return null;
		}
		String key = c.getName() + "#query";
		String sql = sqlCaches.get(key);
		if (sql != null)
			return sql;
		StringBuilder sb = new StringBuilder();
		sb.append("select * from ");
		sb.append(findTableName(c));
		sql = sb.toString();
		sqlCaches.put(key, sql);
		return sql;
	}

	public static String query(Cnd cnd) {
		Class<?> c = cnd.getClazz();
		if (c == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("select ");
		String[] columns = cnd.getColumns();
		if (columns == null)
			sb.append("*");
		else {
			for (int i = 0; i < columns.length; i++) {
				Field f = ClassUtils.findTableField(c, columns[i]);
				Column column = f.getAnnotation(Column.class);
				if (column == null || column.updatable())
					sb.append(findFieldName(f)).append(",");
			}
		}
		int index = -1;
		if ((index = sb.lastIndexOf(",")) == sb.length() - 1) {
			sb.deleteCharAt(index);
		}
		sb.append(" from ");
		sb.append(findTableName(c));
		sb.append(cnd.toString());
		return sb.toString();
	}

	/**
	 * 根据传入的vo类型生成id的sql,sql中id的列名为id,步长为1
	 */
	public static String nextId(Class<?> c) {
		if (c == null) {
			return null;
		}
		String key = c.getName() + "#nextId";
		if (sqlCaches.containsKey(key)) {
			return sqlCaches.get(key);
		}
		StringBuilder sb = new StringBuilder();
		sb.append("select (max(");
		sb.append(findIdName(c));
		sb.append(") + 1) as id");
		sb.append(" from ");
		sb.append(findTableName(c));
		String sql = sb.toString();
		sqlCaches.put(key, sql);
		return sql;
	}

	public static void main(String[] args) {
		System.out.println(JdbcGenerator.insert(TestVO.class));
		System.out.println(JdbcGenerator.update(TestVO.class));
		System.out.println(JdbcGenerator.delete(TestVO.class));
		//		System.out.println(JdbcGenerator.fetch(TestVO.class));
		System.out.println(JdbcGenerator.query(TestVO.class));
		TestVO vo = new TestVO();
		try {
			Field f = vo.getClass().getDeclaredField("flag");
			f.setAccessible(true);
			System.out.println(f.get(vo));
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
