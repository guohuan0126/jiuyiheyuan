package com.duanrong.dataAnalysis.business.UserInfo.service;

import java.util.Date;
import java.util.List;

import base.pagehelper.PageInfo;

import com.duanrong.dataAnalysis.business.UserInfo.model.ComeBack;
import com.duanrong.dataAnalysis.business.UserInfo.model.UserInfo;
import com.duanrong.dataAnalysis.business.UserInfo.model.UserSourceData;

public interface UserInfoService {

	PageInfo<UserInfo> pageLite4Map(int pageNo,int pageSize,String userSource, String liveInvestMin,
			String liveInvestMax, String regularInvestMin,
			String regularInvestMax, String minRedPackCount,
			String maxRedPackCount, String minRateCount, String maxRateCount,
			String minInvestMoney, String maxInvestMoney, String real,String registerTimeBegin,String registerTimeEnd);

	double getUserAllMoney(String userSource,String registerTimeBegin,String registerTimeEnd1);

	List<ComeBack> getComeBack(String id);


	long getFirstInvestTimeToTime(String id, String registerTime);

	UserSourceData getUserSourceData(String userSource,String registerTimeBegin,String registerTimeEnd1);

	double getUserAllMoneyById(String id);

	double getUserUsedMoney(String userSource,String registerTimeBegin,String registerTimeEnd1);


}
