/**
 * <p>
 * </p>
 *
 * @author hungpx
 * @since
 */
package com.pdt.core.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author august
 *
 */
@Entity
@DiscriminatorValue(value = "CUSTOUR")
public class CustomTourRequest extends Request {
	/** */
	private static final long serialVersionUID = 1568393845272330721L;
	private String expectedArrivalDate;
	private int numberOfDay;
	private int numberOfCouple;
	private int numberOfparticipantMinors;
	private String accommodation;
	private String meal;
	private String places;
	private String tourTypes;
	private String transportTypes;

	public String getExpectedArrivalDate() {
		return expectedArrivalDate;
	}

	public void setExpectedArrivalDate(String expectedArrivalDate) {
		this.expectedArrivalDate = expectedArrivalDate;
	}

	public int getNumberOfDay() {
		return numberOfDay;
	}

	public void setNumberOfDay(int numberOfDay) {
		this.numberOfDay = numberOfDay;
	}

	public int getNumberOfCouple() {
		return numberOfCouple;
	}

	public void setNumberOfCouple(int numberOfCouple) {
		this.numberOfCouple = numberOfCouple;
	}

	public int getNumberOfparticipantMinors() {
		return numberOfparticipantMinors;
	}

	public void setNumberOfparticipantMinors(int numberOfparticipantMinors) {
		this.numberOfparticipantMinors = numberOfparticipantMinors;
	}

	public String getAccommodation() {
		return accommodation;
	}

	public void setAccommodation(String accommodation) {
		this.accommodation = accommodation;
	}

	public String getMeal() {
		return meal;
	}

	public void setMeal(String meal) {
		this.meal = meal;
	}

	public String getTourTypes() {
		return tourTypes;
	}

	public void setTourTypes(String tourTypes) {
		this.tourTypes = tourTypes;
	}

	public String getPlaces() {
		return places;
	}

	public void setPlaces(String places) {
		this.places = places;
	}

	public String getTransportTypes() {
		return transportTypes;
	}

	public void setTransportTypes(String transportTypes) {
		this.transportTypes = transportTypes;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.pdt.core.model.Request#getType()
	 */
	@Override
	public Class<? extends Request> getType() {
		return CustomTourRequest.class;
	}
}