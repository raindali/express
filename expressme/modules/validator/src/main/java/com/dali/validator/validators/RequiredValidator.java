/**
 * 
 */
package com.dali.validator.validators;

import com.dali.validator.data.Valid;

/**
 * 类描述
 *
 * @author xiaoli
 * @since  2009-8-20
 */
public class RequiredValidator extends AbstractValidator {
	public RequiredValidator() {
		super("required");
	}

	@Override
	protected boolean validatorInternal(Object object, String property, Valid valid) {
		Object obj = getProperty(object, property);
		if (isEmpty(obj))
			return false;
		return true;
	}
}
