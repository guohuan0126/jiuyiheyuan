package com.duanrong.business.withdraw.service.impl;

import java.math.BigDecimal;
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

	public List<WithdrawCash> readByCondition(WithdrawCash withdrawCash) {
		return withdrawCashDao.getByCondition(withdrawCash);
	}

	public Double readTotalWithdrawCash(WithdrawCash withdrawCash) {
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
	public PageData<WithdrawCash> readPaging(int pageIndex, int pageSize,
			WithdrawCash cash) {
		return withdrawCashDao.findPaging(pageIndex, pageSize, cash);
	}

	/**
	 * 查询用户的提现次数
	 * 
	 * @param withcash
	 * @return
	 */
	public long readWithdrawCount(WithdrawCash withcash) {
		return withdrawCashDao.GetWithdrawCount(withcash);
	}

	@Override
	public WithdrawCash read(String id) {
		return withdrawCashDao.get(id);
	}
	
	@Override
	public PageInfo<WithdrawCash> readPageInfo(int pageNo, int pageSize,
			WithdrawCash withcash) {
		return withdrawCashDao.pageLite(pageNo, pageSize, withcash);
	}

	@Override
	public Double readTotalFee(WithdrawCash withcash) {
		Double d=0d;
		d= withdrawCashDao.getTotalFee(withcash);
		if( d!=null) return d ; 
		else return (double) 0;
	}

	@Override
	public double readWithdrawMoneyPerDay(Map<String, Object> params) {
		return withdrawCashDao.getWithdrawMoneyPerDay(params);
	}

	@Override
	public List<WithdrawCash> readCashNum(Map map) {
		return withdrawCashDao.getCashNum(map);
	}

	/**
	 * 总提现金额[去除固定借款人]
	 */
	@Override
	public BigDecimal readExcludeFixedFee(WithdrawCash withdrawCash) {
		return withdrawCashDao.getExcludeFixedFee(withdrawCash);
	}
	
	@Override
	public void update(WithdrawCash withcash) {
		withdrawCashDao.update(withcash);
	}

	@Override
	public int readExcludeFixedFeePeopleCount(WithdrawCash withdrawCash) {
		// TODO Auto-generated method stub
		return withdrawCashDao.getExcludeFixedFeePeopleCount(withdrawCash);
	}


}