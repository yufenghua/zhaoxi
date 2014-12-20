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
	    	alert('出现错误' + textStatus);
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

	 var linkSpan=$('<span>');
	 linkSpan.text(item.userCaption);
	 linkDiv.append(linkSpan);
	 li.append(linkDiv);

	 var gapDiv=$('<div>');
	 gapDiv.addClass('gap');
	 var gapImg=$('<img>');
	 gapImg.attr('src','../static/img/done.jpg');
	 gapDiv.append(gapImg);
	 li.append(gapDiv);

	 var chatDiv=$('<div>');
	 chatDiv.addClass('chat');
	 var chatButton=$('<button>');
	 chatButton.attr('class','btn btn-primary btn-wide');
	 chatButton.text('聊天');
	 chatDiv.append(chatButton);
	 li.append(chatDiv);

	 container.append(li);
}
