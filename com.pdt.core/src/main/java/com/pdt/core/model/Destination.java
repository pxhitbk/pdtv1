package com.pdt.core.model;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

@Entity
@Table(name = "pdt_destination")
@Cacheable(true)
@DiscriminatorColumn(name = "destType")
@Audited
public class Destination extends BaseEntity {

	private static final long serialVersionUID = 4993027253227549715L;
	public static final String DETAIL_ADDRESS = "detailAddress";

	private String detailAddress;
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private String description;

	@NotAudited
	@Column(nullable = true, updatable = false, insertable = false)
	private Long cityId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cityId")
	private City city;

	public Destination() {
	}

	public Destination(String detailAddress, City city) {
		this.detailAddress = detailAddress;
		this.city = city;
	}

	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCity(City city) {
		this.city = city;
		// cityId = city.getId();
	}

	public City getCity() {
		return city;
	}
}
