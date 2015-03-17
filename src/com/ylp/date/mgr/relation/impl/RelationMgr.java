package com.ylp.date.mgr.relation.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.ylp.date.mgr.BaseObjMgr;
import com.ylp.date.mgr.BusinessException;
import com.ylp.date.mgr.IBaseObj;
import com.ylp.date.mgr.PageCondition;
import com.ylp.date.mgr.condtion.ConditionPair;
import com.ylp.date.mgr.condtion.ConditionType;
import com.ylp.date.mgr.condtion.impl.Condition;
import com.ylp.date.mgr.condtion.impl.MultiPair;
import com.ylp.date.mgr.condtion.impl.SimglePair;
import com.ylp.date.mgr.relation.IRelMgr;
import com.ylp.date.mgr.relation.IRelation;
import com.ylp.date.mgr.relation.IRelationBuilder;
import com.ylp.date.mgr.relation.RelationTypeMgr;
import com.ylp.date.mgr.user.IUser;
import com.ylp.date.mgr.user.IUserMgr;
import com.ylp.date.mgr.user.impl.User;
import com.ylp.date.server.Server;
import com.ylp.date.server.SpringNames;
import com.ylp.date.util.CollectionTool;

@Component(SpringNames.RelationMgr)
@DependsOn(SpringNames.Server)
@Lazy(false)
public class RelationMgr extends BaseObjMgr implements IRelMgr {
	private static final Logger logger = LoggerFactory
			.getLogger(RelationMgr.class);
	private RelationTypeMgr typeMgr;

	public void init() {
		typeMgr = new RelationTypeMgrImpl();
		typeMgr.load();
	}

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
		return listRelation(userId, IRelation.TYPE_LINE,
				typeMgr.getType(IRelation.TYPE_LINE).getRecognize());
	}

	public List<IRelation> listFlower(String userId) {
		return listRelation(userId, IRelation.TYPE_FLOWER, -1);
	}

	public boolean canBuild(String userId, String userId1, int relationType,
			String objId) {
		if (relationType == IRelation.TYPE_FLOWER) {
			return checkFlower(userId, userId1);
		}
		if (relationType == IRelation.TYPE_LINE) {
			return checkLine(userId, userId1);
		}
		// 所有的关系应该有统一的判定标准，这个先暂缓
		List<IRelation> list = getRelationBetween(relationType, userId,
				userId1, objId);
		return CollectionTool.checkNull(list);
	}

	private boolean checkLine(String userId, String userId1) {
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
		userType.setSecond(pair);
		userType.setRelation(ConditionType.PAIR_AND);
		if (!listRelation(null, userType).isEmpty()) {
			return false;
		}
		return true;
	}

	private boolean checkFlower(String userId, String userId1) {
		IRelation flower = getFlowerBetween(userId, userId1);
		// 不存在 或者没有成立
		boolean b = (flower == null || flower.getRecognition() < IRelation.RECOG_FLOWER);
		return b;
	}

	@Override
	protected Class getBean() {
		return UserRelation.class;
	}

	public IRelation getFlowerBetween(String userId, String userId1) {
		List<IRelation> list = getRelationBetween(IRelation.TYPE_FLOWER,
				userId, userId1, null);
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	public void buildLine(String userId, String one, String other)
			throws Exception {
		buildRelation(IRelation.TYPE_LINE, one, other, userId, null);
	}

	/**
	 * 处理lastline的问题
	 * 
	 * @param relation
	 * @param okTime
	 * @throws Exception
	 */
	private void handleLastLine(UserRelation relation, Date okTime)
			throws Exception {
		// FIXME 此处代码应该在关系监听器里面来执行
		IUserMgr userMgr = Server.getInstance().userMgr();
		String oneUser = relation.getOne();
		User one = (User) userMgr.getObj(oneUser);
		one.setLastLine(okTime);
		userMgr.update(oneUser, one);
		String otherId = relation.getOne();
		User otherOne = (User) userMgr.getObj(otherId);
		otherOne.setLastLine(okTime);
		userMgr.update(otherId, otherOne);
	}

	private boolean checkBuilder(String id, String userId) {
		List<IRelationBuilder> builders = Server.getInstance()
				.getRelationBuilderMgr().getAllBuilders(id);
		for (IRelationBuilder iRelationBuilder : builders) {
			if (StringUtils.equals(iRelationBuilder.getUserId(), userId)) {
				return false;
			}
		}
		return true;
	}

	private void handleBuilder(String id, String userId) {
		RelationBuilder bui = new RelationBuilder();
		bui.setCreateTime(new Date());
		bui.setRelationId(id);
		bui.setUserId(userId);
		Server.getInstance().getRelationBuilderMgr().add(bui);
		try {
			Server.getInstance().userMgr().addCupidValue(userId, id);
		} catch (Exception e) {
			logger.error("添加丘比特值时发生错误！用户id" + userId + "关系id" + id, e);
			throw new BusinessException(BusinessException.CODE_ADD_CUPID);
		}
	}

	public IRelation getLineBetween(String userId, String userId1) {
		List<IRelation> list = getRelationBetween(IRelation.TYPE_LINE, userId,
				userId1, null);
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	public void sendFlower(String sender, String receiver) {
		User user = (User) Server.getInstance().userMgr().getObj(sender);
		if (user == null) {
			throw new BusinessException(BusinessException.CODE_NO_SUCH_USER,
					sender);
		}
		int flower = user.getFlower();
		if (flower == 0) {
			throw new BusinessException(BusinessException.CODE_NO_FLOWER,
					sender);
		}
		try {
			buildRelation(IRelation.TYPE_FLOWER, sender, receiver, sender, null);
			user.setFlower(flower - 1);
			try {
				Server.getInstance().userMgr().update(sender, user);
			} catch (Exception e) {
				logger.error("更新用户信息失败,用户id" + sender + "原始鲜花数量" + flower, e);
			}
		} catch (Exception e1) {
			Server.getInstance().handleException(e1);
		}
	}

	@Override
	public void recognize(int type, String userId) {
		Session session = Server.getInstance().openSession();
		try {
			session.beginTransaction();
			String hql1 = "update UserRelation set oneReg=? where one=? and type=? and recognition>=? and oneReg is null";
			int one = executeUpdate(session, hql1,
					new Object[] {
							new Date(),
							userId,
							type,
							(type == IRelation.TYPE_FLOWER ? 1
									: IRelation.RECOG_LINE) });
			String hql2 = "update UserRelation set otherOneReg=? where otherOne=? and type=? and recognition>=? and otherOneReg is null ";
			int otherOne = executeUpdate(session, hql2,
					new Object[] {
							new Date(),
							userId,
							type,
							(type == IRelation.TYPE_FLOWER ? 1
									: IRelation.RECOG_LINE) });
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			Server.getInstance().handleException(e);
		} finally {
			session.close();
		}
	}

	@Override
	public void buildRelation(int type, String one, String other,
			String builder, String objId) throws Exception {
		if (canBuild(one, other, type, objId)) {
			List<IRelation> relationBetween = getRelationBetween(type, one,
					other, objId);
			UserRelation relation = (UserRelation) (relationBetween.isEmpty() ? null
					: relationBetween.get(0));
			if (relation == null) {
				relation = new UserRelation();
				relation.setOne(one);
				relation.setOtherOne(other);
				relation.setType(type);
				relation.setRecognition(1);
				relation.setContextObjId(objId);
				relation = (UserRelation) add(relation);
				handleBuilder(relation.getId(), builder);
			} else {
				int recognize = typeMgr.getType(type).getRecognize();
				if (relation.getRecognition() == recognize) {
					throw new BusinessException(
							BusinessException.CODE_LINE_FULL);
				}
				if (checkBuilder(relation.getId(), builder)) {
					int recognition = relation.getRecognition() + 1;
					relation.setRecognition(recognition);
					if (recognition == recognize) {
						Date okTime = new Date();
						relation.setOkTime(okTime);
					}
					update(relation.getId(), relation);
					try {
						handleBuilder(relation.getId(), builder);
					} catch (Exception e) {
						logger.error("建立创建者数据时发生后错误，创建者id" + builder + ",关系id"
								+ relation.getId(), e);
					}
					try {
						if (type == IRelation.TYPE_LINE) {
							handleLastLine(relation, relation.getOkTime());
						}
					} catch (Exception e) {
						logger.error("修正用户信息是发生错误", e);
					}
				} else {
					throw new BusinessException(BusinessException.BUILD_DONE);
				}
			}
		} else {
			throw new BusinessException(BusinessException.CODE_RELATION_EXIST);
		}
	}

	@Override
	public List<IRelation> getRelationBetween(int type, String one,
			String other, String objId) {
		Condition condition = new Condition();
		condition.eq("one", one);
		condition.eq("otherOne", other);
		SimglePair pair = new SimglePair();
		pair.setFirst(condition);
		condition = new Condition();
		condition.eq("one", other);
		condition.eq("otherOne", one);
		pair.setSecond(condition);
		pair.setRelation(ConditionType.PAIR_OR);
		MultiPair multi = new MultiPair();
		multi.setFirst(pair);
		SimglePair otherPair = null;
		if (type != -1) {
			otherPair = new SimglePair();
			condition = new Condition();
			condition.eq("type", IRelation.TYPE_LINE);
			otherPair.setFirst(condition);
			multi.setSecond(otherPair);
		}
		if (!StringUtils.isEmpty(objId)) {
			if (otherPair == null) {
				otherPair = new SimglePair();
				condition = new Condition();
				condition.eq("contextObjId", objId);
				otherPair.setFirst(condition);
				multi.setSecond(otherPair);
			} else {
				condition = new Condition();
				condition.eq("contextObjId", objId);
				otherPair.setSecond(condition);
				otherPair.setRelation(ConditionType.PAIR_AND);
			}
			multi.setSecond(otherPair);
		}
		multi.setRelation(ConditionType.PAIR_AND);
		List<IRelation> list = listRelation(null, multi);
		return list;
	}

	@Override
	public List<IRelation> listRelation(String userId, int type, int recognize) {
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
		if (type != -1) {
			pair = new SimglePair();
			condition = new Condition();
			condition.eq("type", type);
			pair.setFirst(condition);
			if (recognize != -1) {
				condition = new Condition();
				condition.eq("recognition", recognize);
				pair.setSecond(condition);
				pair.setRelation(ConditionType.PAIR_AND);
			}
			multi.setSecond(pair);
			multi.setRelation(ConditionType.PAIR_AND);
		}
		return listRelation(null, multi);
	}

}
