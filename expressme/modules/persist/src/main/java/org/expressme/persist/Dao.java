package org.expressme.persist;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Dao {

	public final static ThreadLocal<Boolean> autoCommit = new ThreadLocal<Boolean>();

	final Log log = LogFactory.getLog(getClass());
	DaoFactory daoFactory;

	String[] packageNames;

	public void setDaoFactory(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	public void setPackageName(String packageName) {
		this.packageNames = new String[] { packageName };
	}

	public void setPackageNames(List<String> packageNames) {
		this.packageNames = packageNames.toArray(new String[packageNames.size()]);
	}

	final Map<Class<?>, Object> daoMap = new ConcurrentHashMap<Class<?>, Object>();

	@SuppressWarnings("unchecked")
	<T> T getDaoByClazz(final Class<T> clazz, final TransactionManager txManager) {
		T dao = (T) daoMap.get(clazz);
		if (dao == null) {
			dao = daoFactory.createDao(clazz, txManager);
			daoMap.put(clazz, dao);
			log.info("Cache Dao Proxy Instance!");
		}
		return dao;
	}

	public <T> T createDao(final Class<T> clazz, final TransactionManager txManager) {
		final T dao = getDaoByClazz(clazz, txManager);
		@SuppressWarnings("unchecked")
		T proxyTarget = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[] { clazz },
				new InvocationHandler() {
					@Override
					public Object invoke(Object proxy, final Method method, final Object[] args) throws Throwable {
						if (autoCommit.get() == Boolean.FALSE) {
							return (T) method.invoke(dao, args);
						}
						return new TransactionCallback<T>(txManager) {
							@Override
							protected T doInTransaction() throws Exception {
								return (T) method.invoke(dao, args);
							}
						}.execute();
					}
				});
		return proxyTarget;
	}

}
