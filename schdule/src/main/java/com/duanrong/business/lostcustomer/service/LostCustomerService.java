package com.duanrong.business.lostcustomer.service;

import java.util.List;
import java.util.Map;

import base.pagehelper.PageInfo;

import com.duanrong.business.lostcustomer.model.LostCustomer;



public interface LostCustomerService {
	
	/**
	 * 获取流失预警客户
	 * @author guohuan
	 * @param
	 * @return 流失预警客户
	 * @serialData 16/11/1
	 */
	public List<LostCustomer> readAllLostCustomer();
	/**
	 * 插入流失预警客户
	 * @author guohuan
	 * @param
	 * @return 
	 * @serialData 16/11/1
	 */
	public int insertAllLostCustomer(List<LostCustomer> lostCustomerList);
	/**
	 * 根据userId获取流失预警客户
	 * @author guohuan
	 * @param
	 * @return 
	 * @serialData 16/11/1
	 */
	public LostCustomer readLostCustomerByUserId(String userId);
	/**
	 * 插入流失客户预警历史表并删除流失客户表中数据
	 * @author guohuan
	 * @param
	 * @return 
	 * @serialData 16/11/1
	 */
	public void insertLostCustomerToHistoryAndUpdate(LostCustomer lost);
	/**
	 * 查出所有流失预警客户
	 */
	public List<LostCustomer> readPageInfo(Map<String , Object> params);
	public List<LostCustomer> readPageInfo1(Map<String , Object> params);
	
	public void update(LostCustomer lostCustomer);
	
	
}
