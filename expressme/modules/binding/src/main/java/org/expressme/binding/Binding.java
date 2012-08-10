package org.expressme.binding;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used by HttpServlet parameter set to Java object.
 * 
 * @author Xiaoli (mengfan0871@gmail.com)
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Binding {

    Class<?> value();
}
