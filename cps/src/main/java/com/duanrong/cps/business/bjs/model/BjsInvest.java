package com.duanrong.cps.business.bjs.model;

public class BjsInvest {

	private String p2pInvestmentId; //合作平台投资记录唯一标志 主键
	private Double encourageRate; //奖励利率（加息利率），％前的数字，没有传0即可。
	private Double manageRate; //平台管理费率，％前的数字，没有传0即可
	private String productId;  //合作平台产品标识id 主键
	private String period; //投资周期，如30天、6月；以p2p平台自身的编码或描述为准即可。
	private String btApr;  //年化收益，如:“8%”或“8%到10%”都可以。
	private String bearingDate; //起息日，设置起息日期 ，格式：yyyy/mm/dd；T+N: 标满付息
	private Double money; //投资金额
	private Double estimateIncome; //改笔投资能带来的收益（不含本金）
	private String productName; // 	p2p产品名称
	private String state; //投资状态 如：失败、回款中、结束等，以p2p平台自身的编码或描述为准即可。
	private String purchases; //购买时间，精确到毫秒级的时间戳,如：1448268372534
	private String remark; // 	备注
	private  String channel ; //渠道，八金社值为（bjs）,非bjs的导流交易，此字段传NULL即可
	public String getP2pInvestmentId() {
		return p2pInvestmentId;
	}
	public void setP2pInvestmentId(String p2pInvestmentId) {
		this.p2pInvestmentId = p2pInvestmentId;
	}
	public Double getEncourageRate() {
		return encourageRate;
	}
	public void setEncourageRate(Double encourageRate) {
		this.encourageRate = encourageRate;
	}
	public Double getManageRate() {
		return manageRate;
	}
	public void setManageRate(Double manageRate) {
		this.manageRate = manageRate;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getBtApr() {
		return btApr;
	}
	public void setBtApr(String btApr) {
		this.btApr = btApr;
	}
	public String getBearingDate() {
		return bearingDate;
	}
	public void setBearingDate(String bearingDate) {
		this.bearingDate = bearingDate;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public Double getEstimateIncome() {
		return estimateIncome;
	}
	public void setEstimateIncome(Double estimateIncome) {
		this.estimateIncome = estimateIncome;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPurchases() {
		return purchases;
	}
	public void setPurchases(String purchases) {
		this.purchases = purchases;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	
}
