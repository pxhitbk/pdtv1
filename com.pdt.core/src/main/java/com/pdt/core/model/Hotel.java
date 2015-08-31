/**
 * <p>
 * </p>
 *
 * @author hungpx
 * @since
 */
package com.pdt.core.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.pdt.core.util.Ultility;

/**
 * @author august
 *
 */
@Entity
@DiscriminatorValue(value = Hotel.DEST_TYPE)
public class Hotel extends Destination {

	private HotelLevel hotelLevel;

	/** */
	private static final long serialVersionUID = 5677353791488242019L;
	public static final String DEST_TYPE = "1";

	private String name;
	private String mainPhoneNo;
	private String subPhoneNo;
	private double fromPrice;
	private double toPrice;
	private String seoPath;

	@Column(insertable = false, updatable = false, nullable = true)
	private long thumbnailId;
	@JoinColumn(name = "thumbnailId")
	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE })
	private Image thumbnail;

	@Column(updatable = false, insertable = false)
	private Long articlesId;
	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "articlesId")
	private Articles articles;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		if (name != null)
			setSeoPath(Ultility.removeAccents(name));
	}

	public HotelLevel getHotelLevel() {
		return hotelLevel;
	}

	public void setHotelLevel(HotelLevel hotelLevel) {
		this.hotelLevel = hotelLevel;
	}

	public Articles getArticles() {
		return articles;
	}

	public void setArticles(Articles articles) {
		this.articles = articles;
		articlesId = articles.getId();
	}

	public Long getArticlesId() {
		return articlesId;
	}

	public Long getThumbnailId() {
		return thumbnailId;
	}

	public void setThumbnail(Image thumbnail) {
		this.thumbnail = thumbnail;
		thumbnailId = thumbnail.getId();
	}

	public Image getThumbnail() {
		return thumbnail;
	}

	public String getSeoPath() {
		return seoPath;
	}

	public void setSeoPath(String seoPath) {
		this.seoPath = seoPath;
	}

	public double getFromPrice() {
		return fromPrice;
	}

	public void setFromPrice(double fromPrice) {
		this.fromPrice = fromPrice;
	}

	public double getToPrice() {
		return toPrice;
	}

	public void setToPrice(double toPrice) {
		this.toPrice = toPrice;
	}

	public String getMainPhoneNo() {
		return mainPhoneNo;
	}

	public void setMainPhoneNo(String mainPhoneNo) {
		this.mainPhoneNo = mainPhoneNo;
	}

	public String getSubPhoneNo() {
		return subPhoneNo;
	}

	public void setSubPhoneNo(String subPhoneNo) {
		this.subPhoneNo = subPhoneNo;
	}

	public int getStars() {
		return hotelLevel.getLevel();
	}
}
