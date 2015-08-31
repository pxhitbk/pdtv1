/**
 *<p>
 *</p>
 * @author hungpx
 * @since
 */
package com.pdt.web.view.model;

import java.io.Serializable;

import com.pdt.core.model.Tour;
import com.pdt.core.util.CommonValidation;

/**
 * @author august
 *
 */
public class VTour implements Serializable {

	/** */
	private static final long serialVersionUID = 7035131180417987043L;

	private final String thumbnail;
	private final Tour tour;

	public VTour(String thumbnail, Tour tour) {
		CommonValidation.forceNotNull("A tour cannot be null while initialize view tour (VTour)", tour);
		this.thumbnail = thumbnail;
		this.tour = tour;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public Tour getTour() {
		return tour;
	}

	public String getName() {
		return tour.getName();
	}

	public String getDescription() {
		return tour.getDescription();
	}

	public Long getId() {
		return tour.getId();
	}

	public String getSeoPath() {
		return tour.getSeoPath();
	}

}
