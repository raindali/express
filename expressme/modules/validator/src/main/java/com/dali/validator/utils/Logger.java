package com.dali.validator.utils;

import java.lang.reflect.Field;
import java.util.List;

public class Logger {

	public static <T> String logger(T object) {
		StringBuilder sb = new StringBuilder(32);
		sb.append(object.getClass().getSimpleName());
		sb.append(":{");
		Field[] fields = object.getClass().getDeclaredFields();
		for (Field f : fields) {
			if (f.getClass().isAssignableFrom(List.class)) {

			} else {
				sb.append(f.getName());
				sb.append("=");
				try {
					f.setAccessible(true);
					sb.append(f.get(object));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		sb.append("}");
		return sb.toString();
	}
}
