package com.ylp.date.mgr.relation;

import java.util.List;

import com.ylp.date.mgr.Condition;
import com.ylp.date.mgr.IMgrBase;
import com.ylp.date.mgr.PageCondition;

public interface IRelMgr extends IMgrBase {
	List<IRelation> listRelation();
	List<IRelation> listRelation(PageCondition page);
	List<IRelation> listRelation(PageCondition  page,Condition cond);
}
