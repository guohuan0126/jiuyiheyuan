package com.duanrong.business.user.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.business.sms.model.Sms;
import com.duanrong.business.user.dao.UserMoneyDao;
import com.duanrong.business.user.model.User;
import com.duanrong.business.user.model.UserMoney;

/**
 * 
 * @author 尹逊志
 * @date 2014-8-29下午1:42:42
 */
@Repository
public class UserMoneyDaoImpl extends BaseDaoImpl<UserMoney> implements
		UserMoneyDao {

	public UserMoneyDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.user.mapper.UserMoneyMapper");
	}
	
	public Double getMoneyByType(String userId,String type) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("type", type);
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getMoneyByType", param);
	}

	public List<UserMoney> getLastestUserMoney(String userId) {
		return this.getSqlSession().selectList(this.getMapperNameSpace() + ".getLastestUserMoneyByUserId", userId);
	}

	public Double getAwardTotalMoney(String userId) {
		return this.getSqlSession().selectOne(this.getMapperNameSpace() + ".getAwardTotalMoney", userId);
	}

	public List<UserMoney> getAwardDetail(String userId) {
		return this.getSqlSession().selectList(this.getMapperNameSpace() + ".getAwardDetail", userId);
	}

	@Override
	public PageInfo<UserMoney> pageInfo(int pageNo, int pageSize, Map map) {
		PageHelper.startPage(pageNo, pageSize);
		List<UserMoney> list = getSqlSession().selectList(
				getMapperNameSpace() + ".pageInfo", map);
		PageInfo<UserMoney> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public List<User> getBorrowerUserId() {
		
		return this.getSqlSession().selectList("getBorrowerUserId");
	}

	@Override
	public double getInvestMoney(String userId, String start, String end) {
		Map<String, Object> params=new HashMap<>();
		params.put("userId", userId);
		params.put("start", start);
		params.put("end", end);
		return this.getSqlSession().selectOne("getMoney", params);
	}

	@Override
	public double getInvestingMoney(String userId, String start, String end) {
		Map<String, Object> params=new HashMap<>();
		params.put("userId", userId);
		params.put("start", start);
		params.put("end", end);
		return this.getSqlSession().selectOne("getNowMoney", params);
	}

	@Override
	public double readUsedMoeny(String user_Id, String time) {
		
		Map<Object, Object> params=new HashMap<>();
		params.put("userId", user_Id);
		params.put("time", time);
		params.put("money", 0);
		params.put("frozen", 0);
		this.getSqlSession().selectOne("readBalanceMoney", params);
		return (double) params.get("money");
	}
	
	@Override
	public Map<String, Object> getBanlance(String userId) {
		
		Map<String, Object> params=new HashMap<>();
		params.put("userId", userId);
		params.put("banlance", 0D);
		params.put("frozen", 0D);
		this.getSqlSession().selectOne("getBanlance", params);
		return params;
	}

	@Override
	public String getPayMnetIdBybankByUser(String cardNo, String userid) {
		Map<String, Object> params=new HashMap<>();
		params.put("cardNo", cardNo);
		params.put("userid", userid);
		return this.getSqlSession().selectOne("getPayMnetIdBybankByUser", params);
	}

}
