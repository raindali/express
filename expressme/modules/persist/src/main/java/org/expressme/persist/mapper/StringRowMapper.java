package org.expressme.persist.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mapping the first field of row to String.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class StringRowMapper extends RowMapper<String> {

    @Override
    public String mapRow(ResultSet rs, String[] names, int[] types) throws SQLException {
        return rs.getString(1);
    }

}
