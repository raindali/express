package org.expressme.binding.mapper;

import java.sql.SQLException;
import java.util.Map;

/**
 * To map a ResultSet's current row set to an Object. Define RowMapper<T> as an 
 * abstract class rather than interface makes possible to get generic type of T 
 * from subclass.
 * 
 * @param <T> Generic object type.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public abstract class RowMapper {

    /**
     * Mapping a row to a Java object.
     * 
     * @param rs ResultSet object.
     * @param names Column names.
     * @return Java object.
     * @throws SQLException If any SQLException occurs.
     */
    public abstract<T> T mapRow(Map<String, String> rs, String[] names) throws MappingException;

}
