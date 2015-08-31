/**
 * <p>
 * </p>
 *
 * @author hungpx
 * @since
 */
package com.pdt.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.pdt.core.model.City;
import com.pdt.core.model.Hotel;
import com.pdt.core.model.Image;
import com.pdt.core.model.RegionType;
import com.pdt.core.service.CoreService;
import com.pdt.service.PdtDestinationService;
import com.pdt.util.PdtUtils;
import com.pdt.util.Static;
import com.pdt.web.view.model.VHotel;

/**
 * @author august
 *
 */
@Transactional
public class PdtDestinationServiceImpl implements PdtDestinationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PdtDestinationServiceImpl.class);
	@Autowired
	private CoreService coreService;

	private static final String FETCH_HOTELS_WITH_CITY = "SELECT h FROM " + Hotel.class.getName()
			+ " h join fetch h.city";
	private static final String GET_RELATED_HOTELS_BY_CITY = "SELECT distinct(h) FROM " + Hotel.class.getName()
			+ " h WHERE h.cityId = :cid AND h.id <> :hid ORDER BY h.name";

	private static final String GET_HOTEL_BY_CITY_ID = "SELECT h FROM " + Hotel.class.getName()
			+ " h join fetch h.thumbnail t WHERE h.cityId = :cid";

	private static final String GET_RELATED_CITIES_BY_REGION = "SELECT distinct (c) FROM " + Hotel.class.getName()
			+ " d INNER JOIN d.city c WHERE c.region = :region AND c.id <> :cid ORDER BY c.city";

	@Override
	public List<Hotel> getAllHotelWithCity() {
		List<Hotel> events = coreService.getEntityManager().createQuery(FETCH_HOTELS_WITH_CITY, Hotel.class)
				.getResultList();

		return events;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.pdt.service.PdtDestinationService#getVHotelsById(long, java.lang.String)
	 */
	@Override
	public List<VHotel> getVHotelsByCityId(long id, String path) {
		List<Hotel> hotels = coreService.getEntityManager().createQuery(GET_HOTEL_BY_CITY_ID, Hotel.class)
				.setParameter("cid", id).getResultList();

		return getVHotelsFromHotels(hotels, path);
	}

	/**
	 * @param tours
	 * @param path
	 * @return
	 */
	private List<VHotel> getVHotelsFromHotels(List<Hotel> hotels, String path) {
		List<VHotel> vhotels = new ArrayList<VHotel>();

		File file = new File(path);
		if (!file.exists()) {
			if (file.mkdir()) {
				LOGGER.info("Directory is created!");
			} else {
				LOGGER.warn("Failed to create directory!");
			}
		}

		for (Hotel h : hotels) {
			Image thumb = h.getThumbnail();
			if (thumb != null && thumb.getData() != null) {
				try {
					PdtUtils.writingImageFile(thumb, path);
				} catch (IOException e) {
					// e.printStackTrace();
					LOGGER.error("Cannot writing thumbnail when retrieving the hotel " + h.getName(), e);
				}
			}

			VHotel vt = new VHotel(Static.UPLOAD_THUMBNAIL_URI_PATH + thumb.getFileName(), h);
			vhotels.add(vt);
		}
		return vhotels;
	}

	@Override
	public List<City> getRelatedCitiesByRegion(RegionType region, Long exceptedCityId) {
		List<City> cities = coreService.getEntityManager().createQuery(GET_RELATED_CITIES_BY_REGION, City.class)
				.setParameter("region", region).setParameter("cid", exceptedCityId).setMaxResults(12).getResultList();
		return cities;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.pdt.service.PdtDestinationService#getRelatedHotelsByCity(long, long)
	 */
	@Override
	public List<Hotel> getRelatedHotelsByCity(long cityId, long relatedToHotelId) {

		List<Hotel> hotels = coreService.getEntityManager().createQuery(GET_RELATED_HOTELS_BY_CITY, Hotel.class)
				.setParameter("cid", cityId).setParameter("hid", relatedToHotelId).setMaxResults(12).getResultList();
		return hotels;
	}

}
