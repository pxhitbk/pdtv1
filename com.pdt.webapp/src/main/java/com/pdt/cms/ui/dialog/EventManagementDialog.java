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
import com.pdt.cms.ui.form.DestinationSelectForm;
import com.pdt.cms.ui.form.EventDetailForm;
import com.pdt.core.model.City;
import com.pdt.core.model.Destination;
import com.pdt.core.model.TravelEvent;
import com.pdt.core.service.CoreService;
import com.pdt.core.util.ServiceResolver;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

/**
 * @author august
 *
 */
public class EventManagementDialog extends Dialog<TravelEvent> {

	/** */
	private static final long serialVersionUID = -4879682291374721645L;

	private static final CommandButtonType[] BUTTONS = new CommandButtonType[] { CommandButtonType.SAVE,
			CommandButtonType.CLOSE };
	private EventDetailForm eventDetailForm;
	private DestinationSelectForm destinationSelectForm;

	/**
	 * @param modelType
	 * @param buttons
	 * @param model
	 * @param caption
	 * @param closeListener
	 */
	public EventManagementDialog(TravelEvent model, String caption, CloseListener closeListener) {
		super(TravelEvent.class, BUTTONS, model, caption, closeListener);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.pdt.cms.ui.Dialog#constructLayout()
	 */
	@Override
	protected AbstractLayout constructLayout() {
		VerticalLayout layout = new VerticalLayout();
		eventDetailForm = new EventDetailForm(getModel());
		CoreService coreService = ServiceResolver.findService(CoreService.class);
		Destination dest = null;
		if (getModel() != null && getModel().getId() != 0)
			dest = coreService.findById(Destination.class, getModel().getId());
		destinationSelectForm = new DestinationSelectForm(dest);
		TabSheet tabs = new TabSheet();
		layout.addComponent(tabs);
		tabs.addTab(eventDetailForm, "Cập nhật sự kiện");
		tabs.addTab(destinationSelectForm, "Địa điểm");

		return layout;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.pdt.cms.ui.Dialog#save()
	 */
	@Override
	public boolean save() {
		boolean commitSuccess = eventDetailForm.commit() && destinationSelectForm.commit();
		if (!eventDetailForm.isModified() && !destinationSelectForm.isModified())
			return true;
		if (commitSuccess) {
			CoreService coreService = ServiceResolver.findService(CoreService.class);
			if (eventDetailForm.isModified()) {
				Destination d = destinationSelectForm.getModel();
				TravelEvent e = eventDetailForm.getModel();
				if (destinationSelectForm.isModified()) {
					City city = d.getCity();
					if (d.getCityId() == null || d.getCityId() == 0) {
						if (city.getCity() == null && city.getRegion() == null) {
							city = null;
						} else {
							city = coreService.insert(city);
						}
						d.setCity(city);
					}
					d = coreService.insertOrUpdate(d);
					e.setDestination(d);
				}

				coreService.insertOrUpdate(e);
			} else {
				Destination d = destinationSelectForm.getModel();
				if (destinationSelectForm.isModified()) {
					d = coreService.insertOrUpdate(d);
				}
			}
			return true;
		}
		return false;
	}

}
