package com.ylp.date.mgr.tag;

import java.util.List;

import com.ylp.date.mgr.IMgrBase;
import com.ylp.date.mgr.PageCondition;

public interface ITagMgr extends IMgrBase {
	/**
	 * get tag 。an override from IMgrBase
	 */
	ITag getObj(String id);
	/**
	 * get the tag list of an user
	 * @param userId
	 * @return a list with content or Collectios.emptyList()
	 */
	List<ITag> getTagsByUser(String userId);
	/**
	 * get the tag for the giving sugid
	 * @param sugId
	 * @return a list with content or Collectios.emptyList()
	 */
	List<ITag> getTagBySugId(PageCondition condition,String sugId);

}
