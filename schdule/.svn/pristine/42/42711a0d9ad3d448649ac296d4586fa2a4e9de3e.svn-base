package com.duanrong.business.user.dao;

import java.util.List;
import java.util.Map;

import base.dao.BaseDao;
import base.pagehelper.PageInfo;

import com.duanrong.business.user.model.UserMoney;

/**
 * 
 * @author 尹逊志
 * @date 2014-8-29下午1:41:59
 */
public interface UserMoneyDao extends BaseDao<UserMoney> {

	/**
	 * 根据交易金额类型查询用户资金
	 * 
	 * @param userId
	 * @param type
	 * @return
	 */
	public Double getMoneyByType(String userId, String type);

	/**
	 * 根据用户ID查询用户余额
	 * 
	 * @param userId
	 * @return
	 */
	public List<UserMoney> getLastestUserMoney(String userId);

	/**
	 * 获取用户奖励总额
	 * 
	 * @param userId
	 * @return
	 */
	public Double getAwardTotalMoney(String userId);

	/**
	 * 获得用户奖励详情
	 * 
	 * @param userId
	 * @return
	 */
	public List<UserMoney> getAwardDetail(String userId);
	
	PageInfo<UserMoney> pageInfo(int pageNo, int pageSize, Map map);
	
	/**
	 * 查询用户本地账户信息
	 * @param userId
	 * @return
	 */
	Map<String, Object> queryUserMoney(String userId);
}
