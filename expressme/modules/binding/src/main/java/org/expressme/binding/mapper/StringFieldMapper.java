package org.expressme.binding.mapper;

import java.util.Map;

/**
 * Mapping SQL types CHAR, VARCHAR and LONGVARCHAR to String.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class StringFieldMapper implements FieldMapper<String> {

    public static final StringFieldMapper INSTANCE = new StringFieldMapper();

    public String mapField(Map<String, String> rs, String key, boolean nullable) throws MappingException {
        String s = rs.get(key);
        if (! nullable && s==null)
            return "";
        return s;
    }

}
