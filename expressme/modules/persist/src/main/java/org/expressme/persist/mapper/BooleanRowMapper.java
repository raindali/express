package org.expressme.persist.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mapping the first field of row to Boolean.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class BooleanRowMapper extends RowMapper<Boolean> {

    @Override
    public Boolean mapRow(ResultSet rs, String[] names, int[] types) throws SQLException {
        return rs.getBoolean(1);
    }

}
