package org.expressme.persist.mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MapperUtils {

    public static FieldMapper<?> getFieldMapper(Class<?> clazz) {
        return javaTypeMap.get(clazz);
    }

    public static FieldMapper<?> getFieldMapper(int sqlType) {
        return sqlTypeMap.get(sqlType);
    }

    public static Class<? extends RowMapper<?>> getRowMapper(Class<?> clazz) {
		return javaRowMap.get(clazz);
    }
    
    private static Map<Integer, FieldMapper<?>> sqlTypeMap = new HashMap<Integer, FieldMapper<?>>();

    private static Map<Class<?>, FieldMapper<?>> javaTypeMap = new HashMap<Class<?>, FieldMapper<?>>();
    
    private static Map<Class<?>, Class<? extends RowMapper<?>>> javaRowMap = new HashMap<Class<?>, Class<? extends RowMapper<?>>>();

    static {
        registerFieldMapper(StringFieldMapper.INSTANCE, String.class);
        registerFieldMapper(BigDecimalFieldMapper.INSTANCE, BigDecimal.class);
        registerFieldMapper(ByteArrayFieldMapper.INSTANCE, byte[].class);
        registerFieldMapper(DateFieldMapper.INSTANCE, Date.class);
        registerFieldMapper(BooleanFieldMapper.INSTANCE, boolean.class, Boolean.class);
        registerFieldMapper(DoubleFieldMapper.INSTANCE, double.class, Double.class);
        registerFieldMapper(FloatFieldMapper.INSTANCE, float.class, Float.class);
        registerFieldMapper(IntegerFieldMapper.INSTANCE, int.class, Integer.class);
        registerFieldMapper(LongFieldMapper.INSTANCE, long.class, Long.class);
        registerFieldMapper(ShortFieldMapper.INSTANCE, short.class, Short.class);
        
        registerRowMapper(StringRowMapper.class, String.class);
        registerRowMapper(BigDecimalRowMapper.class, BigDecimal.class);
        registerRowMapper(BooleanRowMapper.class, boolean.class, Boolean.class);
        registerRowMapper(DoubleRowMapper.class, double.class, Double.class);
        registerRowMapper(FloatRowMapper.class, float.class, Float.class);
        registerRowMapper(IntegerRowMapper.class, int.class, Integer.class);
        registerRowMapper(LongRowMapper.class, long.class, Long.class);
        registerRowMapper(ShortRowMapper.class, short.class, Short.class);
    }

    private static void registerFieldMapper(FieldMapper<?> fm, Class<?>... classes) {
        // register SQL types:
        int[] types = fm.getTypes();
        for (int type : types) {
            if (sqlTypeMap.containsKey(type)) {
                throw new MappingException("Duplicate FieldMapper for the same SQL type: " + type);
            }
            sqlTypeMap.put(type, fm);
        }
        // register Java types:
        for (Class<?> clazz : classes) {
            javaTypeMap.put(clazz, fm);
        }
    }

    private static void registerRowMapper(Class<? extends RowMapper<?>> rm, Class<?>... classes) {
        // register Java types:
        for (Class<?> clazz : classes) {
            javaRowMap.put(clazz, rm);
        }
    }
}
