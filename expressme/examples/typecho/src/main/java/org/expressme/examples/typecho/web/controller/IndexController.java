package org.expressme.examples.typecho.web.controller;

import org.expressme.binding.Binding;
import org.expressme.examples.typecho.dao.CityBusDict;
import org.expressme.examples.typecho.web.AbstractWebSupport;
import org.expressme.examples.typecho.web.command.IndexCommand;
import org.expressme.examples.typecho.web.service.IndexService;
import org.expressme.modules.web.RendererFactory;
import org.expressme.webwind.Mapping;
import org.expressme.webwind.renderer.Renderer;

import com.google.inject.Inject;

@Mapping("/dali")
public class IndexController extends AbstractWebSupport {
	@Inject IndexService indexService;
	@Mapping("/")
	public String index() {
		indexService.index();
		return "";
	}

	@Mapping(value = "/index/$1")
	@Binding(IndexCommand.class)
	public Renderer channel(String id) {
		logger.debug("--");
		CityBusDict.dict();
		return RendererFactory.renderer("channel");
	}
}
