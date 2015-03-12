package com.ylp.date.mgr.relation;

/**
 * 关系类型接口，关系类型表征了一类关系的基本特征
 * 
 * @author Qiaolin Pan
 * 
 */
public interface RelationType {
	/**
	 * type id，类型之间的id不允许重复
	 * 
	 * @return
	 */
	int getType();

	/**
	 * 简短的说明文字 可以没有
	 * 
	 * @return
	 */
	String getCaption();

	/**
	 * 关系能否在同性之间发生
	 * 
	 * @return
	 */
	boolean canBeHomo();

	/**
	 * 关系成立的认可度
	 * 
	 * @return
	 */
	int getRecognize();

	/**
	 * 关系是否是双向的，如果关系是双向的，那么，AB和BA是同一种关系
	 * 
	 * @return
	 */
	boolean canBeBidirectional();
}
