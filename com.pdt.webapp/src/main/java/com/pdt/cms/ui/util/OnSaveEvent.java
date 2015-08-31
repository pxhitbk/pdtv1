/**
 * <p>
 * </p>
 * 
 * @author hungpx
 * @since
 */
package com.pdt.cms.ui.util;

import com.pdt.cms.ui.form.CityForm.OnSaveListener;

/**
 * @author august
 *
 */
public interface OnSaveEvent {
	void addOnSaveListener(OnSaveListener listener);

	void fireAction();
}
