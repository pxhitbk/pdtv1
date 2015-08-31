package com.pdt.cms.ui.model;

import java.util.List;

/**
 * Model that defines common properties of a perspective
 *
 * @author hungpx
 *
 */
public class PerspectiveModel {
	private String name;
	private String title;
	private String caption;
	private String path;
	private GroupModel group;
	private List<String> contentClasses;
	private int order;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<String> getContentClasses() {
		return contentClasses;
	}

	public void setContentClasses(List<String> contentClasses) {
		this.contentClasses = contentClasses;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public GroupModel getGroup() {
		return group;
	}

	public void setGroup(GroupModel group) {
		this.group = group;
	}
}
