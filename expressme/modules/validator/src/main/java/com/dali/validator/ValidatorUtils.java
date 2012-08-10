package com.dali.validator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.dali.validator.data.Command;
import com.dali.validator.data.Field;
import com.dali.validator.data.Group;
import com.dali.validator.data.Valid;
import com.dali.validator.validators.AbstractValidator;

public abstract class ValidatorUtils {
	private final static ValidatorContext context = new ValidatorContext();
	private final static Map<String, Command> cacheValidator = new HashMap<String, Command>();

	static {
		for (Command cmd : ValidatorUtils.context.getCmds()) {
			for (Group method : cmd.groups.values()) {
				cacheValidator.put(cmd.clazz + ":" + method.name, cmd);
			}
		}
	}

	public static <T> Map<String, String> validator(T object, String name) {
		Map<String, String> errors = new LinkedHashMap<String, String>();
		StringBuilder b = new StringBuilder();
		b.append(object.getClass().getCanonicalName());
		b.append(":").append(name);
		if (context != null && cacheValidator.containsKey(b.toString())) {
			Command cmd = cacheValidator.get(b.toString());
			Group method = cmd.groups.get(name);
			if (method != null) {
				for (Field field : method.fields) {
					errors.putAll(validatorField(object, field));
				}
			}
		}
		return errors;
	}

	/**
	 * 多个值校验
	 * @param <T>
	 * @param clazz
	 * @param filedName
	 * @param object
	 * @return
	 */
	public static <T> Map<String, String> validator(T object, String... fields) {
		Map<String, String> errors = new LinkedHashMap<String, String>();
		List<String> list = Arrays.asList(fields);
		for (Command cmd : ValidatorUtils.context.getCmds()) {
			if (object.getClass().getCanonicalName().equals(cmd.clazz)) {
				for (Field field : cmd.fields.values()) {
					if (list.contains(field.property)) {
						errors.putAll(validatorField(object, field));
					}
				}
				break;
			}
		}
		return errors;
	}

	private static <T> Map<String, String> validatorField(T object, Field field) {
		Map<String, String> errors = new LinkedHashMap<String, String>();
		for (Valid valid : field.valid) {
			AbstractValidator validator = ValidatorRegister.getValidator(valid.rule);
			if (validator != null) {
				if (valid.parmeter != null) {
					validator.setParmeter(valid.parmeter);
				}
				// 验证值
				if (!validator.validator(object, field.property, valid)) {
					errors.put(field.property, String.format(valid.notice, validator.getParmeter().split(":")));
					break;
				}
			}
		}
		return errors;
	}
}
