<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page import="com.sea.weather.utils.Cache"%>
<%@page import="com.sea.weather.utils.Cachekey"%>
<%@ page language="java" import="java.io.PrintWriter" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>测试应用</title>
</head>
<body>
<%
	Cache.putValue(Cachekey.aboutkey, null);
	PrintWriter outPrint = response.getWriter();
	outPrint.print("成功清理");
	outPrint.close();
%>
</body>
</html>