package org.expressme.modules.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class ContextUtils {
	private static final Log logger = LogFactory.getLog(ContextUtils.class);
	private static Properties properties;

	public static final String SERVER_DEPLOY = "server.deploy";

	public static String getWebappRoot() {
		return System.getProperty("webapp.root");
	}

	public static String getProperty(String key) {
		if (properties == null) {
			try {
				properties = new Properties();
				String ctx = getWebappRoot() + File.separator + "WEB-INF" + File.separator + "context.properties";
				logger.debug("context.properties path:" + ctx);
				properties.load(new FileReader(ctx));
			} catch (FileNotFoundException e) {
				logger.error("文件不存在！", e);
				properties = null;
			} catch (IOException e) {
				logger.error("IO错误！", e);
				properties = null;
			}
		}
		return properties.getProperty(key);
	}

}
