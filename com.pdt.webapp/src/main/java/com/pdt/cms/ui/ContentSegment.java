package com.pdt.cms.ui;

import com.vaadin.ui.AbstractLayout;

/**
 * <p>
 * Interface for content segment object. Content segment is an UI object that consist of an UI layout to show the
 * content and properties to draw content, common method to control it from out site.
 * </p>
 *
 * @author hungpx
 *
 */
public interface ContentSegment<L extends AbstractLayout> {

	String getName();

	int getOrder();

	/**
	 * initialize
	 */
	void init();

	void activate();

	void deactive();

	boolean isActive();

	void refresh();

	/**
	 * Get the vaadin UI layout that contain content segment.
	 *
	 * @return
	 */
	L getLayout();
}
