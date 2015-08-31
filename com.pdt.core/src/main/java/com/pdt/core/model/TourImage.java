/**
 *<p>
 *</p>
 * @author hungpx
 * @since
 */
package com.pdt.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author august
 *
 */
@Entity
@Table(name = "pdt_tourimg")
public class TourImage extends ImageAssociation {

	private static final long serialVersionUID = 895177005063515099L;

	@JoinColumn(name = "tourId")
	@ManyToOne(fetch = FetchType.LAZY)
	private Tour tour;
	@Column(insertable = false, updatable = false)
	private long tourId;

	/**
	 * @param image
	 */
	public TourImage(Tour tour, Image image) {
		super(image);
		this.tour = tour;
		tourId = tour.getId();
	}

	public long getTourId() {
		return tourId;
	}

	public void setTour(Tour tour) {
		this.tour = tour;
	}
}
