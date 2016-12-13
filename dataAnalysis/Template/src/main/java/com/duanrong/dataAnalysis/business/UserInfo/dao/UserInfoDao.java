package com.duanrong.dataAnalysis.business.UserInfo.dao;

import java.util.Date;
import java.util.List;

import com.duanrong.dataAnalysis.business.UserInfo.model.ComeBack;
import com.duanrong.dataAnalysis.business.UserInfo.model.UserInfo;
import com.duanrong.dataAnalysis.business.UserInfo.model.UserSourceData;

import base.dao.BaseDao;



public interface UserInfoDao extends BaseDao<UserInfo> {

	double getLiveMoney(String id);

	double getInvestIngMoney(String id);

	double getNotUsedMoeny(String id);

	List<ComeBack> getComeBack(String id);

	String getInvestTime(String id);

	UserSourceData getUserSourceData(String userSource,String registerTimeEnd,String registerTimeBegin);

	double getUserAllMoneyById(String id);

	double getUserAllMoney(String userSource,String registerTimeEnd,String registerTimeBegin);

	double getUserUsedMoney(String userSource,String registerTimeEnd,String registerTimeBegin);




	
}
