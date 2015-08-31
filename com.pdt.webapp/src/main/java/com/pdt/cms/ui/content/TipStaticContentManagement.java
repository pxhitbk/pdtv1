package com.pdt.cms.ui.content;

import com.pdt.core.model.ContentType;

public class TipStaticContentManagement extends StaticContentManagementContent {

	/** */
	private static final long serialVersionUID = -4349019227746384478L;

	/**
	 * @param type
	 */
	public TipStaticContentManagement() {
		super(ContentType.TIPS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pdt.cms.ui.ContentSegment#getName()
	 */
	@Override
	public String getName() {
		return "Thong tin huong dan";
	}
}
