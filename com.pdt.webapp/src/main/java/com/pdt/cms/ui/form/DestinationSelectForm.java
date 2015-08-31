/**
 * <p>
 * </p>
 *
 * @author hungpx
 * @since
 */
package com.pdt.cms.ui.form;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pdt.cms.ui.customcomponent.NativeSelectEnum;
import com.pdt.cms.ui.form.CityForm.OnSaveListener;
import com.pdt.cms.ui.util.FieldFactory;
import com.pdt.cms.ui.util.OnSaveEvent;
import com.pdt.core.model.City;
import com.pdt.core.model.Destination;
import com.pdt.core.model.RegionType;
import com.pdt.core.resources.LanguageType;
import com.pdt.core.service.CoreService;
import com.pdt.core.util.QueryCondition;
import com.pdt.core.util.QueryCondition.Operator;
import com.pdt.core.util.ServiceResolver;
import com.pdt.util.Static;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator.EmptyValueException;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.UserError;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

/**
 * @author august
 *
 */
public class DestinationSelectForm extends AbstractForm<Destination> {
	/** */
	private static final long serialVersionUID = -133435717007302034L;
	private static final Logger LOGGER = LoggerFactory.getLogger(DestinationSelectForm.class);
	private Destination destination;
	private City city;

	private BeanFieldGroup<Destination> destBinder;
	private BeanFieldGroup<City> cityBinder;

	private CoreService coreService;

	public DestinationSelectForm(Destination dest) {
		super(dest, Destination.class);
	}

	/**
	 *
	 */
	@Override
	protected void init() {
		coreService = ServiceResolver.findService(CoreService.class);
		destination = getModel();
		if (destination == null) {
			destination = new Destination();
			setModel(destination);
		}
		if (destination.getCityId() == null || destination.getCityId() == 0) {
			city = new City();
		} else {
			city = coreService.findById(City.class, destination.getCityId());
		}

		// RegionType defaultRegion = city.getRegion() == null ? RegionType.NORTH : city.getRegion();
		// List<City> cities = coreService.findByPropertiy(City.class,
		// new QueryCondition("region", Operator.EQ, String.valueOf(defaultRegion.ordinal())));

		destBinder = new BeanFieldGroup<Destination>(Destination.class);
		BeanItem<Destination> destBean = new BeanItem<Destination>(destination);
		destBinder.setItemDataSource(destBean);
		cityBinder = new BeanFieldGroup<City>(City.class);
		BeanItem<City> cityBean = new BeanItem<City>(city);
		cityBinder.setItemDataSource(cityBean);

		// List<String> cityName = coreService.findAllValueOfProperty(City.class, String.class, "city");
		// destination field
		TextField detailAddress = FieldFactory.createTextField(TextField.class, "Địa chỉ", null, false, null);

		final NativeSelectEnum<RegionType> region = new NativeSelectEnum<RegionType>("Mien ", RegionType.values(),
				RegionType.getCaptions(LanguageType.VN));
		cityBinder.bind(region, "region");
		// region.setValue(defaultRegion);

		final ComboBox cityCb = new ComboBox("Thanh pho");
		// for (City city : cities) {
		// cityCb.addItem(city.getCity());
		// cityCb.setItemCaption(city.getCity(),
		// city.getCity() + ", " + city.getRegion().getCaption(Static.CMS_LANGUAGE));
		// }
		cityBinder.bind(cityCb, "city");
		cityCb.setValue(city.getCity());
		cityCb.setImmediate(true);
		TextArea description = FieldFactory.createTextField(TextArea.class, "Mô tả", null, false, null);

		// final HorizontalLayout cityLayout = new HorizontalLayout();

		region.addValueChangeListener(new ValueChangeListener() {

			/** */
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				RegionType itemId = (RegionType) event.getProperty().getValue();
				if (itemId != null) {
					List<City> cities = coreService.findByPropertiy(City.class, new QueryCondition("region",
							Operator.EQ, String.valueOf(itemId.ordinal())));
					cityCb.removeAllItems();
					for (City city : cities) {
						cityCb.addItem(city.getCity());
						cityCb.setItemCaption(city.getCity(),
								city.getCity() + ", " + city.getRegion().getCaption(Static.CMS_LANGUAGE));
					}
				}
			}
		});

		Button addCity = new Button("Cap nhat thanh pho");
		addCity.addClickListener(new ClickListener() {

			/** */
			private static final long serialVersionUID = 6679408780628171349L;

			@Override
			public void buttonClick(ClickEvent event) {

				Window popup = new Window("Cap nhat thanh pho");
				popup.setWidth("500px");
				popup.setHeight("400px");
				popup.center();
				CityForm cityPopup = new CityForm(new City());
				cityPopup.addOnSaveListener(new OnSaveListener() {

					@Override
					public void performAction(OnSaveEvent event) {
						CityForm popup = (CityForm) event;
						City c = popup.getModel();
						if (c != null && c.getId() != 0) {
							List<City> cities = coreService.findByPropertiy(City.class, new QueryCondition("region",
									Operator.EQ, String.valueOf(c.getRegion().ordinal())));
							cityCb.removeAllItems();
							for (City city : cities) {
								cityCb.addItem(city.getCity());
								cityCb.setItemCaption(city.getCity(), city.getCity());
							}
							region.setValue(city.getRegion());
							cityCb.setValue(city.getCity());
						}
					}
				});
				popup.setContent(cityPopup);
				UI.getCurrent().addWindow(popup);

			}
		});

		// cityLayout.setDefaultComponentAlignment(Alignment.BOTTOM_LEFT);

		destBinder.bind(detailAddress, Destination.DETAIL_ADDRESS);
		destBinder.bind(description, "description");

		destBinder.bindMemberFields(this);
		cityBinder.bindMemberFields(this);
		try {
			cityBinder.commit();
		} catch (CommitException e) {
		}

		addComponent(detailAddress);
		addComponents(region, cityCb, addCity);
		addComponent(description);
	}

	@Override
	public boolean commit() {
		try {
			setModified(destBinder.isModified() || cityBinder.isModified());
			cityBinder.commit();
			destBinder.commit();
			List<City> cs = coreService.findByPropertiy(City.class,
					new QueryCondition("city", Operator.EQ, "'" + city.getCity() + "'"));
			if (cs != null && cs.size() == 1)
				city = cs.get(0);
			/*
			 * else { Notification.show("Ten thanh pho khong hop le"); }
			 */

			destination.setCity(city);
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
}
