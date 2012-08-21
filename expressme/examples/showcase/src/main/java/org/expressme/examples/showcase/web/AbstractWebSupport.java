package org.expressme.examples.showcase.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.expressme.bind2.BindUtils;
import org.expressme.modules.persist.DaoAccessor;
import org.expressme.modules.web.security.IdentityManager;
import org.expressme.modules.web.security.SecurityChecker;
import org.expressme.webwind.ActionContext;
import org.expressme.webwind.renderer.TemplateRenderer;

import com.google.inject.Inject;

public abstract class AbstractWebSupport implements Settings {
	protected Logger logger = Logger.getLogger(getClass());
	@Inject
	protected IdentityManager identityManager;
	@Inject
	protected DaoAccessor daoAccessor;

	protected AbstractWebSupport() {
		SecurityChecker.init(getClass());
	}

	protected ActionContext getActionContext() {
		return ActionContext.getActionContext();
	}

	protected HttpServletRequest getHttpServletRequest() {
		return getActionContext().getHttpServletRequest();
	}

	protected HttpServletResponse getHttpServletResponse() {
		return getActionContext().getHttpServletResponse();
	}

	protected String getParameter(String name) {
		return getHttpServletRequest().getParameter(name);
	}

	protected <T> T bind(Class<T> clazz) {
		return BindUtils.bind(getHttpServletRequest(), clazz);
	}
	

	private static String prefix = "/WEB-INF/jsp/";
	private static String suffix = ".jsp";

	private static String fullPath(String path) {
		return prefix + path + suffix;
	}

	public static TemplateRenderer renderer(String path) {
		return new TemplateRenderer(fullPath(path));
	}

	public TemplateRenderer renderer(String path, Map<String, Object> model) {
		return new TemplateRenderer(fullPath(path), model);
	}

	public static TemplateRenderer renderer(String path, String modelKey, Object modelValue) {
		return new TemplateRenderer(fullPath(path), modelKey, modelValue);
	}

}
