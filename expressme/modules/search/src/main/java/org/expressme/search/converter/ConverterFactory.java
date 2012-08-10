package org.expressme.search.converter;

import java.util.HashMap;
import java.util.Map;

/**
 * Holds all registered converters.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class ConverterFactory {

    final Map<Class<?>, Converter> map = new HashMap<Class<?>, Converter>();
    final Map<Class<?>, Class<?>> primitiveMap = new HashMap<Class<?>, Class<?>>();

    private static final ConverterFactory instance = new ConverterFactory();

    public static ConverterFactory getInstance() {
        return instance;
    }

    private ConverterFactory() {
        primitiveMap.put(boolean.class, Boolean.class);
        primitiveMap.put(byte.class, Byte.class);
        primitiveMap.put(char.class, Character.class);
        primitiveMap.put(short.class, Short.class);
        primitiveMap.put(int.class, Integer.class);
        primitiveMap.put(long.class, Long.class);
        primitiveMap.put(float.class, Float.class);
        primitiveMap.put(double.class, Double.class);

        register(boolean.class, new BooleanConverter());
        register(Boolean.class, new BooleanConverter());
        register(short.class, new ShortConverter());
        register(Short.class, new ShortConverter());
        register(int.class, new IntegerConverter());
        register(Integer.class, new IntegerConverter());
        register(long.class, new LongConverter());
        register(Long.class, new LongConverter());
        register(float.class, new FloatConverter());
        register(Float.class, new FloatConverter());
        register(double.class, new DoubleConverter());
        register(Double.class, new DoubleConverter());

        register(String.class, new StringConverter());
    }

    public Converter getConverter(Class<?> clazz) {
        return map.get(clazz);
    }

    public void register(Class<?> clazz, Converter converter) {
        map.put(clazz, converter);
        if (clazz.isPrimitive()) {
            clazz = getWrapperClass(clazz);
            if (clazz!=null)
                map.put(clazz, converter);
        }
    }

    Class<?> getWrapperClass(Class<?> clazz) {
        return primitiveMap.get(clazz);
    }

}
