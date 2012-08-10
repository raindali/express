package org.expressme.examples.typecho.web.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.expressme.modules.utils.GuiceContextHolder;
import org.expressme.modules.web.Identity;
import org.expressme.modules.web.security.IdentityManager;
import org.expressme.webwind.ActionContext;

public class TypechoTag {
	public static final String __TYPECHO_THEME_DIR__ = "/usr/themes";

	public static Identity getIdentity() {
		IdentityManager identityManager = GuiceContextHolder.getBean(IdentityManager.class);
		HttpServletRequest request = ActionContext.getActionContext().getHttpServletRequest();
		HttpServletResponse response = ActionContext.getActionContext().getHttpServletResponse();
		Identity identity = identityManager.getIdentity(request, response);
		return identity;
	}
	
	/**
	 * 国际化 
	 */
	public static String _e(String text) {
		return text;
	}
}
