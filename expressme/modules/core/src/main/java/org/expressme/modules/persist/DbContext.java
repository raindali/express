package org.expressme.modules.persist;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.expressme.modules.utils.AssertUtils;
import org.expressme.persist.Db;

/**
 * @author dali
 */
public class DbContext {

	private static Db db = null;

	private static Log logger = LogFactory.getLog(DbContext.class);

	/**
	 * 取得存储在静态变量中的ApplicationContext.
	 */
	public static Db getDb() {
		assertInjected();
		return db;
	}

	/**
	 * 清除Context中db为Null.
	 */
	public static void clear() {
		logger.debug("清除SpringContextHolder中的ApplicationContext:" + db);
		db = null;
	}

	/**
	 * 注入Context到静态变量中.
	 */
	public static void setDb(Db db) {
		logger.debug("注入db到Context:{}");
		if (DbContext.db != null) {
			logger.warn("Context中的db被覆盖, 原有db为:" + DbContext.db);
		}
		DbContext.db = db; //NOSONAR
	}

	/**
	 * 实现destroy接口, 在Context关闭时清理静态变量.
	 */
	public void destroy() throws Exception {
		DbContext.clear();
	}

	/**
	 * 检查db不为空.
	 */
	private static void assertInjected() {
		AssertUtils.notNull(db, "db属性未注入, 请在module中定义Context");
	}
}
