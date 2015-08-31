package com.pdt.cms.ui.content;

import com.pdt.cms.ui.AbstractContentSegment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class DashboardContent extends AbstractContentSegment {

	public DashboardContent() {
		super(new VerticalLayout());
		getLayout()
				.addComponent(
						new Label(
								"Dashboard currently umimplements. This should contain all notification to admin like feedback, request and other information that updated by customer"));
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public void init() {
	}

	@Override
	public void refresh() {

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.pdt.cms.ui.ContentSegment#getOrder()
	 */
	@Override
	public int getOrder() {
		return 0;
	}
}
