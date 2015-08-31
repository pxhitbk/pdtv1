package com.pdt.core.model;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

@Entity
@Table(name = "pdt_city")
@Cacheable(true)
@Audited
public class City extends BaseEntity {
	private static final long serialVersionUID = 8556954917632529453L;

	public static String CITY = "city";
	public static String REGION = "region";
	public static String COUNTRY = "country";

	private String city;
	private RegionType region;
	private String country;
	private String code;

	public City() {
	}

	public City(String city, RegionType region, String country) {
		this.city = city;
		this.region = region;
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public RegionType getRegion() {
		return region;
	}

	public void setRegion(RegionType region) {
		this.region = region;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
