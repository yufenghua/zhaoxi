package com.ylp.date.mgr.msg.impl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.ylp.date.mgr.msg.IMessage;
@Entity
@Table(name = "DATE_MESSAGE")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Message implements IMessage {
	public void setCaption(String caption) {
		this.caption = caption;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 9208969755976037685L;
	@Column(name = "ID_", nullable = false)
	@Id
	@GeneratedValue(generator = "uuidger")
	@GenericGenerator(name = "uuidger", strategy = "uuid")
	private String id;
	@Column(name = "CAPTION_", nullable = false)
	private String caption;
	@Column(name = "SENDER_", nullable = false)
	private String sender;
	@Column(name = "RECEIVER_", nullable = false)
	private String receiver;
	@Column(name = "DATE_", nullable = true)
	private Date date=new Date();
	@Column(name = "READED_", nullable = false)
	private boolean readed=false;

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
	public String getSender() {
		return sender;
	}

	@Override
	public String getReceiver() {
		return receiver;
	}


	@Override
	public Date getDate() {
		return date;
	}

	public boolean isReaded() {
		return readed;
	}

	public void setReaded(boolean readed) {
		this.readed = readed;
	}

}
