package com.duanrong.drpay.business.payment.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import util.DateUtil;
import util.Log;
import util.Week;
import base.exception.UserAccountException;

import com.duanrong.drpay.business.account.PlatformAccountEnum;
import com.duanrong.drpay.business.account.service.PlatformAccountService;
import com.duanrong.drpay.business.account.service.UserAccountService;
import com.duanrong.drpay.business.payment.dao.WithdrawCashDao;
import com.duanrong.drpay.business.payment.model.WithdrawCash;
import com.duanrong.drpay.business.payment.service.WithdrawCashService;
import com.duanrong.drpay.business.user.UserConstants;
import com.duanrong.drpay.config.BusinessEnum;

@Service
public class WithdrawCashServiceImpl implements WithdrawCashService {

	@Resource
	WithdrawCashDao withdrawCashDao;

	@Resource
	UserAccountService userAccountService;
	
	@Resource
	PlatformAccountService platformAccountService;
	
	@Resource
	Log log;

	@Override
	public WithdrawCash get(String id) {
		return withdrawCashDao.get(id);
	}

	@Override
	public WithdrawCash getWithLock(String id) {
		return withdrawCashDao.getWithLock(id);
	}
	
	@Override
	public Date getArrivalDate(Date date){
		Date tomorrow = DateUtil.addDay(date, 2);//明天
		Integer i = withdrawCashDao.getHolidayDate(tomorrow,"holiday");
		if(Week.SATURDAY!=DateUtil.getWeek(tomorrow)&&Week.SUNDAY!=DateUtil.getWeek(tomorrow)&&i==0){
			return tomorrow;
		}
		//法定上班日
		Integer j = withdrawCashDao.getHolidayDate(tomorrow,"work");
		if(j>0){
			return tomorrow;
		}
		return getArrivalDate(tomorrow);
	}

	@Override
	public void insert(WithdrawCash withdrawCash) {
		withdrawCashDao.insert(withdrawCash);
	}

	@Override
	public void successWithdraw(WithdrawCash withdrawCash) throws UserAccountException {
		if (StringUtils.equals(withdrawCash.getStatus(),
				UserConstants.WithdrawStatus.WAIT_VERIFY)) {
			withdrawCash.setStatus(UserConstants.WithdrawStatus.SUCCESS);
			// 更新withdraw_cash表，放在user_bill插入操作之后，以免出现事物不同步的问题
			withdrawCashDao.update(withdrawCash);
			String userId = withdrawCash.getUserId();
			Double actualMoney = withdrawCash.getActualMoney();
			userAccountService.transferOut(userId, actualMoney,
					BusinessEnum.withdraw_cash, "提现成功", "提现成功",
					withdrawCash.getId());
			// 计算手续费
			try {
				platformAccountService.transferOut(
						PlatformAccountEnum.PLATFORM_SYS, 1,
						BusinessEnum.fee, "提现手续费", withdrawCash.getId());
			} catch (Exception e) {
				e.printStackTrace();
				log.errLog("转出提现手续费失败", "userId:"+userId+","+e);
			}			
		} 
	}

	@Override
	public void update(WithdrawCash withdrawCash) {
		withdrawCashDao.update(withdrawCash);
	}

}