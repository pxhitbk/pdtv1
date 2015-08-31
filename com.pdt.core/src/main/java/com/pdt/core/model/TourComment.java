/**
 *<p>
 *</p>
 * @author hungpx
 * @since
 */
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
@DiscriminatorValue(value = "1")
public class TourComment extends Comment {

	/** */
	private static final long serialVersionUID = 1075038070391193485L;

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
}
