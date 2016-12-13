package com.duanrong.cps.business.bjs.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duanrong.cps.business.bjs.dao.BjsDao;
import com.duanrong.cps.business.bjs.model.BjsProductType;
import com.duanrong.cps.business.bjs.service.BjsService;
import com.duanrong.cps.business.invest.model.Invest;
import com.duanrong.cps.business.loan.model.Loan;
import com.duanrong.cps.business.repay.model.Repay;
import com.duanrong.cps.business.user.model.UserBill;

@Service
public class BjsServiceImpl implements BjsService {

	@Autowired
	private BjsDao bjsDao;
	
	/**
	 * 查询产品信息
	 */
	@Override
	public List<Loan> queryLoan(Date date, Integer start,
			Integer limit) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("date", date);
		params.put("startProduct", start);
		params.put("limitProduct", limit);
		return bjsDao.queryLoan(params);
	}

	/**
	 * 查询产品总数量
	 */
	@Override
	public int queryLoanAccount(Date date) {
		Map<String,Object> params=new HashMap<String,Object>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		params.put("date", sdf.format(date));
		
		return bjsDao.queryLoanAccount(params);
	}
	
	/**
	 * 记录请求日志信息
	 */
	@Override
	public void logInsertBjsRequestLog(Map<String, Object> params) {
		bjsDao.logInsertBjsRequestLog(params);
		
	}

	/**
	 * 查询八金社用户信息
	 */
	@Override
	public Map<String, Object> queryBjsUserInfo(String BjsUserId) {
		// TODO Auto-generated method stub
		return bjsDao.queryBjsUserInfo(BjsUserId);
	}
	/**
	 * 查询我们的用户与第三方用户关联表
	 */
	@Override
	public Map<String, Object> getPlatFormUserRelation(String userId) {
		
		return bjsDao.getPlatFormUserRelation(userId);
	}

	/**
	 * 查询用户的总收益 
	 */
	@Override
	public Double getUserTotalInterest(String userId) {
		
		return bjsDao.getUserTotalInterest(userId);
	}

	/**
	 * 用户交易流水查询接口
	 * @param param
	 * @return
	 */
	@Override
	public List<UserBill> queryCapitalFlow(Date date, Integer start,
			Integer limit, String userId) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("date", date);
		params.put("startAccount", start);
		params.put("limitAccout", limit);
		params.put("userId", userId);
		return bjsDao.queryCapitalFlow(params);
	}

	/**
	 * 用户交易流水个数查询
	 */
	@Override
	public int queryCapitalFlowAccount(String userId, Date date) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId", userId);
		params.put("date", date);
		return bjsDao.queryCapitalFlowAccount(params);
	}

	
	/**
	 * 投资查询接口
	 */
	@Override
	public List<Invest> queryInvestByUserId(Date date, Integer start,
			Integer limit, String userId) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("date", date);
		params.put("startInvest", start);
		params.put("limitInvest", limit);
		params.put("userId", userId);
		return bjsDao.queryInvestByUserId(params);
	}

	/**
	 * 查询用户投资的总记录数
	 */
	@Override
	public int queryInvestAccount(String userId, Date date) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId", userId);
		params.put("date", date);
		return bjsDao.queryInvestAccount(params);
	}

	/**
	 * 收益相关查询接口
	 * @return
	 */
	@Override
	public List<Invest> queryInterest(Date date, Integer start, Integer limit,
			String userId) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("date", date);
		params.put("startInterest", start);
		params.put("limitInterest", limit);
		params.put("userId", userId);
		return bjsDao.queryInterest(params);
	}

	/**
	 * 查询还款表
	 * @param param
	 * @return
	 */
	@Override
	public List<Repay> queryRepay(String loanId) {
		
		return bjsDao.queryRepay(loanId);
	}

	/**
	 * 查询收益的总记录数
	 * @return
	 */
	@Override
	public int queryInterestAccount(String userId, Date date) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userId", userId);
		params.put("date", date);
		return bjsDao.queryInterestAccount(params);
	}

}
