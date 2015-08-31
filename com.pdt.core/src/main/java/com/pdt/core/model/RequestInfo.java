/**
 * <p>
 * </p>
 *
 * @author hungpx
 * @since
 */
package com.pdt.core.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Information of the request was update by system or system user to response to customer request.
 *
 * @author august
 *
 */
@Entity
@Table(name = "pdt_requestinfo")
public class RequestInfo extends BaseEntity {

	/** */
	private static final long serialVersionUID = 5520511289953406041L;

	private double paid;
	private double planedPrice;
	private double dealPrice;
	private String note;
	private PaymentMethod paymentMethod;
	private RequestStatus status;

	public RequestInfo() {
	}

	public RequestInfo(RequestStatus status) {
		this.status = status;
	}

	public double getPaid() {
		return paid;
	}

	public void setPaid(double paid) {
		this.paid = paid;
	}

	public double getPlanedPrice() {
		return planedPrice;
	}

	public void setPlanedPrice(double planedPrice) {
		this.planedPrice = planedPrice;
	}

	public double getDealPrice() {
		return dealPrice;
	}

	public void setDealPrice(double dealPrice) {
		this.dealPrice = dealPrice;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public RequestStatus getStatus() {
		return status;
	}

	public void setStatus(RequestStatus status) {
		this.status = status;
	}
}
