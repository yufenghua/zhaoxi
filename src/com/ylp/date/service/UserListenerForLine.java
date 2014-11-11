package com.ylp.date.service;

import java.util.Calendar;
import java.util.Date;

import com.ylp.date.mgr.IBaseObj;
import com.ylp.date.mgr.ObjListener;
import com.ylp.date.mgr.user.IUser;
import com.ylp.date.mgr.user.impl.User;
import com.ylp.date.server.Server;

/**
 * 用户信息更新监听器 主要用于连线时候的用户池的相关信息更新
 * 
 * @author Qiaolin Pan
 * 
 */
public class UserListenerForLine implements ObjListener {

	@Override
	public void fileAdd(IBaseObj obj) {
		 Server.getInstance().getLineService().whenUserAdd((User) obj);
	}

	@Override
	public void fireUpdate(String id, IBaseObj old, IBaseObj newObj) {
		if (!(old instanceof IUser)) {
			return;
		}
		if (!(newObj instanceof IUser)) {
			return;
		}
		IUser oldUser=(IUser) old,newUser=(IUser) newObj;
		//用户信息初始化
		if(oldUser.getGender()==0&&newUser.getGender()!=0){
			 Server.getInstance().getLineService().whenUserAdd(newUser);
			 return;
		}
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date today = cal.getTime();
		Date lastLine = newUser.getLastLine();
		if (lastLine == null) {
			return;
		}
		// 此处还需要判定是否在线
		if (lastLine.after(today)) {
			Server.getInstance().getLineService().whenUserRemove(id);
		}

	}

	@Override
	public void fireRemove(String id) {
		Server.getInstance().getLineService().whenUserRemove(id);

	}

}
