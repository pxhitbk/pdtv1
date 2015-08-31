package com.pdt.webapp.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.pdt.core.model.Articles;
import com.pdt.core.model.ContactMessage;
import com.pdt.core.model.CustomTourRequest;
import com.pdt.core.model.Customer;
import com.pdt.core.model.TravelEvent;
import com.pdt.core.model.TravelSubject;
import com.pdt.core.service.CoreService;
import com.pdt.core.util.QueryCondition;
import com.pdt.core.util.QueryCondition.Operator;
import com.pdt.service.PdtTourService;
import com.pdt.util.PdtUtils;
import com.pdt.util.Static;
import com.pdt.web.view.model.VHomeQueryParams;
import com.pdt.web.view.model.VTour;
import com.pdt.webapp.model.HomePageImage;
import com.pdt.webapp.validator.ContactMessageValidator;

@Controller
public class HomeController extends AbstractController {

	@Autowired
	private CoreService coreService;
	@Autowired
	private PdtTourService tourService;
	@Autowired
	private ServletContext context;

	@RequestMapping(value = "/listtour")
	public ModelAndView navigateToFirstPage() {
		ModelAndView mav = new ModelAndView("listtour");
		return mav;
	}

	@RequestMapping({ "/", "/home" })
	public String home(Model model, HttpServletRequest request, HttpServletResponse response) {
		List<HomePageImage> slideImages = coreService.findAll(HomePageImage.class);

		for (HomePageImage i : slideImages) {
			if (i.getData() != null) {

				try {
					PdtUtils.writingImageFile(i, context.getRealPath(Static.UPLOAD_SLIDE_IMG_DISK_PATH));
					i.setTempPath(Static.UPLOAD_SLIDE_IMG_URI_PATH + i.getFileName());
				} catch (IOException e) {
					logger.error("Cannot wrting slide image to disk", e);
				}
			}
		}

		List<VTour> favourTours = tourService.getMostFavorTours(context.getRealPath(Static.UPLOAD_THUMBNAIL_DISK_PATH));
		List<VTour> stickSubjectTours = tourService.getToursByStickSubject(context
				.getRealPath(Static.UPLOAD_THUMBNAIL_DISK_PATH));
		List<VTour> recommendTours = tourService.getTopRecommendTour(context
				.getRealPath(Static.UPLOAD_THUMBNAIL_DISK_PATH));
		// List<VEvent> events = tourService.getMostRecentVEvents();

		List<TravelSubject> stickedSubjects = coreService.findByPropertiy(TravelSubject.class, new QueryCondition(
				"stick", Operator.EQ, "1"));
		String stickedSubjectName = null;
		if (stickedSubjects != null && !stickedSubjects.isEmpty())
			stickedSubjectName = stickedSubjects.get(0).getName();

		List<TravelEvent> events = tourService.fetchMostRecentEvents();

		model.addAttribute("stickedSubjectName", stickedSubjectName);
		model.addAttribute("slideImages", slideImages);
		model.addAttribute("queryParams", new VHomeQueryParams());
		model.addAttribute("favourTours", favourTours);
		model.addAttribute("recommendTours", recommendTours);
		model.addAttribute("stickSubjectTours", stickSubjectTours);
		model.addAttribute("events", events);

		return "home";
	}

	@RequestMapping(value = "/aboutus")
	public String aboutus(Model model) {
		Articles aboutUs = tourService.getAboutUsArticles();
		model.addAttribute("articles", aboutUs);
		return "aboutus";
	}

	@RequestMapping(value = "/service/{serviceId}")
	public String service(Model model, @PathVariable Long serviceId) {
		Articles service = tourService.getArticlesByContentId(serviceId);
		if (service == null)
			return notfound("Cannot find service with id " + serviceId);
		model.addAttribute("articles", service);

		return "aboutus";
	}

	@RequestMapping(value = "/tip/{tip}")
	public String tip(Model model, @PathVariable Long tip) {
		Articles service = tourService.getArticlesByContentId(tip);
		if (service == null)
			return notfound("Cannot find tip with id " + tip);
		model.addAttribute("articles", service);
		return "aboutus";
	}

	@RequestMapping(value = "/contactus")
	public String contactUs(Model model) {
		Customer customer = new Customer();
		ContactMessage newMessage = new ContactMessage();
		newMessage.setCustomer(customer);
		model.addAttribute("newMessage", newMessage);
		return "contactus";
	}

	@RequestMapping(value = "/sendContactMessage", method = RequestMethod.POST)
	public String sendContactMessage(@ModelAttribute("newMessage") ContactMessage message, BindingResult result) {
		ContactMessageValidator validator = new ContactMessageValidator();
		validator.validate(message, result);
		if (result.hasErrors()) {
			return "contactus";
		}
		Customer customer = coreService.insert(message.getCustomer());
		message.setCustomer(customer);
		coreService.insert(message);
		return "submitsuccess";
	}

	@RequestMapping(value = "/tourdesign", method = RequestMethod.GET)
	public String designTour(Model model) {
		CustomTourRequest tourRequest = new CustomTourRequest();
		Customer customer = new Customer();
		tourRequest.setCustomer(customer);
		model.addAttribute("newRequest", tourRequest);
		return "tourdesign";
	}

	@RequestMapping(value = "/createDesignedTour", method = RequestMethod.POST)
	public String createDesignedTour(@ModelAttribute CustomTourRequest request, BindingResult result) {
		Customer c = coreService.insert(request.getCustomer());
		request.setCustomer(c);
		coreService.insert(request);
		return "submitsuccess";
	}
}
