package org.expressme.examples.showcase.web.controller;

import org.expressme.binding.Binding;
import org.expressme.examples.showcase.dao.CityBusDict;
import org.expressme.examples.showcase.web.AbstractWebSupport;
import org.expressme.examples.showcase.web.command.IndexCommand;
import org.expressme.examples.showcase.web.service.IndexService;
import org.expressme.modules.web.RendererFactory;
import org.expressme.webwind.Mapping;
import org.expressme.webwind.renderer.Renderer;

import com.google.inject.Inject;

@Mapping("/dali")
public class IndexController extends AbstractWebSupport {
	@Inject IndexService indexService;
	@Mapping("/")
	public Renderer index() {
		String[] k = getHttpServletRequest().getParameterValues("k");
		String k2 = getHttpServletRequest().getParameter("k2");
		System.out.println(k==null?"":k.length);
//		indexService.index();
		return RendererFactory.renderer("index");
	}

	@Mapping(value = "/index/$1")
	@Binding(IndexCommand.class)
	public Renderer channel(String id) {
		logger.debug("--");
		CityBusDict.dict();
		return RendererFactory.renderer("channel");
	}
}
