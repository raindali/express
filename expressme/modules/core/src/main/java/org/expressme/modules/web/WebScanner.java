package org.expressme.modules.web;

import java.io.File;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.Binder;

public class WebScanner {
	private  static final Log log = LogFactory.getLog(WebScanner.class);

	public static void scanning(Binder binder, String packAge) {
		String webClasses = new File(WebScanner.class.getResource("/").getFile()).getPath() + File.separator;
		File directory = new File(webClasses + packAge.replace('.', File.separatorChar));
		Collection<File> files = FileUtils.listFiles(directory, new String[] { "class" }, true);
		for (File f : files) {
			String clazz = f.getPath().substring(webClasses.length(), f.getPath().lastIndexOf(".class"))
					.replace(File.separatorChar, '.');
			try {
				if (clazz.endsWith("Controller") || clazz.endsWith("Service") || clazz.endsWith("Manager")) {
					Class<?> loadClazz = WebScanner.class.getClassLoader().loadClass(clazz);
					binder.bind(loadClazz).asEagerSingleton();
					log.debug("Guice binder:" + clazz);
				}
			} catch (ClassNotFoundException e) {
				log.error("Guice binder error!", e);
			}
		}
	}
	
	public static void scanningDao(Binder binder, String packAge) {
		String webClasses = new File(WebScanner.class.getResource("/").getFile()).getPath() + File.separator;
		File directory = new File(webClasses + packAge.replace('.', File.separatorChar));
		Collection<File> files = FileUtils.listFiles(directory, new String[] { "class" }, true);
		for (File f : files) {
			String clazz = f.getPath().substring(webClasses.length(), f.getPath().lastIndexOf(".class"))
					.replace(File.separatorChar, '.');
			try {
				if (clazz.endsWith("Dao")) {
					Class<?> loadClazz = WebScanner.class.getClassLoader().loadClass(clazz);
					binder.bind(loadClazz).asEagerSingleton();
					log.debug("Guice binder:" + clazz);
				}
			} catch (ClassNotFoundException e) {
				log.error("Guice binder error!", e);
			}
		}
	}
}
