package org.expressme.persist.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mapping the first field of row to Float.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class FloatRowMapper extends RowMapper<Float> {

    @Override
    public Float mapRow(ResultSet rs, String[] names, int[] types) throws SQLException {
        return rs.getFloat(1);
    }

}
