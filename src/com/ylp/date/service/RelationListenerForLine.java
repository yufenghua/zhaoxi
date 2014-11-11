package com.ylp.date.service;

import com.ylp.date.mgr.IBaseObj;
import com.ylp.date.mgr.ObjListener;
import com.ylp.date.mgr.relation.IRelation;

/**
 * 关系监听器 主要用于连线用户
 * 
 * @author Qiaolin Pan
 * 
 */
public class RelationListenerForLine implements ObjListener {

	@Override
	public void fileAdd(IBaseObj obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fireUpdate(String id, IBaseObj old, IBaseObj newObj) {
		// 主要是关系更新的逻辑 关系更新的时候 ，如果关系成立，需要从池内 删除 该关系相关的两个用户且 这两个用户 不能再次参加连线
		if(newObj instanceof IRelation){
			
		}
	}

	@Override
	public void fireRemove(String id) {
		// TODO Auto-generated method stub

	}

}
