<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page import="com.sea.weather.date.nmc.action.TideLoadAction"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" import="java.io.PrintWriter" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>获取基础数据</title>
</head>
<body>
<%
String selectDate = request.getParameter("selectDate");
PrintWriter outPrint = response.getWriter();
if(selectDate!=null&&!"".equals(selectDate)){
TideLoadAction objTideLoadAction = new TideLoadAction();
List<String> forecastTime = new ArrayList<String>();
forecastTime.add(selectDate);
objTideLoadAction.loadDate(forecastTime);
outPrint.print("成功执行");
outPrint.close();
}else{
	outPrint.print("请输入选择时间");
	outPrint.close();	
}
%>
</body>
</html>