package com.duanrong.business.trusteeship.model;

import java.util.Date;

import base.model.BaseModel;

/**
 * 与易宝操作的记录数据类
 * 
 * @author Administrator
 * 
 */
public class TrusteeshipOperation extends BaseModel {
	private static final long serialVersionUID = 469801636634056236L;
	
	private String id;
	/**
	 * 操作类型（开户、投标等等）
	 */
	private String type;
	/**
	 * 操作的唯一标识（与回调的唯一标识一致，用于查询某一条操作记录）
	 */
	private String markId;
	/**
	 * 操作者（如果是开户，此字段为userId；如果为充值，此字段为rechargeId）
	 */
	private String operator;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 请求时间
	 */
	private Date requestTime;
	/**
	 * 请求地址
	 */
	private String requestUrl;
	private String requestData;
	/**
	 * 返回时间
	 */
	private Date responseTime;
	private String responseData;
	/**
	 * 托管方
	 */
	private String trusteeship;

	// 操作人ID
	private String userId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMarkId() {
		return markId;
	}

	public void setMarkId(String markId) {
		this.markId = markId;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String getRequestData() {
		return requestData;
	}

	public void setRequestData(String requestData) {
		this.requestData = requestData;
	}

	public Date getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(Date responseTime) {
		this.responseTime = responseTime;
	}

	public String getResponseData() {
		return responseData;
	}

	public void setResponseData(String responseData) {
		this.responseData = responseData;
	}

	public String getTrusteeship() {
		return trusteeship;
	}

	public void setTrusteeship(String trusteeship) {
		this.trusteeship = trusteeship;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
