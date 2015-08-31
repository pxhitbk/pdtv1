/**
 * <p>
 * </p>
 * 
 * @author hungpx
 * @since
 */
package com.pdt.web.view.model;

/**
 * @author august
 *
 */
public class VHomeQueryParams {
	private String subjectParam;
	private String regionParam;
	private boolean includeEvent;

	public String getSubjectParam() {
		return subjectParam;
	}

	public void setSubjectParam(String subjectParam) {
		this.subjectParam = subjectParam;
	}

	public String getRegionParam() {
		return regionParam;
	}

	public void setRegionParam(String regionParam) {
		this.regionParam = regionParam;
	}

	public boolean isIncludeEvent() {
		return includeEvent;
	}

	public void setIncludeEvent(boolean includeEvent) {
		this.includeEvent = includeEvent;
	}

}
