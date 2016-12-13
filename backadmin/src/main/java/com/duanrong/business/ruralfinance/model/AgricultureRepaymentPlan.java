package com.duanrong.business.ruralfinance.model;

import java.util.Date;

/**
 * 还款计划表
 * @author bj
 *
 */
public class AgricultureRepaymentPlan {
	//uuid 主键
	private String id;
	//借款数据id
	private String loanDataId;
	//还款日期（应还日期）
	private Date repayDate;
	//实际还款日期
	private Date operationTime;
	//每月应还金额
	private double monthMoney;
	//应还本金
	private double corpus;
	//应还利息
	private double intereat;
	//逾期罚息
	private double latePenalty;
	//每月实际还金额
	private double realrepayMoney;
	//期数
	private int period;
	private String uid;
	private String name;
    private String contractId;
	//总金额
	private double money;
	//明细流水号
	private String detailsNumber;
	//是否提前还款
	private int earlyRepayment;
	//服务费
	private double serviceFee;
	//剩余本金
	private double remainingPrincipal;	
	
	//退还的服务费
	private double returnMoney;
	/**
	 * 借款信息的综合利率
	 */
	private double  compositeInteresRate;
	
	//借款期限(月)
	private int loanTerm;
	//利率
	private double rate;
	//农贷类型
	private String agriculturalType;
	/**
	 * 是否提前还款,默认否0，1是
	 */
	private int whetherEarlyRepayment;
	//还款方式(等额本息)
	private String repayType;
	//给借款人的打款金额
	private double loanMoney;
	//公司收取的服务费用
	private double serviceMoney;
	//减免本金
	private double remitCorpus;
	//减免利息
	private double remitIntereat;
	public double getRemitCorpus() {
		return remitCorpus;
	}
	public void setRemitCorpus(double remitCorpus) {
		this.remitCorpus = remitCorpus;
	}
	public double getRemitIntereat() {
		return remitIntereat;
	}
	public void setRemitIntereat(double remitIntereat) {
		this.remitIntereat = remitIntereat;
	}
	/**
	 * @return the loanMoney
	 */
	public double getLoanMoney() {
		return loanMoney;
	}
	/**
	 * @param loanMoney the loanMoney to set
	 */
	public void setLoanMoney(double loanMoney) {
		this.loanMoney = loanMoney;
	}
	/**
	 * @return the serviceMoney
	 */
	public double getServiceMoney() {
		return serviceMoney;
	}
	/**
	 * @param serviceMoney the serviceMoney to set
	 */
	public void setServiceMoney(double serviceMoney) {
		this.serviceMoney = serviceMoney;
	}
	public String getAgriculturalType() {
		return agriculturalType;
	}
	public void setAgriculturalType(String agriculturalType) {
		this.agriculturalType = agriculturalType;
	}
	/**
	 * @return the whetherEarlyRepayment
	 */
	public int getWhetherEarlyRepayment() {
		return whetherEarlyRepayment;
	}
	/**
	 * @param whetherEarlyRepayment the whetherEarlyRepayment to set
	 */
	public void setWhetherEarlyRepayment(int whetherEarlyRepayment) {
		this.whetherEarlyRepayment = whetherEarlyRepayment;
	}
	/**
	 * @return the repayType
	 */
	public String getRepayType() {
		return repayType;
	}
	/**
	 * @param repayType the repayType to set
	 */
	public void setRepayType(String repayType) {
		this.repayType = repayType;
	}
	/**
	 * @return the compositeInteresRate
	 */
	public double getCompositeInteresRate() {
		return compositeInteresRate;
	}
	/**
	 * @param compositeInteresRate the compositeInteresRate to set
	 */
	public void setCompositeInteresRate(double compositeInteresRate) {
		this.compositeInteresRate = compositeInteresRate;
	}
	/**
	 * @return the loanTerm
	 */
	public int getLoanTerm() {
		return loanTerm;
	}
	/**
	 * @param loanTerm the loanTerm to set
	 */
	public void setLoanTerm(int loanTerm) {
		this.loanTerm = loanTerm;
	}
	/**
	 * @return the rate
	 */
	public double getRate() {
		return rate;
	}
	/**
	 * @param rate the rate to set
	 */
	public void setRate(double rate) {
		this.rate = rate;
	}
	/**
	 * @return the contractId
	 */
	public String getContractId() {
		return contractId;
	}
	/**
	 * @param contractId the contractId to set
	 */
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	/**
	 * @return the detailsNumber
	 */
	public String getDetailsNumber() {
		return detailsNumber;
	}
	/**
	 * @param detailsNumber the detailsNumber to set
	 */
	public void setDetailsNumber(String detailsNumber) {
		this.detailsNumber = detailsNumber;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 0未还 unrepay
		1未还清 repaying
		2还清 finish
	 */
	private String repayStatus;
	// 备注
	private String remark;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLoanDataId() {
		return loanDataId;
	}
	public void setLoanDataId(String loanDataId) {
		this.loanDataId = loanDataId;
	}
	public Date getRepayDate() {
		return repayDate;
	}
	public void setRepayDate(Date repayDate) {
		this.repayDate = repayDate;
	}
	public Date getOperationTime() {
		return operationTime;
	}
	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}
	public double getMonthMoney() {
		return monthMoney;
	}
	public void setMonthMoney(double monthMoney) {
		this.monthMoney = monthMoney;
	}
	public double getCorpus() {
		return corpus;
	}
	public void setCorpus(double corpus) {
		this.corpus = corpus;
	}
	public double getIntereat() {
		return intereat;
	}
	public void setIntereat(double intereat) {
		this.intereat = intereat;
	}
	public double getLatePenalty() {
		return latePenalty;
	}
	public void setLatePenalty(double latePenalty) {
		this.latePenalty = latePenalty;
	}
	public double getRealrepayMoney() {
		return realrepayMoney;
	}
	public void setRealrepayMoney(double realrepayMoney) {
		this.realrepayMoney = realrepayMoney;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public String getRepayStatus() {
		return repayStatus;
	}
	public void setRepayStatus(String repayStatus) {
		this.repayStatus = repayStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return the earlyRepayment
	 */
	public int getEarlyRepayment() {
		return earlyRepayment;
	}
	/**
	 * @param earlyRepayment the earlyRepayment to set
	 */
	public void setEarlyRepayment(int earlyRepayment) {
		this.earlyRepayment = earlyRepayment;
	}
	/**
	 * @return the serviceFee
	 */
	public double getServiceFee() {
		return serviceFee;
	}
	/**
	 * @param serviceFee the serviceFee to set
	 */
	public void setServiceFee(double serviceFee) {
		this.serviceFee = serviceFee;
	}
	/**
	 * @return the remainingPrincipal
	 */
	public double getRemainingPrincipal() {
		return remainingPrincipal;
	}
	/**
	 * @param remainingPrincipal the remainingPrincipal to set
	 */
	public void setRemainingPrincipal(double remainingPrincipal) {
		this.remainingPrincipal = remainingPrincipal;
	}
	/**
	 * @return the returnMoney
	 */
	public double getReturnMoney() {
		return returnMoney;
	}
	/**
	 * @param returnMoney the returnMoney to set
	 */
	public void setReturnMoney(double returnMoney) {
		this.returnMoney = returnMoney;
	}

}
