package org.expressme.modules.utils;

import java.util.UUID;

/**
 * Generate random UUID - a 32-bit hex string.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public abstract class RandomUtils {

    private RandomUtils() {}

    public static String nextId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
