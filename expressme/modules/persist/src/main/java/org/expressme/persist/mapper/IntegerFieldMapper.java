package org.expressme.persist.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Mapping SQL type INTEGER to Integer.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class IntegerFieldMapper implements FieldMapper<Integer> {

    public static final IntegerFieldMapper INSTANCE = new IntegerFieldMapper();

    public int[] getTypes() {
        return new int[] { Types.INTEGER };
    }

    public Integer mapField(ResultSet rs, int type, int columnIndex, boolean nullable) throws SQLException {
        int i = rs.getInt(columnIndex);
        if (nullable && rs.wasNull())
            return null;
        return Integer.valueOf(i);
    }
}
