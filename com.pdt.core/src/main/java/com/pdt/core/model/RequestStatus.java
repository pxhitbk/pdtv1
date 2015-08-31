/**
 * <p>
 * </p>
 *
 * @author hungpx
 * @since
 */
package com.pdt.core.model;

/**
 * <p>
 * Describe status of the request by following work flow:<br/>
 * <ul>
 * <li>1. Customer has made request success: DRAFT
 * <li>2. System sent response email for customer to confirm their request: RESPONSED.
 * <li>3. Customer confirmed the request by clicked the confirmation link was provided in email: CONFIRMED
 * <li>4. Admin is trying contact to customer for more information: AUTHORIZING
 * <li>5. Admin contacted success to customer and ensured the authorization of request: AUTHORIZED
 * <li>6. A deal has been made: GOT_DEAL
 * <li>7. Complete processing: COMPLETE
 * <li>8. Admin can close request anytime: CLOSED
 * </ul>
 * </p>
 * @author august
 *
 */
public enum RequestStatus {
	DRAFT(1), RESPONSED(2), CONFIRMED(3), AUTHORIZING(4), AUTHORIZED(5), GOT_DEAL(6), COMPLETE(7), CLOSED(8);

	private final int order;

	RequestStatus(int order) {
		this.order = order;
	}

	public int getOrder() {
		return order;
	}

}
