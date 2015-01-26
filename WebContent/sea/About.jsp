<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page import="com.sea.weather.utils.Cache"%>
<%@page import="com.sea.weather.utils.Cachekey"%>
<%@ page language="java" import="java.io.PrintWriter" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>测试应用</title>
</head>
<body>
<%
	String str = (String)Cache.getValue(Cachekey.aboutkey);
	PrintWriter outPrint = response.getWriter();
	if(str==null || "".equals(str)){
		str ="简介及免责声明:\n        此应用主要用于海上工作人员能方便并且快速的获取海区天气、台风、潮汐等关键天气信息，其数据定时抓取于以下网站，具体如下：\n        1、海区天气预报抓取于中央气象台以及中国天气网；\n        2、台风天气抓取于中央气象台及中国天气台风网；\n        3、环境预报抓取于国家环境预报中心；\n        4、潮汐数据抓取于中国港口网；\n        5、航行通告及航行警告抓取于中华人民共和国共和国海事局；\n        其数据都为定时抓取，给船员，海钓一族等海上工作者提供辅助参考，每次查看必须检查数据发布时间，以保证时效以及正确性，然后再辅助电视、广播或网络形式等获取更多权威数据一并使用，以确保安全，具体的天气情况及通告以政府相关部门预报及通告为准。\n        无论在何种情况下，海洋天气均不对由于信息网络正常的设备维护，信息网络连接故障，电脑、通讯或其他系统故障，黑客、电脑病毒，电力故障，罢工，劳动争议，暴乱，起义，骚乱，生产力或生产资料不足，火灾，洪水，风暴，爆炸，战争，政府行为，司法行政机关的命令，第三方原因等其他海洋天气不能预测或控制的行为而造成的不能服务或延迟服务承担责任。";
		Cache.putValue(Cachekey.aboutkey, str);
	}
	outPrint.print(str);
	outPrint.close();
%>
</body>
</html>