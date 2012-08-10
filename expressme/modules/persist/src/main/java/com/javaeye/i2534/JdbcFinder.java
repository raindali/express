package com.javaeye.i2534;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JdbcFinder<T> {

	public JdbcFinder() {
		// setPersistentClass();
		hqlBuilder = new StringBuilder();
	}

	public JdbcFinder(Class<T> clazz) {
		hqlBuilder = new StringBuilder();
		this.clazz = clazz;
	}

	@SuppressWarnings("unchecked")
	public Class<T> getPersistentClass() {
		this.clazz = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		return clazz;
	}

	public JdbcFinder(String sql) {
		hqlBuilder = new StringBuilder(sql);
	}

	public JdbcFinder(String sql, Class<T> clazz) {
		hqlBuilder = new StringBuilder(sql);
		this.clazz = clazz;
	}

	public static <T> JdbcFinder<T> create(String sql) {
		JdbcFinder<T> finder = new JdbcFinder<T>(sql);
		return finder;
	}

	public static <T> JdbcFinder<T> create(String sql, Class<T> clazz) {
		JdbcFinder<T> finder = new JdbcFinder<T>(sql, clazz);
		return finder;
	}

	public JdbcFinder<T> append(String sql) {
		hqlBuilder.append(" ").append(sql).append(" ");
		return this;
	}

	public JdbcFinder<T> where() {
		hqlBuilder.append(" where ");
		return this;
	}

	public JdbcFinder<T> where(String sql) {
		hqlBuilder.append(" where ").append(sql).append(" ");
		return this;
	}

	public JdbcFinder<T> and() {
		hqlBuilder.append(" and ");
		return this;
	}

	public JdbcFinder<T> and(String sql) {
		hqlBuilder.append(" and ").append(sql).append(" ");
		return this;
	}

	public JdbcFinder<T> or() {
		hqlBuilder.append(" or ");
		return this;
	}

	public JdbcFinder<T> or(String sql) {
		hqlBuilder.append(" or ").append(sql).append(" ");
		return this;
	}

	public JdbcFinder<T> orderBy_desc(String param) {
		setOrders(param, OrderType.DESC);
		return this;
	}

	public JdbcFinder<T> orderBy_asc(String param) {
		setOrders(param, OrderType.ASC);
		return this;
	}

	/**
	 * like
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public JdbcFinder<T> like(Object value) {
		if (value instanceof String) {
			if (value != null)
				value = (StringUtils.removeSpaces((String) value))
						.toUpperCase();
		}
		String param = setWheres(value);
		hqlBuilder.append(" like :").append(param);
		return setParam(param, "%" + value + "%");
	}
	
	public JdbcFinder<T> likeNoUn(Object value) {
		String param = setWheres(value);
		hqlBuilder.append(" like :").append(param);
		return setParam(param, "%" + value + "%");
	}

	/**
	 * 相等
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public JdbcFinder<T> eq(Object value) {
		if (value instanceof String) {
			value = ((String) value).toUpperCase();
		}
		String param = setWheres(value);
		hqlBuilder.append(" =:").append(param);
		return setParam(param, value);
	}

	/**
	 * 不相等
	 * 
	 * @param value
	 * @return
	 */
	public JdbcFinder<T> noteq(Object value) {
		String param = setWheres(value);
		hqlBuilder.append(" !=:").append(param);
		return setParam(param, value);
	}

	/**
	 * 大于等于
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public JdbcFinder<T> ge(Object value) {
		String param = setWheres(value);
		hqlBuilder.append(" >=:").append(param);
		return setParam(param, value);
	}

	/**
	 * 大于
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public JdbcFinder<T> gt(Object value) {
		String param = setWheres(value);
		hqlBuilder.append(" >:").append(param);
		return setParam(param, value);
	}

	/**
	 * 小于等于
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public JdbcFinder<T> le(Object value) {
		String param = setWheres(value);
		hqlBuilder.append(" <=:").append(param);
		return setParam(param, value);
	}

	/**
	 * 小于
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public JdbcFinder<T> lt(Object value) {
		String param = setWheres(value);
		hqlBuilder.append(" <:").append(param);
		return setParam(param, value);
	}

	/**
	 * 不等于
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public JdbcFinder<T> ne(Object value) {
		String param = setWheres(value);
		hqlBuilder.append(" !=:").append(param);
		return setParam(param, value);
	}

	/**
	 * 不为空
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public JdbcFinder<T> isNotNull() {
		hqlBuilder.append(" is not null");
		return this;
	}

	/**
	 * 为空
	 * 
	 * @return
	 */
	public JdbcFinder<T> isNull() {
		hqlBuilder.append(" is null");
		return this;
	}

	/**
	 * in
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public JdbcFinder<T> in(List<?> value) {
		String param = setWheres(value);
		hqlBuilder.append(" in(:").append(param).append(")");
		return setParam(param, value);
	}

	/**
	 * in
	 * 
	 * @param sql
	 * @return
	 */
	public JdbcFinder<T> in(String sql) {
		hqlBuilder.append(" in(").append(sql).append(")");
		return this;
	}

	/**
	 * not in
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public JdbcFinder<T> notIn(List<?> value) {
		String param = setWheres(value);
		hqlBuilder.append(" not in(:").append(param).append(")");
		return setParam(param, value);
	}

	/**
	 * not in
	 * 
	 * @param sql
	 * @return
	 */
	public JdbcFinder<T> notIn(String sql) {
		hqlBuilder.append(" not in(").append(sql).append(")");
		return this;
	}

	/**
	 * 统一用and连接,此查询不存在or连接
	 * @param key
	 * @param value
	 * @param type
	 * @return
	 */
	public JdbcFinder<T> search(String key, Object value, SearchType type) {
		if (org.apache.commons.lang.StringUtils.isBlank(key) || value == null)
			return this;
		if(value instanceof String){
			String v = (String)value;
			if(org.apache.commons.lang.StringUtils.isBlank(v)){
				return this;
			}
		}
		if (type == SearchType.EQ) {
			return and(key).eq(value);
		} else if (type == SearchType.NEQ) {
			return and(key).noteq(value);
		} else if (type == SearchType.GE) {
			return and(key).ge(value);
		} else if (type == SearchType.GT) {
			return and(key).gt(value);
		} else if (type == SearchType.LE) {
			return and(key).le(value);
		} else if (type == SearchType.LT) {
			return and(key).lt(value);
		} else if (type == SearchType.LIKE) {
			return and(key).likeNoUn(value);
		}
		throw new RuntimeException("Null in point searchtype");
	}

	/**
	 * 获得原始hql语句
	 * 
	 * @return
	 */
	public String getOrigSql() {
		return hqlBuilder.toString();
	}

	/**
	 * 获得原始sql
	 * 
	 * @param first
	 * @param max
	 * @return
	 */
	public String getOrigSqlPagin(int first, int max) {
		StringBuilder hql = new StringBuilder();
		if (first == 0 && max == -1) {
			hql.append("select * from ( ").append(hqlBuilder).append(" ) ");
		} else if (firstResult == 0) {
			hql.append("select * from ( ").append(hqlBuilder)
					.append(" ) where rownum <= ").append(max);
		} else {
			hql.append("select * from (select row_.*,rownum rownum_ from ( ")
					.append(hqlBuilder).append(" ) row_ where rownum <= ")
					.append(max + first).append(" ) where rownum_ > ")
					.append(first);
		}
		return hql.toString();
	}

	/**
	 * 获得查询数据库记录数的sql语句。
	 * 
	 * @return
	 */
	public String getRowCountSql() {
		String hql = hqlBuilder.toString();
		int fromIndex;
		if (hql.contains("group by")) {
			return "select count(*) from ( ".concat(hql).concat(" )");
		}
		if (hql.toLowerCase().indexOf(",") > 0) {
			fromIndex = hql.toLowerCase().indexOf(",");
		} else {
			fromIndex = hql.toLowerCase().indexOf(FROM);
		}
		String projectionHql = hql.substring(0, fromIndex);

		hql = hql.substring(hql.toLowerCase().indexOf(FROM));
		String rowCountHql = hql.replace(HQL_FETCH, "");
		int index = rowCountHql.indexOf(ORDER_BY);
		if (index > 0) {
			rowCountHql = rowCountHql.substring(0, index);
		}
		return wrapProjection(projectionHql) + rowCountHql;
	}

	public int getFirstResult() {
		return firstResult;
	}

	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}

	public int getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}

	public JdbcFinder<T> setParam(Object args) {
		getValues().add(args);
		return this;
	}

	public JdbcFinder<T> setParam(String key, Object value) {
		if (value != null)
			getMaps().put(key, value);
		return this;
	}

	public JdbcFinder<T> setParam(Map<String, Object> map) {
		getMaps().putAll(map);
		return this;
	}

	/**
	 * 
	 * @param paramMap
	 * @return
	 */
	public JdbcFinder<T> setParams(List<Object> args) {
		for (Object object : args) {
			setParam(object);
		}
		return this;
	}

	private List<String> getOrders() {
		if (orders == null)
			orders = new ArrayList<String>();
		return orders;
	}

	private void setOrders(String order, OrderType orderType) {
		String type = null;
		if (orderType == OrderType.ASC) {
			type = " asc";
		} else {
			type = " desc";
		}
		if (getOrders().size() < 1)
			hqlBuilder.append(" order by ").append(order).append(type);
		else
			hqlBuilder.append(" ,").append(order).append(type);
		getOrders().add(order);
	}

	private String wrapProjection(String projection) {
		if (projection.indexOf("select") == -1) {
			return ROW_COUNT;
		} else {
			return projection.replace("select", "select count(") + ") ";
		}
	}

	private List<Object> getWheres() {
		if (wheres == null) {
			wheres = new ArrayList<Object>();
		}
		return wheres;
	}

	private String setWheres(Object where) {
		String param = "param" + getWheres().size();
		getWheres().add(where);
		return param;
	}

	private List<Object> getValues() {
		if (values == null) {
			values = new ArrayList<Object>();
		}
		return values;
	}

	private Map<String, Object> getMaps() {
		if (maps == null) {
			maps = new ConcurrentHashMap<String, Object>();
		}
		return maps;
	}

	public Object[] getParams() {
		if (values == null || values.size() < 1)
			return null;
		return values.toArray();
	}

	public Map<String, Object> getMparams() {
		return maps;
	}

	public Class<T> getClazz() {
		return clazz;
	}

	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}

	private StringBuilder hqlBuilder;
	private Class<T> clazz;

	private List<Object> values;

	private List<String> orders;

	private List<Object> wheres;

	private Map<String, Object> maps;

	private int firstResult = 0;

	private int maxResults = 0;

	public static final String ROW_COUNT = "select count(*) ";
	public static final String FROM = "from";
	public static final String DISTINCT = "distinct";
	public static final String HQL_FETCH = "fetch";
	public static final String ORDER_BY = "order";

	public static enum OrderType {
		ASC, DESC
	}

	public static enum SearchType {
		EQ, NEQ, GE, GT, LE, LT, LIKE
	}

}