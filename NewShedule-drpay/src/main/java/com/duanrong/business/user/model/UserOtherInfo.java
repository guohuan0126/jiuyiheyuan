package com.duanrong.business.user.model;

import base.model.BaseModel;

/**
 * @Description: 用户地址、邮编、来源、用户IP等
 * @Author: 林志明
 * @CreateDate: Sep 12, 2014
 */
public class UserOtherInfo extends BaseModel {
	private static final long serialVersionUID = 2420992735395046858L;

	private String id;

	// 邮寄地址
	private String postAddress;

	// 邮政编码
	private String postCode;

	// 用户IP
	private String userIP;

	// 用户来源
	private String userSource;
	//来源
	private String visitSource;
	//是否投过p2p
	private String hasP2p;
	//有新标是否通知
	private String notice;

	public String getVisitSource() {
		return visitSource;
	}

	public void setVisitSource(String visitSource) {
		this.visitSource = visitSource;
	}

	public String getHasP2p() {
		return hasP2p;
	}

	public void setHasP2p(String hasP2p) {
		this.hasP2p = hasP2p;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPostAddress() {
		return postAddress;
	}

	public void setPostAddress(String postAddress) {
		this.postAddress = postAddress;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getUserSource() {
		return userSource;
	}

	public void setUserSource(String userSource) {
		this.userSource = userSource;
	}

	public String getUserIP() {
		return userIP;
	}

	public void setUserIP(String userIP) {
		this.userIP = userIP;
	}

	@Override
	public String toString() {
		return "UserOtherInfo [id=" + id + ", postAddress=" + postAddress
				+ ", postCode=" + postCode + ", userIP=" + userIP
				+ ", userSource=" + userSource + "]";
	}

}