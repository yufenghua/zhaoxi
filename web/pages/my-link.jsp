<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <title>朝夕</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
   	 <link href="../static/thirdparty/flat-ui/css/jquery-ui-1.10.4.custom.min.css" rel="stylesheet">
    <link href="../static/thirdparty/flat-ui/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="../static/thirdparty/flat-ui/css/flat-ui.css" rel="stylesheet">
    <link rel="shortcut icon" href="../static/thirdparty/flat-ui/images/favicon.ico">
    <!--[if lt IE 9]>
      <script src="../static/thirdparty/flat-ui/js/html5shiv.js"></script>
      <script src="../static/thirdparty/flat-ui/js/respond.min.js"></script>
    <![endif]-->
    <link rel="stylesheet" href="../static/css/nav.css">
    <link rel="stylesheet" href="../static/css/my-link.css">
  </head>
  <body>
    <header>
      <nav class="navbar navbar-default" role="navigation">
        <div class="container">
          <div class="navbar-header">
            <a class="navbar-brand" href="../match.do">朝夕</a>
          </div>
          <div class="collapse navbar-collapse" id="navbar-collapse-01">
            <ul class="nav navbar-nav">    
            <li ><a href="/user/plan.do">新年计划</a></li>          
              <li><a href="../match.do">园游会</a></li>
              <li><a href="usermatch.do" id="myLine">我的匹配</a></li>
              <li><a href="/user/userflower.do" id="myFlower">我的花</a></li>
                 <li ><a href="/user/partner.do" id="mypartner">我的伙伴</a></li>
        <li class="active"><a href="/user/userline.do">我的牵线</a></li>
              <li><a href="userinfo.do?action=setting">设置</a></li>
              <li><a href="" class="logout-btn">退出</a></li>
            </ul>
          </div>
          
        </div>
      </nav>
    </header>
    <div class="container" >
        <p class="cupid" >幸运值<em id="cupid"></em></p>
        <ul class="link-list" id="mainContainer">
         
        </ul>
    </div>

    <script src="../static/thirdparty/flat-ui/js/jquery-1.8.3.min.js"></script>
    <script src="../static/thirdparty/flat-ui/js/jquery-ui-1.10.4.custom.min.js"></script>
    <script src="../static/thirdparty/flat-ui/js/jquery.ui.touch-punch.min.js"></script>
    <script src="../static/thirdparty/flat-ui/js/bootstrap.min.js"></script>
    <script src="../static/thirdparty/flat-ui/js/bootstrap-select.js"></script>
    <script src="../static/thirdparty/flat-ui/js/bootstrap-switch.js"></script>
    <script src="../static/thirdparty/flat-ui/js/flatui-checkbox.js"></script>
    <script src="../static/thirdparty/flat-ui/js/flatui-radio.js"></script>
    <script src="../static/thirdparty/flat-ui/js/jquery.tagsinput.js"></script>
    <script src="../static/thirdparty/flat-ui/js/jquery.placeholder.js"></script>
        <script src="../pages/js/util.js"></script>
     <script src="../pages/js/mylink.js"></script>
    <script src="/pages/js/unread.js"></script>
    <script>
        $(function () {
        	initDatas('userline.do');
            $('#refresh-btn').on('click', function (e) {
              e.preventDefault();
              window.location.reload();
            });
        });
    </script>
    
    <!-- 页脚 -->
   <div id="footer">
		<div>
			<a href="/static/aboutus/about.html" target="_blank">关于我们</a>
			<span>|</span>
			<a href="/static/aboutus/contact.html" target="_blank">联系我们</a>
			<span>|</span>
			<a href="/static/aboutus/faq.html" target="_blank">常见问题</a>
			<span>|</span>
			<a href="/static/aboutus/join.html" target="_blank">加入我们</a>
	    </div>
		<p class="copyright">Copyright © 1998 - 2015 imzhaoxi. 京ICP备15006259号</p>
	</div>
  </body>
</html>
<script>
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "//hm.baidu.com/hm.js?b725df11d54455c0fe21ab9c5c7ef7e2";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();
</script>
