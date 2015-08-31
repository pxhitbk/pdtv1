package com.pdt.core.model;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue(value = "2")
@Cacheable(true)
public class TravelSubjectTourAssociation extends TourAssociation {
	private static final long serialVersionUID = -8053631562539728628L;

	public static final String SUBJECT_REF_NAME = "subject";
	public static final String SUBJECTID_REF_NAME = "subjectId";

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "subjectId")
	private TravelSubject subject;
	@Column(nullable = true, insertable = false, updatable = false)
	private long subjectId;

	private String title;
	private String description;

	public TravelSubjectTourAssociation(final Tour tour, final TravelSubject travelSubject) {
		super(tour);
		this.subject = travelSubject;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getSubjectId() {
		return subjectId;
	}

	public void setSubject(TravelSubject subject) {
		this.subject = subject;
		this.subjectId = subject.getId();
	}
}
