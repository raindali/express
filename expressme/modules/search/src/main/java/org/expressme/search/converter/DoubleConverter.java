package org.expressme.search.converter;

/**
 * Convert between String and double.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class DoubleConverter implements Converter {

    private static final Double ZERO = Double.valueOf(0f);

    public String toString(Object o) {
        return o==null ? "0" : o.toString();
    }

    public Object toObject(String s) {
        try {
            return s==null ? ZERO : Double.valueOf(s);
        }
        catch (NumberFormatException e) {
            return ZERO;
        }
    }

}
