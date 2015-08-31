/**
 * <p>
 * </p>
 *
 * @author hungpx
 * @since
 */
package com.pdt.core.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author august
 *
 */
@Entity
@DiscriminatorValue(value = "HOTEL")
public class HotelRequest extends Request {

	/** */
	private static final long serialVersionUID = 2280476329215642129L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hotelId")
	private Hotel hotel;
	@Column(nullable = true, insertable = false, updatable = false)
	private long hotelId;

	public long getHotelId() {
		return hotelId;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public Hotel getHotel() {
		return hotel;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.pdt.core.model.Request#getType()
	 */
	@Override
	public Class<? extends Request> getType() {
		return HotelRequest.class;
	}
}
