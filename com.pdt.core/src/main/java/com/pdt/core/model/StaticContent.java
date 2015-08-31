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
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.pdt.core.util.Ultility;

/**
 * @author august
 *
 */
@Entity
@Table(name = "pdt_staticcontent")
public class StaticContent extends BaseEntity {
	/** */
	private static final long serialVersionUID = 2360777887427982704L;
	private String name;
	private String seoPath;
	private ContentType contentType;
	@Column(updatable = false, insertable = false)
	private Long articlesId;
	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "articlesId")
	private Articles articles;

	public Long getArticlesId() {
		return articlesId;
	}

	public void setArticlesId(Long articlesId) {
		this.articlesId = articlesId;
	}

	public Articles getArticles() {
		return articles;
	}

	public void setArticles(Articles articles) {
		this.articles = articles;
	}

	public ContentType getContentType() {
		return contentType;
	}

	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}

	public String getSeoPath() {
		return seoPath;
	}

	public void setSeoPath(String seoPath) {
		this.seoPath = seoPath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		if (name != null)
			setSeoPath(Ultility.removeAccents(name));
	}
}
