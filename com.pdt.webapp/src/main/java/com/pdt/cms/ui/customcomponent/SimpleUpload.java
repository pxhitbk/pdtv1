/**
 *<p>
 *</p>
 * @author hungpx
 * @since
 */
package com.pdt.cms.ui.customcomponent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.vaadin.ui.Upload.Receiver;

public class SimpleUpload implements Receiver {
	/** */
	private static final long serialVersionUID = 7313696021695122596L;
	private File tempFile;

	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {
		try {
			tempFile = new File(filename);
			return new FileOutputStream(tempFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	File getFile() {
		return tempFile;
	}
}
