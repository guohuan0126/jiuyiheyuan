package com.duanrong.drpay.business.demand.model;

import java.util.Date;

/**
 * @Description: 活期宝项目
 * @Author: 
 * @CreateDate: Nov 24, 2014
 */
public class DemandTreasureLoan {
	
	// 当前存量资产总额
	private Double repayMentMoney; 
	// 已到期资产总额
	private Double alreadyMoney; 
	/**************** 项目基本信息 **********************/
	// id
	private String id;
	// 项目名称
	private String loanName;
	// 项目类型
	private String loanType;
	// 项目状态 （还款中、完成）
	private String loanStatus;
	// 项目描述
	private String description;
	// 借款总金额
	private Double totalMoney;
	// 开始时间
	private Date startTime;
	// 结束时间
	private Date finishTime;
	// 是否显示(0删除,1显示)
	private Integer display;
	// 项目创建者
	private String creator;
	// 创建时间
	private Date createTime;
	// 修改人
	private String modifyPerson;
	// 修改时间
	private Date modifyTime;
	// 计算方式(天、月)
	private String operationType;
	// 借款期限(月)
	private Integer month;
	// 借款期限(天)
	private Integer day;
	// 项目周期
	private String term;
	
	/**************** 借款人信息 **********************/
	// 借款人
	private String borrower;
	// 身份证号
	private String idCard;
	// 品牌和型号
	private String brand;
	// 车牌号
	private String licensePlateNumber;
	// 公里数
	private String kilometreAmount;
	// 购买时间
	private Date buyTime;
	// 评估价格
	private String assessPrice;
	// 抵押方式
	private String guaranteeType;
	// 抵/质押率
	private String guaranteeRate;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLoanName() {
		return loanName;
	}
	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}
	public String getLoanType() {
		return loanType;
	}
	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}
	public String getLoanStatus() {
		return loanStatus;
	}
	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Double getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	public Integer getDisplay() {
		return display;
	}
	public void setDisplay(Integer display) {
		this.display = display;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getModifyPerson() {
		return modifyPerson;
	}
	public void setModifyPerson(String modifyPerson) {
		this.modifyPerson = modifyPerson;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public Integer getDay() {
		return day;
	}
	public void setDay(Integer day) {
		this.day = day;
	}
	public String getBorrower() {
		return borrower;
	}
	public void setBorrower(String borrower) {
		this.borrower = borrower;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
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
	public String getKilometreAmount() {
		return kilometreAmount;
	}
	public void setKilometreAmount(String kilometreAmount) {
		this.kilometreAmount = kilometreAmount;
	}
	public Date getBuyTime() {
		return buyTime;
	}
	public void setBuyTime(Date buyTime) {
		this.buyTime = buyTime;
	}
	public String getAssessPrice() {
		return assessPrice;
	}
	public void setAssessPrice(String assessPrice) {
		this.assessPrice = assessPrice;
	}
	public String getGuaranteeType() {
		return guaranteeType;
	}
	public void setGuaranteeType(String guaranteeType) {
		this.guaranteeType = guaranteeType;
	}
	public String getGuaranteeRate() {
		return guaranteeRate;
	}
	public void setGuaranteeRate(String guaranteeRate) {
		this.guaranteeRate = guaranteeRate;
	}
	public Double getRepayMentMoney() {
		return repayMentMoney;
	}
	public void setRepayMentMoney(Double repayMentMoney) {
		this.repayMentMoney = repayMentMoney;
	}
	public Double getAlreadyMoney() {
		return alreadyMoney;
	}
	public void setAlreadyMoney(Double alreadyMoney) {
		this.alreadyMoney = alreadyMoney;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	@Override
	public String toString() {
		return "DemandTreasureLoan [repayMentMoney=" + repayMentMoney
				+ ", alreadyMoney=" + alreadyMoney + ", id=" + id
				+ ", loanName=" + loanName + ", loanType=" + loanType
				+ ", loanStatus=" + loanStatus + ", description=" + description
				+ ", totalMoney=" + totalMoney + ", startTime=" + startTime
				+ ", finishTime=" + finishTime + ", display=" + display
				+ ", creator=" + creator + ", createTime=" + createTime
				+ ", modifyPerson=" + modifyPerson + ", modifyTime="
				+ modifyTime + ", operationType=" + operationType + ", month="
				+ month + ", day=" + day + ", term=" + term + ", borrower="
				+ borrower + ", idCard=" + idCard + ", brand=" + brand
				+ ", licensePlateNumber=" + licensePlateNumber
				+ ", kilometreAmount=" + kilometreAmount + ", buyTime="
				+ buyTime + ", assessPrice=" + assessPrice + ", guaranteeType="
				+ guaranteeType + ", guaranteeRate=" + guaranteeRate + "]";
	}
}