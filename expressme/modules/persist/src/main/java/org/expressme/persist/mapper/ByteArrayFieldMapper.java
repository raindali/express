package org.expressme.persist.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Mapping BINARY, VARBINARY and LONGVARBINARY to byte[].
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class ByteArrayFieldMapper implements FieldMapper<byte[]> {

    public static final ByteArrayFieldMapper INSTANCE = new ByteArrayFieldMapper();

    private static final byte[] EMPTY_BYTES = new byte[0];

    public int[] getTypes() {
        return new int[] { Types.BINARY, Types.VARBINARY, Types.LONGVARBINARY };
    }

    public byte[] mapField(ResultSet rs, int type, int columnIndex, boolean nullable) throws SQLException {
        byte[] bs = rs.getBytes(columnIndex);
        if (!nullable && bs==null)
            return EMPTY_BYTES;
        return bs;
    }

}
