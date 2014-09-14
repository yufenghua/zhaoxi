package com.ylp.date.mgr.relation.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.ylp.date.mgr.BaseObjMgr;
import com.ylp.date.mgr.IBaseObj;
import com.ylp.date.mgr.PageCondition;
import com.ylp.date.mgr.condtion.ConditionPair;
import com.ylp.date.mgr.condtion.ConditionType;
import com.ylp.date.mgr.condtion.impl.Condition;
import com.ylp.date.mgr.condtion.impl.MultiPair;
import com.ylp.date.mgr.condtion.impl.SimglePair;
import com.ylp.date.mgr.relation.IRelMgr;
import com.ylp.date.mgr.relation.IRelation;
import com.ylp.date.server.SpringNames;

@Component(SpringNames.RelationMgr)
@DependsOn(SpringNames.Server)
@Lazy(false)
public class RelationMgr extends BaseObjMgr implements IRelMgr {
	public IRelation getObj(String id) {
		return (IRelation) super.getObj(id);
	}

	public List<IRelation> listRelation() {
		return listRelation(null);
	}

	public List<IRelation> listRelation(PageCondition page) {
		return listRelation(page, null);
	}

	public List<IRelation> listRelation(PageCondition page, ConditionPair cond) {
		List<IBaseObj> list = list(page, cond);
		if (list.isEmpty()) {
			return Collections.emptyList();
		}
		List<IRelation> result = new ArrayList<IRelation>(list.size());
		for (IBaseObj iRelation : list) {
			result.add((IRelation) iRelation);
		}
		return result;
	}

	public List<IRelation> listLine(String userId) {
		// build contionpair
		Condition condition = new Condition();
		condition.eq("one", userId);
		SimglePair pair = new SimglePair();
		pair.setFirst(condition);
		condition = new Condition();
		condition.eq("otherOne", userId);
		pair.setSecond(condition);
		pair.setRelation(ConditionType.PAIR_OR);
		MultiPair multi = new MultiPair();
		multi.setFirst(pair);
		pair = new SimglePair();
		condition = new Condition();
		condition.eq("type", IRelation.TYPE_LINE);
		pair.setFirst(condition);
		condition = new Condition();
		condition.eq("recognition", IRelation.RECOG_LINE);
		pair.setSecond(condition);
		pair.setRelation(ConditionType.PAIR_AND);
		multi.setSecond(pair);
		multi.setRelation(ConditionType.PAIR_AND);
		return listRelation(null, multi);
	}

	public List<IRelation> listFlower(String userId) {
		Condition condition = new Condition();
		condition.eq("one", userId);
		SimglePair pair = new SimglePair();
		pair.setFirst(condition);
		condition = new Condition();
		condition.eq("otherOne", userId);
		pair.setSecond(condition);
		pair.setRelation(ConditionType.PAIR_OR);
		MultiPair multi = new MultiPair();
		multi.setFirst(pair);

		pair = new SimglePair();
		condition = new Condition();
		condition.eq("type", IRelation.TYPE_FLOWER);
		pair.setFirst(condition);
		pair.setRelation(ConditionType.PAIR_AND);
		multi.setSecond(pair);
		multi.setRelation(ConditionType.PAIR_AND);
		return listRelation(null, multi);
	}

	public boolean canBuild(String userId, String userId1) {
		Condition condition = new Condition();
		condition.eq("one", userId);
		SimglePair pair = new SimglePair();
		pair.setFirst(condition);
		condition = new Condition();
		condition.eq("otherOne", userId1);
		pair.setSecond(condition);
		pair.setRelation(ConditionType.PAIR_AND);
		MultiPair multi = new MultiPair();
		multi.setFirst(pair);

		pair = new SimglePair();
		condition = new Condition();
		condition.eq("one", userId1);
		pair = new SimglePair();
		pair.setFirst(condition);
		condition = new Condition();
		condition.eq("otherOne", userId);
		pair.setSecond(condition);
		pair.setRelation(ConditionType.PAIR_AND);
		multi.setSecond(pair);
		multi.setRelation(ConditionType.PAIR_OR);

		MultiPair userType = new MultiPair();
		userType.setFirst(multi);

		pair = new SimglePair();
		condition = new Condition();
		condition.eq("type", IRelation.TYPE_LINE);
		pair.setFirst(condition);
		condition = new Condition();
		condition.eq("recognition", IRelation.RECOG_LINE);
		pair.setSecond(condition);
		pair.setRelation(ConditionType.PAIR_AND);
		if (!listRelation(null, multi).isEmpty()) {
			return false;
		}
		IRelation flower = listFlowerBetween(userId, userId1);
		boolean b = flower != null
				&& flower.getRecognition() == IRelation.RECOG_FLOWER;
		return b;
	}

	@Override
	protected Class getBean() {
		return UserRelation.class;
	}

	public IRelation listFlowerBetween(String userId, String userId1) {
		Condition condition = new Condition();
		condition.eq("one", userId);
		SimglePair pair = new SimglePair();
		pair.setFirst(condition);
		condition = new Condition();
		condition.eq("otherOne", userId1);
		pair.setSecond(condition);
		pair.setRelation(ConditionType.PAIR_AND);

		MultiPair multi = new MultiPair();
		multi.setFirst(pair);
		pair = new SimglePair();
		condition = new Condition();
		condition.eq("type", IRelation.TYPE_FLOWER);
		pair.setFirst(condition);
		multi.setSecond(multi);
		multi.setRelation(ConditionType.PAIR_AND);
		List<IRelation> list = listRelation(null, multi);
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

}
