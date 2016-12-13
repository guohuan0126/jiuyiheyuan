package com.duanrong.cps.business.bjs.model;

public class BjsInterest {
	private String p2pRewardId; // 	合作平台投资收益唯一标志 主键
	private String p2pRewardName;//收益的名称，描述性信息
	private String description; //收益的描述，描述性信息
	private String p2pInvestmentId; //合作平台投资记录唯一标志，与投资相关的收益；如果不与“某一投资记录”相关的所有类型的收益此字段可为空。
	private String calendar; //还款款时间，精确到毫秒级的时间戳,如：1448268372534
	private Double income; //还款利息
	private Double principal; //还款本金
	private String incomeState; //还款状态 如：待收 、已收、逾期等，以p2p平台自身的编码或描述为准即可。
	public String getP2pRewardId() {
		return p2pRewardId;
	}
	public void setP2pRewardId(String p2pRewardId) {
		this.p2pRewardId = p2pRewardId;
	}
	public String getP2pRewardName() {
		return p2pRewardName;
	}
	public void setP2pRewardName(String p2pRewardName) {
		this.p2pRewardName = p2pRewardName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getP2pInvestmentId() {
		return p2pInvestmentId;
	}
	public void setP2pInvestmentId(String p2pInvestmentId) {
		this.p2pInvestmentId = p2pInvestmentId;
	}
	public String getCalendar() {
		return calendar;
	}
	public void setCalendar(String calendar) {
		this.calendar = calendar;
	}
	public Double getIncome() {
		return income;
	}
	public void setIncome(Double income) {
		this.income = income;
	}
	public Double getPrincipal() {
		return principal;
	}
	public void setPrincipal(Double principal) {
		this.principal = principal;
	}
	public String getIncomeState() {
		return incomeState;
	}
	public void setIncomeState(String incomeState) {
		this.incomeState = incomeState;
	}
	
	

}
