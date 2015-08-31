/**
 * <p>
 * </p>
 *
 * @author hungpx
 * @since
 */
package com.pdt.core.resources;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * @author august
 *
 */
public class Messages {
	public static final String BUNDLE_NAME = "com.pdt.core.resources.messages";
	public static final String FR_BUNDLE_NAME = "com.pdt.core.resources.messages_fr";
	public static final String VN_BUNDLE_NAME = "com.pdt.core.resources.messages_vi";

	private static ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);
	private static ResourceBundle frResourceBundle = ResourceBundle.getBundle(FR_BUNDLE_NAME);
	private static ResourceBundle vnResourceVnBundle = ResourceBundle.getBundle(VN_BUNDLE_NAME);

	private Messages() {
	}

	/**
	 * <p>
	 * Resolve a message by a key and argument replacements for English that stored in messages.properties.
	 * </p>
	 *
	 * @see MessageFormat#format(String, Object...)
	 * @param key
	 *            the message to look up
	 * @param arguments
	 *            optional message arguments
	 * @return the resolved message
	 **/
	public static String get(final String key, final Object... arguments) {
		String value = resourceBundle.getString(key);
		return arguments == null || arguments.length == 0 ? value : MessageFormat.format(value, arguments);
	}

	/**
	 * <p>
	 * Resolve a message by a key and argument replacements for French that stored in fr_messages.properties.
	 * </p>
	 *
	 * @see MessageFormat#format(String, Object...)
	 * @param key
	 *            the message to look up
	 * @param arguments
	 *            optional message arguments
	 * @return the resolved message
	 **/
	public static String getFr(final String key, final Object... arguments) {
		String value = frResourceBundle.getString(key);
		return arguments == null || arguments.length == 0 ? value : MessageFormat.format(value, arguments);
	}

	/**
	 * <p>
	 * Resolve a message by a key and argument replacements for Vietnamese that stored in vn_messages.properties.
	 * </p>
	 *
	 * @see MessageFormat#format(String, Object...)
	 * @param key
	 *            the message to look up
	 * @param arguments
	 *            optional message arguments
	 * @return the resolved message
	 **/
	public static String getVn(final String key, final Object... arguments) {
		String value = vnResourceVnBundle.getString(key);
		return arguments == null || arguments.length == 0 ? value : MessageFormat.format(value, arguments);
	}
}
