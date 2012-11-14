package org.expressme.webwind.annotations;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.TYPE)
@Retention(RUNTIME)
public @interface Controller {
	String value() default "";
}
