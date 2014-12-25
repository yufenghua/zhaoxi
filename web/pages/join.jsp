<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>朝夕</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
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
<link rel="stylesheet" href="../static/css/register.css">
</head>
<body>
	<div class="container">
		<div class="form-wrapper">
			<form id="register" action="join.do" method="post"
				enctype="multipart/form-data">
				<input type="hidden" name="action" value="reg" />
				<div class="form-group">
					<input type="text" name="username" class="form-control"
						id="username_reg" placeholder="用户名" />
				</div>
				<div class="form-group">
					<input type="password" class="form-control" name="password"
						id="password_reg" placeholder="密码" />
				</div>
				<div class="form-group">
					<input type="text" class="form-control" name="email" id="email_reg"
						placeholder="email" />
				</div>
				<div class="form-group">
					<input type="file" class="form-control" name="file" /> <span
						class="tip">请上传证件照片</span>
				</div>
				<div class="form-group">
					<input id="submit_reg" type="submit"
						class="btn btn-embossed btn-primary btn-wide" value="注册" />
				</div>
			</form>
			<form id="login" action="login.do" method="post">
				<input type="hidden" name="action" value="login" />
				<div class="form-group">
					<input type="text" class="form-control" name="username" id="username_login"
						placeholder="用户名" />
				</div>
				<div class="form-group">
					<input type="password" class="form-control" placeholder="密码"
						name="password" id="password_login" />
				</div>
				<div class="form-group">
					<input type="submit" class="btn btn-embossed btn-primary btn-wide" id="submit_login"
						value="登录" />
				</div>
			</form>
		</div>
		<button class="btn btn-embossed btn-default btn-wide toggle-btn"
			id="toggle">登录</button>
	</div>

	<script src="../static/thirdparty/flat-ui/js/jquery-1.8.3.min.js"></script>
	<script
		src="../static/thirdparty/flat-ui/js/jquery-ui-1.10.3.custom.min.js"></script>
	<script
		src="../static/thirdparty/flat-ui/js/jquery.ui.touch-punch.min.js"></script>
	<script src="../static/thirdparty/flat-ui/js/bootstrap.min.js"></script>
	<script src="../static/thirdparty/flat-ui/js/bootstrap-select.js"></script>
	<script src="../static/thirdparty/flat-ui/js/bootstrap-switch.js"></script>
	<script src="../static/thirdparty/flat-ui/js/flatui-checkbox.js"></script>
	<script src="../static/thirdparty/flat-ui/js/flatui-radio.js"></script>
	<script src="../static/thirdparty/flat-ui/js/jquery.tagsinput.js"></script>
	<script src="../static/thirdparty/flat-ui/js/jquery.placeholder.js"></script>
	<script src="../pages/js/util.js"></script>
	<script>
		$(function() {
			var $toggle = $('#toggle');
			var $forms = $('#register, #login');
			var lock = true;
			$toggle.on('click', function(e) {
				$forms.toggle();
				this.innerHTML = lock ? '注册' : '登录';
				$forms.filter(':visible').find('input:first').focus();
				lock = !lock;
			});
			//注册按钮提交逻辑
			$('#submit_reg').on('click', function(e) {
				var username = $('#username_reg').val();
				if (isEmpty(username)) {
					alert('用户名不能为空！');
					return false;
				}
				var password = $('#password_reg').val();
				if (isEmpty(password)) {
					alert('密码不能为空！');
					return false;
				}
				var email = $('#email_reg').val();
				if (isEmpty(email)) {
					alert('邮箱不能为空！');
					return false;
				}
				$('#register').submit();
				e.stopPropagation();
			});
			$('#submit_login').on('click', function(e) {
				var username = $('#username_login').val();
				if (isEmpty(username)) {
					alert('用户名不能为空！');
					return false;
				}
				var password = $('#password_login').val();
				if (isEmpty(password)) {
					alert('密码不能为空！');
					return false;
				}
				$('#login').submit();
				e.stopPropagation();
			});
		});
	</script>
</body>
</html>