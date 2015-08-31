/**
 * <p>
 * </p>
 *
 * @author hungpx
 * @since
 */
package com.pdt.cms.ui.dialog;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.pdt.core.model.Tour;
import com.pdt.core.model.TravelEvent;
import com.pdt.core.service.CoreService;
import com.pdt.core.util.ServiceResolver;
import com.pdt.service.PdtTourService;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.VerticalLayout;

/**
 * @author august
 *
 */
public class TourEventSelection extends VerticalLayout {
	/** */
	private static final long serialVersionUID = 7834532615939044271L;

	private ListSelect listSelect;
	private Tour tour;

	public TourEventSelection(Tour t) {
		this.tour = t;
		init();
	}

	/**
	 *
	 */
	private void init() {
		List<TravelEvent> selectedEvents;
		listSelect = new ListSelect("Press Ctrl to select multiple events for tour");
		listSelect.setImmediate(true);
		listSelect.setNullSelectionAllowed(true);
		listSelect.setMultiSelect(true);

		CoreService coreService = ServiceResolver.findService(CoreService.class);
		List<TravelEvent> allEvents = coreService.findAll(TravelEvent.class);

		if (tour.getId() != null && tour.getId() != 0) {
			selectedEvents = ServiceResolver.findService(PdtTourService.class).getEventByTour(tour.getId());
		} else {
			selectedEvents = new ArrayList<TravelEvent>();
		}

		List<Long> selectedEventIds = Lists.transform(selectedEvents, new Function<TravelEvent, Long>() {

			@Override
			public Long apply(TravelEvent input) {
				return input.getId();
			}
		});

		Set<Long> s = new HashSet<Long>(selectedEventIds);

		IndexedContainer container = new IndexedContainer();
		listSelect.setContainerDataSource(container);
		for (TravelEvent event : allEvents) {
			container.addItem(event.getId());
			listSelect.setItemCaption(event.getId(), event.getName());
		}
		listSelect.setValue(s);
		addComponent(listSelect);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.data.Property.ValueChangeListener#valueChange(com.vaadin.data.Property.ValueChangeEvent)
	 */
	public List<Long> getSelectedEventIds() {
		Set<Long> vs = (Set<Long>) listSelect.getValue();
		return new ArrayList<Long>(vs);
	}
}
