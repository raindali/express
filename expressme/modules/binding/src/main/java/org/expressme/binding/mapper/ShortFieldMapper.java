package org.expressme.binding.mapper;

import java.util.Map;

/**
 * Maping SQL types TINYINT and SMALLINT to Short.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class ShortFieldMapper implements FieldMapper<Short> {

    public static final ShortFieldMapper INSTANCE = new ShortFieldMapper();

    public Short mapField(Map<String, String> rs, String key, boolean nullable) throws MappingException {
        String s = rs.get(key);
        if (s == null || "".equals(s))
            return nullable?null:Short.valueOf("0");
        return Short.valueOf(s);
    }

}
