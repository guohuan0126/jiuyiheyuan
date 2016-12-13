package base.model;

import java.io.Serializable;
import java.util.Date;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion.User;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2014-8-28 上午11:16:46
 * @Description : drsoa com.duanrong.business.loan.model Loan.java
 * 
 */
public class Loan extends BaseModel implements Serializable {
	private static final long serialVersionUID = -4982339010511415019L;

	// id
	private String id;
	// 项目名称
	private String name;
	// 借款人(用户表id 外键)
	private User borrowMoneyUser;
	// 借款人(用户表id 外键)
	private String borrowMoneyUserID;
	// 抵押物信息
	private String pawn;
	// 项目描述
	private String description;
	// 项目亮点
	private String lightspot;
	// 风向控制措施
	private String riskControlMeasures;
	// 借款合同存储路径
	private String contract;
	// 借款总金额
	private Double totalmoney;
	// 投资起点金额
	private Double investOriginMoney;
	// 单位递增金额
	private Double increaseMoney;
	// 借款利率(不是百分比形式)
	private Double rate;
	// 借款利率的百分比展示形式
	private Double ratePercent;
	// 奖励金额比例(不是百分比利率)
	private Double awardMoneyRate;
	// 奖励金额比例的百分比展示形式
	private Double awardMoneyRatePercent;
	// 奖励对应链接
	private String awardLink;
	// 项目类型
	private String loanType;
	// 借款管理费
	private Double loanGuranteeFee;
	// 计算方式
	private String operationType;
	// 还款方式
	private String repayType;
	// 是否提前还款
	private String beforeRepay;
	// 借款期限(月)
	private Integer deadline;
	// 借款期限(天)
	private Integer day;
	// 预计开始执行时间
	private Date expectTime;
	// 项目状态
	private String status;
	// 是否为测试项目
	private String textItem;
	// 提交时间
	private Date commitTime;
	// 放款时间
	private Date giveMoneyTime;
	// 项目发起人
	private User sponsorUser;
	// 借款合同名称
	private String contractName;

	private String type;

	// 页面投资进度显示(已募集金额)
	private Double calculateMoneyNeedRaised;
	// 页面投资进度显示(圆圈)
	private int investPlan;

	// 最大投资金额
	private Double maxInvestMoney;
	// 预计执行之间格式化
	private String expectTimeFormat;
	// 符号
	private Integer symbol;
	//新手专享标
	private String newbieEnjoy;
	/**
	 * 放款操作时间
	 */
	private Date giveMoneyOperationTime;
	public String getExpectTimeFormat() {
		return expectTimeFormat;
	}

	public void setExpectTimeFormat(String expectTimeFormat) {
		this.expectTimeFormat = expectTimeFormat;
	}

	/**
	 * @return the investPlan
	 */
	public int getInvestPlan() {
		return investPlan;
	}

	/**
	 * @param investPlan
	 *            the investPlan to set
	 */
	public void setInvestPlan(int investPlan) {
		this.investPlan = investPlan;
	}

	/**
	 * @return the calculateMoneyNeedRaised
	 */
	public Double getCalculateMoneyNeedRaised() {
		return calculateMoneyNeedRaised;
	}

	/**
	 * @param calculateMoneyNeedRaised
	 *            the calculateMoneyNeedRaised to set
	 */
	public void setCalculateMoneyNeedRaised(Double calculateMoneyNeedRaised) {
		this.calculateMoneyNeedRaised = calculateMoneyNeedRaised;
	}

	public Loan() {
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the borrowMoneyUserID
	 */
	public String getBorrowMoneyUserID() {
		return borrowMoneyUserID;
	}

	/**
	 * @param borrowMoneyUserID
	 *            the borrowMoneyUserID to set
	 */
	public void setBorrowMoneyUserID(String borrowMoneyUserID) {
		this.borrowMoneyUserID = borrowMoneyUserID;
	}

	/**
	 * @return the borrowMoneyUser
	 */
	public User getBorrowMoneyUser() {
		return borrowMoneyUser;
	}

	/**
	 * @param borrowMoneyUser
	 *            the borrowMoneyUser to set
	 */
	public void setBorrowMoneyUser(User borrowMoneyUser) {
		this.borrowMoneyUser = borrowMoneyUser;
	}

	/**
	 * @return the giveMoneyTime
	 */
	public Date getGiveMoneyTime() {
		return giveMoneyTime;
	}

	/**
	 * @param giveMoneyTime
	 *            the giveMoneyTime to set
	 */
	public void setGiveMoneyTime(Date giveMoneyTime) {
		this.giveMoneyTime = giveMoneyTime;
	}

	/**
	 * @return the sponsorUser
	 */
	public User getSponsorUser() {
		return sponsorUser;
	}

	/**
	 * @param sponsorUser
	 *            the sponsorUser to set
	 */
	public void setSponsorUser(User sponsorUser) {
		this.sponsorUser = sponsorUser;
	}

	/**
	 * @return the contractName
	 */
	public String getContractName() {
		return contractName;
	}

	/**
	 * @param contractName
	 *            the contractName to set
	 */
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the pawn
	 */
	public String getPawn() {
		return pawn;
	}

	/**
	 * @param pawn
	 *            the pawn to set
	 */
	public void setPawn(String pawn) {
		this.pawn = pawn;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the lightspot
	 */
	public String getLightspot() {
		return lightspot;
	}

	/**
	 * @param lightspot
	 *            the lightspot to set
	 */
	public void setLightspot(String lightspot) {
		this.lightspot = lightspot;
	}

	/**
	 * @return the riskControlMeasures
	 */
	public String getRiskControlMeasures() {
		return riskControlMeasures;
	}

	/**
	 * @param riskControlMeasures
	 *            the riskControlMeasures to set
	 */
	public void setRiskControlMeasures(String riskControlMeasures) {
		this.riskControlMeasures = riskControlMeasures;
	}

	/**
	 * @return the contract
	 */
	public String getContract() {
		return contract;
	}

	/**
	 * @param contract
	 *            the contract to set
	 */
	public void setContract(String contract) {
		this.contract = contract;
	}

	/**
	 * @return the totalmoney
	 */
	public Double getTotalmoney() {
		return totalmoney;
	}

	/**
	 * @param totalmoney
	 *            the totalmoney to set
	 */
	public void setTotalmoney(Double totalmoney) {
		this.totalmoney = totalmoney;
	}

	/**
	 * @return the investOriginMoney
	 */
	public Double getInvestOriginMoney() {
		return investOriginMoney;
	}

	/**
	 * @param investOriginMoney
	 *            the investOriginMoney to set
	 */
	public void setInvestOriginMoney(Double investOriginMoney) {
		this.investOriginMoney = investOriginMoney;
	}

	/**
	 * @return the increaseMoney
	 */
	public Double getIncreaseMoney() {
		return increaseMoney;
	}

	/**
	 * @param increaseMoney
	 *            the increaseMoney to set
	 */
	public void setIncreaseMoney(Double increaseMoney) {
		this.increaseMoney = increaseMoney;
	}

	/**
	 * @return the rate
	 */
	public Double getRate() {
		return rate;
	}

	/**
	 * @param rate
	 *            the rate to set
	 */
	public void setRate(Double rate) {
		this.rate = rate;
	}

	/**
	 * @return the awardMoneyRate
	 */
	public Double getAwardMoneyRate() {
		return awardMoneyRate;
	}

	/**
	 * @param awardMoneyRate
	 *            the awardMoneyRate to set
	 */
	public void setAwardMoneyRate(Double awardMoneyRate) {
		this.awardMoneyRate = awardMoneyRate;
	}

	/**
	 * @return the awardLink
	 */
	public String getAwardLink() {
		return awardLink;
	}

	/**
	 * @param awardLink
	 *            the awardLink to set
	 */
	public void setAwardLink(String awardLink) {
		this.awardLink = awardLink;
	}

	/**
	 * @return the loanType
	 */
	public String getLoanType() {
		return loanType;
	}

	/**
	 * @param loanType
	 *            the loanType to set
	 */
	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	/**
	 * @return the loanGuranteeFee
	 */
	public Double getLoanGuranteeFee() {
		return loanGuranteeFee;
	}

	/**
	 * @param loanGuranteeFee
	 *            the loanGuranteeFee to set
	 */
	public void setLoanGuranteeFee(Double loanGuranteeFee) {
		this.loanGuranteeFee = loanGuranteeFee;
	}

	/**
	 * @return the operationType
	 */
	public String getOperationType() {
		return operationType;
	}

	/**
	 * @param operationType
	 *            the operationType to set
	 */
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	/**
	 * @return the repayType
	 */
	public String getRepayType() {
		return repayType;
	}

	/**
	 * @param repayType
	 *            the repayType to set
	 */
	public void setRepayType(String repayType) {
		this.repayType = repayType;
	}

	/**
	 * @return the beforeRepay
	 */
	public String getBeforeRepay() {
		return beforeRepay;
	}

	/**
	 * @param beforeRepay
	 *            the beforeRepay to set
	 */
	public void setBeforeRepay(String beforeRepay) {
		this.beforeRepay = beforeRepay;
	}

	/**
	 * @return the deadline
	 */
	public Integer getDeadline() {
		return deadline;
	}

	/**
	 * @param deadline
	 *            the deadline to set
	 */
	public void setDeadline(Integer deadline) {
		this.deadline = deadline;
	}

	/**
	 * @return the day
	 */
	public Integer getDay() {
		return day;
	}

	/**
	 * @param day
	 *            the day to set
	 */
	public void setDay(Integer day) {
		this.day = day;
	}

	/**
	 * @return the expectTime
	 */
	public Date getExpectTime() {
		return expectTime;
	}

	/**
	 * @param expectTime
	 *            the expectTime to set
	 */
	public void setExpectTime(Date expectTime) {
		this.expectTime = expectTime;
	}

	/**
	 * @return the textItem
	 */
	public String getTextItem() {
		return textItem;
	}

	/**
	 * @param textItem
	 *            the textItem to set
	 */
	public void setTextItem(String textItem) {
		this.textItem = textItem;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the commitTime
	 */
	public Date getCommitTime() {
		return commitTime;
	}

	/**
	 * @param commitTime
	 *            the commitTime to set
	 */
	public void setCommitTime(Date commitTime) {
		this.commitTime = commitTime;
	}

	public Double getMaxInvestMoney() {
		return maxInvestMoney;
	}

	public void setMaxInvestMoney(Double maxInvestMoney) {
		this.maxInvestMoney = maxInvestMoney;
	}

	/**
	 * @return the symbol
	 */
	public Integer getSymbol() {
		return symbol;
	}

	/**
	 * @param symbol
	 *            the symbol to set
	 */
	public void setSymbol(Integer symbol) {
		this.symbol = symbol;
	}

	
	
	/**
	 * @return the newbieEnjoy
	 */
	public String getNewbieEnjoy() {
		return newbieEnjoy;
	}

	/**
	 * @param newbieEnjoy the newbieEnjoy to set
	 */
	public void setNewbieEnjoy(String newbieEnjoy) {
		this.newbieEnjoy = newbieEnjoy;
	}

	
	
	/**
	 * @return the giveMoneyOperationTime
	 */
	public Date getGiveMoneyOperationTime() {
		return giveMoneyOperationTime;
	}

	/**
	 * @param giveMoneyOperationTime the giveMoneyOperationTime to set
	 */
	public void setGiveMoneyOperationTime(Date giveMoneyOperationTime) {
		this.giveMoneyOperationTime = giveMoneyOperationTime;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Loan [id=" + id + ", name=" + name + ", borrowMoneyUser="
				+ borrowMoneyUser + ", borrowMoneyUserID=" + borrowMoneyUserID
				+ ", pawn=" + pawn + ", description=" + description
				+ ", lightspot=" + lightspot + ", riskControlMeasures="
				+ riskControlMeasures + ", contract=" + contract
				+ ", totalmoney=" + totalmoney + ", investOriginMoney="
				+ investOriginMoney + ", increaseMoney=" + increaseMoney
				+ ", rate=" + rate + ", ratePercent=" + ratePercent
				+ ", awardMoneyRate=" + awardMoneyRate
				+ ", awardMoneyRatePercent=" + awardMoneyRatePercent
				+ ", awardLink=" + awardLink + ", loanType=" + loanType
				+ ", loanGuranteeFee=" + loanGuranteeFee + ", operationType="
				+ operationType + ", repayType=" + repayType + ", beforeRepay="
				+ beforeRepay + ", deadline=" + deadline + ", day=" + day
				+ ", expectTime=" + expectTime + ", status=" + status
				+ ", textItem=" + textItem + ", commitTime=" + commitTime
				+ ", giveMoneyTime=" + giveMoneyTime + ", sponsorUser="
				+ sponsorUser + ", contractName=" + contractName + ", type="
				+ type + ", calculateMoneyNeedRaised="
				+ calculateMoneyNeedRaised + ", investPlan=" + investPlan
				+ ", maxInvestMoney=" + maxInvestMoney + ", expectTimeFormat="
				+ expectTimeFormat + ", symbol=" + symbol + ", newbieEnjoy="
				+ newbieEnjoy + ", giveMoneyOperationTime="
				+ giveMoneyOperationTime + "]";
	}

	
}
