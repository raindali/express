package org.expressme.binding.mapper;

import java.util.Map;

/**
 * Mapping SQL type INTEGER to Integer.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class IntegerFieldMapper implements FieldMapper<Integer> {

    public static final IntegerFieldMapper INSTANCE = new IntegerFieldMapper();

    public Integer mapField(Map<String, String> rs, String key, boolean nullable) throws MappingException {
        String i = rs.get(key);
        if (i == null || "".equals(i))
            return nullable?null:Integer.valueOf(0);
        return Integer.valueOf(i);
    }
}
