/**
 * <p>
 * </p>
 *
 * @author hungpx
 * @since
 */
package com.pdt.cms.ui.form;

import java.util.ArrayList;
import java.util.List;

import com.pdt.cms.ui.customcomponent.NativeSelectEnum;
import com.pdt.cms.ui.util.OnSaveEvent;
import com.pdt.core.model.City;
import com.pdt.core.model.RegionType;
import com.pdt.core.resources.LanguageType;
import com.pdt.core.service.CoreService;
import com.pdt.core.util.ServiceResolver;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.UserError;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;

/**
 * @author august
 *
 */
public class CityForm extends AbstractForm<City> implements OnSaveEvent, ClickListener {

	/** */
	private static final long serialVersionUID = 4704023906068972130L;
	private FieldGroup fieldGroup;
	private List<OnSaveListener> listeners;

	public CityForm(City city) {
		super(city, City.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vaadin.ui.CustomField#initContent()
	 */
	@Override
	protected void init() {
		if (getModel() == null)
			setModel(new City());
		setCaption("Thêm thành phố");
		TextField cityName = new TextField("Thanh pho");
		NativeSelectEnum<RegionType> region = new NativeSelectEnum<RegionType>("Mien ", RegionType.values(),
				RegionType.getCaptions(LanguageType.VN));

		TextField country = new TextField("Quoc gia");

		cityName.setRequired(true);
		region.setRequired(true);
		country.setRequired(true);

		addComponent(cityName);
		addComponent(region);
		addComponent(country);

		fieldGroup = new BeanFieldGroup<City>(City.class);
		BeanItem<City> beanItem = new BeanItem<City>(getModel());
		fieldGroup.setItemDataSource(beanItem);

		fieldGroup.bind(region, "region");
		fieldGroup.bind(cityName, "city");
		fieldGroup.bind(country, "country");

		country.setValue("Vietnam");
		region.setValue(RegionType.NORTH);
		Button save = new Button("Save", this);
		addComponent(save);

		fieldGroup.bindMemberFields(this);
	}

	@Override
	public void addOnSaveListener(OnSaveListener listener) {
		if (listeners == null)
			listeners = new ArrayList<OnSaveListener>();
		listeners.add(listener);
	}

	@Override
	public void fireAction() {
		if (listeners != null && listeners.size() > 0)
			for (OnSaveListener l : listeners)
				l.performAction(this);
	}

	public interface OnSaveListener {
		void performAction(com.pdt.cms.ui.util.OnSaveEvent event);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.vaadin.ui.Button.ClickListener#buttonClick(com.vaadin.ui.Button.ClickEvent)
	 */
	@Override
	public void buttonClick(ClickEvent event) {
		try {
			if (fieldGroup.isModified()) {
				fieldGroup.commit();
				ServiceResolver.findService(CoreService.class).insert(getModel());
				fireAction();
				City c = new City();
				c.setCountry(getModel().getCountry());
				c.setRegion(getModel().getRegion());
				setModel(c);
				BeanItem<City> beanItem = new BeanItem<City>(c);
				fieldGroup.setItemDataSource(beanItem);
				Notification.show("Cập nhật thành công");
			}
		} catch (CommitException e) {
			for (Component c : this)
				if (c instanceof AbstractField<?>) {
					try {
						((AbstractField<?>) c).validate();
					} catch (InvalidValueException ex) {
						((AbstractField<?>) c).setComponentError(new UserError(ex.getMessage()));
					}
				}
		}
	}
}
