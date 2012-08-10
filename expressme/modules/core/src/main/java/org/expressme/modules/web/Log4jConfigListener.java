package org.expressme.modules.web;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.web.util.Log4jWebConfigurer;

/**
 * Bootstrap listener for custom log4j initialization in a web environment.
 * Delegates to {@link Log4jWebConfigurer} (see its javadoc for configuration details).
 */
public class Log4jConfigListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent event) {
		ServletContext servletContext = event.getServletContext();
		PropertyConfigurator.configure(servletContext.getRealPath("/") + File.separator + "WEB-INF" + File.separator
				+ "log4j.properties");
	}

	public void contextDestroyed(ServletContextEvent event) {
		LogManager.shutdown();
	}

}
