<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page import="com.sea.weather.date.nmc.action.RssMsaAction"%>
<%@ page language="java" import="java.io.PrintWriter" %>
<%@ page language="java" import="com.sea.weather.utils.GZipUtil" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>测试应用</title>
</head>
<body>
<%
String code = request.getParameter("code");
PrintWriter outPrint = response.getWriter();
RssMsaAction objRssMsaAction = new RssMsaAction();
String str = objRssMsaAction.getTgCache(code);
str = GZipUtil.unGzip(str);
outPrint.print(str);
outPrint.close();
%>
</body>
</html>