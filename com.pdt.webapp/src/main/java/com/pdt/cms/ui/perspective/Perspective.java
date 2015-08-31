package com.pdt.cms.ui.perspective;

import com.pdt.cms.ui.model.GroupModel;
import com.pdt.cms.ui.model.PerspectiveModel;
import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.VerticalLayout;

/**
 * Perspective is kind of page. Each perspective play a role like a content node of website structure by wrapping the
 * content area.
 *
 * @author hungpx
 *
 */
public interface Perspective {
	void init();

	void activate();

	void deactivate();

	boolean isActive();

	GroupModel getGroup();

	VerticalLayout getSidebar();

	PerspectiveModel getModel();

	/**
	 * Get the container of a perspective that hold the side bar (if has any) and the content segment
	 *
	 * @return
	 */
	public AbstractComponentContainer getContainer();
}
