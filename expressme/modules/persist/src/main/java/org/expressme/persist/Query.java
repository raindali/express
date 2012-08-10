package org.expressme.persist;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to execute a query JDBC SQL. The arguments of method must match the 
 * named parameters in SQL, and return value MUST be consistent with @MappedBy. 
 * For example:
 * <pre>
 *   @MappedBy(UserRowMapper.class)
 *   @Query("select * from User where u.age>=:age")
 *   List&lt;User&gt; getUsers(@Param("age") int age);
 * </pre>
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Query {

    /**
     * Prepared SQL query string with named parameters. For example, 
     * "select * from User where u.id=:id".
     * 
     * @return Query string.
     */
    String value() default "";

    /**
     * Fetch size of query, default to 20.
     */
    int fetchSize() default 20;
}
