package org.expressme.search;

/**
 * Define index type.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public enum Index {

    /**
     * Index the field's value so it can be searched. An Analyzer will be used 
     * to tokenize and possibly further normalize the text before its terms 
     * will be stored in the index. This is useful for common text.
     */
    ANALYZED,

    /**
     * Index the field's value without using an Analyzer, so it can be searched. 
     * As no analyzer is used the value will be stored as a single term. This 
     * is useful for unique id.
     */
    NOT_ANALYZED,

    /**
     * Do not index the field value. This field can thus not be searched, but 
     * one can still access its contents provided it is stored.
     */
    NO

}
