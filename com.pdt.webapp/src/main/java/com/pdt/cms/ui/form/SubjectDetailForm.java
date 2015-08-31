/**
 * <p>
 * </p>
 *
 * @author hungpx
 * @since
 */
package com.pdt.cms.ui.form;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pdt.core.model.TravelSubject;
import com.vaadin.data.Validator.EmptyValueException;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.UserError;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

/**
 * @author august
 *
 */
public class SubjectDetailForm extends FormLayout {
	private static final Logger LOGGER = LoggerFactory.getLogger(SubjectDetailForm.class);
	/** */
	private static final long serialVersionUID = -4860861645299380956L;

	private TravelSubject subject;
	private FieldGroup binder;

	/**
	 * @param model
	 */
	public SubjectDetailForm(TravelSubject model) {
		this.subject = model;
		initLayout();
	}

	/**
	 *
	 */
	private void initLayout() {
		if (subject == null) {
			subject = new TravelSubject();
		}

		BeanItem<TravelSubject> sbjItem = new BeanItem<TravelSubject>(subject);
		binder = new FieldGroup(sbjItem);

		TextField name = new TextField("Subject name");
		name.setRequired(true);
		name.setRequiredError("Name is required");

		CheckBox isStick = new CheckBox("Stick");

		name.setImmediate(true);
		TextArea description = new TextArea("Description");
		description.setImmediate(true);

		addComponent(name);
		addComponent(isStick);
		addComponent(description);

		binder.bind(name, "name");
		binder.bind(isStick, "stick");
		binder.bind(description, "description");
		binder.bindMemberFields(this);
	}

	public boolean commit() {
		try {
			binder.commit();
		} catch (CommitException e) {
			for (Component c : this)
				if (c instanceof AbstractField<?>) {
					try {
						((AbstractField<?>) c).validate();
					} catch (InvalidValueException ex) {
						((AbstractField<?>) c).setComponentError(new UserError(ex.getMessage()));
					}
				}
			return false;
		} catch (EmptyValueException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return true;
	}

	public TravelSubject getModel() {
		return subject;
	}

}
