/**
 * <p>
 * </p>
 *
 * @author hungpx
 * @since
 */
package com.pdt.service;

import java.util.List;

import com.pdt.core.model.City;
import com.pdt.core.model.Hotel;
import com.pdt.core.model.RegionType;
import com.pdt.web.view.model.VHotel;

/**
 * @author august
 *
 */
public interface PdtDestinationService {

	/**
	 * @return
	 */
	List<Hotel> getAllHotelWithCity();

	/**
	 * @param id
	 * @param realPath
	 * @return
	 */
	List<VHotel> getVHotelsByCityId(long id, String realPath);

	/**
	 * @param region
	 * @param exceptedCityId
	 * @return
	 */
	List<City> getRelatedCitiesByRegion(RegionType region, Long exceptedCityId);

	List<Hotel> getRelatedHotelsByCity(long cityId, long relatedToHotelId);

}
