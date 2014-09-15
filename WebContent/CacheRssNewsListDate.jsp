<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page import="com.sea.weather.utils.CacheDate"%>
<%@page import="com.sea.weather.utils.Cache"%>
<%@page import="com.sea.weather.utils.Cachekey"%>
<%@page import="com.sea.weather.utils.SeaConstant"%>
<%@page import="com.sea.weather.utils.StringUtils"%>
<%@ page language="java" import="java.io.PrintWriter" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>测试应用</title>
</head>
<body>
<%
	String strVersionCode = (String)request.getParameter("versionCode");
	int versionCode = 0;
	if (strVersionCode != null) {
		versionCode = Integer.parseInt(strVersionCode);
	}
	PrintWriter outPrint = response.getWriter();
	String verstr = "";
	if (versionCode < SeaConstant.versionCode) {
		verstr = (String) Cache.getValue(Cachekey.rsskey_5);
	}
	if(versionCode == 9){
		verstr = (String) Cache.getValue(Cachekey.rsskey_noNews);
	}
	if (StringUtils.isBlank(verstr)) {
		verstr = CacheDate.getRssNewsList();
	}

	outPrint.print(verstr);
	outPrint.close();
%>
</body>
</html>