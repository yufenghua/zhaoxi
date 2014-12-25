package com.ylp.date.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import com.ylp.date.mgr.relation.IRelation;
import com.ylp.date.mgr.relation.IRelationBuilder;
import com.ylp.date.mgr.relation.impl.RelationBldMgr;
import com.ylp.date.mgr.relation.impl.RelationMgr;
import com.ylp.date.mgr.user.IUser;
import com.ylp.date.server.Server;
import com.ylp.date.util.CollectionTool;

/**
 * 检查一个lineuserObj对象中所有可能存在的关系的关系创建者
 * 如果需要对连线次数做出限制的话，需要在此类做出处理
 * @author Qiaolin Pan
 * 
 */
class LinedUserChecker implements Callable<List<String>> {
	private LineUsersObj obj;
	private RelationMgr relationMgr;
	private RelationBldMgr relationBuilderMgr;
	//FIXME 如果需要对连接次数做出处理的话 初步预测会在此类做出处理

	LinedUserChecker(LineUsersObj userObj, RelationMgr relationMgr,
			RelationBldMgr relationBuilderMgr) {
		obj = userObj;
		this.relationBuilderMgr = relationBuilderMgr;
		this.relationMgr = relationMgr;
	}

	@Override
	public List<String> call() throws Exception {
		if (obj == null) {
			return Collections.emptyList();
		}
		List<IUser> males = obj.getMale();
		if (males == null || males.isEmpty()) {
			return Collections.emptyList();
		}
		List<IUser> females = obj.getFemale();
		if (females == null || females.isEmpty()) {
			return Collections.emptyList();
		}
		List<String> result = new ArrayList<String>();
		for (IUser male : males) {
			for (IUser female : females) {
				IRelation relation = relationMgr.getLineBetween(male.getId(),
						female.getId());
				if (relation == null) {
					continue;
				}
				List<IRelationBuilder> builders = relationBuilderMgr
						.getAllBuilders(relation.getId());
				if (CollectionTool.checkNull(builders)) {
					continue;
				}
				for (IRelationBuilder iRelationBuilder : builders) {
					String userId = iRelationBuilder.getUserId();
					if (!result.contains(userId)) {
						result.add(userId);
					}
				}
			}
		}
		return result;
	}

}
