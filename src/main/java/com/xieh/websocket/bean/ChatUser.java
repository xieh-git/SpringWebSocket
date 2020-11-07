package com.xieh.websocket.bean;

import com.google.gson.annotations.Expose;

//用户
public class ChatUser {
	// @SerializedName 注解的作用:定义属性序列化后的名称;
	@Expose // 没有@Expose注释的属性将不会被序列化
	private String id;// 唯一标识属性
	@Expose
	private String nickname;

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + nickname + "]";
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
