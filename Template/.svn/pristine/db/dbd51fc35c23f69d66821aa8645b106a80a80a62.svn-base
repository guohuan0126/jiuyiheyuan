package com.duanrong.dataAnalysis.business.redPackage.service;

import java.util.List;

import base.pagehelper.PageInfo;

import com.duanrong.dataAnalysis.business.redPackage.modle.RedPackage;


public interface RedPackageService {

	List<RedPackage> getRedPackageList();
	
	PageInfo<RedPackage> pageLite4Map(int pageNo, int pageSize, String type);

	int getCount(String id);

	int getByUserCount(String id);

	int getUsedUserCount(String id);

	int getRedPackageSendedCount(String id);

	int getUnCount(String id);

	int getUnUsedCount(String id);

	int getUsedCount(String id);

	double getPayMoney(String id);

	double getAllPayMoney(String id);


}
