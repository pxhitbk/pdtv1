/**
 *<p>
 *</p>
 * @author hungpx
 * @since
 */
package com.pdt.cms.data.container;

import java.util.List;

/**
 * @author hungpx
 *
 */
public interface JpaContainerAdapter {

	/**
	 * @return
	 */
	RowItem getAllRows();

	/**
	 * @return
	 */
	List<Long> getAllItemIds();

}
