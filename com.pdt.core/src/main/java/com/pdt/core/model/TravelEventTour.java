/**
 *<p>
 *</p>
 * @author hungpx
 * @since
 */
package com.pdt.core.model;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author hungpx
 *
 */
@Entity
@DiscriminatorValue(value = "3")
@Cacheable(true)
public class TravelEventTour extends TourAssociation {

	/** */
	private static final long serialVersionUID = -8925533700920565808L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "eventId")
	private TravelEvent event;
	@Column(nullable = true, insertable = false, updatable = false)
	private long eventId;

	/**
	 * Construct an association between tour and event
	 *
	 * @param tour
	 * @param travelEvent
	 */
	public TravelEventTour(Tour tour, final TravelEvent travelEvent) {
		super(tour);
		this.event = travelEvent;
	}

	public long getEventId() {
		return eventId;
	}

	public void setEvent(TravelEvent event) {
		this.event = event;
		this.eventId = event.getId();
	}
}
