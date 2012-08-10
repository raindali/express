package org.expressme.search.converter;

/**
 * Convert between String and boolean.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class BooleanConverter implements Converter {

    public String toString(Object o) {
        return o==null ? "false" : o.toString();
    }

    public Object toObject(String s) {
        return s==null ? Boolean.FALSE : Boolean.valueOf(s);
    }

}
