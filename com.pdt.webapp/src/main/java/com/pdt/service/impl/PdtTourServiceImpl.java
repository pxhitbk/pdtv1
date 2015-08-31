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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.pdt.cms.data.model.TourImageDto;
import com.pdt.core.model.Album;
import com.pdt.core.model.Articles;
import com.pdt.core.model.AvailableState;
import com.pdt.core.model.City;
import com.pdt.core.model.Comment;
import com.pdt.core.model.ContentType;
import com.pdt.core.model.Destination;
import com.pdt.core.model.Hotel;
import com.pdt.core.model.Image;
import com.pdt.core.model.RegionType;
import com.pdt.core.model.StaticContent;
import com.pdt.core.model.Tour;
import com.pdt.core.model.TourAssociation;
import com.pdt.core.model.TourComment;
import com.pdt.core.model.TourImage;
import com.pdt.core.model.TourRequest;
import com.pdt.core.model.TravelEvent;
import com.pdt.core.model.TravelEventTour;
import com.pdt.core.model.TravelSubject;
import com.pdt.core.model.TravelSubjectTourAssociation;
import com.pdt.core.service.CoreService;
import com.pdt.core.service.TourService;
import com.pdt.core.util.CommonValidation;
import com.pdt.service.PdtTourService;
import com.pdt.util.PdtUtils;
import com.pdt.util.Static;
import com.pdt.web.view.model.VEvent;
import com.pdt.web.view.model.VTour;
import com.pdt.webapp.ui.model.MenuItemModel;

/**
 * @author hungpx
 *
 */
@Transactional(noRollbackFor = Exception.class)
public class PdtTourServiceImpl implements PdtTourService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PdtTourServiceImpl.class);
	@Autowired
	private CoreService coreService;
	@Autowired
	private TourService tourService;

	private static final String GET_CITY_CONTAIN_HOTEL = "SELECT distinct (c) FROM " + Destination.class.getName()
			+ " d INNER JOIN d.city c WHERE destType = " + Hotel.DEST_TYPE + " ORDER BY c.city";

	private static final String GET_SUBJECT_CONTAIN_TOUR = "SELECT distinct (s) FROM "
			+ TourAssociation.class.getName() + " ta INNER JOIN ta." + TravelSubjectTourAssociation.SUBJECT_REF_NAME
			+ " s";

	private static final String COUNT_TOUR_BY_EVENT_ID = "SELECT count (ta) FROM " + TourAssociation.class.getName()
			+ " ta WHERE ta.eventId = :eid";

	private static final String GET_SUBJECT_BY_TOURID = "SELECT distinct (s) FROM " + TourAssociation.class.getName()
			+ " ta INNER JOIN ta." + TravelSubjectTourAssociation.SUBJECT_REF_NAME + " s WHERE tourId = :tid";

	private static final String GET_EVENTS_BY_TOURID = "SELECT distinct (e) FROM " + TourAssociation.class.getName()
			+ " ta INNER JOIN ta.event e WHERE tourId = :tid";

	private static final String REMOVE_ALL_TOUR_SJ_ASSO_BY_TOURID = "DELETE FROM "
			+ TravelSubjectTourAssociation.class.getName() + " ta WHERE ta.tourId = :tid";
	private static final String REMOVE_ALL_TOUR_EV_ASSO_BY_TOURID = "DELETE FROM " + TravelEventTour.class.getName()
			+ " ta WHERE ta.tourId = :tid";

	private static final String REMOVE_ALL_TOUR_IMAGES_BY_TOURID = "DELETE FROM " + TourImage.class.getName()
			+ " ti WHERE ti.tourId = :tid";

	private static final String GET_ALBUM_BY_NAME = "SELECT a FROM " + Album.class.getName()
			+ " a WHERE a.name = :name";

	private static final String GET_ALL_IMAGES_BY_TOUR = "SELECT i FROM " + TourImage.class.getName()
			+ " ti INNER JOIN ti.image i WHERE ti.tourId = :tid";

	private static final String GET_ALL_COMMENTS_BY_TOUR = "SELECT c FROM " + TourComment.class.getName()
			+ " c WHERE cmtType = 1 AND c.tourId = :tid ORDER BY c.id DESC";

	private static final String GET_TOUR_BY_REGION = "SELECT t from " + Tour.class.getName()
			+ " t WHERE t.regionType = :regionType AND t.availableState = " + AvailableState.OPEN.ordinal()
			+ " ORDER BY t.id DESC";
	private static final String GET_RECOMMEND_TOURS = "SELECT t from " + Tour.class.getName()
			+ " t WHERE t.favouriteOrder <> 99 AND t.availableState = " + AvailableState.OPEN.ordinal()
			+ " ORDER BY t.recommendOrder";
	private static final String GET_FAVOUR_TOURS = "SELECT t from " + Tour.class.getName()
			+ " t WHERE t.recommendOrder <> 99 AND t.availableState = " + AvailableState.OPEN.ordinal()
			+ " ORDER BY t.favouriteOrder";

	private static final String GET_RELATED_TOUR_BY_REGION = "SELECT t from " + Tour.class.getName()
			+ " t WHERE t.regionType = :regionType AND t.id <> :exceptTourId AND t.availableState = "
			+ AvailableState.OPEN.ordinal() + " ORDER BY t.id DESC";

	private static final String GET_TOUR_BY_SUBJECT_ID = "SELECT distinct (t) from " + TourAssociation.class.getName()
			+ " ta INNER JOIN ta.tour t WHERE ta.subjectId = :sid AND  t.availableState = "
			+ AvailableState.OPEN.ordinal() + " ORDER BY t.id DESC";
	private static final String GET_RELATED_TOUR_BY_SUBJECT_ID = "SELECT distinct  (t) from "
			+ TourAssociation.class.getName()
			+ " ta INNER JOIN ta.tour t WHERE ta.subjectId = :sid AND t.id <> :exceptTourId AND  t.availableState = "
			+ AvailableState.OPEN.ordinal() + " ORDER BY t.id DESC";

	private static final String GET_TOUR_BY_EVENT_ID = "SELECT distinct (t) from " + TourAssociation.class.getName()
			+ " ta INNER JOIN ta.tour t WHERE ta.eventId = :eid AND t.availableState = "
			+ AvailableState.OPEN.ordinal();

	private static final String GET_RELATED_TOUR_BY_EVENT_ID = "SELECT distinct (t) from "
			+ TourAssociation.class.getName()
			+ " ta INNER JOIN ta.tour t WHERE ta.eventId = :eid AND t.id <> :exceptTourId AND t.availableState = "
			+ AvailableState.OPEN.ordinal();

	private static final String GET_TOUR_HAS_EVENT = "SELECT distinct (t) from " + TourAssociation.class.getName()
			+ " ta INNER JOIN ta.tour t WHERE ta.eventId is not null AND t.availableState = "
			+ AvailableState.OPEN.ordinal();

	private static final String GET_TOUR_BY_STICK_SUBJECT = "SELECT distinct (t) from "
			+ TourAssociation.class.getName()
			+ " ta INNER JOIN ta.tour t INNER JOIN ta.subject s WHERE s.stick = 1 AND t.availableState = "
			+ AvailableState.OPEN.ordinal();

	private static final String GET_RELATED_TOUR_HAS_EVENT = "SELECT distinct (t) from "
			+ TourAssociation.class.getName()
			+ " ta INNER JOIN ta.tour t INNER JOIN ta.event e WHERE ta.eventId is not null AND e.beginDate > CURRENT_DATE AND t.id <> :exceptTourId AND t.availableState = "
			+ AvailableState.OPEN.ordinal() + " ORDER BY e.beginDate";

	private static final String GET_MOST_RECENT_EVENTS = "SELECT e FROM " + TravelEvent.class.getName()
			+ " e WHERE e.beginDate > CURRENT_DATE ORDER BY e.beginDate";

	private static final String FETCH_MOST_RECENT_EVENTS = "SELECT e FROM " + TravelEvent.class.getName()
			+ " e join fetch e.destination d join fetch d.city WHERE e.endDate > CURRENT_DATE ORDER BY e.endDate";

	private static final String GET_MOST_RECENT_VEVENTS = "SELECT new " + VEvent.class.getName()
			+ "(e, d, c) FROM TravelEvent e INNER JOIN e.destination d INNER JOIN d.city c"
			+ " WHERE e.beginDate > CURRENT_DATE ORDER BY e.beginDate";

	private static final String GET_ABOUTUS_ARTICLES = "SELECT a FROM " + StaticContent.class.getName()
			+ " c INNER JOIN c.articles a WHERE c.contentType = :ct";
	private static final String GET_ARTICLES_BY_CONTENT_ID = "SELECT a FROM " + StaticContent.class.getName()
			+ " c INNER JOIN c.articles a WHERE c.id = :cid";

	/*
	 * (non-Javadoc)
	 *
	 * @see com.pdt.service.PdtTourService#getMenuItemModel()
	 */
	@Override
	public MenuItemModel getMenuItemModel() {

		MenuItemModel model = null;

		Map<Long, String> tourBySubjects = new HashMap<Long, String>();
		Map<Long, String> hotelsByCities = new HashMap<Long, String>();
		Map<Long, String> services = new HashMap<Long, String>();
		Map<Long, String> tips = new HashMap<Long, String>();

		List<City> cities = coreService.getEntityManager().createQuery(GET_CITY_CONTAIN_HOTEL, City.class)
				.getResultList();
		if (cities != null) {
			for (City c : cities) {
				hotelsByCities.put(c.getId(), c.getCity());
			}
		}

		List<TravelSubject> subjects = coreService.getEntityManager()
				.createQuery(GET_SUBJECT_CONTAIN_TOUR, TravelSubject.class).getResultList();

		if (subjects != null && !subjects.isEmpty()) {
			for (TravelSubject s : subjects) {
				tourBySubjects.put(s.getId(), s.getName());
			}
		}

		List<StaticContent> travelServices = tourService.getStaticContent(ContentType.SERVICES);
		if (travelServices != null && !travelServices.isEmpty())
			for (StaticContent s : travelServices)
				services.put(s.getId(), s.getName());

		List<StaticContent> travelTips = tourService.getStaticContent(ContentType.TIPS);
		if (travelTips != null && !travelTips.isEmpty())
			for (StaticContent t : travelTips)
				tips.put(t.getId(), t.getName());

		model = new MenuItemModel(tourBySubjects, hotelsByCities, services, tips);

		return model;
	}

	@Override
	public Tour updateTourImages(TourImageDto tourImgs) {
		CommonValidation.forceNotNull("tourImagesDto cannot be null", tourImgs);
		Tour tour = null;
		if (tourImgs.getTour().getId() != null) {
			tour = coreService.update(tourImgs.getTour());
		} else
			tour = coreService.insert(tourImgs.getTour());
		List<Image> images = tourImgs.getImages();
		if (images != null && !images.isEmpty()) {
			int i = coreService.getEntityManager().createQuery(REMOVE_ALL_TOUR_IMAGES_BY_TOURID)
					.setParameter("tid", tour.getId()).executeUpdate();
			LOGGER.info("DELETE " + i + "images associate with tour " + tour.getId());
			for (Image image : images) {
				if (image.getAlbumId() == null || image.getAlbumId() == 0)
					image.setAlbum(findAlbumByName(Album.DEFAULT_ALBUM_NAME, true));
				if (image.getId() != null)
					image = coreService.update(image);
				else
					image = coreService.insert(image);
				TourImage ti = new TourImage(tour, image);
				coreService.insert(ti);
			}
		}
		return tour;
	}

	@Override
	public void removeTour(Tour tour) {
		// remove all association
		coreService
				.getEntityManager()
				.createQuery("DELETE FROM " + TourAssociation.class.getName() + " ta WHERE ta.tourId = " + tour.getId())
				.executeUpdate();
		coreService.getEntityManager()
				.createQuery("DELETE FROM " + TourImage.class.getName() + " ti WHERE ti.tourId = " + tour.getId())
				.executeUpdate();
		coreService.getEntityManager()
				.createQuery("DELETE FROM " + TourComment.class.getName() + " tc WHERE tc.tourId = " + tour.getId())
				.executeUpdate();
		coreService.getEntityManager()
				.createQuery("DELETE FROM " + TourRequest.class.getName() + " tr WHERE tr.tourId = " + tour.getId())
				.executeUpdate();

		// remove tour
		coreService.remove(tour);
	}

	@Override
	public void removeTravelSubject(TravelSubject travelSubject) {
		// remove all association
		coreService
				.getEntityManager()
				.createQuery(
						"DELETE FROM " + TourAssociation.class.getName() + " ta WHERE ta.subjectId = "
								+ travelSubject.getId()).executeUpdate();
		// remove tour
		coreService.remove(travelSubject);
	}

	@Override
	public void removeTravelEvent(TravelEvent travelEvent) {
		// remove all association
		coreService
				.getEntityManager()
				.createQuery(
						"DELETE FROM " + TourAssociation.class.getName() + " ta WHERE ta.eventId = "
								+ travelEvent.getId()).executeUpdate();
		// remove tour
		coreService.remove(travelEvent);
	}

	@Override
	public Album findAlbumByName(String albumName, boolean createIfNotExist) {

		Album df = null;
		try {
			df = coreService.getEntityManager().createQuery(GET_ALBUM_BY_NAME, Album.class)
					.setParameter("name", albumName).getSingleResult();
		} catch (NoResultException e) {
			if (createIfNotExist) {
				df = new Album();
				df.setName(albumName);
				df = coreService.insert(df);
			}
		}

		return df;
	}

	@Override
	public List<Image> getImagesByTour(Long tourId) {
		if (tourId == null)
			return null;
		return coreService.getEntityManager().createQuery(GET_ALL_IMAGES_BY_TOUR, Image.class)
				.setParameter("tid", tourId).getResultList();
	}

	@Override
	public Long countTourAssociationWithEvent(Long eventId) {
		if (eventId == null)
			return 0L;
		return coreService.getEntityManager().createQuery(COUNT_TOUR_BY_EVENT_ID, Long.class)
				.setParameter("eid", eventId).getSingleResult();
	}

	@Override
	public List<VTour> getTourByRegion(RegionType region, String path) {
		List<Tour> tours = coreService.getEntityManager().createQuery(GET_TOUR_BY_REGION, Tour.class)
				.setParameter("regionType", region).getResultList();

		return getViewToursFromTour(tours, path);
	}

	@Override
	public List<VTour> getMostFavorTours(String path) {
		List<Tour> tours = coreService.getEntityManager().createQuery(GET_FAVOUR_TOURS, Tour.class).setMaxResults(6)
				.getResultList();

		return getViewToursFromTour(tours, path);
	}

	@Override
	public List<VTour> getTopRecommendTour(String path) {
		List<Tour> tours = coreService.getEntityManager().createQuery(GET_RECOMMEND_TOURS, Tour.class).setMaxResults(3)
				.getResultList();

		return getViewToursFromTour(tours, path);
	}

	@Override
	public List<TravelEvent> getMostRecentEvents() {
		List<TravelEvent> events = coreService.getEntityManager()
				.createQuery(GET_MOST_RECENT_EVENTS, TravelEvent.class).setMaxResults(6).getResultList();

		return events;
	}

	@Override
	public List<TravelEvent> fetchMostRecentEvents() {
		List<TravelEvent> events = coreService.getEntityManager()
				.createQuery(FETCH_MOST_RECENT_EVENTS, TravelEvent.class).setMaxResults(6).getResultList();

		return events;
	}

	@Transactional
	@Override
	public List<VEvent> getMostRecentVEvents() {
		List<VEvent> events = coreService.getEntityManager().createQuery(GET_MOST_RECENT_VEVENTS, VEvent.class)
				.setMaxResults(6).getResultList();

		return events;
	}

	@Override
	public List<VTour> getRelatedTourByRegion(RegionType region, Long exceptedTourId, String path) {
		List<Tour> tours = coreService.getEntityManager().createQuery(GET_RELATED_TOUR_BY_REGION, Tour.class)
				.setParameter("regionType", region).setParameter("exceptTourId", exceptedTourId).setMaxResults(3)
				.getResultList();
		return getViewToursFromTour(tours, path);
	}

	@Override
	public List<VTour> getToursBySubjectId(Long subjectId, String path) {
		List<Tour> tours = coreService.getEntityManager().createQuery(GET_TOUR_BY_SUBJECT_ID, Tour.class)
				.setParameter("sid", subjectId).getResultList();
		return getViewToursFromTour(tours, path);
	}

	@Override
	public List<VTour> getToursByStickSubject(String path) {
		List<Tour> tours = coreService.getEntityManager().createQuery(GET_TOUR_BY_STICK_SUBJECT, Tour.class)
				.setMaxResults(3).getResultList();
		return getViewToursFromTour(tours, path);
	}

	@Override
	public List<VTour> getRelatedToursBySubjectId(Long subjectId, Long tourId, String path) {
		List<Tour> tours = coreService.getEntityManager().createQuery(GET_RELATED_TOUR_BY_SUBJECT_ID, Tour.class)
				.setParameter("sid", subjectId).setParameter("exceptTourId", tourId).setMaxResults(3).getResultList();
		return getViewToursFromTour(tours, path);
	}

	@Override
	public List<VTour> getToursByEventId(Long eventId, String path) {
		List<Tour> tours = coreService.getEntityManager().createQuery(GET_TOUR_BY_EVENT_ID, Tour.class)
				.setParameter("eid", eventId).getResultList();
		return getViewToursFromTour(tours, path);
	}

	@Override
	public List<VTour> getToursHasEvent(String path) {
		List<Tour> tours = coreService.getEntityManager().createQuery(GET_TOUR_HAS_EVENT, Tour.class).getResultList();
		return getViewToursFromTour(tours, path);
	}

	@Override
	public List<VTour> getRelatedToursHasEvent(String path, Long exceptedtourId) {
		List<Tour> tours = coreService.getEntityManager().createQuery(GET_RELATED_TOUR_HAS_EVENT, Tour.class)
				.setParameter("exceptTourId", exceptedtourId).setMaxResults(3).getResultList();
		return getViewToursFromTour(tours, path);
	}

	private List<VTour> getViewToursFromTour(List<Tour> tours, String path) {
		List<VTour> vtours = new ArrayList<VTour>();
		// byte[] encodedImage = null;
		File file = new File(path);
		if (!file.exists()) {
			if (file.mkdir()) {
				LOGGER.info("Directory is created!");
			} else {
				LOGGER.warn("Failed to create directory!");
			}
		}
		for (Tour t : tours) {
			Image thumb = null;
			if (t.getThumbnailId() != null) {
				thumb = coreService.findById(Image.class, t.getThumbnailId());
				if (thumb.getData() != null) {

					try {
						PdtUtils.writingImageFile(thumb, path);
					} catch (IOException e) {
						// e.printStackTrace();
						LOGGER.error("Cannot writing thumbnail when retrieving the tour " + t.getName(), e);
					}
				}
			}

			VTour vt = new VTour(/* "/resources/uploads/tours/thumbnail/" */Static.UPLOAD_THUMBNAIL_URI_PATH
					+ thumb.getFileName(), t);
			vtours.add(vt);
		}
		return vtours;
	}

	@Override
	public List<TravelSubject> getSubjectByTour(Long tourId) {
		return coreService.getEntityManager().createQuery(GET_SUBJECT_BY_TOURID, TravelSubject.class)
				.setParameter("tid", tourId).getResultList();
	}

	@Override
	public List<TravelEvent> getEventByTour(Long tourId) {
		return coreService.getEntityManager().createQuery(GET_EVENTS_BY_TOURID, TravelEvent.class)
				.setParameter("tid", tourId).getResultList();
	}

	@Override
	public boolean updateTourSubjects(Tour tour, List<Long> subjectIds) {
		if (tour != null && subjectIds != null) {
			if (tour.getId() == 0 || tour.getId() == null) {
				tour = coreService.insert(tour);
			}
			coreService.getEntityManager()
					.createQuery(REMOVE_ALL_TOUR_SJ_ASSO_BY_TOURID + " AND ta.subjectId is not null")
					.setParameter("tid", tour.getId()).executeUpdate();
			for (Long sid : subjectIds) {
				TravelSubject subject = coreService.findById(TravelSubject.class, sid);
				if (subject != null)
					coreService.insert(new TravelSubjectTourAssociation(tour, subject));
			}
		} else {
			LOGGER.warn("Update FAIL for tour subject association with tour id null or 0");
			return false;
		}
		return true;
	}

	@Override
	public boolean updateTourEvents(Tour tour, List<Long> eventIds) {
		if (tour != null && eventIds != null) {
			if (tour.getId() == 0 || tour.getId() == null) {
				tour = coreService.insert(tour);
			}
			coreService.getEntityManager()
					.createQuery(REMOVE_ALL_TOUR_EV_ASSO_BY_TOURID + " AND ta.eventId is not null")
					.setParameter("tid", tour.getId()).executeUpdate();
			for (Long sid : eventIds) {
				TravelEvent event = coreService.findById(TravelEvent.class, sid);
				if (event != null)
					coreService.insert(new TravelEventTour(tour, event));
			}
		} else {
			LOGGER.warn("Update FAIL for tour event association with tour id null or 0");
			return false;
		}
		return true;
	}

	@Override
	public List<String> storeTourImages(List<Image> images, String path) {
		List<String> urls = new ArrayList<String>();
		try {
			for (Image image : images) {
				PdtUtils.writingImageFile(image, path);
				urls.add(Static.UPLOAD_TOUR_PHOTO_URI_PATH + image.getFileName());
			}
		} catch (IOException e) {
			LOGGER.error("Error while writing tour image to local disk", e);
		}

		return urls;
	}

	@Override
	public List<Comment> getAllCommentByTour(Long tourId) {
		return coreService.getEntityManager().createQuery(GET_ALL_COMMENTS_BY_TOUR, Comment.class)
				.setParameter("tid", tourId).getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pdt.service.PdtTourService#search(java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public List<VTour> search(String region, String subject, boolean hasEvent, String path) {
		StringBuilder query = new StringBuilder("SELECT distinct t FROM " + TourAssociation.class.getName()
				+ " ta INNER JOIN ta.tour t WHERE t.availableState = ").append(AvailableState.OPEN.ordinal());
		if (region != null && PdtUtils.valueOfEnumType(RegionType.class, region))
			query.append(" AND t.regionType = " + RegionType.valueOf(region).ordinal());
		if (subject != null && PdtUtils.isLong(subject))
			query.append(" AND ta.subjectId = ").append(subject);
		List<Tour> tours = coreService.getEntityManager().createQuery(query.toString(), Tour.class).getResultList();

		if (hasEvent) {
			List<Long> ids = Lists.transform(tours, new Function<Tour, Long>() {

				@Override
				public Long apply(Tour input) {
					return input.getId();
				}
			});

			String teQuery = "SELECT distinct t from " + TourAssociation.class.getName()
					+ " ta INNER JOIN ta.tour t WHERE ta.eventId is not null AND ta.tourId IN :ids";
			tours = coreService.getEntityManager().createQuery(teQuery, Tour.class).setParameter("ids", ids)
					.getResultList();

		}

		return getViewToursFromTour(tours, path);
	}

	@Override
	public Articles getAboutUsArticles() {
		List<Articles> articles = coreService.getEntityManager().createQuery(GET_ABOUTUS_ARTICLES, Articles.class)
				.setParameter("ct", ContentType.ABOUT_US).getResultList();
		return articles != null && articles.size() > 0 ? articles.get(0) : null;
	}

	@Override
	public Articles getArticlesByContentId(Long contentId) {
		return coreService.getEntityManager().createQuery(GET_ARTICLES_BY_CONTENT_ID, Articles.class)
				.setParameter("cid", contentId).getSingleResult();
	}
}
