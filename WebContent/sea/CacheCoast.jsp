<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page import="com.sea.weather.date.nmc.action.CoastWeatherAction"%>
<%@ page language="java" import="java.io.PrintWriter" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>测试应用</title>
</head>
<body>
<%
PrintWriter outPrint = response.getWriter();
CoastWeatherAction objCoastWeatherAction = new CoastWeatherAction();
String str = objCoastWeatherAction.getCoastCache();
outPrint.print(str);
outPrint.close();
%>
</body>
</html>