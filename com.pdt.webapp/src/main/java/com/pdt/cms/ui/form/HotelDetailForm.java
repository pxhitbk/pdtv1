/**
 * <p>
 * </p>
 *
 * @author hungpx
 * @since
 */
package com.pdt.cms.ui.form;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pdt.cms.ui.customcomponent.ImageUploader;
import com.pdt.cms.ui.customcomponent.NativeSelectEnum;
import com.pdt.cms.ui.form.CityForm.OnSaveListener;
import com.pdt.cms.ui.util.FieldFactory;
import com.pdt.cms.ui.util.OnSaveEvent;
import com.pdt.core.model.Album;
import com.pdt.core.model.Articles;
import com.pdt.core.model.City;
import com.pdt.core.model.Hotel;
import com.pdt.core.model.HotelLevel;
import com.pdt.core.model.Image;
import com.pdt.core.model.RegionType;
import com.pdt.core.resources.LanguageType;
import com.pdt.core.service.CoreService;
import com.pdt.core.util.QueryCondition;
import com.pdt.core.util.QueryCondition.Operator;
import com.pdt.core.util.ServiceResolver;
import com.pdt.service.PdtTourService;
import com.pdt.util.Static;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator.EmptyValueException;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.StreamResource;
import com.vaadin.server.UserError;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Window;

/**
 * @author august
 *
 */
public class HotelDetailForm extends FormLayout {
	/** */
	private static final long serialVersionUID = 5243499270283352466L;
	private static final Logger LOGGER = LoggerFactory.getLogger(HotelDetailForm.class);
	private Hotel hotel;
	private City city;
	private boolean modified;

	private ImageUploader receiver;
	private final Embedded image = new Embedded("Uploaded Image");
	private Image thumbnail;

	private BeanFieldGroup<Hotel> destBinder;
	private BeanFieldGroup<City> cityBinder;
	private BeanFieldGroup<Articles> articlesBinder;

	private CoreService coreService = ServiceResolver.findService(CoreService.class);

	public HotelDetailForm(Hotel hotel) {
		this.hotel = hotel;
		init();
	}

	protected void init() {
		if (hotel == null) {
			hotel = new Hotel();
		}
		if (hotel.getCityId() == null || hotel.getCityId() == 0) {
			city = new City();
		} else {
			city = coreService.findById(City.class, hotel.getCityId());
		}
		Articles articles = null;
		if (hotel.getArticlesId() == null)
			articles = new Articles();
		else
			articles = ServiceResolver.findService(CoreService.class).findById(Articles.class, hotel.getArticlesId());

		RegionType defaultRegion = city.getRegion() == null ? RegionType.NORTH : city.getRegion();
		List<City> cities = coreService.findByPropertiy(City.class,
				new QueryCondition("region", Operator.EQ, String.valueOf(defaultRegion.ordinal())));

		destBinder = new BeanFieldGroup<Hotel>(Hotel.class);
		BeanItem<Hotel> destBean = new BeanItem<Hotel>(hotel);
		destBinder.setItemDataSource(destBean);
		cityBinder = new BeanFieldGroup<City>(City.class);
		BeanItem<City> cityBean = new BeanItem<City>(city);
		cityBinder.setItemDataSource(cityBean);
		BeanItem<Articles> articlesItem = new BeanItem<Articles>(articles);
		articlesBinder = new BeanFieldGroup<Articles>(Articles.class);
		articlesBinder.setItemDataSource(articlesItem);

		// List<String> cityName = coreService.findAllValueOfProperty(City.class, String.class, "city");
		// destination field
		TextField name = FieldFactory.createTextField(TextField.class, "Name", hotel.getName(), true, null);
		final NativeSelectEnum<HotelLevel> level = new NativeSelectEnum<HotelLevel>("Level ", HotelLevel.values(), null);
		level.setRequired(true);
		TextField fromPrice = FieldFactory.createTextField(TextField.class, "From price (€)", null, false, null);
		TextField toPrice = FieldFactory.createTextField(TextField.class, "To price (€)", null, false, null);

		TextField detailAddress = FieldFactory.createTextField(TextField.class, "Địa chỉ", null, false, null);
		final NativeSelectEnum<RegionType> region = new NativeSelectEnum<RegionType>("Mien ", RegionType.values(),
				RegionType.getCaptions(LanguageType.VN));
		cityBinder.bind(region, "region");
		region.setValue(defaultRegion);

		final ComboBox cityCb = new ComboBox("Thanh pho");
		for (City city : cities) {
			cityCb.addItem(city.getCity());
			cityCb.setItemCaption(city.getCity(),
					city.getCity() + ", " + city.getRegion().getCaption(Static.CMS_LANGUAGE));
		}
		cityBinder.bind(cityCb, "city");
		cityCb.setValue(city.getCity());
		cityCb.setImmediate(true);
		TextArea description = FieldFactory.createTextField(TextArea.class, "Mô tả", null, false, null);

		// final HorizontalLayout cityLayout = new HorizontalLayout();

		region.addValueChangeListener(new ValueChangeListener() {

			/** */
			private static final long serialVersionUID = 317240622455230106L;

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

		final CityForm cityPopup = new CityForm(new City());
		cityPopup.addOnSaveListener(new OnSaveListener() {

			@Override
			public void performAction(OnSaveEvent event) {
				CityForm popup = (CityForm) event;
				City c = popup.getModel();
				if (c != null && c.getId() != 0) {
					List<City> cities = coreService.findByPropertiy(City.class, new QueryCondition("region",
							Operator.EQ, String.valueOf(c.getRegion())));
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

		Button addCity = new Button("Add");
		addCity.addClickListener(new ClickListener() {

			/** */
			private static final long serialVersionUID = 6679408780628171349L;

			@Override
			public void buttonClick(ClickEvent event) {

				Window popup = new Window("Cap nhat thanh pho");
				popup.setWidth("500px");
				popup.setHeight("400px");
				popup.center();
				popup.setContent(new CityForm(new City()));
				UI.getCurrent().addWindow(popup);

			}
		});
		addComponents(name, level, fromPrice, toPrice, detailAddress);
		addComponents(region, cityCb, addCity);
		addComponent(description);

		if (hotel.getThumbnailId() != null && hotel.getThumbnailId() != 0) {
			thumbnail = ServiceResolver.findService(CoreService.class).findById(Image.class, hotel.getThumbnailId());
			final byte[] imgData = thumbnail != null ? thumbnail.getData() : null;
			if (imgData != null) {
				StreamResource.StreamSource imageSource = new StreamResource.StreamSource() {
					private static final long serialVersionUID = -7924008988643152334L;

					@Override
					public InputStream getStream() {
						return new ByteArrayInputStream(imgData);
					}
				};

				StreamResource imageResource = new StreamResource(imageSource, thumbnail.getFileName());
				imageResource.setCacheTime(0);
				image.setSource(imageResource);
				// image.requestRepaint();
				imageResource.setFilename(thumbnail.getFileName());
			}
		} else {
			image.setVisible(false);
			thumbnail = new Image();
		}
		image.setWidth("200px");
		receiver = new ImageUploader(image);

		// Create the upload with a caption and set receiver later
		Upload upload = new Upload("Thumbnail", receiver);
		upload.setButtonCaption("Start Upload");
		upload.addSucceededListener(receiver);

		// Put the components in a panel
		addComponents(upload, image);

		RichTextArea basicContent = new RichTextArea("Lead", articlesItem.getItemProperty("lead"));
		RichTextArea articleContent = new RichTextArea("Detail", articlesItem.getItemProperty("content"));
		articleContent.setImmediate(true);
		addComponent(basicContent);
		addComponent(articleContent);
		hotel.setArticles(articles);

		// cityLayout.setDefaultComponentAlignment(Alignment.BOTTOM_LEFT);

		destBinder.bind(name, "name");
		destBinder.bind(level, "hotelLevel");
		destBinder.bind(detailAddress, Hotel.DETAIL_ADDRESS);
		destBinder.bind(description, "description");
		articlesBinder.bind(basicContent, "lead");
		articlesBinder.bind(articleContent, "content");

		destBinder.bindMemberFields(this);
		cityBinder.bindMemberFields(this);
	}

	public boolean commit() {
		File file = receiver.getFile();
		if (file == null && thumbnail.getId() == 0) {
			Notification.show("Vui long upload hinh dai dien va dien thong tin can thiet");
			return false;
		}
		try {
			setModified(destBinder.isModified() || cityBinder.isModified() || receiver.isModified()
					|| articlesBinder.isModified());
			cityBinder.commit();
			destBinder.commit();
			articlesBinder.commit();
			List<City> cs = coreService.findByPropertiy(City.class,
					new QueryCondition("city", Operator.EQ, "'" + city.getCity() + "'"));
			if (cs != null && cs.size() == 1)
				city = cs.get(0);
			else {
				Notification.show("Ten thanh pho khong hop le");
			}

			hotel.setCity(city);

			if (receiver.getFile() != null) {
				thumbnail.setAlbum(ServiceResolver.findService(PdtTourService.class).findAlbumByName(
						Album.THUMBNAIL_ALBUM_NAME, true));
				thumbnail.setFileName(file.getName());
				thumbnail.setData(Files.readAllBytes(file.toPath()));
				hotel.setThumbnail(thumbnail);
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
			return false;
		} catch (EmptyValueException e) {
			LOGGER.error(e.getMessage(), e);
			return false;
		} catch (IOException e) {
			LOGGER.error("Cannot read thumbnail data for hotel " + hotel.getId());
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.pdt.cms.ui.form.AbstractForm#getModel()
	 */
	public Hotel getModel() {
		return hotel;
	}

	public boolean isModified() {
		return modified;
	}

	public void setModified(boolean modified) {
		this.modified = modified;
	}
}
