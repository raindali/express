/**
 * 
 */
package org.expressme.modules.persist;

import org.expressme.modules.cache.Cache;
import org.expressme.modules.cache.FetchCallback;
import org.expressme.modules.cache.LocalCache;

import com.google.inject.Inject;

/**
 * @author xiaoli
 */
public class DaoAccessor {
	// 存放线程ID
	private final static ThreadLocal<Long> autoCommitThreadLocal = new ThreadLocal<Long>();
	private final Cache<Class<?>, Object> cache;
	@Inject private DaoProxyFactory factory;

	public DaoAccessor() {
		cache = new LocalCache<Class<?>, Object>();
	}

	@SuppressWarnings("unchecked")
	public <T> T getDaoSupport(final Class<T> clazz) {
		return (T) cache.get(clazz, new FetchCallback<Object>() {
			public Object fetch() {
				return factory.newProxy(clazz);
			}
		});
	}

	public static boolean getAutoCommit() {
		return autoCommitThreadLocal.get() == null;
	}

	public static void setAutoCommit(boolean autoCommit) {
		if (autoCommit) {
			autoCommitThreadLocal.remove();
		} else {
			autoCommitThreadLocal.set(Thread.currentThread().getId());
		}
	}

	public static void cleanAutoCommit() {
		autoCommitThreadLocal.remove();
	}
}