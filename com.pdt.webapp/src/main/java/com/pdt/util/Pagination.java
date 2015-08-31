/**
 *<p>
 *</p>
 * @author hungpx
 * @since
 */
package com.pdt.util;

/**
 * Pojo class that store paging information.
 *
 * @author august
 *
 */
public class Pagination {
	private int totalElements;
	private int currentPage = 1;
	private int numberOfPage;

	public Pagination(int totalElements, int currentIndex) {
		this.totalElements = totalElements;
		this.currentPage = currentIndex;
		numberOfPage = totalElements / Static.PAGE_SIZE + (totalElements % Static.PAGE_SIZE > 0 ? 1 : 0);
	}

	public int getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(int totalElements) {
		this.totalElements = totalElements;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentIndex) {
		this.currentPage = currentIndex;
	}

	public int getNumberOfPage() {
		return numberOfPage;
	}

	public boolean hasNext() {
		return currentPage * Static.PAGE_SIZE < totalElements;
	}

	public boolean hasPrevious() {
		return currentPage > Static.PAGE_SIZE;
	}

	public boolean isPaged() {
		return totalElements > Static.PAGE_SIZE;
	}

	/**
	 * Check whether input page is valid or not.
	 *
	 * @param page
	 * @return
	 */
	public boolean isValidPage(int page) {
		// 1,2,3,4,5,6 - 7,8,9,10
		//
		// p = 1; 1 < p*6=6 < total=10
		// p = 2; 1 < p*6=12 & 12%10=2 < 6
		return page >= 1 && page <= numberOfPage;
	}
}
