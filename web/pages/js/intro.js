'use strict';
function start() {
    $('#matchlistContainer ul li').eq(0).attr('data-intro', '拖动上面照片到下面的照片上，即连线成功。体验一下吧！').attr('data-step', '1');
    $('#matchlistContainer ul li a.send-flower').eq(1).attr('data-intro', '喜欢，就赞TA一下！谁知道有什么惊喜呢~').attr('data-step', '2');
    introJs().start();
    $.ajax({
        url: '/zhaoxi/user/helpinfo.do?action=read&version=1.0'
    });
}
setTimeout(function () {
    $.ajax({
        url: '/zhaoxi/user/helpinfo.do?action=check',
        dataType: 'json',
        success: function (data) {
            if (!data.read) {
                start();
            }
        }
    });
}, 1000);