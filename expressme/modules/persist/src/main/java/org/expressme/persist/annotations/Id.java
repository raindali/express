package org.expressme.persist.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Used to link table primary id.
 * 
 * @author xiaoli (mengfan0871@gmail.com)
 */

@Target({ METHOD, FIELD })
@Retention(RUNTIME)
public @interface Id {
}
