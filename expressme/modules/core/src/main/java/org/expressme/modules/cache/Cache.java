package org.expressme.modules.cache;

/**
 * Cache interface for all implementations.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 *
 * @param <K> Type of key.
 * @param <V> Type of value.
 */
public interface Cache<K, V> {

    /**
     * Get value from cache by key.
     * 
     * @param key Key.
     * @return Value if found, or null if not exist.
     */
    V get(K key);

    /**
     * Get value from cache by key, and if not exist, callback object will be 
     * called to get the value, then put into cache and return.
     * 
     * @param key Key.
     * @param callback Callback object to fetch value if not found in cache.
     * @return Value from cache or callback.
     */
    V get(K key, FetchCallback<V> callback);

    /**
     * Put value into cache.
     * 
     * @param key Key.
     * @param value Value.
     */
    void put(K key, V value);

    /**
     * Remove value from cache.
     * 
     * @param key Key.
     */
    void remove(K key);

    /**
     * Get current size of cache.
     * 
     * @return Number of cached objects.
     */
    int size();

}
