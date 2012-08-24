package org.expressme.simplejdbc.core;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.expressme.simplejdbc.DbException;

/**
 * Database interface.
 * 
 * @author Michael Liao
 */
@SuppressWarnings("unchecked")
public class Db {

    final Log log = LogFactory.getLog(getClass());

    JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    final Map<String, EntityOperation<?>> entityMap = new ConcurrentHashMap<String, EntityOperation<?>>();
    final static RowMapper<Long> longRowMapper = new RowMapper<Long>() {
        public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getLong(1);
        }
    };

    final static RowMapper<Integer> intRowMapper = new RowMapper<Integer>() {
        public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getInt(1);
        }
    };

    @SuppressWarnings("rawtypes")
    EntityOperation<?> getEntityOperation(Class<?> entityClass) {
    	Class<?> actualEntityClass = Utils.getEntityClass(entityClass);
    	if (actualEntityClass==null)
    		throw new DbException("Unknown entity: " + entityClass.getName());
    	String entityClassName = actualEntityClass.getName();
        EntityOperation<?> op = entityMap.get(entityClassName);
        if (op==null) {
            log.info("Found entity class: " + actualEntityClass.getName());
            op = new EntityOperation(actualEntityClass);
            entityMap.put(actualEntityClass.getName(), op);
        }
        return op;
    }

    /**
     * Execute any update SQL statement.
     * 
     * @param sql SQL query.
     * @param params SQL parameters.
     * @return Number of affected rows.
     */
    public int executeUpdate(String sql, Object... params) {
        return jdbcTemplate.update(sql, params);
    }

    /**
     * Delete an entity by its id property. For example:
     * 
     * User user = new User(12300); // id=12300
     * db.deleteEntity(user);
     * 
     * @param entity Entity object instance.
     */
    public int deleteEntity(Object entity) {
        EntityOperation<?> op = getEntityOperation(entity.getClass());
        try {
            SQLOperation sqlo = op.deleteEntity(entity);
            return jdbcTemplate.update(sqlo.sql, sqlo.params);
        }
        catch (Exception e) {
            throw new DbException(e);
        }
    }

    /**
     * Update the entity with all updatable properties.
     * 
     * @param entity Entity object instance.
     */
    public int updateEntity(Object entity) {
        EntityOperation<?> op = getEntityOperation(entity.getClass());
        try {
            SQLOperation sqlo = op.updateEntity(entity);
            return jdbcTemplate.update(sqlo.sql, sqlo.params);
        }
        catch (Exception e) {
            throw new DbException(e);
        }
    }

    /**
     * Update the entity with specified properties.
     * 
     * @param entity Entity object instance.
     * @param properties Properties that are about to update.
     */
    public int updateProperties(Object entity, String... properties) {
        if (properties.length == 0)
            throw new DbException("Update properties required.");
        EntityOperation<?> op = getEntityOperation(entity.getClass());
        SQLOperation sqlo = null;
        try {
            sqlo = op.updateProperties(entity, properties);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return jdbcTemplate.update(sqlo.sql, sqlo.params);
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
    public long selectForLong(String sql, Object... args) {
        log.info("Query for long: " + sql);
        List<Long> list = jdbcTemplate.query(sql, args, longRowMapper);
        if (list.isEmpty())
            throw new DbException("empty results.");
        if (list.size() > 1)
            throw new DbException("non-unique results.");
        return list.get(0);
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
    public int selectForInt(String sql, Object... args) {
        log.info("Query for int: " + sql);
        List<Integer> list = jdbcTemplate.query(sql, args, intRowMapper);
        if (list.isEmpty())
            throw new DbException("empty results.");
        if (list.size() > 1)
            throw new DbException("non-unique results.");
        return list.get(0);
    }

	public <T> T selectForObject(String sql, Object[] args, RowMapper<T> rowMapper) {
        log.info("Query for Primitive: " + sql);
        List<T> list = (List<T>) jdbcTemplate.query(sql, args, rowMapper);
        if (list.isEmpty())
            throw new DbException("empty results.");
        if (list.size() > 1)
            throw new DbException("non-unique results.");
        return list.get(0);
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
    public <T> T selectForObject(Class<T> clazz, String sql, Object... args) {
        log.info("Query for object: " + sql);
        List<T> list = selectForList(clazz, sql, args);
        if (list.isEmpty())
            return null;
        if (list.size() > 1)
            throw new DbException("non-unique results.");
        return list.get(0);
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
    public <T> List<T> selectForList(Class<T> clazz, String sql, Object... params) {
        log.info("Query for list: " + sql);
        EntityOperation<?> op = getEntityOperation(clazz);
        return (List<T>) jdbcTemplate.query(sql, params, op.rowMapper);
    }

    /**
     * Get entity by its id.
     * 
     * @param <T> Entity class type.
     * @param clazz Entity class type.
     * @param idValue Id value.
     * @return Entity instance, or null if no such entity.
     */
    public <T> T selectById(Class<T> clazz, Object idValue) {
        EntityOperation<?> op = getEntityOperation(clazz);
        SQLOperation sqlo = op.getById(idValue);
        List<T> list = (List<T>) jdbcTemplate.query(sqlo.sql, sqlo.params, op.rowMapper);
        if (list.isEmpty())
            return null;
        if (list.size()>1)
            throw new DbException("non-unique results.");
        return list.get(0);
    }

    /**
     * Create an entity in database, writing all insertable properties.
     * 
     * @param entity Entity object instance.
     */
    public int create(Object entity) {
        EntityOperation<?> op = getEntityOperation(entity.getClass());
        SQLOperation sqlo = null;
        try {
            sqlo = op.insertEntity(entity);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return jdbcTemplate.update(sqlo.sql, sqlo.params);
    }

    /**
     * Delete an entity by its id value.
     * 
     * @param clazz Entity class type.
     * @param idValue Id value.
     */
    public int deleteById(Class<?> clazz, Object idValue) {
        EntityOperation<?> op = getEntityOperation(clazz);
        SQLOperation sqlo = op.deleteById(idValue);
        return jdbcTemplate.update(sqlo.sql, sqlo.params);
    }

    /**
     * Query for limited list. For example:
     * <code>
     * // first 5 users:
     * List&lt;User&gt; users = db.queryForList("select * from User where age>?", 0, 5, 20);
     * </code>
     * 
     * @param <T> Return type of list element.
     * @param sql SQL query.
     * @param first First result index.
     * @param max Max results.
     * @param params SQL parameters.
     * @return List of query result.
     */
    public <T> List<T> selectForLimitedList(Class<T> clazz, String sql, int first, int max, Object... args) {
        log.info("Query for limited list (first=" + first + ", max=" + max + "): " + sql);
        return selectForList(clazz, buildLimitedSelect(sql), buildLimitedArgs(args, first, max));
    }

    String buildLimitedSelect(String select) {
        StringBuilder sb = new StringBuilder(select.length() + 20);
        boolean forUpdate = select.toLowerCase().endsWith(" for update");
        if (forUpdate) {
            sb.append(select.substring(0, select.length() - 11));
        }
        else {
            sb.append(select);
        }
        sb.append(" limit ?,?");
        if (forUpdate) {
            sb.append(" for update");
        }
        return sb.toString();
    }

    Object[] buildLimitedArgs(Object[] args, int first, int max) {
        Object[] newArgs = new Object[args.length + 2];
        for (int i = 0; i < args.length; i++) {
            newArgs[i] = args[i];
        }
        newArgs[newArgs.length - 2] = first;
        newArgs[newArgs.length - 1] = max;
        return newArgs;
    }
}
