package org.expressme.modules.cache;

/**
 * If not hit in cache, then get object from callback.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 * 
 * @param <T> Generic type of cache object.
 */
public interface FetchCallback<T> {

    T fetch();

}
