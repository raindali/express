package org.expressme.binding.mapper;

import java.util.Map;

/**
 * Mapping SQL type BIGINT to Java Long.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class LongFieldMapper implements FieldMapper<Long> {

    public static final LongFieldMapper INSTANCE = new LongFieldMapper();

    public Long mapField(Map<String, String> rs, String key, boolean nullable) throws MappingException {
        String lng = rs.get(key);
        if (lng == null || "".equals(lng)) {
        	return nullable?null:Long.valueOf("0");
        }
        return Long.valueOf(lng);
    }

}
