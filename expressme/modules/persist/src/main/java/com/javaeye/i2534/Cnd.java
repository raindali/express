package com.javaeye.i2534;

import java.util.ArrayList;
import java.util.List;

public class Cnd {
	private Class<?> clazz;
	private String[] columns;
	private List<Object> values;
	private StringBuilder sb;
	private boolean hasWhere = false;
	private boolean hasOrder = false;
	private boolean hasLimit = false;
	private int firstResult = -1;
	private int maxResults = -1;

	public Cnd(Class<?> clazz) {
		this.clazz = clazz;
		this.sb = new StringBuilder();
		this.values = new ArrayList<Object>();
	}

	public Cnd column(String... column){
		this.columns = column;
		return this;
	}

	public Cnd where(Clause clause) {
		checkLimit();
		checkOrder();
		sb.append(" where ");
		sb.append(clause.toString());
		values.add(clause.getValues());
		this.hasWhere = true;
		return this;
	}

	public Cnd and(Clause clause) {
		checkLimit();
		checkOrder();
		checkWhere();
		sb.append(" and ");
		if (clause.getValues().size() > 1) {
			sb.append("(");
			sb.append(clause.toString());
			sb.append(")");
		} else {
			sb.append(clause.toString());
		}
		values.add(clause.getValues());
		return this;
	}

	public Cnd or(Clause clause) {
		checkLimit();
		checkOrder();
		checkWhere();
		sb.append(" or ");
		if (clause.getValues().size() > 1) {
			sb.append("(");
			sb.append(clause.toString());
			sb.append(")");
		} else {
			sb.append(clause.toString());
		}
		values.add(clause.getValues());
		return this;
	}

	public Cnd orderBy(String name, boolean asc) {
		checkLimit();
		if (!this.hasOrder) {
			sb.append(" order by ");
		} else {
			sb.append(",");
		}
		sb.append(ClassUtils.findColumnName(this.clazz, name));
		if (asc) {
			sb.append(" asc");
		} else {
			sb.append(" desc");
		}
		this.hasOrder = true;
		return this;
	}

	public Cnd limit(int firstResult, int maxResults) {
		this.firstResult = firstResult;
		this.maxResults = maxResults;
		this.hasLimit = true;
		return this;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public String[] getColumns() {
		return columns;
	}
	
	public int getFirstResult() {
		return firstResult;
	}

	public int getMaxResults() {
		return maxResults;
	}

	public List<Object> getValues() {
		return values;
	}

	@Override
	public String toString() {
		return this.sb.toString();
	}

	private void checkWhere() {
		if (!this.hasWhere) {
			throw new IllegalStateException("没有where!");
		}
	}

	private void checkOrder() {
		if (this.hasOrder) {
			throw new IllegalStateException("已有order!");
		}
	}

	private void checkLimit() {
		if (this.hasLimit) {
			throw new IllegalStateException("已有Limit!");
		}
	}
}
