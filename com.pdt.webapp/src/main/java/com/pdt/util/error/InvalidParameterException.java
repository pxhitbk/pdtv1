/**
 * <p>
 * </p>
 *
 * @author hungpx
 * @since
 */
package com.pdt.util.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author august
 *
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Invalid url")
public class InvalidParameterException extends RuntimeException {

	/** */
	private static final long serialVersionUID = 8015782161929602983L;

}
