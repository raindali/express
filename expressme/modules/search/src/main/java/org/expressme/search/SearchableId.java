package org.expressme.search;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Property with this annotation represent an ID in Lucene index.
 * This annotation must be put on getter method. <br/>
 * A SearchableId has such properties: Index.NOT_ANALYZED, Store.YES.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface SearchableId {

}
