package org.expressme.persist.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Mapping SQL type BIGINT to Java Long.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class LongFieldMapper implements FieldMapper<Long> {

    public static final LongFieldMapper INSTANCE = new LongFieldMapper();

    public int[] getTypes() {
        return new int[] { Types.BIGINT };
    }

    public Long mapField(ResultSet rs, int type, int columnIndex, boolean nullable) throws SQLException {
        long lng = rs.getLong(columnIndex);
        if (nullable && rs.wasNull())
            return null;
        return Long.valueOf(lng);
    }

}
