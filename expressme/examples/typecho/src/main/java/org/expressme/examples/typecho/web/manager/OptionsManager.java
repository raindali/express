package org.expressme.examples.typecho.web.manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.expressme.examples.typecho.dao.OptionsDao;
import org.expressme.examples.typecho.entity.Options;
import org.expressme.modules.persist.DaoAccessor;
import org.expressme.modules.reload.ReloadAbstract;

import com.google.inject.Inject;

public class OptionsManager extends ReloadAbstract {
	private Log logger = LogFactory.getLog(this.getClass());
	private final Map<String, Options> options = new ConcurrentHashMap<String, Options>();

	private @Inject
	DaoAccessor daoAccessor;

	public OptionsManager() {
		reload();
	}

	@Override
	public void load() {
		options.clear();
		OptionsDao dao = daoAccessor.getDaoSupport(OptionsDao.class);
		for (Options ops : dao.queryAll()) {
			options.put(ops.getName() + "-" + ops.getUser(), ops);
		}
		logger.info("初始化数据共计:" + options.size());
	}

	public Options getOptions(String name, int user) {
		reload();
		return options.get(name + "-" + user);
	}
}
