package com.ylp.date.service;

import java.util.List;
import java.util.Map;

import com.ylp.date.login.Login;
import com.ylp.date.mgr.user.IUser;

/**
 * 用于连线的相关服务入口
 * 
 * @author Qiaolin Pan
 * 
 */
public class LineService {
	/**
	 * 其id 是一个md5值 通过所有的用户id求出的MD5
	 */
	private Map<String, List<IUser>> userPool;
	private List<String> userIds;

	/**
	 * 
	 * @param login
	 * @return
	 */
	List<IUser> getLineUser(Login login) {
		/**
		 * 1）从注册用户中随机出4男4女编为一组。男女通过性别标签来分辨。
		 * （2）如果该组中某对男女被连线达到3次，则配对成功。同时该组合解散，剩余3男3女返回数据库，重新生成其他组。
		 * （3）每个用户每天可以最多查看并连线3组，每组中只能连线一对男女。
		 * （4）用户自己每天最多被匹配成功一次。如果用户连着3天没有被匹配成功，则在第四天抽取时
		 * ，先只从这批人里抽取。即系统每天凌晨抽取时，要先从连着三天没有匹配成功的人中生成组来匹配。
		 * （4）系统每天凌晨自动生成5组，5组随机展现给用户
		 * 。每匹配成功一组，则系统自动生成新的一组。新生成的一组仍然从没有匹配成功的人中抽取，直至匹配成功的人数占连续3天未匹配人数的一半
		 * 。也可以直接定个数值，如直至匹配成功15人。然后再从整体用户中随机匹配。 （5）从整体用户中抽取时需排除掉3类人: a.今日已配对成功的；
		 * b.正在其他组中展示的；
		 * c.已配对成功但一直没有查看的。即如果用户配对成功，但是一直没有登录查看，则不再将用户进行新的匹配（抽取时不抽取他
		 * /她）。这样也能保证用户每次打开网站，最多只有一个匹配对象。
		 */
		return null;
	}

}
