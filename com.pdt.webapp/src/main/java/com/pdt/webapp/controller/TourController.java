/**
 * <p>
 * </p>
 *
 * @author hungpx
 * @since
 */
package com.pdt.webapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import net.sf.ehcache.Element;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pdt.cms.ui.util.CacheHelper;
import com.pdt.core.model.Articles;
import com.pdt.core.model.Comment;
import com.pdt.core.model.Customer;
import com.pdt.core.model.HotelLevel;
import com.pdt.core.model.Image;
import com.pdt.core.model.RegionType;
import com.pdt.core.model.RequestInfo;
import com.pdt.core.model.RequestStatus;
import com.pdt.core.model.Tour;
import com.pdt.core.model.TourComment;
import com.pdt.core.model.TourRequest;
import com.pdt.core.model.TravelSubject;
import com.pdt.core.resources.LanguageType;
import com.pdt.core.service.CoreService;
import com.pdt.service.PdtTourService;
import com.pdt.util.Pagination;
import com.pdt.util.PdtUtils;
import com.pdt.util.Static;
import com.pdt.web.view.model.VTour;
import com.pdt.web.view.model.VTourRequest;

/**
 * Handle front end tour request. Following business should be done: </br>
 * <ul>
 * <li>(1) List all tour by 3 criteria (Region, Subject and event); sorting and paging the list.</li>
 * <li>(2) Process filtering tours and return value.</li>
 * <li>(3) Show tour details.</li>
 * </ul>
 *
 * @author hungpx
 *
 */

@Controller
@RequestMapping("/tour")
public class TourController extends AbstractController {

	private static final String TOUR_BY_REGION_CACHE = "regionToursCache";
	private static final String TOUR_BY_SUBJECT_CACHE = "subjectToursCache";
	private static final String TOUR_BY_EVENT_CACHE = "eventToursCache";
	private static final String TOUR_BY_REGION = "region";
	private static final String TOUR_BY_EVENT = "event";
	private static final String TOUR_BY_SUBJECT = "subject";

	private static final String SESSION_TOUR = "tmptour";

	@Autowired
	private PdtTourService tourService;
	@Autowired
	private CoreService coreService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ServletContext context;
	@Autowired
	private HttpServletRequest request;

	@RequestMapping(value = "/region/{regionPath}", method = RequestMethod.GET)
	public String getTourByRegion(@PathVariable String regionPath, Model model, HttpServletRequest request) {
		return getTourByRegion(regionPath, model, 0, request);
	}

	@RequestMapping(value = "/region/{regionPath}/{page}", method = RequestMethod.GET)
	public String getTourByRegion(
			@PathVariable String regionPath, Model model, @PathVariable int page, HttpServletRequest request) {

		Element regionCache = CacheHelper.getCache(Static.TOUR_LIST_CACHE).get(TOUR_BY_REGION_CACHE + regionPath);
		@SuppressWarnings("unchecked")
		List<VTour> tourByRegion = regionCache != null ? (List<VTour>) regionCache.getObjectValue() : null;

		String caption = getRegionCaption(regionPath);
		if (caption == null)
			return notfound("Get tour with wrong region path " + regionPath);

		if (tourByRegion == null) {
			if (regionPath.equals(RegionType.NORTH.name().toLowerCase())) {
				tourByRegion = tourService.getTourByRegion(RegionType.NORTH,
						context.getRealPath(Static.UPLOAD_THUMBNAIL_DISK_PATH));
			} else if (regionPath.equals(RegionType.SOUTH.name().toLowerCase())) {
				tourByRegion = tourService.getTourByRegion(RegionType.SOUTH,
						context.getRealPath(Static.UPLOAD_THUMBNAIL_DISK_PATH));
			} else if (regionPath.equals(RegionType.CENTRAL.name().toLowerCase())) {
				tourByRegion = tourService.getTourByRegion(RegionType.CENTRAL,
						context.getRealPath(Static.UPLOAD_THUMBNAIL_DISK_PATH));
			}

			CacheHelper.getCache(Static.TOUR_LIST_CACHE).put(
					new Element(TOUR_BY_REGION_CACHE + regionPath, tourByRegion));
		}

		Pagination pagination = new Pagination(tourByRegion.size(), page == 0 ? 1 : page);

		if (pagination.isPaged()) {
			if (pagination.isValidPage(pagination.getCurrentPage())) {
				tourByRegion = tourByRegion.subList(
						(pagination.getCurrentPage() - 1) * Static.PAGE_SIZE,
						pagination.getCurrentPage() * Static.PAGE_SIZE > pagination.getTotalElements() ? pagination
								.getTotalElements() - 1 : pagination.getCurrentPage() * Static.PAGE_SIZE);
			}
		}

		model.addAttribute("tourTypePath", TOUR_BY_REGION);
		// model.addAttribute("regionPath", regionPath);
		model.addAttribute("path", caption);// caption in breadcrumb
		model.addAttribute("pathId", regionPath);// path id to query page list
		model.addAttribute("tourList", tourByRegion);
		model.addAttribute("pagination", pagination);

		return "listtour";
	}

	@RequestMapping(value = "/subject/{subjectId}", method = RequestMethod.GET)
	public String getTourBySubject(@PathVariable String subjectId, Model model) {
		return getTourBySubject(subjectId, model, 0);
	}

	@RequestMapping(value = "/subject/{subjectId}/{page}", method = RequestMethod.GET)
	public String getTourBySubject(@PathVariable String subjectId, Model model, @PathVariable int page) {

		Long sid;
		try {
			sid = Long.valueOf(subjectId);
		} catch (NumberFormatException e) {
			return notfound("Invalid subject id = " + subjectId);
		}
		TravelSubject subject = coreService.findById(TravelSubject.class, sid);
		if (subject == null)
			return notfound("No subject with id = " + subjectId);

		Element subjectToursCache = CacheHelper.getCache(Static.TOUR_LIST_CACHE).get(TOUR_BY_SUBJECT_CACHE + sid);
		@SuppressWarnings("unchecked")
		List<VTour> toursBySubject = subjectToursCache != null ? (List<VTour>) subjectToursCache.getObjectValue()
				: null;

		if (toursBySubject == null) {
			toursBySubject = tourService.getToursBySubjectId(sid,
					context.getRealPath(Static.UPLOAD_THUMBNAIL_DISK_PATH));
			CacheHelper.getCache(Static.TOUR_LIST_CACHE).put(new Element(TOUR_BY_SUBJECT_CACHE + sid, toursBySubject));
		}

		Pagination pagination = new Pagination(toursBySubject.size(), page == 0 ? 1 : page);

		if (pagination.isPaged()) {
			if (pagination.isValidPage(pagination.getCurrentPage())) {
				toursBySubject = toursBySubject.subList(
						(pagination.getCurrentPage() - 1) * Static.PAGE_SIZE,
						pagination.getCurrentPage() * Static.PAGE_SIZE > pagination.getTotalElements() ? pagination
								.getTotalElements() - 1 : pagination.getCurrentPage() * Static.PAGE_SIZE);
			}
		}

		model.addAttribute("tourTypePath", TOUR_BY_SUBJECT);
		// model.addAttribute("regionPath", regionPath);
		model.addAttribute("path", subject.getName());// caption in breadcrumb
		model.addAttribute("pathId", sid);// path id to query page list
		model.addAttribute("tourList", toursBySubject);
		model.addAttribute("pagination", pagination);

		return "listtour";
	}

	// @RequestMapping(value = "/event", method = RequestMethod.GET)
	// public String getTourByEvent(Model model) {
	// return getTourByEvent(model, 0);
	// }

	@RequestMapping(value = "/event/{id}", method = RequestMethod.GET)
	public String getTourByEventId(Model model, @PathVariable String id) {
		return getTourByEventId(model, id, 0);
	}

	@RequestMapping(value = "/event/{id}/{page}", method = RequestMethod.GET)
	public String getTourByEventId(Model model, @PathVariable String id, @PathVariable int page) {
		Long eid = null;
		Element eventToursCache = null;

		try {
			eid = Long.valueOf(id);
		} catch (NumberFormatException e) {
			return notfound("Invalid event id " + id);
		}

		eventToursCache = CacheHelper.getCache(Static.TOUR_LIST_CACHE).get(TOUR_BY_EVENT_CACHE + id);

		@SuppressWarnings("unchecked")
		List<VTour> toursByEvent = eventToursCache != null ? (List<VTour>) eventToursCache.getObjectValue() : null;

		if (toursByEvent == null) {
			if (eid == 0)
				toursByEvent = tourService.getToursHasEvent(context.getRealPath(Static.UPLOAD_THUMBNAIL_DISK_PATH));
			else
				toursByEvent = tourService.getToursByEventId(eid,
						context.getRealPath(Static.UPLOAD_THUMBNAIL_DISK_PATH));
			CacheHelper.getCache(Static.TOUR_LIST_CACHE).put(new Element(TOUR_BY_EVENT_CACHE + id, toursByEvent));
		}

		Pagination pagination = new Pagination(toursByEvent.size(), page == 0 ? 1 : page);

		if (pagination.isPaged()) {
			if (pagination.isValidPage(pagination.getCurrentPage())) {
				toursByEvent = toursByEvent.subList(
						(pagination.getCurrentPage() - 1) * Static.PAGE_SIZE,
						pagination.getCurrentPage() * Static.PAGE_SIZE > pagination.getTotalElements() ? pagination
								.getTotalElements() - 1 : pagination.getCurrentPage() * Static.PAGE_SIZE);
			}
		}

		model.addAttribute("tourTypePath", TOUR_BY_EVENT);
		// model.addAttribute("regionPath", regionPath);
		model.addAttribute("path", "événement");// caption in breadcrumb
		model.addAttribute("pathId", id == null ? 0 : id);// path id to query page list
		model.addAttribute("tourList", toursByEvent);
		model.addAttribute("pagination", pagination);

		return "listtour";
	}

	@RequestMapping(value = "/{tourTypePath}/{tourTypePathId}/{tourId}/{seoPath}", method = RequestMethod.GET)
	public String tourDetails(
			@PathVariable String tourTypePath, @PathVariable String tourTypePathId, @PathVariable Long tourId,
			@PathVariable String seoPath, Model model, HttpServletRequest request) {
		Tour tour = coreService.findById(Tour.class, tourId);

		if (tour == null)
			return notfound("Wrong tour id: " + tourId);

		request.getSession().setAttribute(SESSION_TOUR, tour);

		List<Image> images = tourService.getImagesByTour(tourId);
		List<String> imagePaths = null;
		if (images != null && images.size() > 0) {
			imagePaths = tourService.storeTourImages(images, context.getRealPath(Static.UPLOAD_TOUR_PHOTO_DISK_PATH));
		}
		List<VTour> relatedTours = null;
		try {

			if (tourTypePath.equals(TOUR_BY_REGION))
				relatedTours = tourService.getRelatedTourByRegion(tour.getRegionType(), tourId,
						context.getRealPath(Static.UPLOAD_THUMBNAIL_DISK_PATH));
			// relatedTours = tourService.getTourByRegion(tour.getRegionType(),
			// context.getRealPath(Static.UPLOAD_THUMBNAIL_DISK_PATH));
			if (tourTypePath.equals(TOUR_BY_SUBJECT))
				relatedTours = tourService.getRelatedToursBySubjectId(Long.valueOf(tourTypePathId), tourId,
						context.getRealPath(Static.UPLOAD_THUMBNAIL_DISK_PATH));
			if (tourTypePath.equals(TOUR_BY_EVENT)) {

				Long eid = Long.valueOf(tourTypePathId);
				if (eid == 0)
					relatedTours = tourService.getRelatedToursHasEvent(
							context.getRealPath(Static.UPLOAD_THUMBNAIL_DISK_PATH), tourId);
				else
					// FIXME change to get related tour by event id
					relatedTours = tourService.getRelatedToursHasEvent(
							context.getRealPath(Static.UPLOAD_THUMBNAIL_DISK_PATH), tourId);
			}
		} catch (NumberFormatException e) {
			return notfound("Invalid tour type path id " + tourTypePath);
		}

		model.addAttribute("tourTypePath", tourTypePath);
		model.addAttribute("pathId", tourTypePathId);// path id to query page list
		// check tour type to get list of related tour
		model.addAttribute("relatedTours", relatedTours);
		model.addAttribute("tour", tour);
		model.addAttribute("articles", coreService.findById(Articles.class, tour.getArticlesId()));
		model.addAttribute("images", imagePaths != null ? imagePaths : new ArrayList<String>());
		model.addAttribute("comments", allComments(tourId));
		model.addAttribute("newComment", new Comment()); // required model to bind with form to add new comment
		model.addAttribute(SESSION_MESSAGE, request.getSession().getAttribute(SESSION_MESSAGE));
		request.getSession().removeAttribute(SESSION_MESSAGE);

		// tour request backing object
		VTourRequest tourRequest = new VTourRequest();
		Customer customer = new Customer();
		tourRequest.setCustomer(customer);
		tourRequest.setProductId(tourId);
		tourRequest.setProductName(tour.getName());
		tourRequest.setNumberOfAdults(1);
		model.addAttribute("newRequest", tourRequest);

		return "tourdetails";
	}

	protected Map<String, String> referenceData(HttpServletRequest request) throws Exception {
		// TODO check why this doesn't not work
		Map<String, String> hotelLevels = new HashMap<String, String>();
		for (HotelLevel hl : HotelLevel.values())
			hotelLevels.put(hl.name(), hl.name()/*
												 * messageSource.getMessage("controller.common.label.hotel_" +
												 * hl.name(), null,// Locale.getDefault())
												 */);
		// model.addAttribute("hotelLevels", hotelLevels);
		return hotelLevels;

	}

	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Comment> allComments(Long tourId) {
		return tourService.getAllCommentByTour(tourId);
	}

	@RequestMapping(value = "/createComment", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Comment createComment(@RequestBody Comment comment) {
		Tour tour = (Tour) request.getSession().getAttribute(SESSION_TOUR);
		TourComment tc = new TourComment();
		tc.setContent(comment.getContent());
		tc.setAuthor(comment.getAuthor());
		tc.setAddress(comment.getAddress());
		tc.setEmail(comment.getEmail());
		tc.setTour(tour);
		return coreService.insert(tc);
	}

	@RequestMapping(value = "/{tourTypePath}/{tourTypePathId}/{tourId}/{seoPath}/createRequest", method = RequestMethod.POST)
	public String createRequest(
			@PathVariable String tourTypePath, @PathVariable String tourTypePathId, @PathVariable Long tourId,
			@PathVariable String seoPath, @ModelAttribute VTourRequest tr) {
		// TODO save request and send email
		String message = null;
		try {
			logger.info("A tour request has been made: tour " + tourId + ", customer name: " + tr == null ? "NULL" : tr
					.getCustomer().getFullName() + ": ");
			TourRequest tRequest = new TourRequest();
			Tour tour = (Tour) request.getSession().getAttribute(SESSION_TOUR);
			if (tour != null && tour.getId() == tr.getProductId())
				tRequest.setTour(tour);
			else {
				logger.warn("Tour is loss from session");
				Tour t = coreService.findById(Tour.class, tr.getProductId());
				if (t != null) {
					tRequest.setTour(t);
				}
			}

			Customer c = coreService.insert(tr.getCustomer());
			tRequest.setCustomer(c);
			tRequest.setNumberOfAdults(tr.getNumberOfAdults());
			tRequest.setNumberOfChildren2to9(tr.getNumberOfChildren2to9());
			tRequest.setPlannedPeriod(tr.getPlannedPeriod());
			tRequest.setExpectedTime(tr.getExpectedTime());
			tRequest.setSubject(tr.getSubject());
			tRequest.setDetail(tr.getDetail());
			tRequest.setRequestInfo(new RequestInfo(RequestStatus.DRAFT));
			if (PdtUtils.valueOfEnumType(HotelLevel.class, tr.getHotelLevel()))
				tRequest.setHotelLevel(HotelLevel.valueOf(tr.getHotelLevel()));

			coreService.insert(tRequest);
			message = "Demander succès.";
			request.getSession().setAttribute(SESSION_MESSAGE, "Demander succès.");
		} catch (Exception e) {
			message = "Demande échec, se il vous plaît essayer à nouveau.";
			request.getSession().setAttribute(SESSION_MESSAGE, "Demande échec, se il vous plaît essayer à nouveau.");
		}
		Model model = new BindingAwareModelMap();
		model.addAttribute("message", message);
		// StringBuilder path = new StringBuilder();
		// path.append(tourTypePath).append("/").append(tourTypePathId).append("/").append(tourId).append("/")
		// .append(seoPath);
		return "redirect:";
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search(
			@RequestParam(required = false) String region, @RequestParam(required = false) String subject,
			@RequestParam(required = false) boolean hasEvent, Model model) {
		List<VTour> tours = tourService.search(region, subject, hasEvent,
				context.getRealPath(Static.UPLOAD_THUMBNAIL_DISK_PATH));

		model.addAttribute("tourList", tours);
		return "searchresults";
	}

	private String getRegionCaption(String regionPath) {
		String caption = null;
		if (RegionType.NORTH.name().toLowerCase().equals(regionPath)) {
			caption = RegionType.NORTH.getCaption(LanguageType.FR);
		} else if (RegionType.SOUTH.name().toLowerCase().equals(regionPath)) {
			caption = RegionType.SOUTH.getCaption(LanguageType.FR);
		} else if (RegionType.CENTRAL.name().toLowerCase().equals(regionPath)) {
			caption = RegionType.CENTRAL.getCaption(LanguageType.FR);
		}
		return caption;
	}
}
