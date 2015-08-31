/**
 * <p>
 * </p>
 *
 * @author hungpx
 * @since
 */
package com.pdt.service;

import java.util.List;

import com.pdt.cms.data.model.TourImageDto;
import com.pdt.core.model.Album;
import com.pdt.core.model.Articles;
import com.pdt.core.model.Comment;
import com.pdt.core.model.Image;
import com.pdt.core.model.RegionType;
import com.pdt.core.model.Tour;
import com.pdt.core.model.TravelEvent;
import com.pdt.core.model.TravelSubject;
import com.pdt.web.view.model.VEvent;
import com.pdt.web.view.model.VTour;
import com.pdt.webapp.ui.model.MenuItemModel;

/**
 * @author hungpx
 *
 */
/**
 * Common services relate to Tour module
 *
 * @author hungpx
 *
 */
public interface PdtTourService {

	MenuItemModel getMenuItemModel();

	/**
	 * Insert or update tour with all associated images.
	 * @param tourImgs
	 * @return success inserted tour
	 */
	Tour updateTourImages(TourImageDto tourImgs);

	/**
	 * Remove tour and all associated reference to subject, event. This also remove all associated images.
	 *
	 * @param tour
	 */
	void removeTour(Tour tour);

	/**
	 * @param albumName
	 * @param createIfNotExist
	 * @return
	 */
	Album findAlbumByName(String albumName, boolean createIfNotExist);

	/**
	 * Get all images associated with a tour
	 *
	 * @param tourId
	 * @return
	 */
	List<Image> getImagesByTour(Long tourId);

	/**
	 * @param region
	 * @param path
	 * @return
	 */
	List<VTour> getTourByRegion(RegionType region, String path);

	/**
	 * @param tourId
	 * @return
	 */
	List<TravelSubject> getSubjectByTour(Long tourId);

	/**
	 * @param tourId
	 * @param subjectIds
	 * @return
	 */
	boolean updateTourSubjects(Tour tour, List<Long> subjectIds);

	/**
	 * @param images
	 * @param path
	 * @return
	 */
	List<String> storeTourImages(List<Image> images, String path);

	/**
	 * @param tourId
	 * @return
	 */
	List<Comment> getAllCommentByTour(Long tourId);

	/**
	 * @param subjectId
	 * @param path
	 * @return
	 */
	List<VTour> getToursBySubjectId(Long subjectId, String path);

	/**
	 * Count number of tours associated with an event by event id.
	 * @param eventId
	 * @return
	 */
	Long countTourAssociationWithEvent(Long eventId);

	/**
	 * @param tour
	 * @param eventIds
	 * @return
	 */
	boolean updateTourEvents(Tour tour, List<Long> eventIds);

	/**
	 * @param tourId
	 * @return
	 */
	List<TravelEvent> getEventByTour(Long tourId);

	/**
	 * @param travelEvent
	 */
	void removeTravelEvent(TravelEvent travelEvent);

	/**
	 * @param travelSubject
	 */
	void removeTravelSubject(TravelSubject travelSubject);

	/**
	 * @param path
	 * @return
	 */
	List<VTour> getToursHasEvent(String path);

	/**
	 * @param eventId
	 * @param path
	 * @return
	 */
	List<VTour> getToursByEventId(Long eventId, String path);

	/**
	 * @param subjectId
	 * @param tourId except tour
	 * @param path
	 * @return
	 */
	List<VTour> getRelatedToursBySubjectId(Long subjectId, Long tourId, String path);

	/**
	 * @param path
	 * @param exceptedtourId
	 * @return
	 */
	List<VTour> getRelatedToursHasEvent(String path, Long exceptedtourId);

	/**
	 * @param region
	 * @param exceptedTourId
	 * @param path
	 * @return
	 */
	List<VTour> getRelatedTourByRegion(RegionType region, Long exceptedTourId, String path);

	/**
	 * @param region
	 * @param subject
	 * @param hasEvent
	 * @param path
	 * @return
	 */
	List<VTour> search(String region, String subject, boolean hasEvent, String path);

	/**
	 * @param path
	 * @return
	 */
	List<VTour> getTopRecommendTour(String path);

	/**
	 * @param path
	 * @return
	 */
	List<VTour> getMostFavorTours(String path);

	/**
	 * @return
	 */
	List<TravelEvent> getMostRecentEvents();

	/**
	 * @return
	 */
	List<VEvent> getMostRecentVEvents();

	/**
	 * @param path
	 * @return
	 */
	List<VTour> getToursByStickSubject(String path);

	/**
	 * @return
	 */
	List<TravelEvent> fetchMostRecentEvents();

	/**
	 * @return
	 */
	Articles getAboutUsArticles();

	/**
	 * @param contentId
	 * @return
	 */
	Articles getArticlesByContentId(Long contentId);
}
