package com.duanrong.drpay.business.account.model;

import java.util.Date;

public class BankCard {

	private String id;
	private String userId;
	// 银行中文名称
	private String name;
	// 银行名称
	private String bank;
	// 银行卡号
	private String cardNo;
	// 绑定时间
	private Date time;
	// 银行卡状态
	private String status;
	// 银行卡类型
	private String bankCardType;
	// 是否删除银行卡(对应数据库中bank_city字段)
	private String deleteBankCard;
	// 取消绑定银行卡时间(对应数据库中bank_no字段,因为数据库该字段为varchar类型,所以该时间设置成string)
	private String cancelBandDingTime;
	// 取消绑定状态(对应数据库中bank_province字段)
	private String cancelStatus;
	// 存储返回的XML
	private String accountName;
	//表示用户是否已开通快捷支付。NORMAL 表示未升级，UPGRADE 表示已升级
	private String paySwift;
	
	private String paymentId;
	
	private String paymentNo;
	
	private String bankMobile;
	
	private String bankImgUrl;
	
	
	public String getBankImgUrl() {
		return bankImgUrl;
	}

	public void setBankImgUrl(String bankImgUrl) {
		this.bankImgUrl = bankImgUrl;
	}

	public String getBankMobile() {
		return bankMobile;
	}

	public void setBankMobile(String bankMobile) {
		this.bankMobile = bankMobile;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBankCardType() {
		return bankCardType;
	}

	public void setBankCardType(String bankCardType) {
		this.bankCardType = bankCardType;
	}

	public String getDeleteBankCard() {
		return deleteBankCard;
	}

	public void setDeleteBankCard(String deleteBankCard) {
		this.deleteBankCard = deleteBankCard;
	}

	public String getCancelBandDingTime() {
		return cancelBandDingTime;
	}

	public void setCancelBandDingTime(String cancelBandDingTime) {
		this.cancelBandDingTime = cancelBandDingTime;
	}

	public String getCancelStatus() {
		return cancelStatus;
	}

	public void setCancelStatus(String cancelStatus) {
		this.cancelStatus = cancelStatus;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getPaySwift() {
		return paySwift;
	}

	public void setPaySwift(String paySwift) {
		this.paySwift = paySwift;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getPaymentNo() {
		return paymentNo;
	}

	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	@Override
	public String toString() {
		return "BankCard [id=" + id + ", userId=" + userId + ", name=" + name
				+ ", bank=" + bank + ", cardNo=" + cardNo + ", time=" + time
				+ ", status=" + status + ", bankCardType=" + bankCardType
				+ ", deleteBankCard=" + deleteBankCard
				+ ", cancelBandDingTime=" + cancelBandDingTime
				+ ", cancelStatus=" + cancelStatus + ", accountName="
				+ accountName + ", paySwift=" + paySwift + ", paymentId="
				+ paymentId + ", paymentNo=" + paymentNo + ", bankMobile="
				+ bankMobile + ", bankImgUrl=" + bankImgUrl + "]";
	}
}
