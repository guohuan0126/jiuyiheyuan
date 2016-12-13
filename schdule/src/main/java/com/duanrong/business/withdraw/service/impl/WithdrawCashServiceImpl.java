package com.duanrong.business.withdraw.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import base.model.PageData;
import base.pagehelper.PageInfo;

import com.duanrong.business.withdraw.dao.WithdrawCashDao;
import com.duanrong.business.withdraw.model.WithdrawCash;
import com.duanrong.business.withdraw.service.WithdrawCashService;

@Service
public class WithdrawCashServiceImpl implements WithdrawCashService {

	@Resource
	WithdrawCashDao withdrawCashDao;

	public List<WithdrawCash> getByCondition(WithdrawCash withdrawCash) {
		return withdrawCashDao.getByCondition(withdrawCash);
	}

	public Double getTotalWithdrawCash(WithdrawCash withdrawCash) {
		Double d=0d;
		d= withdrawCashDao.getTotalWithdrawCash(withdrawCash);
		if( d!=null) return d ; 
		else return (double) 0;
	}

	/**
	 * 查询用户的提现记录
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public PageData<WithdrawCash> findPaging(int pageIndex, int pageSize,
			WithdrawCash cash) {
		return withdrawCashDao.findPaging(pageIndex, pageSize, cash);
	}

	/**
	 * 查询用户的提现次数
	 * 
	 * @param withcash
	 * @return
	 */
	public long GetWithdrawCount(WithdrawCash withcash) {
		return withdrawCashDao.GetWithdrawCount(withcash);
	}

	@Override
	public WithdrawCash get(String id) {
		return withdrawCashDao.get(id);
	}
	
	@Override
	public PageInfo<WithdrawCash> findPageInfo(int pageNo, int pageSize,
			WithdrawCash withcash) {
		return withdrawCashDao.pageLite(pageNo, pageSize, withcash);
	}

	@Override
	public Double getTotalFee(WithdrawCash withcash) {
		Double d=0d;
		d= withdrawCashDao.getTotalFee(withcash);
		if( d!=null) return d ; 
		else return (double) 0;
	}

	@Override
	public double getWithdrawMoneyPerDay(Map<String, Object> params) {
		return withdrawCashDao.getWithdrawMoneyPerDay(params);
	}

	@Override
	public List<WithdrawCash> getCashNum(Map map) {
		return withdrawCashDao.getCashNum(map);
	}

	@Override
	public void update(WithdrawCash withcash) {
		withdrawCashDao.update(withcash);
	}

	@Override
	public WithdrawCash queryWithLock(String id) {
		return withdrawCashDao.getWithLock(id);
	}

}