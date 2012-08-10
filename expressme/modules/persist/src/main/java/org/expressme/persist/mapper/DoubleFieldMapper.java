package org.expressme.persist.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Mapping SQL type FLOAT and DOUBLE to Double. NOTE that the SQL type 'FLOAT' 
 * is equivalent to Java type 'double' rather than 'float'.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class DoubleFieldMapper implements FieldMapper<Double> {

    public static final DoubleFieldMapper INSTANCE = new DoubleFieldMapper();

    public int[] getTypes() {
        return new int[] { Types.FLOAT, Types.DOUBLE };
    }

    public Double mapField(ResultSet rs, int type, int columnIndex, boolean nullable) throws SQLException {
        double d = rs.getDouble(columnIndex);
        if (nullable && rs.wasNull())
            return null;
        return new Double(d);
    }

}
