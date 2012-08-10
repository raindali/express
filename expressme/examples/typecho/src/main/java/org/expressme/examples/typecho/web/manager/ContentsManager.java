package org.expressme.examples.typecho.web.manager;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.expressme.examples.typecho.dao.ContentsDao;
import org.expressme.examples.typecho.entity.Contents;
import org.expressme.examples.typecho.entity.constants.Xcontents;
import org.expressme.examples.typecho.utils.TypechoUtils;
import org.expressme.modules.persist.DaoAccessor;

import com.google.inject.Inject;

public class ContentsManager {
	private Log logger = LogFactory.getLog(this.getClass());
	private @Inject DaoAccessor daoAccessor; 
	public List<Contents> queryPostRecent() {
		ContentsDao contentsDao = daoAccessor.getDaoSupport(ContentsDao.class);
		List<Contents> cs = contentsDao.queryPostRecent(Xcontents.Type.PAGE.db(), Xcontents.Status.PUBLIC.db(),
				TypechoUtils.getNow());
		return cs;
	}
}
