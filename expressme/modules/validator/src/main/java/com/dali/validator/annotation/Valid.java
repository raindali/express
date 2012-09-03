package com.dali.validator.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.PARAMETER)
@Retention(RUNTIME)
public @interface Valid {
	String value() default "";
}
