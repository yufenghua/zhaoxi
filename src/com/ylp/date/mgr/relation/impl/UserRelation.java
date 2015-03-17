package com.ylp.date.mgr.relation.impl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.ylp.date.mgr.relation.IRelation;

@Entity
@Table(name = "DATE_RELATION")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserRelation implements IRelation {
	@Column(name = "ID_", nullable = false)
	@Id
	@GeneratedValue(generator = "uuidger")
	@GenericGenerator(name = "uuidger", strategy = "uuid")
	private String id;
	@Column(name = "PART_", nullable = false)
	private String one;
	@Column(name = "OTHREPART_", nullable = false)
	private String otherOne;
	@Column(name = "TYPE_", nullable = false)
	private int type;
	@Column(name = "RECOGNITION_")
	private int recognition = 1;
	@Column(name = "ONEREG_")
	private Date oneReg;
	@Column(name = "OTHERONEREG_")
	private Date otherOneReg;
	@Column(name = "OKTIME_")
	private Date okTime;
	@Column(name = "CONTEXTTYPE_", nullable = true)
	private int contextType;
	@Column(name = "CONTEXTOBJID_", nullable = true)
	private String contextObjId;

	public void setContextObjId(String contextObjId) {
		this.contextObjId = contextObjId;
	}

	public String getId() {
		return id;
	}

	public Date getOneReg() {
		return oneReg;
	}

	public void setOneReg(Date oneReg) {
		this.oneReg = oneReg;
	}

	public Date getOtherOneReg() {
		return otherOneReg;
	}

	public void setOtherOneReg(Date otherOneReg) {
		this.otherOneReg = otherOneReg;
	}

	@Override
	public Date getOkTime() {
		return okTime;
	}

	public void setOkTime(Date okTime) {
		this.okTime = okTime;
	}

	public String getCaption() {
		return id;
	}

	public void setId(String id) {
		this.id = id;

	}

	public String getOne() {
		return one;
	}

	public String getOtherOne() {
		return otherOne;
	}

	public int getType() {
		return type;
	}

	public int getRecognition() {
		return recognition;
	}

	public void setOne(String one) {
		this.one = one;
	}

	public void setOtherOne(String otherOne) {
		this.otherOne = otherOne;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setRecognition(int recognition) {
		this.recognition = recognition;
	}

	@Override
	public String getOther(String userId) {
		if (StringUtils.equals(one, userId)) {
			return otherOne;
		}
		if (StringUtils.equals(otherOne, userId)) {
			return one;
		}
		return null;
	}

	@Override
	public int getContextType() {
		return contextType;
	}

	@Override
	public String getContextObjId() {
		return contextObjId;
	}

	public void setContextType(int contextType) {
		this.contextType = contextType;
	}
}
