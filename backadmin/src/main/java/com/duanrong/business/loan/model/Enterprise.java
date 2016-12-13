package com.duanrong.business.loan.model;

import java.util.Date;

import base.model.BaseModel;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2014-12-23 上午11:03:07
 * @Description : jdp2p com.esoft.jdp2p.loan.babyModel Enterprise.java
 * 
 */
public class Enterprise extends BaseModel {
	private static final long serialVersionUID = -4176474589099041609L;
	/**
	 * 项目id
	 */
	private String loanId;

	/*********************** 工商信息 *************************/

	/**
	 * 成立时间
	 */
	private Date establishTime;
	/**
	 * 经营范围
	 */
	private String businessScope;
	/**
	 * 注册资本
	 */
	private String registeredCapital;
	/**
	 * 借款人持股比例
	 */
	private String inshold;
	/**
	 * 已经营年限
	 */
	private String operatingPeriod;
	/**
	 * 实收资本
	 */
	private String paidInCapital;
	/**
	 * 关联企业
	 */
	private String affiliatedEnterprise;

	/**
	 * 公司简介
	 */
	private String companyIntroduction;

	/*********************** 个人信息 *************************/

	/**
	 * 客户姓名
	 */
	private String customerName;

	/**
	 * 客户性别
	 */
	private String customerGender;

	/**
	 * 客户年龄
	 */
	private String customerAge;
	/**
	 * 户籍所在地
	 */
	private String registeredPermanentResidence;
	/**
	 * 婚姻状况
	 */
	private String maritalStatus;
	/**
	 * 教育程度
	 */
	private String educationalStatus;
	/**
	 * 本地居住年限
	 */
	private String locaResidenceTime;
	/**
	 * 住房状况
	 */
	private String housingConditions;
	/**
	 * 生产经营地址
	 */
	private String productionAddress;
	/**
	 * 负债历史
	 */
	private String debtHistory;
	/**
	 * 资产状况
	 */
	private String financialStatus;

	/*********************** 经营情况 *************************/

	/**
	 * 组织结构
	 */
	private String organizationStructure;
	/**
	 * 员工人数
	 */
	private String numberEmployees;
	/**
	 * 主营产品或服务
	 */
	private String specializedProductsAndServices;

	/*********************** 财务状况 ***************************/

	/**
	 * 财务数据
	 */
	private String financialData;
	/**
	 * 偿债能力分析
	 */
	private String solvencyAnalysis;
	/**
	 * 盈利能力分析
	 */
	private String profitabilityAnalysis;
	/**
	 * 运营能力分析
	 */
	private String capabilitiesAnalysis;
	/**
	 * 发展能力分析
	 */
	private String developmentAbilityAnalysis;

	/*********************** 信用分析 (个人信用) ***************************/

	/**
	 * 个人信用报告
	 */
	private String personalCreditReport;
	/**
	 * 全国法院被执行人信息(个人)
	 */
	private String ncriByPersonal;
	/**
	 * 失信被执行人名单查询(个人)
	 */
	private String lttoftpstelqByPersonal;
	/**
	 * 公开渠道查询负面信息(个人)
	 */
	private String ocniqByPersonal;

	/*********************** 信用分析 (企业信用) ***************************/

	/**
	 * 企业信用报告
	 */
	private String enterpriseCreditReport;
	/**
	 * 全国法院被执行人信息(企业)
	 */
	private String ncriByEnterprise;
	/**
	 * 失信被执行人名单查询(企业)
	 */
	private String lttoftpstelqByEnterprise;
	/**
	 * 公开渠道查询负面信息(企业)
	 */
	private String ocniqByEnterprise;
	/**
	 * 行政处罚信息(企业)
	 */
	private String api;

	/*********************** 风控信息 ***************************/

	/**
	 * 担保措施
	 */
	private String guaranteesMeasures;

	/**
	 * 借款用途
	 */
	private String loanPurpose;
	/**
	 * 经营性现金收入
	 */
	private String operatingCashIncome;
	/**
	 * 净盈利
	 */
	private String netEarnings;
	/**
	 * 偿债准备金
	 */
	private String sinkingFund;
	/**
	 * 债务收入
	 */
	private String debtRevenue;
	/**
	 * 资产变现
	 */
	private String assetsRealization;
	/**
	 * 外部支持
	 */
	private String externalSupport;

	/**
	 * 其他说明
	 */
	private String otherDescription;
	
	//风险提示
	private String reminderInfo;
	// 风控措施
	private String measuresInfo;
	//逾期处理
	private String overdueInfo;
	//其他说明
	private String otherInfo;
			
	//项目地点
	private String itemAddress;
	//借款人姓名
	private String borrowerIdCard;
	//借款人姓名
	private String borrowerName;
	//新增/展期
	private String remark;
	
	
	
	public String getBorrowerIdCard() {
		return borrowerIdCard;
	}

	public void setBorrowerIdCard(String borrowerIdCard) {
		this.borrowerIdCard = borrowerIdCard;
	}

	public String getItemAddress() {
		return itemAddress;
	}

	public void setItemAddress(String itemAddress) {
		this.itemAddress = itemAddress;
	}

	public String getBorrowerName() {
		return borrowerName;
	}

	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getReminderInfo() {
		return reminderInfo;
	}

	public void setReminderInfo(String reminderInfo) {
		this.reminderInfo = reminderInfo;
	}

	public String getMeasuresInfo() {
		return measuresInfo;
	}

	public void setMeasuresInfo(String measuresInfo) {
		this.measuresInfo = measuresInfo;
	}

	public String getOverdueInfo() {
		return overdueInfo;
	}

	public void setOverdueInfo(String overdueInfo) {
		this.overdueInfo = overdueInfo;
	}

	public String getOtherInfo() {
		return otherInfo;
	}

	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}

	public Date getEstablishTime() {
		return establishTime;
	}

	public void setEstablishTime(Date establishTime) {
		this.establishTime = establishTime;
	}

	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	public String getOperatingPeriod() {
		return operatingPeriod;
	}

	public void setOperatingPeriod(String operatingPeriod) {
		this.operatingPeriod = operatingPeriod;
	}

	public String getRegisteredCapital() {
		return registeredCapital;
	}

	public void setRegisteredCapital(String registeredCapital) {
		this.registeredCapital = registeredCapital;
	}

	public String getInshold() {
		return inshold;
	}

	public void setInshold(String inshold) {
		this.inshold = inshold;
	}

	public String getPaidInCapital() {
		return paidInCapital;
	}

	public void setPaidInCapital(String paidInCapital) {
		this.paidInCapital = paidInCapital;
	}

	public String getAffiliatedEnterprise() {
		return affiliatedEnterprise;
	}

	public void setAffiliatedEnterprise(String affiliatedEnterprise) {
		this.affiliatedEnterprise = affiliatedEnterprise;
	}

	public String getCompanyIntroduction() {
		return companyIntroduction;
	}

	public void setCompanyIntroduction(String companyIntroduction) {
		this.companyIntroduction = companyIntroduction;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerGender() {
		return customerGender;
	}

	public void setCustomerGender(String customerGender) {
		this.customerGender = customerGender;
	}

	public String getCustomerAge() {
		return customerAge;
	}

	public void setCustomerAge(String customerAge) {
		this.customerAge = customerAge;
	}

	public String getRegisteredPermanentResidence() {
		return registeredPermanentResidence;
	}

	public void setRegisteredPermanentResidence(
			String registeredPermanentResidence) {
		this.registeredPermanentResidence = registeredPermanentResidence;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getEducationalStatus() {
		return educationalStatus;
	}

	public void setEducationalStatus(String educationalStatus) {
		this.educationalStatus = educationalStatus;
	}

	public String getLocaResidenceTime() {
		return locaResidenceTime;
	}

	public void setLocaResidenceTime(String locaResidenceTime) {
		this.locaResidenceTime = locaResidenceTime;
	}

	public String getHousingConditions() {
		return housingConditions;
	}

	public void setHousingConditions(String housingConditions) {
		this.housingConditions = housingConditions;
	}

	public String getProductionAddress() {
		return productionAddress;
	}

	public void setProductionAddress(String productionAddress) {
		this.productionAddress = productionAddress;
	}

	public String getDebtHistory() {
		return debtHistory;
	}

	public void setDebtHistory(String debtHistory) {
		this.debtHistory = debtHistory;
	}

	public String getFinancialStatus() {
		return financialStatus;
	}

	public void setFinancialStatus(String financialStatus) {
		this.financialStatus = financialStatus;
	}

	public String getOrganizationStructure() {
		return organizationStructure;
	}

	public void setOrganizationStructure(String organizationStructure) {
		this.organizationStructure = organizationStructure;
	}

	public String getNumberEmployees() {
		return numberEmployees;
	}

	public void setNumberEmployees(String numberEmployees) {
		this.numberEmployees = numberEmployees;
	}

	public String getSpecializedProductsAndServices() {
		return specializedProductsAndServices;
	}

	public void setSpecializedProductsAndServices(
			String specializedProductsAndServices) {
		this.specializedProductsAndServices = specializedProductsAndServices;
	}

	public String getFinancialData() {
		return financialData;
	}

	public void setFinancialData(String financialData) {
		this.financialData = financialData;
	}

	public String getSolvencyAnalysis() {
		return solvencyAnalysis;
	}

	public void setSolvencyAnalysis(String solvencyAnalysis) {
		this.solvencyAnalysis = solvencyAnalysis;
	}

	public String getProfitabilityAnalysis() {
		return profitabilityAnalysis;
	}

	public void setProfitabilityAnalysis(String profitabilityAnalysis) {
		this.profitabilityAnalysis = profitabilityAnalysis;
	}

	public String getCapabilitiesAnalysis() {
		return capabilitiesAnalysis;
	}

	public void setCapabilitiesAnalysis(String capabilitiesAnalysis) {
		this.capabilitiesAnalysis = capabilitiesAnalysis;
	}

	public String getDevelopmentAbilityAnalysis() {
		return developmentAbilityAnalysis;
	}

	public void setDevelopmentAbilityAnalysis(String developmentAbilityAnalysis) {
		this.developmentAbilityAnalysis = developmentAbilityAnalysis;
	}

	public String getPersonalCreditReport() {
		return personalCreditReport;
	}

	public void setPersonalCreditReport(String personalCreditReport) {
		this.personalCreditReport = personalCreditReport;
	}

	public String getNcriByPersonal() {
		return ncriByPersonal;
	}

	public void setNcriByPersonal(String ncriByPersonal) {
		this.ncriByPersonal = ncriByPersonal;
	}

	public String getLttoftpstelqByPersonal() {
		return lttoftpstelqByPersonal;
	}

	public void setLttoftpstelqByPersonal(String lttoftpstelqByPersonal) {
		this.lttoftpstelqByPersonal = lttoftpstelqByPersonal;
	}

	public String getOcniqByPersonal() {
		return ocniqByPersonal;
	}

	public void setOcniqByPersonal(String ocniqByPersonal) {
		this.ocniqByPersonal = ocniqByPersonal;
	}

	public String getEnterpriseCreditReport() {
		return enterpriseCreditReport;
	}

	public void setEnterpriseCreditReport(String enterpriseCreditReport) {
		this.enterpriseCreditReport = enterpriseCreditReport;
	}

	public String getNcriByEnterprise() {
		return ncriByEnterprise;
	}

	public void setNcriByEnterprise(String ncriByEnterprise) {
		this.ncriByEnterprise = ncriByEnterprise;
	}

	public String getLttoftpstelqByEnterprise() {
		return lttoftpstelqByEnterprise;
	}

	public void setLttoftpstelqByEnterprise(String lttoftpstelqByEnterprise) {
		this.lttoftpstelqByEnterprise = lttoftpstelqByEnterprise;
	}

	public String getOcniqByEnterprise() {
		return ocniqByEnterprise;
	}

	public void setOcniqByEnterprise(String ocniqByEnterprise) {
		this.ocniqByEnterprise = ocniqByEnterprise;
	}

	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}

	public String getGuaranteesMeasures() {
		return guaranteesMeasures;
	}

	public void setGuaranteesMeasures(String guaranteesMeasures) {
		this.guaranteesMeasures = guaranteesMeasures;
	}

	public String getLoanPurpose() {
		return loanPurpose;
	}

	public void setLoanPurpose(String loanPurpose) {
		this.loanPurpose = loanPurpose;
	}

	public String getOperatingCashIncome() {
		return operatingCashIncome;
	}

	public void setOperatingCashIncome(String operatingCashIncome) {
		this.operatingCashIncome = operatingCashIncome;
	}

	public String getNetEarnings() {
		return netEarnings;
	}

	public void setNetEarnings(String netEarnings) {
		this.netEarnings = netEarnings;
	}

	public String getSinkingFund() {
		return sinkingFund;
	}

	public void setSinkingFund(String sinkingFund) {
		this.sinkingFund = sinkingFund;
	}

	public String getDebtRevenue() {
		return debtRevenue;
	}

	public void setDebtRevenue(String debtRevenue) {
		this.debtRevenue = debtRevenue;
	}

	public String getAssetsRealization() {
		return assetsRealization;
	}

	public void setAssetsRealization(String assetsRealization) {
		this.assetsRealization = assetsRealization;
	}

	public String getExternalSupport() {
		return externalSupport;
	}

	public void setExternalSupport(String externalSupport) {
		this.externalSupport = externalSupport;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	
	
	public String getOtherDescription() {
		return otherDescription;
	}

	public void setOtherDescription(String otherDescription) {
		this.otherDescription = otherDescription;
	}

	@Override
	public String toString() {
		return "Enterprise [loanId=" + loanId + ", establishTime="
				+ establishTime + ", businessScope=" + businessScope
				+ ", registeredCapital=" + registeredCapital + ", inshold="
				+ inshold + ", operatingPeriod=" + operatingPeriod
				+ ", paidInCapital=" + paidInCapital
				+ ", affiliatedEnterprise=" + affiliatedEnterprise
				+ ", companyIntroduction=" + companyIntroduction
				+ ", customerName=" + customerName + ", customerGender="
				+ customerGender + ", customerAge=" + customerAge
				+ ", registeredPermanentResidence="
				+ registeredPermanentResidence + ", maritalStatus="
				+ maritalStatus + ", educationalStatus=" + educationalStatus
				+ ", locaResidenceTime=" + locaResidenceTime
				+ ", housingConditions=" + housingConditions
				+ ", productionAddress=" + productionAddress + ", debtHistory="
				+ debtHistory + ", financialStatus=" + financialStatus
				+ ", organizationStructure=" + organizationStructure
				+ ", numberEmployees=" + numberEmployees
				+ ", specializedProductsAndServices="
				+ specializedProductsAndServices + ", financialData="
				+ financialData + ", solvencyAnalysis=" + solvencyAnalysis
				+ ", profitabilityAnalysis=" + profitabilityAnalysis
				+ ", capabilitiesAnalysis=" + capabilitiesAnalysis
				+ ", developmentAbilityAnalysis=" + developmentAbilityAnalysis
				+ ", personalCreditReport=" + personalCreditReport
				+ ", ncriByPersonal=" + ncriByPersonal
				+ ", lttoftpstelqByPersonal=" + lttoftpstelqByPersonal
				+ ", ocniqByPersonal=" + ocniqByPersonal
				+ ", enterpriseCreditReport=" + enterpriseCreditReport
				+ ", ncriByEnterprise=" + ncriByEnterprise
				+ ", lttoftpstelqByEnterprise=" + lttoftpstelqByEnterprise
				+ ", ocniqByEnterprise=" + ocniqByEnterprise + ", api=" + api
				+ ", guaranteesMeasures=" + guaranteesMeasures
				+ ", loanPurpose=" + loanPurpose + ", operatingCashIncome="
				+ operatingCashIncome + ", netEarnings=" + netEarnings
				+ ", sinkingFund=" + sinkingFund + ", debtRevenue="
				+ debtRevenue + ", assetsRealization=" + assetsRealization
				+ ", externalSupport=" + externalSupport
				+ ", otherDescription=" + otherDescription + "]";
	}

	

}
