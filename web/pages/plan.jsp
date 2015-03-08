<%@page import="com.ylp.date.controller.ControlUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>朝夕</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link
	href="../static/thirdparty/flat-ui/css/jquery-ui-1.10.4.custom.min.css"
	rel="stylesheet">
<link href="../static/thirdparty/flat-ui/bootstrap/css/bootstrap.css"
	rel="stylesheet">
<link href="../static/thirdparty/flat-ui/css/flat-ui.css"
	rel="stylesheet">
<link rel="shortcut icon"
	href="../static/thirdparty/flat-ui/images/favicon.ico">
<!--[if lt IE 9]>
      <script src="../static/thirdparty/flat-ui/js/html5shiv.js"></script>
      <script src="../static/thirdparty/flat-ui/js/respond.min.js"></script>
    <![endif]-->
<link rel="stylesheet" href="../static/css/nav.css">
<link rel="stylesheet" href="../static/css/plan.css">
<link rel="stylesheet" href="../static/css/zzsc.css">
</head>



<body>
	<header>
		<nav class="navbar navbar-default" role="navigation">
			<div class="container">
				<div class="navbar-header">
					<a class="navbar-brand" href="#">朝夕</a>
				</div>
				<div class="collapse navbar-collapse" id="navbar-collapse-01">
					<ul class="nav navbar-nav">
						 <li class="active"><a href="">新年计划</a></li>          
              			<li><a href="../match.do">园游会</a></li>
              			<li><a href="usermatch.do" id="myLine">我的匹配</a></li>
             			<li><a href="/user/userflower.do" id="myFlower">我的花</a></li>
       					<li ><a href="/user/userline.do">我的牵线</a></li>
             		    <li><a href="userinfo.do?action=setting">设置</a></li>
              			<li><a href="" class="logout-btn">退出</a></li>
					</ul>
				</div>


			</div>
		</nav>
	</header>

	<div class="container">
		<div class="form-wrapper">
			<form class="form-inline" role="form" style="width:100%">

				<div class="form-group">

					<label for="fav" class="col-lg-8"><span
						style="font-size: 24px">我的新年计划：</span></label>
					<div class="col-sm-16">
						<input type="text" class="form-control" name="plan" id="planinput">
						<button id="plansubmit" name="" class="btn btn-primary btn-wide">发布</button>
					</div>

				</div>
				<br> <br> <br>
				<hr />
			</form>
			<div id="planlistcon">
				
			</div>

			<a href="#0" class="cd-top">Top</a>
		</div>
	</div>
	<script src="../static/thirdparty/flat-ui/js/jquery-1.8.3.min.js"></script>
	<script
		src="../static/thirdparty/flat-ui/js/jquery-ui-1.10.4.custom.min.js"></script>
	<script
		src="../static/thirdparty/flat-ui/js/jquery.ui.touch-punch.min.js"></script>
	<script src="../static/thirdparty/flat-ui/js/bootstrap.min.js"></script>
	<script src="../static/thirdparty/flat-ui/js/bootstrap-select.js"></script>
	<script src="../static/thirdparty/flat-ui/js/bootstrap-switch.js"></script>
	<script src="../static/thirdparty/flat-ui/js/flatui-checkbox.js"></script>
	<script src="../static/thirdparty/flat-ui/js/flatui-radio.js"></script>
	<script src="../static/thirdparty/flat-ui/js/jquery.tagsinput.js"></script>
	<script src="../static/thirdparty/flat-ui/js/jquery.placeholder.js"></script>
	<script src="../pages/js/zzsc.js"></script>
	<script src="../pages/js/plan.js"></script>
	<script src="../pages/js/util.js"></script>

	<script>
	var planlist;
	var loginId='<%=ControlUtil.getLogin(request).getUser().getId()%>';
		$(function() {
			var con=$('#planlistcon');
			$(document).scroll(function(){
				//滚动到底部
				if($(document).scrollTop()>=$(document).height()-$(window).height()){
					planlist.listOld();
				}
			});
			planlist=new PlanList(window,con);
			$('#plansubmit').click(function(e){
				debugger;
				if(isEmpty($('#planinput').val())){
					showMessage('计划不能为空！');
					return;
				}
				$.ajax({
					type: "POST",
					url: "/user/plan.do",
					data: { action: 'add',content:$('#planinput').val()},
					success: function(data) {
						planlist.init();
					},
					error: function (xhr, textStatus, errorThrown) {
							self.isListing=false;
						    showMessage('出现错误');
						 }
			});
				e.stopPropagation();
			});
			
		});
	</script>
</body>
</html>
