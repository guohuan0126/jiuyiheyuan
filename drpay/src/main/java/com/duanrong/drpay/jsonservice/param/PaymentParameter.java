package com.duanrong.drpay.jsonservice.param;

import com.duanrong.drpay.business.payment.RechargeWay;
import com.duanrong.drpay.trusteeship.helper.model.ExpectPayCompany;

public class PaymentParameter extends Parameter {

	private static final long serialVersionUID = -300763624991554759L;

	// 平台佣金
	private Double commission;
	// 偏好支付公司，见【支付公司】
	private ExpectPayCompany expectPayCompany;
	// 【支付方式】，支持网银、快捷支付
	private RechargeWay rechargeWay;
	// 【见银行编码】若支付方式为网银，此处非必填，若支付方式为快捷支付，此处必填，如果填写，则直接跳转至银行网银页面，不填则跳转至支付公司收银台页面
	private String bankcode;
	// 【网银类型】，若支付方式为网银，且传入【银行编码】时必填
	private String payType;
	// 【授权交易类型】，若想实现充值投标，则此参数必传，固定“TENDER”
	private String authtradeType;
	// 授权投标金额，充值成功后可操作对应金额范围内的授权投标预处理；若传入了【授权交易类型】，则此参数必传；
	private Double authtenderAmount;
	// 标的号；若传入了【授权交易类型】，则此参数必传。
	private String projectNo;

	public void setExpectPayCompany(ExpectPayCompany expectPayCompany) {
		this.expectPayCompany = expectPayCompany;
	}

	public void setRechargeWay(RechargeWay rechargeWay) {
		this.rechargeWay = rechargeWay;
	}

	public ExpectPayCompany getExpectPayCompany() {
		return expectPayCompany;
	}

	public RechargeWay getRechargeWay() {
		return rechargeWay;
	}

	public Double getCommission() {
		return commission;
	}

	public void setCommission(Double commission) {
		this.commission = commission;
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

	public String getProjectNo() {
		return projectNo;
	}

	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}

}