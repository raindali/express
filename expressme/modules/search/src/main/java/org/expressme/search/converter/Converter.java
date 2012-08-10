package org.expressme.search.converter;

/**
 * Convert specific object to String.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public interface Converter {

    /**
     * Convert specific object to String.
     */
    String toString(Object o);

    /**
     * Convert String to specific object.
     */
    Object toObject(String s);

}
