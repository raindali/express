package org.expressme.persist.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mapping the first field of row to Short.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class ShortRowMapper extends RowMapper<Short> {

    @Override
    public Short mapRow(ResultSet rs, String[] names, int[] types) throws SQLException {
        return rs.getShort(1);
    }

}
