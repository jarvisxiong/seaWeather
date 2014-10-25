<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page import="com.sea.weather.date.action.HlAction"%>
<%@ page language="java" import="java.io.PrintWriter" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
</head>
<body>
<%
PrintWriter outPrint = response.getWriter();
HlAction objHlAction = new HlAction();
String str = objHlAction.getHlCache();
outPrint.print(str);
outPrint.close();
%>
</body>
</html>