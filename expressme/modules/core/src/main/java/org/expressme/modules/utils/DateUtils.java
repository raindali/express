package org.expressme.modules.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Format date and time.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public abstract class DateUtils {

    private DateUtils() {}
	
    public static String formatDate(String pattern, long date) {
        return new SimpleDateFormat(pattern).format(date);
    }

    public static String formatTime(String pattern, long time) {
        return new SimpleDateFormat(pattern).format(time);
    }

    public static String formatDateTime(String pattern, long datetime) {
        return new SimpleDateFormat(pattern).format(datetime);
    }

    /**
     * Format time as standard format: EEE, dd MMM yyyy HH:mm:ss Z.
     */
    public static String formatLong(long datetime) {
        // Sat, 11 Apr 2009 00:04:31 +0800
        return new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH).format(datetime);
    }

    public static String formatLong(long datetime, Locale locale) {
        return DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, locale).format(datetime);
    }
}
