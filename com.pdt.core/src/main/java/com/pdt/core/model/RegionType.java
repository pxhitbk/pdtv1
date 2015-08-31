package com.pdt.core.model;

import static com.pdt.core.resources.Messages.get;
import static com.pdt.core.resources.Messages.getFr;
import static com.pdt.core.resources.Messages.getVn;

import com.pdt.core.resources.LanguageType;

public enum RegionType {
	NORTH(1), CENTRAL(2), SOUTH(0)/* , ALONG_COUNTRY, MUTIPLE_REGION */;

	private String caption;
	private int index;

	private RegionType(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getCaption(LanguageType language) {
		switch (language) {
		case EN:
			caption = get("RegionType." + name() + "_caption");
			break;
		case FR:
			caption = getFr("RegionType." + name() + "_caption");
			break;
		case VN:
			caption = getVn("RegionType." + name() + "_caption");
			break;
		default:
			caption = get("RegionType." + name() + "_caption");
			break;
		}
		return caption;
	}

	public static String[] getCaptions(LanguageType language) {
		return new String[] { NORTH.getCaption(language), CENTRAL.getCaption(language), SOUTH.getCaption(language) };
	}
}
