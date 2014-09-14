package com.ylp.date.mgr.relation.impl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ylp.date.mgr.relation.IRelation;
import com.ylp.date.mgr.relation.IRelationBuilder;
import com.ylp.date.server.Server;

@Entity
@Table(name = "DATE_RELBUILDER")
public class RelationBuilder implements IRelationBuilder {
	@Column(name = "ID_", nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	@Column(name = "RELID_", nullable = false)
	private String relationId;
	@Column(name = "BUILDERID_", nullable = false)
	private String userId;
	@Column(name = "BUILDERTIME_", nullable = false)
	private Date createTime;

	public String getId() {
		return id;
	}

	public String getCaption() {
		return id;
	}

	public String getRelationId() {
		return relationId;
	}

	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setId(String id) {
		this.id = id;
	}

	public IRelation getRelation() {
		return Server.getInstance().getRelationMgr().getObj(relationId);
	}

	public String getUserId() {
		return userId;
	}

	public Date getCreateTime() {
		return createTime;
	}

}
