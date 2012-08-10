package com.dali.validator.resolver;

import java.util.Map;

import org.junit.Test;

import com.dali.validator.ValidatorUtils;
import com.dali.validator.utils.Logger;

public class ValidatorUtilsTest {
	@Test
	public void resolveValidator_test() {
		User user = new User();
		user.setLogUsername("dali");
		user.setLogPassword("pwd");
		user.setDeptCode("12");
		UserCommand uc = new UserCommand();
		uc.setUser(user);
		Map<String, String> err = ValidatorUtils.validator(uc, "submit");
		System.out.println(err.size());
		System.out.println(Logger.logger(user));
	}
}
