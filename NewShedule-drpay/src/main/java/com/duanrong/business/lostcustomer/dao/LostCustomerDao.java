package com.duanrong.business.lostcustomer.dao;

import java.util.List;


import java.util.Map;

import base.dao.BaseDao;
import base.pagehelper.PageInfo;

import com.duanrong.business.lostcustomer.model.LostCustomer;
import com.duanrong.business.user.model.User;

public interface LostCustomerDao extends BaseDao<LostCustomer>{
	/**
	 * 获取最晚的一个项目结束时间至今已超过15天的所有用户
	 * @author guohuan
	 * @param
	 * @return 
	 * @serialData 16/11/1
	 */
	public List<User> getAllUser();
	/**
	 * 根据userId获取天天赚资产
	 * @author guohuan
	 * @param
	 * @return 
	 * @serialData 16/11/1
	 */
	public double getDemandMoneyByUserId(String userId);
	
	/**
	 * 根据userId查询在投定期项目个数
	 * @author guohuan
	 * @param
	 * @return 
	 * @serialData 16/11/1
	 */
	public int getInvestStatusCount(String userId);
	/**
	 * 根据userId查询用户定期投资次数
	 * @author guohuan
	 * @param
	 * @return 
	 * @serialData 16/11/1
	 */
	public int getInvestNumByUserId(String userId);
	/**
	 * 根据userId查询用户活期投资次数
	 * @author guohuan
	 * @param
	 * @return 
	 * @serialData 16/11/1
	 */
	public int getDemandNumByUserId(String userId);
	/**
	 * 批量插入流失预警客户
	 * @author guohuan
	 * @param
	 * @return 
	 * @serialData 16/11/1
	 */
	public int insertAllLostCustomer(List<LostCustomer> lostCustomerList);
	/**
	 * 根据userId查询流失预警客户
	 * @author guohuan
	 * @param
	 * @return 
	 * @serialData 16/11/1
	 */
	public LostCustomer getLostCustomerByUserId(String userId);
	/**
	 * 将流失预警客户插入流失预警历史纪录表
	 * @author guohuan
	 * @param
	 * @return 
	 * @serialData 16/11/1
	 */
	public void insertLostCustomerToHistory(LostCustomer lost);
	/**
	 * 判断经理推荐人是否为空
	 * @author guohuan
	 * @param
	 * @return 返回true为空，false不为空
	 * @serialData 16/11/1
	 */
	public boolean isOreferrerNull(String userId);
	/**
	 * 分页
	 * @author guohuan
	 * @param pageNo
	 * @param pageSize
	 * @param params
	 * @return
	 */
	public List<LostCustomer> pageInfo(Map<String , Object> params);
	public List<LostCustomer> pageInfo1(Map<String , Object> params);
	/**
	 * @param userId
	 */
	public void updateByUserId(String userId);
}