package com.duanrong.cps.business.user.dao;

import java.util.List;
import java.util.Map;

import base.dao.BaseDao;
import base.pagehelper.PageInfo;


import com.duanrong.cps.business.user.model.User;
import com.duanrong.cps.business.user.model.UserMoney;

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
	 * //查询固定借款人userid
	 * @return
	 */
	public List<User> getBorrowerUserId();
	
	/**
	 * 根据时间 userId 查询投资金额
	 * @param userId
	 * @param start
	 * @param end
	 * @return
	 */
	public double getInvestMoney(String userId, String start, String end);
	/**
	 * 根据时间 userId 查询正在投资金额
	 * @param userId
	 * @param start
	 * @param end
	 * @return
	 */
	public double getInvestingMoney(String userId, String start, String end);
	/**
	 * 根据userid查询可用余额
	 * @param user_Id
	 * @param time
	 * @return
	 */
	public double readUsedMoeny(String user_Id, String time);
}
