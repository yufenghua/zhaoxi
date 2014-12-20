package com.ylp.date.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.ylp.date.login.Login;
import com.ylp.date.mgr.IBaseObj;
import com.ylp.date.mgr.relation.IRelation;
import com.ylp.date.mgr.relation.impl.RelationBldMgr;
import com.ylp.date.mgr.relation.impl.RelationMgr;
import com.ylp.date.mgr.user.IUser;
import com.ylp.date.mgr.user.impl.UserMgr;
import com.ylp.date.server.Server;
import com.ylp.date.server.ServerConfigRation;
import com.ylp.date.server.SpringNames;
import com.ylp.date.util.CollectionTool;
import com.ylp.date.util.StringTools;

/**
 * 用于连线的相关服务入口
 * 
 * @author Qiaolin Pan
 * 
 */
@Component(SpringNames.LineService)
@DependsOn({ SpringNames.Server, SpringNames.ServerConfigRation })
@Lazy(true)
public class LineService implements Runnable {
	private static final Logger logger = LoggerFactory
			.getLogger(LineService.class);
	private static final int ONE_DAY = 1000 * 60 * 60 * 24;
	private ReadWriteLock lock = new ReentrantReadWriteLock();
	private Lock read = lock.readLock();
	private Lock write = lock.writeLock();
	private Map<String, List<String>> lined = new HashMap<String, List<String>>();
	/**
	 * 其id 是一个md5值 通过所有的用户id求出的MD5
	 */
	private Map<String, LineUsersObj> userPool;
	private List<LineUsersObj> lineUsers;
	private int defaultLength;
	private SortedMap<String, Integer> userDisplay;
	private boolean isMaleFulled;
	private boolean isFemaleFulled;
	private Date today;
	private Random random;
	@Autowired
	private ServerConfigRation configration;
	@Autowired
	private UserMgr userMgr;
	@Autowired
	private RelationMgr relationMgr;
	@Autowired
	private RelationBldMgr relationBuilderMgr;

	public void init() {
		write.lock();
		try {
			defaultLength = configration.getLineLength();
			int max = Math.max(16, defaultLength);
			userPool = new HashMap<String, LineUsersObj>(max);
			userDisplay = new TreeMap<String, Integer>();
			lineUsers = new ArrayList<LineUsersObj>();
			userMgr.regListener(new UserListenerForLine());
			relationMgr.regListener(new RelationListenerForLine());
			run();
			Server.getInstance().getScheduledService().scheduleWithFixedDelay(this, getTodayCost(), ONE_DAY, TimeUnit.MILLISECONDS);
		} finally {
			write.unlock();
		}
	}

	private long getTodayCost() {
		Calendar cal=Calendar.getInstance();
		//延迟20分钟
		return ONE_DAY-(cal.getTime().getTime()-today.getTime())+1000*60*20;
	}

	public void markBuild(String one, String other, String user) {
		write.lock();
		try {
			for (LineUsersObj lineUser : lineUsers) {
				if (lineUser.contains(one) && lineUser.contains(other)) {
					markBuild(lineUser.getKey(), user);
				}
			}
		} finally {
			write.unlock();
		}
	}

	/**
	 * 对某个lineuser进行连线后，标志改组对该user不显示
	 * 
	 * @param lineId
	 * @param user
	 */
	public void markBuild(String lineId, String user) {
		write.lock();
		try {
			List<String> lineBuiled = lined.get(lineId);
			if (lineBuiled == null) {
				lineBuiled = new ArrayList<String>();
				lineBuiled.add(user);
				lined.put(lineId, lineBuiled);
				return;
			}
			if (!lineBuiled.contains(user)) {
				lineBuiled.add(user);
				lined.put(lineId, lineBuiled);
			}
		} finally {
			write.unlock();
		}
	}

	/**
	 * 能否连线
	 * 
	 * @param lineObjId
	 * @param userId
	 * @return
	 */
	public boolean canBuild(String lineObjId, String userId) {
		read.lock();
		try {
			List<String> list = lined.get(lineObjId);
			return CollectionTool.checkNull(list) ? true : (!list
					.contains(userId));
		} finally {
			read.unlock();
		}
	}

	/**
	 * 
	 * @param login
	 * @return
	 */
	public LineUsersObj getLineUser(Login login) {
		write.lock();
		try {
			if (!login.isLogined()) {
				return null;
			}
			String id = login.getUser().getId();
			LineUsersObj results = null;
			// 有一次都还没有展示的 那么 展示此组
			if (userDisplay.size() != userPool.size()) {
				results = handleHasNoDisplay(id);
				if (results != null) {
					logger.info("输出值" + results.getKey());
					return results;
				}
			}
			// 否则 随机选取一组 有两种情况 ：1.所有的组都未被展示 2.所有的组都已被展示
			if (userDisplay.isEmpty()) {
				results = handleAllNoDisplay(id);
				logger.info("输出值" + (results == null ? "木有" : results.getKey()));
				return results;
			} else {
				results = handleAllDisplay(id);
				logger.info("输出值" + (results == null ? "木有" : results.getKey()));
				return results;
			}
		} finally {
			write.unlock();
		}
	}

	private LineUsersObj handleAllDisplay(String id) {
		String key = null;
		for (Map.Entry<String, Integer> entry : userDisplay.entrySet()) {
			String key2 = entry.getKey();
			if (StringUtils.isNotEmpty(key)) {
				if (userPool.get(key2)==null||userPool.get(key).contains(id)) {
					continue;
				}
				if (userDisplay.get(key) > entry.getValue()) {
					key = key2;
				}
			} else {
				if (userPool.get(key2)==null||userPool.get(key2).contains(id)) {
					continue;
				}
				key = key2;
			}
		}
		if (StringUtils.isNotEmpty(key)) {
			userDisplay.put(key, userDisplay.get(key) + 1);
			return userPool.get(key);
		}
		return null;
	}

	private LineUsersObj handleAllNoDisplay(String id) {
		for (Map.Entry<String, LineUsersObj> entry : userPool.entrySet()) {
			LineUsersObj value = entry.getValue();
			if (value.contains(id)) {
				continue;
			}
			userDisplay.put(entry.getKey(), 1);
			return value;
		}
		return null;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	private LineUsersObj handleHasNoDisplay(String id) {
		String key = null;
		for (Map.Entry<String, LineUsersObj> entry : userPool.entrySet()) {
			String key2 = entry.getKey();
			if (userDisplay.containsKey(key2)) {
				continue;
			}
			if (entry.getValue().contains(id)) {
				continue;
			}
			key = key2;

		}
		if (StringUtils.isNotEmpty(key)) {
			userDisplay.put(key, 1);
			return userPool.get(key);
		}
		return null;
	}

	public void whenUserAdd(IUser obj) {
		write.lock();
		try {
			IUser user = (IUser) obj;
			if (user.getGender() == 0) {
				return;
			}
			String id = obj.getId();
			for (Map.Entry<String, LineUsersObj> entry : userPool.entrySet()) {
				LineUsersObj value = entry.getValue();
				if (value.contains(id)) {
					return;
				}
				int gender = user.getGender();
				int femaleSize = value.getFemale().size();
				int maleSize = value.getMale().size();
				// 保证男女对称
				if ((!value.isFemaleFulled() && gender == IUser.FEMALE)
						&& (maleSize - femaleSize == 1 || maleSize == femaleSize)) {
					value.addUser(user);
					return;
				}
				if ((!value.isMaleFulled() && gender == IUser.MALE)
						&& (femaleSize - maleSize == 1 || maleSize == femaleSize)) {
					value.addUser(user);
					return;
				}
			}
			if (userPool.size() != defaultLength) {
				LineUsersObj lineobj = new LineUsersObj();
				lineobj.addUser(user);
				userPool.put(lineobj.getKey(), lineobj);
				return;

			}
		} finally {
			write.unlock();
		}
	}

	public void whenUserRemove(String id) {
		write.lock();
		try {
			List<String> keys = new ArrayList<String>();
			for (Map.Entry<String, LineUsersObj> entry : userPool.entrySet()) {
				LineUsersObj value = entry.getValue();
				if (value.contains(id)) {
					keys.add(entry.getKey());
				}
			}
			if (keys.isEmpty()) {
				return;
			}
			for (String string : keys) {
				userPool.remove(string);
				userDisplay.remove(string);
			}
		} finally {
			write.unlock();
		}
	}

	@Override
	/**
	 * 1）从注册用户中随机出4男4女编为一组。男女通过性别标签来分辨。
	 * （2）如果该组中某对男女被连线达到3次，则配对成功。同时该组合解散，剩余3男3女返回数据库，重新生成其他组。
	 * （3）每个用户每天可以最多查看并连线3组，每组中只能连线一对男女。
	 * （4）用户自己每天最多被匹配成功一次。如果用户连着3天没有被匹配成功，则在第四天抽取时
	 * ，先只从这批人里抽取。即系统每天凌晨抽取时，要先从连着三天没有匹配成功的人中生成组来匹配。
	 * （4）系统每天凌晨自动生成5组，5组随机展现给用户
	 * 。每匹配成功一组，则系统自动生成新的一组。新生成的一组仍然从没有匹配成功的人中抽取，直至匹配成功的人数占连续3天未匹配人数的一半
	 * 。也可以直接定个数值，如直至匹配成功15人。然后再从整体用户中随机匹配。 
	 * （5）从整体用户中抽取时需排除掉3类人: a.今日已配对成功的；
	 * b.正在其他组中展示的；
	 * c.已配对成功但一直没有查看的。即如果用户配对成功，但是一直没有登录查看，则不再将用户进行新的匹配（抽取时不抽取他
	 * /她）。这样也能保证用户每次打开网站，最多只有一个匹配对象。
	 */
	public void run() {
		// FIXME 现在有一个问题 这个在琢磨一下
		write.lock();
		try {
			logger.info("运行时间"+StringTools.formateDate(new Date()));
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			today = cal.getTime();
			// 设计的随机算法原则如下：
			// 1.根据shownum降序
			doRun();
			if (CollectionTool.checkNull(lineUsers)) {
				return;
			}
			Map<Future<List<String>>, LineUsersObj> map = new HashMap<Future<List<String>>, LineUsersObj>(
					lineUsers.size());
			buildFutureMap(map);
			buildLineMap(map);
		} finally {
			write.unlock();
		}
	}

	private void buildLineMap(Map<Future<List<String>>, LineUsersObj> map) {
		for (Future<List<String>> future : map.keySet()) {
			try {
				List<String> list = future.get();
				lined.put(map.get(future).getKey(), list);
			} catch (Exception e) {
				logger.error("LinedUserChecker error", e);
			}
		}
	}

	private void buildFutureMap(Map<Future<List<String>>, LineUsersObj> map) {
		for (LineUsersObj lineUser : lineUsers) {
			LinedUserChecker checker = new LinedUserChecker(lineUser,
					relationMgr, relationBuilderMgr);
			Future<List<String>> future = Server.getInstance()
					.getThreadPoolService().submit(checker);
			map.put(future, lineUser);
		}
	}

	private void doRun() {
		List<IBaseObj> firstMale = getFirst(true);
		List<IBaseObj> firstFemale = getFirst(false);
		adjustMale(firstMale);
		adjustFamale(firstFemale);
		// 如果上述完成 返回
		if (isMaleFulled && isFemaleFulled) {
			return;
		}
		// 否则 往池中插入男或者女
		if (isMaleFulled) {
			handleWithFemale();
			return;
		} else {
			handWithMale();
			if (!isFemaleFulled) {
				handleWithFemale();
			}
			return;
		}
	}

	private void handWithMale() {
		// 查询出 所有没有匹配成功
		List<IBaseObj> users = getUsersSecond(true);
		if (users.isEmpty()) {
			logger.warn("没有符合条件的用户");
			return;
		}
		List<Integer> selectedIndexes = new ArrayList<Integer>();
		boolean needCreate = userPool.size() != defaultLength;
		while (true) {
			int index = genarateIndex(users.size());
			while (!selectedIndexes.contains(index)) {
				selectedIndexes.add(index);
			}
			IUser user = (IUser) users.get(index);
			String userId = user.getId();
			for (Map.Entry<String, LineUsersObj> lineUsers : userPool
					.entrySet()) {
				LineUsersObj value = lineUsers.getValue();
				if (value.contains(userId)) {
					continue;
				}
				if (!value.isMaleFulled()) {
					value.addUser(user);
				}
				String key = lineUsers.getKey();
				userPool.put(key, value);
				break;
			}
			if (allFulled(false)) {
				break;
			}
			if (selectedIndexes.size() == users.size()) {
				break;
			}
		}
		int index = genarateIndex(users.size());
		while (needCreate && selectedIndexes.size() != users.size()) {
			while (!selectedIndexes.contains(index)) {
				selectedIndexes.add(index);
			}
			IUser user = (IUser) users.get(index);
			LineUsersObj obj = new LineUsersObj();
			obj.addUser(user);
			userPool.put(obj.getKey(), obj);
			needCreate = userPool.size() != defaultLength;
		}
	}

	private void handleWithFemale() {
		// 查询出 所有没有匹配成功
		List<IBaseObj> users = getUsersSecond(false);
		List<Integer> selectedIndexes = new ArrayList<Integer>();
		// 是否需要创建
		boolean needCreate = userPool.size() != defaultLength;
		if (users.isEmpty()) {
			logger.warn("没有符合条件的用户");
			return;
		} // 循环退出条件是 line池满了 或者所有用户都已经遍历一边
		int size = users.size();
		while (true) {
			int index = genarateIndex(size);
			while (!selectedIndexes.contains(index)) {
				selectedIndexes.add(index);
			}
			IUser user = (IUser) users.get(index);
			String userId = user.getId();
			for (Map.Entry<String, LineUsersObj> lineUsers : userPool
					.entrySet()) {
				LineUsersObj value = lineUsers.getValue();
				if (value.contains(userId)) {
					continue;
				}
				if (!value.isFemaleFulled()) {
					value.addUser(user);
				}
				String key = lineUsers.getKey();
				userPool.put(key, value);
				break;
			}
			if (allFulled(false)) {
				break;
			}
			if (selectedIndexes.size() == size) {
				break;
			}
		}
		// 如果需要创建的情况
		int index = genarateIndex(size);
		while (needCreate && selectedIndexes.size() != size) {
			while (!selectedIndexes.contains(index)) {
				selectedIndexes.add(index);
			}
			IUser user = (IUser) users.get(index);
			LineUsersObj obj = new LineUsersObj();
			obj.addUser(user);
			userPool.put(obj.getKey(), obj);
			lineUsers.add(obj);
			needCreate = userPool.size() != defaultLength;
		}
	}

	private boolean allFulled(boolean b) {
		for (Map.Entry<String, LineUsersObj> lineUsers : userPool.entrySet()) {
			LineUsersObj value = lineUsers.getValue();
			if (!value.isFemaleFulled()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 产生一个随机数
	 * 
	 * @param size
	 * @return
	 */
	private int genarateIndex(int size) {
		random = new java.util.Random();
		return random.nextInt(size);
	}

	private List<IBaseObj> getUsersSecond(boolean b) {
		String hql = "from User user_ "
				+ "where user_.gender=? and"
				// 三天内连线成功，但今天没有
				+ " (user_.lastLine is not null and  user_.lastLine >? and user_.lastLine<?) and"
				+ " user_.id not in "
				+ "("
				// 有连线成功，没有确认的人
				+ "select rel_.one from UserRelation rel_ where rel_.type=? and  rel_.recognition=? and rel_.oneReg is  null) "
				+ " and  user_.id not in ("
				+ "select rel_.otherOne from UserRelation rel_ where rel_.type=? and  rel_.recognition=? and rel_.otherOneReg is  null"
				+ ") order by user_.lastShowNum";
		List<Object> list = new ArrayList<Object>(5);
		list.add(b ? IUser.MALE : IUser.FEMALE);
		list.add(new Date(System.currentTimeMillis() - 2 * ONE_DAY));
		list.add(today);
		list.add(IRelation.TYPE_LINE);
		list.add(IRelation.RECOG_LINE);
		list.add(IRelation.TYPE_LINE);
		list.add(IRelation.RECOG_LINE);
		return userMgr.executeQuery(hql, list.toArray());
	}

	private void adjustFamale(List<IBaseObj> firstMale) {
		int count = 0;
		for (LineUsersObj lineUser : this.lineUsers) {
			while (!lineUser.isFemaleFulled()) {
				if (count < firstMale.size()) {
					IUser user = (IUser) firstMale.get(count);
					lineUser.addUser(user);
					logger.debug(user.getId() + "加入" + lineUser.getKey());
				}
				if (lineUser.isFemaleFulled() || count == firstMale.size()) {
					userPool.put(lineUser.getKey(), lineUser);
					if (count == firstMale.size()) {
						break;
					}
				}
				count++;
			}
			if (count < firstMale.size()) {
				// TODO
			}
		}
	}

	private void adjustMale(List<IBaseObj> firstMale) {
		int size = firstMale.size();
		if (userPool.isEmpty()) {
			LineUsersObj obj = new LineUsersObj();
			String key = obj.getKey();
			logger.debug("创建对象{}", key);
			for (int i = 0; i <= size; i++) {
				if (i != size) {
					IUser user = (IUser) firstMale.get(i);
					obj.addUser(user);
					logger.debug(user.getId() + "加入" + key);
				}

				if (obj.isMaleFulled() || i == size) {
					userPool.put(key, obj);
					lineUsers.add(obj);
					if (i != size) {
						obj = new LineUsersObj();
						key = obj.getKey();
						logger.debug("创建对象{}", key);
					}
				}
				if (userPool.size() == defaultLength) {
					break;
				}
			}
		}
	}

	/**
	 * 获取三天没有匹配成功，且没有任何已成立的关系没有查看的人
	 * 
	 * @param b
	 * @return
	 */
	private List<IBaseObj> getFirst(boolean b) {
		String hql = "from User user_ "
				+ "where user_.gender=? and"
				// 联系三天连线未成功
				+ " (user_.lastLine is null or user_.lastLine <?) and"
				+ " user_.id not in "
				+ "("
				// 有连线成功，没有确认的人
				+ "select rel_.one from UserRelation rel_ where rel_.type=? and  rel_.recognition=? and rel_.oneReg is  null)"
				+ "and  user_.id not in("
				+ "select rel_.otherOne from UserRelation rel_ where rel_.type=? and  rel_.recognition=? and rel_.otherOneReg is  null"
				+ ") order by user_.lastShowNum";
		List<Object> list = new ArrayList<Object>(5);
		int gender = b ? IUser.MALE : IUser.FEMALE;
		list.add(gender);
		logger.debug("params:" + gender);
		// 三天内全部没有匹配成功
		Date date = new Date(System.currentTimeMillis() - 3 * ONE_DAY);
		list.add(date);
		logger.debug("params:" + date);
		list.add(IRelation.TYPE_LINE);
		logger.debug("params:" + IRelation.TYPE_LINE);
		list.add(IRelation.RECOG_LINE);
		logger.debug("params" + IRelation.RECOG_LINE);
		list.add(IRelation.TYPE_LINE);
		logger.debug("params" + IRelation.TYPE_LINE);
		list.add(IRelation.RECOG_LINE);
		logger.debug("params" + IRelation.RECOG_LINE);
		return userMgr.executeQuery(hql, list.toArray());
	}

	public void remove(String one, String otherOne) {
		write.lock();
		try {
			List<LineUsersObj> obj = new ArrayList<LineUsersObj>();
			for (LineUsersObj lineUser : this.lineUsers) {
				if (lineUser.contains(one) && lineUser.contains(otherOne)) {
					obj.add(lineUser);
				}
			}
			for (LineUsersObj lineUsersObj : obj) {
				this.lineUsers.remove(obj);
				this.userPool.remove(lineUsersObj.getKey());
			}
			// FIXME 随机性是个问题
			run();
		} finally {
			write.unlock();
		}
	}
}
