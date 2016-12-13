package com.duanrong.dataAnalysis.business.redPackage.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import base.pagehelper.PageInfo;

import com.duanrong.dataAnalysis.business.redPackage.service.RedPackageService;
import com.duanrong.dataAnalysis.business.redPackage.dao.RedPackageDao;
import com.duanrong.dataAnalysis.business.redPackage.modle.RedPackage;
/**
 * 分别查询各项数据 并封装到一个model中 返回 controller
 * @author bj
 *
 */
@Service
public class RedPackageServiceImpl implements RedPackageService {
@Resource
private RedPackageDao redPackageDao;

	@Override
	public PageInfo<RedPackage> pageLite4Map(int pageNo, int pageSize, String type) {
		Map<String, Object> params = new HashMap<>();
		params.put("type", type);
		return redPackageDao.pageLite(pageNo, pageSize, params);
	}

	@Override
	public List<RedPackage> getRedPackageList() {
		List<RedPackage> redPackageList=redPackageDao.getRedPackageList();
		return redPackageList;
	}

	@Override
	public int getCount(String id) {
	
		return redPackageDao.getRedCount(id);
	}

	@Override
	public int getByUserCount(String id) {
		// TODO Auto-generated method stub
		return redPackageDao.getByUserCount(id);
	}

	@Override
	public int getUsedUserCount(String id) {
		// TODO Auto-generated method stub
		return redPackageDao.getUsedUserCount(id);
	}

	@Override
	public int getRedPackageSendedCount(String id) {
		// TODO Auto-generated method stub
		return redPackageDao.getRedPackageSendedCount(id);
	}

	@Override
	public int getUnCount(String id) {
		// TODO Auto-generated method stub
		return redPackageDao.getUnCount(id);
	}

	@Override
	public int getUnUsedCount(String id) {
		// TODO Auto-generated method stub
		return redPackageDao.getUnUsedCount(id);
	}

	@Override
	public int getUsedCount(String id) {
		// TODO Auto-generated method stub
		return redPackageDao.getUsedCount(id);
	}

	@Override
	public double getPayMoney(String id) {
		// TODO Auto-generated method stub
		return redPackageDao.getPayMoney(id);
	}

	@Override
	public double getAllPayMoney(String id) {
		// TODO Auto-generated method stub
		return redPackageDao.getAllPayMoney(id);
	}
	
	
}
