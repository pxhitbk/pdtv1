package com.pdt.cms.ui.content;

import com.pdt.core.model.ContentType;

public class ServiceStaticContentManagement extends StaticContentManagementContent {

	/** */
	private static final long serialVersionUID = -3337198099247421433L;

	/**
	 * @param type
	 */
	public ServiceStaticContentManagement() {
		super(ContentType.SERVICES);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pdt.cms.ui.ContentSegment#getName()
	 */
	@Override
	public String getName() {
		return "Thong tin dich vu";
	}

}
