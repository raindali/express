package org.expressme.modules.persist;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.expressme.persist.Db;
import org.expressme.persist.Utils;

import com.dali.validator.ValidatorUtils;

@SuppressWarnings("unchecked")
public abstract class ActiveRecord<T, ID> {
	final Log log = LogFactory.getLog(getClass());
	private Map<String, String> errors = new LinkedHashMap<String, String>();
	private Db db;

	public boolean valid(String name) {
		Map<String, String> es = ValidatorUtils.validator((T) this, name);
		errors.putAll(es);
		return es.size() == 0;
	}

	public void setDb(Db db) {
		this.db = db;
	}

	private Db getDb() {
		if (db == null) {
			db = DbContext.getDb();
		}
		return db;
	}
	/**
	 * Create an entity in database, writing all insertable properties.
	 * 
	 * @param entity Entity object instance.
	 */
	public void create() {
		getDb().create(this);
	}

	/**
	 * Update the entity with all updatable properties.
	 * 
	 * @param entity Entity object instance.
	 */
	public void updateEntity() {
		getDb().updateEntity(this);
	}

	/**
	 * Update the entity with specified properties.
	 * 
	 * @param entity Entity object instance.
	 * @param properties Properties that are about to update.
	 */
	public void updateProperties(String... properties) {
		getDb().updateProperties(this, properties);
	}

	/**
	 * Delete an entity by its id value.
	 * 
	 * @param clazz Entity class type.
	 * @param idValue Id value.
	 */
	public void deleteById() {
		deleteById(getIdValue());
	}

	public void deleteById(ID idValue) {
		getDb().deleteById(this.getClass(), idValue);
	}

	/**
	 * Delete an entity by its id property. For example:
	 * 
	 * User user = new User(12300); // id=12300
	 * getDb().deleteEntity(user);
	 * 
	 * @param entity Entity object instance.
	 */
	public void deleteEntity() {
		getDb().deleteEntity(this);
	}


	/**
	 * Execute any update SQL statement.
	 * 
	 * @param sql SQL query.
	 * @param params SQL parameters.
	 * @return Number of affected rows.
	 */
	public int executeUpdate(String sql, Object... params) {
		return getDb().executeUpdate(sql, params);
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
		return selectById(getIdValue());
	}

	public T selectById(ID idValue) {
		Object entity = getDb().selectById(this.getClass(), idValue);
		Utils.copy(this, entity);
		return (T) this;
	}
	
	/**
	 * Query for one single object. For example:
	 * <code>
	 * User user = getDb().queryForObject("select * from User where name=?", "Michael");
	 * </code>
	 * 
	 * @param sql SQL query statement.
	 * @param args SQL query parameters.
	 * @return The only one single result, or null if no result.
	 */
	public T selectEntity(String sql, Object... args) {
		Object entity = getDb().selectForObject(getActurlClass(), sql, args);
		return convert(entity);
	}

	/**
	 * Query for list. For example:
	 * <code>
	 * List&lt;User&gt; users = getDb().queryForList("select * from User where age>?", 20);
	 * </code>
	 * 
	 * @param <T> Return type of list element.
	 * @param sql SQL query.
	 * @param params SQL parameters.
	 * @return List of query result.
	 */
	public List<T> selectForList(String sql, Object... params) {
		List<?> list = getDb().selectForList(getActurlClass(), sql, params);
		List<T> rest = new ArrayList<T>(list.size());
		for (Object entity : list) {
			rest.add(convert(entity));
		}
		return rest;
	}

	public void selectProperties(String... properties) {
		getDb().updateProperties(this, properties);
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
	public List<T> selectForLimitedList(String sql, int first, int max, Object... args) {
		List<?> list = getDb().selectForLimitedList(getActurlClass(), sql, first, max, args);
		List<T> rest = new ArrayList<T>(list.size());
		for (Object entity : list) {
			rest.add(convert(entity));
		}
		return rest;
	}
	private Class<?> getActurlClass() {
		return Utils.getEntityClass(getClass());
	}

	private ID getIdValue() {
		Map<String, Method> getter = Utils.findPublicGetters(this.getClass());
		String idProperty = Utils.findIdProperty(getter);
		try {
			return (ID) getter.get(idProperty).invoke(this, new Object[] {});
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}


	private <E> T convert(E entity) {
		if (entity == null) {
			return null;
		} else if (entity.getClass() == this.getClass()) {
			return (T) entity;
		} else {
			Utils.copy(this, entity);
			return (T) this;
		}
	}
}