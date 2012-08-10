package com.dali.validator;

import java.util.HashMap;
import java.util.Map;

import com.dali.validator.validators.AbstractValidator;
import com.dali.validator.validators.LengthValidator;
import com.dali.validator.validators.RegexValidator;
import com.dali.validator.validators.RequiredValidator;


public class ValidatorRegister {

	public final static Map<String, AbstractValidator> validators = new HashMap<String, AbstractValidator>();

	static {
		registerValidator(new RequiredValidator());
		registerValidator(new LengthValidator());
		registerValidator(new RegexValidator());
	}

	public static void registerValidator(AbstractValidator validator) {
		if (validator != null) {
			validators.put(validator.getName(), validator);
		}
	}

	public static AbstractValidator getValidator(String name) {
		if (name == null) {
			return null;
		}
		return validators.get(name);
	}
}
