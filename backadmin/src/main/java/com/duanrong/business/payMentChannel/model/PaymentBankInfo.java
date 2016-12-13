package com.duanrong.business.payMentChannel.model;

public class PaymentBankInfo {

	
	private String id;
	//银行名称
	private String name;
	//银行简称
	private String code;
	//银行logo
	private String logo;
	//富友银行机构码
	private String fuIouCode;
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
	public String getFuIouCode() {
		return fuIouCode;
	}
	public void setFuIouCode(String fuIouCode) {
		this.fuIouCode = fuIouCode;
	}

	
}
