package com.duanrong.business.demand.service;

import java.util.Date;
import java.util.List;

import base.pagehelper.PageInfo;

import com.duanrong.business.demand.model.DemandTreasureAccount;
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
	public double readDemandTreasureInMoney(String userId);
	
	
	public List<String> readInterestToday(Date date, String userId);
	/**
	 * 获取用户累计转出活期宝金额
	 * @param userId
	 * @return
	 */
	public double readDemandTreasureOutMoney(String userId);
	
	/**
	 * 获取用户活期宝累计收益
	 * @param userId
	 * @return
	 */
	public double readDemandTreasureInterestMoney(String userId);
	
	
	/**
	 * 获取无效的计息本金
	 * @param userId
	 * @return
	 */
	public double readDemandTreasureInvalidMoney(String userId, String type, Date date);
	
	/**
	 * 获取活期宝用户Id
	 * @return
	 */
	public List<String> readDemandTreasureUser();
	
	/**
	 * 获取活期宝用户Id
	 * @return
	 */
	public List<String> readInterestUser();
	
	/**
	 * 获取用户活期宝计息本金
	 * @param userId
	 * @return
	 */
	public double readDemandTreasureValidMoney(String userId);
	
	/**
	 * 获取用户活期宝计息本金
	 * @param userId
	 * @return
	 */
	public double readDemandTreasureValidMoney(String userId, Date date);
	
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
	public PageInfo<DemandTreasureBill> readPageLite(int pageNo, int pageSize, DemandTreasureBill bill);
	
	
	/**
	 * 分页查询
	 * @param pageNo
	 * @param pageSize
	 * @param bill
	 * @return
	 */
	public PageInfo<DemandTreasureBill> readPageLite02(int pageNo, int pageSize, DemandTreasureBill bill);
	
	public void insert(DemandTreasureBill bill);
	
	/**
	 * 活期宝数据统计
	 * @return
	 */
	public double[] readTreasuerAccount();
	
	/**
	 * 活期宝数据统计
	 * @param days
	 * @param type
	 * @return
	 */
	public List<DemandTreasureBill> readAccount(int days, String type);
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public double readDemandTreasureRateMoney(String userId);
	public int insertBill(DemandTreasureBill bill);
	
	/**
	 * 获得计息结果列表
	 * @return
	 */
	public List<String> readInterestToday();
	public double readDemandTreasureMoney(String userId);

	double readDemandTreasureOutInterestMoney(String userId);
	public int readCount(String billId);
	public double readDemandAvlidTreasureMoney(String userId);
	public double readDemandAvlidInterestMoney(String userId);
	public double[] readTreasuerAccount(String userId);
	
	/**
	 * 判断用户是否参与活期宝
	 * @param userId
	 * @return
	 */
	public boolean readIsDemandUser(String userId);
	
	
	public DemandTreasureAccount readDemandAccount(String userId, Date date);
	/**
	 * 获取用户当前天天赚投资总额
	 * @param userId
	 * @return
	 */
	public double readUserDemandTreasure(String userId) ;
}