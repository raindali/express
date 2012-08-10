package org.expressme.persist.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mapping the first field of row to Double.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class DoubleRowMapper extends RowMapper<Double> {

    @Override
    public Double mapRow(ResultSet rs, String[] names, int[] types) throws SQLException {
        return rs.getDouble(1);
    }

}
