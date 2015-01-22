<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@page import="com.sea.weather.date.action.SeaAdAction"%>
<%@ page language="java" import="java.io.PrintWriter" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>测试应用</title>
</head>
<body>
<%
String belong = request.getParameter("belong");
SeaAdAction objSeaAdAction = new SeaAdAction();
PrintWriter outPrint = response.getWriter();
String str = objSeaAdAction.getSeaAdVO(belong);
outPrint.print(str);
outPrint.close();
%>
</body>
</html>