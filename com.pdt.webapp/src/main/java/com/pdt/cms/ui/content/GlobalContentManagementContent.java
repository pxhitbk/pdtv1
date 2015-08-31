/**
 * <p>
 * </p>
 *
 * @author hungpx
 * @since
 */
package com.pdt.cms.ui.content;

import com.pdt.core.model.ContentType;

/**
 * @author august
 *
 */
public class GlobalContentManagementContent extends StaticContentManagementContent {

	/** */
	private static final long serialVersionUID = -9167653103682815434L;

	/**
	 * @param type
	 */
	public GlobalContentManagementContent() {
		super(ContentType.ABOUT_US);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pdt.cms.ui.ContentSegment#getName()
	 */
	@Override
	public String getName() {
		return "Thong tin chung";
	}
}
