package com.duanrong.drpay.trusteeship.helper.model;

import util.ArithUtil;

/**
 * 项目通用实体
 * 
 * @author xiao
 * @datetime 2016年12月7日 下午12:22:47
 */
public class GeneratorLoanJSON extends GeneratorJSON {

	// 项目金额
	private double projectAmount;
	// 项目名称
	private String projectName;
	// 项目类型
	private ProjectType projectType;
	
	//项目期限
	private Integer projectPeriod;
	
	// 年化利率
	private double annnualInterestRate;
	// 还款方式
	private RepaymentWay repaymentWay;
	
	private ProjectStatus status;

	//已放款金额
	private double loanAmount;
	
	//已还款本金
	private double repaymentAmount;
	
	//已还款利息
	private double income;
	//标的属性
	private String projectProperties;
	
	//批量投标计划编号
	private String intelProjectNo;
	//产品名称
	private String intelProjectName;
	//产品描述
	private String intelProjectDescription;
	//预期收益率
	private double annualInterestRate;
	
	public ProjectType getProjectType() {
		return projectType == null ? ProjectType.STANDARDPOWDER : projectType;
	}

	public void setProjectType(ProjectType projectType) {
		this.projectType = projectType;
	}

	
	public String getStatus() {
		return status != null ? status.name() : "";
	}

	public void setStatus(ProjectStatus status) {
		this.status = status;
	}

	public double getProjectAmount() {
		return ArithUtil.round(projectAmount, 2);
	}

	public void setProjectAmount(double projectAmount) {
		this.projectAmount = projectAmount;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Integer getProjectPeriod() {
		return projectPeriod;
	}

	public void setProjectPeriod(Integer projectPeriod) {
		this.projectPeriod = projectPeriod;
	}

	public double getAnnnualInterestRate() {
		return ArithUtil.round(annnualInterestRate, 2);
	}

	public void setAnnnualInterestRate(double annnualInterestRate) {
		this.annnualInterestRate = annnualInterestRate;
	}

	public RepaymentWay getRepaymentWay() {
		return repaymentWay;
	}

	public void setRepaymentWay(RepaymentWay repaymentWay) {
		this.repaymentWay = repaymentWay;
	}

	public double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public double getRepaymentAmount() {
		return repaymentAmount;
	}

	public void setRepaymentAmount(double repaymentAmount) {
		this.repaymentAmount = repaymentAmount;
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
	}

	public String getProjectProperties() {
		return projectProperties;
	}

	public void setProjectProperties(String projectProperties) {
		this.projectProperties = projectProperties;
	}

	public String getIntelProjectNo() {
		return intelProjectNo;
	}

	public void setIntelProjectNo(String intelProjectNo) {
		this.intelProjectNo = intelProjectNo;
	}

	public String getIntelProjectName() {
		return intelProjectName;
	}

	public void setIntelProjectName(String intelProjectName) {
		this.intelProjectName = intelProjectName;
	}

	public String getIntelProjectDescription() {
		return intelProjectDescription;
	}

	public void setIntelProjectDescription(String intelProjectDescription) {
		this.intelProjectDescription = intelProjectDescription;
	}

	public double getAnnualInterestRate() {
		return annualInterestRate;
	}

	public void setAnnualInterestRate(double annualInterestRate) {
		this.annualInterestRate = annualInterestRate;
	}

}
