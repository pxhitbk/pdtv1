/**
 *<p>
 *</p>
 * @author hungpx
 * @since
 */
package com.pdt.core.model;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * @author august
 *
 */
@MappedSuperclass
public class ImageAssociation extends BaseEntity {
	private static final long serialVersionUID = -8125932377339683119L;

	@JoinColumn(name = "imageId")
	@ManyToOne(fetch = FetchType.LAZY)
	private Image image;
	@Column(insertable = false, updatable = false)
	private long imageId;

	public ImageAssociation(final Image image) {
		this.image = image;
	}

	public long getImageId() {
		return imageId;
	}

	public void setImage(Image image) {
		this.image = image;
		imageId = image.getId();
	}
}
