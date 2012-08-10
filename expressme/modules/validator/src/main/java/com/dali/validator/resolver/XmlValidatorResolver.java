package com.dali.validator.resolver;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dali.validator.data.Command;
import com.dali.validator.data.Field;
import com.dali.validator.data.Group;
import com.dali.validator.data.Valid;
import com.dali.validator.utils.XmlReader;
import com.dali.validator.validators.AbstractValidator;

public class XmlValidatorResolver implements ValidatorResolver {
	private final Log log = LogFactory.getLog(getClass());
	private String xmlDir;

	public XmlValidatorResolver() {
		xmlDir = System.getProperty("webapp.root") + File.separator + "WEB-INF" + File.separator + "classes"
				+ File.separator + "validator";
		try {
			if (!new File(xmlDir).exists()) {
				xmlDir = this.getClass().getResource("/").getFile() + "validator";
			}
			log.debug("xmlDir:" + xmlDir);
		} catch (Exception e) {
			log.error("validator配置文件路径错误", e);
		}
	}

	public List<AbstractValidator> resolveValidator() {
		List<AbstractValidator> validators = new ArrayList<AbstractValidator>();
		try {
			XmlReader xr = new XmlReader(new StringReader(read(new File(getFilePrefix(xmlDir) + "validator.xml"),
					"utf8")));
			while (xr.next() != XmlReader.END_DOCUMENT) {
				if (xr.getType() == XmlReader.START_TAG) {
					if ("validator".equals(xr.getName()) || "extendValidator".equals(xr.getName())) {
						String name = xr.getAttributeValue("name");
						String clazz = xr.getAttributeValue("class");
						String parmeter = xr.getAttributeValue("parmeter");
						try {
							if (name == null || clazz == null)
								continue;
							AbstractValidator validatorClazz = (AbstractValidator) Class.forName(clazz).newInstance();
							if (name != null) {
								validatorClazz.setName(name);
							}
							if (parmeter != null) {
								validatorClazz.setParmeter(parmeter);
							}
							validators.add(validatorClazz);
						} catch (Exception e) {
							log.error("validator配置解析错误", e);
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("validator配置解析错误", e);
		}
		return validators;
	}

	public List<Command> resolverCommand() {
		List<Command> list = new ArrayList<Command>();
		File[] files = null;
		files = listAllFile(getFilePrefix(xmlDir));
		for (File file : files) {
			try {
				Command command = new Command();
				Group group = null;
				Valid valid = null;
				Field field = null;
				XmlReader xr = new XmlReader(new StringReader(read(file, "utf8")));
				while (xr.next() != XmlReader.END_DOCUMENT) {
					if (xr.getType() == XmlReader.START_TAG) {
						if ("command".equals(xr.getName())) {
							command.clazz = xr.getAttributeValue("clazz");
						} else if ("field".equals(xr.getName())) {
							String property = xr.getAttributeValue("property");
							String[] props = property.replaceAll(" ", "").split(",");
							// 将<fields/>是下的field添加到command中，<groups/>下的field获取之前的引用
							for (String prop : props) {
								if (group != null) {
									field = command.fields.get(prop);
									if (field != null) {
										group.fields.add(field);
									}
								} else {
									field = new Field();
									field.property = prop;
									command.fields.put(prop, field);
								}
							}
						} else if ("valid".equals(xr.getName())) {
							valid = new Valid();
							valid.rule = xr.getAttributeValue("rule");
							valid.parmeter = xr.getAttributeValue("parmeter");
							valid.notice = xr.getAttributeValue("notice");
							field.valid.add(valid);
						} else if ("group".equals(xr.getName())) {
							group = new Group();
							group.name = xr.getAttributeValue("name");
							command.groups.put(group.name, group);
						}
					} else if (xr.getType() == XmlReader.END_TAG) {
						if ("command".equals(xr.getName())) {
						} else if ("fields".equals(xr.getName())) {
						} else if ("groups".equals(xr.getName())) {
						} else if ("field".equals(xr.getName())) {
							field = null;
						} else if ("valid".equals(xr.getName())) {
							valid = null;
						} else if ("group".equals(xr.getName())) {
							group = null;
						}
					}
				}
				log.debug(command.toString());
				list.add(command);
			} catch (Exception e) {
				log.error("validator配置解析错误", e);
			}
		}
		return list;
	}

	private String getFilePrefix(String prefix) {
		return prefix + (prefix.endsWith("/") ? "" : "/");
	}

	private File[] listAllFile(String root) {
		File file = new File(root);
		File[] files = file.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.getName().endsWith(".val.xml");
			}
		});
		return files;
	}

	public static String read(File file, String encoding) throws IOException {
		InputStream in = null;
		try {
			in = new FileInputStream(file);
			InputStreamReader input = new InputStreamReader(in, encoding);
			StringWriter output = new StringWriter();
			copy(input, output);
			return output.toString();
		} finally {
			if (in != null)
				in.close();
		}
	}

	public static long copy(Reader input, Writer output) throws IOException {
		int DEFAULT_BUFFER_SIZE = 1024 * 4;
		char[] buffer = new char[DEFAULT_BUFFER_SIZE];
		long count = 0;
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}
}
