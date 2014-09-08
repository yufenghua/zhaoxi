package com.ylp.date.mgr;

import java.io.Serializable;

/**
 * 
 * @author Qiaolin Pan
 * 
 */
public interface IBaseObj  extends Serializable{
	/**
	 * 
	 * @return
	 */
	String getId();

	/**
	 * 
	 * @return
	 */
	String getCaption();
}
