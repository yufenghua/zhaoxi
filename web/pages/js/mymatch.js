/**
 *初始化数据
 */
function initDatas(url){
	$.ajax({
	    type: "POST",
	    url: url+"?action=list",
	    contentType: "application/json; charset=utf-8",
	    dataType: "json",
	    success: function(data) {
	    	var matchs = data.matchs;
	    	if (!matchs) {
	    		return;
	    	};
	    	var container=$('#mainContainer');
	    	for (var i = 0; i < matchs.length; i++) {
	    		var item=matchs[i];
	    		handleWithItem(container,item);
	    	};
	    },
	    error: function (xhr, textStatus, errorThrown) {
	    	showMessage('出现错误' + textStatus);
	    }
	});
}
/**
 *<li><span class="time">2014年5月3日</span>
				<div class="link">
					<img src="../static/img/photo/photo.png"> <span>某某某</span>
				</div>
				<div class="gap">
					<img src="../static/img/done.jpg">
				</div>
				<div class="chat">
					<button type="submit" class="btn btn-primary btn-wide">聊天</button>
				</div></li>
 */
function handleWithItem(container,item){
	 var li=$('<li>');
	 var time=$('<span>');
	 time.addClass('time');
	 time.text(item.time);
	 li.append(time);

	 var linkDiv=$('<div>');
	 linkDiv.addClass('link');

	 var linkImg=$('<img>');
	 linkImg.attr('src',item.img)
	 linkDiv.append(linkImg);

	 var linkSpan=$('<div>');
	 handleWithUserInfo(linkSpan,item);
	 linkDiv.append(linkSpan);
	 li.append(linkDiv);

	 var gapDiv=$('<div>');
	 gapDiv.addClass('gap');
	 var gapImg=$('<img>');
	 gapImg.attr('src','../static/img/done.jpg');
	 gapDiv.append(gapImg);
	 li.append(gapDiv);

	 var user = document.cookie.match(/(^|(\s?))DATE_LOGIN_USER=([^;]+)/i);
     var me = user && user.length ? user[user.length - 1] : null;

	 var chatDiv=$('<div>');
	 chatDiv.addClass('chat');
	 var chatButton=$('<a>');
	 chatButton.attr('href', '../static/chat/main/index.html?' +
	 	'me=' + me +
	 	'&to=' + item.otherid +
	 	'&name=' + item.userCaption);
	 chatButton.attr('target', '_blank');
	 chatButton.attr('class','btn btn-primary btn-wide');
	 chatButton.text('聊天');
	 chatDiv.append(chatButton);
	 //未读消息
	 if (item.msgcount!=0) {
	 	chatDiv.append('<span class="unread">' + item.msgcount + '</span>')
	 };
	 li.append(chatDiv);

	 container.append(li);
}

function handleWithUserInfo(container,userObj){
	//用户名
	var userCapTag = $('<p>');
	var userCaption=userObj.userCaption;
	var userAgeRange='未知年龄段';
	switch(userObj.age){
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

	userCapTag.html(userCaption+'&nbsp&nbsp&nbsp&nbsp'+userAgeRange+'<br/>');
	container.append(userCapTag);

	var tags = userObj.tags;
	var tag;
	for (var i = 0; i < tags.length; i++) {
		tag = $('<p>');
		tag.html(tags[i].tagsug+":&nbsp&nbsp&nbsp&nbsp"+tags[i].tagInfo+'<br/>');
		container.append(tag);
	}
}
