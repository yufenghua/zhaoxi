package com.ylp.date.mgr.plan.impl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.ylp.date.mgr.plan.IUserPlan;
@Entity
@Table(name = "DATE_USERPLAN")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class UserPlan implements IUserPlan {

	/**
	 * created 2015.3.8 by panql
	 */
	private static final long serialVersionUID = 161137191054229230L;
	@Column(name = "ID_", nullable = false)
	@Id
	@GeneratedValue(generator = "uuidger")
	@GenericGenerator(name = "uuidger", strategy = "uuid")
	private String id;
	@Column(name = "CAPTION_", nullable = false)
	private String caption;
	@Column(name = "TYPE_", nullable = false)
	private String type;
	@Column(name = "USERID_", nullable = false)
	private String userId;
	@Column(name = "VALID_", nullable = false)
	private boolean valid=true;
	@Column(name = "DATE_", nullable = false)
	private Date date=new Date();

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getCaption() {
		return caption;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public String getContent() {
		return getCaption();
	}

	@Override
	public String getUserId() {
		return userId;
	}

	@Override
	public Date getDate() {
		return date;
	}

	@Override
	public boolean isValid() {
		return valid;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
