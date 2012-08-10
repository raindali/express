package org.expressme.simplejdbc;

/**
 * Table name and class name mapping strategies.
 * @author mengfanjun
 */
public interface NamingStrategy {

	String clazzToTable(String clazz);

	String tableToClazz(String table);

	String fieldToColumn(String field);

	String columnToField(String column);

	/**
	 * Table names and class names consistent.
	 * 表名和类名一致。
	 */
	NamingStrategy defaultNamingStrategy = new NamingStrategy() {

		@Override
		public String clazzToTable(String clazz) {
			return clazz;
		}

		@Override
		public String tableToClazz(String table) {
			return table;
		}

		@Override
		public String fieldToColumn(String field) {
			return field;
		}

		@Override
		public String columnToField(String column) {
			return column;
		}

	};
	/**
	 * Table names and class names are mapped to uppercase letters in accordance with an underscore.
	 * 表名与类名按照下划线映射为大写字母。
	 */
	NamingStrategy underlinedNamingStrategy = new NamingStrategy() {
		char underlined_char = '_';

		private String underlined2upper(String string) {
			char[] carr = string.toCharArray();
			StringBuilder sb = new StringBuilder(carr.length);
			boolean upper = false;
			for (int i = 0; i < carr.length; i++) {
				if (carr[i] == underlined_char) {
					upper = true;
					continue;
				}
				sb.append(i == 0 || upper ? Character.toUpperCase(carr[i]) : carr[i]);
				upper = false;
			}
			return sb.toString();
		}

		private String upper2underlined(String string) {
			char[] carr = string.toCharArray();
			StringBuilder sb = new StringBuilder(carr.length);
			for (int i = 0; i < carr.length; i++) {
				if (i > 0 && Character.isUpperCase(carr[i])) {
					sb.append(underlined_char);
				}
				sb.append(Character.toLowerCase(carr[i]));
			}
			return sb.toString();
		}

		@Override
		public String tableToClazz(String table) {
			return underlined2upper(table);
		}

		@Override
		public String fieldToColumn(String field) {
			return upper2underlined(field);
		}

		@Override
		public String columnToField(String column) {
			return underlined2upper(column);
		}

		@Override
		public String clazzToTable(String clazz) {
			return upper2underlined(clazz);
		}
	};
}
