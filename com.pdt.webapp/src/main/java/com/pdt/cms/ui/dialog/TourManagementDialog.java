/**
 * <p>
 * </p>
 *
 * @author hungpx
 * @since
 */
package com.pdt.cms.ui.dialog;

import javax.transaction.Transactional;

import com.pdt.cms.data.model.TourImageDto;
import com.pdt.cms.ui.CommandButton.CommandButtonType;
import com.pdt.cms.ui.Dialog;
import com.pdt.cms.ui.form.TourDetailForm;
import com.pdt.cms.ui.form.TourImageUploadForm;
import com.pdt.core.model.Tour;
import com.pdt.core.util.ServiceResolver;
import com.pdt.service.PdtTourService;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

/**
 * Pop-up dialog to add new tour with associated images.
 *
 * @author august
 *
 */
@Transactional
public class TourManagementDialog extends Dialog<TourImageDto> {

	/** */
	private static final long serialVersionUID = 4240084638847758753L;

	private static final CommandButtonType[] COMMAND_BUTTONS = { CommandButtonType.SAVE, CommandButtonType.CLOSE };
	TourDetailForm tourDetailForm;
	TourImageUploadForm uploadImageTab;
	TourSubjectsSelection tourSubjectsSelection;
	TourEventSelection tourEventsSelection;

	/**
	 * @param modelType
	 * @param buttons
	 * @param model
	 */
	public TourManagementDialog(Class<TourImageDto> modelType, TourImageDto tourDto, String caption) {
		super(TourImageDto.class, COMMAND_BUTTONS, tourDto == null ? new TourImageDto(null, null) : tourDto, caption,
				null);
	}

	/**
	 * @param modelType
	 * @param buttons
	 * @param model
	 */
	public TourManagementDialog(Class<TourImageDto> modelType, TourImageDto tourDto, String caption,
			CloseListener closeListener) {
		super(TourImageDto.class, COMMAND_BUTTONS, tourDto == null ? new TourImageDto(null, null) : tourDto, caption,
				closeListener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pdt.cms.ui.Dialog#constructLayout()
	 */
	@Override
	protected AbstractLayout constructLayout() {
		// Construct tabsheet with tabs: Details, Image (thumbnail, photos)

		TabSheet tabsheet = new TabSheet();
		tourDetailForm = new TourDetailForm(getModel().getTour());
		uploadImageTab = new TourImageUploadForm(getModel().getImages());
		tourSubjectsSelection = new TourSubjectsSelection(getModel().getTour());
		tourEventsSelection = new TourEventSelection(getModel().getTour());

		tabsheet.addTab(tourDetailForm, "Detail");
		tabsheet.addTab(uploadImageTab, "Upload images");
		tabsheet.addTab(tourSubjectsSelection, "Select subjects");
		tabsheet.addTab(tourEventsSelection, "Select events");

		// Some basic content for the window
		VerticalLayout content = new VerticalLayout();
		content.setMargin(true);

		content.addComponent(tabsheet);
		return content;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.pdt.cms.ui.Dialog#save()
	 */
	@Transactional
	@Override
	public boolean save() {
		if (tourDetailForm.commit()) {
			PdtTourService service = ServiceResolver.findService(PdtTourService.class);
			TourImageDto dto = new TourImageDto(tourDetailForm.getTour(), uploadImageTab.commit());
			Tour t = service.updateTourImages(dto);
			service.updateTourSubjects(t, tourSubjectsSelection.getSelectedSubjectIds());
			service.updateTourEvents(t, tourEventsSelection.getSelectedEventIds());
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.pdt.cms.ui.Dialog#delete()
	 */
	@Override
	public boolean delete() {
		PdtTourService service = ServiceResolver.findService(PdtTourService.class);
		service.removeTour(getModel().getTour());
		return true;
	}
}
