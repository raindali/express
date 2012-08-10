package org.expressme.simplejdbc.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.expressme.simplejdbc.DbException;
import org.expressme.simplejdbc.annotations.Entity;
import org.expressme.simplejdbc.annotations.Id;

/**
 * Utils for db operation.
 * 
 * @author Michael Liao
 */
class Utils {

    static final Set<Class<?>> SUPPORTED_SQL_OBJECTS = new HashSet<Class<?>>();

    static {
        Class<?>[] classes = {
                boolean.class, Boolean.class,
                short.class, Short.class,
                int.class, Integer.class,
                long.class, Long.class,
                float.class, Float.class,
                double.class, Double.class,
                String.class,
                Date.class,
                Timestamp.class
        };
        SUPPORTED_SQL_OBJECTS.addAll(Arrays.asList(classes));
    }

    static boolean isSupportedSQLObject(Class<?> clazz) {
        return clazz.isEnum() || SUPPORTED_SQL_OBJECTS.contains(clazz);
    }

    
	static String findIdProperty(Map<String, Method> getters) {
		String idProperty = null;
		for (String property : getters.keySet()) {
			Method getter = getters.get(property);
			Id id = Utils.getAnnotation(getter, Id.class);
			if (id != null) {
				if (idProperty != null)
					throw new DbException("Duplicate @Id detected.");
				idProperty = property;
			}
		}
		if (idProperty == null)
			throw new DbException("Missing @Id.");
		return idProperty;
	}
    
    static Map<String, Method> findPublicGetters(Class<?> clazz) {
        Map<String, Method> map = new HashMap<String, Method>();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (Modifier.isStatic(method.getModifiers()))
                continue;
            if (method.getParameterTypes().length != 0)
                continue;
            if (method.getName().equals("getClass"))
                continue;
            Class<?> returnType = method.getReturnType();
            if (void.class.equals(returnType))
                continue;
            if ((returnType.equals(boolean.class)
                    || returnType.equals(Boolean.class))
                    && method.getName().startsWith("is")
                    && method.getName().length() > 2) {
                map.put(getGetterName(method), method);
                continue;
            }
            if ( ! method.getName().startsWith("get"))
                continue;
            if (method.getName().length() < 4)
                continue;
            map.put(getGetterName(method), method);
        }
        return map;
    }

    static Map<String, Method> findPublicSetters(Class<?> clazz) {
        Map<String, Method> map = new HashMap<String, Method>();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (Modifier.isStatic(method.getModifiers()))
                continue;
            if ( ! void.class.equals(method.getReturnType()))
                continue;
            if (method.getParameterTypes().length != 1)
                continue;
            if ( ! method.getName().startsWith("set"))
                continue;
            if (method.getName().length() < 4)
                continue;
            map.put(getSetterName(method), method);
        }
        return map;
    }

    static String getGetterName(Method getter) {
        String name = getter.getName();
        if (name.startsWith("is"))
            name = name.substring(2);
        else
            name = name.substring(3);
        return Character.toLowerCase(name.charAt(0)) + name.substring(1);
    }

    private static String getSetterName(Method setter) {
        String name = setter.getName().substring(3);
        return Character.toLowerCase(name.charAt(0)) + name.substring(1);
    }

	static String addUnderscores(String name) {
		StringBuilder buf = new StringBuilder(name);
		for (int i = 1; i < buf.length() - 1; i++) {
			if (Character.isLowerCase(buf.charAt(i - 1)) && Character.isUpperCase(buf.charAt(i))
					&& Character.isLowerCase(buf.charAt(i + 1))) {
				buf.insert(i++, '_');
			}
		}
		return buf.toString().toLowerCase();
	}

	static String delUnderscores(String name) {
		StringBuilder buf = new StringBuilder(name.toLowerCase());
		for (int i = buf.length() - 1; i >= 1; i--) {
			if (buf.charAt(i) == '_') {
				buf.deleteCharAt(i);
				buf.setCharAt(i, Character.toUpperCase(buf.charAt(i)));
			}
		}
		return buf.toString();
	}
	
	static Class<?> getEntityClass(Class<?> clazz) {
		while (clazz != Object.class) {
			if (clazz.isAnnotationPresent(Entity.class)) {
				return clazz;
			}
			clazz = clazz.getSuperclass();
		}
		return null;
	}
	
	static void copy(Object dest, Object orig) {
		Map<String, Method> setters = findPublicSetters(dest.getClass());
		Map<String, Method> getters = findPublicGetters(orig.getClass());
		for (String name : getters.keySet()) {
			Method setter = setters.get(name);
			Method getter = getters.get(name);
			if (setter != null) {
				try {
					setter.invoke(dest, getter.invoke(orig, new Object[] {}));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	}

	static <T extends Annotation> T getAnnotation(Method getter, Class<T> annotationClass) {
		T annotation = getter.getAnnotation(annotationClass);
		if (annotation == null) {
			try {
				Field field = getter.getDeclaringClass().getDeclaredField(getGetterName(getter));
				if (field != null) {
					annotation = field.getAnnotation(annotationClass);
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
		}
		return annotation;
	}

	public static void main(String[] args) {
		System.out.println(delUnderscores("User_name"));
		System.out.println(delUnderscores("U_N"));
		System.out.println(addUnderscores("userName"));
	}
}
