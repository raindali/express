package org.expressme.search.converter;

/**
 * Convert between String and short.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class ShortConverter implements Converter {

    private static final Short ZERO = Short.valueOf((short)0);

    public String toString(Object o) {
        return o==null ? "0" : o.toString();
    }

    public Object toObject(String s) {
        try {
            return s==null ? ZERO : Short.valueOf(s);
        }
        catch (NumberFormatException e) {
            return ZERO;
        }
    }

}
