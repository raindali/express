package org.expressme.persist.mapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Mapping SQL type DECIMAL and NUMERIC to BigDecimal.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class BigDecimalFieldMapper implements FieldMapper<BigDecimal> {

    public static final BigDecimalFieldMapper INSTANCE = new BigDecimalFieldMapper();

    public int[] getTypes() {
        return new int[] { Types.DECIMAL, Types.NUMERIC };
    }

    public BigDecimal mapField(ResultSet rs, int type, int columnIndex, boolean nullable) throws SQLException {
        BigDecimal bd = rs.getBigDecimal(columnIndex);
        if (!nullable && bd==null)
            return BigDecimal.ZERO;
        return bd;
    }

}
