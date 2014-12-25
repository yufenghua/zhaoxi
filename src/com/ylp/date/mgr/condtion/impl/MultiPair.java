package com.ylp.date.mgr.condtion.impl;

import java.util.HashSet;
import java.util.Set;

import com.ylp.date.mgr.condtion.ConditionBuilder;
import com.ylp.date.mgr.condtion.ConditionPair;
import com.ylp.date.mgr.condtion.ConditionType;

public class MultiPair implements ConditionPair {
	private ConditionPair first;
	private ConditionPair second;
	private ConditionType type;

	/**
	 * check cyclic reference
	 * 
	 * @param set
	 *            the set of ConditionPair that have been used，if null,will
	 *            create one
	 * @return if there is a cyclic reference
	 */
	public boolean isCycle(Set<ConditionPair> set) {
		if (set == null) {
			set = new HashSet<ConditionPair>();
		}
		if (first != null) {
			if (set.contains(first)) {
				return true;
			}
			if (first instanceof MultiPair) {
				if (((MultiPair) first).isCycle(set)) {
					return true;
				}
			}
		}
		if (second != null) {
			if (set.contains(second)) {
				return true;
			}
			if (second instanceof MultiPair) {
				if (((MultiPair) second).isCycle(set)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * @return
	 */
	public ConditionPair getFirst() {
		return first;
	}

	/**
	 * 
	 * @return
	 */
	public ConditionPair getSecond() {
		return second;
	}

	/**
	 * @param pair
	 */
	public void setFirst(ConditionPair pair) {
		first = pair;
	}

	/**
	 * @param pair
	 */
	public void setSecond(ConditionPair pair) {
		second = pair;
	}

	public ConditionType getRelation() {
		return this.type;
	}

	public void setRelation(ConditionType type) {
		this.type = type;
	}

	public void build(ConditionBuilder builder) throws Exception {
		if(isCycle(null)){
			throw new Exception("条件成环！");
		}
		builder.build(this);
	}

}
