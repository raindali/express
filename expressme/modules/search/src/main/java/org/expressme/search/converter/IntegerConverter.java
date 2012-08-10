package org.expressme.search.converter;

/**
 * Convert between String and integer.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class IntegerConverter implements Converter {

    private static final Integer ZERO = Integer.valueOf(0);

    public String toString(Object o) {
        return o==null ? "0" : o.toString();
    }

    public Object toObject(String s) {
        try {
            return s==null ? ZERO : Integer.valueOf(s);
        }
        catch (NumberFormatException e) {
            return ZERO;
        }
    }

}
