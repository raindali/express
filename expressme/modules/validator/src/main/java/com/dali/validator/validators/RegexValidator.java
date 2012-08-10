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
public class RegexValidator extends AbstractValidator {
	public RegexValidator() {
		super("regex");
	}

	@Override
	protected boolean validatorInternal(Object object, String property, Valid valid) {
		Object obj = getProperty(object, property);
		if (isNull(obj))
			return false;
		return obj.toString().matches(getParmeter());
	}
}
