package com.ylp.date.mgr.condtion;

/**
 * 
 * @author Qiaolin Pan
 * 
 */
public interface ConditionPair {
	/**
	 * 
	 * @return
	 */
	ConditionType getRelation();

	/**
	 * 
	 * @param type
	 */
	void setRelation(ConditionType type);

	/**
	 * 
	 * @param builder
	 * @throws Exception
	 */
	void build(ConditionBuilder builder) throws Exception;
}
