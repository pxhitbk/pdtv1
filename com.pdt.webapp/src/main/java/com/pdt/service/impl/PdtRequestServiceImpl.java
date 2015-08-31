/**
 * <p>
 * </p>
 *
 * @author hungpx
 * @since
 */
package com.pdt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.pdt.core.model.HotelRequest;
import com.pdt.core.model.Request;
import com.pdt.core.model.RequestInfo;
import com.pdt.core.model.RequestStatus;
import com.pdt.core.model.TourRequest;
import com.pdt.core.service.CoreService;
import com.pdt.service.PdtRequestService;

/**
 * @author august
 *
 */
@Transactional
public class PdtRequestServiceImpl implements PdtRequestService {

	private static final String GET_REQUEST_INFO_BY_REQ_ID = "SELECT ri FROM " + Request.class.getName()
			+ " r INNER JOIN r.requestInfo ri WHERE r.id = :rid";
	@Autowired
	private CoreService coreService;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.pdt.service.PdtRequestService#getRequests(java.lang.Class)
	 */
	@Override
	public <T extends Request> List<T> getRequests(Class<T> type) {
		String fetchService = null;
		if (TourRequest.class.equals(type))
			fetchService = "tour";
		if (HotelRequest.class.equals(type))
			fetchService = "hotel";
		String query = "SELECT r FROM " + type.getName()
				+ " r INNER JOIN r.requestInfo ri JOIN FETCH r.customer c JOIN FETCH r." + fetchService
				+ " WHERE ri.status <> " + RequestStatus.CLOSED.ordinal() + " ORDER BY r.createdDate DESC";
		return coreService.getEntityManager().createQuery(query, type).getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pdt.service.PdtRequestService#getRequestInfoByReqId(java.lang.Long)
	 */
	@Override
	public RequestInfo getRequestInfoByReqId(Long reqId) {

		return coreService.getEntityManager().createQuery(GET_REQUEST_INFO_BY_REQ_ID, RequestInfo.class)
				.setParameter("rid", reqId).getSingleResult();
	}

}
