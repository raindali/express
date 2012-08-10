package org.expressme.binding.mapper;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Mapping SQL type DECIMAL and NUMERIC to BigDecimal.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class BigDecimalFieldMapper implements FieldMapper<BigDecimal> {

    public static final BigDecimalFieldMapper INSTANCE = new BigDecimalFieldMapper();

    public BigDecimal mapField(Map<String, String> rs, String key, boolean nullable) throws MappingException {
    	String bd = rs.get(key);
        if (bd == null || "".equals(bd))
            return nullable?null:BigDecimal.ZERO;
        return new BigDecimal(bd);
    }

}
