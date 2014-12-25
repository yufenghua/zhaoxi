<%@page import="com.ylp.date.controller.ControlUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
</body>
</html>
<%
if(!ControlUtil.getLogin(request).isLogined()){
	response.sendRedirect("user/join.do");
}else{
	response.sendRedirect(request.getContextPath() + "/match.do");
}
%>