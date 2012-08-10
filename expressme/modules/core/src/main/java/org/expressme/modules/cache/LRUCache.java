package org.expressme.modules.cache;

import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * A simple LRU cache in memory which allow a maximum cache size. This class is 
 * thread-safe guaranteed by {@link java.util.concurrent.locks.ReadWriteLock}.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 * 
 * @param <K> Type of key.
 * @param <V> Type of value.
 */
public class LRUCache<K, V> implements Cache<K, V> {

    private final LinkedHashMap<K, V> map;
    private final int maxSize;
    private final Lock rLock;
    private final Lock wLock;

    public LRUCache(int maxSize) {
        this.maxSize = maxSize;
        this.map = new LinkedHashMap<K, V>(maxSize, 0.75f, true) {
			private static final long serialVersionUID = 1L;

			@Override
            protected boolean removeEldestEntry(Entry<K, V> eldest) {
                return size() > LRUCache.this.maxSize;
            }
        };
        ReadWriteLock rwLock = new ReentrantReadWriteLock();
        this.rLock = rwLock.readLock();
        this.wLock = rwLock.writeLock();
    }

    public final void put(K key, V value) {
        wLock.lock();
        try {
            map.put(key, value);
        }
        finally {
            wLock.unlock();
        }
    }

    public final V get(K key, FetchCallback<V> callback) {
        rLock.lock();
        try {
            V value = map.get(key);
            if (value!=null)
                return value;
            rLock.unlock();
            wLock.lock();
            try {
                value = callback.fetch();
                map.put(key, value);
                rLock.lock();
            }
            finally {
                wLock.unlock();
            }
            return value;
        }
        finally {
            rLock.unlock();
        }
    }

    public final V get(K key) {
        rLock.lock();
        try {
            return map.get(key);
        }
        finally {
            rLock.unlock();
        }
    }

    public final void remove(K key) {
        wLock.lock();
        try {
            map.remove(key);
        }
        finally {
            wLock.unlock();
        }
    }

    public int size() {
        rLock.lock();
        try {
            return map.size();
        }
        finally {
            rLock.unlock();
        }
    }

}
