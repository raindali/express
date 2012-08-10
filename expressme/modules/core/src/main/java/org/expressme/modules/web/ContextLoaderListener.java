package org.expressme.modules.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Set character encoding for all request, and intercept all non-html resource.
 * 
 * @author Michael Liao (menfan0871@gmail.com)
 */
public class ContextLoaderListener implements ServletContextListener {
	public static final String DEFAULT_WEB_APP_ROOT_KEY = "webapp.root";

	public void contextInitialized(ServletContextEvent sce) {
		String key = DEFAULT_WEB_APP_ROOT_KEY;
		String root = sce.getServletContext().getRealPath("/");
		System.setProperty(key, root);
		sce.getServletContext().log("Set web app root system property: '" + key + "' = [" + root + "]");
	}

	public void contextDestroyed(ServletContextEvent sce) {
		String key = DEFAULT_WEB_APP_ROOT_KEY;
		System.getProperties().remove(key);
	}
}
