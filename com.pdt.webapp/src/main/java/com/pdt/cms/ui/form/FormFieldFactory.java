/**
 *<p>
 *</p>
 * @author hungpx
 * @since
 */
package com.pdt.cms.ui.form;

import static com.pdt.resources.Messages.get;

import com.pdt.cms.ui.dialog.FormFieldProperty;
import com.pdt.util.Static;
import com.vaadin.data.validator.DoubleRangeValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.ui.Field;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextField;

/**
 * @author august
 *
 */
public class FormFieldFactory {
	/**
	 * Create vaadin field base on entity model and it's field type.
	 *
	 * @param modelType
	 * @param formField
	 * @return
	 */
	public static Field<?> createFormField(Class<?> modelType, FormFieldProperty<?> formField) {
		Field<?> vaadinField = null;
		try {
			java.lang.reflect.Field f = modelType.getDeclaredField(formField.getFieldId());
			if (f.getType().equals(Long.TYPE) || f.getType().equals(Integer.TYPE)) {
				vaadinField = new TextField();
				vaadinField.addValidator(new RegexpValidator("[0-9]", true, get(Static.CMS_LANGUAGE,
						"FormFieldFactory.numeric_only")));
			} else if (f.getType().equals(Double.TYPE)) {
				vaadinField = new TextField();
				vaadinField.addValidator(new DoubleRangeValidator(get(Static.CMS_LANGUAGE,
						"FormFieldFactory.invalid_double"), 0.0d, 1000000d));
			} else if (f.getType().equals(String.class)) {
				vaadinField = new TextField();
			} else if (f.getType().isEnum()) {
				vaadinField = new NativeSelect();
			}

			vaadinField.setCaption(formField.getCaption());
			vaadinField.setRequired(formField.isRequired());
			vaadinField.setRequiredError(get(Static.CMS_LANGUAGE, "FormFieldFactory.field_required_error",
					formField.getCaption()));
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		return vaadinField;
	}
}
