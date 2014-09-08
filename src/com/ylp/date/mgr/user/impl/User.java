package com.ylp.date.mgr.user.impl;

import java.io.File;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.Table;

import com.ylp.date.mgr.user.IUser;

@Entity
@Table(name = "DATE_USER")
public class User implements IUser {
	@Id
	@Column(name = "ID_", nullable = false, unique = true)
	private String id;
	@Column(name = "CAPTION_", nullable = false)
	private String caption;
	@Column(name = "PASSWORD_")
	private String pwd;
	@Column(name = "CREATEDATE_", nullable = false)
	private Date createDate;
	@Column(name = "EMAIL_")
	private String email;
	@Column(name = "CARDTYPE_")
	private int cardType;
	@Column(name = "CUPIDVALUE_")
	private int cupidvalue;
	@Column(name = "GENDER_")
	private int gender;
	@Column(name = "STATUS_")
	private int status;
	@Column(name = "BIRTH_")
	private Date birth;
	@Column(name = "MODERATORID_")
	private String moderatorId;
	@Column(name = "INVITERID_")
	private String inviterId;
	@Column(name = "SHOWNUM_")
	private int showNum;
	@Column(name = "LASTSHOWNUM_")
	private int lastShowNum;
	@Column(name = "ROLE_")
	private String role;

	public String getId() {
		return id;
	}

	public String getCaption() {
		return caption;
	}

	public String getPwd() {
		return pwd;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public String getEmail() {
		return email;
	}

	public int getCardType() {
		// TODO Auto-generated method stub
		return cardType;
	}

	public File getCardImg() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getCupidvalue() {
		return cupidvalue;
	}

	public int getGender() {
		return gender;
	}

	public int getStatus() {
		return status;
	}

	public String getModeratorId() {
		return moderatorId;
	}

	public String getInviterId() {
		return inviterId;
	}

	public Date getBirth() {
		return birth;
	}

	public File getImge() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getShowNum() {
		return showNum;
	}

	public int getLastShowNum() {
		return lastShowNum;
	}

	public String getRole() {
		return role;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setCardType(int cardType) {
		this.cardType = cardType;
	}

	public void setCupidvalue(int cupidvalue) {
		this.cupidvalue = cupidvalue;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public void setModeratorId(String moderatorId) {
		this.moderatorId = moderatorId;
	}

	public void setShowNum(int showNum) {
		this.showNum = showNum;
	}

	public void setLastShowNum(int lastShowNum) {
		this.lastShowNum = lastShowNum;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public IUser getInviter() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setInviterId(String inviterId) {
		this.inviterId = inviterId;
	}

}