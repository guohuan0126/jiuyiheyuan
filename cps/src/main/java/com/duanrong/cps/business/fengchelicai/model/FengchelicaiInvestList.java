package com.duanrong.cps.business.fengchelicai.model;

import java.math.BigDecimal;
import java.util.Date;

public class FengchelicaiInvestList {

	private String investUser;
	
	private BigDecimal investMoney;
	
	private String investTime;

	public String getInvestUser() {
		return investUser;
	}

	public void setInvestUser(String investUser) {
		this.investUser = investUser;
	}

	public BigDecimal getInvestMoney() {
		return investMoney;
	}

	public void setInvestMoney(BigDecimal investMoney) {
		this.investMoney = investMoney;
	}

	public String getInvestTime() {
		return investTime;
	}

	public void setInvestTime(String investTime) {
		this.investTime = investTime;
	}
	
	
}
