package com.dali.validator.data;

import java.util.LinkedHashMap;
import java.util.Map;

public class Command {
	public Map<String, Group> groups = new LinkedHashMap<String, Group>();
	public Map<String, Field> fields = new LinkedHashMap<String, Field>();
	public String clazz;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(512);
		builder.append("\n");
		builder.append("Command:");
		builder.append("{");
		builder.append("property=");
		builder.append(clazz);
		builder.append(",");
		builder.append("\n");
		for (Field f : fields.values()) {
			builder.append("\t");
			builder.append(f.toString());
			builder.append(",\n");
		}
		for (Group m : groups.values()) {
			builder.append("\t");
			builder.append(m.toString());
			builder.append(",\n");
		}
		builder.append("}");
		return builder.toString();
	}
}