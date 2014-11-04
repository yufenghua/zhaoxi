/**
 * 
 * json对象数据结构
 * {
“me”: “male”, //当前用户的性别
“male”: [
{
“id”: 3434353455,
“photo”: “xxxxxxx.png”,
“favorite”: “呵呵”,
“idol”: “潘巧林”,
“others”: “xxxxx”
},{
“id”: 3434353455,
“photo”: “xxxxxxx.png”,
“favorite”: “呵呵”,
“idol”: “潘巧林”,
“others”: “xxxxx”
},{
“id”: 3434353455,
“photo”: “xxxxxxx.png”,
“favorite”: “呵呵”,
“idol”: “潘巧林”,
“others”: “xxxxx”
},{
“id”: 3434353455,
“photo”: “xxxxxxx.png”,
“favorite”: “呵呵”,
“idol”: “潘巧林”,
“others”: “xxxxx”
}
],
“female”: [
{
“id”: 3434353455,
“photo”: “xxxxxxx.png”,
“favorite”: “呵呵”,
“idol”: “潘巧林”,
“others”: “xxxxx”
},{
“id”: 3434353455,
“photo”: “xxxxxxx.png”,
“favorite”: “呵呵”,
“idol”: “潘巧林”,
“others”: “xxxxx”
},{
“id”: 3434353455,
“photo”: “xxxxxxx.png”,
“favorite”: “呵呵”,
“idol”: “潘巧林”,
“others”: “xxxxx”
},{
“id”: 3434353455,
“photo”: “xxxxxxx.png”,
“favorite”: “呵呵”,
“idol”: “潘巧林”,
“others”: “xxxxx”
}
]
}
 */
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
	this.opsiteUl = $('<ul>');
	this.opsiteUl.addClass('list');
	this.opsiteUl.addClass('clearfix');

	this.sameUl = $('<ul>');
	this.sameUl.addClass('list');
	this.sameUl.addClass('clearfix');

	this.parent.append(this.opsiteUl);
	this.parent.append(this.sameUl);
};
MatchInfoMgr.prototype.refresh = function() {
	var self = this;
	$.ajax({
	    type: "POST",
	    url: "../match.do?action=getMatchInfo",
	    contentType: "application/json; charset=utf-8",
	    dataType: "json",
	    success: function(data) {
	    	self.userInfo = data.user;
			self.infoId = data.id;
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
	    	alert('出现错误' + textStatus);
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

};
/**
 * 获取同性
 * 
 * @returns
 */
MatchInfoMgr.prototype.getSameSex = function() {

};
/**
 * 
 * @param id
 */
MatchInfoMgr.prototype.getItem = function(id) {

};
/**
 * 连线动作
 * 
 * @param id1
 * @param id2
 */
MatchInfoMgr.prototype.match = function(id1, id2) {

};
/**
 * 
 */
function MatchUser(userObj, mgr, isSame) {
	this.userObj = userObj;
	this.mgr = mgr;
	this.isSame = isSame;
}
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

	this.imgDiv = $('<div>');
	this.imgDiv.addClass('photo');
	this.img = $('<img>');
	this.img.attr('src', this.userObj.img);
	this.imgDiv.append(this.img);
	this.liDom.append(this.imgDiv);

	this.tagsDiv = $('<div>');
	var tags = this.userObj.tags;
	var tag;
	for (var i = 0; i < tags.length; i++) {
		tag = $('<p>');
		tag.text(tags[i]);
		this.tagsDiv.append(tag);
	}
	this.liDom.append(this.tagsDiv);
	if (!this.isSame) {
		this.flowerDiv = $('<div>');
		this.flower = $('<a>');
		this.flower.addClass('send-flower');
		this.flower.attr('title','送花');
		this.flower.attr('href','flower.do');
		this.flowerDiv.append(this.flower);
		this.liDom.append(this.flowerDiv);
		this.mgr.opsiteUl.append(this.liDom);
		this.mgr.opsiteUser[this.userObj.id] = this;
	} else {
		this.mgr.sameUl.append(this.liDom);
		this.mgr.sameUser[this.userObj.id] = this;
	}
};
/**
 * 
 */
MatchUser.prototype.canMatch = function(id) {

};
