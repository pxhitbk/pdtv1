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
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ImmutableList;
import com.pdt.core.model.Album;
import com.pdt.core.model.Articles;
import com.pdt.core.model.City;
import com.pdt.core.model.Image;
import com.pdt.core.model.Priority;
import com.pdt.core.model.RegionType;
import com.pdt.core.model.Tour;
import com.pdt.core.model.TravelService;
import com.pdt.core.model.TravelSubject;
import com.pdt.core.model.TravelSubjectTourAssociation;
import com.pdt.core.service.CoreService;
import com.pdt.core.util.ServiceResolver;
import com.pdt.service.CreateDummyData;

/**
 * Service to create dummy data in development process
 *
 * @author august
 *
 */
@Transactional
public class CreateDummyDataImpl implements CreateDummyData {
	private CoreService coreService = ServiceResolver.findService(CoreService.class);
	static Logger LOGGER = LoggerFactory.getLogger(CreateDummyDataImpl.class);

	public CreateDummyDataImpl() {
		// createSubjectTours();
		// createServices();
		// createCity();
	}

	/**
	 *
	 */
	private void createCity() {
		City hanoi = new City("Hanoi", RegionType.NORTH, "Viet Nam");

		City haiphong = new City("Haiphong", RegionType.NORTH, "Viet Nam");
		City thainguyen = new City("Thai Nguyen", RegionType.NORTH, "Viet Nam");
		City quangninh = new City("Quang Ninh", RegionType.NORTH, "Viet Nam");
		City danang = new City("Da Nang", RegionType.NORTH, "Viet Nam");
		City hcm = new City("Hochiminh", RegionType.NORTH, "Viet Nam");
		coreService.insert(ImmutableList.of(haiphong, hanoi, thainguyen, quangninh, danang, hcm));
	}

	/**
	 *
	 */
	private void createServices() {
		String[] serviceName = { "Dich vu thue xe", "Van tai duong thuy", "Lam visa" };
		for (String service : serviceName) {
			TravelService ts = new TravelService();
			ts.setName(service);
			coreService.insert(ts);
		}

	}

	@Override
	public List<TravelSubjectTourAssociation> createSubjectTours() {
		String tourName[] = { "Du lich Ha Long", "Kham pha dao Cat Ba", "Ngam tuyet roi tai SaPa", "Tham quan thu do",
				"Tham dan toc HMong" };
		List<Tour> tours = new ArrayList<>();
		Album a1 = new Album();
		a1.setName("thumbnail");
		coreService.insert(a1);

		File i = new File("src/main/webapp/resources/assets/Uploads/tour-6.jpg");

		for (String element : tourName) {
			Image img = new Image();
			try {
				img.setFileName(i.getName());
				img.setData(Files.readAllBytes(i.toPath()));
			} catch (IOException e) {
				LOGGER.error("Cannot read image " + e.getMessage(), e);
			}
			img.setAlbum(a1);
			System.out.println("Inserting tour '" + element);
			System.out.println("ROUND IMG : " + img.getId());
			Tour t = new Tour();
			Articles a = new Articles("Randonnée au pays des Thai - 8 jours / 7 nuits", "Pham Hung", "");
			t.setName(element);
			t.setArticles(a);
			t.setBeginDate(new Date());
			t.setEndDate(new Date(2014, 11, 12));
			t.setFromPrice(10.2d);
			t.setPriority(Priority.HIGH);
			t.setRegionType(RegionType.NORTH);
			t.setThumbnail(img);
			t = coreService.insert(t);
			tours.add(t);
		}

		TravelSubject culture = new TravelSubject("Van hoa", "Du lich van hoa Viet Nam");
		TravelSubject city = new TravelSubject("Thanh thi", "Du lich cac tinh thanh Viet Nam");

		culture = coreService.insert(culture);
		city = coreService.insert(city);

		TravelSubjectTourAssociation cultureTour = new TravelSubjectTourAssociation(tours.get(1), culture);
		TravelSubjectTourAssociation cultureTour1 = new TravelSubjectTourAssociation(tours.get(4), culture);
		TravelSubjectTourAssociation cityTour = new TravelSubjectTourAssociation(tours.get(0), culture);
		TravelSubjectTourAssociation cityTour1 = new TravelSubjectTourAssociation(tours.get(3), culture);
		List<TravelSubjectTourAssociation> subjectTour = ImmutableList.of(cultureTour, cultureTour1, cityTour,
				cityTour1);

		return coreService.insert(subjectTour);
	}
}
