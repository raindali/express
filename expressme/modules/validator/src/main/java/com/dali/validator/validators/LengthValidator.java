package com.dali.validator.validators;

import com.dali.validator.data.Valid;

public class LengthValidator extends AbstractValidator {
	public LengthValidator() {
		super("length");
	}


	@Override
	public boolean validatorInternal(Object object, String property, Valid valid) {
		String[] pars = getParmeter().split(":");
		int mix = Integer.parseInt(pars[0]);
		int max = Integer.parseInt(pars[1]);
		Object obj = getProperty(object, property);
		if (isNull(obj)) {
			return false;
		}
		if (mix > -1 && obj.toString().length() < mix) {
			return false;
		} else if (max > -1 && obj.toString().length() > max) {
			return false;
		}
		return true;
	}
}
