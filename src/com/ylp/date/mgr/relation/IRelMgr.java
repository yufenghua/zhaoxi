package com.ylp.date.mgr.relation;

import java.util.List;

import com.ylp.date.mgr.IMgrBase;
import com.ylp.date.mgr.PageCondition;
import com.ylp.date.mgr.condtion.ConditionPair;

public interface IRelMgr extends IMgrBase {
	List<IRelation> listRelation();
	List<IRelation> listRelation(PageCondition page);
	List<IRelation> listRelation(PageCondition  page,ConditionPair cond);
}
