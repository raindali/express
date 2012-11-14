package org.expressme.examples.showcase.web.controller;

import org.expressme.examples.showcase.web.AbstractWebSupport;
import org.expressme.examples.showcase.web.command.IndexCommand;
import org.expressme.examples.showcase.web.service.IndexService;
import org.expressme.modules.web.RendererFactory;
import org.expressme.webwind.Mapping;
import org.expressme.webwind.annotations.Controller;
import org.expressme.webwind.annotations.ModelAttribute;
import org.expressme.webwind.renderer.Renderer;

import com.google.inject.Inject;

@Controller
@Mapping("/dali")
public class IndexController extends AbstractWebSupport {
	@Inject
	IndexService indexService;

	@Mapping("/")
	public Renderer index() {
		return RendererFactory.renderer("index/index");
	}

	@Mapping("/index/$1")
	public Renderer channel(@ModelAttribute("index") IndexCommand index, String id) {
		logger.debug("--");
		return RendererFactory.renderer("index/index");
	}
}
