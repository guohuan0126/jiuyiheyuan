package com.duanrong.business.loan.model;

import base.model.BaseModel;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2014-12-23 上午11:02:42
 * @Description : jdp2p com.esoft.jdp2p.loan.babyModel House.java
 * 
 */
public class House extends BaseModel {
	private static final long serialVersionUID = -6524264191558392682L;
	/**
	 * 项目id
	 */
	private String loanId;
	/**
	 * 所处位置
	 */
	private String position;
	/**
	 * 性质
	 */
	private String nature;
	/**
	 * 建筑面积
	 */
	private String area;
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
	 * 逾期处理方式
	 */
	private String overdueHandling;

	/**
	 * 其他说明
	 */
	private String otherDescription;

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
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

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
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
		return "House [loanId=" + loanId + ", position=" + position
				+ ", nature=" + nature + ", area=" + area + ", assessPrice="
				+ assessPrice + ", guaranteeType=" + guaranteeType
				+ ", mortgagee=" + mortgagee + ", guaranteeRate="
				+ guaranteeRate + ", overdueHandling=" + overdueHandling
				+ ", otherDescription=" + otherDescription + "]";
	}

}
