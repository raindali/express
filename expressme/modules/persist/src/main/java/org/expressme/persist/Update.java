package org.expressme.persist;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to execute an Update JDBC SQL. The arguments of method must match the 
 * named parameters in SQL, and return value MUST be 'int' or 'void'. 
 * For example:
 * <pre>
 *   @Update("update User u set u.name=:name where u.id=:id")
 *   int updateUser(@Param("id") String id, @Param("name") String name);
 * </pre>
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Update {

    /**
     * Prepared JDBC SQL update string with named parameters. For example, 
     * "update User u set u.name=:name where u.id=:id". NOTE that this value 
     * is only available when using JDBC DAO implementation.
     * 
     * @return Update string.
     */
    String value() default "";

}
