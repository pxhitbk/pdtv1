package com.pdt.cms.ui;

import com.pdt.core.util.CommonValidation;
import com.vaadin.ui.AbstractLayout;

public abstract class AbstractContentSegment<L extends AbstractLayout> implements ContentSegment<L> {

	private boolean isActive;
	private final L layout;

	public AbstractContentSegment(L layout) {
		CommonValidation.forceNotNull("Layout cannot be null", layout);
		this.layout = layout;
	}

	public void setActive(boolean active) {
		this.isActive = active;
	}

	@Override
	public boolean isActive() {
		return isActive;
	}

	@Override
	public void activate() {
		isActive = true;
	}

	@Override
	public void deactive() {
		isActive = true;
	}

	@Override
	public L getLayout() {
		return layout;
	}
}
