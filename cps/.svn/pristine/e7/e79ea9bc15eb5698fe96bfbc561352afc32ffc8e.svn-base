package com.duanrong.cps.business.invest.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import base.pagehelper.PageInfo;

import com.duanrong.cps.business.fengchelicai.model.FengchelicaiInvestList;
import com.duanrong.cps.business.invest.dao.InvestDao;
import com.duanrong.cps.business.invest.model.Invest;
import com.duanrong.cps.business.invest.service.InvestService;


/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2014-9-1 上午11:31:26
 * @Description : drsoa Maven Webapp com.duanrong.business.invest.service.impl
 *              InvestServiceImpl.java
 * 
 */
@Service
public class InvestServiceImpl implements InvestService {

	final Lock lock = new ReentrantLock();

	@Resource
	InvestDao investDao;


	
	/**
	 * 投资记录（只显示天眼用户的投资记录）
	 */
	public PageInfo<Invest> getInvestRecordsNetLoanEye(int pageNo, int pageSize, Map<String, Object> map){
		return investDao.getInvestRecordsNetLoanEye(pageNo, pageSize, map);
	}

	/**
	 * 
	 */
	public BigDecimal getTotalMoneyForNetLoanEye(Map<String, Object> map) {
		return investDao.getTotalMoneyForNetLoanEye(map);
	}

	@Override
	public int getTotalNumForNetLoanEye(Map<String, Object> map) {
		return investDao.getTotalNumForNetLoanEye(map);
	}

	@Override
	public List<Invest> getExportInvestInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return investDao.getExportInvestInfo(map);
	}

	@Override
	public Object getInvestDateLastOne(String loanId) {
		Date date = investDao.getInvestDateLastOne(loanId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
		String dateStr = sdf.format(date);
		return dateStr;
	}

	@Override
	public Object getInvestDateFirstOne(String loanId) {
		Date date = investDao.getInvestDateFirstOne(loanId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
		String dateStr = sdf.format(date);
		return dateStr;
	}

	@Override
	public Object getTotalCount(String loanId) {	
		return investDao.getTotalCount(loanId);
	}

	@Override
	public Object getInvestRecords(String loanId) {
		List<Invest> selectList =  investDao.getInvestRecords(loanId);
		List<FengchelicaiInvestList> resultList = new ArrayList<FengchelicaiInvestList>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
		if(selectList.size()>0){
			for (Invest invest : selectList) {
				FengchelicaiInvestList fengchelicaiInvestList = new FengchelicaiInvestList();
				Double money = invest.getMoney();
				java.math.BigDecimal bd1 = new java.math.BigDecimal(money);
				fengchelicaiInvestList.setInvestMoney(bd1);
				Date date = invest.getTime();
				String dateStr = sdf.format(date);
				fengchelicaiInvestList.setInvestTime(dateStr);
				fengchelicaiInvestList.setInvestUser(invest.getUserId());
				resultList.add(fengchelicaiInvestList);
			}
		}
		
		return resultList;
	}

	@Override
	public Double getTotalMoney4AlreadyFundraising(String id) {
		return investDao.getTotalMoney4AlreadyFundraising(id);
	}

	
}
