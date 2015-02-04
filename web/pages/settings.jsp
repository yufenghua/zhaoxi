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
    <link rel="stylesheet" href="../static/css/settings.css">
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
              <li><a href="/user/usermatch.do" id="myLine">我的匹配</a></li>
              <li><a href="/user/userflower.do" id="myFlower">我的花</a></li>
        <li><a href="/user/userline.do">我的牵线</a></li>
              <li class="active"><a href="">设置</a></li>
              <li><a href="" class="logout-btn">退出</a></li>
            </ul> 
          </div>
          
        </div>
      </nav>
    </header>
    <div class="container">
        <div class="form-wrapper">
            <form class="form-horizontal" role="form">
              <h5>我的照片</h5>
              <div class="form-group">
                <div class="col-sm-10">
                  <input id="avatarInput" name="avatar" type="file" class="hidden">
                  <span id="avatar" class="avatar"><img id="img" src="../static/img/photo/photo.png"></span>
                  <button id="uploadBtn" class="btn btn-default navbar-btn">换照片</button>
                </div>
              </div>
              
              <h5>我的标签</h5>
              <div class="">
                <span class="info">
                  <span id="labelInfo" class="">刘涵 23-26 打篮球 朴树 不爱说话</span>
                  <textarea id="labelInput" class="" name="label" style="display:none;">刘涵 23-26 打篮球 朴树 不爱说话</textarea>
                </span>
                <a id="changeBtn" href="javascript:;" class="btn btn-default navbar-btn">换标签</a>
              </div>

              <div class="form-group" style="padding-top:3em;">
                <div class="col-sm-offset-2 col-sm-10">
                  <button type="submit" class="btn btn-primary btn-wide">提交</button>
                </div>
              </div>
            </form>
        </div>
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
    <script src="/pages/js/unread.js"></script>
    <script>
        $(function () {
            var input = document.getElementById('avatarInput');
            var img = document.getElementById("img");
            function getFile(event) {
                var file = this.files[0];
                var reader = new FileReader();
                reader.onload = function(e) {
                    img.src = this.result;
                }
                reader.readAsDataURL(file);
            }
            input.addEventListener('change', getFile);
            uploadBtn.addEventListener('click', function (e) {
                input.click();
                e.preventDefault();
            });

            $('#changeBtn').on('click', function (e) {
              e.preventDefault();
              $('#labelInfo').hide();
              $('#labelInput').show();
              $(this).hide();
            });
        });
    </script>
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