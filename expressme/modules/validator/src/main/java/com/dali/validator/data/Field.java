package com.dali.validator.data;

import java.util.ArrayList;
import java.util.List;

public class Field {
	public List<Valid> valid  = new ArrayList<Valid>();
	public String property;
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(128);
		builder.append("Field:{");
		builder.append("property=");
		builder.append(property);
		builder.append(",");
		builder.append("\n\t");
		// #start of valid
		builder.append("valid={");
		for (Valid v : valid) {
			builder.append("\n\t\t");
			builder.append(v.toString());
		}
		builder.append("\n\t");
		builder.append("}");
		// #end of valid
		builder.append("}");
		return builder.toString();
	}
}
