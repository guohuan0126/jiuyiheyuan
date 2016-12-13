package com.duanrong.cps.business.platform.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import base.pagehelper.PageInfo;

import com.duanrong.cps.business.bsy.model.InvestByBsy;
import com.duanrong.cps.business.invest.model.Invest;
import com.duanrong.cps.business.loan.model.Loan;
import com.duanrong.cps.business.platform.dao.DemandBillDao;
import com.duanrong.cps.business.platform.dao.PlatformDao;
import com.duanrong.cps.business.platform.model.DemandBill;
import com.duanrong.cps.business.platform.model.PlatformUserRelation;
import com.duanrong.cps.business.platform.service.PlatformService;
@Service
public class PlatformServiceImpl implements PlatformService{

	@Autowired
	private PlatformDao platformDao;
	@Autowired
	private DemandBillDao demandBillDao;
	/**
	 * 记录请求日志信息
	 */
	@Override
	public void logInsertRequestLog(Map<String, Object> params) {
		
		platformDao.logInsertRequestLog(params);
		
	}
	
	/**
	 * 查询第三方平台用户信息
	 */
	@Override
	public PageInfo<PlatformUserRelation> getPlatformUserInfo(int parseInt,
			int parseInt2, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return platformDao.getPlatformUserInfo(parseInt, parseInt2, params);
	}
	
	/**
	 * 查询第三方用户投资信息
	 */
	@Override
	public PageInfo<InvestByBsy> getInvestRecords(int pageNo,
			int pageSize, Map<String, Object> params) {
		
		return platformDao.getInvestRecords(pageNo, pageSize, params);
	}

	/**
	 * 根据user_other_info表中用户来源查询用户信息
	 */
	@Override
	public PageInfo<PlatformUserRelation> queryUserByUserSrouce(int pageNo,
			int pageSize, Map<String, Object> params) {
		
		return platformDao.queryUserByUserSrouce(pageNo, pageSize, params);
	}

	/**
	 * 根据user_other_info表中用户来源查询投资记录
	 */
	@Override
	public PageInfo<InvestByBsy> queryUserInvestByUserSource(int pageNo,
			int pageSize, Map<String, Object> params) {
		
		return platformDao.queryUserInvestByUserSource(pageNo, pageSize, params);
	}

	/**
	 * 查询用户活期流水记录
	 */
	@Override
	public PageInfo<DemandBill> queryDemandBill(int pageNo, int pageSize,
			Map<String, Object> params) {
		
		return demandBillDao.queryDemandBill(pageNo, pageSize, params);
	}

	/**
	 * 查询推送到第三方平台的标的
	 */
	@Override
	public PageInfo<Loan> getLoanHistory(int pageNo, int pageSize,
			Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 *  根据user_other_info表查询投资用户的用户来源
	 */
	@Override
	public List<Invest> getUserInfoInvest(Map<String, Object> param) {
		
		return platformDao.getUserInfoInvest(param);
	}

	/**
	 * 查询注册来源为第三方平台，但是没有绑定关系的用户
	 */
	@Override
	public List<Invest> getNoRelationThirdUser(Map<String, Object> param) {
		return platformDao.getNoRelationThirdUser(param);
	}

	/**
	 * 查询没有绑定关系的第三方用户的投资信息
	 */
	@Override
	public List<Invest> queryInvestUserOtherInfo(Map<String, Object> param) {
		return platformDao.queryInvestUserOtherInfo(param);
	}
}