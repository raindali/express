package org.expressme.binding.mapper;

import java.util.Map;

/**
 * Mapping Integer to String[].
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class IntegerArrayFieldMapper implements FieldMapper<Integer[]> {

	public static final IntegerArrayFieldMapper INSTANCE = new IntegerArrayFieldMapper();

	private static final Integer[] EMPTY_INTEGER = new Integer[0];

	public Integer[] mapField(Map<String, String> rs, String key, boolean nullable) throws MappingException {
		String str = rs.get(key);
		if (str == null || "".equals(str)) {
			return nullable ? null : EMPTY_INTEGER;
		}
		String[] values = str.split(",");
		Integer[] objs = new Integer[values.length];
		for (int i = 0; i < values.length; i++) {
			try {
				objs[i] = Integer.parseInt(values[i]);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return objs;
	}

}
