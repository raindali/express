package org.expressme.persist.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Mapping SQL type REAL to Float.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class FloatFieldMapper implements FieldMapper<Float> {

    public static final FloatFieldMapper INSTANCE = new FloatFieldMapper();

    public int[] getTypes() {
        return new int[] { Types.REAL };
    }

    public Float mapField(ResultSet rs, int type, int columnIndex, boolean nullable) throws SQLException {
        float f = rs.getFloat(columnIndex);
        if (nullable && rs.wasNull())
            return null;
        return new Float(f);
    }

}
