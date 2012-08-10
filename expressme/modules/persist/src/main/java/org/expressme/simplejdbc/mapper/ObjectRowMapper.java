package org.expressme.simplejdbc.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Mapping a row to Object[].
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class ObjectRowMapper extends RowMapper<Object> {


    public Object mapRow(ResultSet rs, String[] names, int[] types) throws SQLException {
        return rs.getObject(1);
    }

}
