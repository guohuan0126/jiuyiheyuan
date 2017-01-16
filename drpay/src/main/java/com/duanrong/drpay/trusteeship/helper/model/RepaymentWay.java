package com.duanrong.drpay.trusteeship.helper.model;

/**
 * 还款方式
 * @author xiao
 * @datetime 2016年12月6日 下午4:24:41
 */
public enum RepaymentWay {

	//一次性还本付息
	ONE_TIME_SERVICING,
	
	//先息后本
	FIRSEINTREST_LASTPRICIPAL,
	
	//等额本息
	FIXED_PAYMENT_MORTGAGE,
	
	//等额本金
	FIXED_BASIS_MORTGAGE
}
