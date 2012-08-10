package org.expressme.modules.web;

import java.util.LinkedHashMap;
import java.util.Map;

public class Container {
	private Map<String, String> errors = new LinkedHashMap<String, String>();
	private Map<String, Object> values = new LinkedHashMap<String, Object>();

	public void addError(String key, String value) {
		this.errors.put(key, value);
	}

	public <T> void addValue(String key, T value) {
		this.values.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public <T> T getValue(String key) {
		return (T) this.values.get(key);
	}

	/**
	 * @param errors the errors to set
	 */
	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}

	/**
	 * @return the errors
	 */
	public Map<String, String> getErrors() {
		return errors;
	}

	/**
	 * @param values the values to set
	 */
	public void setValues(Map<String, Object> values) {
		this.values = values;
	}

	/**
	 * @return the values
	 */
	public Map<String, Object> getValues() {
		return values;
	}
}
