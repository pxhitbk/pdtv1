/**
 * <p>
 * </p>
 *
 * @author hungpx
 * @since
 */
package com.pdt.webapp.controller;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import net.sf.ehcache.Element;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pdt.cms.ui.util.CacheHelper;
import com.pdt.core.model.Articles;
import com.pdt.core.model.City;
import com.pdt.core.model.Customer;
import com.pdt.core.model.Hotel;
import com.pdt.core.model.HotelRequest;
import com.pdt.core.model.RequestInfo;
import com.pdt.core.model.RequestStatus;
import com.pdt.core.resources.LanguageType;
import com.pdt.core.service.CoreService;
import com.pdt.service.PdtDestinationService;
import com.pdt.util.Pagination;
import com.pdt.util.Static;
import com.pdt.web.view.model.VHotel;
import com.pdt.web.view.model.VHotelRequest;

/**
 * @author august
 *
 */
@Controller
@RequestMapping("/hotel")
public class HotelController extends AbstractController {

	private static final String HOTEL_LIST_CACHE = "hotelList";
	private static final String SESSION_HOTEL = "tmphotel";

	@Autowired
	private ServletContext context;
	@Autowired
	private HttpServletRequest request;

	@Autowired
	private CoreService coreService;
	@Autowired
	private PdtDestinationService destService;

	@RequestMapping(value = "/{cityId}", method = RequestMethod.GET)
	public String hotels(@PathVariable long cityId, Model model) {
		return hotels(cityId, 0, model);
	}

	@RequestMapping(value = "/{cityId}/{page}", method = RequestMethod.GET)
	public String hotels(@PathVariable long cityId, @PathVariable int page, Model model) {
		City city = coreService.findById(City.class, cityId);
		if (city == null)
			return notfound("Not found city with id = " + cityId);

		Element cachedHotelList = CacheHelper.getCache(Static.TOUR_LIST_CACHE).get(HOTEL_LIST_CACHE + cityId);
		@SuppressWarnings("unchecked")
		List<VHotel> hotelsByCity = cachedHotelList != null ? (List<VHotel>) cachedHotelList.getObjectValue() : null;

		if (hotelsByCity == null) {
			hotelsByCity = destService.getVHotelsByCityId(cityId,
					context.getRealPath(Static.UPLOAD_THUMBNAIL_DISK_PATH));
			CacheHelper.getCache(Static.TOUR_LIST_CACHE).put(new Element(HOTEL_LIST_CACHE + cityId, hotelsByCity));
		}

		Pagination pagination = new Pagination(hotelsByCity.size(), page == 0 ? 1 : page);

		if (pagination.isPaged()) {
			if (pagination.isValidPage(pagination.getCurrentPage())) {
				hotelsByCity = hotelsByCity.subList(
						(pagination.getCurrentPage() - 1) * Static.PAGE_SIZE,
						pagination.getCurrentPage() * Static.PAGE_SIZE > pagination.getTotalElements() ? pagination
								.getTotalElements() - 1 : pagination.getCurrentPage() * Static.PAGE_SIZE);
			}
		}

		List<City> relatedCities = destService.getRelatedCitiesByRegion(city.getRegion(), cityId);

		model.addAttribute("cityName", city.getCity());
		model.addAttribute("regionName", city.getRegion().getCaption(LanguageType.FR));
		model.addAttribute("relatedCity", relatedCities);
		model.addAttribute("hotelList", hotelsByCity);
		model.addAttribute("pagination", pagination);

		return "hotels";
	}

	@RequestMapping(value = "/{cityId}/{hotelId}/{seoPath}/{page}", method = RequestMethod.GET)
	public String hotelDetails(
			@PathVariable long cityId, @PathVariable long hotelId, @PathVariable String seoPath,
			@PathVariable int page, Model model) {

		Hotel hotel = coreService.findById(Hotel.class, hotelId);
		if (hotel == null)
			return notfound("Not found hotel with id " + hotelId);
		if (hotel.getCityId() != cityId)
			return notfound("Wrong city id " + cityId + " for hotel id " + hotelId);

		request.getSession().setAttribute(SESSION_HOTEL, hotel);
		Articles articles = coreService.findById(Articles.class, hotel.getArticlesId());
		City city = coreService.findById(City.class, cityId);

		List<Hotel> relatedHotels = destService.getRelatedHotelsByCity(cityId, hotelId);

		VHotelRequest hotelRequest = new VHotelRequest();
		hotelRequest.setProductId(hotelId);
		hotelRequest.setProductName(hotel.getName());
		model.addAttribute("hotel", hotel);
		model.addAttribute("cityName", city.getCity());
		model.addAttribute("cityId", cityId);
		model.addAttribute("relatedHotels", relatedHotels);
		model.addAttribute("articles", articles);
		model.addAttribute("newRequest", hotelRequest);
		return "hoteldetails";
	}

	@RequestMapping(value = "/{cityId}/{hotelId}/{seoPath}", method = RequestMethod.GET)
	public String hotelDetails(
			@PathVariable long cityId, @PathVariable long hotelId, @PathVariable String seoPath, Model model) {
		return hotelDetails(cityId, hotelId, seoPath, 0, model);
	}

	@RequestMapping(value = "/{cityId}/{hotelId}/{seoPath}/createRequest", method = RequestMethod.POST)
	public String createRequest(@PathVariable Long hotelId, @ModelAttribute VHotelRequest hr) {
		// TODO save request and send email
		String message = null;
		try {
			logger.info("A hotel request has been made: " + hr == null ? "NULL" : hr.getCustomer().getFullName() + ": ");
			HotelRequest tRequest = new HotelRequest();
			Hotel hotel = (Hotel) request.getSession().getAttribute(SESSION_HOTEL);
			if (hotel != null && hotel.getId() == hr.getProductId())
				tRequest.setHotel(hotel);
			else {
				logger.warn("Hotel is lost from session");
				Hotel h = coreService.findById(Hotel.class, hr.getProductId());
				if (h != null) {
					tRequest.setHotel(h);
				}
			}

			Customer c = coreService.insert(hr.getCustomer());
			tRequest.setCustomer(c);
			tRequest.setNumberOfAdults(hr.getNumberOfAdults());
			tRequest.setNumberOfChildren2to9(hr.getNumberOfChildren2to9());
			tRequest.setPlannedPeriod(hr.getPlannedPeriod());
			tRequest.setExpectedTime(hr.getExpectedTime());
			tRequest.setSubject(hr.getSubject());
			tRequest.setDetail(hr.getDetail());
			tRequest.setRequestInfo(new RequestInfo(RequestStatus.DRAFT));

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
}
