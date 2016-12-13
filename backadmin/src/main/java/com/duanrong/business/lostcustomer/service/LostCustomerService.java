package com.duanrong.business.lostcustomer.service;

import java.util.List;
import java.util.Map;

import base.pagehelper.PageInfo;

import com.duanrong.business.lostcustomer.model.LostCustomer;
import com.duanrong.business.user.model.User;

public interface LostCustomerService {
	
	/**
	 * 分页
	 * @author guohuan
	 * @param pageNo
	 * @param pageSize
	 * @param params
	 * @return
	 */
	 public PageInfo<LostCustomer> readPageInfo(int pageNo, int pageSize,Map<String , Object> params);
	 /**
	  * 获取所有流失预警客户
	  * @author guohuan
	  * @return
	  */
	 public List<LostCustomer> readLostCustomers();
	 /**
	  * 更新流失预警客户
	  * @author guohuan
	  * @return
	  */
	 public int updateCustomer(Map<String, Object> params);
	 /**
	  * 导出excle
	  * @author guohuan
	  * @return
	  */
	 public List<LostCustomer> readExportList(Map<String, Object> map);
	 /**
	  * 获取用户在投定期总额和回访后投资总额
	  * @author guohuan
	  * @return
	  */
	/* public PageInfo<LostCustomer> readUserTotalInvest(int pageNo, int pageSize,
				Map<String, Object> params);*/
	 /**
	  * 获取用户回访后定期投资总额
	  * @author guohuan
	  * @return
	  */
	 public PageInfo<LostCustomer> readUserTotalInvest1(int pageNo, int pageSize,
				Map<String, Object> params);
	 /**
	  * 获取用户回访后活期投资总额
	  * @author guohuan
	  * @return
	  */
	 public PageInfo<LostCustomer> readUserTotalDemand1(int pageNo, int pageSize,
				Map<String, Object> params);
	 /**
	  * 更新流失预警客户
	  * @author guohuan
	  * @return
	  */
	 public void update(LostCustomer lostCustomer);
	 /**
	  * 获取所有流失预警客户
	  * @author guohuan
	  * @return
	  */
	 public List<LostCustomer> readLostCustomers(Map<String, Object> params);
}
