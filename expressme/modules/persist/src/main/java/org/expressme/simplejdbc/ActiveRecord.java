package org.expressme.simplejdbc;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.expressme.simplejdbc.annotations.Entity;

@SuppressWarnings("unchecked")
public abstract class ActiveRecord<T, ID extends Serializable> {
	final Log log = LogFactory.getLog(getClass());
	private Db db = new Db();

	public void setDb(Db db) {
		this.db = db;
	}
	
	/**
	 * Create an entity in database, writing all insertable properties.
	 * 
	 * @param entity Entity object instance.
	 */
	public void create() {
		db.create((T) this);
	}

	/**
	 * Update the entity with all updatable properties.
	 * 
	 * @param entity Entity object instance.
	 */
	public void updateEntity() {
		db.updateEntity((T) this);
	}

	/**
	 * Update the entity with specified properties.
	 * 
	 * @param entity Entity object instance.
	 * @param properties Properties that are about to update.
	 */
	public void updateProperties(String... properties) {
		db.updateProperties((T) this, properties);
	}


	/**
	 * Delete an entity by its id value.
	 * 
	 * @param clazz Entity class type.
	 * @param idValue Id value.
	 */
	public void deleteById() {
		db.deleteById(getActurlClass(), getIdValue());
	}

	/**
	 * Delete an entity by its id property. For example:
	 * 
	 * User user = new User(12300); // id=12300
	 * db.deleteEntity(user);
	 * 
	 * @param entity Entity object instance.
	 */
	public void deleteEntity() {
		db.deleteEntity((T) this);
	}

	/**
	 * Get entity by its id.
	 * 
	 * @param <T> Entity class type.
	 * @param clazz Entity class type.
	 * @param idValue Id value.
	 * @return Entity instance, or null if no such entity.
	 */
	public T selectById() {
		return (T) db.selectById(getActurlClass(), getIdValue());
	}

	/**
	 * Execute any update SQL statement.
	 * 
	 * @param sql SQL query.
	 * @param params SQL parameters.
	 * @return Number of affected rows.
	 */
	public int executeUpdate(String sql, Object... params) {
		return db.executeUpdate(sql, params);
	}

	/**
	 * Query for long result. For example:
	 * <code>
	 * long count = db.queryForLong("select count(*) from User where age>?", 20);
	 * </code>
	 * 
	 * @param sql SQL query statement.
	 * @param args SQL query parameters.
	 * @return Long result.
	 */
	public long queryForLong(String sql, Object... args) {
		return db.queryForLong(sql, args);
	}

	/**
	 * Query for int result. For example:
	 * <code>
	 * int count = db.queryForLong("select count(*) from User where age>?", 20);
	 * </code>
	 * 
	 * @param sql SQL query statement.
	 * @param args SQL query parameters.
	 * @return Int result.
	 */
	public int queryForInt(String sql, Object... args) {
		return db.queryForInt(sql, args);
	}

	/**
	 * Query for one single object. For example:
	 * <code>
	 * User user = db.queryForObject("select * from User where name=?", "Michael");
	 * </code>
	 * 
	 * @param sql SQL query statement.
	 * @param args SQL query parameters.
	 * @return The only one single result, or null if no result.
	 */
	public T queryForObject(String sql, Object... args) {
		return db.queryForObject(sql, args);
	}

	/**
	 * Query for list. For example:
	 * <code>
	 * List&lt;User&gt; users = db.queryForList("select * from User where age>?", 20);
	 * </code>
	 * 
	 * @param <T> Return type of list element.
	 * @param sql SQL query.
	 * @param params SQL parameters.
	 * @return List of query result.
	 */
	public List<T> queryForList(String sql, Object... params) {
		return db.queryForList(sql, params);
	}

	/**
	 * Query for limited list. For example:
	 * <code>
	 * // first 5 users:
	 * List&lt;User&gt; users = User.queryForList("select * from User where age>?", 0, 5, 20);
	 * </code>
	 * 
	 * @param <T> Return type of list element.
	 * @param sql SQL query.
	 * @param first First result index.
	 * @param max Max results.
	 * @param params SQL parameters.
	 * @return List of query result.
	 */
	public List<T> queryForLimitedList(String sql, int first, int max, Object... args) {
		return db.queryForLimitedList(sql, first, max, args);
	}

	private ID getIdValue() {
		return null;
	}

//	private Class<?> getActurlClass() {
//		return ((T) this).getClass();
//	}
	
	private Class<?> getActurlClass() {
		Class<?> clazz = this.getClass();
		while (clazz != ActiveRecord.class) {
			if (clazz.isAnnotationPresent(Entity.class)) {
				return clazz;
			}
			clazz = clazz.getSuperclass();
		}
		return null;
	}

}