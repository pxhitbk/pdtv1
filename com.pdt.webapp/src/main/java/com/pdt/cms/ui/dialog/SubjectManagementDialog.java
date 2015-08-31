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
import com.pdt.cms.ui.form.SubjectDetailForm;
import com.pdt.core.model.TravelSubject;
import com.pdt.core.service.TourService;
import com.pdt.core.util.ServiceResolver;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.VerticalLayout;

/**
 * @author august
 *
 */
public class SubjectManagementDialog extends Dialog<TravelSubject> {

	private static final CommandButtonType[] BUTTONS = new CommandButtonType[] { CommandButtonType.SAVE,
			CommandButtonType.CLOSE };

	SubjectDetailForm subjectDetailForm;

	/**
	 * @param modelType
	 * @param buttons
	 * @param model
	 * @param caption
	 * @param closeListener
	 */
	public SubjectManagementDialog(TravelSubject model, String caption, CloseListener closeListener) {
		super(TravelSubject.class, BUTTONS, model, caption, closeListener);
	}

	/** */
	private static final long serialVersionUID = -4879682291374721645L;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.pdt.cms.ui.Dialog#constructLayout()
	 */
	@Override
	protected AbstractLayout constructLayout() {
		VerticalLayout layout = new VerticalLayout();
		subjectDetailForm = new SubjectDetailForm(getModel());
		layout.addComponent(subjectDetailForm);
		return layout;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pdt.cms.ui.Dialog#save()
	 */
	@Override
	public boolean save() {
		boolean commitSuccess = subjectDetailForm.commit();
		if (commitSuccess) {
			TourService service = ServiceResolver.findService(TourService.class);
			TravelSubject subject = subjectDetailForm.getModel();
			service.updateTravelSubject(subject);
			return true;
		}
		return false;
	}

}
