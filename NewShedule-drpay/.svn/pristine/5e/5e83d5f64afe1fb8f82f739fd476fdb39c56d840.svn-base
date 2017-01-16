package com.duanrong.business.user.model;

import java.util.Date;

import base.model.BaseModel;

/**
 * @Description: 站内信
 * @Author: 林志明
 * @CreateDate: Sep 4, 2014
 */
public class Information extends BaseModel {
	private static final long serialVersionUID = 583679790051736253L;
	
	private String id;
	private String username;
	private Date time;
	// 默认值为1，显示消息通知
	private byte status;
	private String title;
	private String content;

	private Byte isRead;
	private User user;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Byte getIsRead() {
		return isRead;
	}

	public void setIsRead(Byte isRead) {
		this.isRead = isRead;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Information [id=" + id + ", username=" + username + ", time="
				+ time + ", status=" + status + ", title=" + title
				+ ", content=" + content + ", isRead=" + isRead + "]";
	}

}