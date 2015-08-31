/**
 * <p>
 * </p>
 *
 * @author hungpx
 * @since
 */
package com.pdt.cms.ui.validator;

import com.vaadin.data.Validator;

/**
 * @author august
 *
 */
public class NumericValidator<T extends Number & Comparable<T>> implements Validator {
	/** */
	private static final long serialVersionUID = -7464674983060740188L;
	private T min;
	private T max;
	private String numberFormatErrorMessage;
	private String minErrorMessage;
	private String maxErrorMessage;
	private final Class<T> numberType;
	private boolean allowNegative;

	public NumericValidator(Class<T> numberType) {
		this(numberType, null, null);
	};

	public NumericValidator(Class<T> numberType, T min, T max) {
		this.numberType = numberType;
		this.min = min;
		this.max = max;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.vaadin.data.Validator#validate(java.lang.Object)
	 */
	@Override
	public void validate(Object value) throws InvalidValueException {
		String strValue = String.valueOf(value);
		try {
			System.out.println("value: " + value);
			if (Long.class.equals(numberType)) {
				Long v = Long.valueOf(strValue);
				Long mn = min == null ? allowNegative ? Long.MIN_VALUE : 0 : (Long) min;
				Long mx = max == null ? Long.MAX_VALUE : (Long) max;
				validateInput(mn, mx, v);
			} else if (Integer.class.equals(numberType)) {
				Integer v = Integer.valueOf(strValue);
				Integer mn = min == null ? allowNegative ? Integer.MIN_VALUE : 0 : (Integer) min;
				Integer mx = max == null ? Integer.MAX_VALUE : (Integer) max;
				validateInput(mn, mx, v);
			} else if (Double.class.equals(numberType)) {
				Double v = Double.valueOf(strValue);
				Double mn = min == null ? allowNegative ? Double.MIN_VALUE : 0 : (Double) min;
				Double mx = max == null ? Double.MAX_VALUE : (Double) max;
				validateInput(mn, mx, v);
			} else {
				throw new IllegalArgumentException("Unsupport data type");
			}

		} catch (NumberFormatException e) {
			throw new InvalidValueException(
					numberFormatErrorMessage = numberFormatErrorMessage == null ? "Must be numberic"
							: numberFormatErrorMessage);
		}
	}

	// private <C extends Comparable<C>> void validateParams(C min, C max) {
	// if (max.compareTo(min) < 0)
	// throw new IllegalArgumentException("Invalid min and max arguments for validator, min > max");
	// }

	private <C extends Comparable<C>> void validateInput(C min, C max, C value) {
		if (max.compareTo(min) < 0)
			throw new IllegalArgumentException("Invalid min and max arguments for validator, min > max");
		if (max.compareTo(value) < 0)
			throw new InvalidValueException(maxErrorMessage == null ? "Cannot more than " + max : maxErrorMessage);
		if (value.compareTo(min) < 0)
			throw new InvalidValueException(minErrorMessage == null ? "Cannot less than " + min : minErrorMessage);
	}

	public boolean isAllowNegative() {
		return allowNegative;
	}

	/**
	 * Set allow negative number. By default, allowNegative is false.
	 * @param allowNegative
	 */
	public void setAllowNegative(boolean allowNegative) {
		this.allowNegative = allowNegative;
	}

	public void setNumberFormatErrorMessage(String numberFormatErrorMessage) {
		this.numberFormatErrorMessage = numberFormatErrorMessage;
	}

	public void setMinErrorMessage(String minErrorMessage) {
		this.minErrorMessage = minErrorMessage;
	}

	public void setMaxErrorMessage(String maxErrorMessage) {
		this.maxErrorMessage = maxErrorMessage;
	}
}
