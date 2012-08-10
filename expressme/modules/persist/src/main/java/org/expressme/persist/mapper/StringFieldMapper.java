package org.expressme.persist.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Mapping SQL types CHAR, VARCHAR and LONGVARCHAR to String.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class StringFieldMapper implements FieldMapper<String> {

    public static final StringFieldMapper INSTANCE = new StringFieldMapper();

    public int[] getTypes() {
        return new int[] { Types.CHAR, Types.VARCHAR, Types.LONGVARCHAR };
    }

    public String mapField(ResultSet rs, int type, int columnIndex, boolean nullable) throws SQLException {
        String s = rs.getString(columnIndex);
        if (! nullable && s==null)
            return "";
        return s;
    }

}
