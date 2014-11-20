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
	    	if(!matchs){
	    		return;
	    	}
	    	var matchs = data.matchs;
	    	if (!matchs) {
	    		return;
	    	};
	    	var container=$(mainContainer);
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
 *<span class="time">2014年5月3日</span>
            <div class="gap"><img src="../static/img/flower.jpg"></div>
            <span class="from">来自</span>
            <div class="link">
              <img src="../static/img/photo/photo2.png">
              <span>某某某</span>
            </div>
            <div class="chat">
              <button type="submit" class="btn btn-primary btn-wide">同意聊天</button>
            </div>
 */
function handleWithItem(container,item){
	 var li=$('<li>');
	 var time=$('<span>');
	 time.addClass('time');
	 time.text(item.time);
	 li.append(time);

	 var gapDiv=$('<div>');
	 gapDiv.addClass('gap');
	 var gapImg=$('<img>');
	 gapImg.attr('src','../static/img/flower.jpg');
	 gapDiv.append(gapImg);
	 li.append(gapDiv);

	 var fromSpan=$('<span>');
	 fromSpan.addClass('from');
	 fromSpan.text(item.isSend?'送给':'来自');

	 var linkDiv=$('<div>');
	 linkDiv.addClass('link');

	 var linkImg=$('<img>');
	 linkImg.attr('src',item.img)
	 linkDiv.append(linkImg);

	 var linkSpan=$('<span>');
	 linkSpan.text(item.userCaption);
	 linkDiv.append(linkSpan);
	 li.append(linkDiv);

	 

	 var chatDiv=$('<div>');
	 chatDiv.addClass('chat');
	 var chatButton=$('<button>');
	 chatButton.attr('class','btn btn-primary btn-wide');
	 chatButton.text((!item.isSend&&!item.success)?'同意聊天':'聊天');
	 if(item.isSend&&!item.success){
	 	chatButton.CSS('display','none');
	 }
	 chatDiv.append(chatButton);
	 li.append(chatDiv);

	 container.append(li);
}
