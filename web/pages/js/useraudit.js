function UserAuditMgr(parent){
	this.parent=parent;
};
UserAuditMgr.prototype._init = function() {
};
UserAuditMgr.prototype.audit=function(id){

};
/**
 *<table class="all">
		<tr>
			<td>
				<img src="E:/html/ceshi.png" width="120" >
			</td>

			<td rowspan="2" >
				<table>
					<tr>
						 <td style="height:30px;"> 昵称：xxxxx</td>
					</tr>
					<tr>
						<td style="height:30px;"> 年龄：正值妙龄  </td>
					</tr>
					<tr>
						 <td style="height:30px;">爱好：打球，跑步，游泳  </td>
					</tr>
					<tr>
						 <td style="height:30px;">性别：男 </td>
					</tr>
				
				</table>
			</td>
		</tr>		
		<tr >
				<td >
				   <input type="button" value="审核" style="width:60px"/> 
				   <input type="button" value="删除" style="width:60px"/> 
				   <input type="button" value="详细" style="width:60px"/> 
				</td>
		</tr>
	</table>
 */
UserAuditMgr.prototype._addUser=function(user){
	var addTextInfo=function(text){
		var textTr=$('<tr>');
		var textTd=$('<td>');
		textTd.addClass('infotd');
		textTd.text(text);
		textTr.append(textTd);
		return textTr;
	};
	UserAuditMgr.prototype._addUser=function(user){
		var userCon=$('<table>');
		userCon.addClass('all');
		//基本信息
		var infoTr=$('<tr>');
		//图片
		var imgtd=$('<td>');
		var img=$('<img>');
		img.attr('src',user.img);
		img.css('width','120px');
		imgtd.append(img);
		infoTr.append(imgtd);

		//基本信息
		var infoTd=$('<td>');
		infoTd.attr('rowspan','2');
		var infoTable=$('<table>');
		infoTable.append(addTextInfo("昵称："+user.caption));
		infoTable.append(addTextInfo("年龄："+getAgeRangeText(user.agerender)));
		if (user.tags) {
			for (var i = 0; i < user.tags.length; i++) {
				infoTable.append(addTextInfo(user.tags[i].sug+"："+user.tags[i].tag));
			};
		}
		infoTd.append(infoTable);
		infoTr.append(infoTd);
		userCon.append(infoTr);

		//按钮
		var btnTr=$('<tr>');
		var btnTd=$('<td>');
		var auditBtn=$('<input>');
		auditBtn.attr('type','button');
		auditBtn.attr('value','审核');
		auditBtn.addClass('btn');
		btnTd.append(auditBtn);

		var rejectBtn=$('<input>');
		rejectBtn.attr('type','button');
		rejectBtn.attr('value','退回');
		rejectBtn.addClass('btn');
		btnTd.append(rejectBtn);
		
		btnTr.append(btnTd);
		userCon.append(btnTr);

		this.parent.append(userCon);
	};
	this._addUser(user);
};
UserAuditMgr.prototype.list=function(){
	var self=this;
	$.ajax({
		type: "POST",
		url: "/zhaoxi/user/audit.do",
		data: { action: 'listunaudit'},
		success: function(data) {
			if (!data) {
				return;
			}
			if(!data.users){
				return;
			}
			for (var i = 0; i < data.users.length; i++) {
				self._addUser(data.users[i]);
			};
		},
		error: function (xhr, textStatus, errorThrown) {
			alert('出现错误' + textStatus);
		}
	});


};