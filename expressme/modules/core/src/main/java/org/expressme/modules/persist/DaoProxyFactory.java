/**
 * 
 */
package org.expressme.modules.persist;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.expressme.persist.DaoConfigException;
import org.expressme.persist.DaoFactory;
import org.expressme.persist.Transaction;
import org.expressme.persist.TransactionCallback;
import org.expressme.persist.TransactionException;
import org.expressme.persist.TransactionManager;

import com.google.inject.Inject;

/**
 * @author xiaoli
 * 
 */
public class DaoProxyFactory {
	private final Log log = LogFactory.getLog(getClass());
	@Inject private TransactionManager txManager;
	@Inject private DaoFactory daoFactory;

	public DaoProxyFactory() {}

	public DaoProxyFactory(TransactionManager txManager, DaoFactory daoFactory) {
		this.txManager = txManager;
		this.daoFactory = daoFactory;
	}

	public <T> T new2(final Class<T> clazz) {
		final T dao = daoFactory.createDao(clazz, txManager);
		if (!clazz.isInterface()) {
			throw new DaoConfigException("DAO class is not interface: " + clazz.getName());
		}
		return new TransactionCallback<T>(txManager) {
			@Override
			protected T doInTransaction() throws Exception {
				// TODO Auto-generated method stub
				return null;
			}
		}.execute();
	}
	
	@SuppressWarnings("unchecked")
	public <T> T newProxy(final Class<T> clazz) {
		final T dao = daoFactory.createDao(clazz, txManager);
		if (!clazz.isInterface()) {
			throw new DaoConfigException("DAO class is not interface: " + clazz.getName());
		}
		T t = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[] { clazz }, new InvocationHandler() {
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				Object object = null;
				Transaction tx = null;
				try {
					// 自动提交
					if (DaoAccessor.getAutoCommit()) {
						tx = txManager.beginTransaction();
						object = method.invoke(dao, args);
						tx.commit();
					} else
						object = method.invoke(dao, args);
				} catch (TransactionException te) {
					log.error("Cannot invoke method because transaction occur error.", te);
					if (tx != null)
						tx.rollback();
				} catch (Throwable e) {
					log.error("Cannot invoke method because SqlExecutor occur error.", e);
					if (tx != null)
						tx.rollback();
				}
				return object;
			}
		});
		log.info("DAO class '" + clazz.getName() + "' created successfully!");
		return t;
	}
}
