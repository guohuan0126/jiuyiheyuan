package com.duanrong.business.lostcustomer.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import base.pagehelper.PageInfo;

import com.duanrong.business.lostcustomer.dao.LostCustomerDao;
import com.duanrong.business.lostcustomer.model.LostCustomer;
import com.duanrong.business.lostcustomer.service.LostCustomerService;

@Service
public class LostCustomerServiceImpl implements LostCustomerService{
	
	@Resource
	LostCustomerDao lostCustomerDao;
	
	
	/**
	  * 获取所有流失预警客户
	  * @author guohuan
	  * @return
	  */
	@Override
	public List<LostCustomer> readLostCustomers() {
		return lostCustomerDao.getLostCustomers();
	}
	/**
	 * 分页
	 * @author guohuan
	 * @param pageNo
	 * @param pageSize
	 * @param params
	 * @return
	 */
	@Override
	public PageInfo<LostCustomer> readPageInfo(int pageNo, int pageSize,
			Map<String, Object> params) {
		return lostCustomerDao.pageInfo(pageNo, pageSize,params);
	}
	/**
	  * 更新流失预警客户
	  * @author guohuan
	  * @return
	  */
	@Override
	public int updateCustomer(Map<String, Object> params) {
		return lostCustomerDao.updateLostCustomer(params);
		
	}
	/**
	  * 导出excle
	  * @author guohuan
	  * @return
	  */
	@Override
	public List<LostCustomer> readExportList(Map<String, Object> map) {
		return lostCustomerDao.exportList(map);
	}
	/**
	  * 获取用户在投定期总额和回访后投资总额
	  * @author guohuan
	  * @return
	  */
	/*@Override
	public PageInfo<LostCustomer> readUserTotalInvest(int pageNo, int pageSize,
			Map<String, Object> params) {
		return lostCustomerDao.getUserTotalInvest(pageNo,pageSize,params);
	}*/
	/**
	  * 获取用户回访后投定期总额
	  * @author guohuan
	  * @return
	  */
	@Override
	public PageInfo<LostCustomer> readUserTotalInvest1(int pageNo, int pageSize,
			Map<String, Object> params) {
		return lostCustomerDao.getUserTotalInvest1(pageNo,pageSize,params);
	}
	/**
	  * 获取用户回访后投活期总额
	  * @author guohuan
	  * @return
	  */
	@Override
	public PageInfo<LostCustomer> readUserTotalDemand1(int pageNo, int pageSize,
			Map<String, Object> params) {
		return lostCustomerDao.getUserTotalDemand1(pageNo,pageSize,params);
	}
	 /**
	  * 更新流失预警客户
	  * @author guohuan
	  * @return
	  */
	@Override
	public void update(LostCustomer lostCustomer) {
		lostCustomerDao.update(lostCustomer);
	}
	/**
	  * 根据条件获取所有流失预警客户
	  * @author guohuan
	  * @return
	  */
	@Override
	public List<LostCustomer> readLostCustomers(Map<String, Object> params) {
		return lostCustomerDao.readUsers(params);
	}
}
