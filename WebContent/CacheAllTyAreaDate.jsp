<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page import="com.sea.weather.date.action.SeaWeatherDateAction"%>
<%@page import="com.sea.weather.utils.SeaConstant"%>
<%@page import="com.sea.weather.utils.StringUtils"%>
<%@ page language="java" import="java.io.PrintWriter" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>测试应用</title>
</head>
<body>
<%
	SeaWeatherDateAction objSeaWeatherDateAction = new SeaWeatherDateAction();
	PrintWriter outPrint = response.getWriter();
	String verstr = objSeaWeatherDateAction.getCacheAllTfAreaVOJson();
	outPrint.print(verstr);
	outPrint.close();
%>
</body>
</html>