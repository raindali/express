package org.expressme.persist.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mapping a row to Object[].
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class ObjectArrayRowMapper extends RowMapper<Object[]> {

    public static final ObjectArrayRowMapper INSTANCE = new ObjectArrayRowMapper();

    public Object[] mapRow(ResultSet rs, String[] names, int[] types) throws SQLException {
        Object[] objs = new Object[types.length];
        for (int i=0; i<types.length; i++) {
            int type = types[i];
            FieldMapper<?> mapper = MapperUtils.getFieldMapper(type);
            if (mapper==null)
                throw new MappingException("No FieldMapper found for maping SQL type " + type + " to Java object.");
            objs[i] = mapper.mapField(rs, type, i + 1, true);
        }
        return objs;
    }

}
