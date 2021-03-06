'use strict';

$(function () {
	function render(data) {
		var flower = parseInt(data.flowerCount);
		var flowerMsgCount=parseInt(data.msgcount4f,10);
		var lineMsgCount=parseInt(data.msgcount4l,10);
		var planMsgCount=parseInt(data.msgcount4p,10);
		var line = parseInt(data.lineCount);
		if (flower > 0) {
			$('#myFlower').append('<span class="unread">' + flower + '</span>');
		}else if(flowerMsgCount>0){
			$('#myFlower').append('<span class="unread">' + flowerMsgCount + '</span>');
		}
		if (line > 0) {
			$('#myLine').append('<span class="unread">' + line + '</span>');
		}else if(lineMsgCount>0){
			$('#myLine').append('<span class="unread">' + lineMsgCount + '</span>');
		}
		if(planMsgCount>0){
			$('#mypartner').append('<span class="unread">' + planMsgCount + '</span>');
		}
	}
	$.ajax({
		url: '/user/userinfo.do',
		data: {
			action: 'newinfo'
		},
		dataType: 'json',
		success: render
	});
});