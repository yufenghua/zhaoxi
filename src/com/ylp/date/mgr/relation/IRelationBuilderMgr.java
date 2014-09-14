package com.ylp.date.mgr.relation;

import java.util.List;

import com.ylp.date.mgr.IMgrBase;

public interface IRelationBuilderMgr extends IMgrBase {
	IRelationBuilder getObj(String id);

	/**
	 * get all builders with giving relation
	 * 
	 * @param userId
	 * @return
	 */
	List<IRelationBuilder> getAllBuilders(String relationId);

	/**
	 * get the relations with giving builder user and type
	 * 
	 * @param user
	 * @return
	 */
	List<IRelation> getRelation(String user, int type);
}
