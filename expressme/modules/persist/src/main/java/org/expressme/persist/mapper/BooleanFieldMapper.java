package org.expressme.persist.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Mapping SQL types BIT and BOOLEAN to Boolean.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class BooleanFieldMapper implements FieldMapper<Boolean> {

    public static final BooleanFieldMapper INSTANCE = new BooleanFieldMapper();

    public int[] getTypes() {
        return new int[] { Types.BIT, Types.BOOLEAN };
    }

    public Boolean mapField(ResultSet rs, int type, int columnIndex, boolean nullable) throws SQLException {
        boolean b = rs.getBoolean(columnIndex);
        if (nullable && rs.wasNull())
            return null;
        return Boolean.valueOf(b);
    }

}
