/**
 * <p>
 * </p>
 *
 * @author hungpx
 * @since
 */
package com.pdt.core.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author hungpx
 *
 */
@Entity
@Table(name = "pdt_image")
@DiscriminatorColumn(name = "iType")
public class Image extends BaseEntity {
	private static final long serialVersionUID = -2934655933018296009L;
	private String name;
	private String fileName;
	private String description;
	@Lob
	@Column(length = 1500000)
	private byte[] data;

	@JoinColumn(name = "albumId")
	@ManyToOne(fetch = FetchType.LAZY)
	private Album album;
	@Column(insertable = false, updatable = false, nullable = false)
	private long albumId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public void setAlbum(Album album) {
		this.album = album;
		this.albumId = album.getId();
	}

	public Long getAlbumId() {
		return albumId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
