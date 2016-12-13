package com.duanrong.cps.business.bsy.model;

import com.duanrong.cps.business.loan.model.Loan;

public class BsyInvest {
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getLoanName() {
		return loanName;
	}
	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}
	public String getInvestMoney() {
		return investMoney;
	}
	public void setInvestMoney(String investMoney) {
		this.investMoney = investMoney;
	}
	public String getTimeType() {
		return timeType;
	}
	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getInvestTime() {
		return investTime;
	}
	public void setInvestTime(String investTime) {
		this.investTime = investTime;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public String getRepayType() {
		return repayType;
	}
	public void setRepayType(String repayType) {
		this.repayType = repayType;
	}
	public String getRateTime() {
		return rateTime;
	}
	public void setRateTime(String rateTime) {
		this.rateTime = rateTime;
	}
	public String getExpectTime() {
		return expectTime;
	}
	public void setExpectTime(String expectTime) {
		this.expectTime = expectTime;
	}
	public String getBankCard() {
		return bankCard;
	}
	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}
	public String getLoanStatus() {
		return loanStatus;
	}
	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}
	private String id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	//投资id
	private String investId;
	//投资状态
	private String investStatus;
	private String loanId;
	//用户id
	private String userId;
	//身份证号
	private String idCard;
	//项目名称
	private String loanName;
	//投资金额
	private String investMoney;
	//月标还是天标
	private String timeType;
	//天
	private String day;
	//月
	private String month;
	//投资时间
	private String investTime;
	//年化利率
	private double rate;
	//还款方式(收益方式)
	private String repayType;
	//起息日
	private String rateTime;
	//到期日
	private String expectTime;
	//银行卡
	private String bankCard;
	//项目状态
	private String loanStatus;
	// 项目
	private Loan loan;
	private PlatformUser platformUser;
	
	
	public String getInvestId() {
		return investId;
	}
	public void setInvestId(String investId) {
		this.investId = investId;
	}
	public String getInvestStatus() {
		return investStatus;
	}
	public void setInvestStatus(String investStatus) {
		this.investStatus = investStatus;
	}
	public Loan getLoan() {
		return loan;
	}
	public void setLoan(Loan loan) {
		this.loan = loan;
	}
	public PlatformUser getPlatformUser() {
		return platformUser;
	}
	public void setPlatformUser(PlatformUser platformUser) {
		this.platformUser = platformUser;
	}
	
	
}
