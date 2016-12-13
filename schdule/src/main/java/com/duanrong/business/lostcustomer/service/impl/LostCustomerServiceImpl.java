package com.duanrong.business.lostcustomer.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.Log;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.business.lostcustomer.dao.LostCustomerDao;
import com.duanrong.business.lostcustomer.model.LostCustomer;
import com.duanrong.business.lostcustomer.service.LostCustomerService;
import com.duanrong.business.user.model.User;
import com.duanrong.business.user.service.UserMoneyService;

@Service
public class LostCustomerServiceImpl implements LostCustomerService{
	
	@Resource
	LostCustomerDao lostCustomerDao;
	@Resource
	UserMoneyService userMoneyService;
	@Resource
	Log log;
	
	/**
	 * 获取流失预警客户
	 * @author guohuan
	 * @param
	 * @return 流失预警客户
	 */
	@Override
	public List<LostCustomer> readAllLostCustomer() {
		List<User> allUsers = lostCustomerDao.getAllUser();
		List<LostCustomer> allLostCustomers = new ArrayList<>();
 		for (int i = 0; i < allUsers.size(); i++) {
			User user = allUsers.get(i);
			try {
				if (user!=null) {
					int count = lostCustomerDao.getInvestStatusCount(user.getUserId());
					if (count==0) {
						int demandNum = lostCustomerDao.getDemandNumByUserId(user.getUserId());
						int investNum = lostCustomerDao.getInvestNumByUserId(user.getUserId());
						if ((demandNum + investNum)!=0) {
							double demandTotalMoney = lostCustomerDao.getDemandMoneyByUserId(user.getUserId());
							if (demandTotalMoney==0) {
								boolean oreferrer = lostCustomerDao.isOreferrerNull(user.getUserId());
								LostCustomer lostCustomer = new LostCustomer();
								lostCustomer.setUserId(user.getUserId());
								lostCustomer.setCreateTime(new Date());
								if (!oreferrer) {
									lostCustomer.setOreferrerStatus(1);
								}
								allLostCustomers.add(lostCustomer);
							}
						}
						
					}
					
				}
			} catch (Exception e) {
				log.errLog("获取流失客户异常"+user.getUserId(), e);
				e.printStackTrace();
			}
 		}
		return allLostCustomers;
	}
	/**
	 * 插入流失预警客户
	 * @author guohuan
	 * @param
	 * @return 
	 */
	@Override
	public int insertAllLostCustomer(List<LostCustomer> lostCustomers) {
		return lostCustomerDao.insertAllLostCustomer(lostCustomers);
	}
	/**
	 * 根据userId获取流失预警客户
	 * @author guohuan
	 * @param
	 * @return 
	 */
	@Override
	public LostCustomer readLostCustomerByUserId(String userId) {
		return lostCustomerDao.getLostCustomerByUserId(userId);
	}
	/**
	 * 插入流失客户预警历史表并删除流失客户表中数据
	 * @author guohuan
	 * @param
	 * @return 
	 */
	@Override
	@Transactional
	public void insertLostCustomerToHistoryAndUpdate(LostCustomer lost) {
		lostCustomerDao.insertLostCustomerToHistory(lost);
		lostCustomerDao.updateByUserId(lost.getUserId());
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
	public List<LostCustomer> readPageInfo(Map<String, Object> params) {
		return lostCustomerDao.pageInfo(params);
	}
	@Override
	public List<LostCustomer> readPageInfo1(Map<String, Object> params) {
		return lostCustomerDao.pageInfo1(params);
	}
	
	@Override
	public void update(LostCustomer lostCustomer) {
		lostCustomerDao.update(lostCustomer);
	}
}
