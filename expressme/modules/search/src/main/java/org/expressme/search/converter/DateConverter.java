package org.expressme.search.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Convert between String and java.util.Date.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class DateConverter implements Converter {

    private final String format;

    public DateConverter() {
        this("yyyy-MM-dd HH:mm:ss");
    }

    public DateConverter(String format) {
        this.format = format;
    }

    public String toString(Object date) {
        return date==null ? "" : new SimpleDateFormat(format).format(date);
    }

    public Object toObject(String s) {
        if (s==null || s.length()==0)
            return null;
        try {
            return new SimpleDateFormat(format).parse(s);
        }
        catch (ParseException e) {
            return null;
        }
    }

}
