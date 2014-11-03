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
PrintWriter outPrint = response.getWriter();
TideLoadAction objTideLoadAction = new TideLoadAction();
objTideLoadAction.loadAllDate();
outPrint.print("成功执行");
outPrint.close();
%>
</body>
</html>