package org.expressme.examples.typecho.web.tags;

import org.expressme.examples.typecho.entity.Options;
import org.expressme.examples.typecho.entity.constants.Xcontents;
import org.expressme.examples.typecho.entity.constants.Xcontents.Status;
import org.expressme.examples.typecho.web.manager.OptionsManager;
import org.expressme.modules.utils.GuiceContextHolder;

/**
 * @author xiaoli
 */
public class ContentsTag {
	
	public static String ___tags() {
		Status.PUBLIC.name();
		return null;
	}
	public static String ___author() {
		return null;
	}
	
	public static String value(String name, int user) {
		OptionsManager om = GuiceContextHolder.getBean(OptionsManager.class);
		Options opt = om.getOptions(name, user);
		return opt.getValue();
	}
}