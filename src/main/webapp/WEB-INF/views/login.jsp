<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>在线留言系统</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/lbt.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/style.css"
	type="text/css" media="all" />
<script type="text/javascript">
	$(function(){
		
	});

</script>

</head>
<body>
	<!--顶部开始-->
	<div class="top">
		<div class="inTop"></div>
	</div>
	<!--顶部end-->
	<!--头部开始-->
	<div class="header">
		<h1>聊天室系统</h1>
	</div>
	<!--头部end-->
	<!--登陆区域开始-->
	<div class="loginMain">
		<div class="con">
			<div class="inCon">
				<ul class="imgList">
					<li><img
						src="${pageContext.request.contextPath}/resources/images/1.png"
						width="680" height="494" /></li>
					<li><img
						src="${pageContext.request.contextPath}/resources/images/2.png"
						width="680" height="494" /></li>
					<li><img
						src="${pageContext.request.contextPath}/resources/images/3.png"
						width="680" height="494" /></li>
					<li><img
						src="${pageContext.request.contextPath}/resources/images/4.png"
						width="680" height="494" /></li>
					<li><img
						src="${pageContext.request.contextPath}/resources/images/5.png"
						width="680" height="494" /></li>
					<li><img
						src="${pageContext.request.contextPath}/resources/images/6.png"
						width="680" height="494" /></li>
					<li><img
						src="${pageContext.request.contextPath}/resources/images/7.png"
						width="680" height="494" /></li>
				</ul>
			</div>
			<ol class="btnList">
				<li class="current"></li>
				<li></li>
				<li></li>
				<li></li>
				<li></li>
				<li></li>
				<li></li>
			</ol>
			<a href="javascript:;" class="left"></a> <a href="javascript:;"
				class="right"></a>
		</div>


		<div class="loginArea">
			<h2>欢迎登陆</h2>
			<p>--</p>
			<div>
				<font color="red" size="16">${requestScope.errorTips }</font>
			</div>
			<form action="${pageContext.request.contextPath }/chat/login"
				method="post">
				<input type="text" value="请输入您想显示的昵称" name="nickname" id="myText" />
				<button>进入聊天室</button>
			</form>
		</div>
	</div>
	<!--登陆区域结束-->

	<div class="footer">
		<p>----------</p>
	</div>
</body>
<script type="text/javascript">
	var myText=document.getElementById('myText');
	myText.onfocus=function(){
		if(myText.value=='请输入您想显示的昵称'){
			myText.value='';
			myText.style.color='#333';	
		}	
	}
	myText.onblur=function(){
		if(myText.value==''){
			myText.value='请输入您想显示的昵称';
			myText.style.color='#ccc';
		}	
	}

</script>
</html>