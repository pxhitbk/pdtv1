package com.pdt.core.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author august
 *
 */
@Entity
@DiscriminatorValue(value = "TOUR")
public class TourRequest extends Request {

	/** */
	private static final long serialVersionUID = 484084706528613228L;

	private HotelLevel hotelLevel;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tourId")
	private Tour tour;
	@Column(nullable = true, insertable = false, updatable = false)
	private long tourId;

	public long getTourId() {
		return tourId;
	}

	public void setTour(Tour newTour) {
		this.tour = newTour;
		tourId = newTour.getId();
	}

	public HotelLevel getHotelLevel() {
		return hotelLevel;
	}

	public void setHotelLevel(HotelLevel hotelLevel) {
		this.hotelLevel = hotelLevel;
	}

	public Tour getTour() {
		return tour;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.pdt.core.model.Request#getType()
	 */
	@Override
	public Class<? extends Request> getType() {
		return TourRequest.class;
	}

}
