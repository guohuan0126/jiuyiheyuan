package com.duanrong.business.demand.model;

import java.util.Date;

import base.model.BaseModel;

/**
 * @Description: 活期宝项目
 * @Author: 
 * @CreateDate: Nov 24, 2014
 */
public class DemandtreasureLoan extends BaseModel {

	private static final long serialVersionUID = -5418222607291713534L;
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
	// 是否显示(0默认显示,1不显示)
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
	//项目地点
	private String loanAddr;
	
	//显示用 开始时间
	private String  showstartTime;
	//显示用 结束时间
	private String  showfinishTime;
	//核算公司
	private String accountingCompany;
	//农贷id
	private String  loaninfoId;
	//农贷项目的还款方式
	private String agrepayType;
	//农贷项目的主合同编号
	private String agcontractId;
	
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
	 * @return the sourceRemark
	 */
	public String getSourceRemark() {
		return sourceRemark;
	}
	/**
	 * @param sourceRemark the sourceRemark to set
	 */
	public void setSourceRemark(String sourceRemark) {
		this.sourceRemark = sourceRemark;
	}
	/**
	 * @return the accountingDepartment
	 */
	public Integer getAccountingDepartment() {
		return accountingDepartment;
	}
	/**
	 * @param accountingDepartment the accountingDepartment to set
	 */
	public void setAccountingDepartment(Integer accountingDepartment) {
		this.accountingDepartment = accountingDepartment;
	}
	//车贷的合同编号
	private String contractId;
	//来源标识
	private String sourceRemark;
	//核算单位
	private Integer accountingDepartment;
	//查询条件开始时间
	private String start;
	private String end;
	//查询条件实际结束时间
	private String actualStart;
	private String actualEnd;
	//应结束时间
	private String actualFinishTime;
	public String getActualFinishTime() {
		return actualFinishTime;
	}
	public void setActualFinishTime(String actualFinishTime) {
		this.actualFinishTime = actualFinishTime;
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
	public String getActualStart() {
		return actualStart;
	}
	public void setActualStart(String actualStart) {
		this.actualStart = actualStart;
	}
	public String getActualEnd() {
		return actualEnd;
	}
	public void setActualEnd(String actualEnd) {
		this.actualEnd = actualEnd;
	}

	
	/**
	 * @return the agcontractId
	 */
	public String getAgcontractId() {
		return agcontractId;
	}
	/**
	 * @param agcontractId the agcontractId to set
	 */
	public void setAgcontractId(String agcontractId) {
		this.agcontractId = agcontractId;
	}
	/**
	 * @return the agrepayType
	 */
	public String getAgrepayType() {
		return agrepayType;
	}
	/**
	 * @param agrepayType the agrepayType to set
	 */
	public void setAgrepayType(String agrepayType) {
		this.agrepayType = agrepayType;
	}
	/**
	 * @return the loaninfoId
	 */
	public String getLoaninfoId() {
		return loaninfoId;
	}
	/**
	 * @param loaninfoId the loaninfoId to set
	 */
	public void setLoaninfoId(String loaninfoId) {
		this.loaninfoId = loaninfoId;
	}
	/**
	 * @return the accountingCompany
	 */
	public String getAccountingCompany() {
		return accountingCompany;
	}
	/**
	 * @param accountingCompany the accountingCompany to set
	 */
	public void setAccountingCompany(String accountingCompany) {
		this.accountingCompany = accountingCompany;
	}
	public String getShowstartTime() {
		return showstartTime;
	}
	public void setShowstartTime(String showstartTime) {
		this.showstartTime = showstartTime;
	}
	public String getShowfinishTime() {
		return showfinishTime;
	}
	public void setShowfinishTime(String showfinishTime) {
		this.showfinishTime = showfinishTime;
	}
	public String getLoanAddr() {
		return loanAddr;
	}
	public void setLoanAddr(String loanAddr) {
		this.loanAddr = loanAddr;
	}
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
	// 初次登记日期
	private Date buyTime;
	// 评估价格
	private String assessPrice;
	// 抵押方式
	private String guaranteeType;
	// 抵/质押率
	private String guaranteeRate;
	//楼房性质
	private String buildingProperty;
	// 建筑面积
	private String buildingArea;

	private String sbuyTime;
	
	private String borrowingPurposes;	//借款用途
	private String maritalStatus;		//婚姻状况【0：未婚；1已婚；2：离异；3：丧偶】
	private String location;			//所在地
	private String sourceOfRepayment;	//还款来源
	//子标的的id号
	private String forkId;
	//未开放金额，用于统计用户赎回的资金
	private Double openAmount;
	//剩余可投金额，当赎回的资金重新开放时，可投金额=可投金额+赎回的金额，赎回金额变为0
	private Double validMoney;
	//资产开放状态  notopen未开放  opened已开放
	private String openStatus;
	
	//活期宝资产开放执行id，用于删除计划时进行资产回滚
	private String availableId;
	//原先赎回中的资产，用于删除计划时进行转让的那部分资产回滚
	private Double originalRedemptionMoney;
	//排序类型，为按项目名称排序，为创建时间排序
	private String type;
	
	/**************** 供应宝借款信息 **********************/
	//借款公司
	private String borrowingCompany;
	//营业执照号
	private String businessLicenseNumber;
	//经营年限
	private String operationYear;
	//主营产品或服务
	private String operationProduction;
	//注册资本
	private String registeredCapital;
	//实收资本
	private String realIncomeCapital;
	//员工人数
	private String staffNumber;
	//全国法院被执行人信息
	private String nationalCourtsReport;
	//失信被执行人名单查询
	private String dishonstPersonReport;
	//公开渠道查询负面信息
	private String publicChannelNegativeInfo;
	//其他负面信息
	private String otherNegativeInfo;
	//其他说明
	private String otherInfo;
	//还款方式
	private String repayType;
	//父标Id
	private String parentId;	
	//---------------setter and getter---------------------------
	
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	public String getBorrowingPurposes() {
		return borrowingPurposes;
	}
	/**
	 * @return the availableId
	 */
	public String getAvailableId() {
		return availableId;
	}
	/**
	 * @param availableId the availableId to set
	 */
	public void setAvailableId(String availableId) {
		this.availableId = availableId;
	}
	/**
	 * @return the originalRedemptionMoney
	 */
	public Double getOriginalRedemptionMoney() {
		return originalRedemptionMoney;
	}
	/**
	 * @param originalRedemptionMoney the originalRedemptionMoney to set
	 */
	public void setOriginalRedemptionMoney(Double originalRedemptionMoney) {
		this.originalRedemptionMoney = originalRedemptionMoney;
	}
	public void setBorrowingPurposes(String borrowingPurposes) {
		this.borrowingPurposes = borrowingPurposes;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getSourceOfRepayment() {
		return sourceOfRepayment;
	}
	public void setSourceOfRepayment(String sourceOfRepayment) {
		this.sourceOfRepayment = sourceOfRepayment;
	}
	public String getSbuyTime() {
		return sbuyTime;
	}
	public void setSbuyTime(String sbuyTime) {
		this.sbuyTime = sbuyTime;
	}
	public String getSstartTime() {
		return sstartTime;
	}
	public void setSstartTime(String sstartTime) {
		this.sstartTime = sstartTime;
	}
	public String getSfinishTime() {
		return sfinishTime;
	}
	public void setSfinishTime(String sfinishTime) {
		this.sfinishTime = sfinishTime;
	}
	private String sstartTime;
	private String sfinishTime;
	
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
	/**
	 * @return the forkId
	 */
	public String getForkId() {
		return forkId;
	}
	/**
	 * @param forkId the forkId to set
	 */
	public void setForkId(String forkId) {
		this.forkId = forkId;
	}
	/**
	 * @return the openAmount
	 */
	public Double getOpenAmount() {
		return openAmount;
	}
	/**
	 * @param openAmount the openAmount to set
	 */
	public void setOpenAmount(Double openAmount) {
		this.openAmount = openAmount;
	}
	/**
	 * @return the openStatus
	 */
	public String getOpenStatus() {
		return openStatus;
	}
	/**
	 * @param openStatus the openStatus to set
	 */
	public void setOpenStatus(String openStatus) {
		this.openStatus = openStatus;
	}
	/**
	 * @return the validMoney
	 */
	public Double getValidMoney() {
		return validMoney;
	}
	/**
	 * @param validMoney the validMoney to set
	 */
	public void setValidMoney(Double validMoney) {
		this.validMoney = validMoney;
	}
	/**
	 * @return the buildingProperty
	 */
	public String getBuildingProperty() {
		return buildingProperty;
	}
	/**
	 * @param buildingProperty the buildingProperty to set
	 */
	public void setBuildingProperty(String buildingProperty) {
		this.buildingProperty = buildingProperty;
	}
	/**
	 * @return the buildingArea
	 */
	public String getBuildingArea() {
		return buildingArea;
	}
	/**
	 * @param buildingArea the buildingArea to set
	 */
	public void setBuildingArea(String buildingArea) {
		this.buildingArea = buildingArea;
	}
	/**
	 * @return the borrowingCompany
	 */
	public String getBorrowingCompany() {
		return borrowingCompany;
	}
	/**
	 * @param borrowingCompany the borrowingCompany to set
	 */
	public void setBorrowingCompany(String borrowingCompany) {
		this.borrowingCompany = borrowingCompany;
	}
	/**
	 * @return the businessLicenseNumber
	 */
	public String getBusinessLicenseNumber() {
		return businessLicenseNumber;
	}
	/**
	 * @param businessLicenseNumber the businessLicenseNumber to set
	 */
	public void setBusinessLicenseNumber(String businessLicenseNumber) {
		this.businessLicenseNumber = businessLicenseNumber;
	}
	/**
	 * @return the operationYear
	 */
	public String getOperationYear() {
		return operationYear;
	}
	/**
	 * @param operationYear the operationYear to set
	 */
	public void setOperationYear(String operationYear) {
		this.operationYear = operationYear;
	}
	/**
	 * @return the operationProduction
	 */
	public String getOperationProduction() {
		return operationProduction;
	}
	/**
	 * @param operationProduction the operationProduction to set
	 */
	public void setOperationProduction(String operationProduction) {
		this.operationProduction = operationProduction;
	}
	/**
	 * @return the registeredCapital
	 */
	public String getRegisteredCapital() {
		return registeredCapital;
	}
	/**
	 * @param registeredCapital the registeredCapital to set
	 */
	public void setRegisteredCapital(String registeredCapital) {
		this.registeredCapital = registeredCapital;
	}
	/**
	 * @return the realIncomeCapital
	 */
	public String getRealIncomeCapital() {
		return realIncomeCapital;
	}
	/**
	 * @param realIncomeCapital the realIncomeCapital to set
	 */
	public void setRealIncomeCapital(String realIncomeCapital) {
		this.realIncomeCapital = realIncomeCapital;
	}
	/**
	 * @return the staffNumber
	 */
	public String getStaffNumber() {
		return staffNumber;
	}
	/**
	 * @param staffNumber the staffNumber to set
	 */
	public void setStaffNumber(String staffNumber) {
		this.staffNumber = staffNumber;
	}
	/**
	 * @return the nationalCourtsReport
	 */
	public String getNationalCourtsReport() {
		return nationalCourtsReport;
	}
	/**
	 * @param nationalCourtsReport the nationalCourtsReport to set
	 */
	public void setNationalCourtsReport(String nationalCourtsReport) {
		this.nationalCourtsReport = nationalCourtsReport;
	}
	/**
	 * @return the dishonstPersonReport
	 */
	public String getDishonstPersonReport() {
		return dishonstPersonReport;
	}
	/**
	 * @param dishonstPersonReport the dishonstPersonReport to set
	 */
	public void setDishonstPersonReport(String dishonstPersonReport) {
		this.dishonstPersonReport = dishonstPersonReport;
	}
	/**
	 * @return the publicChannelNegativeInfo
	 */
	public String getPublicChannelNegativeInfo() {
		return publicChannelNegativeInfo;
	}
	/**
	 * @param publicChannelNegativeInfo the publicChannelNegativeInfo to set
	 */
	public void setPublicChannelNegativeInfo(String publicChannelNegativeInfo) {
		this.publicChannelNegativeInfo = publicChannelNegativeInfo;
	}
	/**
	 * @return the otherNegativeInfo
	 */
	public String getOtherNegativeInfo() {
		return otherNegativeInfo;
	}
	/**
	 * @param otherNegativeInfo the otherNegativeInfo to set
	 */
	public void setOtherNegativeInfo(String otherNegativeInfo) {
		this.otherNegativeInfo = otherNegativeInfo;
	}
	/**
	 * @return the otherInfo
	 */
	public String getOtherInfo() {
		return otherInfo;
	}
	/**
	 * @param otherInfo the otherInfo to set
	 */
	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
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
	 * @return the parentId
	 */
	public String getParentId() {
		return parentId;
	}
	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	
}