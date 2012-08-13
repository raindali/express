package com.dali.validator.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ClassUtils {
	/**
	 * 是否为公开方法
	 * 
	 * @param method
	 *            方法
	 * @return 是否为公开方法
	 */
	public static boolean isPublicMethod(Method method) {
		return method != null && (method.getModifiers() & Modifier.PUBLIC) == 1;
	}
	
	/**
	 * 获取方法, 或相似方法
	 *
	 * @param clazz 类
	 * @param name 方法名
	 * @param args 参数
	 * @return 方法
	 * @throws NoSuchMethodException 未找到相应方法时抛出
	 */
	public static Method getMethod(Class<?> clazz, String name, Object[] args) throws NoSuchMethodException {
		if (args == null)
			args = new Object[0];
		Class<?>[] types = new Class[args.length];
		for (int i = 0, n = args.length; i < n; i ++) {
			types[i] = (args[i] == null ? Object.class : args[i].getClass());
		}
		try {
			return clazz.getMethod(name, types);
		} catch (NoSuchMethodException e) {
			return getLikeMethod(clazz, name, types);
		}
	}

	// 获取相似方法
	private static Method getLikeMethod(Class<?> clazz, String name, Class<?>[] types) throws NoSuchMethodException {
		// TODO 考虑进行String转char, int转long的处理
		Method[] methods = clazz.getMethods();
		for (int i = 0, n = methods.length; i < n; i ++) {
			Method method = methods[i];
			Class<?>[] paramTypes = method.getParameterTypes();
			if (method.getName().equals(name)
					&& paramTypes.length == types.length) {
				if (isLikeClasses(paramTypes, types))
					return method;
			}
		}
		throw new NoSuchMethodException("No method found of " +clazz+"." + getSignature(clazz.getName(), types));
	}

	// 判断两个参数列表类型是否相似
	private static boolean isLikeClasses(Class<?>[] cs1, Class<?>[] cs2) {
		for (int j = 0, m = cs1.length; j < m; j ++) {
			Class<?> c1 = cs1[j]; // 前面已作判断，c1, c2都不为空
			Class<?> c2 = cs2[j];
			if (! isLikeClass(c1, c2))
				return false;
		}
		return true;
	}

	// 判断两个参数列表类型是否相似
	private static boolean isLikeClass(Class<?> c1, Class<?> c2) {
		if (c1 == c2)
			return true;
		if (c1 == Object.class)
			return true;
		if (c2 == null)
			return true;
		if (c1 == null)
			return false;
		if (c1.isAssignableFrom(c2))
			return true;
		if (c1.isPrimitive()
				&& isLikePrimitiveClass(c1, c2))
			return true;
		if (c2.isPrimitive()
				&& isLikePrimitiveClass(c2, c1))
			return true;
		return false;
	}

	// 判断基本类型是否相似
	private static boolean isLikePrimitiveClass(Class<?> c1, Class<?> c2) {
		return (c1 == Boolean.TYPE && c2 == Boolean.class)
				|| (c1 == Byte.TYPE && c2 == Byte.class)
				|| (c1 == Character.TYPE && c2 == Character.class)
				|| (c1 == Short.TYPE && c2 == Short.class)
				|| (c1 == Integer.TYPE && c2 == Integer.class)
				|| (c1 == Long.TYPE && c2 == Long.class)
				|| (c1 == Float.TYPE && c2 == Float.class)
				|| (c1 == Double.TYPE && c2 == Double.class);
	}

	// 获取函数签名
	private static String getSignature(String name, Class<?>[] types) {
		StringBuffer signature = new StringBuffer();
		signature.append(name);
		signature.append("(");
		if (types.length > 0) {
			for (int i = 0, n = types.length; i < n; i ++) {
				if (i != 0)
					signature.append(", ");
				signature.append(types[i].getName());
			}
		}
		signature.append(")");
		return signature.toString();
	}

	/**
	 * 执行类方法
	 *
	 * @param obj 类实例
	 * @param name 方法名
	 * @param args 方法参数
	 * @return 方法返回值
	 * @throws NoSuchMethodException 方法不存在时抛出
	 */
	public static Object invokeMethod(Object obj, String name, Object[] args) throws NoSuchMethodException {
		if (obj == null || name == null)
			return null;
		try {
			Method method = getMethod(obj.getClass(), name, args);
			return method.invoke(obj, args);
		} catch (SecurityException e) {
			throw new NoSuchMethodException(e.getMessage());
		} catch (IllegalArgumentException e) {
			throw new NoSuchMethodException(e.getMessage());
		} catch (IllegalAccessException e) {
			throw new NoSuchMethodException(e.getMessage());
		} catch (InvocationTargetException e) {
			throw new NoSuchMethodException(e.getMessage());
		}
	}

	/**
	 * 执行静态方法
	 *
	 * @param clazz 类元
	 * @param name 方法名
	 * @param args 方法参数
	 * @return 方法返回值
	 * @throws NoSuchMethodException 方法不存在时抛出
	 */
	public static Object invokeStaticMethod(Class<?> clazz, String name, Object[] args) throws NoSuchMethodException {
		if (clazz == null || name == null)
			return null;
		try {
			Method method = getMethod(clazz, name, args);
			return method.invoke(null, args);
		} catch (SecurityException e) {
			throw new NoSuchMethodException(e.getMessage());
		} catch (IllegalArgumentException e) {
			throw new NoSuchMethodException(e.getMessage());
		} catch (IllegalAccessException e) {
			throw new NoSuchMethodException(e.getMessage());
		} catch (InvocationTargetException e) {
			throw new NoSuchMethodException(e.getMessage());
		}
	}
	
}
