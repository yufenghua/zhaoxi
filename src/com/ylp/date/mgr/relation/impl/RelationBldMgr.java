package com.ylp.date.mgr.relation.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.ylp.date.mgr.BaseObjMgr;
import com.ylp.date.mgr.IBaseObj;
import com.ylp.date.mgr.condtion.impl.Condition;
import com.ylp.date.mgr.condtion.impl.SimglePair;
import com.ylp.date.mgr.relation.IRelation;
import com.ylp.date.mgr.relation.IRelationBuilder;
import com.ylp.date.mgr.relation.IRelationBuilderMgr;
import com.ylp.date.server.Server;
import com.ylp.date.server.SpringNames;

@Component(SpringNames.RelationBuilderMgr)
@DependsOn(SpringNames.Server)
@Lazy(false)
public class RelationBldMgr extends BaseObjMgr implements IRelationBuilderMgr {
	public RelationBuilder getObj(String id) {
		return (RelationBuilder) super.getObj(id);
	}

	public List<IRelationBuilder> getAllBuilders(String relationId) {
		Condition condition = new Condition();
		condition.eq("relationId", relationId);
		SimglePair pair = new SimglePair();
		pair.setFirst(condition);
		List<IBaseObj> list = list(null, pair);
		if (list.isEmpty()) {
			return Collections.emptyList();
		}
		List<IRelationBuilder> result = new ArrayList<IRelationBuilder>(
				list.size());
		for (IBaseObj iBaseObj : list) {
			result.add((IRelationBuilder) iBaseObj);
		}
		return result;
	}

	public List<IRelation> getRelation(String user, int type) {
		Condition condition = new Condition();
		condition.eq("userId", user);
		SimglePair pair = new SimglePair();
		pair.setFirst(condition);
		List<IBaseObj> list = list(null, pair);
		if (list.isEmpty()) {
			return Collections.emptyList();
		}
		List<IRelation> result = new ArrayList<IRelation>();
		for (IBaseObj iBaseObj : list) {
			IRelationBuilder builder=(IRelationBuilder)iBaseObj;
			IRelation obj = builder.getRelation();
			if(obj==null){
				continue;
			}
			if (obj.getType() == type) {
				result.add(obj);
			}
		}
		return result;
	}

	@Override
	protected Class getBean() {
		// TODO Auto-generated method stub
		return RelationBuilder.class;
	}

}
