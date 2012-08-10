package org.expressme.binding.mapper;

import java.sql.SQLException;
import java.util.Map;

/**
 * Map a field to a Java object.
 * 
 * @param <T> Java object type.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public interface FieldMapper<T> {

    /**
     * Map a field value to a Java object. If <code>nullable</code> parameter is 
     * set to "false", and field data retrieved from database is null, then 
     * default value will be returned. For example, a "null" int value will be 0, 
     * a "null" boolean value will be <code>false</code>.
     * 
     * @param rs ResultSet object.
     * @param nullable If allow "null" value to return.
     * @return Java object.
     * @throws SQLException If any SQLException occurs.
     */
    T mapField(Map<String, String> rs, String key, boolean nullable) throws MappingException;

}
