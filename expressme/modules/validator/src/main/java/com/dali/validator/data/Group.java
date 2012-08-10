package com.dali.validator.data;

import java.util.ArrayList;
import java.util.List;

public class Group {
	public String name;
	public List<Field> fields = new ArrayList<Field>();

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(128);
		builder.append("Group:");
		builder.append("{");
		builder.append("name=");
		builder.append(name);
		builder.append(", ");
		builder.append("valid={");
		boolean isFirst = true;
		for (Field f : fields) {
			if (isFirst)
				isFirst = false;
			else
				builder.append(", ");
			builder.append(f.property);
		}
		builder.append("}");
		builder.append("}");
		return builder.toString();
	}
}
