package org.expressme.modules.web;

import org.expressme.modules.web.Container;

public class ContainerContext {

	private static final ThreadLocal<ContainerContext> containerContextThreadLocal = new ThreadLocal<ContainerContext>();

	private Container container;

	/**
	 * @return the Container
	 */
	public Container getContainer() {
		return container;
	}

	/**
	 * Get current Container object.
	 */
	public static <T extends Container> Container getCurrentContainer() {
		if (containerContextThreadLocal.get() != null) {
			return containerContextThreadLocal.get().getContainer();
		}
		return null;
	}

	/**
	 * Get current ContainerContext object.
	 */
	public static ContainerContext getContainerContext() {
		return containerContextThreadLocal.get();
	}

	public static void setContainerContext(Container container) {
		ContainerContext ctx = new ContainerContext();
		ctx.container = container;
		containerContextThreadLocal.set(ctx);
	}

	public static void removeContainerContext() {
		containerContextThreadLocal.remove();
	}
}
