package com.duanrong.business.trusteeship.model;

import java.util.Date;

import base.model.BaseModel;

/**
 * @Description: 易宝开户成功记录
 * @Author: 林志明
 * @CreateDate: Aug 29, 2014
 */
public class TrusteeshipQuery extends BaseModel {
	private static final long serialVersionUID = -8189198520914248838L;
	
	private double ramount;
	private String ruserNo;
	private Date  rcreateTime;
	private String rstatus;
	private String rpayProduct;
	
	private double wamount;
	private String wuserNo;
	private Date wcreateTime;
	private String wstatus;
	private String wremitStatus;
	
	private String trequestNo;
	private String tbizType;
	private double tamount;
	private String tstatus;
	private String tsubStatus;
	
	private String frequestNo;
	private String fplatformUserNo;
	private double famount;
	private Date fexpired;
	private Date fcreateTime;
	private String fstatus;
	private String type;
	private String description;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getRamount() {
		return ramount;
	}
	public void setRamount(double ramount) {
		this.ramount = ramount;
	}
	public String getRuserNo() {
		return ruserNo;
	}
	public void setRuserNo(String ruserNo) {
		this.ruserNo = ruserNo;
	}
	public Date getRcreateTime() {
		return rcreateTime;
	}
	public void setRcreateTime(Date rcreateTime) {
		this.rcreateTime = rcreateTime;
	}
	public String getRstatus() {
		return rstatus;
	}
	public void setRstatus(String rstatus) {
		this.rstatus = rstatus;
	}
	public String getRpayProduct() {
		return rpayProduct;
	}
	public void setRpayProduct(String rpayProduct) {
		this.rpayProduct = rpayProduct;
	}
	public double getWamount() {
		return wamount;
	}
	public void setWamount(double wamount) {
		this.wamount = wamount;
	}
	public String getWuserNo() {
		return wuserNo;
	}
	public void setWuserNo(String wuserNo) {
		this.wuserNo = wuserNo;
	}
	public Date getWcreateTime() {
		return wcreateTime;
	}
	public void setWcreateTime(Date wcreateTime) {
		this.wcreateTime = wcreateTime;
	}
	public String getWstatus() {
		return wstatus;
	}
	public void setWstatus(String wstatus) {
		this.wstatus = wstatus;
	}
	public String getWremitStatus() {
		return wremitStatus;
	}
	public void setWremitStatus(String wremitStatus) {
		this.wremitStatus = wremitStatus;
	}
	public String getTrequestNo() {
		return trequestNo;
	}
	public void setTrequestNo(String trequestNo) {
		this.trequestNo = trequestNo;
	}
	public String getTbizType() {
		return tbizType;
	}
	public void setTbizType(String tbizType) {
		this.tbizType = tbizType;
	}
	public double getTamount() {
		return tamount;
	}
	public void setTamount(double tamount) {
		this.tamount = tamount;
	}
	public String getTstatus() {
		return tstatus;
	}
	public void setTstatus(String tstatus) {
		this.tstatus = tstatus;
	}
	public String getTsubStatus() {
		return tsubStatus;
	}
	public void setTsubStatus(String tsubStatus) {
		this.tsubStatus = tsubStatus;
	}
	public String getFrequestNo() {
		return frequestNo;
	}
	public void setFrequestNo(String frequestNo) {
		this.frequestNo = frequestNo;
	}
	public String getFplatformUserNo() {
		return fplatformUserNo;
	}
	public void setFplatformUserNo(String fplatformUserNo) {
		this.fplatformUserNo = fplatformUserNo;
	}
	public double getFamount() {
		return famount;
	}
	public void setFamount(double famount) {
		this.famount = famount;
	}
	public Date getFexpired() {
		return fexpired;
	}
	public void setFexpired(Date fexpired) {
		this.fexpired = fexpired;
	}
	public Date getFcreateTime() {
		return fcreateTime;
	}
	public void setFcreateTime(Date fcreateTime) {
		this.fcreateTime = fcreateTime;
	}
	public String getFstatus() {
		return fstatus;
	}
	public void setFstatus(String fstatus) {
		this.fstatus = fstatus;
	}
	
	
	
	
	
	
	
	
}
