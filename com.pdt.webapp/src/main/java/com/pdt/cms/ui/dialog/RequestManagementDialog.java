/**
 * <p>
 * </p>
 *
 * @author hungpx
 * @since
 */
package com.pdt.cms.ui.dialog;

import com.pdt.cms.ui.CommandButton.CommandButtonType;
import com.pdt.cms.ui.Dialog;
import com.pdt.core.model.Request;
import com.vaadin.ui.AbstractLayout;

/**
 * @author august
 *
 */
public class RequestManagementDialog<T extends Request> extends Dialog<T> {

	/** */
	private static final long serialVersionUID = -1323896856412048799L;
	private static final CommandButtonType[] BUTTONS = new CommandButtonType[] { CommandButtonType.SAVE,
			CommandButtonType.CLOSE };

	public RequestManagementDialog(T model, String caption, CloseListener closeListener) {
		super(model, BUTTONS, caption, closeListener);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.pdt.cms.ui.Dialog#constructLayout()
	 */
	@Override
	protected AbstractLayout constructLayout() {
		// TODO Auto-generated method stub
		return null;
	}

}
