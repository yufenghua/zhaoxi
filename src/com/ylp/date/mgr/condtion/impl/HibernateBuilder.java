package com.ylp.date.mgr.condtion.impl;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.ylp.date.mgr.condtion.ConditionBuilder;
import com.ylp.date.mgr.condtion.ConditionPair;

public class HibernateBuilder implements ConditionBuilder {
	private Map<SimglePair, Criterion> criterionMap = new HashMap<SimglePair, Criterion>();
	private Criteria criteria;
	private ConditionPair pair;

	public HibernateBuilder(Criteria criteria) {
		this.criteria = criteria;
	}

	/**
 * 
 */
	public void build(ConditionPair pair) throws Exception {
		if (this.pair == null) {
			this.pair = pair;
		}
		// 将所有的条件分解为 simplepair 并将条件插进map
		buildPair(this.pair);
		// 层层递归 创建
		this.criteria.add(buildCriterion(null));
	}

	/**
	 * 
	 * @param pair2
	 */
	private void buildPair(ConditionPair pair2) {
		if (pair2 instanceof SimglePair) {
			Condition firstCondition = ((SimglePair) pair2).getFirst();
			Condition secondCondtion = ((SimglePair) pair2).getSecond();
			Criterion first = handleCondtion(firstCondition);
			Criterion sec = handleCondtion(secondCondtion);
			Criterion result = null;
			if (first == null && sec == null) {
				return;
			}
			if (first == null) {
				result = sec;
			} else if (sec == null) {
				result = first;
			} else {
				switch (pair2.getRelation()) {
				case PAIR_AND:
					result = Restrictions.and(first, sec);
					break;
				case PAIR_OR:
					result = Restrictions.or(first, sec);
					break;
				default:
					break;
				}
			}
			criterionMap.put((SimglePair) pair2, result);
		} else if (pair2 instanceof MultiPair) {
			buildPair(((MultiPair) pair2).getFirst());
			buildPair(((MultiPair) pair2).getSecond());
		}

	}

	private Criterion buildCriterion(ConditionPair pair) {
		if (pair == null) {
			pair = this.pair;
		}
		if (pair instanceof SimglePair) {
			return criterionMap.get(pair);
		}
		if (pair instanceof MultiPair) {
			Criterion first = buildCriterion(((MultiPair) pair).getFirst());
			Criterion sec = buildCriterion(((MultiPair) pair).getSecond());
			Criterion result = null;
			if (first == null) {
				result = sec;
			} else if (sec == null) {
				result = first;
			} else {
				switch (pair.getRelation()) {
				case PAIR_AND:
					result = Restrictions.and(first, sec);
					break;
				case PAIR_OR:
					result = Restrictions.or(first, sec);
					break;
				default:
					break;
				}
			}
			return result;
		}
		return null;
	}

	private Criterion handleCondtion(Condition first) {
		if (first == null) {
			return null;
		}
		Map<String, Object> map = first.getEqs();
		if (map.isEmpty()) {
			return null;
		}
		Conjunction conJoin = Restrictions.conjunction();
		for (Map.Entry<String, Object> item : map.entrySet()) {
			conJoin.add(Restrictions.eq(item.getKey(), item.getValue()));
		}
		return conJoin;
	}
}
