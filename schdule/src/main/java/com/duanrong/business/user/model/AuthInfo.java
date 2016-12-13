package com.duanrong.business.user.model;

import java.util.Date;

import base.model.BaseModel;

/**
 * @Description: 手机验证码
 * @Author: 林志明
 * @CreateDate: Sep 10, 2014
 */
public class AuthInfo extends BaseModel {
	private static final long serialVersionUID = -2783170340188435705L;

	private String id;

	// 生成时间
	private Date generationTime;

	// 验证码
	private String authCode;

	// 验证手机号
	private String mobileNumber;

	// 验证状态
	private String status;

	// 失效时间
	private Date deadline;

	// 类型
	private String type;
	
	private int num;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public Date getGenerationTime() {
		return generationTime;
	}

	public void setGenerationTime(Date generationTime) {
		this.generationTime = generationTime;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "PhoneAuth [id=" + id + ", generationTime=" + generationTime
				+ ", authCode=" + authCode + ", mobileNumber=" + mobileNumber
				+ ", status=" + status + ", deadline=" + deadline + ", type="
				+ type + "]";
	}

}
