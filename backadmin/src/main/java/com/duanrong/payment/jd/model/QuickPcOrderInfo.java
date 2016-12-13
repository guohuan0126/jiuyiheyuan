package com.duanrong.payment.jd.model;

import java.io.Serializable;

/**
 * 商户加密用的基本类
 * 
 */
public class QuickPcOrderInfo implements Serializable{
	/**
	 * 交易信息签名
	 */
	protected String merchantSign;
	/**
	 * 用户交易令牌
	 */
	protected String token;
	/**
	 * 版本号
	 */
	protected String version;
	/**
	 * 商户号
	 */
	protected String merchantNum;
	/**
	 * 商户备注
	 */
	protected String merchantRemark;

	/**
	 * 交易流水号--商户提供的唯一交易流水号
	 */
	protected String tradeNum;
	/**
	 * 交易名称
	 */
	protected String tradeName;
	/**
	 * 交易描述
	 */
	protected String tradeDescription;
	/**
	 * 交易时间
	 */
	protected String tradeTime;
	/**
	 * 交易金额：单位是分--商户使用
	 */
	private String tradeAmount;
	/**
	 * 货币种类
	 */
	protected String currency;
	/**
	 * 支付成功跳转路径
	 */
	protected String successCallbackUrl;
	/**
	 * 异步通知页面地址
	 */
	protected String notifyUrl;
	/**
	 * 用户IP
	 */
	protected String ip;
	/**
	 * 指定信息
	 */
	protected Object specifyInfoJson;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	
	public String getMerchantSign() {
		return merchantSign;
	}

	public void setMerchantSign(String merchantSign) {
		this.merchantSign = merchantSign;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMerchantNum() {
		return merchantNum;
	}

	public void setMerchantNum(String merchantNum) {
		this.merchantNum = merchantNum;
	}

	public String getMerchantRemark() {
		return merchantRemark;
	}

	public void setMerchantRemark(String merchantRemark) {
		this.merchantRemark = merchantRemark;
	}

	public String getTradeNum() {
		return tradeNum;
	}

	public void setTradeNum(String tradeNum) {
		this.tradeNum = tradeNum;
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public String getTradeDescription() {
		return tradeDescription;
	}

	public void setTradeDescription(String tradeDescription) {
		this.tradeDescription = tradeDescription;
	}

	public String getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}

	public String getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(String tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	
	public String getSuccessCallbackUrl() {
		return successCallbackUrl;
	}

	public void setSuccessCallbackUrl(String successCallbackUrl) {
		this.successCallbackUrl = successCallbackUrl;
	}

	public Object getSpecifyInfoJson() {
		return specifyInfoJson;
	}

	public void setSpecifyInfoJson(Object specifyInfoJson) {
		this.specifyInfoJson = specifyInfoJson;
	}

	@Override
	public String toString() {
		StringBuffer xml = new StringBuffer();
		xml.append("<form method=\"post\" action=\"https:// plus.jdpay.com/pay.htm\">");
		xml.append("<input type=\"hidden\" name=\"version\" value=\""+this.version+"\"/>");
		xml.append("<input type=\"hidden\" name=\"token\" value=\""+this.token+"\"/>");
		xml.append("<input type=\"hidden\" name=\"merchantNum\" value=\""+this.merchantNum+"\"/>");
		xml.append("<input type=\"hidden\" name=\"merchantRemark\" value=\""+this.merchantRemark+"\"/>");
		xml.append("<input type=\"hidden\" name=\"tradeNum\" value=\""+this.tradeNum+"\"/>");
		xml.append("<input type=\"hidden\" name=\"tradeName\" value=\""+this.tradeName+"\"/>");
		xml.append("<input type=\"hidden\" name=\"tradeDescription\" value=\""+this.tradeDescription+"\"/>");
		xml.append("<input type=\"hidden\" name=\"tradeTime\" value=\""+this.tradeTime+"\"/>");
		xml.append("<input type=\"hidden\" name=\"tradeAmount\" value=\""+this.tradeAmount+"\"/>");
		xml.append("<input type=\"hidden\" name=\"currency\" value=\""+this.currency+"\"/>");
		xml.append("<input type=\"hidden\" name=\"notifyUrl\" value=\""+this.notifyUrl+"\"/>");
		xml.append("<input type=\"hidden\" name=\"successCallbackUrl\" value=\""+this.successCallbackUrl+"\"/>");
		xml.append("<input type=\"hidden\" name=\"ip\" value=\""+this.ip+"\"/>");
		xml.append("<input type=\"hidden\" name=\"merchantSign\" value=\""+this.merchantSign+"\"/>");
		xml.append("<input type=\"hidden\" name=\"specifyInfoJson\" value=\""+this.specifyInfoJson+"\"/>");
		xml.append("</form>");
		return xml.toString();
	}
}
