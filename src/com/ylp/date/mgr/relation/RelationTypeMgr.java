package com.ylp.date.mgr.relation;

/**
 * 关系类型配置，关系类型有多种，不同的类型表现了不同的行为
 * 
 * @author Qiaolin Pan
 * 
 */
public interface RelationTypeMgr {
	/**
	 * 初始化
	 */
	void load();

	/**
	 * 根据id获取一种类型
	 * 
	 * @param type
	 * @return
	 */
	RelationType getType(int type);
}
