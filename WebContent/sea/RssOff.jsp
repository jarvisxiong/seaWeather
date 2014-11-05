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
	String rssOff = (String)request.getParameter(Cachekey.rssOffkey);
	Cache.putValue(Cachekey.rssOffkey, rssOff);
	PrintWriter outPrint = response.getWriter();
	outPrint.print(rssOff);
	outPrint.close();
%>
</body>
</html>