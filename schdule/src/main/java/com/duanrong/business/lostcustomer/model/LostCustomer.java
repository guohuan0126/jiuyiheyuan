package com.duanrong.business.lostcustomer.model;

import java.util.Date;

import base.model.BaseModel;

/**
 * @author 郭欢
 * @CreateDate: Oct 20, 2016
 */
public class LostCustomer extends BaseModel{

	private static final long serialVersionUID = 1L;
	//用户来源
	private int id ;
	//用户id
	private String userId ;
	//流失状态
	private int status ;
	//激活客服
	private String customerService ;
	//激活时间
	private Date activationTime ;
	//回访后首投时间
	private Date returnInvestTime ;
	//回访后定期投资总额
	private Double returnTotalInvest ;
	//回访后活期投资总额
	private Double returnTotalDemand ;
	//激活工作评定
	private int evaluation ;
	//创建时间
	private Date createTime ;
	//是否有经理推荐人0:没有，1：有
	private int oreferrerStatus;
	//回访后活期首投时间
	private Date returnDemandTime ;
	//回访后首投时间
	private Date returnTime ;

	public Date getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}

	public Date getReturnDemandTime() {
		return returnDemandTime;
	}

	public void setReturnDemandTime(Date returnDemandTime) {
		this.returnDemandTime = returnDemandTime;
	}

	public int getOreferrerStatus() {
		return oreferrerStatus;
	}

	public void setOreferrerStatus(int oreferrerStatus) {
		this.oreferrerStatus = oreferrerStatus;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getCustomerService() {
		return customerService;
	}
	
	public void setCustomerService(String customerService) {
		this.customerService = customerService;
	}
	
	public Date getActivationTime() {
		return activationTime;
	}
	
	public void setActivationTime(Date activationTime) {
		this.activationTime = activationTime;
	}
	
	public Date getReturnInvestTime() {
		return returnInvestTime;
	}
	
	public void setReturnInvestTime(Date returnInvestTime) {
		this.returnInvestTime = returnInvestTime;
	}
	
	public Double getReturnTotalInvest() {
		return returnTotalInvest;
	}
	
	public void setReturnTotalInvest(Double returnTotalInvest) {
		this.returnTotalInvest = returnTotalInvest;
	}
	
	public Double getReturnTotalDemand() {
		return returnTotalDemand;
	}
	
	public void setReturnTotalDemand(Double returnTotalDemand) {
		this.returnTotalDemand = returnTotalDemand;
	}
	
	public int getEvaluation() {
		return evaluation;
	}
	
	public void setEvaluation(int evaluation) {
		this.evaluation = evaluation;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "LostCustomer [id=" + id + ", userId=" + userId + ", status="
				+ status + ", customerService=" + customerService
				+ ", activationTime=" + activationTime + ", returnInvestTime="
				+ returnInvestTime + ", returnTotalInvest=" + returnTotalInvest
				+ ", returnTotalDemand=" + returnTotalDemand + ", evaluation="
				+ evaluation + ", createTime=" + createTime + "]";
	}
	
}
