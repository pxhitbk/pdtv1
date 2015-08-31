/**
 * <p>
 * </p>
 *
 * @author hungpx
 * @since
 */
package com.pdt.cms.ui.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.data.Container;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.AbstractTextField;

/**
 * @author august
 *
 */
public class FieldFactory {
	private static Logger LOGGER = LoggerFactory.getLogger(FieldFactory.class);

	public static <T extends AbstractTextField> T createTextField(
			Class<T> textfieldType, String caption, String defaultValue, boolean required, String requiredMessage) {
		T tf = null;
		try {
			tf = textfieldType.newInstance();
			tf.setCaption(caption);
			tf.setImmediate(true);
			tf.setRequired(required);
			if (defaultValue != null)
				tf.setValue(defaultValue);
			if (required && requiredMessage != null)
				tf.setRequiredError(requiredMessage);
			tf.setNullRepresentation("");
		} catch (InstantiationException e) {
			LOGGER.error("cannot instance text field", e);
		} catch (IllegalAccessException e) {
			LOGGER.error("cannot instance text field", e);
		}

		return tf;
	}

	public static <T extends AbstractSelect> T createSelectField(
			Class<T> selectType, String caption, List<?> options, String[] displayOptionCaptions, boolean required,
			String requiredMessage) {
		T selection = null;
		try {
			selection = selectType.newInstance();
			if (displayOptionCaptions == null) {
				displayOptionCaptions = new String[options.size()];
			}
			if (options.size() != displayOptionCaptions.length)
				throw new IllegalArgumentException("options length and displayValue length must be equal, "
						+ options.size() + " # " + displayOptionCaptions.length);
			final Container c = new IndexedContainer();
			selection.setContainerDataSource(c);
			for (int i = 0; i < options.size(); i++) {
				Object option = options.get(i);
				c.addItem(option);
				selection.setItemCaption(option, displayOptionCaptions[i] == null ? String.valueOf(option)
						: displayOptionCaptions[i]);
			}

			selection.setImmediate(true);
			selection.setRequired(required);
			if (required)
				selection.setRequiredError(requiredMessage);
			selection.setCaption(caption);

		} catch (InstantiationException e) {
			LOGGER.error("cannot instance select field", e);
		} catch (IllegalAccessException e) {
			LOGGER.error("cannot instance select field", e);
		}
		return selection;
	}
}
