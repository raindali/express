package com.javaeye.i2534;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.javaeye.i2534.annotations.Column;
import com.javaeye.i2534.annotations.Id;
import com.javaeye.i2534.annotations.Table;
import com.javaeye.i2534.annotations.Transient;

public final class ClassUtils {

	private static Map<Class<?>, Field[]> clazzCaches = new ConcurrentHashMap<Class<?>, Field[]>();

	public static String findTableName(Class<?> clazz) {
		Table t = findTableAnnotation(clazz);
		String name = t.name().trim();
		if ("".equals(name)) {
			name = clazz.getSimpleName();
			name = convertName(name);
		}
		return name;
	}

	public static String findColumnName(Class<?> clazz, String name) {
		Field f = findTableField(clazz, name);
		if (f == null) {
			throw new NullPointerException(clazz.getName() + "中找不到名称为" + name + "的字段!");
		}
		String n = findFieldName(f);
		return n;
	}

	public static String findIdName(Class<?> clazz) {
		Field field = findTableId(clazz);
		if (field == null) {
			throw new NullPointerException(clazz.getName() + " Unfound Table id field!");
		}
		Id id = field.getAnnotation(Id.class);
		String name = id.value();
		if (name.equals("")) {
			name = field.getName();
			name = convertName(name);
		}
		return name;
	}

	public static String findFieldName(Field f) {
		Column field = f.getAnnotation(Column.class);
		String n = f.getName().toLowerCase();
		if (field == null) {
			Id id = f.getAnnotation(Id.class);
			if (id != null) {
				n = id.value();
			}
		} else {
			n = field.name();
		}
		if (n.equals("")) {
			n = f.getName();
			n = convertName(n);
		}
		return n;
	}

	public static Field[] listTableFields(Class<?> c) {
		Table t = findTableAnnotation(c);
		Field[] fields = clazzCaches.get(c);
		if (fields != null)
			return fields;
		fields = c.getDeclaredFields();
		if (fields == null)
			return null;
		boolean full = t.full();
		ArrayList<Field> list = new ArrayList<Field>();
		for (Field field : fields) {
			if (full) {
				if (!field.isAnnotationPresent(Transient.class)) {
					list.add(field);
				}
			} else {
				if (field.isAnnotationPresent(Id.class) || field.isAnnotationPresent(Column.class)) {
					list.add(field);
				}
			}
		}
		fields = list.toArray(new Field[list.size()]);
		clazzCaches.put(c, fields);
		return fields;
	}

	public static Field findTableField(Class<?> c, String name) {
		Field[] fields = listTableFields(c);
		for (Field f : fields) {
			if (f.getName().equalsIgnoreCase(name)) {
				return f;
			}
		}
		return null;
	}

	public static Field findTableId(Class<?> c) {
		Field[] fields = listTableFields(c);
		if (fields == null)
			return null;
		for (Field f : fields) {
			if (f.isAnnotationPresent(Id.class)) {
				return f;
			}
		}
		return null;
	}

	public static Field[] listFieldsWithoutId(Class<?> c) {
		Field id = findTableId(c);
		Field[] fields = listTableFields(c);
		if (id == null || fields == null)
			return fields;
		Field[] array = new Field[fields.length - 1];
		for (int i = 0, j = 0; i < fields.length; i++) {
			Field f = fields[i];
			if (!f.equals(id)) {
				array[j++] = f;
			}
		}
		return array;
	}

	public static String convertName(String fn) {
		if (fn == null || "".equals(fn.trim()))
			return fn;
		StringBuilder sb = new StringBuilder(fn.trim());
		char[] ch = fn.toCharArray();
		for (int i = 1, j = 0; i < ch.length; i++) {
			if (Character.isUpperCase(ch[i])) {
				int offset = i + j++;
				sb.insert(offset, '_');
			}
		}
		return sb.toString().toLowerCase();
	}

	private static Table findTableAnnotation(Class<?> c) {
		Table t = c.getAnnotation(Table.class);
		if (t == null) {
			throw new NullPointerException("Unfound Table annotation");
		}
		return t;
	}
}
