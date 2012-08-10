package org.expressme.modules.utils;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.expressme.webwind.Destroyable;
import org.expressme.webwind.guice.ServletContextAware;

import com.google.inject.Binder;
import com.google.inject.Injector;
import com.google.inject.Module;

public class GuiceContextHolder implements Module, ServletContextAware, Destroyable {
	private static Log logger = LogFactory.getLog(GuiceContextHolder.class);
	private static ServletContext servletContext;
	private static Injector injector;

	public static ServletContext getServletContext() {
		assertContextInjected();
		return servletContext;
	}
	
	public static Injector getInjector() {
		return injector;
	}
	
	/**
	 * 从静态变量servletContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	public static <T> T getBean(Class<T> requiredType) {
		assertContextInjected();
		return injector.getInstance(requiredType);
	}

	/**
	 * 清除GuiceContextHolder中的ServletContext为Null.
	 */
	public static void clearHolder() {
		logger.debug("清除GuiceContextHolder中的servletContext:" + servletContext);
		servletContext = null;
		injector = null;
	}

	/**
	 * 检查ServletContext不为空.
	 */
	private static void assertContextInjected() {
		AssertUtils.state(servletContext != null,
		"ServletContext属性未注入, 请在web.xml中定义GuiceContextHolder.");
	}

	/**
	 * 实现ServletContextAware接口, 注入Context到静态变量中.
	 */
	@Override
	public void setServletContext(ServletContext servletContext) {
		logger.debug("注入ServletContext到GuiceContextHolder:" + servletContext);

		if (GuiceContextHolder.servletContext != null) {
			logger.warn("GuiceContextHolder中的ServletContext被覆盖, 原有ServletContext为:"
					+ GuiceContextHolder.servletContext);
		}

		GuiceContextHolder.servletContext = servletContext; //NOSONAR
		GuiceContextHolder.injector = (Injector) servletContext.getAttribute(Injector.class.getName());
	}

	/**
	 * 实现DisposableBean接口, 在Context关闭时清理静态变量.
	 */
	@Override
	public void destroy(){
		GuiceContextHolder.clearHolder();
	}

	@Override
	public void configure(Binder binder) {}
}
