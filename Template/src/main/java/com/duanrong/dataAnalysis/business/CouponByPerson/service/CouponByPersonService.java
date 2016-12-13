package com.duanrong.dataAnalysis.business.CouponByPerson.service;

import java.util.List;

import base.pagehelper.PageInfo;

import com.duanrong.dataAnalysis.business.CouponByPerson.model.CouponByPerson;
import com.duanrong.dataAnalysis.business.CouponByPerson.model.UserInfo;

public interface CouponByPersonService {

	List<CouponByPerson> getRedPackageByUserId(String id, String type,
			String status, String activeId);

	PageInfo<CouponByPerson> pageLite4Map(String id, String type,
			String status, String activeId, Integer pageNo, Integer pageSize,
			String type2);

	UserInfo getRedPackageInfo(String id);

	double getUserMoney(String id);
	//根据用户id查询出使用红包投资金额
	double getInvestMoney(String userId);




}
