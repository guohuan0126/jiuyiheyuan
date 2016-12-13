package com.duanrong.dataAnalysis.business.redPackage.dao;

import java.util.List;

import base.dao.BaseDao;

import com.duanrong.dataAnalysis.business.redPackage.modle.RedPackage;

public interface RedPackageDao extends BaseDao<RedPackage> {

	List<RedPackage> getRedPackageList();

	int getRedCount(String id);

	int getByUserCount(String id);

	int getUsedUserCount(String id);

	int getRedPackageSendedCount(String id);

	int getUnCount(String id);

	int getUnUsedCount(String id);

	int getUsedCount(String id);

	double getPayMoney(String id);

	double getAllPayMoney(String id);




}
