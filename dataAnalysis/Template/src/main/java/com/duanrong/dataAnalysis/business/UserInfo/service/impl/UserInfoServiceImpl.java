package com.duanrong.dataAnalysis.business.UserInfo.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import base.pagehelper.PageInfo;

import com.duanrong.dataAnalysis.business.CouponByPerson.dao.CouponByPersonDao;
import com.duanrong.dataAnalysis.business.UserInfo.dao.UserInfoDao;
import com.duanrong.dataAnalysis.business.UserInfo.model.ComeBack;
import com.duanrong.dataAnalysis.business.UserInfo.model.UserInfo;
import com.duanrong.dataAnalysis.business.UserInfo.model.UserSourceData;
import com.duanrong.dataAnalysis.business.UserInfo.service.UserInfoService;
@Service
public class UserInfoServiceImpl implements UserInfoService {
@Resource
private UserInfoDao userInfoDao;
	@Override
	public PageInfo<UserInfo> pageLite4Map(int pageNo,int pageSize,String userSource,
			String liveInvestMin, String liveInvestMax,
			String regularInvestMin, String regularInvestMax,
			String minRedPackCount, String maxRedPackCount,
			String minRateCount, String maxRateCount, String minInvestMoney,
			String maxInvestMoney, String real,String registerTimeBegin, String registerTimeEnd1) {
		
		String registerTimeEnd=registerTimeEnd1+=" 23:59";
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("userSource", userSource);
		params.put("liveInvestMin", liveInvestMin);
		params.put("liveInvestMax", liveInvestMax);
		params.put("regularInvestMin", regularInvestMin);
		params.put("regularInvestMax", regularInvestMax);
		params.put("minRedPackCount", minRedPackCount);
		params.put("maxRedPackCount", maxRedPackCount);
		params.put("minRateCount", minRateCount);
		params.put("maxRateCount", maxRateCount);
		params.put("minInvestMoney", minInvestMoney);
		params.put("maxInvestMoney", maxInvestMoney);
		params.put("registerTimeBegin", registerTimeBegin);
		params.put("registerTimeEnd", registerTimeEnd);
		params.put("real", real);
		
		return userInfoDao.pageLite(pageNo, pageSize, params);
				
	}
	/**
	 * 计算账户总资产
	 */
	@Resource	
	private CouponByPersonDao couponByPersonDao;
	@Override
	public double getUserAllMoney(String userSource,String registerTimeBegin,String registerTimeEnd1) {
		String registerTimeEnd=registerTimeEnd1+=" 23:59";
		//总资产
		return userInfoDao.getUserAllMoney(userSource,registerTimeBegin,registerTimeEnd);
	}
	/**
	 * 回访记录
	 */
	@Override
	public List<ComeBack> getComeBack(String id) {
		// TODO Auto-generated method stub
		return userInfoDao.getComeBack(id);
	}
	

	
	
	/**
	 * 注册 投资间隔时间
	 */
	@Override
	public long getFirstInvestTimeToTime(String id, String registerTime) {
		String investTime=userInfoDao.getInvestTime(id);
			if (investTime==null) {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				investTime=formatter.format(new Date());
			}
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
		long invest = 0;
	
		try {
				 invest = df.parse(investTime).getTime();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		long register = 0;
		try {
				 register = df.parse(registerTime).getTime();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		
		long timeToTime=(invest - register) / (1000 * 60 * 60 * 24);
		return timeToTime;
	}
	@Override
	public UserSourceData getUserSourceData(String userSource,String registerTimeBegin,String registerTimeEnd1) {
		String registerTimeEnd=registerTimeEnd1+=" 23:59";
		return userInfoDao.getUserSourceData(userSource,registerTimeBegin,registerTimeEnd);
	}
	@Override
	public double getUserAllMoneyById(String id) {
		
		return userInfoDao.getUserAllMoneyById(id);
	}
	@Override
	// 账户可用余额
	public double getUserUsedMoney(String userSource,String registerTimeBegin,String registerTimeEnd1) {
		// TODO Auto-generated method stub
		String registerTimeEnd=registerTimeEnd1+=" 23:59";
		return userInfoDao.getUserUsedMoney(userSource,registerTimeBegin,registerTimeEnd);
	}

	
}
