package com.duanrong.business.loan.model;

import java.util.Date;
import java.util.List;

import base.model.BaseModel;

import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.repay.model.Repay;
import com.duanrong.business.user.model.User;
import com.duanrong.util.ArithUtil;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2014-8-28 上午11:16:46
 * @Description : drsoa com.duanrong.business.loan.model Loan.java
 * 
 */
public class Loan extends BaseModel {
	private static final long serialVersionUID = -4982339010511415019L;
	/**
	 * 给本类添加任何字段,请重写该toString方法,方便记录该字段值的变化
	 * 
	 * 给本类添加任何字段,请重写该toString方法,方便记录该字段值的变化
	 * 
	 * 给本类添加任何字段,请重写该toString方法,方便记录该字段值的变化
	 * 
	 * 给本类添加任何字段,请重写该toString方法,方便记录该字段值的变化
	 * 
	 */
	// id
	private String id;
	// 项目名称
	private String name;
	// 借款人(用户表id 外键)
	private User borrowMoneyUser;
	// 借款人姓名
	private String borrowMoneyUserName;
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
	private String totalmoneyStr;
	// 机构专享
	private String organizationExclusive;
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
	// 计息规则
	private String interestRule;
	// 放款操作时间
	private Date giveMoneyOperationTime;
	
	//到期时间
	private Date finishTime;
	
	// 给投资人补息总和
	private double loanAllowanceInterest;
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

	// 查询开始时间(条件查询用)
	private String startTime;

	// 查询结束时间(条件查询用)
	private String endTime;

	// 提交时间
	private Date commitTime;
	// 计息时间
	private Date giveMoneyTime;
	// 项目发起人
	private User sponsorUser;
	// 借款合同名称
	private String contractName;
	// 项目图片相关
	private BannerPicture picture;

	private String type;

	// 新手专享
	private String newbieEnjoy;
	// 页面投资进度显示(已募集金额)
	private Double calculateMoneyNeedRaised;
	// 页面投资进度显示(圆圈)
	private int investPlan;

	// 最大投资金额
	private Double maxInvestMoney;
	// 预计执行之间格式化
	private String expectTimeFormat;

	private List<Invest> invests;
	private List<Repay> repays;

	private Vehicle vehicle;
	private House house;
	private Enterprise enterprise;

	private Integer symbol;

	// 用于预告项目时间显示的毫秒值
	private Long millsTime;

	// 进度，前台用
	private Integer progress;

	// 剩余金额，前台用
	private Double remainAmount;

	// 自动投标上限金额
	private Double autoInvestMoneyTotal;

	// 项目审核人
	private String verifyUser;

	// 审核是否通过
	private String verified;

	private Double deposit;

	private String emailSend;

	private boolean opendAuto;
	//查询条件
	private String start;
	/**
	 * 品牌和型号
	 */
	private String brand;
	
	/**
	 * 车牌号
	 */
	private String licensePlateNumber;
	private Double maxcorpus;
	private Double maxinterest;
	private Date maxrepayday;
	private Double maxreapymoney;
	private String specialType;
	
	public String getSpecialType() {
		return specialType;
	}

	public void setSpecialType(String specialType) {
		this.specialType = specialType;
	}

	public Double getMaxcorpus() {
		return maxcorpus;
	}

	public void setMaxcorpus(Double maxcorpus) {
		this.maxcorpus = maxcorpus;
	}

	public Double getMaxinterest() {
		return maxinterest;
	}

	public void setMaxinterest(Double maxinterest) {
		this.maxinterest = maxinterest;
	}

	public Date getMaxrepayday() {
		return maxrepayday;
	}

	public void setMaxrepayday(Date maxrepayday) {
		this.maxrepayday = maxrepayday;
	}

	public Double getMaxreapymoney() {
		return maxreapymoney;
	}

	public void setMaxreapymoney(Double maxreapymoney) {
		this.maxreapymoney = maxreapymoney;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getLicensePlateNumber() {
		return licensePlateNumber;
	}

	public void setLicensePlateNumber(String licensePlateNumber) {
		this.licensePlateNumber = licensePlateNumber;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	private String end;
	private String statusStr;

	public boolean isOpendAuto() {
		return opendAuto;
	}

	public void setOpendAuto(boolean opendAuto) {
		this.opendAuto = opendAuto;
	}

	/************************* 附加信息 *****************************/
	private String itemAddress;
	private String itemRate;
	private String customerManagerJobNumber;
	private String customerManagerName;
	private String borrowerName;
	private String yaCarAndGPS;
	private String remark;

	/**
	 * @return the itemAddress
	 */
	public String getItemAddress() {
		return itemAddress;
	}

	/**
	 * @param itemAddress
	 *            the itemAddress to set
	 */
	public void setItemAddress(String itemAddress) {
		this.itemAddress = itemAddress;
	}

	/**
	 * @return the itemRate
	 */
	public String getItemRate() {
		return itemRate;
	}

	/**
	 * @param itemRate
	 *            the itemRate to set
	 */
	public void setItemRate(String itemRate) {
		this.itemRate = itemRate;
	}

	/**
	 * @return the customerManagerJobNumber
	 */
	public String getCustomerManagerJobNumber() {
		return customerManagerJobNumber;
	}

	/**
	 * @param customerManagerJobNumber
	 *            the customerManagerJobNumber to set
	 */
	public void setCustomerManagerJobNumber(String customerManagerJobNumber) {
		this.customerManagerJobNumber = customerManagerJobNumber;
	}

	/**
	 * @return the customerManagerName
	 */
	public String getCustomerManagerName() {
		return customerManagerName;
	}

	/**
	 * @param customerManagerName
	 *            the customerManagerName to set
	 */
	public void setCustomerManagerName(String customerManagerName) {
		this.customerManagerName = customerManagerName;
	}

	/**
	 * @return the borrowerName
	 */
	public String getBorrowerName() {
		return borrowerName;
	}

	/**
	 * @param borrowerName
	 *            the borrowerName to set
	 */
	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}

	/**
	 * @return the yaCarAndGPS
	 */
	public String getYaCarAndGPS() {
		return yaCarAndGPS;
	}

	/**
	 * @param yaCarAndGPS
	 *            the yaCarAndGPS to set
	 */
	public void setYaCarAndGPS(String yaCarAndGPS) {
		this.yaCarAndGPS = yaCarAndGPS;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/************************* 附加信息 *****************************/

	public Double getRemainAmount() {
		return remainAmount;
	}

	public void setRemainAmount(Double remainAmount) {
		this.remainAmount = remainAmount;
	}

	public Integer getProgress() {
		return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}

	public Integer getSymbol() {
		return symbol;
	}

	public void setSymbol(Integer symbol) {
		this.symbol = symbol;
	}

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

	public String getBorrowMoneyUserName() {
		return borrowMoneyUserName;
	}

	public void setBorrowMoneyUserName(String borrowMoneyUserName) {
		this.borrowMoneyUserName = borrowMoneyUserName;
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
	 * @return the ratePercent
	 */
	public Double getRatePercent() {
		if (this.ratePercent == null && this.getRate() != null) {
			return ArithUtil.round(this.getRate() * 100, 2);
		}
		return ratePercent;
	}

	/**
	 * @param ratePercent
	 *            the ratePercent to set
	 */
	public void setRatePercent(Double ratePercent) {
		this.ratePercent = ratePercent;
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
	 * @return the awardMoneyRatePercent
	 */
	public Double getAwardMoneyRatePercent() {
		if (this.awardMoneyRatePercent == null
				&& this.getAwardMoneyRate() != null) {
			return ArithUtil.round(this.getAwardMoneyRate() * 100, 2);
		}
		return awardMoneyRatePercent;
	}

	/**
	 * @param awardMoneyRatePercent
	 *            the awardMoneyRatePercent to set
	 */
	public void setAwardMoneyRatePercent(Double awardMoneyRatePercent) {
		this.awardMoneyRatePercent = awardMoneyRatePercent;
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

	/**
	 * @return the picture
	 */
	public BannerPicture getPicture() {
		return picture;
	}

	/**
	 * @param picture
	 *            the picture to set
	 */
	public void setPicture(BannerPicture picture) {
		this.picture = picture;
	}

	public Double getMaxInvestMoney() {
		return maxInvestMoney;
	}

	public void setMaxInvestMoney(Double maxInvestMoney) {
		this.maxInvestMoney = maxInvestMoney;
	}

	public List<Invest> getInvests() {
		return invests;
	}

	public void setInvests(List<Invest> invests) {
		this.invests = invests;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public House getHouse() {
		return house;
	}

	public void setHouse(House house) {
		this.house = house;
	}

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	public Long getMillsTime() {
		return millsTime;
	}

	public void setMillsTime(Long millsTime) {
		this.millsTime = millsTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Double getAutoInvestMoneyTotal() {
		return autoInvestMoneyTotal;
	}

	public void setAutoInvestMoneyTotal(Double autoInvestMoneyTotal) {
		this.autoInvestMoneyTotal = autoInvestMoneyTotal;
	}

	/**
	 * @return the verifyUser
	 */
	public String getVerifyUser() {
		return verifyUser;
	}

	/**
	 * @param verifyUser
	 *            the verifyUser to set
	 */
	public void setVerifyUser(String verifyUser) {
		this.verifyUser = verifyUser;
	}

	/**
	 * @return the verified
	 */
	public String getVerified() {
		return verified;
	}

	/**
	 * @param verified
	 *            the verified to set
	 */
	public void setVerified(String verified) {
		this.verified = verified;
	}

	/**
	 * @return the deposit
	 */
	public Double getDeposit() {
		return deposit;
	}

	/**
	 * @param deposit
	 *            the deposit to set
	 */
	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}

	/**
	 * @return the emailSend
	 */
	public String getEmailSend() {
		return emailSend;
	}

	/**
	 * @param emailSend
	 *            the emailSend to set
	 */
	public void setEmailSend(String emailSend) {
		this.emailSend = emailSend;
	}

	public String getTotalmoneyStr() {
		return totalmoneyStr;
	}

	public void setTotalmoneyStr(String totalmoneyStr) {
		this.totalmoneyStr = totalmoneyStr;
	}

	public List<Repay> getRepays() {
		return repays;
	}

	public void setRepays(List<Repay> repays) {
		this.repays = repays;
	}

	public String getNewbieEnjoy() {
		return newbieEnjoy;
	}

	public void setNewbieEnjoy(String newbieEnjoy) {
		this.newbieEnjoy = newbieEnjoy;
	}

	/**
	 * @return the interestRule
	 */
	public String getInterestRule() {
		return interestRule;
	}

	/**
	 * @param interestRule
	 *            the interestRule to set
	 */
	public void setInterestRule(String interestRule) {
		this.interestRule = interestRule;
	}

	/**
	 * @return the giveMoneyOperationTime
	 */
	public Date getGiveMoneyOperationTime() {
		return giveMoneyOperationTime;
	}

	/**
	 * @param giveMoneyOperationTime
	 *            the giveMoneyOperationTime to set
	 */
	public void setGiveMoneyOperationTime(Date giveMoneyOperationTime) {
		this.giveMoneyOperationTime = giveMoneyOperationTime;
	}

	/**
	 * @return the loanAllowanceInterest
	 */
	public double getLoanAllowanceInterest() {
		return loanAllowanceInterest;
	}

	/**
	 * @param loanAllowanceInterest
	 *            the loanAllowanceInterest to set
	 */
	public void setLoanAllowanceInterest(double loanAllowanceInterest) {
		this.loanAllowanceInterest = loanAllowanceInterest;
	}

	/**
	 * @return the organizationExclusive
	 */
	public String getOrganizationExclusive() {
		return organizationExclusive;
	}

	/**
	 * @param organizationExclusive
	 *            the organizationExclusive to set
	 */
	public void setOrganizationExclusive(String organizationExclusive) {
		this.organizationExclusive = organizationExclusive;
	}

	
	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	@Override
	public String toString() {
		return "Loan [id=" + id + ", name=" + name + ", borrowMoneyUser="
				+ borrowMoneyUser + ", borrowMoneyUserName="
				+ borrowMoneyUserName + ", borrowMoneyUserID="
				+ borrowMoneyUserID + ", pawn=" + pawn + ", description="
				+ description + ", lightspot=" + lightspot
				+ ", riskControlMeasures=" + riskControlMeasures
				+ ", contract=" + contract + ", totalmoney=" + totalmoney
				+ ", totalmoneyStr=" + totalmoneyStr
				+ ", organizationExclusive=" + organizationExclusive
				+ ", investOriginMoney=" + investOriginMoney
				+ ", increaseMoney=" + increaseMoney + ", rate=" + rate
				+ ", ratePercent=" + ratePercent + ", awardMoneyRate="
				+ awardMoneyRate + ", awardMoneyRatePercent="
				+ awardMoneyRatePercent + ", awardLink=" + awardLink
				+ ", loanType=" + loanType + ", interestRule=" + interestRule
				+ ", giveMoneyOperationTime=" + giveMoneyOperationTime
				+ ", loanAllowanceInterest=" + loanAllowanceInterest
				+ ", loanGuranteeFee=" + loanGuranteeFee + ", operationType="
				+ operationType + ", repayType=" + repayType + ", beforeRepay="
				+ beforeRepay + ", deadline=" + deadline + ", day=" + day
				+ ", expectTime=" + expectTime + ", status=" + status
				+ ", textItem=" + textItem + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", commitTime=" + commitTime
				+ ", giveMoneyTime=" + giveMoneyTime + ", sponsorUser="
				+ sponsorUser + ", contractName=" + contractName + ", picture="
				+ picture + ", type=" + type + ", newbieEnjoy=" + newbieEnjoy
				+ ", calculateMoneyNeedRaised=" + calculateMoneyNeedRaised
				+ ", investPlan=" + investPlan + ", maxInvestMoney="
				+ maxInvestMoney + ", expectTimeFormat=" + expectTimeFormat
				+ ", invests=" + invests + ", repays=" + repays + ", vehicle="
				+ vehicle + ", house=" + house + ", enterprise=" + enterprise
				+ ", symbol=" + symbol + ", millsTime=" + millsTime
				+ ", progress=" + progress + ", remainAmount=" + remainAmount
				+ ", autoInvestMoneyTotal=" + autoInvestMoneyTotal
				+ ", verifyUser=" + verifyUser + ", verified=" + verified
				+ ", deposit=" + deposit + ", emailSend=" + emailSend
				+ ", opendAuto=" + opendAuto + ", itemAddress=" + itemAddress
				+ ", itemRate=" + itemRate + ", customerManagerJobNumber="
				+ customerManagerJobNumber + ", customerManagerName="
				+ customerManagerName + ", borrowerName=" + borrowerName
				+ ", yaCarAndGPS=" + yaCarAndGPS + ", remark=" + remark + "]";
	}

}
