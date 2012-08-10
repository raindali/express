package org.expressme.modules.web.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限，可以使用在类和方法上
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Security {
	/**
	 * 需要拦截的方法名，使用正则表达式，每个条件独立；@Security匹配任何方法，@Security("")不匹配任何方法<br>
	 * <blockquote><pre>
	 *	@Security({"index", "show"})	匹配index、show方法
	 *	@Security({"index.*", "show.*"})	匹配index或show开头方法
	 *	@Security({"!show"})		匹配除show方法外的其他方法
	 *	@Security({"show.+"})		匹配以show方法开头的方法，如show123
	 * </pre></blockquote>
	 */
	String[] value() default {".+"};
}
