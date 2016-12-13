package com.duanrong.dataAnalysis.business.cashData.service;

import java.util.List;

import com.duanrong.dataAnalysis.business.cashData.model.ResCashData;
import com.duanrong.dataAnalysis.business.cashData.model.cashData;
import com.duanrong.dataAnalysis.business.cashData.model.payAndCashData;

public interface cashDataService {

	cashData getCashDataT();
	//用户充值
	List<ResCashData> getPayData(String beginTime, String endTime);
	//用户提现
	List<ResCashData> getDrawData(String beginTime, String endTime);
	//邮件 充值提现
	payAndCashData getDataByMail();
	
}
