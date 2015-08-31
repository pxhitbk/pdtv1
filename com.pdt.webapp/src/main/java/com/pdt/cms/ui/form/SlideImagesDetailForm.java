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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pdt.core.model.Album;
import com.pdt.core.util.ServiceResolver;
import com.pdt.service.PdtTourService;
import com.pdt.webapp.model.HomePageImage;
import com.vaadin.data.Validator.EmptyValueException;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.StreamResource;
import com.vaadin.server.UserError;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;

/**
 * @author august
 *
 */
public class SlideImagesDetailForm extends FormLayout {

	private static final Logger LOGGER = LoggerFactory.getLogger(SubjectDetailForm.class);
	/** */
	private static final long serialVersionUID = -4860861645299380956L;

	private HomePageImage imageModel;
	private FieldGroup binder;
	private ImageUploader receiver;
	private final Embedded image = new Embedded("Uploaded Image");

	/**
	 * @param model
	 */
	public SlideImagesDetailForm(HomePageImage model) {
		this.imageModel = model;
		initLayout();
	}

	/**
	 *
	 */
	private void initLayout() {
		if (imageModel == null) {
			imageModel = new HomePageImage();
		}

		BeanItem<HomePageImage> imageItem = new BeanItem<HomePageImage>(imageModel);
		binder = new FieldGroup(imageItem);

		if (imageModel.getId() != 0) {

			final byte[] imgData = imageModel.getData();
			if (imgData != null) {
				StreamResource.StreamSource imageSource = new StreamResource.StreamSource() {
					private static final long serialVersionUID = -7924008988643152334L;

					@Override
					public InputStream getStream() {
						return new ByteArrayInputStream(imgData);
					}
				};

				StreamResource imageResource = new StreamResource(imageSource, imageModel.getFileName());
				imageResource.setCacheTime(0);
				image.setSource(imageResource);
				// image.requestRepaint();
				imageResource.setFilename(imageModel.getFileName());
			}
		} else {
			image.setVisible(false);
		}

		receiver = new ImageUploader();
		// Create the upload with a caption and set receiver later
		Upload upload = new Upload("Thumbnail", receiver);
		upload.setButtonCaption("Start Upload");
		upload.addSucceededListener(receiver);
		Label label = new Label("Recommend image ratio: 1365 x 528");
		TextField link = new TextField("Liên kết (URL)", imageItem.getItemProperty("link"));
		TextField linkCaption = new TextField("Tiêu đề", imageItem.getItemProperty("linkCaption"));

		link.setImmediate(true);
		linkCaption.setImmediate(true);
		addComponent(label);
		addComponent(link);
		addComponent(linkCaption);
		addComponents(upload, image);
		image.setWidth("300px");
		binder.bindMemberFields(this);
	}

	public boolean commit() {
		if (receiver.file == null && imageModel.getId() == 0) {
			Notification.show("Vui lòng upload hình ảnh");
			return false;
		}
		try {
			binder.commit();
			if (receiver.file != null) {
				imageModel.setAlbum(ServiceResolver.findService(PdtTourService.class).findAlbumByName(
						Album.HOMEPAGE_ALBUM_NAME, true));
				imageModel.setData(Files.readAllBytes(receiver.file.toPath()));
			}

		} catch (IOException e) {
			Notification.show("Lỗi! Không cập nhật đuợc file ảnh");
			LOGGER.error("Cannot read image for homepage");
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

	public HomePageImage getModel() {
		return imageModel;
	}

	// Implement both receiver that saves upload in a file and
	// listener for successful upload
	class ImageUploader implements Receiver, SucceededListener {
		/** */
		private static final long serialVersionUID = 3348364042002407729L;
		File file;

		@Override
		public OutputStream receiveUpload(String filename, String mimeType) {
			// Create upload stream
			FileOutputStream fos = null; // Stream to write to
			file = new File("/tmp/uploads");
			if (!file.exists()) {
				if (file.mkdir()) {
					LOGGER.info("Directory is created!");
				} else {
					LOGGER.warn("Failed to create directory!");
				}
			}
			try {
				// Open the file for writing.
				file = new File("/tmp/uploads/" + filename);
				fos = new FileOutputStream(file);
			} catch (final java.io.FileNotFoundException e) {
				new Notification("Could not open file<br/>", e.getMessage(), Notification.Type.ERROR_MESSAGE).show(Page
						.getCurrent());
				return null;
			}
			return fos; // Return the output stream to write to
		}

		@Override
		public void uploadSucceeded(SucceededEvent event) {
			// Show the uploaded file in the image viewer
			image.setVisible(true);
			image.setSource(new FileResource(file));
			imageModel.setFileName(file.getName());
		}
	}
}
