<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getServerName() + ":" + request.getServerPort() + path + "/";
	String baseUrlPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>用户B</title>
		<script src="${pageContext.request.contextPath}/resources/demo/js/jquery2.1.4.min.js"></script>
		<script src="${pageContext.request.contextPath}/resources/demo/js/sockjs.min.js"></script>
	</head>

	<body>
		用户B
		<input type="text" id="msg" /><button id="btn">点击发消息</button>
		<button id="close">关闭连接</button>
	</body>

	<script type="text/javascript">
		var path = '<%=basePath%>';
		var websocket;
		//不同浏览器的WebSocket对象类型不同
		//alert("ws://" + path + "/ws?uid="+uid);
		if('WebSocket' in window) {
			websocket = new WebSocket("ws://" + path + "demo");
			console.log("=============WebSocket：" + websocket);
			//火狐
		} else if('MozWebSocket' in window) {
			websocket = new MozWebSocket("ws://" + path + "demo");
			console.log("=============MozWebSocket：" + websocket);
		} else {
			websocket = new SockJS("http://" + path + "demo/sockjs");
			console.log("=============SockJS：" + websocket);
		}

		console.log("ws://" + path + "ws");

		//打开Socket,
		websocket.onopen = function(event) {
			console.log("WebSocket:已连接");
		}

		// 监听消息
		//onmessage事件提供了一个data属性，它可以包含消息的Body部分。消息的Body部分必须是一个字符串，可以进行序列化/反序列化操作，以便传递更多的数据。
		websocket.onmessage = function(event) {
			console.log("event:" + event);
			alert("收到消息:" + event.data);
		}

		// 监听WebSocket的关闭
		websocket.onclose = function(event) {
			console.log("WebSocket:已关闭：Client notified socket has closed", event);
		};

		//监听异常
		websocket.onerror = function(event) {

			console.log("WebSocket:发生错误 ", event);
		};

		//发送消息
		function sendMsg() {
			//对象为空了
			if(websocket == undefined || websocket == null) {
				//alert('WebSocket connection not established, please connect.');
				alert('您的连接已经丢失，请退出聊天重新进入');
				return;
			}
			//获取用户要发送的消息内容
			var msg = $("#msg").val();
			if(msg == "") {
				return;
			} else {
				var data = {};
				data["id"] = "userB";
				data["text"] = msg;
				//发送消息
				websocket.send(JSON.stringify(data));
				//发送完消息，清空输入框
				$("#msg").val("");
			}
		}

		//关闭Websocket连接
		function closeWebsocket() {
			if(websocket != null) {
				websocket.close();
				websocket = null;
			}

		}

		$("#btn").click(function() {
			sendMsg();
		});

		$("#close").click(function() {
			closeWebsocket();
		});
	</script>

</html>