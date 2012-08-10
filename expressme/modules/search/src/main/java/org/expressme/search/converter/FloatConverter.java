package org.expressme.search.converter;

/**
 * Convert between String and float.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class FloatConverter implements Converter {

    private static final Float ZERO = Float.valueOf(0f);

    public String toString(Object o) {
        return o==null ? "0" : o.toString();
    }

    public Object toObject(String s) {
        try {
            return s==null ? ZERO : Float.valueOf(s);
        }
        catch (NumberFormatException e) {
            return ZERO;
        }
    }

}
