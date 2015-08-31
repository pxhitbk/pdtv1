/**
 * <p>
 * </p>
 * 
 * @author hungpx
 * @since
 */
package com.pdt.core.util;

import java.text.Normalizer;

/**
 * @author august
 *
 */
public class Ultility {
	private Ultility() {
	}

	/**
	 * Remove all accents from string, all space will be turn to '-' character.
	 *
	 * @param input
	 * @return
	 */
	public static String removeAccents(String input) {
		return Normalizer.normalize(input.replaceAll("[,!\\./]", ""), Normalizer.Form.NFD)
				.replaceAll("[^\\p{ASCII}]", "").replaceAll("[\\s']", "-");
	}
}
