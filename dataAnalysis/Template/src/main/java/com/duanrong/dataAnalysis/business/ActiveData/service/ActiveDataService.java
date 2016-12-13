package com.duanrong.dataAnalysis.business.ActiveData.service;

import java.util.List;

import base.pagehelper.PageInfo;

import com.duanrong.dataAnalysis.business.ActiveData.model.ActiveData;
import com.duanrong.dataAnalysis.business.ActiveData.model.ActiveR;
import com.duanrong.dataAnalysis.business.ActiveData.model.UserR;

public interface ActiveDataService {

	List<ActiveData> getActiveDataById(String activeId,String userSource);

	PageInfo<ActiveData> pageLite4Map(String activeId,
			 Integer pageNo, Integer pageSize);

	ActiveR getData(java.lang.String activeId);

	List<UserR> getUserR(String activeId, String userSource);
	//根据用户id 查询该用户投资金额
	double getActiveDataService(String userId);

	int getSendCount(String activeId, double money);

	int getUsedNum(String activeId, double money);

	int getUnusedCount(String activeId, double money);

	int getExpiredCount(String activeId, double money);

	double getUserInvestMoney(String activeId, double money);

}
