package com.duanrong.dataAnalysis.business.ActiveData.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import base.pagehelper.PageInfo;

import com.duanrong.dataAnalysis.business.ActiveData.dao.ActiveDataDao;
import com.duanrong.dataAnalysis.business.ActiveData.model.ActiveData;
import com.duanrong.dataAnalysis.business.ActiveData.model.ActiveR;
import com.duanrong.dataAnalysis.business.ActiveData.model.UserR;
import com.duanrong.dataAnalysis.business.ActiveData.service.ActiveDataService;


@Service
public class ActiveDataServiceImpl implements ActiveDataService {

	@Resource 
	private ActiveDataDao activeDataDao;
	@Override
	public List<ActiveData> getActiveDataById(String activeId, String userSource) {
		// TODO Auto-generated method stub
		return activeDataDao.getActiveDataById(activeId,userSource);
	}
	@Override
	public PageInfo<ActiveData> pageLite4Map(String activeId,
			 Integer pageNo, Integer pageSize) {
		// TODO Auto-generated method stub
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("activeId", activeId);
		return activeDataDao.pageLite(pageNo, pageSize, params);
	}
	@Override
	public ActiveR getData(String activeId) {
		// TODO Auto-generated method stub
		return activeDataDao.getData(activeId);
	}
	//根据活动id 查询 首次投资 使用该活动的红包的userId
	public List<UserR> getUserR(String activeId,String userSource) {
		// TODO Auto-generated method stub
		return activeDataDao.getUserR(activeId,userSource);
	}
	//根据用户id 查询该用户投资金额
	public double getActiveDataService(String userId) {
		// TODO Auto-generated method stub
		return activeDataDao.getActiveDataService(userId);
	}
	@Override
	public int getSendCount(String activeId, double money) {

		return activeDataDao.getSendCount(activeId,money);
	}
	@Override
	public int getUsedNum(String activeId, double money) {

		return activeDataDao.getUsedNum(activeId,money);
	}
	@Override
	public int getUnusedCount(String activeId, double money) {

		return activeDataDao.getUnusedCount(activeId,money);
	}
	@Override
	public int getExpiredCount(String activeId, double money) {
		
		return activeDataDao.getExpiredCount(activeId,money);
	}
	@Override
	public double getUserInvestMoney(String activeId, double money) {
		// TODO Auto-generated method stub
		return activeDataDao.getUserInvestMoney(activeId,money);
	}

}
