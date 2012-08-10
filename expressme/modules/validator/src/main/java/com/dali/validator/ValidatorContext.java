package com.dali.validator;

import java.util.List;

import com.dali.validator.data.Command;
import com.dali.validator.resolver.ValidatorResolver;
import com.dali.validator.resolver.XmlValidatorResolver;
import com.dali.validator.validators.AbstractValidator;

public class ValidatorContext {
	private ValidatorResolver resolver;
	private List<Command> cmds;

	public ValidatorContext() {
		this.resolver = new XmlValidatorResolver();
		initializeContext();
	}

	public ValidatorContext(ValidatorResolver resolver) {
		this.resolver = resolver;
		initializeContext();
	}

	public void initializeContext() {
		List<AbstractValidator> list = resolver.resolveValidator();
		for (AbstractValidator validator : list) {
			ValidatorRegister.registerValidator(validator);
		}
		cmds = resolver.resolverCommand();
	}

	public List<Command> getCmds() {
		return cmds;
	}
}
