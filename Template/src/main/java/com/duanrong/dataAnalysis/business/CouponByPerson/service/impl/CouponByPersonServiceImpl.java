package com.duanrong.dataAnalysis.business.CouponByPerson.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import base.pagehelper.PageInfo;

import com.duanrong.dataAnalysis.business.CouponByPerson.dao.CouponByPersonDao;
import com.duanrong.dataAnalysis.business.CouponByPerson.model.CouponByPerson;
import com.duanrong.dataAnalysis.business.CouponByPerson.model.UserInfo;
import com.duanrong.dataAnalysis.business.CouponByPerson.service.CouponByPersonService;
@Service
public class CouponByPersonServiceImpl implements CouponByPersonService {

	@Resource 
	CouponByPersonDao couponByPersonDao;

	@Override
	public List<CouponByPerson> getRedPackageByUserId(String id, String type,
			String status, String activeId) {
		// TODO Auto-generated method stub
		if (status.equals("1")) {
			String status1="money";
			
			return couponByPersonDao.getRedPackageByUserId(id,type,status1,activeId);
		
		} else {
			String status1="rate";
			return couponByPersonDao.getRedPackageByUserId(id,type,status1,activeId);
		}
		
	}

	@Override
	public PageInfo<CouponByPerson> pageLite4Map(String id, String type,
			String status, String activeId, Integer pageNo, Integer pageSize,
			String type2) {
		if (status.equals("1")) {
			String status1="money";
			Map<String, Object> params = new HashMap<>();
			params.put("id", id);
			params.put("type", type);
			params.put("status", status1);
			params.put("activeId", activeId);
			return couponByPersonDao.pageLite(pageNo, pageSize, params);
		
		} else {
			String status1="rate";
			Map<String, Object> params = new HashMap<>();
			params.put("id", id);
			params.put("type", type);
			params.put("status", status1);
			params.put("activeId", activeId);
			return couponByPersonDao.pageLite(pageNo, pageSize, params);
		}
	}

	@Override
	public UserInfo getRedPackageInfo(String id) {
		
		return couponByPersonDao.getRedPackageInfo(id);
	}

	//拿到账户余额
	@Override
	public double getUserMoney(String id) {
		// TODO Auto-generated method stub
		return couponByPersonDao.getUserMoney(id);
	}

	@Override
	public double getInvestMoney(String userId) {
		
		return couponByPersonDao.getInvestMoney(userId);
	}


	
}
