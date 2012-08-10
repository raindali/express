package com.javaeye.i2534;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Transactional
public final class SpringJdbcTemplate {

	// ////////////////////////////////查询操作/////////////////////////////////////////

	// ///////////////////JdbcFinder////////////////////////////////

	public <T> T find(JdbcFinder<T> finder) {
		return find(finder.getOrigSql(), finder.getClazz(), finder.getParams());
	}

	public <T> List<T> findList(JdbcFinder<T> finder) {
		return findList(finder.getOrigSql(), finder.getClazz(),
				finder.getParams());
	}

	public <T> List<T> findListBySelect(JdbcFinder<T> finder) {
		return findListBySelect(finder.getOrigSql(), finder.getClazz(),
				finder.getParams());
	}

	public <T> T findBySelect(JdbcFinder<T> finder) {
		return findBySelect(finder.getOrigSql(), finder.getClazz(),
				finder.getParams());
	}

	public <T> T findCriterSelect(JdbcFinder<T> finder) {
		return findBySelect(finder.getOrigSql(), finder.getClazz(),
				finder.getMparams());
	}

	public <T> Object findQueryForObject(JdbcFinder<T> finder) {
		return findQueryForObject(finder.getOrigSql(), finder.getParams());
	}

	public <T> List<Object> findQueryForListObject(JdbcFinder<T> finder) {
		return findQueryForListObject(finder.getOrigSql(), finder.getParams());
	}

	public <T> List<Map<String, Object>> findQueryPage(JdbcFinder<T> finder) {
		return findQueryPage(finder.getOrigSql(), finder.getFirstResult(),
				finder.getMaxResults(), finder.getParams());
	}

	public <T> Pagination<T> findQueyForListPage(JdbcFinder<T> finder) {
		Pagination<T> pagination = new Pagination<T>();
		int size = findQueryCount(finder.getRowCountSql(), finder.getParams());
		List<T> list = findQueryPage(finder.getOrigSql(),
				finder.getFirstResult(), finder.getMaxResults(),
				finder.getClazz(), finder.getParams());
		pagination.setSize(size);
		pagination.setList(list);
		return pagination;
	}

	public <T> int findQueryCount(JdbcFinder<T> finder) {
		return findQueryCount(finder.getOrigSql(), finder.getParams());
	}

	// ///////////////////////JdbcFinder Map
	public <T> Pagination<T> findCriterForPage(JdbcFinder<T> finder) {
		String rowcountsql=finder.getRowCountSql();
		int count = findQueryCount(rowcountsql, finder.getMparams());
		List<Map<String, Object>> result = findQueryPage(finder.getOrigSql(),
				finder.getFirstResult(), finder.getMaxResults(),
				finder.getMparams());
		List<T> list = builderList(result, finder.getClazz());
		return new Pagination<T>(count, list);
	}

	public <T> List<T> findCriterList(JdbcFinder<T> finder) {
		if (finder.getMaxResults() > 0) {
			List<Map<String, Object>> result = findQueryPage(
					finder.getOrigSql(), finder.getFirstResult(),
					finder.getMaxResults(), finder.getMparams());
			return builderList(result, finder.getClazz());
		}
		List<Map<String, Object>> result = simpleJdbcTemplate.queryForList(
				finder.getOrigSql(), finder.getMparams());
		return builderList(result, finder.getClazz());
	}

	public <T> T findCriterBean(JdbcFinder<T> finder) {
		/**
		 * return findQueryBean(finder.getOrigSql(), finder.getClazz(),
		 * finder.getMparams());
		 */
		return findCriterSelect(finder);
	}

	public <T> T findCriterUnqieBean(JdbcFinder<T> finder) {
		List<T> list = findCriterList(finder);
		if (CollectionUtils.isEmpty(list))
			return null;
		return list.get(0);
	}

	public <T> int findCriterCount(JdbcFinder<T> finder) {
		return findQueryCount(finder.getOrigSql(), finder.getMparams());
	}

	public <T> List<Map<String, Object>> findCriterForList(JdbcFinder<T> finder) {
		return findQueryForList(finder.getOrigSql(), finder.getParams());
	}

	@Transactional(readOnly = true)
	public <T> int findCriterForInt(JdbcFinder<T> finder) {
		return simpleJdbcTemplate.queryForInt(finder.getOrigSql(),
				finder.getMparams());
	}

	@Transactional(readOnly = true)
	public <T> Object findCriterForObject(JdbcFinder<T> finder) {
		return findQueryForObject(finder.getOrigSql(), finder.getMparams());
	}

	@Transactional(readOnly = true)
	public <T> Long findCriterForLong(JdbcFinder<T> finder) {
		return simpleJdbcTemplate.queryForLong(finder.getOrigSql(),
				finder.getMparams());
	}

	@Transactional(readOnly = true)
	public <T> Map<String, Object> findCriterForMap(JdbcFinder<T> finder) {
		return simpleJdbcTemplate.queryForMap(finder.getOrigSql(),
				finder.getMparams());
	}

	@Transactional(readOnly = true)
	public <T> List<Map<String, Object>> findCriterForListMap(
			JdbcFinder<T> finder) {
		return simpleJdbcTemplate.queryForList(finder.getOrigSql(),
				finder.getMparams());
	}

	// //////////////////////////-----------------JdbcFinder end

	@Transactional(readOnly = true)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> T find(String sql, Class<T> clazz, Object... args) {
		return (T) this.simpleJdbcTemplate.getJdbcOperations().queryForObject(
				sql, args, new BeanPropertyRowMapper(clazz));
	}

	/**
	 * 正常查询
	 * <p>
	 * 如AA对象里有a,b,c属性,表里有a,b,c,d 查询语句为select a,b,c,d from AA，则会抛出异常,
	 * 而findListBySelect
	 * </p>
	 * 数据列表
	 * 
	 * @param <T>
	 * @param sql
	 * @param args
	 * @param clazz
	 * @return
	 */
	@Transactional(readOnly = true)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> List<T> findList(String sql, Class<T> clazz, Object[] args) {
		return this.simpleJdbcTemplate.getJdbcOperations().query(sql, args,
				new BeanPropertyRowMapper(clazz));
	}

	/**
	 * 查询翻转
	 * <p>
	 * 由查询的属性生成Bean
	 * </p>
	 * <p>
	 * 如:select a,b,c from AA
	 * </p>
	 * 则会把a,b,c的值塞到AA对象里,如AA里d属性则会直接无视
	 * 
	 * @param <T>
	 * @param sql
	 * @param args
	 * @param clazz
	 * @return
	 * @throws NoSuchMethodException
	 */

	public <T> List<T> findListBySelect(String sql, Class<T> clazz,
			Object... args) {
		try {
			List<Map<String, Object>> result = findQueryForList(sql, args);
			List<T> list = new ArrayList<T>();
			T o = null;
			for (Map<String, Object> map : result) {
				o = BeanUtils.instantiate(clazz);
				MyBeanUtils.convertoBeanByMap(map, o);
				list.add(o);
			}
			return list;
		} catch (NoSuchMethodException ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}

	/**
	 * 说明同上
	 * 
	 * @param <T>
	 * @param sql
	 * @param args
	 * @param clazz
	 * @return
	 */

	public <T> T findBySelect(String sql, Class<T> clazz, Object... args) {
		try {
			Map<String, Object> result = findQueryForMap(sql, args);
			T o = BeanUtils.instantiate(clazz);
			MyBeanUtils.convertoBeanByMap(result, o);
			return o;
		} catch (NoSuchMethodException ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}

	private <T> T findBySelect(String sql, Class<T> clazz, Map<String, ?> args) {
		try {
			Map<String, Object> result = simpleJdbcTemplate.queryForMap(sql,
					args);
			T o = BeanUtils.instantiate(clazz);
			MyBeanUtils.convertoBeanByMap(result, o);
			return o;
		} catch (NoSuchMethodException ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}

	/**
	 * 分页查询
	 * 
	 * @param <T>
	 * @param sql
	 * @param firstResult
	 * @param maxResults
	 * @param args
	 * @param clazz
	 * @return
	 */

	public <T> List<T> findQueryPage(String sql, int firstResult,
			int maxResults, Class<T> clazz, Object[] args) {
		List<Map<String, Object>> result = findQueryPage(sql, firstResult,
				maxResults, args);
		return builderList(result, clazz);
	}

	public <T> List<T> findQueryList(String sql, Class<T> clazz,
			Map<String, ?> args) {
		List<Map<String, Object>> result = simpleJdbcTemplate.queryForList(sql,
				args);
		return builderList(result, clazz);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> T findQueryBean(String sql, Class<T> clazz, Map<String, ?> args) {
		Object o = simpleJdbcTemplate.queryForObject(sql,
				new BeanPropertyRowMapper(clazz), args);
		return o == null ? null : (T) o;
	}

	/**
	 * 无用
	 * <p>
	 * 暂时保留
	 * </p>
	 * 
	 * @deprecated
	 * @param <T>
	 * @param sql
	 * @param clazz
	 * @param id
	 * @return
	 */
	public <T> T get(String table, Class<T> clazz, Serializable id) {
		String sql = "select * from " + table + " where id =?";
		return getSimpleJdbcTemplate().queryForObject(sql, clazz, id);
	}

	public Map<String, Object> findQueryForMap(String sql, Object... args) {
		return simpleJdbcTemplate.queryForMap(sql, args);
	}

	public <T> Map<String, Object> findQueryForMap(JdbcFinder<T> finder) {
		return findQueryForMap(finder.getOrigSql(), finder.getParams());
	}

	private <T> List<T> builderList(List<Map<String, Object>> result,
			Class<T> clazz) {
		if (result == null || result.size() < 1)
			return null;
		try {
			List<T> list = new ArrayList<T>();
			T o = null;
			for (Map<String, Object> map : result) {
				o = BeanUtils.instantiate(clazz);
				MyBeanUtils.convertoBeanByMap(map, o);
				list.add(o);
			}
			return list;
		} catch (NoSuchMethodException ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}

	/**
	 * 
	 * @param table
	 * @return
	 */

	public int findQueryCount(String sql, Object... args) {
		if (args == null || args.length < 1)
			return getSimpleJdbcTemplate().queryForInt(sql);
		return getSimpleJdbcTemplate().queryForInt(sql, args);
	}

	public int findQueryCount(String sql, Map<String, ?> args) {
		return getSimpleJdbcTemplate().queryForInt(sql, args);
	}

	public <T> Long findQueryForLong(JdbcFinder<T> finder) {
		return findQueryForLong(finder.getOrigSql(), finder.getParams());
	}

	/**
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */

	public Long findQueryForLong(String sql, Object... args) {
		return getSimpleJdbcTemplate().queryForLong(sql, args);
	}

	/**
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */

	public Object findQueryForObject(String sql, Object... args) {
		return getSimpleJdbcTemplate().queryForObject(sql, Object.class, args);
	}

	public Object findQueryForObject(String sql, Map<String, ?> args) {
		return getSimpleJdbcTemplate().queryForObject(sql, Object.class, args);
	}

	/**
	 * 返回集合
	 * 
	 * @param sql
	 * @param args
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Map<String, Object>> findQueryForList(String sql,
			Object... args) {
		if (args == null || args.length < 1)
			return getSimpleJdbcTemplate().queryForList(sql);
		return getSimpleJdbcTemplate().queryForList(sql, args);
	}

	public List<Object> findQueryForListObject(String sql, Object... args) {
		List<Map<String, Object>> listM = findQueryForList(sql, args);
		List<Object> list = new ArrayList<Object>();
		for (Map<String, Object> map : listM) {
			list.addAll(map.values());
		}
		return list;
	}

	/**
	 * 分页
	 * 
	 * @param sql
	 * @param firstResult
	 * @param maxResults
	 * @param args
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Map<String, Object>> findQueryPage(String sql, int firstResult,
			int maxResults, Object... args) {
		StringBuilder hql = new StringBuilder();
		if (firstResult == 0 && maxResults == -1) {
			hql.append("select * from ( ").append(sql).append(" ) ");
		} else if (firstResult == 0) {
			hql.append("select * from ( ").append(sql)
					.append(" ) where rownum <= ").append(maxResults);
		} else {
			hql.append("select * from (select row_.*,rownum rownum_ from ( ")
					.append(sql).append(" ) row_ where rownum <= ")
					.append(maxResults + firstResult)
					.append(" ) where rownum_ > ").append(firstResult);
		}
		if (args == null || args.length < 1)
			return getSimpleJdbcTemplate().queryForList(hql.toString());
		return getSimpleJdbcTemplate().queryForList(hql.toString(), args);
	}

	@Transactional(readOnly = true)
	public List<Map<String, Object>> findQueryPage(String sql, int firstResult,
			int maxResults, Map<String, ?> args) {
		StringBuilder hql = new StringBuilder();
		if(CustomerContextHolder.getCustomerType()==CustomerType.MYSQL){
			hql.append(sql).append(" ").append(" limit ")
				.append(firstResult).append(",").append(maxResults);
		}else{//默认Oracle
			if (firstResult == 0 && maxResults == -1) {
				hql.append("select * from ( ").append(sql).append(" ) ");
			} else if (firstResult == 0) {
				hql.append("select * from ( ").append(sql)
						.append(" ) where rownum <= ").append(maxResults);
			} else {
				hql.append("select * from (select row_.*,rownum rownum_ from ( ")
						.append(sql).append(" ) row_ where rownum <= ")
						.append(maxResults + firstResult)
						.append(" ) where rownum_ > ").append(firstResult);
			}	
		}
		return getSimpleJdbcTemplate().queryForList(hql.toString(), args);
	}

	// ////////////////////////////////////////以下为更新操作
	public int update(String sql, Object... args) {
		return this.getSimpleJdbcTemplate().update(sql, args);
	}

	/**
	 * 插入数据返回数据ID
	 * 
	 * @param <T>
	 * @param sql
	 * @param Bean
	 * @return
	 */
	public <T> int update(String sql, T Bean) {

		SqlParameterSource param = new BeanPropertySqlParameterSource(Bean);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.simpleJdbcTemplate.getNamedParameterJdbcOperations().update(sql,
				param, keyHolder);
		return keyHolder.getKey().intValue();
	}

	/**
	 * 修改或删除数据
	 * 
	 * @param sql
	 * @param args
	 */
	public void updateByObjectArray(String sql, Object... args) {
		this.simpleJdbcTemplate.getJdbcOperations().update(sql, args);
	}
	
	/**
	 * 批量更新
	 * @param sql
	 * @param batchArgs
	 */
	public void batchByObjectArray(String sql,List<Object[]> batchArgs){
		this.simpleJdbcTemplate.batchUpdate(sql, batchArgs);
	}

	/**
	 * 修改或删除数据
	 * 
	 * @param sql
	 * @param Bean
	 */
	public <T> void updateByBean(String sql, T Bean) {

		SqlParameterSource param = new BeanPropertySqlParameterSource(Bean);
		this.simpleJdbcTemplate.getJdbcOperations().update(sql, param);
	}
	
	public long getCount(String sql,Object[] objs){
		return simpleJdbcTemplate.queryForLong(sql, objs);
	}

	private SimpleJdbcTemplate simpleJdbcTemplate;

	public SimpleJdbcTemplate getSimpleJdbcTemplate() {
		return simpleJdbcTemplate;
	}

	public void setSimpleJdbcTemplate(SimpleJdbcTemplate simpleJdbcTemplate) {
		this.simpleJdbcTemplate = simpleJdbcTemplate;
	}

}
