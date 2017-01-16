package com.duanrong.drpay.business.payment.model;

/**
 * 渠道信息
 * 
 */
public class PaymentChannel {

	// id
	private String id;
	// 渠道名称
	private String name;
	// 渠道字母缩写
	private String code;
	// 渠道logo
	private String logo;
	// 普通充值费率
	private Double rateGateway;
	// 快捷充值费率
	private Double rateQuick;
	// 提现手续费
	private Double withdrawFee;
	// 是否支持pc
	private Integer pcUsable;
	// 是否支持Mobile
	private Integer mobUsable;
	// 排序
	private Integer sort;
	//快捷最低手续费quick_min_fee
	private Double quickMinFee;
	//代付手续费dfpay_fee
	private Double dfpayFee;
	
	
	public Double getQuickMinFee() {
		return quickMinFee;
	}

	public void setQuickMinFee(Double quickMinFee) {
		this.quickMinFee = quickMinFee;
	}

	public Double getDfpayFee() {
		return dfpayFee;
	}

	public void setDfpayFee(Double dfpayFee) {
		this.dfpayFee = dfpayFee;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public double getRateGateway() {
		return rateGateway;
	}

	public void setRateGateway(double rateGateway) {
		this.rateGateway = rateGateway;
	}

	public double getRateQuick() {
		return rateQuick;
	}

	public void setRateQuick(double rateQuick) {
		this.rateQuick = rateQuick;
	}

	public double getWithdrawFee() {
		return withdrawFee;
	}

	public void setWithdrawFee(double withdrawFee) {
		this.withdrawFee = withdrawFee;
	}
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public void setRateGateway(Double rateGateway) {
		this.rateGateway = rateGateway;
	}

	public void setRateQuick(Double rateQuick) {
		this.rateQuick = rateQuick;
	}

	public void setWithdrawFee(Double withdrawFee) {
		this.withdrawFee = withdrawFee;
	}

	public Integer getPcUsable() {
		return pcUsable;
	}

	public void setPcUsable(Integer pcUsable) {
		this.pcUsable = pcUsable;
	}

	public Integer getMobUsable() {
		return mobUsable;
	}

	public void setMobUsable(Integer mobUsable) {
		this.mobUsable = mobUsable;
	}

	@Override
	public String toString() {
		return "PaymentChannel [id=" + id + ", name=" + name + ", code=" + code
				+ ", logo=" + logo + ", rateGateway=" + rateGateway
				+ ", rateQuick=" + rateQuick + ", withdrawFee=" + withdrawFee
				+ ", pcUsable=" + pcUsable + ", mobUsable=" + mobUsable
				+ ", sort=" + sort + ", quickMinFee=" + quickMinFee
				+ ", dfpayFee=" + dfpayFee + "]";
	}
	
}