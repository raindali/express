package org.expressme.modules.web;

import java.util.Map;

import org.expressme.webwind.renderer.TemplateRenderer;

public abstract class RendererFactory {
	public static String prefix = "/WEB-INF/views/";
	public static String suffix = ".jsp";

	private static String fullPath(String path) {
		return prefix + path + suffix;
	}

	public static TemplateRenderer renderer(String path) {
		return new TemplateRenderer(fullPath(path));
	}

	public TemplateRenderer renderer(String path, Map<String, Object> model) {
		return new TemplateRenderer(fullPath(path), model);
	}

	public static TemplateRenderer renderer(String path, String modelKey, Object modelValue) {
		return new TemplateRenderer(fullPath(path), modelKey, modelValue);
	}
}
