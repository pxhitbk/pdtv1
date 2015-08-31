package com.pdt.core.model;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.pdt.core.util.Ultility;

/**
 *
 * @author hungpx
 *
 */
@Entity
@Table(name = "pdt_tour")
@Audited
@Cacheable(true)
public class Tour extends BaseEntity implements Comparable<Tour> {

	private static final long serialVersionUID = 4534493609310679662L;

	private String name;
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private String description;
	private int minDay;
	private int minNight;
	private int maxDay;
	private int maxNight;
	private Date beginDate;
	private Date endDate;
	private double fromPrice;
	private double toPrice;
	private PriceUnit priceUnit;
	private AvailableState availableState;
	private SeasonType seasonType;
	private RegionType regionType;
	private Priority priority;
	private int favouriteOrder = 99;
	private int recommendOrder = 99;

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

	public Tour() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		if (name != null)
			setSeoPath(Ultility.removeAccents(name));
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getMinDay() {
		return minDay;
	}

	public void setMinDay(int minDay) {
		this.minDay = minDay;
	}

	public Integer getMinNight() {
		return minNight;
	}

	public void setMinNight(int minNight) {
		this.minNight = minNight;
	}

	public int getMaxDay() {
		return maxDay;
	}

	public void setMaxDay(int maxDay) {
		this.maxDay = maxDay;
	}

	public int getMaxNight() {
		return maxNight;
	}

	public void setMaxNight(int maxNight) {
		this.maxNight = maxNight;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	public PriceUnit getPriceUnit() {
		return priceUnit;
	}

	public void setPriceUnit(PriceUnit priceUnit) {
		this.priceUnit = priceUnit;
	}

	public AvailableState getAvailableState() {
		return availableState;
	}

	public void setAvailableState(AvailableState availableState) {
		this.availableState = availableState;
	}

	public SeasonType getSeasonType() {
		return seasonType;
	}

	public void setSeasonType(SeasonType season) {
		this.seasonType = season;
	}

	public RegionType getRegionType() {
		return regionType;
	}

	public void setRegionType(RegionType regionType) {
		this.regionType = regionType;
	}

	public void setArticles(Articles articles) {
		this.articles = articles;
		articlesId = articles.getId();
	}

	public Long getArticlesId() {
		return articlesId;
	}

	/**
	 * @return the priority
	 */
	public Priority getPriority() {
		return priority;
	}

	/**
	 * @param priority
	 *            the priority to set
	 */
	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public Long getThumbnailId() {
		return thumbnailId;
	}

	public void setThumbnail(Image thumbnail) {
		this.thumbnail = thumbnail;
		thumbnailId = thumbnail.getId();
	}

	public String getSeoPath() {
		return seoPath;
	}

	public void setSeoPath(String seoPath) {
		this.seoPath = seoPath;
	}

	public int getFavouriteOrder() {
		return favouriteOrder;
	}

	public void setFavouriteOrder(int favouriteOrder) {
		this.favouriteOrder = favouriteOrder;
	}

	public int getRecommendOrder() {
		return recommendOrder;
	}

	public void setRecommendOrder(int recommendOrder) {
		this.recommendOrder = recommendOrder;
	}

	@Override
	public int compareTo(Tour cmpTour) {
		// Criteria 1: preset priority
		int result = getPriority().getOrder() - cmpTour.getPriority().getOrder();
		if (result == 0) {
			// Criteria 2: nearest time next will have higher priority
			long currentTimeMil = System.currentTimeMillis();
			long thisTourTimeMil = getBeginDate() != null ? getBeginDate().getTime() : new Date().getTime();
			long cmpTourTimeMil = cmpTour.getBeginDate() != null ? cmpTour.getBeginDate().getTime() : new Date()
					.getTime();
			long order[] = new long[] { thisTourTimeMil, cmpTourTimeMil, currentTimeMil };
			Arrays.sort(order);

			if (currentTimeMil == order[0]) // {current, t1, t2}
				result = thisTourTimeMil == order[1] ? 1 : -1; // thisTourTimeMil
																// = t1 ? higher
																// : lesser
			else if (currentTimeMil == order[1]) // {t1, current, t2}
				result = thisTourTimeMil == order[2] ? 1 : -1; // thisTourTimeMil
																// = t2 ? higher
																// : lesser
		}

		return result;
	}
}
