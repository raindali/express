package org.expressme.persist.mapper;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Mapping a row to a Dynamic Java bean.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 * 
 * @param <T> Generic type.
 */
public class DynamicBeanRowMapper extends RowMapper<Object> {

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

    public DynamicBeanRowMapper(Class<?> clazz) {
        this.clazz = clazz;
        Method[] ms = clazz.getMethods();
        // find all setXxx() method:
        for (Method m : ms) {
            PropertyMapper mapper = getPropertyMapper(m);
            if (mapper!=null) {
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
        if (! name.startsWith("set") || name.length()<4)
            return null;
        Class<?>[] params = m.getParameterTypes();
        if (params.length!=1)
            return null;
        Class<?> param = params[0];
        FieldMapper<?> mapper = MapperUtils.getFieldMapper(param);
        if (mapper==null)
            return null;
        return new PropertyMapper(name.substring(3).toLowerCase(), m, !param.isPrimitive(), mapper);
    }

    public Object mapRow(ResultSet rs, String[] names, int[] types) throws SQLException {
        try {
        	Object bean = clazz.newInstance();
            for (int i=0; i<names.length; i++) {
                PropertyMapper pm = map.get(names[i].toLowerCase().replaceAll("_", ""));
                if (pm!=null) {
                    Object o = pm.mapper.mapField(rs, types[i], i+1, pm.nullable);
                    pm.method.invoke(bean, o);
                }
            }
            return bean;
        }
        catch(Exception e) {
            throw new MappingException(e);
        }
    }
}
