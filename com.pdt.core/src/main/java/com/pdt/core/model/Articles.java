package com.pdt.core.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "pdt_articles")
public class Articles extends BaseEntity {
	private static final long serialVersionUID = 1771888439559007889L;

	private String title;
	private String author;
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private String lead;
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private String content;

	public Articles() {
	}

	public Articles(String title, String author, String lead) {
		this.title = title;
		this.author = author;
		this.lead = lead;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getLead() {
		return lead;
	}

	public void setLead(String lead) {
		this.lead = lead;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
