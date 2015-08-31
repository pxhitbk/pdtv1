package com.pdt.core.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author august
 *
 */
@Entity
@Table(name = "pdt_customer")
public class Customer extends BaseEntity {

	/** */
	private static final long serialVersionUID = 235843436688730975L;

	private String fullName;
	private String nationality;
	private String address;
	private String phone;
	private String email;
	private boolean anonymous;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAnonymous() {
		return anonymous;
	}

	public void setAnonymous(boolean anonymous) {
		this.anonymous = anonymous;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
