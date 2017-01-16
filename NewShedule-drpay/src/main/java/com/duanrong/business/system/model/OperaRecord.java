package com.duanrong.business.system.model;

import java.util.Date;

import base.model.BaseModel;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : wangjing
 * @CreateTime : 2014-12-23 上午11:02:42
 * @Description : jdp2p com.esoft.jdp2p.loan.babyModel House.java
 * 
 */
public class OperaRecord extends BaseModel {
	private static final long serialVersionUID = -6524264191558392682L;
	private int id;
	private String title;
	private String userid;
	private String content;
	private Date time;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
}
