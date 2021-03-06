package org.expressme.simplejdbc.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Used to ignore table column.
 * 
 * @author xiaoli (mengfan0871@gmail.com)
 */
/**
 * Specifies that the property or field is not persistent. It is used
 * to annotate a property or field of an entity class, mapped
 * superclass, or embeddable class.
 *
 * <pre>
 *    Example:
 *
 *    &#064;Entity
 *    public class Employee {
 *        &#064;Id int id;
 *        &#064;Transient User currentUser;
 *        ...
 *    }
 * </pre>
 *
 * @since Java Persistence 1.0
 */
@Target({ METHOD, FIELD })
@Retention(RUNTIME)
public @interface Transient {
}
