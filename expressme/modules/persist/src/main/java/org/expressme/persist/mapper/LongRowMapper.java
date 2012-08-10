package org.expressme.persist.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mapping the first field of row to Long.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class LongRowMapper extends RowMapper<Long> {

    @Override
    public Long mapRow(ResultSet rs, String[] names, int[] types) throws SQLException {
        return rs.getLong(1);
    }

}
