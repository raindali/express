package org.expressme.examples.showcase.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.expressme.modules.persist.DaoAccessor;
import org.expressme.modules.web.security.IdentityManager;
import org.expressme.modules.web.security.SecurityChecker;
import org.expressme.webwind.ActionContext;

import com.google.inject.Inject;

public abstract class AbstractWebSupport implements Settings {
	protected Logger logger  =  Logger.getLogger(getClass());
	@Inject protected IdentityManager identityManager;
	@Inject protected DaoAccessor daoAccessor;

	protected AbstractWebSupport() {
		SecurityChecker.init(getClass());
	}

	protected HttpServletRequest getHttpServletRequest() {
		return ActionContext.getActionContext().getHttpServletRequest();
	}

	protected HttpServletResponse getHttpServletResponse() {
		return ActionContext.getActionContext().getHttpServletResponse();
	}

	protected String getParameter(String name) {
		return getHttpServletRequest().getParameter(name);
	}

}
