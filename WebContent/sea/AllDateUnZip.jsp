<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page import="com.sea.weather.date.nmc.action.AllDateAction"%>
<%@ page language="java" import="java.io.PrintWriter" %>
<%@ page language="java" import="com.sea.weather.utils.ZipUtils" %>
<%@ page language="java" import="com.sea.weather.utils.GZipUtil" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
</head>
<body>
<%
	PrintWriter outPrint = response.getWriter();
AllDateAction objAllDateAction = new AllDateAction();
String str = objAllDateAction.getAllDateVO();
str = GZipUtil.gzip(str);
outPrint.print(str);
outPrint.close();
%>
</body>
</html>