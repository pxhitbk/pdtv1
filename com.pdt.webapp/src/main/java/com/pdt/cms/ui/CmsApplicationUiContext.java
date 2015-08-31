/**
 *<p>
 *</p>
 * @author hungpx
 * @since
 */
package com.pdt.cms.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pdt.cms.ui.perspective.Perspective;
import com.vaadin.server.Page;

/**
 * @author hungpx
 *
 */
public class CmsApplicationUiContext {
	private final Logger LOGGER = LoggerFactory.getLogger(CmsApplicationUiContext.class);

	void setActivePerspective(Perspective p) {
		Page.getCurrent().setTitle(p.getModel().getTitle());
		Page.getCurrent().setUriFragment(p.getModel().getPath());
	}

	public Perspective getActivePerspective() {
		if (Page.getCurrent().getUriFragment() == null) {
			LOGGER.warn("Wrong access. Missing uri fragment.");
			return null;
		}
		return PerspectiveDefinition.DECLARED_PERSPECTIVES.get(Page.getCurrent().getUriFragment());
	}
}
