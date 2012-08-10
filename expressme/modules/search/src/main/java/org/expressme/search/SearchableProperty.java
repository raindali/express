package org.expressme.search;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Property with this annotation represent a field in Lucene index. 
 * This annotation must be put on getter method.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface SearchableProperty {

    Index index() default Index.ANALYZED;

    Store store() default Store.YES;

    float boost() default 1.0f;

}
