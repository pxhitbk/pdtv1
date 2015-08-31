/**
 *<p>
 *</p>
 * @author hungpx
 * @since
 */
package com.pdt.cms.ui.customcomponent;

import com.vaadin.data.Container;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.NativeSelect;

/**
 * @author august
 *
 */
public class NativeSelectEnum<T extends Enum<T>> extends NativeSelect {

	private static final long serialVersionUID = -8995349824276216795L;

	/**
	 * Construct a dropdown-list
	 *
	 * @param caption
	 *            Select box caption
	 * @param options
	 *            Enum values in array
	 * @param displayOptionCaptions
	 *            if displayOptionCaptions is null, default item caption will be enum item name
	 * @param defaultOption
	 */
	public NativeSelectEnum(String caption, T[] options, String[] displayOptionCaptions) {

		if (displayOptionCaptions == null) {
			displayOptionCaptions = new String[options.length];
		}
		if (options.length != displayOptionCaptions.length)
			throw new IllegalArgumentException("options length and displayValue length must be equal, "
					+ options.length + " # " + displayOptionCaptions.length);
		final Container c = new IndexedContainer();
		setContainerDataSource(c);
		for (int i = 0; i < options.length; i++) {
			c.addItem(options[i]);
			setItemCaption(options[i], displayOptionCaptions[i] == null ? options[i].name() : displayOptionCaptions[i]);
		}

		setCaption(caption);
	}
}
