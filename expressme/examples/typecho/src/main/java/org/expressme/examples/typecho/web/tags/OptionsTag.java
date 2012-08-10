package org.expressme.examples.typecho.web.tags;

import org.expressme.examples.typecho.entity.Options;
import org.expressme.examples.typecho.web.manager.OptionsManager;
import org.expressme.modules.utils.GuiceContextHolder;
import org.expressme.modules.web.Identity;

/**
 * @author xiaoli
 */
public class OptionsTag extends TypechoTag {
	public static String value(String name, int user) {
		Options opt = getOptions(name, user);
		return opt == null ? null : opt.getValue();
	}

	public static String themeUrl(String css) {
		Options siteUrl = getOptions("siteUrl");
		return siteUrl.getValue() + __TYPECHO_THEME_DIR__ + "/" + css;
	}

	private static Options getOptions(String name, int user) {
		OptionsManager om = GuiceContextHolder.getBean(OptionsManager.class);
		return om.getOptions(name, user);
	}
	
	private static Options getOptions(String name) {
		OptionsManager om = GuiceContextHolder.getBean(OptionsManager.class);
		Identity identity = getIdentity();
		return om.getOptions(name, Integer.parseInt(identity.getId()));
	}
}