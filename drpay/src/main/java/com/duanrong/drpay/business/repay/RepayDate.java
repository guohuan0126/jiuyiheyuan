package com.duanrong.drpay.business.repay;

import java.util.Date;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2015-3-12 下午4:36:40
 * @Description : NewAdmin com.duanrong.business.loan.model RepayDate.java
 * 
 */
public class RepayDate {
	private Date repayDate;

	public Date getRepayDate() {
		return repayDate;
	}

	public void setRepayDate(Date repayDate) {
		this.repayDate = repayDate;
	}

	private int DayAmount;

	public int getDayAmount() {
		return DayAmount;
	}

	public void setDayAmount(int dayAmount) {
		DayAmount = dayAmount;
	}
}
