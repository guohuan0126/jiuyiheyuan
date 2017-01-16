package com.duanrong.drpay.trusteeship.helper.model;

import java.util.Date;

import util.ArithUtil;


/**
 * 存管通请求实体类 所有存管通实体衍生类需继承此类
 * 
 * @author xiao
 * @datetime 2016年12月6日 下午4:49:19
 */
public class GeneratorDetailJSON {

	//放款明细请求流水号
	private String loanRequestNo;
	
	//批量请求流水号
	private String intelRequestNo;
	
	//批量理财计划子标编号
	private String matchProjectNo;
	
	//标的号
	private String projectNo;
	
	//批量计划子标购买份额
	private Double matchShare;
	
	//佣金
	private Double commission;
	
	//预处理业务流水号
	private String preTransactionNo;
	
	//收款人编号
	private String platformUserNo;
	
	//放款金额
	private Double amount;
	
	//用户使用平台营销款金额投标，要求与各类预处理的预备使用的红包金额一致
	private String marketingAmount;
	
	//平台商户自定义参数，平台交易时传入的自定义参数
	private String customDefine;
	
	//利息，包含在还款本息之内
	private Double income;
	//投资佣金，包含在投资人应得金额总和之内
	private Double investCommission;
	
	private String status;
	//还款请求流水号
	private String repaymentRequestNo;

	private Double interest;
	//交易明细订单号
	private String asyncRequestNo;
	//异步通知业务类型
	private BizType bizType;
	//交易发起时间
	private Date createTime;
	//交易完成时间
	private Date transactionTime;
	//返回码
	private String failCode;
	//描述信息
	private String failReason;
	
	public String getLoanRequestNo() {
		return loanRequestNo;
	}

	public void setLoanRequestNo(String loanRequestNo) {
		this.loanRequestNo = loanRequestNo;
	}

	public String getIntelRequestNo() {
		return intelRequestNo;
	}

	public void setIntelRequestNo(String intelRequestNo) {
		this.intelRequestNo = intelRequestNo;
	}

	public String getProjectNo() {
		return projectNo;
	}

	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}

	public Double getCommission() {
		return commission != null ? ArithUtil.round(commission, 2) : null;
	}

	public void setCommission(Double commission) {
		this.commission = commission;
	}

	public String getPreTransactionNo() {
		return preTransactionNo;
	}

	public void setPreTransactionNo(String preTransactionNo) {
		this.preTransactionNo = preTransactionNo;
	}

	public String getPlatformUserNo() {
		return platformUserNo;
	}

	public void setPlatformUserNo(String platformUserNo) {
		this.platformUserNo = platformUserNo;
	}

	
	public String getMatchProjectNo() {
		return matchProjectNo;
	}

	public void setMatchProjectNo(String matchProjectNo) {
		this.matchProjectNo = matchProjectNo;
	}

	public Double getMatchShare() {
		return matchShare != null ? ArithUtil.round(matchShare, 2) : null;
	}

	public void setMatchShare(Double matchShare) {
		this.matchShare = matchShare;
	}

	public Double getAmount() {
		return amount != null ? ArithUtil.round(amount, 2) : null;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getMarketingAmount() {
		return marketingAmount;
	}

	public void setMarketingAmount(String marketingAmount) {
		this.marketingAmount = marketingAmount;
	}

	public String getCustomDefine() {
		return customDefine;
	}

	public void setCustomDefine(String customDefine) {
		this.customDefine = customDefine;
	}

	public Double getIncome() {
		return income != null ? ArithUtil.round(income, 2) : null;
	}

	public void setIncome(Double income) {
		this.income = income;
	}

	public Double getInvestCommission() {		
		return investCommission != null ? ArithUtil.round(investCommission, 2) : null;
	}

	public void setInvestCommission(Double investCommission) {
		this.investCommission = investCommission;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRepaymentRequestNo() {
		return repaymentRequestNo;
	}

	public void setRepaymentRequestNo(String repaymentRequestNo) {
		this.repaymentRequestNo = repaymentRequestNo;
	}

	public Double getInterest() {
		return interest != null ? ArithUtil.round(interest, 2) : null;
	}

	public void setInterest(Double interest) {
		this.interest = interest;
	}

	public String getAsyncRequestNo() {
		return asyncRequestNo;
	}

	public void setAsyncRequestNo(String asyncRequestNo) {
		this.asyncRequestNo = asyncRequestNo;
	}

	public BizType getBizType() {
		return bizType;
	}

	public void setBizType(BizType bizType) {
		this.bizType = bizType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(Date transactionTime) {
		this.transactionTime = transactionTime;
	}

	public String getFailCode() {
		return failCode;
	}

	public void setFailCode(String failCode) {
		this.failCode = failCode;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

}
