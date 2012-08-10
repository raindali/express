package org.expressme.search.converter;

/**
 * Convert between String and integer.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class StringConverter implements Converter {

    public String toString(Object o) {
        return o==null ? "" : o.toString();
    }

    public Object toObject(String s) {
        return s;
    }

}
