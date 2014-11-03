<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page import="com.sea.weather.date.nmc.action.TideLoadAction"%>
<%@ page language="java" import="java.io.PrintWriter" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>获取基础数据</title>
</head>
<body>
<%
String selectDate = request.getParameter("selectDate");
String code = request.getParameter("code");
PrintWriter outPrint = response.getWriter();
TideLoadAction objTideLoadAction = new TideLoadAction();
String str = objTideLoadAction.queryDate(selectDate, code);
outPrint.print(str);
outPrint.close();
%>
</body>
</html>