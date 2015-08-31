/**
 * <p>
 * </p>
 *
 * @author hungpx
 * @since
 */
package com.pdt.service;

import java.util.List;

import com.pdt.core.model.Request;
import com.pdt.core.model.RequestInfo;

/**
 * @author august
 *
 */
public interface PdtRequestService {
	<T extends Request> List<T> getRequests(Class<T> type);

	RequestInfo getRequestInfoByReqId(Long reqId);
}
