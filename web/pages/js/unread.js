'use strict';

$(function () {
	function render(data) {
		var flower = parseInt(data.flowerCount);
		var line = parseInt(data.lineCount);
		if (flower > 0) {
			$('#myFlower').append('<span class="unread">' + flower + '</span>');
		}
		if (line > 0) {
			$('#myLine').append('<span class="unread">' + line + '</span>');
		}
	}
	$.ajax({
		url: '/zhaoxi/user/userinfo.do',
		data: {
			action: 'newinfo'
		},
		dataType: 'json',
		success: render
	});
});