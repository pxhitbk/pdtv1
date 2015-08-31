package com.pdt.core.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author hungpx
 *
 */
@Entity
@Table(name = "pdt_request")
@DiscriminatorColumn(name = "reqType")
public abstract class Request extends BaseEntity {
	private static final long serialVersionUID = -4653952836465909443L;

	private int numberOfAdults;
	private int numberOfChildren2to9;
	private int numberOfChildrenLessThan2;
	private String plannedPeriod;
	private String expectedTime;
	private String subject;
	private String detail;

	@Column(insertable = false, updatable = false, nullable = true)
	private long requestInfoId;
	@JoinColumn(name = "requestInfoId")
	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE })
	private RequestInfo requestInfo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customerId")
	private Customer customer;
	@Column(nullable = false, insertable = false, updatable = false)
	private long customerId;

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
		customerId = customer.getId();
	}

	public Customer getCustomer() {
		return customer;
	}

	public long getRequestInfoId() {
		return requestInfoId;
	}

	public void setRequestInfo(RequestInfo requestInfo) {
		this.requestInfo = requestInfo;
		requestInfoId = requestInfo.getId();
	}

	public int getNumberOfAdults() {
		return numberOfAdults;
	}

	public void setNumberOfAdults(int numberOfAdults) {
		this.numberOfAdults = numberOfAdults;
	}

	public int getNumberOfChildren2to9() {
		return numberOfChildren2to9;
	}

	public void setNumberOfChildren2to9(int numberOfChildren2to9) {
		this.numberOfChildren2to9 = numberOfChildren2to9;
	}

	public int getNumberOfChildrenLessThan2() {
		return numberOfChildrenLessThan2;
	}

	public void setNumberOfChildrenLessThan2(int numberOfChildrenLessThan2) {
		this.numberOfChildrenLessThan2 = numberOfChildrenLessThan2;
	}

	public String getPlannedPeriod() {
		return plannedPeriod;
	}

	public void setPlannedPeriod(String plannedPeriod) {
		this.plannedPeriod = plannedPeriod;
	}

	public String getExpectedTime() {
		return expectedTime;
	}

	public void setExpectedTime(String expectedTime) {
		this.expectedTime = expectedTime;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getTotalPerson() {
		return getNumberOfAdults() + getNumberOfChildren2to9() + getNumberOfChildrenLessThan2();
	}

	public abstract Class<? extends Request> getType();
}
