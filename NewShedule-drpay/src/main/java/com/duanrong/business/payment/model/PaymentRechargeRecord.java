package com.duanrong.business.payment.model;

import java.util.Date;

import base.model.BaseModel;
/**
 * @Description	第三方支付流水表
 * @author JD
 * @CreateDate	2016年4月26日14:58:51
 *	
 */
public class PaymentRechargeRecord extends BaseModel{

	private static final long serialVersionUID = 3033638669021039555L;
	
	private String id;
	/**
	 * 操作的唯一标识（与回调的唯一标识一致，用于查询某一条操作记录）
	 */
	private String markId;
	/**
	 * 操作者（如果是开户，此字段为userId；如果为充值，此字段为rechargeId）
	 */
	private String operator;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 充值金额
	 */
	private double money;
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
	 * 第三方通道ID
	 */
	private String paymentId;
	/**
	 * 途径
	 */
	private String transferWay;
	
	private String type;
	/**
	 * 状态 
	 * Sended 发起
	 * Passed 成功
	 * Failed 失败
	 * Refused 未完成
	 */
	private String status;
	
	private String isYeepayTransfer;
	
	private String cardNo;
	
	private String cardType;
	
	private String bankCode;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
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
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public String getTransferWay() {
		return transferWay;
	}
	public void setTransferWay(String transferWay) {
		this.transferWay = transferWay;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIsYeepayTransfer() {
		return isYeepayTransfer;
	}
	public void setIsYeepayTransfer(String isYeepayTransfer) {
		this.isYeepayTransfer = isYeepayTransfer;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	@Override
	public String toString() {
		return "PaymentRechargeRecord [id=" + id + ", markId=" + markId
				+ ", operator=" + operator + ", userId=" + userId + ", money="
				+ money + ", requestTime=" + requestTime + ", requestUrl="
				+ requestUrl + ", requestData=" + requestData
				+ ", responseTime=" + responseTime + ", responseData="
				+ responseData + ", paymentId=" + paymentId + ", transferWay="
				+ transferWay + ", type=" + type + ", status=" + status
				+ ", isYeepayTransfer=" + isYeepayTransfer + ", cardNo="
				+ cardNo + ", cardType=" + cardType + ", bankCode=" + bankCode
				+ "]";
	}

}
