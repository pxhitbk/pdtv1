/**
 * <p>
 * </p>
 *
 * @author hungpx
 * @since
 */
package com.pdt.web.view.model;

import java.io.Serializable;

import com.pdt.core.model.Hotel;

/**
 * @author august
 *
 */
public class VHotel implements Serializable {
	/** */
	private static final long serialVersionUID = 3516780642207776553L;
	private final String thumbnail;
	private final Hotel hotel;

	public VHotel(String thumbnail, Hotel hotel) {
		this.thumbnail = thumbnail;
		this.hotel = hotel;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public String getName() {
		return hotel.getName();
	}

	public String getDescription() {
		return hotel.getDescription();
	}

	public Long getId() {
		return hotel.getId();
	}

	public String getSeoPath() {
		return hotel.getSeoPath();
	}

	public double getFromPrice() {
		return hotel.getFromPrice();
	}

	public int getStars() {
		return hotel.getHotelLevel().getLevel();
	}
}
