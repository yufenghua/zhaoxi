<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link
	href="/zhaoxi/static/thirdparty/flat-ui/bootstrap/css/bootstrap.css"
	rel="stylesheet">
<link href="/zhaoxi/static/thirdparty/flat-ui/css/flat-ui.css"
	rel="stylesheet">
<link rel="shortcut icon"
	href="/zhaoxi/static/thirdparty/flat-ui/images/favicon.ico">
<!--[if lt IE 9]>
      <script src="../static/thirdparty/flat-ui/js/html5shiv.js"></script>
      <script src="../static/thirdparty/flat-ui/js/respond.min.js"></script>
    <![endif]-->
<link rel="stylesheet" href="/zhaoxi/static/css/nav.css">
<title></title>
<style type="text/css">
img {
	float: left
}

table.all {
	width: 100%;
	padding:10px 10px 10px 10px;
	border:1px solid;
}

.infotd {
	height: 30px;
}

.btn {
	width: 60px;
	margin:10px;
	}
</style>
</head>
<body>
	<header> <nav class="navbar navbar-default" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="#">朝夕</a>
		</div>
		<div class="collapse navbar-collapse" id="navbar-collapse-01">
			<ul class="nav navbar-nav">
				<li class="active"><a href="">首页</a></li>
				<li><a href="/zhaoxi/user/usermatch.do">我的匹配</a></li>
				<li><a href="/zhaoxi/user/userflower.do">我的花</a></li>
				<li><a href="/zhaoxi/user/userline.do">我的牵线</a></li>
				<li><a href="/zhaoxi/user/userinfo.do?action=setting">设置</a></li>
				<li><a href="" class="logout-btn">退出</a></li>
			</ul>
		</div>

	</div>
	</nav> </header>
	<div class="container" id="auditlistContainer"></div>
</body>
</html>
 <script src="/zhaoxi/static/thirdparty/flat-ui/js/jquery-1.8.3.min.js"></script>
<script src="/zhaoxi/pages/js/util.js" type="text/javascript"></script>
<script src="/zhaoxi/pages/js/useraudit.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	var mgr=new UserAuditMgr($('#auditlistContainer'));
	mgr.list();
});
</script>