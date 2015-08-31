/**
 * <p>
 * </p>
 * 
 * @author hungpx
 * @since
 */
package com.pdt.webapp.ui.model;

import java.util.Map;

/**
 * @author hungpx
 *
 */
public class MenuItemModel {
	/** Region type map with cities <City.id, City.city> */
	// private Map<RegionType, Map<Long, String>> tourByRegions;
	/** map subject id and subject name */
	private Map<Long, String> tourBySubjects;
	/** map city id and city name */
	private Map<Long, String> hotelsByCities;
	private Map<Long, String> services;
	private Map<Long, String> tips;

	public MenuItemModel() {
	}

	public MenuItemModel(Map<Long, String> tourBySubjects, Map<Long, String> hotelsByCities,
			Map<Long, String> services, Map<Long, String> tips) {
		this.tourBySubjects = tourBySubjects;
		this.hotelsByCities = hotelsByCities;
		this.services = services;
		this.tips = tips;
	}

	public Map<Long, String> getTourBySubjects() {
		return tourBySubjects;
	}

	public void setTourBySubjects(Map<Long, String> tourBySubjects) {
		this.tourBySubjects = tourBySubjects;
	}

	public Map<Long, String> getHotelsByCities() {
		return hotelsByCities;
	}

	public void setHotelsByCities(Map<Long, String> hotelsByCities) {
		this.hotelsByCities = hotelsByCities;
	}

	public Map<Long, String> getServices() {
		return services;
	}

	public void setServices(Map<Long, String> services) {
		this.services = services;
	}

	public Map<Long, String> getTips() {
		return tips;
	}

	public void setTips(Map<Long, String> tips) {
		this.tips = tips;
	}
}
