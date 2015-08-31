package com.pdt.util;

import static com.pdt.resources.Messages.get;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gwt.user.server.Base64Utils;
import com.pdt.core.model.Image;

public final class PdtUtils implements PdtConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(PdtUtils.class);

	public static void sendingMailTLS(final String receiver, final String subject, final String content) {
		final String username = get(PDTUTILS_SENDMAIL_USERNAME);
		final String password = get(PDTUTILS_SENDMAIL_PASSWORD);
		final String yes = get(PDTUTILS_SENDMAIL_SMTP_VAL_YES);

		Properties props = new Properties();
		props.put(get(PDTUTILS_SENDMAIL_SMTP_PROP_AUTH), yes);
		props.put(get(PDTUTILS_SENDMAIL_SMTP_PROP_STARTTLS), yes);
		props.put(get(PDTUTILS_SENDMAIL_SMTP_PROP_HOST), get(PDTUTILS_SENDMAIL_SMTP_VAL_HOST));
		props.put(get(PDTUTILS_SENDMAIL_SMTP_PROP_PORT), get(PDTUTILS_SENDMAIL_SMTP_VAL_PORT));

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			MimeMessage message = new MimeMessage(session);
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
			message.setSubject(subject);
			message.setContent(content, PDTUTILS_SENDMAIL_CONTENT_HTML);
			Transport.send(message);

			System.out.println("Sent successfully!");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	public static String convertImagesToBase64(final String imageName, final byte[] imageContent) {
		if (null == imageName || null == imageContent) {
			return null;
		}
		String mimeType = null;
		if (imageName.endsWith(PDTUTILS_SVG)) {
			mimeType = PDTUTILS_IMAGE_BASE64_SVG;
		} else {
			mimeType = PDTUTILS_IMAGE_BASE64_COMMON;
		}
		return mimeType + Base64Utils.toBase64(imageContent);
	}

	/**
	 * Writing image to local disk path.
	 *
	 * @param image
	 * @param path
	 *            physic path
	 * @throws IOException
	 */
	public static void writingImageFile(Image image, String path) throws IOException {
		InputStream in = new ByteArrayInputStream(image.getData());
		BufferedImage bImageFromConvert = ImageIO.read(in);
		ImageIO.write(bImageFromConvert, image.getFileName().substring(image.getFileName().lastIndexOf(".") + 1),
				new File(path + "/" + image.getFileName()));
	}

	/**
	 * Check whether a string is value of a enum type.
	 * @param enumType
	 * @param typeName
	 * @return
	 */
	public static <T extends Enum<T>> boolean valueOfEnumType(final Class<T> enumType, final String typeName) {
		if (enumType == null || typeName == null)
			return false;

		boolean isValid = false;
		try {
			Enum.valueOf(enumType, typeName);
			isValid = true;
		} catch (IllegalArgumentException e) {
			LOGGER.error(typeName + " is not type of " + enumType.getName());
		}
		return isValid;
	}

	public static boolean isLong(String value) {
		try {
			Long.valueOf(value);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
}
