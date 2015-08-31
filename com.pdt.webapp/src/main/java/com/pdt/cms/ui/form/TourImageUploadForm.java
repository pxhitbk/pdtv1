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
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pdt.core.model.Image;
import com.vaadin.server.Page;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;

/**
 * @author hungpx
 *
 */
public class TourImageUploadForm extends VerticalLayout {
	/** */
	private static final long serialVersionUID = 3732335772218538839L;
	private static final Logger LOGGER = LoggerFactory.getLogger(TourImageUploadForm.class);
	private List<Image> images;

	public TourImageUploadForm(List<Image> images) {
		this.images = images;
		init();
	}

	/**
	 *
	 */
	private void init() {
		removeAllComponents();

		GridLayout grid = new GridLayout();
		if (images != null)
			for (Image img : images) {
				final byte[] imgData = img.getData() != null ? img.getData() : null;
				if (imgData != null) {
					StreamResource.StreamSource imageSource = new StreamResource.StreamSource() {
						private static final long serialVersionUID = -7924008988643152334L;

						@Override
						public InputStream getStream() {
							return new ByteArrayInputStream(imgData);
						}
					};

					StreamResource imageResource = new StreamResource(imageSource, img.getFileName());

					// ImageUploader receiver = new ImageUploader(img);
					Embedded image = new Embedded(img.getFileName());
					// Upload upload = new Upload("Change", receiver);
					// upload.setButtonCaption("Change");
					Button removeButton = new Button("Remove");

					image.setWidth("200px");
					image.setSource(imageResource);

					VerticalLayout cp = new VerticalLayout(image, removeButton);
					// HorizontalLayout cpbt = new HorizontalLayout(removeButton, upload);
					// cp.addComponent(cpbt);

					removeButton.addClickListener(new RemoveImage(img));

					grid.addComponent(cp);
				}
			}
		ImageUploader receiver = new ImageUploader(null);
		// Create the upload with a caption and set receiver later
		Upload upload = new Upload("Add tour image", receiver);
		upload.setButtonCaption("Add image");
		upload.addSucceededListener(receiver);
		Embedded image = new Embedded("New image");
		addComponents(grid, upload, image);
	}

	public List<Image> commit() {
		return images;
	}

	// Implement both receiver that saves upload in a file and
	// listener for successful upload
	class ImageUploader implements Receiver, SucceededListener {
		/** */
		private static final long serialVersionUID = 3348364042002407729L;
		File file;
		Image image;

		public ImageUploader(Image image) {
			this.image = image;
		}

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
			try {
				if (image == null) {
					image = new Image();
					image.setData(Files.readAllBytes(file.toPath()));
					image.setFileName(file.getName());
					images.add(image);
				} else {
					image.setData(Files.readAllBytes(file.toPath()));
					image.setFileName(file.getName());
				}
				init();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	class RemoveImage implements ClickListener {
		/** */
		private static final long serialVersionUID = -2788030206656251958L;
		Image i;

		public RemoveImage(Image image) {
			this.i = image;
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see com.vaadin.ui.Button.ClickListener#buttonClick(com.vaadin.ui.Button.ClickEvent)
		 */
		@Override
		public void buttonClick(ClickEvent event) {
			images.remove(i);
			init();
		}

	}
}
