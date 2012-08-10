package org.expressme.binding.mapper;

import java.util.Map;

/**
 * Mapping String to String[].
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class StringArrayFieldMapper implements FieldMapper<String[]> {

    public static final StringArrayFieldMapper INSTANCE = new StringArrayFieldMapper();

    private static final String[] EMPTY_STRING = new String[0];

    public String[] mapField(Map<String, String> rs, String key, boolean nullable) throws MappingException {
    	String objs = rs.get(key);
    	if (objs == null || "".equals(objs)) {
    		return nullable?null:EMPTY_STRING;
    	}
		return objs.split(",");
    }

}
