/**
 * Copyright (c) 2005-2012 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.expressme.persist.simple;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 以静态变量保存Spring ApplicationContext, 可在任何代码任何地方任何时候取出ApplicaitonContext.
 * 
 * @author calvin
 */
public class DbHolder {

	private static Db db = null;

	private static Log logger = LogFactory.getLog(DbHolder.class);

	/**
	 * 取得存储在静态变量中的ApplicationContext.
	 */
	public static Db getDb() {
		assertInjected();
		return db;
	}

	/**
	 * 清除SpringContextHolder中的ApplicationContext为Null.
	 */
	public static void clearHolder() {
		logger.debug("清除SpringContextHolder中的ApplicationContext:" + db);
		db = null;
	}

	/**
	 * 实现ApplicationContextAware接口, 注入Context到静态变量中.
	 */
	public void setDb(Db db) {
		logger.debug("注入ApplicationContext到SpringContextHolder:{}");

		if (DbHolder.db != null) {
			logger.warn("SpringContextHolder中的ApplicationContext被覆盖, 原有ApplicationContext为:"
					+ DbHolder.db);
		}
		DbHolder.db = db; //NOSONAR
	}

	/**
	 * 实现DisposableBean接口, 在Context关闭时清理静态变量.
	 */
	public void destroy() throws Exception {
		DbHolder.clearHolder();
	}

	/**
	 * 检查ApplicationContext不为空.
	 */
	private static void assertInjected() {
//		Validate.validState(db != null,
//				"applicaitonContext属性未注入, 请在db.xml中定义SpringContextHolder.");
	}
}
