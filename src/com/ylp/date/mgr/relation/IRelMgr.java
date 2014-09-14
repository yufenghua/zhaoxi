package com.ylp.date.mgr.relation;

import java.util.List;

import com.ylp.date.mgr.IMgrBase;
import com.ylp.date.mgr.PageCondition;
import com.ylp.date.mgr.condtion.ConditionPair;

public interface IRelMgr extends IMgrBase {
	List<IRelation> listRelation();
	List<IRelation> listRelation(PageCondition page);

	List<IRelation> listRelation(PageCondition  page,ConditionPair cond);
	/**
	 * 获取所有获得认可的连线
	 * @param userId
	 * @return
	 */
	List<IRelation> listLine(String userId);
	/**
	 * 获取所有送到的和收到的花  其中 {@link IRelation#equals(Object)}为发起者
	 * @param userId
	 * @return
	 */
	List<IRelation> listFlower(String userId);
	/**
	 * 二者能否建立关系
	 * @param userId
	 * @param userId1
	 * @return
	 */
	boolean canBuild(String userId,String userId1);
}
