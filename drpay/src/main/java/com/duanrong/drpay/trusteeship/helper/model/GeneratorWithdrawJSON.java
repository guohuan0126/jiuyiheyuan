package com.duanrong.drpay.trusteeship.helper.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import util.ArithUtil;

public class GeneratorWithdrawJSON extends GeneratorJSON {

	private static DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
	
	//平台佣金
	private Double commission;
	//目前仅支持正常提现
	private WithdrawType withdrawType;
	//提现类型，IMMEDIATE 为直接提现，CONFIRMED 为待确认提现，默认为直接提现方式
	private WithdrawForm withdrawForm;
	//超过此时间即页面过期
	private String expired = format.format(new Date(new Date().getTime() + 600000) );
	
	//存管通响应参数
	private String bankcardNo;//提现银行卡号显示后四位
	private String bankcode;//银行编码
	private String status;//提现状态
	
	//主动异步通知参数
	private String createTime;//交易发起时间
	private String transactionTime;//交易完成时间 
	private String remitTime;//出款时间
	private String completedTime;//到账时间
	
	
	public Double getCommission() {
		return commission != null ? ArithUtil.round(commission, 2) : null;
	}
	public void setCommission(Double commission) {
		this.commission = commission;
	}
	public WithdrawType getWithdrawType() {
		return withdrawType;
	}
	public void setWithdrawType(WithdrawType withdrawType) {
		this.withdrawType = withdrawType;
	}
	public WithdrawForm getWithdrawForm() {
		return withdrawForm;
	}
	public void setWithdrawForm(WithdrawForm withdrawForm) {
		this.withdrawForm = withdrawForm;
	}
	public String getExpired() {
		return expired;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	public String getBankcardNo() {
		return bankcardNo;
	}
	public void setBankcardNo(String bankcardNo) {
		this.bankcardNo = bankcardNo;
	}
	public String getBankcode() {
		return bankcode;
	}
	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getTransactionTime() {
		return transactionTime;
	}
	public void setTransactionTime(String transactionTime) {
		this.transactionTime = transactionTime;
	}
	public String getRemitTime() {
		return remitTime;
	}
	public void setRemitTime(String remitTime) {
		this.remitTime = remitTime;
	}
	public String getCompletedTime() {
		return completedTime;
	}
	public void setCompletedTime(String completedTime) {
		this.completedTime = completedTime;
	}
	
	
}
