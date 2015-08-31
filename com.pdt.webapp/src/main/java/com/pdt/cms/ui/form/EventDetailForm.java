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

import com.pdt.cms.ui.customcomponent.NativeSelectEnum;
import com.pdt.core.model.EventScope;
import com.pdt.core.model.TravelEvent;
import com.vaadin.data.Validator.EmptyValueException;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.UserError;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

/**
 * @author august
 *
 */
public class EventDetailForm extends FormLayout {

	private static final Logger LOGGER = LoggerFactory.getLogger(EventDetailForm.class);
	/** */
	private static final long serialVersionUID = -4860861645299380956L;

	private TravelEvent event;
	// private Destination destination;
	private BeanFieldGroup<TravelEvent> eventBinder;
	// private BeanFieldGroup<Destination> destBinder;

	boolean isModified;

	/**
	 * @param model
	 */
	public EventDetailForm(TravelEvent model) {
		this.event = model;
		initLayout();
	}

	/**
	 *
	 */
	private void initLayout() {
		// CoreService coreService = ServiceResolver.findService(CoreService.class);
		if (event == null) {
			event = new TravelEvent();
		}
		// if (event.getDestinationId() == null || event.getDestinationId() == 0)
		// destination = new Destination();
		// else
		// destination = coreService.findById(Destination.class, event.getId());

		BeanItem<TravelEvent> eventItem = new BeanItem<TravelEvent>(event);
		eventBinder = new BeanFieldGroup<TravelEvent>(TravelEvent.class);
		eventBinder.setItemDataSource(eventItem);
		// BeanItem<Destination> destItem = new BeanItem<Destination>(destination);
		// destBinder = new BeanFieldGroup<Destination>(Destination.class);
		// destBinder.setItemDataSource(destItem);

		TextField name = new TextField("Event name"/* , eventItem.getItemProperty("name") */);
		name.addValidator(new StringLengthValidator("Required at least 2 characters", 2, 100000, false));
		NativeSelectEnum<EventScope> eventScrope = new NativeSelectEnum<EventScope>("Scope", EventScope.values(), null);

		DateField beginDate = new DateField("beginDate"/* , eventItem.getItemProperty("beginDate") */);
		DateField endDate = new DateField("endDate"/* , eventItem.getItemProperty("endDate") */);
		TextArea description = new TextArea("Description"/* , eventItem.getItemProperty("description") */);

		// TextField destAddress = FieldFactory.createTextField(TextField.class, "Address", null, false, null);
		// TextArea destDesc = FieldFactory.createTextField(TextArea.class, "Destination description", null, false,
		// null);

		name.setNullRepresentation("");
		name.setRequired(true);
		name.setRequiredError("Name is required");
		beginDate.setRequired(true);
		endDate.setRequired(true);
		eventScrope.setRequired(true);
		eventScrope.setRequiredError("required");

		name.setImmediate(true);
		beginDate.setImmediate(true);
		endDate.setImmediate(true);
		description.setImmediate(true);
		eventScrope.setImmediate(true);
		// destAddress.setImmediate(true);

		addComponent(name);
		addComponent(beginDate);
		addComponent(endDate);
		addComponent(description);
		addComponent(eventScrope);
		// addComponent(destAddress);
		// addComponent(destDesc);

		eventBinder.bind(name, "name");
		eventBinder.bind(eventScrope, "eventScope");
		eventScrope.setValue(event.getEventScope() != null ? event.getEventScope() : EventScope.LOCAL);

		eventBinder.bind(beginDate, "beginDate");
		eventBinder.bind(endDate, "endDate");
		eventBinder.bind(description, "description");
		// destBinder.bind(destAddress, "detailAddress");
		eventBinder.bindMemberFields(this);
		// destBinder.bindMemberFields(this);
	}

	public boolean commit() {
		try {
			setModified(eventBinder.isModified() /* || destBinder.isModified() */);
			eventBinder.commit();
			// destBinder.commit();
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

	public TravelEvent getModel() {
		return event;
	}

	public boolean isModified() {
		return isModified;
	}

	public void setModified(boolean isModified) {
		this.isModified = isModified;
	}
}
