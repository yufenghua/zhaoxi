package com.ylp.date.mgr.relation;

import java.util.List;

import com.ylp.date.mgr.IMgrBase;
import com.ylp.date.mgr.PageCondition;
import com.ylp.date.mgr.condtion.ConditionPair;

/**
 * 关系管理对象
 * 
 * @author Qiaolin Pan
 * 
 */
public interface IRelMgr extends IMgrBase {
	List<IRelation> listRelation();

	List<IRelation> listRelation(PageCondition page);

	List<IRelation> listRelation(PageCondition page, ConditionPair cond);

	/**
	 * 获取所有获得认可的连线
	 * 
	 * @param userId
	 * @return
	 */
	List<IRelation> listLine(String userId);

	/**
	 * 获取所有送到的和收到的花 其中 {@link IRelation#getOne()}为发起者
	 * 
	 * @param userId
	 * @return
	 */
	List<IRelation> listFlower(String userId);

	/**
	 * 二者能否建立关系，不同的关系类型可以有不同的判定标准
	 * 
	 * @param userId
	 * @param userId1
	 * @param relationType
	 *            关系类型
	 * @param objId
	 *            关系关联对象id，如新年计划的约会关联一个新年计划的id
	 * @return
	 */
	boolean canBuild(String userId, String userId1, int relationType,
			String objId);

	/**
	 * get flower between
	 * 
	 * @param userId
	 * @param userId1
	 * @return
	 */
	IRelation getFlowerBetween(String userId, String userId1);

	/**
	 * get line between
	 * 
	 * @param userId
	 * @param userId1
	 * @return
	 */
	IRelation getLineBetween(String userId, String userId1);

	/**
	 * 获取某个用户所有的关系，type传-1为获取所有类型，recognize 传-1为所有的我
	 * 
	 * @param userId
	 * @param type
	 * @param recognize
	 * @return
	 */
	List<IRelation> listRelation(String userId, int type, int recognize);

	/**
	 * buildline
	 * 
	 * @param userId
	 * @param one
	 * @param other
	 * @throws Exception
	 */
	void buildLine(String userId, String one, String other) throws Exception;

	/**
	 * send flower
	 * 
	 * @param sender
	 * @param receiver
	 */
	void sendFlower(String sender, String receiver);

	/**
	 * 
	 * @param type
	 * @param userId
	 */
	void recognize(int type, String userId);

	/**
	 * 建立一种关系
	 * 
	 * @param type
	 *            关系类型
	 * @param one
	 *            关系的一个
	 * @param other
	 *            关系的另一方
	 * @param builder
	 *            关系创建者id
	 * @param objId
	 *            关联的实体id
	 * @throws Exception
	 */
	void buildRelation(int type, String one, String other, String builder,
			String objId) throws Exception;

	/**
	 * 
	 * @param typeLine
	 * @param one
	 * @param other
	 * @param objId
	 * @return
	 */
	List<IRelation> getRelationBetween(int typeLine, String one, String other,
			String objId);
}
