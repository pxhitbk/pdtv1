/**
 * <p>
 * </p>
 *
 * @author hungpx
 * @since
 */
package com.pdt.core.model;

/**
 * @author august
 *
 */
public enum HotelLevel {
	ANY(1), TWO_STAR(2), THREE_STAR(3), FOUR_STAR(4), FIVE_STAR(5);

	private int level;

	/**
	 *
	 */
	HotelLevel(int star) {
		this.level = star;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}
