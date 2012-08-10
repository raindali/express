/**
 * 
 */
package org.expressme.modules.persist;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.expressme.persist.Transaction;
import org.expressme.persist.TransactionManager;

/**
 * @author mengfanjun
 * 事务模版
 */
public abstract class TxTemplate {
	private final Log log = LogFactory.getLog(getClass());

	protected abstract void executeInternal();

	public void execute(TransactionManager txManager) {
		Transaction tx = null;
		try {
			DaoAccessor.setAutoCommit(false);
			tx = txManager.beginTransaction();
			executeInternal();
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			log.error("事务异常", e);
		} finally {
			DaoAccessor.cleanAutoCommit();
		}
	}

}
