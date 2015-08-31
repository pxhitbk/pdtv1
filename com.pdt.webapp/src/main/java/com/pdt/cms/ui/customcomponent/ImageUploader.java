/**
 * <p>
 * </p>
 *
 * @author hungpx
 * @since
 */
package com.pdt.cms.ui.customcomponent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;

// Implement both receiver that saves upload in a file and
// listener for successful upload
public class ImageUploader implements Receiver, SucceededListener {
	/** */
	private static final long serialVersionUID = 3348364042002407729L;
	private static final Logger LOGGER = LoggerFactory.getLogger(ImageUploader.class);
	private File file;
	private final Embedded image;
	private boolean modified;

	public ImageUploader(final Embedded image) {
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
		// Show the uploaded file in the image viewer
		image.setVisible(true);
		image.setSource(new FileResource(file));
		setModified(true);
	}

	public File getFile() {
		return file;
	}

	public Embedded getImage() {
		return image;
	}

	public void setModified(boolean modified) {
		this.modified = modified;
	}

	public boolean isModified() {
		return modified;
	}
}