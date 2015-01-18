package com.ylp.date.mgr.helpinfo.impl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.ylp.date.mgr.helpinfo.IHelpInfo;
import com.ylp.date.server.Server;

@Entity
@Table(name = "DATE_HELPINFO")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class HelpInfo implements IHelpInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1746239330752636849L;
	@Column(name = "ID_", nullable = false)
	@Id
	@GeneratedValue(generator = "uuidger")
	@GenericGenerator(name = "uuidger", strategy = "uuid")
	private String id;
	@Column(name = "CAPTION_", nullable = true)
	private String caption;
	@Column(name = "VERSION_", nullable = false)
	private String version;
	@Column(name = "USER_", nullable = false)
	private String user;
	@Column(name = "READTIME_", nullable = false)
	private Date readTime=new Date();
	@Column(name = "READED_", nullable = false)
	private boolean readed;

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}

	public void setReaded(boolean readed) {
		this.readed = readed;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getCaption() {
		// TODO Auto-generated method stub
		return caption;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getVersion() {
		return version;
	}

	@Override
	public String getUser() {
		return user;
	}

	@Override
	public boolean isReaded() {
		return readed;
	}

	@Override
	public Date getReadTime() {
		return readTime;
	}

}
