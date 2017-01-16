package com.duanrong.drpay.trusteeship.helper.model;

import util.ArithUtil;

/**
 * 交易通用实体
 * @author xiao
 * @datetime 2016年12月17日 下午7:30:09
 */
public class GeneratorTradeJSON extends GeneratorJSON {

	//佣金
	private Double commission;
	
	//关联账户分润明细
	private GeneratorDetailJSON profitDetails;

	public Double getCommission() {
		return commission != null ? ArithUtil.round(commission, 2) : null;
	}

	public void setCommission(Double commission) {
		this.commission = commission;
	}

	public GeneratorDetailJSON getProfitDetails() {
		return profitDetails;
	}

	public void setProfitDetails(GeneratorDetailJSON profitDetails) {
		this.profitDetails = profitDetails;
	}

	@Override
	public String toString() {
		return "GeneratorTradeJson [commission=" + commission
				+ ", profitDetails=" + profitDetails + "]";
	}

	
}
