/**
 *<p>
 *</p>
 * @author hungpx
 * @since
 */
package com.pdt.core.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;

/**
 * @author august
 *
 */

@MappedSuperclass
public class Message extends BaseEntity {

	/** */
	private static final long serialVersionUID = -2140415846691763420L;

	private String author;
	private String receiver;
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private String content;
	private boolean isViewed;
	private Date lastViewedDate;

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isViewed() {
		return isViewed;
	}

	public void setViewed(boolean isViewed) {
		this.isViewed = isViewed;
	}

	public Date getLastViewedDate() {
		return lastViewedDate;
	}

	public void setLastViewedDate(Date lastViewedDate) {
		this.lastViewedDate = lastViewedDate;
	}
}
