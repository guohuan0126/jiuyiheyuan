package com.duanrong.business.lostcustomer.dao;

import java.util.List;
import java.util.Map;

import com.duanrong.business.lostcustomer.model.LostCustomer;

import base.dao.BaseDao;
import base.pagehelper.PageInfo;

public interface LostCustomerDao extends BaseDao<LostCustomer>{
	/**
	 * 分页
	 * @author guohuan
	 * @param pageNo
	 * @param pageSize
	 * @param params
	 * @return
	 */
	public PageInfo<LostCustomer> pageInfo(int pageNo, int pageSize,Map<String , Object> params);
	/**
	 * 获取所有流失预警客户
	 * @author guohuan
	 * @return
	 */
	public List<LostCustomer> getLostCustomers();
	/**
	 * 更新流失预警客户
	 * @author guohuan
	 * @return
	 */
	public int updateLostCustomer(Map<String , Object> params);
	/**
	 * 导出excle
	 * @author guohuan
	 * @return
	 */
	public List<LostCustomer> exportList(Map<String , Object> map);
	/**
	 * 获取用户回访后定期投资总额
	 * @author guohuan
	 * @return
	 */
	public PageInfo<LostCustomer> getUserTotalInvest1(int pageNo, int pageSize, Map<String , Object> params);
	/**
	 * 获取用户回访后活期投资总额
	 * @author guohuan
	 * @return
	 */
	public PageInfo<LostCustomer> getUserTotalDemand1(int pageNo, int pageSize, Map<String , Object> params);
	/**
	 * 获取用户在投定期总额
	 * @author guohuan
	 * @return
	 */
	/*public PageInfo<LostCustomer> getUserTotalInvest(int pageNo, int pageSize, Map<String , Object> params);*/
	/**
	 * 获取流失预警客户流失天数
	 * @author guohuan
	 * @return
	 */
	public int getLostDaysByUserId(String userId);
	/**
	 * 根据查询条件获取流失预警客户
	 * @author guohuan
	 * @return
	 */
	public List<LostCustomer> readUsers(Map<String, Object> params);
}
