package org.expressme.modules.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Properties文件工具类.
 * 
 * @author calvin
 */
public abstract class PropertiesUtils {

	private static final String DEFAULT_ENCODING = "UTF-8";
	private static final Log logger = LogFactory.getLog(PropertiesUtils.class);

	/**
	 * 载入多个properties文件, 相同的属性在最后载入的文件中的值将会覆盖之前的载入.
	 * 文件路径使用Spring Resource格式, 文件编码使用UTF-8.
	 * 
	 * @see org.springframework.beans.factory.config.PropertyPlaceholderConfigurer
	 */
	public static Properties loadProperties(String... resourcesPaths) throws IOException {
		Properties props = new Properties();
		for (String location : resourcesPaths) {
			logger.debug("Loading properties file from:" + location);
			InputStream is = null;
			try {
				is = new FileInputStream(new File(location));
				props.load(new InputStreamReader(is, DEFAULT_ENCODING));
			} catch (IOException ex) {
				logger.info("Could not load properties from classpath:" + location + ": " + ex.getMessage());
			} finally {
				if (is != null) {
					is.close();
				}
			}
		}
		return props;
	}
}
