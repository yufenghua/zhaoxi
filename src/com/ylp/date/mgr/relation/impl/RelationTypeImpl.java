package com.ylp.date.mgr.relation.impl;

import com.ylp.date.mgr.relation.RelationType;

/**
 * 
 * @author Qiaolin Pan
 * 
 */
public class RelationTypeImpl implements RelationType {
	private int type;
	private String caption;
	private boolean homo;
	private boolean bidirectional;
	int recognize;

	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return type;
	}

	@Override
	public String getCaption() {
		// TODO Auto-generated method stub
		return caption;
	}

	@Override
	public boolean canBeHomo() {
		// TODO Auto-generated method stub
		return homo;
	}

	@Override
	public int getRecognize() {
		// TODO Auto-generated method stub
		return recognize;
	}

	@Override
	public boolean canBeBidirectional() {
		// TODO Auto-generated method stub
		return bidirectional;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public void setHomo(boolean homo) {
		this.homo = homo;
	}

	public void setBidirectional(boolean bidirectional) {
		this.bidirectional = bidirectional;
	}

	public void setRecognize(int recognize) {
		this.recognize = recognize;
	}

}
