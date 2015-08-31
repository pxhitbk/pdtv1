/**
 * <p>
 * </p>
 *
 * @author hungpx
 * @since
 */
package com.pdt.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pdt.util.Static;

/**
 * @author august
 *
 */
abstract class AbstractController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	protected static final String SESSION_MESSAGE = "message";

	protected String notfound(String reason) {
		logger.warn("User enter invalid page. Reason: " + reason);
		return Static.ERROR_PAGE_NOTFOUND;
	}
}
