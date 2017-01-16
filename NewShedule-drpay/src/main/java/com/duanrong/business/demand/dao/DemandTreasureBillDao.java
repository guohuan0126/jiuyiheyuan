package com.duanrong.business.demand.dao;

import java.util.Date;
import java.util.List;

import base.dao.BaseDao;
import base.pagehelper.PageInfo;

import com.duanrong.business.demand.model.DemandTreasureBill;

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
	
	
	public List<String> getInterestToday(Date date);
	/**
	 * 获取累计金额
	 * @param userId
	 * @return
	 */
	public double getDemandTreasureInvalidMoney(String userId, String Type, Date date);
	
	
	/**
	 * 根据类型获取用户活期宝金额
	 * @param userId
	 * @return
	 */
	public double getDemandTreasureMoneyByType(String type);
	
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
	 * 资金统计
	 * @param days
	 * @param type
	 * @return
	 */
	public List<DemandTreasureBill> getAccount(int days, String type);
    
	/**
	 * 插入
	 * @param bill
	 * @return
	 */
	public int insertBill(DemandTreasureBill bill);

	
	/**
	 * 分页查询
	 * 
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页显示的记录数
	 * @param entity
	 *            条件
	 */
	public PageInfo<DemandTreasureBill> pageLite02(int pageNo, int pageSize, DemandTreasureBill entity);
	
	/**
	 * 获取需要计息的用户ID，排除当天计过息的
	 * @return
	 */
	public List<String> getInterestUser();
	
	/**
	 * 获得计息结果列表
	 * @return
	 */
	public List<String> getInterestToday();
	public Integer getCount(String billId);
	public double getDemandLaterInterest(String userId);
	
	
	
	
	/**
	 * 得到该用户活期宝的金额DemandBill,根据类型
	 * @param userId
	 * @return
	 */
	public double getDemandBillAllMoneyByStatus(String userId,String status);
	
}