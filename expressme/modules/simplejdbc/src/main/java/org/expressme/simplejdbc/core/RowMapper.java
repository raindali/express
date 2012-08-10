package org.expressme.simplejdbc.core;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * To map a ResultSet's current row set to an Object. Define RowMapper<T> as an 
 * abstract class rather than interface makes possible to get generic type of T 
 * from subclass.
 * 
 * @param <T> Generic object type.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public abstract class RowMapper<T> {

    /**
     * Mapping a row to a Java object.
     * 
     * @param rs ResultSet object.
     * @param names Column names.
     * @param types Column types.
     * @return Java object.
     * @throws SQLException If any SQLException occurs.
     */
    public abstract T mapRow(ResultSet rs, String[] names, int[] types) throws SQLException;

}
