package org.expressme.search.converter;

/**
 * Convert between String and long.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class LongConverter implements Converter {

    private static final Long ZERO = Long.valueOf(0);

    public String toString(Object o) {
        return o==null ? "0" : o.toString();
    }

    public Object toObject(String s) {
        try {
            return s==null ? ZERO : Long.valueOf(s);
        }
        catch (NumberFormatException e) {
            return ZERO;
        }
    }

}
