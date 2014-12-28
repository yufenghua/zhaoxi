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
	    	if(data.cupidValue){
	    		$("#cupid").text(data.cupidValue);
	    	}else{
	    		$("#cupid").text(0);
	    	}
	    	var matchs = data.lines;
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
 *<li>
            <span class="time">2014年5月3日</span>
            <div class="link">
              <img src="../static/img/photo/photo.png">
              <span>某某某</span>
            </div>
            <div><img src="../static/img/done.jpg"></div>
            <div class="link">
              <img src="../static/img/photo/photo2.png">
              <span>某某某2</span>
            </div>
          </li>
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
	 linkImg.attr('src',item.oneImg)
	 linkDiv.append(linkImg);
	 var linkSpan=$('<span>');
	 linkSpan.text(item.userCaption);
	 linkDiv.append(linkSpan);
	 li.append(linkDiv);


	 var gapDiv=$('<div>');
	 var gapImg=$('<img>');
	 gapImg.attr('src','../static/img/done.jpg');
	 gapDiv.append(gapImg);
	 li.append(gapDiv);

	 var otherLinkDiv=$('<div>');
	 otherLinkDiv.addClass('link');

	 var otherLinkImg=$('<img>');
	 otherLinkImg.attr('src',item.otherImg)
	 otherLinkDiv.append(otherLinkImg);
	 var otherLinkSpan=$('<span>');
	 otherLinkSpan.text(item.userCaption);
	 otherLinkDiv.append(otherLinkSpan);
	 li.append(otherLinkDiv);

	 container.append(li);
}
