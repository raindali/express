/**
 * 
 */
package com.dali.validator.resolver;

import java.util.List;

import com.dali.validator.data.Command;
import com.dali.validator.validators.AbstractValidator;

/**
 * @author xiaoli
 *
 */
public interface ValidatorResolver {

	// 返回配置验证信息
	public List<Command> resolverCommand();
	
	// 返回验证器
	public List<AbstractValidator> resolveValidator();
}
