/**
 *<p>
 *</p>
 * @author hungpx
 * @since
 */
package com.pdt.cms.ui.form;

import java.util.Collection;

import com.vaadin.ui.NativeSelect;

/**
 * @author august
 *
 */
public class BindNativeSelect<T extends Enum<T>> extends NativeSelect {

	private T options;

	public BindNativeSelect(String caption, Collection<T> options) {
		super(caption, options);
	}
}
