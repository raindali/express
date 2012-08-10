package org.expressme.persist.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mapping the first field of row to Integer.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class IntegerRowMapper extends RowMapper<Integer> {

    @Override
    public Integer mapRow(ResultSet rs, String[] names, int[] types) throws SQLException {
        return rs.getInt(1);
    }

}
