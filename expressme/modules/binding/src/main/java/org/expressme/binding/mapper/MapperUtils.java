package org.expressme.binding.mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MapperUtils {

    public static FieldMapper<?> getFieldMapper(Class<?> clazz) {
        return javaTypeMap.get(clazz);
    }

    private static Map<Class<?>, FieldMapper<?>> javaTypeMap = new HashMap<Class<?>, FieldMapper<?>>();

    static {
        registerFieldMapper(StringFieldMapper.INSTANCE, String.class);
        registerFieldMapper(BigDecimalFieldMapper.INSTANCE, BigDecimal.class);
        registerFieldMapper(DateFieldMapper.INSTANCE, Date.class);
        registerFieldMapper(BooleanFieldMapper.INSTANCE, boolean.class, Boolean.class);
        registerFieldMapper(DoubleFieldMapper.INSTANCE, double.class, Double.class);
        registerFieldMapper(FloatFieldMapper.INSTANCE, float.class, Float.class);
        registerFieldMapper(IntegerFieldMapper.INSTANCE, int.class, Integer.class);
        registerFieldMapper(LongFieldMapper.INSTANCE, long.class, Long.class);
        registerFieldMapper(ShortFieldMapper.INSTANCE, short.class, Short.class);
        registerFieldMapper(IntegerArrayFieldMapper.INSTANCE, Integer[].class);
        registerFieldMapper(StringArrayFieldMapper.INSTANCE, String[].class);
    }

    private static void registerFieldMapper(FieldMapper<?> fm, Class<?>... classes) {
        // register Java types:
        for (Class<?> clazz : classes) {
            javaTypeMap.put(clazz, fm);
        }
    }

}
