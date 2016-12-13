package com.duanrong.business.paymentInstitution.model;

import java.util.Date;

public class PaymentCompany {

	private String id;
	private String abbreviation;
	private String name;
	private String imgPc;
	private String imgApp;
	private int availablePc;
	private int availableApp;
	private int sort;
	private Date createTime;
	//PC费率
	private double ratePc;
	//Moblie费率
	private double rateMobile;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAbbreviation() {
		return abbreviation;
	}
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImgPc() {
		return imgPc;
	}
	public void setImgPc(String imgPc) {
		this.imgPc = imgPc;
	}
	public String getImgApp() {
		return imgApp;
	}
	public void setImgApp(String imgApp) {
		this.imgApp = imgApp;
	}
	public int getAvailablePc() {
		return availablePc;
	}
	public void setAvailablePc(int availablePc) {
		this.availablePc = availablePc;
	}
	public int getAvailableApp() {
		return availableApp;
	}
	public void setAvailableApp(int availableApp) {
		this.availableApp = availableApp;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public double getRatePc() {
		return ratePc;
	}
	public void setRatePc(double ratePc) {
		this.ratePc = ratePc;
	}
	public double getRateMobile() {
		return rateMobile;
	}
	public void setRateMobile(double rateMobile) {
		this.rateMobile = rateMobile;
	}
	
}
