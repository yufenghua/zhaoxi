/**
 * <ul class="list clearfix">
 <li>
 <div class="photo">
 <img src="../static/img/photo/photo.png">
 </div>
 <div class="info">
 <p>喜欢做xxx</p>
 <p>偶像xxx</p>
 <p>其他xxx</p>
 </div>
 <a class="send-flower" href="" title="送花"></a>
 </li>
 </ul>
 <!--下列表-->
 <ul class="list clearfix">
 <li>
 <div class="photo">
 <img src="../static/img/photo/photo2.png">
 </div>
 <div class="info">
 <p>喜欢做xxx</p>
 <p>偶像xxx</p>
 <p>其他xxx</p>
 </div>
 </li>
 </ul>
 * */
/**
 * 配对信息管理对象
 */
function MatchInfoMgr(window, parent) {
	this.wnd = window;
	this.parent = parent;
	this._init();
}
MatchInfoMgr.prototype._init = function() {
	this.sameUser={};
	this.opsiteUser={};
	this.users={};
	this.opsiteUl = $('<ul>');
	this.opsiteUl.addClass('list');
	this.opsiteUl.addClass('clearfix');

	this.sameUl = $('<ul>');
	this.sameUl.addClass('list');
	this.sameUl.addClass('clearfix');

	this.parent.append(this.opsiteUl);
	this.parent.append(this.sameUl);
	/**
	 *  <div class="submit-area">
            <button class="btn btn-default btn-wide" id="refresh-btn">换一组</button>
            <button type="submit" class="btn btn-primary btn-wide">确定</button>
          </div>
	 */
	this.btnArea=$('<div>');
	this.btnArea.addClass('submit-area');
	//换一组 按钮
	this.refreshBtn=$('<button>');
	this.refreshBtn.addClass('btn');
	this.refreshBtn.addClass('btn-default');
	this.refreshBtn.addClass('btn-wide');
	this.refreshBtn.text('换一组');
	this.refreshBtn.owner=this;
	var self=this;
	this.refreshBtn.bind('click', function() { 
		self.refresh();
	 });
	this.btnArea.append(this.refreshBtn);
	//FIXME 2014 1109 确定按钮感觉没有存在的必要，暂时不管他
	this.parent.append(this.btnArea);
};
MatchInfoMgr.prototype.getRefreshBtn=function(){
	return this.refreshBtn;
};
/**
 * 刷新
 */
MatchInfoMgr.prototype.refresh = function() {
	this.clear();
	var self = this;
	$.ajax({
	    type: "POST",
	    url: "/zhaoxi/match.do?action=getMatchInfo",
	    contentType: "application/json; charset=utf-8",
	    dataType: "json",
	    success: function(data) {
	    	self.userInfo = data.user;
			self.infoId = data.id;
			self._canMatch=data.canMatch;
			var sameUsers = data.same;
			if (sameUsers) {
				for (var i = 0; i < sameUsers.length; i++) {
					self._addSame(sameUsers[i]);
				}
			}
			var opsiteUsers = data.opposite;
			if (opsiteUsers) {
				for (var i = 0; i < opsiteUsers.length; i++) {
					self._addOpsite(opsiteUsers[i]);
				}
			}
	    },
	    error: function (xhr, textStatus, errorThrown) {
	    	showMessage('出现错误' + textStatus);
	    }
	});
};
MatchInfoMgr.prototype._addSame = function(user) {
	this._addUser(user, true);
};
MatchInfoMgr.prototype._addOpsite = function(user) {
	this._addUser(user, false);
};
/**
 * 
 */
MatchInfoMgr.prototype._addUser = function(user, isSame) {
	var userInfo = new MatchUser(user, this, isSame);
	userInfo.init();
};
/**
 * 获取当前登录id
 */
MatchInfoMgr.prototype.getUserId = function() {
	return this.userId;
};
/**
 * 设置userid
 * 
 * @param userId
 */
MatchInfoMgr.prototype.setUserId = function(userId) {
	this.userId = userId;
};
/**
 * 获取当前连线组的id
 */
MatchInfoMgr.prototype.getInfoId = function() {
	return this.infoId;
};
/**
 * 当前登录者的信息
 */
MatchInfoMgr.prototype.getUserObj = function() {
	return this.userInfo;
};
/**
 * 获取异性列表
 * 
 * @returns 返回异性列表的数组
 */
MatchInfoMgr.prototype.getOppositeSex = function() {
return this.opsiteUsers;
};
/**
 * 获取同性
 * 
 * @returns
 */
MatchInfoMgr.prototype.getSameSex = function() {
	return this.sameUsers;
};
/**
 * 
 * @param id
 */
MatchInfoMgr.prototype.getItem = function(id) {
	return this.users[id];
};
/**
 * 连线动作
 * 
 * @param id1
 * @param id2
 */
MatchInfoMgr.prototype.match = function(id1, id2) {
	var self = this;
	$.ajax({
	    type: "POST",
	    url: "/zhaoxi/match.do?action=MatchUser",
	    data: { user: id1, other: id2},
	    success: function(data) {
	    	if(data.suc){
	    		showMessage('连线成功！');
		    	self.refresh();
	    	}else{
	    		showMessage('ops,连线失败了，换个人再试一次吧。'+data.msg);
	    	}
	    },
	    error: function (xhr, textStatus, errorThrown) {
	    	showMessage('出现错误' + textStatus);
	    }
	});
};
/**
 *清除方法
 */
MatchInfoMgr.prototype.clear=function(){
	this.sameUl.empty();
	this.opsiteUl.empty();
	this.sameUsers={};
	this.opsiteUsers={};
	this.users={};
};
/**
 *是否能够连接
 */
MatchInfoMgr.prototype.canMatch=function(){
	return this._canMatch;
}
/**
 * 
 */
function MatchUser(userObj, mgr, isSame) {
	this.userObj = userObj;
	this.mgr = mgr;
	this.isSame = isSame;
}
MatchUser.LINE_SIDE=100;
/**
 * <li> <div class="photo"> <img src="../static/img/photo/photo.png"> </div>
 * <div class="info">
 * <p>
 * 喜欢做xxx
 * </p>
 * <p>
 * 偶像xxx
 * </p>
 * <p>
 * 其他xxx
 * </p>
 * </div> <a class="send-flower" href="" title="送花"></a> </li>
 */
MatchUser.prototype.init = function() {
	this.liDom = $('<li>');
	var self=this;
	//拖动执行函数
	$(this.liDom).draggable({
		stop:function(event,ui){
			if(!self.mgr.canMatch()){
				return false;
			}
			var position=ui.offset;
			var opsiteSex=self.isSame?self.mgr.getOppositeSex():self.mgr.getSameSex();
			if (!opsiteSex) {
				return false;
			};
			$.each(opsiteSex,function(userObjId,value){
				var tagetPosition=value.getPosition();
				//是否重合的算法 逻辑是 二者的定位误差不超过100像素
				if(Math.abs(position.left-tagetPosition.left)<=MatchUser.LINE_SIDE&&Math.abs(position.top-tagetPosition.top)<=MatchUser.LINE_SIDE){
					showConfirm('确认连线'+self.userObj.name+'和'+value.userObj.name+'吗？',"",function(){
						self.mgr.match(self.userObj.id,userObjId);
					});
				};
			});
		}
	});
	//拖动之后回到起始位置
	$(this.liDom).draggable({ revert: true });


	this.imgDiv = $('<div>');
	this.imgDiv.addClass('photo');
	this.img = $('<img>');
	this.img.attr('src', this.userObj.img);
	this.imgDiv.append(this.img);
	this.liDom.append(this.imgDiv);

	this.tagsDiv = $('<div>');
	//用户名
	var userCapTag = $('<p>');
	var userCaption=this.userObj.name;
	var userAgeRange='未知年龄段';
	switch(this.userObj.age){
		case(1):{
			userAgeRange='18-20';
			break;
		}
		case(2):{
			userAgeRange='23-26';
			break;
		}
		case(3):{
			userAgeRange='20-23';
			break;
		}
		case(4):{
			userAgeRange='26-30';
			break;
		}
		case(5):{
			userAgeRange='30+';
			break;
		}
	}

	userCapTag.html(userCaption+'&nbsp&nbsp&nbsp&nbsp'+userAgeRange);
	this.tagsDiv.append(userCapTag);

	var tags = this.userObj.tags;
	var tag;
	for (var i = 0; i < tags.length; i++) {
		tag = $('<p>');
		tag.html(tags[i].tagsug+":&nbsp&nbsp&nbsp&nbsp"+tags[i].tagInfo);
		this.tagsDiv.append(tag);
	}
	this.liDom.append(this.tagsDiv);
	if (!this.isSame) {
		this.flowerDiv = $('<div>');
		this.flower = $('<a>');
		this.flower.addClass('send-flower');
		this.flower.attr('title','送花');
		this.flower.bind('click',function(){
			$.ajax({
				    type: "POST",
				    url: "/zhaoxi/match.do?action=flower",
				    data: { target: self.userObj.id,},
				    success: function(data) {
				    	showMessage('花已经送出去了，祝你好运');
				    },
				    error: function (xhr, textStatus, errorThrown) {
				    	showMessage('出现错误' + textStatus);
				    }
				});
		})
		this.flowerDiv.append(this.flower);
		this.liDom.append(this.flowerDiv);
		this.mgr.opsiteUl.append(this.liDom);
		this.mgr.opsiteUsers[this.userObj.id] = this;
	} else {
		this.mgr.sameUl.append(this.liDom);
		this.mgr.sameUsers[this.userObj.id] = this;
	}
	this.mgr.users[this.userObj.id] = this;
};
/**
 * 
 */
MatchUser.prototype.canMatch = function(id) {

};
/**
 * 获取用户展示的当前位置
 */
MatchUser.prototype.getPosition=function(){
	return this.liDom.offset();
};
