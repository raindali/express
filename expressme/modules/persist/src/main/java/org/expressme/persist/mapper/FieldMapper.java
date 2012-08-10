package org.expressme.persist.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Map a field to a Java object.
 * 
 * @param <T> Java object type.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public interface FieldMapper<T> {

    /**
     * Get supported SQL types. Constants are defined in {@link java.sql.Types}.
     * 
     * @return Supported SQL types.
     */
    int[] getTypes();

    /**
     * Map a field value to a Java object. If <code>nullable</code> parameter is 
     * set to "false", and field data retrieved from database is null, then 
     * default value will be returned. For example, a "null" int value will be 0, 
     * a "null" boolean value will be <code>false</code>.
     * 
     * @param rs ResultSet object.
     * @param type SQL type.
     * @param columnIndex The column index.
     * @param nullable If allow "null" value to return.
     * @return Java object.
     * @throws SQLException If any SQLException occurs.
     */
    T mapField(ResultSet rs, int type, int columnIndex, boolean nullable) throws SQLException;

}
