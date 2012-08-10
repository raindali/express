package org.expressme.search;

/**
 * Define store type.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public enum Store {

    /**
     * Store the original field value in the index in a compressed form. This 
     * is useful for long documents and for binary valued fields.
     */
    COMPRESS,

    /**
     * Store the original field value in the index. This is useful for short 
     * texts like a document's title which should be displayed with the results. 
     * The value is stored in its original form, i.e. no analyzer is used 
     * before it is stored.
     */
    YES,

    /**
     * Do not store the field value in the index. To get this field's value, 
     * external query is needed, i.e. query database to retrieve the value.
     */
    NO

}
