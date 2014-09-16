<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page import="com.sea.weather.utils.Cache"%>
<%@page import="com.sea.weather.utils.Cachekey"%>
<%@page import="com.sea.weather.date.model.SquareVO"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>测试应用</title>
</head>
<body>
<%
request.setCharacterEncoding("utf-8");
String key = (String)request.getParameter("key");
if("danjiachuan".equals(key)){
	String squareTitle = (String)request.getParameter("title");
	String squareUrl = (String)request.getParameter("url");
	SquareVO objSquareVO = new SquareVO();
	objSquareVO.setSquareTitle(squareTitle);
	objSquareVO.setSquareUrl(squareUrl);
	Cache.putValue(Cachekey.square, objSquareVO);
}else if("clean".equals(key)){
	SquareVO objSquareVO = new SquareVO();
	objSquareVO.setSquareTitle("点击访问网页版");
	objSquareVO.setSquareUrl("http://seasea.duapp.com/");
	Cache.putValue(Cachekey.square, objSquareVO);
}
%>
<input id="key" type="password"  value=""><br>
<input id="title" type="text"  value=""><br>
<input id="url" type="text"  value=""><br>
<button onclick="commit()">提交</button>
<script type="text/javascript">
function post(URL, PARAMS) {        
    var temp = document.createElement("form");        
    temp.action = URL;        
    temp.method = "post";        
    temp.style.display = "none";        
    for (var x in PARAMS) {        
        var opt = document.createElement("textarea");        
        opt.name = x;        
        opt.value = PARAMS[x];        
        // alert(opt.name)        
        temp.appendChild(opt);        
    }        
    document.body.appendChild(temp);        
    temp.submit();        
    return temp;        
}        
	function commit(){
		//调用方法 如        
		post('./SquareUpdate.jsp', {key :document.getElementById("key").value,title:document.getElementById("title").value,url:document.getElementById("url").value}); 
	}
</script>
</body>
</html>