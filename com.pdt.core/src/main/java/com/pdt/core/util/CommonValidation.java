package com.pdt.core.util;

import java.util.Collection;

import org.springframework.util.CollectionUtils;

import com.google.common.base.Strings;

/**
 *
 * @author hungpx
 *
 */
public class CommonValidation {
	private CommonValidation() {
	}

	/**
	 * Check whether object is null or not. If it's null, IllegalArgumentException will be thrown
	 *
	 * @param object
	 *            to check
	 */
	public static void forceNotNull(String message, Object object) {
		if (object == null)
			throw new IllegalArgumentException(message);
	}

	/**
	 * Check whether object is null or not. If it's null, IllegalArgumentException will be thrown
	 *
	 * @param message
	 * @param object
	 *            instance of String or List
	 */
	public static void forceNotNullOrEmpty(String message, Object object) {
		forceNotNull(message, object);
		if (Collection.class.isAssignableFrom(object.getClass()))
			if (CollectionUtils.isEmpty((Collection<?>) object))
				throw new IllegalArgumentException(message);
		if (object instanceof String && Strings.isNullOrEmpty((String) object))
			throw new IllegalArgumentException(message);
	}
}
