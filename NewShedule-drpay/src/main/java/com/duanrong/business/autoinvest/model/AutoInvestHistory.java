package com.duanrong.business.autoinvest.model;

import java.util.Date;

import base.model.BaseModel;
/**
 * 
 * Copyright : duanrong.com.cn All Rights Reserved
 * Company : 久亿财富（北京）投资有限公司
 * @Author : 孙铮
 * @CreateTime : 2014-10-28 下午5:25:21 
 * @Description : wangkun com.esoft.jdp2p.invest.model AutoInvestHistory.java
 *				自动投标历史表
 */
public class AutoInvestHistory extends BaseModel {	
	
	private static final long serialVersionUID = -3086035153464187151L;
	
	private Integer id; 
	//用户编号
	private String userId;
	//投资金额
	private Double investMoney;
	//时间
	private Date time;
	//排队序号
	private Integer seqNum;
	//项目ID
	private String loanID;
	//描述
	private String detail;
	//自动投标状态
	private String status;
	//自动投标请求流水号
	private String requestNo;
	
	public AutoInvestHistory() {
	}
	
	
	/**
	 * @param userId
	 * @param investMoney
	 * @param time
	 * @param seqNum
	 * @param loanID
	 * @param detail
	 * @param status
	 */
	public AutoInvestHistory(String userId, Double investMoney, Date time,
			Integer seqNum, String loanID, String detail, String status) {
		super();
		this.userId = userId;
		this.investMoney = investMoney;
		this.time = time;
		this.seqNum = seqNum;
		this.loanID = loanID;
		this.detail = detail;
		this.status = status;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public Double getInvestMoney() {
		return investMoney;
	}


	public void setInvestMoney(Double investMoney) {
		this.investMoney = investMoney;
	}


	public Date getTime() {
		return time;
	}


	public void setTime(Date time) {
		this.time = time;
	}


	public Integer getSeqNum() {
		return seqNum;
	}


	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}


	public String getLoanID() {
		return loanID;
	}


	public void setLoanID(String loanID) {
		this.loanID = loanID;
	}


	public String getDetail() {
		return detail;
	}


	public void setDetail(String detail) {
		this.detail = detail;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getRequestNo() {
		return requestNo;
	}


	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	
	
}
