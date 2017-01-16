package com.duanrong.business.demand.service;

import java.util.Date;
import java.util.List;

import base.pagehelper.PageInfo;

import com.duanrong.business.demand.model.DemandTreasureBill;


/**
 * @author xiao
 * @date 2015年7月20日下午2:37:08
 */

public interface DemandTreasureBillService {

	/**
	 * 获取用户累计转入活期宝金额
	 * @param userId
	 * @return
	 */
	public double getDemandTreasureInMoney(String userId);
	
	
	public List<String> getInterestToday(Date date);
	/**
	 * 获取用户累计转出活期宝金额
	 * @param userId
	 * @return
	 */
	public double getDemandTreasureOutMoney(String userId);
	
	/**
	 * 获取用户活期宝累计收益
	 * @param userId
	 * @return
	 */
	public double getDemandTreasureInterestMoney(String userId);
	
	
	/**
	 * 获取无效的计息本金
	 * @param userId
	 * @return
	 */
	public double getDemandTreasureInvalidMoney(String userId, String type, Date date);
	
	/**
	 * 获取活期宝用户Id
	 * @return
	 */
	public List<String> getDemandTreasureUser();
	
	/**
	 * 获取活期宝用户Id
	 * @return
	 */
	public List<String> getInterestUser();
	
	/**
	 * 获取用户活期宝计息本金
	 * @param userId
	 * @return
	 */
	public double getDemandTreasureValidMoney(String userId);
	
	/**
	 * 获取用户活期宝计息本金
	 * @param userId
	 * @return
	 */
	public double getDemandTreasureValidMoney(String userId, Date date);
	
	/**
	 * 批量派息
	 * @param list
	 * @return
	 */
	public int insertInterestBatch(List<DemandTreasureBill> list);
	
	
	/**
	 * 分页查询
	 * @param pageNo
	 * @param pageSize
	 * @param bill
	 * @return
	 */
	public PageInfo<DemandTreasureBill> pageLite(int pageNo, int pageSize, DemandTreasureBill bill);
	
	
	/**
	 * 分页查询
	 * @param pageNo
	 * @param pageSize
	 * @param bill
	 * @return
	 */
	public PageInfo<DemandTreasureBill> pageLite02(int pageNo, int pageSize, DemandTreasureBill bill);
	
	public void insert(DemandTreasureBill bill);
	
	/**
	 * 活期宝数据统计
	 * @return
	 */
	public double[] getTreasuerAccount();
	
	/**
	 * 活期宝数据统计
	 * @param days
	 * @param type
	 * @return
	 */
	public List<DemandTreasureBill> getAccount(int days, String type);
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public double getDemandTreasureRateMoney(String userId);
	public int insertBill(DemandTreasureBill bill);
	
	/**
	 * 获得计息结果列表
	 * @return
	 */
	public List<String> getInterestToday();
	public double getDemandTreasureMoney(String userId);

	double getDemandTreasureOutInterestMoney(String userId);
	public int getCount(String billId);
	public double getDemandAvlidTreasureMoney(String userId);
	public double getDemandAvlidInterestMoney(String userId);
	public double[] getTreasuerAccount(String userId);
	
}
