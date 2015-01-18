package com.ylp.date.mgr.condtion.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.ylp.date.mgr.condtion.ConditionType;

/**
 * single condition
 * 
 * @author Qiaolin Pan
 * 
 */
public class Condition {
	private Map<String, Object> eqMap;

	public void eq(String prop, String value) {
		checkEqMap();
		eqMap.put(prop, value);
	}

	private void checkEqMap() {
		if (eqMap == null) {
			eqMap = new HashMap<String, Object>();
		}
	}

	public void eq(String prop, int recogLine) {
		checkEqMap();
		eqMap.put(prop, Integer.valueOf(recogLine));
	}

	/**
	 * 
	 * @return
	 */
	public Map<String, Object> getEqs() {
		if (eqMap == null) {
			return Collections.emptyMap();
		}
		return new HashMap<String, Object>(eqMap);
	}

	public void eq(String prop, boolean b) {
		checkEqMap();
		eqMap.put(prop, b);

	}

}
