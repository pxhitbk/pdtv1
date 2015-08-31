package com.pdt.core.model;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.pdt.core.util.Ultility;

@Entity
@Cacheable(true)
@Table(name = "pdt_travelsubject")
public class TravelSubject extends BaseEntity {
	private static final long serialVersionUID = -8973856402562950930L;

	private String name;
	private String seoPath;
	private boolean stick;
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private String description;

	public TravelSubject() {
	}

	public TravelSubject(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		if (name != null)
			setSeoPath(Ultility.removeAccents(name));
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

	public boolean isStick() {
		return stick;
	}

	public void setStick(boolean stick) {
		this.stick = stick;
	}
}
