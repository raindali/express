package org.expressme.persist.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

/**
 * Mapping SQL types DATE, TIME and TIMESTAMP to {@link java.util.Date}.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class DateFieldMapper implements FieldMapper<Date> {

    public static final DateFieldMapper INSTANCE = new DateFieldMapper();

    public int[] getTypes() {
        return new int[] { Types.DATE, Types.TIME, Types.TIMESTAMP };
    }

    /**
     * The parameter <code>nullable</code> will be ignored, because no default 
     * value for java.util.Date object. If the value is null, "null" will be 
     * returned.
     */
    public Date mapField(ResultSet rs, int type, int columnIndex, boolean nullable) throws SQLException {
        switch (type) {
        case Types.DATE:
            return rs.getDate(columnIndex);
        case Types.TIME:
            return rs.getTime(columnIndex);
        case Types.TIMESTAMP:
            return rs.getTimestamp(columnIndex);
        }
        throw new MappingException("Cannot map SQL type " + type + " to java.util.Date.");
    }

}
