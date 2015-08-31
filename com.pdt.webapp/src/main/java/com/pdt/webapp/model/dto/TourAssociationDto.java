/**
 *<p>
 *</p>
 * @author hungpx
 * @since
 */
package com.pdt.webapp.model.dto;

import com.pdt.core.model.Tour;
import com.pdt.core.model.TourAssociation;
import com.pdt.core.model.TravelSubject;

/**
 * @author hungpx
 *
 */
public class TourAssociationDto {
	private final TourAssociation tourAssociation;
	private final Tour tour;
	private final TravelSubject subject;

	public TourAssociationDto(TourAssociation tourAssociation, Tour tour, TravelSubject subjects) {
		this.tourAssociation = tourAssociation;
		this.tour = tour;
		this.subject = subjects;
	}

	public TourAssociation getTourAssociation() {
		return tourAssociation;
	}

	public Tour getTour() {
		return tour;
	}

	public TravelSubject getSubject() {
		return subject;
	}

	public Long getTourId() {
		return tour.getId();
	}

	public Long getSubjectId() {
		return subject.getId();
	}
}
