package com.duanrong.dataAnalysis.business.ActiveData.dao;

import java.util.List;

import base.dao.BaseDao;

import com.duanrong.dataAnalysis.business.ActiveData.model.ActiveData;
import com.duanrong.dataAnalysis.business.ActiveData.model.ActiveR;
import com.duanrong.dataAnalysis.business.ActiveData.model.UserR;

public interface ActiveDataDao extends BaseDao<ActiveData> {

	List<ActiveData> getActiveDataById(String activeId, String userSource);

	ActiveR getData(String activeId);

	List<UserR> getUserR(String activeId, String userSource);

	double getActiveDataService(String userId);

	int getSendCount(String activeId, double money);

	int getUsedNum(String activeId, double money);

	int getUnusedCount(String activeId, double money);

	int getExpiredCount(String activeId, double money);

	double getUserInvestMoney(String activeId, double money);

}
