package org.expressme.examples.showcase.web.manager;

import org.expressme.modules.persist.DaoAccessor;
import org.expressme.modules.web.Identity;
import org.expressme.modules.web.security.FetchIdentity;

import com.google.inject.Inject;

public class FetchIdentityImpl implements FetchIdentity{

	@Inject private DaoAccessor daoAccessor;
	@Override
	public Identity fetch(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
