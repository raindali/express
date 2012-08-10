package org.expressme.persist.mapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mapping the first field of row to java.math.BigDecimal.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class BigDecimalRowMapper extends RowMapper<BigDecimal> {

    @Override
    public BigDecimal mapRow(ResultSet rs, String[] names, int[] types) throws SQLException {
        return rs.getBigDecimal(1);
    }

}
