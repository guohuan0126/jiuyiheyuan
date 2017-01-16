package com.duanrong.drpay.business.demand.dao;

import java.util.Date;
import java.util.List;

import com.duanrong.drpay.business.demand.model.DemandTreasureBill;

import base.dao.BaseDao;


/**
 * @author xiao
 * @date 2015年7月20日下午2:13:10
 */
public interface DemandTreasureBillDao extends BaseDao<DemandTreasureBill> {

	/**
	 * 根据类型获取用户活期宝金额
	 * @param userId
	 * @return
	 */
	public double getDemandTreasureMoneyByType(String userId, String type);
		
	/**
	 * 活期宝金额
	 * @param type
	 * @return
	 */
	public double getDemandTreasureMoney(String type);
	
	/**
	 * 获取累计金额
	 * @param userId
	 * @return
	 */
	public double getDemandTreasureInvalidMoney(String userId, String Type, Date date);
	
	/**
	 * 获取活期宝用户Id
	 * @return
	 */
	public List<String> getDemandTreasureUser();
	
	/**
	 * 批量派息
	 * @param list
	 * @return
	 */
	public int insertInterestBatch(List<DemandTreasureBill> list);
	
	/**
	 * 获取用户昨日收益
	 * @param userId
	 * @return
	 */
	public double getDemandLaterInterest(String userId);
	
	/**
	 * 用户转出申请中
	 * @param userId
	 * @return
	 */
	public double getDemandTranferOut(String userId);
}
