package com.duanrong.drpay.trusteeship.helper.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import util.ArithUtil;

public class GeneratorRechargeJSON extends GeneratorJSON {

	private static DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
	
	//平台佣金
	private Double commission;
	//偏好支付公司，见【支付公司】
	private ExpectPayCompany expectPayCompany;
	//【支付方式】，支持网银、快捷支付
	private RechargeWay rechargeWay;
	//【见银行编码】若支付方式为网银，此处非必填，若支付方式为快捷支付，此处必填，如果填写，则直接跳转至银行网银页面，不填则跳转至支付公司收银台页面
	private String bankcode;
	//【网银类型】，若支付方式为网银，且传入【银行编码】时必填
	private String payType;
	//【授权交易类型】，若想实现充值投标，则此参数必传，固定“TENDER”
	private String authtradeType;
	//授权投标金额，充值成功后可操作对应金额范围内的授权投标预处理；若传入了【授权交易类型】，则此参数必传；
	private Double authtenderAmount;
	//超过此时间即页面过期
	private String expired = format.format(new Date(new Date().getTime() + 600000) );
	// 快捷充值回调模式，如传入DIRECT_CALLBACK，则订单支付不论成功、失败、处理中均直接同步、异步通知商户；未传入订单仅在支付成功时通知商户；
	private String callbackMode;
	//原提现流水号，回退充值用
	private String WithdrawRequestNo;
	//存管通响应参数
//	private Double amount;//充值金额
//	private Double commission;//平台佣金
	private String payCompany;//实际充值支付公司，见【支付公司】
//	private String rechargeWay;//【支付方式】
//	private String bankcode;//【银行编码】网银不返回银行编码
	private String payMobile;//本次充值手机号，网银为空
	private String transactionTime;//交易完成时间
	//status 订单状态，SUCCESS 表示支付成功， FAIL 表示支付失败，PENDDING 表示支付中
	private String errorCode;//【存管错误码】
	private String errorMessage;//【存管错误描述】
	private String channelErrorCode;//【支付通道错误码】
	private String channelErrorMessage;//【支付通道返回错误消息】
	
	public String getPayCompany() {
		return payCompany;
	}
	public void setPayCompany(String payCompany) {
		this.payCompany = payCompany;
	}
	public String getPayMobile() {
		return payMobile;
	}
	public void setPayMobile(String payMobile) {
		this.payMobile = payMobile;
	}
	public String getTransactionTime() {
		return transactionTime;
	}
	public void setTransactionTime(String transactionTime) {
		this.transactionTime = transactionTime;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getChannelErrorCode() {
		return channelErrorCode;
	}
	public void setChannelErrorCode(String channelErrorCode) {
		this.channelErrorCode = channelErrorCode;
	}
	public String getChannelErrorMessage() {
		return channelErrorMessage;
	}
	public void setChannelErrorMessage(String channelErrorMessage) {
		this.channelErrorMessage = channelErrorMessage;
	}
	
	public Double getCommission() {
		return commission != null ? ArithUtil.round(commission, 2) : null;
	}
	public void setCommission(Double commission) {
		this.commission = commission;
	}
	public ExpectPayCompany getExpectPayCompany() {
		return expectPayCompany;
	}
	public void setExpectPayCompany(ExpectPayCompany expectPayCompany) {
		this.expectPayCompany = expectPayCompany;
	}
	public RechargeWay getRechargeWay() {
		return rechargeWay;
	}
	public void setRechargeWay(RechargeWay rechargeWay) {
		this.rechargeWay = rechargeWay;
	}
	public String getBankcode() {
		return bankcode;
	}
	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getAuthtradeType() {
		return authtradeType;
	}
	public void setAuthtradeType(String authtradeType) {
		this.authtradeType = authtradeType;
	}
	public Double getAuthtenderAmount() {
		return authtenderAmount;
	}
	public void setAuthtenderAmount(Double authtenderAmount) {
		this.authtenderAmount = authtenderAmount;
	}
	public String getExpired() {
		return expired;
	}
	public String getCallbackMode() {
		return callbackMode;
	}
	public String getWithdrawRequestNo() {
		return WithdrawRequestNo;
	}
	public void setWithdrawRequestNo(String withdrawRequestNo) {
		WithdrawRequestNo = withdrawRequestNo;
	}
	
}
