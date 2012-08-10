package org.expressme.bind2.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Convert String to Date.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class DateConverter implements Converter<Date> {

	DateFormat ISO_DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	DateFormat ISO_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	public Date convert(String s) {
		try {
			if (s.length() == 10) {
				return ISO_DATE_FORMAT.parse(s);
			}
			return ISO_DATETIME_FORMAT.parse(s);
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}
}
