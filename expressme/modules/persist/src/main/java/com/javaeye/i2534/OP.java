package com.javaeye.i2534;

import java.text.SimpleDateFormat;
import java.util.Date;

public enum OP {

	LIKE("like"), EQ("="), UNEQ("<>"), GREAT(">"), LESS("<"), GREAT_EQ(">="), LESS_EQ(
			"<="), IN("in") {

		@Override
		protected String afterParam() {
			return ")";
		}

		@Override
		protected String beforParam() {
			return "(";
		}

	};

	public String v = null;

	private OP(String v) {
		this.v = v;
	}

	public String toSql(String name, Object value) {
		StringBuffer sb = new StringBuffer();
		sb.append(ClassUtils.convertName(name));
		sb.append(" ");
		sb.append(this.v);
		sb.append(" ");
		sb.append(this.beforParam());
		sb.append(":");
		sb.append(name);
		sb.append(this.afterParam());
		return sb.toString();
	}

	private String handleParam(Object o) {
		return "?";
	}
//	private String handleParam(Object o) {
//		if (o == null) {
//			return "null";
//		}
//		if (o instanceof Date) {
//			return String.format("'%s'", new SimpleDateFormat(
//					"yyyy-MM-dd HH:mm:ss").format((Date) o));
//		} else if (o instanceof Object[]) {
//			StringBuffer sb = new StringBuffer();
//			Object[] array = (Object[]) o;
//			for (Object obj : array) {
//				sb.append(this.handleParam(obj));
//				sb.append(",");
//			}
//			if (sb.length() > 0) {
//				sb.deleteCharAt(sb.length() - 1);
//			}
//		} else if (o instanceof String || o instanceof Character) {
//			return String.format("'%s'", String.valueOf(o));
//		}
//		return String.valueOf(o);
//	}

	protected String beforParam() {
		return "";
	}

	protected String afterParam() {
		return "";
	}

}
