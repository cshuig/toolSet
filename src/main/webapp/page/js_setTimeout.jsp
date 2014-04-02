<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
					  "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>随机定时执行功能</title>
</head>
<script type="text/javascript">
	var randomNum = 0;
	
	function fuck(){
		//随机返回5-10秒
		randomNum = parseInt(parseInt(Math.random()*(11-5)+5) + "000");
		//根据随机数，设置定时器，第一个参数必须这样写，才能兼容所有的浏览器
		setTimeout(function(){randomTimeExecute()},randomNum);
	}
	function randomTimeExecute(){
		var contentId = document.getElementById("content");
		contentId.innerHTML = contentId.innerHTML + randomNum + "<br/>";
		
		//重新计算随机数，并设置定时器
		//随机返回5-10秒
		randomNum = parseInt(parseInt(Math.random()*(11-5)+5) + "000");
		//根据随机数，设置定时器,第一个参数必须要这样写，才能兼容所有浏览器
		setTimeout(function(){randomTimeExcuete()},randomNum)
	}
</script>
<body onload="fuck();">
	<h1>随机定时执行功能</h1>
	<div id="content"></div>
</body>
</html>