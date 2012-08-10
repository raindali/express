//package org.expressme.bind2;
//
//import java.lang.reflect.Method;
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashSet;
//import java.util.Set;
//
///**
// * Used for holding parameter type.
// * 
// * @author Xuefeng
// */
//public class ParameterType {
//
//    public static final DateFormat ISO_DATETIME_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    public static final DateFormat ISO_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
//
//    private Method method;
//    private Class<?> parameterClass;
//
//    public ParameterType(Method method, Class<?> parameterClass) {
//        this.method = method;
//        this.parameterClass = parameterClass;
//    }
//
//    public Object convert(String value) {
//        if(parameterClass.equals(String.class))
//            return value;
//        if(parameterClass.equals(int.class) || parameterClass.equals(Integer.class))
//            return Integer.valueOf(value);
//        if(parameterClass.equals(boolean.class) || parameterClass.equals(Boolean.class))
//            return Boolean.valueOf(value);
//        if(parameterClass.equals(long.class) || parameterClass.equals(Long.class))
//            return Long.valueOf(value);
//        if(parameterClass.equals(float.class) || parameterClass.equals(Float.class))
//            return Float.valueOf(value);
//        if(parameterClass.equals(double.class) || parameterClass.equals(Double.class))
//            return Double.valueOf(value);
//        if(parameterClass.equals(short.class) || parameterClass.equals(Short.class))
//            return Short.valueOf(value);
//        if(parameterClass.equals(byte.class) || parameterClass.equals(Byte.class))
//            return Byte.valueOf(value);
//        if(value.length()>0 && parameterClass.equals(char.class) || parameterClass.equals(Character.class))
//            return value.charAt(0);
//        if(parameterClass.equals(Date.class)) {
//            if (value !=null && value.length() >= 10) {
//            	try {
//            		return ISO_DATETIME_TIME_FORMAT.parse(value);
//            	} catch (ParseException e) {
//            		try {
//    					return ISO_DATE_FORMAT.parse(value);
//    				} catch (ParseException e1) {
//    				}
//            	}
//            }
//        }
//        throw new IllegalArgumentException("Cannot convert to type: " + parameterClass.getName());
//    }
//
//    public Object convert(String[] values) {
//        if(parameterClass.equals(String[].class))
//            return values;
//        if(parameterClass.equals(int[].class)) {
//            int[] array = new int[values.length];
//            for(int i=0; i<array.length; i++)
//                array[i] = Integer.valueOf(values[i]);
//            return array;
//        }
//        if(parameterClass.equals(Integer[].class)) {
//            Integer[] array = new Integer[values.length];
//            for(int i=0; i<array.length; i++)
//                array[i] = Integer.valueOf(values[i]);
//            return array;
//        }
//        if(parameterClass.equals(boolean[].class)) {
//            boolean[] array = new boolean[values.length];
//            for(int i=0; i<array.length; i++)
//                array[i] = Boolean.valueOf(values[i]);
//            return array;
//        }
//        if(parameterClass.equals(Boolean[].class)) {
//            Boolean[] array = new Boolean[values.length];
//            for(int i=0; i<array.length; i++)
//                array[i] = Boolean.valueOf(values[i]);
//            return array;
//        }
//        if(parameterClass.equals(long[].class)) {
//            long[] array = new long[values.length];
//            for(int i=0; i<array.length; i++)
//                array[i] = Long.valueOf(values[i]);
//            return array;
//        }
//        if(parameterClass.equals(Long[].class)) {
//            Long[] array = new Long[values.length];
//            for(int i=0; i<array.length; i++)
//                array[i] = Long.valueOf(values[i]);
//            return array;
//        }
//        if(parameterClass.equals(float[].class)) {
//            float[] array = new float[values.length];
//            for(int i=0; i<array.length; i++)
//                array[i] = Float.valueOf(values[i]);
//            return array;
//        }
//        if(parameterClass.equals(Float[].class)) {
//            Float[] array = new Float[values.length];
//            for(int i=0; i<array.length; i++)
//                array[i] = Float.valueOf(values[i]);
//            return array;
//        }
//        if(parameterClass.equals(double[].class)) {
//            double[] array = new double[values.length];
//            for(int i=0; i<array.length; i++)
//                array[i] = Double.valueOf(values[i]);
//            return array;
//        }
//        if(parameterClass.equals(Double[].class)) {
//            Double[] array = new Double[values.length];
//            for(int i=0; i<array.length; i++)
//                array[i] = Double.valueOf(values[i]);
//            return array;
//        }
//        if(parameterClass.equals(short[].class)) {
//            short[] array = new short[values.length];
//            for(int i=0; i<array.length; i++)
//                array[i] = Short.valueOf(values[i]);
//            return array;
//        }
//        if(parameterClass.equals(Short[].class)) {
//            Short[] array = new Short[values.length];
//            for(int i=0; i<array.length; i++)
//                array[i] = Short.valueOf(values[i]);
//            return array;
//        }
//        if(parameterClass.equals(byte[].class)) {
//            byte[] array = new byte[values.length];
//            for(int i=0; i<array.length; i++)
//                array[i] = Byte.valueOf(values[i]);
//            return array;
//        }
//        if(parameterClass.equals(Byte[].class)) {
//            Byte[] array = new Byte[values.length];
//            for(int i=0; i<array.length; i++)
//                array[i] = Byte.valueOf(values[i]);
//            return array;
//        }
//        if(parameterClass.equals(char[].class)) {
//            char[] array = new char[values.length];
//            for(int i=0; i<array.length; i++)
//                array[i] = values[i].charAt(0);
//            return array;
//        }
//        if(parameterClass.equals(Character[].class)) {
//            Character[] array = new Character[values.length];
//            for(int i=0; i<array.length; i++)
//                array[i] = values[i].charAt(0);
//            return array;
//        }
//        if(parameterClass.equals(Date[].class)) {
//        	Date[] array = new Date[values.length];
//        	for (int i = 0; i < array.length; i++) {
//                if (values[i] !=null && values[i].length() >= 10) {
//                	try {
//                		array[i] = ISO_DATETIME_TIME_FORMAT.parse(values[i]);
//                	} catch (ParseException e) {
//                		try {
//                			array[i] =  ISO_DATE_FORMAT.parse(values[i]);
//        				} catch (ParseException e1) {
//        				}
//                	}
//                }
//			}
//        	return array;
//        }
//        throw new IllegalArgumentException("Cannot convert to type: " + parameterClass.getName());
//    }
//
//    public Method getMethod() {
//        return method;
//    }
//
//    public Class<?> getParameterClass() {
//        return parameterClass;
//    }
//
//    public static boolean isSupportSingle(Class<?> clazz) {
//        return supportedClasses.contains(clazz);
//    }
//
//    public static boolean isSupportArray(Class<?> clazz) {
//        return supportedArrayClasses.contains(clazz);
//    }
//
//    private static Set<Class<?>> supportedClasses = new HashSet<Class<?>>();
//
//    private static Set<Class<?>> supportedArrayClasses = new HashSet<Class<?>>();
//
//    static {
//        supportedClasses.add(boolean.class);
//        supportedArrayClasses.add(boolean[].class);
//
//        supportedClasses.add(char.class);
//        supportedArrayClasses.add(char[].class);
//
//        supportedClasses.add(byte.class);
//        supportedArrayClasses.add(byte[].class);
//
//        supportedClasses.add(short.class);
//        supportedArrayClasses.add(short[].class);
//
//        supportedClasses.add(int.class);
//        supportedArrayClasses.add(int[].class);
//
//        supportedClasses.add(long.class);
//        supportedArrayClasses.add(long[].class);
//
//        supportedClasses.add(float.class);
//        supportedArrayClasses.add(float[].class);
//
//        supportedClasses.add(double.class);
//        supportedArrayClasses.add(double[].class);
//
//        supportedClasses.add(Boolean.class);
//        supportedArrayClasses.add(Boolean[].class);
//
//        supportedClasses.add(Character.class);
//        supportedArrayClasses.add(Character[].class);
//
//        supportedClasses.add(Byte.class);
//        supportedArrayClasses.add(Byte[].class);
//
//        supportedClasses.add(Short.class);
//        supportedArrayClasses.add(Short[].class);
//
//        supportedClasses.add(Integer.class);
//        supportedArrayClasses.add(Integer[].class);
//
//        supportedClasses.add(Long.class);
//        supportedArrayClasses.add(Long[].class);
//
//        supportedClasses.add(Float.class);
//        supportedArrayClasses.add(Float[].class);
//
//        supportedClasses.add(Double.class);
//        supportedArrayClasses.add(Double[].class);
//
//        supportedClasses.add(String.class);
//        supportedArrayClasses.add(String[].class);
//        
//        supportedClasses.add(Date.class);
//        supportedArrayClasses.add(Date[].class);
//    }
//
//}
