/**
 * 
 */
package com.dali.validator.validators;

import com.dali.validator.data.Valid;
import com.dali.validator.utils.BeanUtils;

/**
 * @author xiaoli
 * 
 */
public abstract class AbstractValidator {
	/* 验证器名称 */
	private String name;
	private String parmeter;

	public AbstractValidator() {}
	
	public AbstractValidator(String name) {
		this.name = name;
	}

	/**
	 * 验证
	 * 
	 * @param object 待验证对象
	 * @return if validator ok is true, other return false
	 */
	public boolean validator(Object object, String property, Valid valid) {
		if (isNull(object) || isNull(property) || isNull(valid)) {
			return false;
		}
		if (isNull(valid.rule) || isNull(valid.notice)) {
			return false;
		}
		return validatorInternal(object, property, valid);
	}

	protected abstract boolean validatorInternal(Object object, String property, Valid valid);

	protected Object getProperty(Object object, String property) {
		return BeanUtils.getProperty(object, property);
	}

	protected boolean isNull(Object object) {
		return object == null;
	}

	protected boolean isEmpty(Object object) {
		return object == null || object.toString().trim().length() == 0;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParmeter() {
		return parmeter;
	}

	public void setParmeter(String parmeter) {
		this.parmeter = parmeter;
	}

}
