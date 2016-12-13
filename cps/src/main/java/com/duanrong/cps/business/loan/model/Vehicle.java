package com.duanrong.cps.business.loan.model;

import java.util.Date;

import base.model.BaseModel;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2014-12-23 上午11:01:07
 * @Description : jdp2p com.esoft.jdp2p.loan.model VehicleBaby.java 车押宝实体类
 */
public class Vehicle extends BaseModel {

	private static final long serialVersionUID = -7978480089646412599L;

	/**
	 * 项目id
	 */
	private String loanId;

	/**
	 * 品牌和型号
	 */
	private String brand;
	/**
	 * 车牌号
	 */
	private String licensePlateNumber;
	/**
	 * 公里数
	 */
	private String kilometreAmount;
	/**
	 * 购买时间
	 */
	private Date buyTime;
	/**
	 * 二手市场价格
	 */
	private String secondHandPrice;
	/**
	 * 评估价格
	 */
	private String assessPrice;
	/**
	 * 抵押方式
	 */
	private String guaranteeType;
	/**
	 * 抵押权人
	 */
	private String mortgagee;
	/**
	 * 抵/质押率
	 */
	private String guaranteeRate;
	/**
	 * 监管方式
	 */
	private String supervisionMode;
	/**
	 * 逾期处理方式
	 */
	private String overdueHandling;

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

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
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

	public Date getBuyTime() {
		return buyTime;
	}

	public void setBuyTime(Date buyTime) {
		this.buyTime = buyTime;
	}

	public String getGuaranteeType() {
		return guaranteeType;
	}

	public void setGuaranteeType(String guaranteeType) {
		this.guaranteeType = guaranteeType;
	}

	public String getMortgagee() {
		return mortgagee;
	}

	public void setMortgagee(String mortgagee) {
		this.mortgagee = mortgagee;
	}

	public String getKilometreAmount() {
		return kilometreAmount;
	}

	public void setKilometreAmount(String kilometreAmount) {
		this.kilometreAmount = kilometreAmount;
	}

	public String getSecondHandPrice() {
		return secondHandPrice;
	}

	public void setSecondHandPrice(String secondHandPrice) {
		this.secondHandPrice = secondHandPrice;
	}

	public String getAssessPrice() {
		return assessPrice;
	}

	public void setAssessPrice(String assessPrice) {
		this.assessPrice = assessPrice;
	}

	public String getGuaranteeRate() {
		return guaranteeRate;
	}

	public void setGuaranteeRate(String guaranteeRate) {
		this.guaranteeRate = guaranteeRate;
	}

	public String getSupervisionMode() {
		return supervisionMode;
	}

	public void setSupervisionMode(String supervisionMode) {
		this.supervisionMode = supervisionMode;
	}

	public String getOverdueHandling() {
		return overdueHandling;
	}

	public void setOverdueHandling(String overdueHandling) {
		this.overdueHandling = overdueHandling;
	}

	public String getOtherDescription() {
		return otherDescription;
	}

	public void setOtherDescription(String otherDescription) {
		this.otherDescription = otherDescription;
	}

	@Override
	public String toString() {
		return "Vehicle [loanId=" + loanId + ", brand=" + brand
				+ ", licensePlateNumber=" + licensePlateNumber
				+ ", kilometreAmount=" + kilometreAmount + ", buyTime="
				+ buyTime + ", secondHandPrice=" + secondHandPrice
				+ ", assessPrice=" + assessPrice + ", guaranteeType="
				+ guaranteeType + ", mortgagee=" + mortgagee
				+ ", guaranteeRate=" + guaranteeRate + ", supervisionMode="
				+ supervisionMode + ", overdueHandling=" + overdueHandling
				+ ", otherDescription=" + otherDescription + "]";
	}

}
