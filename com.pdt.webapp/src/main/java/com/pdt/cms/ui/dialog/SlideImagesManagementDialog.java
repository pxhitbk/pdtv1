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
import com.pdt.cms.ui.form.SlideImagesDetailForm;
import com.pdt.core.service.CoreService;
import com.pdt.core.util.ServiceResolver;
import com.pdt.webapp.model.HomePageImage;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.VerticalLayout;

/**
 * @author august
 *
 */
public class SlideImagesManagementDialog extends Dialog<HomePageImage> {

	private static final CommandButtonType[] BUTTONS = new CommandButtonType[] { CommandButtonType.SAVE,
			CommandButtonType.CLOSE };

	private SlideImagesDetailForm slideImageDetailForm;

	/**
	 * @param modelType
	 * @param buttons
	 * @param model
	 * @param caption
	 * @param closeListener
	 */
	public SlideImagesManagementDialog(HomePageImage model, String caption, CloseListener closeListener) {
		super(HomePageImage.class, BUTTONS, model, caption, closeListener);
	}

	/** */
	private static final long serialVersionUID = 1038860500810989202L;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.pdt.cms.ui.Dialog#constructLayout()
	 */

	@Override
	protected AbstractLayout constructLayout() {
		VerticalLayout layout = new VerticalLayout();
		slideImageDetailForm = new SlideImagesDetailForm(getModel());
		layout.addComponent(slideImageDetailForm);
		return layout;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.pdt.cms.ui.Dialog#save()
	 */
	@Override
	public boolean save() {
		boolean commitSuccess = slideImageDetailForm.commit();
		if (commitSuccess) {
			CoreService coreService = ServiceResolver.findService(CoreService.class);
			HomePageImage image = slideImageDetailForm.getModel();
			if (image.getId() != null && image.getId() != 0) {
				coreService.update(image);
			} else {
				coreService.insert(image);
			}
			return true;
		}
		return false;
	}

}
