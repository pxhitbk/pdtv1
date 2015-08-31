/**
 * <p>
 * </p>
 *
 * @author hungpx
 * @since
 */
package com.pdt.core.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.pdt.core.util.Ultility;

/**
 * @author hungpx
 *
 */
@Entity
@Cacheable(true)
@Table(name = "pdt_travelevent")
public class TravelEvent extends BaseEntity {

	/** */
	private static final long serialVersionUID = -1248035173360230129L;

	private String name;
	private Date beginDate;
	private Date endDate;
	private EventScope eventScope;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "destinationId")
	private Destination destination;
	@Column(nullable = true, insertable = false, updatable = false)
	private Long destinationId;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	private String description;
	private String seoPath;

	/**
	 * Default constructor
	 */
	public TravelEvent() {
	}

	/**
	 * Construct travel event with basic information
	 *
	 * @param name
	 * @param beginDate
	 * @param endDate
	 */
	public TravelEvent(String name, Date beginDate, Date endDate) {
		super();
		this.name = name;
		this.beginDate = beginDate;
		this.endDate = endDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		if (name != null)
			setSeoPath(Ultility.removeAccents(name));
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSeoPath() {
		return seoPath;
	}

	public void setSeoPath(String seoPath) {
		this.seoPath = seoPath;
	}

	public EventScope getEventScope() {
		return eventScope;
	}

	public void setEventScope(EventScope eventScope) {
		this.eventScope = eventScope;
	}

	public Long getDestinationId() {
		return destinationId;
	}

	public void setDestination(Destination destination) {
		this.destination = destination;
	}

	public Destination getDestination() {
		return destination;
	}
}
