package org.expressme.binding.mapper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Mapping a row to a Java bean.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 * 
 * @param <T> Generic type.
 */
public class BeanRowMapper extends RowMapper {

	public static class PropertyMapper {
		public final String name;
		public final Method method;
		public final boolean nullable;
		public final FieldMapper<?> mapper;

		public PropertyMapper(String name, Method method, boolean nullable, FieldMapper<?> mapper) {
			this.name = name;
			this.method = method;
			this.nullable = nullable;
			this.mapper = mapper;
		}
	}

	private final Class<?> clazz;
	private Map<String, PropertyMapper> map = new HashMap<String, PropertyMapper>();

	public BeanRowMapper(Class<?> clazz) {
		this.clazz = clazz;
		Method[] ms = clazz.getMethods();
		// find all setXxx() method:
		for (Method m : ms) {
			PropertyMapper mapper = getPropertyMapper(m);
			if (mapper != null) {
				registerPropertyMapper(mapper);
			}
		}
		init();
	}

	/**
	 * Subclass may override this method to register new PropertyMappers.
	 */
	protected void init() {
	}

	protected final void registerPropertyMapper(PropertyMapper mapper) {
		map.put(mapper.name, mapper);
	}

	private PropertyMapper getPropertyMapper(Method m) {
		String name = m.getName();
		if (!name.startsWith("set") || name.length() < 4)
			return null;
		Class<?>[] params = m.getParameterTypes();
		if (params.length != 1)
			return null;
		Class<?> param = params[0];
		FieldMapper<?> mapper = MapperUtils.getFieldMapper(param);
		if (mapper == null)
			return null;
		return new PropertyMapper(name.substring(3).toLowerCase(), m, !param.isPrimitive(), mapper);
	}

	@SuppressWarnings("unchecked")
	public <T> T mapRow(Map<String, String> rs, String[] names) throws MappingException {
		try {
			T bean = (T) clazz.newInstance();
			for (int i = 0; i < names.length; i++) {
				PropertyMapper pm = map.get(names[i].toLowerCase());
				if (pm != null) {
					try {
						Object o = pm.mapper.mapField(rs, names[i], pm.nullable);
						pm.method.invoke(bean, o);
					} catch (Exception e) {
					}
				}
			}
			return bean;
		} catch (Exception e) {
			throw new MappingException(e);
		}
	}
}
