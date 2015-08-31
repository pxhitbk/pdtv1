package com.pdt.core.service;

import java.util.List;

import com.pdt.core.model.ContentType;
import com.pdt.core.model.StaticContent;
import com.pdt.core.model.TravelSubject;

public interface TourService {

	/**
	 * @param subject
	 * @return
	 */
	TravelSubject updateTravelSubject(TravelSubject subject);

	/**
	 * @param type
	 * @return
	 */
	List<StaticContent> getStaticContent(ContentType type);
}
