package org.expressme.modules.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.expressme.modules.web.ValidateException;

public abstract class ValidateUtils {

    private static final Map<String, Pattern> patterns = new HashMap<String, Pattern>();

    private ValidateUtils() {}

    /**
     * Assert that the value is greater than or equal to min.
     */
    public static void gt(int testValue, int min) {
        if (testValue<min)
            throw new ValidateException("Value '" + testValue + "' is less than " + min);
    }

    /**
     * Assert that the value is less than or equal to max.
     */
    public static void lt(int testValue, int max) {
        if (testValue>max)
            throw new ValidateException("Value '" + testValue + "' is greater than " + max);
    }

    /**
     * Assert that the value is in range of min and max.
     */
    public static void range(int testValue, int min, int max) {
        gt(testValue, min);
        lt(testValue, max);
    }

    /**
     * Assert that the length of String is in range of min and max.
     */
    public static void range(String testValue, int min, int max) {
        notNull(testValue);
        if (testValue.length()<min)
            throw new ValidateException("Length of value '" + testValue+ "' is less than " + min);
        if (testValue.length()>max)
            throw new ValidateException("Length of value '" + testValue+ "' is greater than " + max);
    }

    /**
     * Assert that String is not null.
     */
    public static void notNull(String testValue) {
        if (testValue==null)
            throw new ValidateException("Value is null.");
    }

    /**
     * Assert that String is not null and not empty String ("").
     */
    public static void notEmpty(String testValue) {
        notNull(testValue);
        if (testValue.length()==0)
            throw new ValidateException("Value is empty.");
    }

    /**
     * Assert that String is not null, not empty String (""), and not black String ("  ").
     */
    public static void notBlank(String testValue) {
        notEmpty(testValue);
        if (testValue.trim().length()==0)
            throw new ValidateException("Value '" + testValue + "' is blank.");
    }

    /**
     * Assert that String is not null, and matches the regular expression.
     */
    public static void regex(String testValue, String regex) {
        notNull(testValue);
        Pattern pattern = patterns.get(regex);
        if (pattern==null) {
            synchronized (patterns) {
                pattern = Pattern.compile(regex);
                patterns.put(regex, pattern);
            }
        }
        if (! pattern.matcher(testValue).matches()) {
            throw new ValidateException(
                    new StringBuilder(256)
                            .append("Value '")
                            .append(testValue)
                            .append("' not match the regular expression '")
                            .append(regex)
                            .append("'.")
                            .toString()
            );
        }
    }
}
