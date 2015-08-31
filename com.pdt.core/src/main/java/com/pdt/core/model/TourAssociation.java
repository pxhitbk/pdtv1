package com.pdt.core.model;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.pdt.core.util.CommonValidation;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "assoType")
@Table(name = "pdt_tourassociation")
@Cacheable(true)
public abstract class TourAssociation extends BaseEntity {
	private static final long serialVersionUID = -3905723912349768508L;

	public static final String TOUR_REF_NAME = "tour";
	public static final String TOURID_REF_NAME = "tourId";

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tourId")
	private Tour tour;
	@Column(nullable = false, insertable = false, updatable = false)
	private long tourId;

	public TourAssociation(final Tour tour) {
		CommonValidation.forceNotNull("Cannot construct a tour assocication with a NULL tour", tour);
		this.tour = tour;
	}

	public long getTourId() {
		return tourId;
	}

	public void setTour(Tour newTour) {
		this.tour = newTour;
		tourId = newTour.getId();
	}

}
