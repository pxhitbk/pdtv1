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
import com.pdt.cms.ui.form.HotelDetailForm;
import com.pdt.core.model.Hotel;
import com.pdt.core.service.CoreService;
import com.pdt.core.util.ServiceResolver;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.VerticalLayout;

/**
 * @author august
 *
 */
public class HotelManagementDialog extends Dialog<Hotel> {

	/** */
	private static final long serialVersionUID = -737723976986966732L;

	private static final CommandButtonType[] BUTTONS = new CommandButtonType[] { CommandButtonType.SAVE,
			CommandButtonType.CLOSE };

	private HotelDetailForm hotelDetailForm;
	private CoreService coreService = ServiceResolver.findService(CoreService.class);

	public HotelManagementDialog(Hotel model, String caption, CloseListener closeListener) {
		super(model, BUTTONS, caption, closeListener);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.pdt.cms.ui.Dialog#constructLayout()
	 */
	@Override
	protected AbstractLayout constructLayout() {
		VerticalLayout layout = new VerticalLayout();
		hotelDetailForm = new HotelDetailForm(getModel());

		layout.addComponent(hotelDetailForm);

		return layout;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.pdt.cms.ui.Dialog#save()
	 */
	@Override
	public boolean save() {
		boolean commitSuccess = hotelDetailForm.commit();
		if (!hotelDetailForm.isModified())
			return true;
		if (commitSuccess) {
			coreService.insertOrUpdate(hotelDetailForm.getModel());
			return true;
		}
		return false;
	}

}
