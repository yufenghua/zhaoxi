package com.ylp.date.mgr.condtion.impl;

import com.ylp.date.mgr.condtion.ConditionBuilder;
import com.ylp.date.mgr.condtion.ConditionPair;
import com.ylp.date.mgr.condtion.ConditionType;
import com.ylp.date.server.Server;

public class SimglePair implements ConditionPair {
	private Condition first;
	private Condition second;
	private ConditionType type;

	/**
	 * 
	 * @return
	 */
	public Condition getFirst() {
		return first;
	}

	/**
	 * 
	 * @return
	 */
	public Condition getSecond() {
		return second;
	}

	public void setFirst(Condition first) {
		this.first = first;
	}

	public void setSecond(Condition second) {
		this.second = second;
	}

	public ConditionType getRelation() {
		return type;
	}

	public void setRelation(ConditionType type) {
		this.type = type;
	}

	public void build(ConditionBuilder builder) throws Exception {
		builder.build(this);
	}

}
