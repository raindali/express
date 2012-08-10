package com.dali.validator.resolver;

import org.junit.Test;

public class XmlValidatorResolverTest {

	@Test
	public void resolveValidator_test() {
		new XmlValidatorResolver().resolveValidator();
	}
	
	@Test
	public void resolverCommand_test() {
		new XmlValidatorResolver().resolverCommand();
	}
}
