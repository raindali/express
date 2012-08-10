package com.javaeye.i2534;

import java.util.ArrayList;
import java.util.List;

public class Clause {
	private StringBuilder sb;
	private List<Object> values;

	private Clause() {
		this.sb = new StringBuilder();
		this.values = new ArrayList<Object>();
	}

	public List<Object> getValues() {
		return values;
	}

	@Override
	public String toString() {
		return sb.toString();
	}

	public static Clause to(String name, OP op, Object value) {
		Clause c = new Clause();
		c.sb.append(op.toSql(name, value));
		c.values.add(value);
		return c;
	}

	public Clause and(String name, OP op, Object value) {
		sb.append(" and ");
		sb.append(op.toSql(name, value));
		values.add(value);
		return this;
	}

	public Clause or(String name, OP op, Object value) {
		sb.append(" or ");
		sb.append(op.toSql(name, value));
		values.add(value);
		return this;
	}

}
