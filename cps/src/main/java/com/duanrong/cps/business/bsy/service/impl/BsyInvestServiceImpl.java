package com.duanrong.cps.business.bsy.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.duanrong.cps.business.bsy.dao.BsyDao;
import com.duanrong.cps.business.bsy.model.BsyInterest;
import com.duanrong.cps.business.bsy.model.BsyInvest;
import com.duanrong.cps.business.bsy.model.BsyRepayMoney;
import com.duanrong.cps.business.bsy.service.BsyInvestService;

@Service
public class BsyInvestServiceImpl implements BsyInvestService{

	@Resource
	private BsyDao bsyDao;
	
	/**
	 * 描述：推送起息状态
	 * 
	 * */
	@Override
	public List<BsyInvest> getInterest() {
		
	 return	 bsyDao.getBsyInterest();
	}

	/**
	 * 描述：插入比搜益起息状态表
	 * 
	 * */
	@Override
	public void insertInterest(BsyInterest bsyInterest) {
		bsyDao.insertInterest(bsyInterest);
		
	}

	/**
	 * 描述：查询要推送的回款状态记录
	 * 
	 * */
	@Override
	public List<BsyRepayMoney> getBsyRepayMoney() {
		
		return bsyDao.getBsyRepayMoney();
	}

	/**
	 * 描述：插入推送的回款状态记录
	 * 
	 * */
	@Override
	public void insertRepayMoney(BsyRepayMoney bsyRepayMoney) {
		
		bsyDao.insertRepayMoney(bsyRepayMoney);
	}

}
