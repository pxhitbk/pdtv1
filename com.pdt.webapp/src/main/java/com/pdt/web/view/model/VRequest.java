/**
 * <p>
 * </p>
 *
 * @author hungpx
 * @since
 */
package com.pdt.web.view.model;

import com.pdt.core.model.Customer;

/**
 * @author august
 *
 */
public class VRequest {

	private long productId;
	private String productName;
	private int numberOfAdults;
	private int numberOfChildren2to9;
	private int numberOfChildrenLessThan2;
	private String plannedPeriod;
	private String expectedTime;
	private String subject;
	private String detail;
	private Customer customer;

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

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
}
