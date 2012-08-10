package org.expressme.bind2.converter;

/**
 * Convert String to Long.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class LongConverter implements Converter<Long> {

    public Long convert(String s) {
        return Long.parseLong(s);
    }

}
