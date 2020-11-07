package com.xieh.websocket.web.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Component
//配置开启WebSocket服务用来接收ws请求
@EnableWebSocket
public class DemoWebSocketConfig implements WebSocketConfigurer {

	@Autowired
	private DemoWebSocketHandler demoWebSocketHandler;

	@Autowired
	private DemoWebSocketHandshakeInterceptor demoWebSocketHandshakeInterceptor;

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		// 添加一个处理器还有定义处理器的处理路径
		registry.addHandler(demoWebSocketHandler, "/demo").addInterceptors(demoWebSocketHandshakeInterceptor);

		/*
		 * 在这里我们用到.withSockJS()，SockJS是spring用来处理浏览器对websocket的兼容性，
		 * 目前浏览器支持websocket还不是很好，特别是IE11以下.
		 * SockJS能根据浏览器能否支持websocket来提供三种方式用于websocket请求， 三种方式分别是 WebSocket, HTTP
		 * Streaming以及 HTTP Long Polling
		 */
		registry.addHandler(demoWebSocketHandler, "/demo/sockjs").addInterceptors(demoWebSocketHandshakeInterceptor)
				.withSockJS();

	}

}
