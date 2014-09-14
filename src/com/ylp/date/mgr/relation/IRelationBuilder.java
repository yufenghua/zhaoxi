package com.ylp.date.mgr.relation;

import java.util.Date;

import com.ylp.date.mgr.IBaseObj;

public interface IRelationBuilder extends IBaseObj{
	IRelation getRelation();
	String getUserId();
	Date getCreateTime();
}
