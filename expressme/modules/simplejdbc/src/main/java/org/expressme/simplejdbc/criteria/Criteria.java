package org.expressme.simplejdbc.criteria;

public class Criteria {
	
//	public static Criteria _new() {
//		return new Criteria();
//	}
//	
	public static Criteria  _select(Class<?> clazz, String[] properties) {
		return new Criteria();
	}

	public static Criteria  _update(Class<?> clazz, String[] properties, Object[] args) {
		return new Criteria();
	}

	public static Criteria  _delete(Class<?> clazz) {
		return new Criteria();
	}
	
	public static Criteria  _insert(Class<?> clazz, String[] properties, Object[] args) {
		return new Criteria();
	}

	public Criteria  _where(String conditions, Object... args) {
		return this;
	}

	public Criteria _limit(int limit) {
		return this;
	}

	public Criteria _offset(int offset) {
		return this;
	}
	
	public Criteria _order(String sql) {
		return this;
	}

	public static enum a {
		AND,
		OR
	}
	
	public static enum b {
		IsNull,
		IsNotNull,
		EqualTo,
		NotEqualTo,
		GreaterThan,
		GreaterThanOrEqualTo,
		LessThan,
		LessThanOrEqualTo,
		In,
		NotIn,
		Between,
		NotBetween,
	}
	
	public static void main(String[] args) {
	}
}
