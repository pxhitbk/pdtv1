package com.pdt.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

@Entity
@Table(name="adt_tourdest")
@Audited
public class TourDestination extends BaseEntity {
	private static final long serialVersionUID = 3130670307693860214L;

	private double numberOfDay;
	private double numberOfNight;
	
	@Column(nullable=false, insertable=false, updatable=false)
	private Long destinationId;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="destinationId")
	private Destination destination;
	
	@Column(nullable=false, insertable=false, updatable=false)
	private Long tourId;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="tourId")
	private Tour tour;
	
	public TourDestination() {}

	public TourDestination(double numberOfDay, double numberOfNight,
			Destination destination, Tour tour) {
		super();
		this.numberOfDay = numberOfDay;
		this.numberOfNight = numberOfNight;
		this.destination = destination;
		this.tour = tour;
	}

	public double getNumberOfDay() {
		return numberOfDay;
	}
	public void setNumberOfDay(double numberOfDay) {
		this.numberOfDay = numberOfDay;
	}
	public double getNumberOfNight() {
		return numberOfNight;
	}
	public void setNumberOfNight(double numberOfNight) {
		this.numberOfNight = numberOfNight;
	}
	public Long getDestinationId() {
		return destinationId;
	}
	public Long getTourId() {
		return tourId;
	}
	public void setDestination(Destination destination) {
		this.destination = destination;
	}
	public void setTour(Tour tour) {
		this.tour = tour;
		tourId = tour.getId();
	}
}
