package com.pdt.core.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * <p>
 * Base class for all data model that contain id (primary key field) and version.
 * <p>
 *
 * @author hungpx
 *
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
	private static final long serialVersionUID = -6226425576792550463L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Version
	private long version;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	@Version
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	@Column
	private boolean deleted;

	public final Long getId() {
		return id;
	}

	public final long getVersion() {
		return version;
	}

	public final boolean isDeleted() {
		return deleted;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof BaseEntity))
			return false;
		if (this == obj)
			return true;

		BaseEntity e = (BaseEntity) obj;

		if (e.getId() == null || getId() == null)
			return false;

		if (e.getId().equals(getId()))
			return true;

		return false;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

}
