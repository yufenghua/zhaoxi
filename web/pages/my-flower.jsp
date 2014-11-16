<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
    <title>朝夕</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="../static/thirdparty/flat-ui/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="../static/thirdparty/flat-ui/css/flat-ui.css" rel="stylesheet">
    <link rel="shortcut icon" href="../static/thirdparty/flat-ui/images/favicon.ico">
    <!--[if lt IE 9]>
      <script src="../static/thirdparty/flat-ui/js/html5shiv.js"></script>
      <script src="../static/thirdparty/flat-ui/js/respond.min.js"></script>
    <![endif]-->
    <link rel="stylesheet" href="../static/css/nav.css">
    <link rel="stylesheet" href="../static/css/my-flower.css">
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
              <li><a href="">首页</a></li>
          <li><a href="usermatch.do">我的匹配</a></li>
              <li class="active"><a href="">我的花</a></li>
              <li><a href="">我的牵线</a></li>
              <li><a href="">设置</a></li>
              <li><a href="" class="logout-btn">退出</a></li>
            </ul> 
          </div>
          
        </div>
      </nav>
    </header>
    <div class="container">
        <ul class="link-list">
        </ul>
    </div>

    <script src="../static/thirdparty/flat-ui/js/jquery-1.8.3.min.js"></script>
    <script src="../static/thirdparty/flat-ui/js/jquery-ui-1.10.3.custom.min.js"></script>
    <script src="../static/thirdparty/flat-ui/js/jquery.ui.touch-punch.min.js"></script>
    <script src="../static/thirdparty/flat-ui/js/bootstrap.min.js"></script>
    <script src="../static/thirdparty/flat-ui/js/bootstrap-select.js"></script>
    <script src="../static/thirdparty/flat-ui/js/bootstrap-switch.js"></script>
    <script src="../static/thirdparty/flat-ui/js/flatui-checkbox.js"></script>
    <script src="../static/thirdparty/flat-ui/js/flatui-radio.js"></script>
    <script src="../static/thirdparty/flat-ui/js/jquery.tagsinput.js"></script>
    <script src="../static/thirdparty/flat-ui/js/jquery.placeholder.js"></script>
     <script src="../pages/js/util.js"></script>
     <script src="../pages/js/myflower.js"></script>
    <script>
        $(function () {
        	initDatas('userflower.do');
            $('#refresh-btn').on('click', function (e) {
              e.preventDefault();
              window.location.reload();
            });
        });
    </script>
  </body>
</html>