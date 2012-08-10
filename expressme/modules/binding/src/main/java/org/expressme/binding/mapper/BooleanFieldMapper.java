package org.expressme.binding.mapper;

import java.util.Map;

/**
 * Mapping SQL types BIT and BOOLEAN to Boolean.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class BooleanFieldMapper implements FieldMapper<Boolean> {

    public static final BooleanFieldMapper INSTANCE = new BooleanFieldMapper();

    public Boolean mapField(Map<String, String> rs, String key, boolean nullable) throws MappingException {
		String b = rs.get(key);
		if (b == null || "".equals(b))
			return nullable ? null : Boolean.FALSE;
        return Boolean.valueOf(b);
    }

}
