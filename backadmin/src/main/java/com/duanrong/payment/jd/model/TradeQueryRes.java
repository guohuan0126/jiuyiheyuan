package com.duanrong.payment.jd.model;



public class TradeQueryRes {
	private String tradeNum;
	private String tradeAmount;
	private String tradeCurrency;
	private String tradeDate;
	private String tradeTime;
	private String tradeNote;
	private String tradeStatus;
	private String token;
	public String getTradeNum() {
		return tradeNum;
	}
	public void setTradeNum(String tradeNum) {
		this.tradeNum = tradeNum;
	}
	public String getTradeAmount() {
		return tradeAmount;
	}
	public void setTradeAmount(String tradeAmount) {
		this.tradeAmount = tradeAmount;
	}
	public String getTradeCurrency() {
		return tradeCurrency;
	}
	public void setTradeCurrency(String tradeCurrency) {
		this.tradeCurrency = tradeCurrency;
	}
	public String getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}
	public String getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}
	public String getTradeNote() {
		return tradeNote;
	}
	public void setTradeNote(String tradeNote) {
		this.tradeNote = tradeNote;
	}
	public String getTradeStatus() {
		return tradeStatus;
	}
	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	@Override
	public String toString() {
		return "TradeQueryRes [tradeNum=" + tradeNum + ", tradeAmount="
				+ tradeAmount + ", tradeCurrency=" + tradeCurrency
				+ ", tradeDate=" + tradeDate + ", tradeTime=" + tradeTime
				+ ", tradeNote=" + tradeNote + ", tradeStatus=" + tradeStatus
				+ ", token=" + token + "]";
	}
	

}
