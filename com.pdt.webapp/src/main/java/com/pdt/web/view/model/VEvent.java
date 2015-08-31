/**
 * <p>
 * </p>
 *
 * @author hungpx
 * @since
 */
package com.pdt.web.view.model;

import java.io.Serializable;

import com.pdt.core.model.City;
import com.pdt.core.model.Destination;
import com.pdt.core.model.TravelEvent;

/**
 * @author august
 *
 */
public class VEvent implements Serializable {

	/** */
	private static final long serialVersionUID = -4210969977921140804L;

	private TravelEvent event;
	private Destination destination;
	private City city;

	public VEvent(TravelEvent event, Destination destination, City city) {
		this.event = event;
		this.destination = destination;
		this.city = city;
	}

	public TravelEvent getEvent() {
		return event;
	}

	public void setEvent(TravelEvent event) {
		this.event = event;
	}

	public Destination getDestination() {
		return destination;
	}

	public void setDestination(Destination destination) {
		this.destination = destination;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}
}
