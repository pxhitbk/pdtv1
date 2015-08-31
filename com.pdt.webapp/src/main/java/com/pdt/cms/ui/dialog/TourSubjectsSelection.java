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
import com.pdt.core.model.TravelSubject;
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
public class TourSubjectsSelection extends VerticalLayout /* implements ValueChangeListener */{

	/** */
	private static final long serialVersionUID = 7834532615939044271L;

	private ListSelect listSelect;
	private Tour tour;

	public TourSubjectsSelection(Tour t) {
		this.tour = t;
		init();
	}

	/**
	 *
	 */
	private void init() {
		List<TravelSubject> selectedSubject;
		listSelect = new ListSelect("Press Ctrl to select multiple subjects for tour");
		listSelect.setImmediate(true);
		listSelect.setNullSelectionAllowed(true);
		listSelect.setMultiSelect(true);

		CoreService coreService = ServiceResolver.findService(CoreService.class);
		List<TravelSubject> allSubjects = coreService.findAll(TravelSubject.class);

		if (tour.getId() != null && tour.getId() != 0) {
			selectedSubject = ServiceResolver.findService(PdtTourService.class).getSubjectByTour(tour.getId());
		} else {
			selectedSubject = new ArrayList<TravelSubject>();
		}

		List<Long> selectedSubjectIds = Lists.transform(selectedSubject, new Function<TravelSubject, Long>() {

			@Override
			public Long apply(TravelSubject input) {
				return input.getId();
			}
		});

		Set<Long> s = new HashSet<Long>(selectedSubjectIds);

		IndexedContainer container = new IndexedContainer();
		listSelect.setContainerDataSource(container);
		for (TravelSubject subject : allSubjects) {
			container.addItem(subject.getId());
			listSelect.setItemCaption(subject.getId(), subject.getName());
			// if (selectedSubject.contains(subject)) {
			// listSelect.setValue(subject.getId());
			// }
		}
		listSelect.setValue(s);
		addComponent(listSelect);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.data.Property.ValueChangeListener#valueChange(com.vaadin.data.Property.ValueChangeEvent)
	 */
	public List<Long> getSelectedSubjectIds() {
		Set<Long> vs = (Set<Long>) listSelect.getValue();
		return new ArrayList<Long>(vs);
	}
}
