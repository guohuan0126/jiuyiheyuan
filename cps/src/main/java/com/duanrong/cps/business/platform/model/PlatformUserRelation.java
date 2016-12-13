package com.duanrong.cps.business.platform.model;

import java.util.Date;

public class PlatformUserRelation {
	
	private String id;
	private String platformUserId;
	private String userId;
	private Integer whetherNew;
	private String userKey;
	private Date createTime;   //与第三方平台绑定时间
	private String type;
	private Date registerTime;  //注册时间
	private String uid;
	private String whetherBinding;
	private String username;
	private String mobile;
	private String userSource;
	private String realName;
	
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUserSource() {
		return userSource;
	}
	public void setUserSource(String userSource) {
		this.userSource = userSource;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPlatformUserId() {
		return platformUserId;
	}
	public void setPlatformUserId(String platformUserId) {
		this.platformUserId = platformUserId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getWhetherNew() {
		return whetherNew;
	}
	public void setWhetherNew(Integer whetherNew) {
		this.whetherNew = whetherNew;
	}
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getWhetherBinding() {
		return whetherBinding;
	}
	public void setWhetherBinding(String whetherBinding) {
		this.whetherBinding = whetherBinding;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
	

}
