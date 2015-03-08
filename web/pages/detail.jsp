<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.ylp.date.mgr.user.IUser"%>
<%@page import="com.ylp.date.mgr.IBaseObj"%>
<%@page import="java.util.List"%>
<%@page import="com.ylp.date.mgr.user.impl.User"%>
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
    <link rel="stylesheet" href="../static/css/detail.css">
  </head>
  <body>
  <%
  User user=(User)request.getAttribute("user");
  boolean userNull=user==null;
  boolean isGender=true;
  String checked=" checked=\"checked\"";
  boolean age1=userNull||user.getAgeRange()==User.AGE_18_20;
  boolean age2=!userNull&&user.getAgeRange()==User.AGE_20_23;
  boolean age3=!userNull&&user.getAgeRange()==User.AGE_23_26;
  boolean age4=!userNull&&user.getAgeRange()==User.AGE_26_30;
  boolean age5=!userNull&&user.getAgeRange()==User.AGE_30_OLD;
  String userCaption="";
  if(!userNull){
	  //设置用户信息 
	  isGender=user.getGender()==IUser.MALE;
	  userCaption=user.getCaption(); 
  }
  age2=(!age1&&!age2&&!age3&&!age4&&!age5);
 Boolean bool= ((Boolean)request.getAttribute("fromLogin"));
 String msg=(String)session.getAttribute("savemsg");
 session.removeAttribute("savemsg");
 boolean fromLogin=bool==null?false:bool.booleanValue();
  %>
    <div class="container">
      <%if(fromLogin){ %>  <%} else{%>
      <header> <nav class="navbar navbar-default" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="../match.do">朝夕</a>
		</div>
		<div class="collapse navbar-collapse" id="navbar-collapse-01">
			<ul class="nav navbar-nav">
			 <li ><a href="/user/plan.do">新年计划</a></li>         
				<li><a href="../match.do">园游会</a></li>
				<li ><a href="usermatch.do" id="myLine">我的匹配</a></li>
			   <li><a href="/user/userflower.do" id="myFlower">我的花</a></li>
        <li><a href="/user/userline.do">我的牵线</a></li>
				<li class="active"><a href="">设置</a></li>
				<li><a href="" class="logout-btn">退出</a></li>
			</ul>
		</div>

	</div>
	</nav> </header>
      <%} %>
        <div class="form-wrapper">
            <form class="form-horizontal" role="form" action="userinfo.do" method="post" enctype="multipart/form-data" id="submitform">
            <input type="hidden" name="userid" value="<%=request.getAttribute("userid")%>"/>
              <input type="hidden" name="action" value="update"/>
             <%if(!fromLogin){ %> 
             	<input type="hidden" name="from" value="setting"/>
             <%}else{%>
              <div class="form-group" style="padding-left: 50px;">
                 <img src="../static/img/regsuc.png" style='display:inline-block;width:100px;height:100px;'/>
                 <div style="display:inline-block;vertical-align:middle;">
	                  <label style="font-size: 45px;font-weight: bold;display:block;line-height:1">你好，欢迎来到朝夕</label>
                 </div>
                   <label style="display:block;font-size:20px;padding-left:10px;margin-top:35px;">介绍下自己吧，别忘了再传张美照哦</label>
              </div>
              <%} 
              		if(msg!=null){
              %>
              <div class="form-group">
               <img src="../static/img/upsuc.png" style='display:inline-block;margin-left:51px;width:60px;'/>
                <label style="font-size:20px;"><%=msg %></label>
              </div>
             <%} %>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label">昵称</label>
                <div class="col-sm-10">
                   <input type="text" class="form-control" id="usercaption" name="usercaption" placeholder="输入一个拉风的昵称吧" value="<%=userCaption==null?"":userCaption%>">
                </div>
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label">性别</label>
                <div class="col-sm-10">
                  <label><input type="radio" name="gender" value="10" <%=isGender?checked:"" %>>男</label> <label><input type="radio" name="gender" value="11" <%=!isGender?checked:"" %>>女</label>
                </div>
              </div>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label">年龄</label>
                <div class="col-sm-10">
                  <label><input type="radio" name="age" value="<%=User.AGE_18_20 %>"  <%=age1?checked:"" %>>18-20</label> 
                  <label><input type="radio" name="age" value="<%=User.AGE_20_23 %>"   <%=age2?checked:""%>>20-23</label> 
                  <label><input type="radio" name="age" value="<%=User.AGE_23_26 %>"  <%=age3?checked:""%>>23-26</label> 
                  <label><input type="radio" name="age" value="<%=User.AGE_26_30 %>" <%=age4?checked:""%>>26-30</label> 
                  <label><input type="radio" name="age" value="<%=User.AGE_30_OLD %>" <%=age5?checked:""%>>30+</label>
                </div>
              </div>
              <%
              List<IBaseObj> sugs=(List)request.getAttribute("sugList");
              if(sugs!=null&&!sugs.isEmpty()){
            	  for(IBaseObj obj:sugs){
              %>
               <div class="form-group">
                <label for="fav" class="col-sm-2 control-label"><%=obj.getCaption() %></label>
                <div class="col-sm-10">
                  <input type="text" class="form-control" id="<%="sugid:"+obj.getId() %>" name="<%="sugid:"+obj.getId() %>" placeholder="贴一个标签" value="<%=request.getAttribute(obj.getId())%>">
                </div>
              </div>
             <%
            	  }
              }
              %>
              <div class="form-group">
                <label for="" class="col-sm-2 control-label">美照</label>
                <div class="col-sm-10">
                  <input id="avatarInput" name="avatar" type="file" class="hidden">
                  <button id="uploadBtn" class="btn btn-default navbar-btn btn-xs">点击上传</button>
                  <span id="avatar" class="avatar"></span>
                </div>
              </div>
          </form> 
              <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10" style="padding-left:350px;">
                  <button id="form_submitbutton" class="btn btn-primary btn-wide">提交</button>
                </div>
              </div>
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
    <script src="../pages/js/util.js"></script>
    <script src="/pages/js/unread.js"></script>
    <script>
        $(function () {
            var input = document.getElementById('avatarInput');
            var cont = document.getElementById('avatar');
            var uploadBtn = document.getElementById('uploadBtn');
            var summitBtn=$('#form_submitbutton');
            summitBtn.click(function(evt){
            	var userCaption=$("#usercaption").val();
            	if(validate(userCaption)){
            		showMessage('昵称必须输入哦');
            		return false;
            	}else{
            		$('#submitform').submit();
            	}
            });
            function getFile(event) {
                var file = this.files[0];
                var img = document.createElement("img");

                cont.innerHTML = '';
                cont.appendChild(img);

                var reader = new FileReader();
                reader.onload = function(e) {
                    img.src = this.result;
                    uploadBtn.innerHTML = '更改';
                }
                reader.readAsDataURL(file);
            }
            input.addEventListener('change', getFile);
            uploadBtn.addEventListener('click', function (e) {
                input.click();
                e.preventDefault();
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