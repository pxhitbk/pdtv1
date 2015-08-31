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
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pdt.cms.ui.customcomponent.ImageUploader;
import com.pdt.cms.ui.customcomponent.NativeSelectEnum;
import com.pdt.cms.ui.util.FieldFactory;
import com.pdt.cms.ui.validator.NumericValidator;
import com.pdt.core.model.Album;
import com.pdt.core.model.Articles;
import com.pdt.core.model.AvailableState;
import com.pdt.core.model.Image;
import com.pdt.core.model.Priority;
import com.pdt.core.model.RegionType;
import com.pdt.core.model.SeasonType;
import com.pdt.core.model.Tour;
import com.pdt.core.service.CoreService;
import com.pdt.core.util.ServiceResolver;
import com.pdt.service.PdtTourService;
import com.pdt.util.Static;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator.EmptyValueException;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.StreamResource;
import com.vaadin.server.UserError;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;

/**
 * @author august
 *
 */
public class TourDetailForm extends FormLayout {

	/** */
	private static final long serialVersionUID = 6134179403752332217L;
	private static final Logger LOGGER = LoggerFactory.getLogger(TourDetailForm.class);

	private Tour tour;

	private ImageUploader receiver;
	private final Embedded image = new Embedded("Uploaded Image");
	private Image thumbnail;
	private FieldGroup binder;

	private boolean isModified;

	/**
	 * @param model
	 */
	public TourDetailForm(Tour model) {
		this.tour = model;
		initLayout();
	}

	/**
	 *
	 */
	private void initLayout() {

		if (tour == null) {
			tour = new Tour();
			tour.setMinDay(0);
			tour.setMaxDay(0);
			tour.setRecommendOrder(0);
		}
		image.setWidth("200");

		BeanItem<Tour> tourItem = new BeanItem<Tour>(tour);
		binder = new FieldGroup(tourItem);

		TextField name = new TextField("Tour name");
		name.setRequiredError("Name is required");

		TextArea description = new TextArea("Description", tourItem.getItemProperty("description"));
		TextField minDay = new TextField("Minimum day");
		final TextField maxDay = FieldFactory.createTextField(TextField.class, "Maximum days", null, false, null);

		final DateField beginDate = new DateField("beginDate", tourItem.getItemProperty("beginDate"));
		final DateField endDate = new DateField("endDate", tourItem.getItemProperty("endDate"));

		NativeSelectEnum<Priority> priority = new NativeSelectEnum<>("Priority", Priority.values(), null);
		binder.bind(priority, "priority");
		priority.setValue(tour.getPriority() != null ? tour.getPriority() : Priority.NORMAL);

		// NativeSelect region = new NativeSelect("Region", Arrays.asList(RegionType.values()));
		NativeSelectEnum<RegionType> region = new NativeSelectEnum<RegionType>("Region", RegionType.values(),
				RegionType.getCaptions(Static.CMS_LANGUAGE));
		binder.bind(region, "regionType");
		region.setValue(tour.getRegionType() != null ? tour.getRegionType() : RegionType.NORTH);
		region.addValueChangeListener(new ValueChangeListener() {

			/** */
			private static final long serialVersionUID = -61393810768533276L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				RegionType seleted = (RegionType) event.getProperty().getValue();
				tour.setRegionType(seleted);
			}
		});

		NativeSelect seasonType = new NativeSelect("seasonType", Arrays.asList(SeasonType.values()));
		binder.bind(seasonType, "seasonType");
		seasonType.setValue(tour.getSeasonType() != null ? tour.getSeasonType() : SeasonType.ALL_YEAR);
		if (seasonType.getValue().equals(SeasonType.ALL_YEAR)) {
			beginDate.setReadOnly(true);
			endDate.setReadOnly(true);
			beginDate.setRequired(false);
		}
		seasonType.addValueChangeListener(new ValueChangeListener() {

			/** */
			private static final long serialVersionUID = 2938995743292964905L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				SeasonType selected = (SeasonType) event.getProperty().getValue();
				tour.setSeasonType(selected);
				if (selected == null)
					return;
				if (selected.equals(SeasonType.ALL_YEAR)) {
					beginDate.setReadOnly(true);
					endDate.setReadOnly(true);
					beginDate.setRequired(false);
				} else {
					beginDate.setReadOnly(false);
					endDate.setReadOnly(false);
					beginDate.setRequired(true);
				}
			}
		});
		NativeSelect availableState = new NativeSelect("Available State", Arrays.asList(AvailableState.values()));
		binder.bind(availableState, "availableState");
		// by default, available state is OPEN
		availableState.setValue(tour.getAvailableState() != null ? tour.getAvailableState() : AvailableState.OPEN);
		availableState.addValueChangeListener(new ValueChangeListener() {
			/** */
			private static final long serialVersionUID = 4998710299196055241L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				AvailableState selected = (AvailableState) event.getProperty().getValue();
				tour.setAvailableState(selected);
			}
		});

		TextField fromPrice = new TextField("From price (€)");
		TextField toPrice = FieldFactory.createTextField(TextField.class, "To price (€)", null, false, null);
		TextField favouriteOrder = new TextField("favouriteOrder", tourItem.getItemProperty("favouriteOrder"));
		favouriteOrder.setReadOnly(false);
		TextField recommendOrder = new TextField("recommendOrder", tourItem.getItemProperty("recommendOrder"));
		recommendOrder.setReadOnly(false);

		name.setRequired(true);
		name.setImmediate(true);
		description.setImmediate(true);
		minDay.setImmediate(true);
		beginDate.setImmediate(true);
		endDate.setImmediate(true);

		region.setImmediate(true);
		region.setRequired(true);
		region.setRequiredError("Region is required");
		region.setValidationVisible(true);

		seasonType.setImmediate(true);
		availableState.setImmediate(true);
		fromPrice.setImmediate(true);
		favouriteOrder.setImmediate(true);
		recommendOrder.setImmediate(true);

		addComponent(new Label("BASIC INFORMATION"));
		addComponent(name);
		addComponent(description);
		addComponent(minDay);
		addComponent(maxDay);
		addComponent(beginDate);
		addComponent(endDate);
		addComponent(region);
		addComponent(seasonType);
		addComponent(availableState);
		addComponent(priority);
		addComponent(fromPrice);
		addComponent(toPrice);
		addComponent(favouriteOrder);
		addComponent(recommendOrder);
		addComponent(new Label("DETAIL"));

		if (tour.getThumbnailId() != null && tour.getThumbnailId() != 0) {
			thumbnail = ServiceResolver.findService(CoreService.class).findById(Image.class, tour.getThumbnailId());
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

		receiver = new ImageUploader(image);

		// Create the upload with a caption and set receiver later
		Upload upload = new Upload("Thumbnail", receiver);
		upload.setButtonCaption("Start Upload");
		upload.addSucceededListener(receiver);

		// Put the components in a panel
		addComponents(upload, image);

		Articles articles = null;
		if (tour.getArticlesId() == null)
			articles = new Articles();
		else
			articles = ServiceResolver.findService(CoreService.class).findById(Articles.class, tour.getArticlesId());

		BeanItem<Articles> articlesItem = new BeanItem<Articles>(articles);
		RichTextArea basicContent = new RichTextArea("Lead", articlesItem.getItemProperty("lead"));
		RichTextArea articleContent = new RichTextArea("Detail", articlesItem.getItemProperty("content"));
		articleContent.setImmediate(true);
		addComponent(basicContent);
		addComponent(articleContent);
		tour.setArticles(articles);

		NumericValidator<Integer> integerValidator = new NumericValidator<Integer>(Integer.class);
		NumericValidator<Double> priceValidator = new NumericValidator<Double>(Double.class);
		minDay.addValidator(integerValidator);
		maxDay.addValidator(integerValidator);
		fromPrice.addValidator(priceValidator);
		favouriteOrder.addValidator(integerValidator);
		recommendOrder.addValidator(integerValidator);

		binder.bind(favouriteOrder, "favouriteOrder");
		binder.bind(recommendOrder, "recommendOrder");
		binder.bind(minDay, "minDay");
		binder.bind(maxDay, "maxDay");
		binder.bind(fromPrice, "fromPrice");
		binder.bind(toPrice, "toPrice");
		binder.bind(name, "name");
		binder.bindMemberFields(this);
	}

	public boolean commit() {
		File file = receiver.getFile();
		if (file == null && thumbnail.getId() == 0) {
			Notification.show("Vui long upload hinh dai dien va dien thong tin can thiet");
			return false;
		}

		try {

			binder.commit();
			if (receiver.getFile() != null) {
				thumbnail.setAlbum(ServiceResolver.findService(PdtTourService.class).findAlbumByName(
						Album.THUMBNAIL_ALBUM_NAME, true));
				thumbnail.setFileName(file.getName());
				thumbnail.setData(Files.readAllBytes(file.toPath()));
				tour.setThumbnail(thumbnail);
			}
		} catch (IOException e) {
			LOGGER.error("Cannot read thumbnail data for tour " + tour.getId());
			return false;
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

	public Tour getTour() {
		return tour;
	}

	public boolean isModified() {
		return isModified;
	}

	public void setModified(boolean isModified) {
		this.isModified = isModified;
	}
}
