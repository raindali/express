package org.expressme.persist;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.expressme.persist.mapper.RowMapper;

/**
 * This annotation is used by JDBC persistence to map result set to Java object.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MappedBy {

	Class<? extends RowMapper<?>> value();
}
