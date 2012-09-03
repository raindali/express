package org.expressme.webwind.bind;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.expressme.webwind.converter.ConverterFactory;

/**
 * Used for holding parameter type.
 * 
 * @author Xuefeng
 */
public class ParameterType {

    public static final ConverterFactory CONVERTER_FACTORY = new ConverterFactory();

    private Method method;
    private Class<?> parameterClass;

    public ParameterType(Method method, Class<?> parameterClass) {
        this.method = method;
        this.parameterClass = parameterClass;
    }

    public Object convert(String value) {
    	if (CONVERTER_FACTORY.canConvert(parameterClass))
    		CONVERTER_FACTORY.convert(parameterClass, value);
        throw new IllegalArgumentException("Cannot convert to type: " + parameterClass.getName());
    }

	public Object convert(String[] values) {
		if (parameterClass.isArray() && CONVERTER_FACTORY.canConvert(parameterClass.getClass().getComponentType())) {
			Object[] array = new Object[values.length];
			for (int i = 0; i < values.length; i++) {
				array[i] = convert(values[i]);
			}
			return array;
		}
		throw new IllegalArgumentException("Cannot convert to type: " + parameterClass.getName());
	}

    public Method getMethod() {
        return method;
    }

    public Class<?> getParameterClass() {
        return parameterClass;
    }

    public static boolean isSupportSingle(Class<?> clazz) {
        return supportedClasses.contains(clazz);
    }

    public static boolean isSupportArray(Class<?> clazz) {
        return supportedArrayClasses.contains(clazz);
    }

    private static Set<Class<?>> supportedClasses = new HashSet<Class<?>>();

    private static Set<Class<?>> supportedArrayClasses = new HashSet<Class<?>>();

    static {
        supportedClasses.add(boolean.class);
        supportedArrayClasses.add(boolean[].class);

        supportedClasses.add(char.class);
        supportedArrayClasses.add(char[].class);

        supportedClasses.add(byte.class);
        supportedArrayClasses.add(byte[].class);

        supportedClasses.add(short.class);
        supportedArrayClasses.add(short[].class);

        supportedClasses.add(int.class);
        supportedArrayClasses.add(int[].class);

        supportedClasses.add(long.class);
        supportedArrayClasses.add(long[].class);

        supportedClasses.add(float.class);
        supportedArrayClasses.add(float[].class);

        supportedClasses.add(double.class);
        supportedArrayClasses.add(double[].class);

        supportedClasses.add(Boolean.class);
        supportedArrayClasses.add(Boolean[].class);

        supportedClasses.add(Character.class);
        supportedArrayClasses.add(Character[].class);

        supportedClasses.add(Byte.class);
        supportedArrayClasses.add(Byte[].class);

        supportedClasses.add(Short.class);
        supportedArrayClasses.add(Short[].class);

        supportedClasses.add(Integer.class);
        supportedArrayClasses.add(Integer[].class);

        supportedClasses.add(Long.class);
        supportedArrayClasses.add(Long[].class);

        supportedClasses.add(Float.class);
        supportedArrayClasses.add(Float[].class);

        supportedClasses.add(Double.class);
        supportedArrayClasses.add(Double[].class);

        supportedClasses.add(String.class);
        supportedArrayClasses.add(String[].class);
        
        supportedClasses.add(Date.class);
        supportedArrayClasses.add(Date[].class);
    }

}
