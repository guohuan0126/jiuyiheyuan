package com.duanrong.dataAnalysis.business.cashData.dao;

import java.util.List;

import com.duanrong.dataAnalysis.business.cashData.model.ResCashData;
import com.duanrong.dataAnalysis.business.cashData.model.cashData;
import com.duanrong.dataAnalysis.business.cashData.model.payAndCashData;

public interface cashDataDao {

	cashData getCashDataT();
	//充值金额 
	List<ResCashData> getPayData(String beginTime, String endTime);
	//提现金额
	List<ResCashData> getDrawData(String beginTime, String endTime);
	//邮件充值提现
	payAndCashData getDataByMail();
}
