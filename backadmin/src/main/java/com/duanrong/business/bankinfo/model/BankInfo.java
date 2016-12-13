package com.duanrong.business.bankinfo.model;
import base.model.BaseModel;

/**
 * 银行卡信息管理
 * @author YUMIN
 *
 */


public class BankInfo extends BaseModel {
	
   /**
	 * 
	 */
	private static final long serialVersionUID = -4607890797312609155L;
//银行id，UUid
	private String id;
	// 银行中文名称
	private String name;
	// 银行名称
	private String bank;
	// 限额描述
	private String rechargeDesc;
	// 排序号
	private Integer sortNo;
	// 银行卡logo图片地址
	private String url;
	// 是否禁用银行卡
	private  Integer whetherDelete;
	// 支付通道（易宝，京东）
	private String paymentId;
	//是否支持网银
	private String onlineBank;
	//是否支持绑卡
	private String tiecard;
	//是否支持快捷充值
	private String quickRecharge;
	
	/**
	 * @return the onlineBank
	 */
	public String getOnlineBank() {
		return onlineBank;
	}
	/**
	 * @param onlineBank the onlineBank to set
	 */
	public void setOnlineBank(String onlineBank) {
		this.onlineBank = onlineBank;
	}
	/**
	 * @return the tiecard
	 */
	public String getTiecard() {
		return tiecard;
	}
	/**
	 * @param tiecard the tiecard to set
	 */
	public void setTiecard(String tiecard) {
		this.tiecard = tiecard;
	}
	/**
	 * @return the quickRecharge
	 */
	public String getQuickRecharge() {
		return quickRecharge;
	}
	/**
	 * @param quickRecharge the quickRecharge to set
	 */
	public void setQuickRecharge(String quickRecharge) {
		this.quickRecharge = quickRecharge;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the bank
	 */
	public String getBank() {
		return bank;
	}
	/**
	 * @param bank the bank to set
	 */
	public void setBank(String bank) {
		this.bank = bank;
	}
	/**
	 * @return the rechargeDesc
	 */
	public String getRechargeDesc() {
		return rechargeDesc;
	}
	/**
	 * @param rechargeDesc the rechargeDesc to set
	 */
	public void setRechargeDesc(String rechargeDesc) {
		this.rechargeDesc = rechargeDesc;
	}
	/**
	 * @return the sortNo
	 */
	public Integer getSortNo() {
		return sortNo;
	}
	/**
	 * @param sortNo the sortNo to set
	 */
	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the whetherDelete
	 */
	public Integer getWhetherDelete() {
		return whetherDelete;
	}
	/**
	 * @param whetherDelete the whetherDelete to set
	 */
	public void setWhetherDelete(Integer whetherDelete) {
		this.whetherDelete = whetherDelete;
	}
	/**
	 * @return the paymentId
	 */
	public String getPaymentId() {
		return paymentId;
	}
	/**
	 * @param paymentId the paymentId to set
	 */
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BankInfo [id=" + id + ", name=" + name + ", bank=" + bank
				+ ", rechargeDesc=" + rechargeDesc + ", sortNo=" + sortNo
				+ ", url=" + url + ", whetherDelete=" + whetherDelete
				+ ", paymentId=" + paymentId + ", onlineBank=" + onlineBank
				+ ", tiecard=" + tiecard + ", quickRecharge=" + quickRecharge
				+ "]";
	}
	

	

}
