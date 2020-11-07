package com.xieh.websocket.web.socket;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.objenesis.instantiator.basic.NewInstanceInstantiator;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.alibaba.fastjson.JSON;
import com.xieh.websocket.bean.DemoMessage;
import com.xieh.websocket.utils.GsonUtils;

@Component
public class DemoWebSocketHandler implements WebSocketHandler {

	// 保存用户链接
	private static ConcurrentHashMap<String, WebSocketSession> users = new ConcurrentHashMap<String, WebSocketSession>();

	// 连接 就绪时
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("建立连接成功---sessionID：" + session.getId() + "，session信息：" + session);
		users.put(session.getId(), session);
		System.out.println("\n");

	}

	// 处理信息
	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		System.out.println("开始发送消息......");
		// 空消息不处理
		if (message.getPayloadLength() == 0)
			return;
		// 消息转换
		DemoMessage demoMessage = JSON.parseObject(message.getPayload().toString(), DemoMessage.class);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
		demoMessage.setDate(sdf.format(new Date()));
		// 发给所有人,不包含自己
		sendMessageToAllExclusionSelf(session.getId(), new TextMessage(JSON.toJSONString(demoMessage)));
		System.out.println("发送消息成功！内容是：" + JSON.toJSONString(demoMessage));
		System.out.println("\n");

	}

	// 处理传输时异常
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		System.out.println("Websocket异常断开:" + session.getId() + "已经关闭");
		// 一旦发生异常，强制用户下线，关闭session
		if (session.isOpen()) {
			session.close();
		}
		// 移除连接
		users.remove(session.getId());
		DemoMessage demoMessage = new DemoMessage();
		demoMessage.setId("error");
		demoMessage.setText("Websocket异常断开:" + session.getId() + "已经关闭");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
		demoMessage.setDate(sdf.format(new Date()));
		// 发给所有人,不包含自己
		sendMessageToAllExclusionSelf(session.getId(), new TextMessage(JSON.toJSONString(demoMessage)));
		System.out.println("\n");

	}

	// 关闭 连接时
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		System.out.println("Websocket正常断开:" + session.getId() + "已经关闭");
		// 移除连接
		users.remove(session.getId());
		System.out.println("\n");

	}

	// 是否支持分包
	@Override
	public boolean supportsPartialMessages() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 默认转发给所有人，可以接受消息，根据id来判断时候来接受消息
	 * 
	 * @param message
	 */
	private void sendMessageToAll(final TextMessage message) {
		Set<Entry<String, WebSocketSession>> entrySet = users.entrySet();
		for (Entry<String, WebSocketSession> entry : entrySet) {
			// 某用户的WebSocketSession
			final WebSocketSession webSocketSession = entry.getValue();
			// 判断连接是否仍然打开的
			if (webSocketSession.isOpen()) {
				// 开启多线程发送消息（效率高）
				new Thread(new Runnable() {
					public void run() {
						try {
							if (webSocketSession.isOpen()) {
								webSocketSession.sendMessage(message);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

				}).start();

			}
		}

	}

	/**
	 * 默认转发给所有人，可以接受消息，根据id来判断时候来接受消息，排除自己
	 * 
	 * @param message
	 */
	private void sendMessageToAllExclusionSelf(String id, final TextMessage message) {
		Set<Entry<String, WebSocketSession>> entrySet = users.entrySet();
		for (Entry<String, WebSocketSession> entry : entrySet) {
			// 某用户的WebSocketSession
			final WebSocketSession webSocketSession = entry.getValue();
			// 自己不接受此消息
			if (!webSocketSession.getId().equalsIgnoreCase(id)) {

				// 判断连接是否仍然打开的
				if (webSocketSession.isOpen()) {
					// 开启多线程发送消息（效率高）
					new Thread(new Runnable() {
						public void run() {
							try {
								if (webSocketSession.isOpen()) {
									webSocketSession.sendMessage(message);
								}
							} catch (IOException e) {
								e.printStackTrace();
							}
						}

					}).start();

				}

			}

		}

	}

	/**
	 * 发送消息给某个人
	 * 
	 * @param id      对方的id
	 * @param message
	 */
	private void sendMessageToUser(String id, TextMessage message) {
		// 获取到要接收消息的用户的session
		WebSocketSession webSocketSession = users.get(id);
		if (webSocketSession != null && webSocketSession.isOpen()) {
			// 发送消息
			try {
				webSocketSession.sendMessage(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
