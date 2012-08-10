package org.expressme.binding.mapper;

import java.util.Map;

/**
 * Mapping SQL type FLOAT and DOUBLE to Double. NOTE that the SQL type 'FLOAT' 
 * is equivalent to Java type 'double' rather than 'float'.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class DoubleFieldMapper implements FieldMapper<Double> {

    public static final DoubleFieldMapper INSTANCE = new DoubleFieldMapper();

    public Double mapField(Map<String, String> rs, String key, boolean nullable) throws MappingException {
        String d = rs.get(key);
        if (nullable && d == null)
            return null;
        return new Double(d);
    }

}
