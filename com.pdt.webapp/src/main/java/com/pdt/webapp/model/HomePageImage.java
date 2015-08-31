/**
 * <p>
 * </p>
 *
 * @author hungpx
 * @since
 */
package com.pdt.webapp.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.pdt.core.model.Image;

/**
 * @author august
 *
 */
@Entity
@Table(name = "web_homeimages")
@DiscriminatorValue(value = "HOMESLICE")
public class HomePageImage extends Image {
	/** */
	private static final long serialVersionUID = 421192100370404879L;
	private String linkCaption;
	private String link;
	private @Transient String tempPath;

	public String getLinkCaption() {
		return linkCaption;
	}

	public void setLinkCaption(String linkCaption) {
		this.linkCaption = linkCaption;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getTempPath() {
		return tempPath;
	}

	public void setTempPath(String tempPath) {
		this.tempPath = tempPath;
	}
}
