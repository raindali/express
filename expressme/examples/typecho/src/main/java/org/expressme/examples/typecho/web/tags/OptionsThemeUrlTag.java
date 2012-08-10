package org.expressme.examples.typecho.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.expressme.examples.typecho.entity.Options;
import org.expressme.examples.typecho.web.manager.OptionsManager;
import org.expressme.modules.utils.GuiceContextHolder;

/**
 * @author xiaoli
 */
public class OptionsThemeUrlTag extends TagSupport {

	private static final long serialVersionUID = -8246166191638588615L;

	private String name;

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return EVAL_BODY_INCLUDE or EVAL_BODY_BUFFERED or SKIP_BODY
	 */
	@Override
	public int doStartTag() throws JspException {
		return EVAL_BODY_INCLUDE;
	}

	/**
	 * @return EVAL_PAGE or SKIP_PAGE
	 */
	@Override
	public int doEndTag() throws JspException {
		OptionsManager om = GuiceContextHolder.getBean(OptionsManager.class);
		pageContext.getAttribute(name);
		Options siteUrl = om.getOptions("siteUrl", 0);
		try {
			pageContext.getOut().write(siteUrl.getValue()+this.name);
		} catch (IOException e) {
			throw new JspException("write overridedContent occer IOException,block name:" + name, e);
		}
		return EVAL_PAGE;
	}

	private String getOverriedContent() {
		return null;
	}
}
