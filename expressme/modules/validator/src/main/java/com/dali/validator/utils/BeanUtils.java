package com.dali.validator.utils;

import java.lang.reflect.Method;

/**
 * Bean属性工具类.<br/>
 * Hack:
 * java.beans包下的相关类对实现外部接口的内部类, 匿名类等支持不友好, 故重新实现.
 *
 * @author emailyo@163.com
 *
 */
public class BeanUtils {

	private BeanUtils(){}

	/**
	 * 获取对象的属性值
	 *
	 * @param object 对象实例
	 * @param property 属性名
	 * @return 属性的值
	 */
	public static Object getProperty(Object object, String property) {
		if (object == null || property == null)
			return null;
		Object leaf = object;
		String[] props = property.split("\\.");
		try {
			for (String prop : props) {
				leaf = getGetter(leaf.getClass(), prop).invoke(leaf, new Object[0]);
				if (leaf == null) {
					return null;
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
		return leaf;
	}

	/**
	 * 查找getXXX与isXXX的属性Getter方法
	 *
	 * @param clazz 类元
	 * @param property 属性名
	 * @return 属性Getter方法
	 * @throws NoSuchMethodException Getter不存时抛出
	 */
	private static Method getGetter(Class<?> clazz, String property) throws NoSuchMethodException, SecurityException {
		if (clazz == null || property == null)
			return null;
		property = property.trim();
		String upper = property.substring(0, 1).toUpperCase() + property.substring(1);
		try {
			return getGetterMethod(clazz, "get" + upper);
		} catch (NoSuchMethodException e1) {
			try {
				return getGetterMethod(clazz, "is" + upper);
			} catch (NoSuchMethodException e2) {
				return getGetterMethod(clazz, property);
			}
		}
	}

	/**
	 * 获取类的方法 (保证返回方法的公开性)
	 *
	 * @param clazz 类
	 * @param methodName 方法名
	 * @return 公开的方法
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	private static Method getGetterMethod(Class<?> clazz, String methodName) throws NoSuchMethodException, SecurityException {
		if (clazz == null || methodName == null)
			return null;
		Method getter = clazz.getMethod(methodName, new Class[0]);
		getter = getAccessibleMethod(getter, clazz);
		return getter;
	}

	// 获取可访问方法
	private static Method getAccessibleMethod(Method method, Class<?> clazz) throws NoSuchMethodException, SecurityException {
		if (method == null)
			return null;
		if (! method.isAccessible()) {
			try {
				method.setAccessible(true);
			} catch (SecurityException e) { // 当不允许关闭访问控制时, 采用向上查找公开方法
				String methodName = method.getName();
				try {
					method = searchPublicMethod(clazz, methodName);
				} catch (NoSuchMethodException e1) {
					method = searchPublicMethod(clazz.getInterfaces(), methodName);
				}
			}
		}
		return method;
	}

	// 查找公开的方法 (辅助方法)
	private static Method searchPublicMethod(Class<?>[] classes, String methodName) throws NoSuchMethodException, SecurityException {
		if (classes != null && classes.length > 0) {
			for (int i = 0, n = classes.length; i < n; i ++) {
				Class<?> cls = classes[i];
				try {
					return searchPublicMethod(cls, methodName);
				} catch (NoSuchMethodException e) {
					// ignore, continue
				}
			}
		}
		throw new NoSuchMethodException(); // 未找到方法
	}

	// 查找公开的方法 (辅助方法)
	private static Method searchPublicMethod(Class<?> cls, String methodName) throws NoSuchMethodException, SecurityException {
		Method method = cls.getMethod(methodName, new Class[0]);
		if (ClassUtils.isPublicMethod(method)
				&& (method.getParameterTypes() == null
						|| method.getParameterTypes().length == 0)){ // 再保证方法是公开的
			return method;
		}
		throw new NoSuchMethodException(); // 未找到方法
	}
}
