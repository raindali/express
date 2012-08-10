package org.expressme.modules.reload;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class ReloadAbstract implements Reloadable {
	protected final Log logger = LogFactory.getLog(this.getClass());
	protected final WriteLock writeLock = new ReentrantReadWriteLock().writeLock();
	private boolean reloadable;

	public void reload() {
			writeLock.lock();
			if (reloadable) {
				try {
					load();
				} catch (Exception e) {
					logger.error("数据刷新错误！", e);
				}
			}
			writeLock.unlock();
	}

	protected abstract void load();
}
