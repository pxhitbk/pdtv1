/**
 * <p>
 * </p>
 *
 * @author hungpx
 * @since
 */
package com.pdt.core.model;

import static com.pdt.core.resources.Messages.get;
import static com.pdt.core.resources.Messages.getFr;
import static com.pdt.core.resources.Messages.getVn;

import com.pdt.core.resources.LanguageType;

/**
 * @author august
 *
 */
public enum EventScope {
	INTERNATIONAL, NATIONAL, REGION, LOCAL;

	/**
	 *
	 */
	private EventScope() {

	}

	public String getCaption(LanguageType languageType) {
		String caption;
		switch (languageType) {
		case EN:
			caption = get("EventScope." + name() + "_caption");
			break;
		case FR:
			caption = getFr("EventScope." + name() + "_caption");
			break;
		case VN:
			caption = getVn("EventScope." + name() + "_caption");
			break;
		default:
			caption = get("EventScope." + name() + "_caption");
			break;
		}
		return caption;
	}

	public static String[] getCaptions(LanguageType language) {
		return new String[] { INTERNATIONAL.getCaption(language), NATIONAL.getCaption(language),
				REGION.getCaption(language), LOCAL.getCaption(language) };
	}
}
