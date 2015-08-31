/**
 * <p>
 * </p>
 *
 * @author hungpx
 * @since
 */
package com.pdt.core.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author hungpx
 *
 */
@Entity
@Table(name = "pdt_album", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
public class Album extends BaseEntity {

	/** */
	private static final long serialVersionUID = 191477395505346601L;

	/** Default albums must be exist in database. */
	public static final String THUMBNAIL_ALBUM_NAME = "THUMBNAIL";
	public static final String HOMEPAGE_ALBUM_NAME = "HOMEPAGE";
	public static final String DEFAULT_ALBUM_NAME = "DEFAULT";

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
