package org.expressme.modules.web.security;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class SecurityChecker {
	private final static Map<String, Security> securities = new HashMap<String, Security>(128);

	public static void init(Class<?> clazz) {
		Security security = clazz.getAnnotation(Security.class);
		if (security != null) {
			securities.put(clazz.getName(), security);
		}
		for (Method method : clazz.getDeclaredMethods()) {
			security = method.getAnnotation(Security.class);
			if (security != null) {
				securities.put(clazz.getName() + ":" + method.getName(), security);
			}
		}
	}

	public static boolean hit(Class<?> clazz, String method, boolean context) {
		// 方法注解
		Security security = securities.get(clazz.getName() + ":" + method);
		if (security != null) {
			if (matches(security.value(), method)) {
				return context;
			} else {
				return true;
			}
		}
		security = securities.get(clazz.getName());
		// 类注解
		if (security != null && matches(security.value(), method)) {
			return context;
		}
		return true;
	}

	private static boolean matches(String[] methods, String method) {
		for (String value : methods) {
			if (method.matches(value)) {
				return true;
			}
		}
		return false;
	}
}
