package com.pdt.cms.ui.model;

/**
 * Model that defines common properties of a UI object group.
 *
 * @author hungpx
 *
 */
public class GroupModel implements Comparable<GroupModel> {
	private String id;
	private String caption;
	private int order;

	public GroupModel() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof GroupModel))
			return false;
		GroupModel group = (GroupModel) obj;
		if (this == obj)
			return true;
		if (group.getId() == null)
			return false;
		if (!this.getId().equals(group.getId()))
			return false;
		return true;
	}

	/*
	 * implement comparable to make ascending sorting
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(GroupModel cpGroup) {

		return this.getOrder() - cpGroup.getOrder();
	}
}
