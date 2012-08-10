package org.expressme.persist;

import java.util.UUID;

/**
 * Generate ID.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public final class IdGenerator {

    /**
     * Generate UUID.
     * 
     * @return UUID as String.
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
