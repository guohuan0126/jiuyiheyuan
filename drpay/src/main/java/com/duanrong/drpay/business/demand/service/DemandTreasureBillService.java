package com.duanrong.drpay.business.demand.service;

import java.util.List;
import com.duanrong.drpay.business.demand.model.DemandtreasureTransferOut;



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
	 * 获取活期宝用户Id
	 * @return
	 */
	public List<String> getDemandTreasureUser();
		
	
	/**
	 * 获取用户活期宝账户金额
	 * @param userId
	 * @return
	 */
	public double getDemandTreasureMoney(String userId);
	
	/**
	 * 获取用户活期宝账户本金
	 * @param userId
	 * @return
	 */
	public double getDemandAvlidTreasureMoney(String userId);
	
	/**
	 * 获取用户活期宝账户可提利息
	 * @param userId
	 * @return
	 */
	public double getDemandAvlidInterestMoney(String userId);
		
	/**
	 * 获取用户活期宝账户申赎中金额
	 * @param userId
	 * @return
	 */	
	public DemandtreasureTransferOut getDemandOutTreasureMoney(String userId);
	
	/**
	 * 获取用户昨日收益
	 * @param userId
	 * @return
	 */
	public double getDemandLaterInterest(String userId);
		

	/**
	 * 提息
	 * @param userId
	 * @return
	 */
	public double getDemandTreasureOutInterestMoney(String userId);
	
	
	/**
	 * 获取用户已投的本金
	 * 用于个人上限判断
	 * bill表中所有转入-bill表中所有转出-in表中正在转入的金额
	 * @param userId
	 * @return
	 */
	public double getUserDemandInSumMoney(String userId) ;
	
	/**
	 * 用户转入中的金额
	 * @param userId
	 * @return
	 */
	public double getDemandInTreasureMoney(String userId);
}