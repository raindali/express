package org.expressme.examples.typecho.web.service;

import org.expressme.modules.persist.DaoAccessor;
import org.expressme.modules.persist.selectkey.MySQLSelectKeyDao;

import com.google.inject.Inject;


public class IndexService {
	
	@Inject private DaoAccessor daoAccessor;
	public void index() {
		MySQLSelectKeyDao mySQLSelectKeyDao =  daoAccessor.getDaoSupport(MySQLSelectKeyDao.class);
		System.out.println("----" + mySQLSelectKeyDao.selectKey());
	}
}
