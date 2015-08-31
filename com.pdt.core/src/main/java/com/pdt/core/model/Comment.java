/**
 *<p>
 *</p>
 * @author hungpx
 * @since
 */
package com.pdt.core.model;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author august
 *
 */
@Entity
@Table(name = "pdt_comment")
@DiscriminatorColumn(name = "cmtType")
public class Comment extends Message {

	/** */
	private static final long serialVersionUID = -2131923143403310941L;

	private String email;
	private String address;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
