package com.pdt.core.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.pdt.core.model.ContentType;
import com.pdt.core.model.StaticContent;
import com.pdt.core.model.TravelSubject;
import com.pdt.core.service.CoreService;
import com.pdt.core.service.TourService;

@Transactional
public class TourServiceImpl implements TourService {

	private static final String GET_STATIC_CONTENT = "SELECT c FROM " + StaticContent.class.getName()
			+ " c WHERE c.contentType = :ct";

	@Autowired
	private CoreService coreService;

	@Override
	public TravelSubject updateTravelSubject(TravelSubject subject) {
		if (subject.isStick()) {
			coreService.getEntityManager().createQuery("UPDATE " + TravelSubject.class.getName() + " SET stick = 0")
					.executeUpdate();
		}
		return coreService.insertOrUpdate(subject);
	}

	@Override
	public List<StaticContent> getStaticContent(ContentType type) {
		List<StaticContent> contents = coreService.getEntityManager()
				.createQuery(GET_STATIC_CONTENT, StaticContent.class).setParameter("ct", type).getResultList();

		return contents;
	}

}
