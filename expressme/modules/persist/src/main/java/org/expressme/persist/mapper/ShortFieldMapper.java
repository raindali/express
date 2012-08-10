package org.expressme.persist.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Maping SQL types TINYINT and SMALLINT to Short.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class ShortFieldMapper implements FieldMapper<Short> {

    public static final ShortFieldMapper INSTANCE = new ShortFieldMapper();

    public int[] getTypes() {
        return new int[] { Types.TINYINT, Types.SMALLINT };
    }

    public Short mapField(ResultSet rs, int type, int columnIndex, boolean nullable) throws SQLException {
        short s = rs.getShort(columnIndex);
        if (nullable && rs.wasNull())
            return null;
        return Short.valueOf(s);
    }

}
