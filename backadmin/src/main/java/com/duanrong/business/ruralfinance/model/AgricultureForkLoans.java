package com.duanrong.business.ruralfinance.model;

import java.util.Date;
/**
 * 子标 
 * @author bj
 *
 */
public class AgricultureForkLoans {
	
   //uuid 主键
	private String id;
	//agriculture_loaninfo.id外键
	private String parentId;
	//子合同的编号：规则是主合同编号+拆分的期限
	private String childContractid;
	//项目id
	private String  loanId;
	//金额
	private double money;
	//期限(月)
	private Integer loanTerm;
	//是否已经打包：0未打包，1已经打包
	private Integer packing;
	//创建时间
	private Date createTime;
	//备注信息
	private String remark;

	private AgricultureLoaninfo loaninfo;

	//借款基本  v信息
	private String name;
	private String mobileNumber;
	private String idCard;
	//合同金额
	private double contractMoney;
	//总借款金额
	private double loanMoney;
	//总合同编号
	private String contractId;
	
	//总期限
	private String parentLoanTerm;
	//银行卡号
	private String bankCard;
	//标示位
	private String flag;
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getBankCard() {
		return bankCard;
	}
	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public double getContractMoney() {
		return contractMoney;
	}
	public void setContractMoney(double contractMoney) {
		this.contractMoney = contractMoney;
	}
	public double getLoanMoney() {
		return loanMoney;
	}
	public void setLoanMoney(double loanMoney) {
		this.loanMoney = loanMoney;
	}
	public String getParentLoanTerm() {
		return parentLoanTerm;
	}
	public void setParentLoanTerm(String parentLoanTerm) {
		this.parentLoanTerm = parentLoanTerm;
	}

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
	/**
	 * @return the childContractid
	 */
	public String getChildContractid() {
		return childContractid;
	}
	/**
	 * @param childContractid the childContractid to set
	 */
	public void setChildContractid(String childContractid) {
		this.childContractid = childContractid;
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
	 * @return the money
	 */
	public double getMoney() {
		return money;
	}
	/**
	 * @param money the money to set
	 */
	public void setMoney(double money) {
		this.money = money;
	}
	/**
	 * @return the loanTerm
	 */
	public Integer getLoanTerm() {
		return loanTerm;
	}
	/**
	 * @param loanTerm the loanTerm to set
	 */
	public void setLoanTerm(Integer loanTerm) {
		this.loanTerm = loanTerm;
	}
	/**
	 * @return the packing
	 */
	public Integer getPacking() {
		return packing;
	}
	/**
	 * @param packing the packing to set
	 */
	public void setPacking(Integer packing) {
		this.packing = packing;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	/**
	 * @return the loaninfo
	 */
	public AgricultureLoaninfo getLoaninfo() {
		return loaninfo;
	}
	/**
	 * @param loaninfo the loaninfo to set
	 */
	public void setLoaninfo(AgricultureLoaninfo loaninfo) {
		this.loaninfo = loaninfo;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "agricultureForkLoans [id=" + id + ", parentId=" + parentId
				+ ", childContractid=" + childContractid + ", loanId=" + loanId
				+ ", money=" + money + ", loanTerm=" + loanTerm + ", packing="
				+ packing + ", remark="
				+ remark + "]";
	}
	
	

}
