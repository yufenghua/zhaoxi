<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>错误提示页面</title>
</head>
<body>
发生错误，错误信息如下：
<% if(request.getAttribute("date_server_exception")!=null){
	Exception e=(Exception)request.getAttribute("date_server_exception");
	e.printStackTrace(response.getWriter());
}%>
</body>
</html>