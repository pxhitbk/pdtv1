/**
 *<p>
 *</p>
 * @author hungpx
 * @since
 */
package com.pdt.cms.data.model;

import java.util.List;

import com.pdt.core.model.Image;
import com.pdt.core.model.Tour;
import com.pdt.core.util.CommonValidation;

/**
 * @author hungpx
 *
 */
public class TourImageDto {
	private final Tour tour;
	private List<Image> images;

	public TourImageDto(final Tour tour, List<Image> images) {
		CommonValidation.forceNotNull("Tour cannot be null", tour);
		this.tour = tour;
		this.images = images;
	}

	public Tour getTour() {
		return tour;
	}

	public List<Image> getImages() {
		return images;
	}

	public Long getId() {
		return tour.getId();
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}
}
