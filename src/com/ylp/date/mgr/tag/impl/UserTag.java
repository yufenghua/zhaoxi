package com.ylp.date.mgr.tag.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ylp.date.mgr.tag.ITag;

@Entity
@Table(name = "DATE_USER_TAG")
public class UserTag implements ITag {
	@Column(name = "TAGID_", nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	@Column(name = "TAGSUGID_", nullable = true)
	private String sugId;
	@Column(name = "TAGDESC_", nullable = false)
	private String tagContent;
	@Column(name = "TAGUSER_", nullable = false)
	private String userId;

	public String getId() {
		return id;
	}

	public String getCaption() {
		return tagContent;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public String getTagSug() {
		return sugId;
	}

	public String getSugId() {
		return sugId;
	}

	public String getTagContent() {
		return tagContent;
	}

	public void setSugId(String sugId) {
		this.sugId = sugId;
	}

	public void setTagContent(String tagContent) {
		this.tagContent = tagContent;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
