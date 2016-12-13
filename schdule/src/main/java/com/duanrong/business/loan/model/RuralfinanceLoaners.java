package com.duanrong.business.loan.model;

import java.util.Date;

import base.model.BaseModel;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : YUMIN
 * @CreateTime : 2015-12-03 上午15:01:07
 * @Description : com.duanrong.business.loan.model RuralfinanceLoaners.java 金农宝借款人实体类
 */
public class RuralfinanceLoaners extends BaseModel {
	
	private static final long serialVersionUID = 1019246055375821379L;
	
	// id 主键 UID
	private String id;
	
	/**
	 * 项目id
	 */
	private String loanId;
	
	/**
	 * 借款人编号（1,2）
	 */
	private int loanerId;
	/**
	 * 借款人姓名
	 */
	private String loanerName;


	/**
	 * 性别
	 */
	private String gender;
	/**
	 * 年龄
	 */
	private String age;
	/**
	 * 婚姻状况
	 */
	private String maritalStatus;
	/**
	 * 所在地区
	 */
	private String area;
	/**
	 * 从事行业
	 */
	private String industry;
	/**
	 * 年收入
	 */
	private String annualIncome;

	/**
	 * 房产情况
	 */
	private String realEstateSituation;

	/**
	 * 车辆信息
	 */
	private String vehicleSituation;
	/**
	 * 借款金额
	 */
	private Double money;
	/**
	 * 身份证号码
	 */
	private String idCard;
	/**
	 * 状态
	 */
	private String status;
	
	/**
	 * 更新时间
	 */
	private Date lastAlterTime;
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the loanId
	 */
	public String getLoanId() {
		return loanId;
	}
	
	/**
	 * @param loanId the loanId to set
	 */
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	/**
	 * @return the loanerId
	 */
	public Integer getLoanerId() {
		return loanerId;
	}
	/**
	 * @param loanerId the loanerId to set
	 */
	public void setLoanerId(Integer loanerId) {
		this.loanerId = loanerId;
	}
	/**
	 * @return the loanerName
	 */
	public String getLoanerName() {
		return loanerName;
	}
	/**
	 * @param loanerName the loanerName to set
	 */
	public void setLoanerName(String loanerName) {
		this.loanerName = loanerName;
	}
	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}
	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	/**
	 * @return the age
	 */
	public String getAge() {
		return age;
	}
	/**
	 * @param age the age to set
	 */
	public void setAge(String age) {
		this.age = age;
	}
	/**
	 * @return the maritalStatus
	 */
	public String getMaritalStatus() {
		return maritalStatus;
	}
	/**
	 * @param maritalStatus the maritalStatus to set
	 */
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}
	/**
	 * @param area the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}
	/**
	 * @return the industry
	 */
	public String getIndustry() {
		return industry;
	}
	/**
	 * @param industry the industry to set
	 */
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	/**
	 * @return the annualIncome
	 */
	public String getAnnualIncome() {
		return annualIncome;
	}
	/**
	 * @param annualIncome the annualIncome to set
	 */
	public void setAnnualIncome(String annualIncome) {
		this.annualIncome = annualIncome;
	}
	/**
	 * @return the realEstateSituation
	 */
	public String getRealEstateSituation() {
		return realEstateSituation;
	}
	/**
	 * @param realEstateSituation the realEstateSituation to set
	 */
	public void setRealEstateSituation(String realEstateSituation) {
		this.realEstateSituation = realEstateSituation;
	}
	/**
	 * @return the vehicleSituation
	 */
	public String getVehicleSituation() {
		return vehicleSituation;
	}
	/**
	 * @param vehicleSituation the vehicleSituation to set
	 */
	public void setVehicleSituation(String vehicleSituation) {
		this.vehicleSituation = vehicleSituation;
	}
	/**
	 * @return the money
	 */
	public Double getMoney() {
		return money;
	}
	/**
	 * @param money the money to set
	 */
	public void setMoney(Double money) {
		this.money = money;
	}
	/**
	 * @return the idCard
	 */
	public String getIdCard() {
		return idCard;
	}
	/**
	 * @param idCard the idCard to set
	 */
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the lastAlterTime
	 */
	public Date getLastAlterTime() {
		return lastAlterTime;
	}
	/**
	 * @param lastAlterTime the lastAlterTime to set
	 */
	public void setLastAlterTime(Date lastAlterTime) {
		this.lastAlterTime = lastAlterTime;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RuralfinanceLoaners [id=" + id + ", loanId=" + loanId
				+ ", loanerId=" + loanerId + ", loanerName=" + loanerName
				+ ", gender=" + gender + ", age=" + age + ", maritalStatus="
				+ maritalStatus + ", area=" + area + ", industry=" + industry
				+ ", annualIncome=" + annualIncome + ", realEstateSituation="
				+ realEstateSituation + ", vehicleSituation="
				+ vehicleSituation + ", money=" + money + ", idCard=" + idCard
				+ ", status=" + status + ", lastAlterTime=" + lastAlterTime
				+ "]";
	}
	
}
