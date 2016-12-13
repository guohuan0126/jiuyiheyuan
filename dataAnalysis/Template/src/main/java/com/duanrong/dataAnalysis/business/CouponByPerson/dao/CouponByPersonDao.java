package com.duanrong.dataAnalysis.business.CouponByPerson.dao;

import java.util.List;

import base.dao.BaseDao;

import com.duanrong.dataAnalysis.business.CouponByPerson.model.CouponByPerson;
import com.duanrong.dataAnalysis.business.CouponByPerson.model.UserInfo;

public interface CouponByPersonDao extends BaseDao<CouponByPerson> {

	List<CouponByPerson> getRedPackageByUserId(String id, String type,String status, String activeId);

	UserInfo getRedPackageInfo(String id);

	double getUserMoney(String id);

	double getInvestMoney(String userId);

}
