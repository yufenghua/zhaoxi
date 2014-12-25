package com.ylp.date.mgr.tag.impl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.ylp.date.mgr.tag.ITagSug;
import com.ylp.date.mgr.user.IUser;
import com.ylp.date.server.Server;

@Entity
@Table(name = "DATE_TAG_SUG")
public class UserTagSug implements ITagSug {
	@Column(name = "TAGSUGID_", nullable = false)
	@Id
	@GeneratedValue(generator = "uuidger")  
	@GenericGenerator(name = "uuidger", strategy = "uuid")  
	private String id;
	@Column(name = "TAGSUGINFO_", nullable = false)
	private String caption;
	@Column(name = "TAGSUGCREATOR_")
	private String creatorId;
	@Column(name = "TAGSUGDATE_")
	private Date createDate;
	@Column(name = "TAGSUGRANGGE_")
	private String range;

	public String getId() {
		// TODO Auto-generated method stub
		return id;
	}

	public void setRange(String range) {
		this.range = range;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCaption() {
		return caption;
	}

	public void setId(String id) {
		this.id = id;
	}

	public IUser getCreator() {
		return Server.getInstance().userMgr().getObj(creatorId);
	}

	public Date getCreateDate() {
		return createDate;
	}

	public String getRange() {
		return range;
	}

}
