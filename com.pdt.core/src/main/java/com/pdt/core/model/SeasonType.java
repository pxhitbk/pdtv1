package com.pdt.core.model;

import static com.pdt.core.resources.Messages.get;
import static com.pdt.core.resources.Messages.getFr;
import static com.pdt.core.resources.Messages.getVn;

import java.util.Calendar;

import com.pdt.core.resources.LanguageType;

public enum SeasonType {
	SPRING, SUMMER, AUTUMN, WINTER, ALL_YEAR;

	private String caption;

	private SeasonType() {
	}

	public String getCaption(LanguageType language) {
		String prop = "SeasonType." + name() + "_caption";
		switch (language) {
		case EN:
			caption = get(prop);
			break;
		case FR:
			caption = getFr(prop);
			break;
		case VN:
			caption = getVn(prop);
			break;
		default:
			caption = get(prop);
			break;
		}
		return caption;
	}

	/**
	 *
	 * @param calendarMonthType
	 *            static type from {@link Calendar}, example Calendar.NOVEMBER
	 * @return
	 */
	public static SeasonType getSeasonFromMonth(int calendarMonthType) {
		SeasonType s;
		switch (calendarMonthType) {
		case Calendar.JANUARY:
		case Calendar.FEBRUARY:
		case Calendar.MARCH:
			s = SPRING;
			break;
		case Calendar.APRIL:
		case Calendar.MAY:
		case Calendar.JUNE:
			s = SUMMER;
			break;
		case Calendar.JULY:
		case Calendar.AUGUST:
		case Calendar.SEPTEMBER:
			s = AUTUMN;
		case Calendar.OCTOBER:
		case Calendar.NOVEMBER:
		case Calendar.DECEMBER:
			s = WINTER;
			break;

		default:
			throw new IllegalArgumentException("Not valid month type");
		}

		return s;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return getCaption(LanguageType.FR);
	}

	public String[] captions(LanguageType language) {
		return new String[] { SPRING.getCaption(language), SUMMER.getCaption(language), AUTUMN.getCaption(language),
				WINTER.getCaption(language), ALL_YEAR.getCaption(language) };
	}

}
