'use strict';
(function () {
    var appid = 'or9r4zjq12oiq2uq71c9plewab8z5d8j56yqm8em7akpajh2';
    var appkey = 'vz88ncphghx62yjo0or13rfhdakki9xe7l2bbnkxdwgk8yrh';
    var me;
    var toPeerId;
    var watching = [];

    (function () {
        var to = location.search.match(/(\?|\&)to=([^\?\&]+)/i);
        var name = location.search.match(/(\?|\&)name=([^\?\&]+)/i);
        toPeerId = to && to.length ? to[to.length - 1] : 'testuser-' + Math.random();
        name = name && name.length ? name[name.length - 1] : toPeerId;
        document.title = '与【' + decodeURIComponent(name) + '】对话';
        watching.push(toPeerId);
    }());

    (function () {
        var user = document.cookie.match(/(^|(\s?))DATE_LOGIN_USER=([^;]+)/i);
        me = user && user.length ? user[user.length - 1] : null;
    }());
    console.log(me, toPeerId);
    var chat = new AVChatClient({
        appId: appid,
        peerId: me,
        secure: true,
        // server:'us',//使用美国节点
        // sp:true,//超级用户 不watch即可发送消息 需要配合 auth使用。
        // auth: auth, //当网站设置开启签名的时候需要
        // groupAuth: groupAuth,
        watchingPeerIds: watching
    });

    chat.on('message', function (data) {
        var msg = data.msg;
        console.log('message receive----------');
        console.log(data);
        $('#msgcontainer').append('<div class="msg-receive">' + msg + '</div>');
    });

    chat.on('online', function (peers) {
        console.log(peers);
    });

    chat.open().then(function () {
        console.log('chat open success');
    }, function (err) {
        console.log('chat open fail');
    });

    function send() {
        var msg = $('#msginput').val();
        if (msg) {
            chat.send(msg, toPeerId).then(function () {
                $('#msgcontainer').append('<div class="msg-send">' + msg + '</div>');
                $('#msginput').val('');
            }, function (err) {
                console.log('send fail');
            });
        }
    }

    $('#sendbtn').on('click', send);
    $('#msginput').on('keyup', function (e) {
        if (e.keyCode === 13) {
            send();
        }
    });

}());