package com.javaeye.i2534;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.sql.Types;

/**
 * Decorate named parameter.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CndQuery {

    /**
     * The named parameter in query string.
     */
    String value() default "";

    /**
     * The parameter SQL type if using JDBC query. Default to java.sql.Types.NULL 
     * which means automatically convert Java object type to SQL type.
     */
    int type() default Types.NULL;
}
