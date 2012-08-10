package org.expressme.binding.mapper;

import java.util.Map;

/**
 * Mapping SQL type REAL to Float.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class FloatFieldMapper implements FieldMapper<Float> {

    public static final FloatFieldMapper INSTANCE = new FloatFieldMapper();

    public Float mapField(Map<String, String> rs, String key, boolean nullable) throws MappingException {
        String f = rs.get(key);
        if (f==null || "".equals(f))
            return nullable?null:0.0f;
        return new Float(f);
    }

}
