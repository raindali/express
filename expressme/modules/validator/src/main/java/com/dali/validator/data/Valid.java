package com.dali.validator.data;



public class Valid {
	public String rule;
	public String parmeter;
	public String notice;
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(32);
		builder.append("Valid:");
		builder.append("{");
		builder.append("property=");
		builder.append(rule);
		if (parmeter != null) {
			builder.append(", ");
			builder.append("parmeter=");
			builder.append(parmeter);
		}
		if (notice != null) {
			builder.append(", ");
			builder.append("notice=");
			builder.append(notice);
		}
		builder.append("}");
		return builder.toString();
	}
	
}
