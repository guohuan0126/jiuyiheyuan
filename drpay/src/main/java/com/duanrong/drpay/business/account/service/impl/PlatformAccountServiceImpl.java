package com.duanrong.drpay.business.account.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import util.ArithUtil;
import base.error.ErrorCode;
import base.exception.ParameterException;
import base.exception.PlatformAccountException;
import base.pagehelper.PageInfo;

import com.duanrong.drpay.business.account.AccountEnum;
import com.duanrong.drpay.business.account.PlatformAccountEnum;
import com.duanrong.drpay.business.account.dao.PlatformAccountDao;
import com.duanrong.drpay.business.account.model.PlatformAccount;
import com.duanrong.drpay.business.account.model.PlatformBill;
import com.duanrong.drpay.business.account.service.PlatformAccountService;
import com.duanrong.drpay.business.payment.dao.PaymentBankChannelDao;
import com.duanrong.drpay.business.payment.model.PaymentChannel;
import com.duanrong.drpay.config.BusinessEnum;

@Service
public class PlatformAccountServiceImpl implements PlatformAccountService {

	@Resource
	PlatformAccountDao platformAccountDao;

	@Resource
	PaymentBankChannelDao paymentBankChannelDao;

	/**
	 * 查询平台账户
	 * 
	 * @return
	 */
	@Override
	public PlatformAccount getPlatformAccount(PlatformAccountEnum platformAccountType) {
		return platformAccountDao.get(platformAccountType);
	}

	/**
	 * 查询平台账户
	 * 
	 * @return
	 */
	@Override
	public PlatformAccount getPlatformAccountWithLock(PlatformAccountEnum platformAccountType) {
		return platformAccountDao.getWithLock(platformAccountType);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void transferIn(PlatformAccountEnum platformAccountType, double money, BusinessEnum businessType,
			String typeInfo, String requestNo) {
		PlatformAccount account = platformAccountDao.getWithLock(platformAccountType);
		account.setBalance(account.getBalance() + money);
		account.setAvailableBalance(account.getAvailableBalance() + money);
		account.setTime(new Date());
		platformAccountDao.update(account);
		this.insertPlatformBill(account.getId(), money, account.getBalance(),
				account.getFreezeAmount(), AccountEnum.ti_balance,
				businessType, requestNo, typeInfo);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void transferOut(PlatformAccountEnum platformAccountType, double money, BusinessEnum businessType,
			String typeInfo, String requestNo) {
		PlatformAccount account = platformAccountDao.getWithLock(platformAccountType);
		account.setBalance(account.getBalance() - money);
		account.setAvailableBalance(account.getAvailableBalance() - money);
		account.setTime(new Date());
		platformAccountDao.update(account);
		this.insertPlatformBill(account.getId(), money, account.getBalance(),
				account.getFreezeAmount(), AccountEnum.to_balance,
				businessType, requestNo, typeInfo);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void freeze(PlatformAccountEnum platformAccountType, double money, BusinessEnum businessType,
			String typeInfo, String requestNo) {
		PlatformAccount account = platformAccountDao.getWithLock(platformAccountType);
		account.setAvailableBalance(account.getAvailableBalance() - money);
		account.setFreezeAmount(account.getFreezeAmount() + money);
		account.setTime(new Date());
		platformAccountDao.update(account);
		this.insertPlatformBill(account.getId(), money, account.getBalance(),
				account.getFreezeAmount(), AccountEnum.freeze, businessType,
				requestNo, typeInfo);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void unfreeze(PlatformAccountEnum platformAccountType, double money, BusinessEnum businessType,
			String typeInfo, String requestNo) throws PlatformAccountException {
		PlatformAccount account = platformAccountDao.getWithLock(platformAccountType);
		if (ArithUtil.round(account.getFreezeAmount(), 2) < ArithUtil.round(
				money, 2)) {
			throw new PlatformAccountException(ErrorCode.PlatformFrozeToLow);
		}
		account.setAvailableBalance(account.getAvailableBalance() + money);
		account.setFreezeAmount(account.getFreezeAmount() - money);
		account.setTime(new Date());
		platformAccountDao.update(account);
		this.insertPlatformBill(account.getId(), money, account.getBalance(),
				account.getFreezeAmount(), AccountEnum.unfreeze, businessType,
				requestNo, typeInfo);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void tofreeze(PlatformAccountEnum platformAccountType, double money, BusinessEnum businessType,
			String typeInfo, String requestNo) throws PlatformAccountException {
		PlatformAccount account = platformAccountDao.getWithLock(platformAccountType);
		if (ArithUtil.round(account.getFreezeAmount(), 2) < ArithUtil.round(
				money, 2)) {
			throw new PlatformAccountException(ErrorCode.PlatformFrozeToLow);
		}
		account.setBalance(account.getBalance() - money);
		account.setFreezeAmount(account.getFreezeAmount() - money);
		account.setTime(new Date());
		platformAccountDao.update(account);
		this.insertPlatformBill(account.getId(), money, account.getBalance(),
				account.getFreezeAmount(), AccountEnum.to_frozen, businessType,
				requestNo, typeInfo);
	}

	/**
	 * 插入平台流水
	 * 
	 * @param money
	 * @param balance
	 * @param freezeAmount
	 * @param accountType
	 * @param businessType
	 * @param requestNo
	 * @param operatorInfo
	 */
	private void insertPlatformBill(Integer platformId, double money, double balance,
			double freezeAmount, AccountEnum accountType,
			BusinessEnum businessType, String requestNo, String operatorInfo) {
		// 插入资金流水
		PlatformBill bill = new PlatformBill();
		bill.setPlatformId(platformId);
		bill.setBalance(balance);
		bill.setMoney(money);
		bill.setRequestNo(requestNo);
		bill.setType(accountType.toString());
		bill.setTypeInfo(operatorInfo);
		bill.setFreezeAmount(freezeAmount);
		bill.setBusinessType(businessType.toString());
		bill.setTime(new Date());
		platformAccountDao.insertBill(bill);
	}

	/**
	 * 平台账户流水分页，默认查询当天流水，按时间短查询，时间前后差不能超过一个月
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param platformBill
	 * @return
	 */
	@Override
	public PageInfo<PlatformBill> pageLite(int pageNo, int pageSize,
			PlatformBill platformBill) throws ParameterException {
		if (platformBill.getBeginTime() != null
				&& platformBill.getEndTime() != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(platformBill.getBeginTime());
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
			Date nextMonth = calendar.getTime();
			if (nextMonth.getTime() < platformBill.getEndTime().getTime()) {
				throw new ParameterException(ErrorCode.QueryTimeTooLong);
			}
		}
		if (platformBill.getBeginTime() == null
				&& platformBill.getEndTime() == null) {
			Calendar c1 = new GregorianCalendar();
			c1.set(Calendar.HOUR_OF_DAY, 0);
			c1.set(Calendar.MINUTE, 0);
			c1.set(Calendar.SECOND, 0);
			platformBill.setBeginTime(c1.getTime());
			Calendar c2 = new GregorianCalendar();
			c2.set(Calendar.HOUR_OF_DAY, 23);
			c2.set(Calendar.MINUTE, 59);
			c2.set(Calendar.SECOND, 59);
			platformBill.setEndTime(c2.getTime());
		}
		if (platformBill.getBeginTime() != null
				&& platformBill.getEndTime() == null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(platformBill.getBeginTime());
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
			Date nextMonth = calendar.getTime();
			platformBill.setEndTime(nextMonth);
		}
		if (platformBill.getBeginTime() == null
				&& platformBill.getEndTime() != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(platformBill.getEndTime());
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
			Date preMonth = calendar.getTime();
			platformBill.setEndTime(preMonth);
		}
		return platformAccountDao.pageLite(pageNo, pageSize, platformBill);
	}

	@Override
	public double getFee(BusinessEnum businessType) throws Exception {
		return getFee(businessType, 0, null, null);
	}

	@Override
	public double getFee(BusinessEnum businessType, double money,
			String paymentId, String type) throws Exception {
		double fee = 0;
		if (BusinessEnum.create_account.equals(businessType)) {
			fee = 1;
			return fee;
		}
		double rate = 0.0;
		PaymentChannel channel = paymentBankChannelDao
				.getChannelByCode(paymentId);
		if (StringUtils.equalsIgnoreCase(type, "quick")) {
			rate = channel.getRateQuick();
		} else {
			rate = channel.getRateGateway();
		}
		if (BusinessEnum.recharge.equals(businessType)) {
			fee = money * rate;
			if (fee < 2) {
				fee = 2;
			}
		} else if (BusinessEnum.withdraw_cash.equals(businessType)) {
			fee = channel.getWithdrawFee();
		}
		return ArithUtil.round(fee, 2);
	}

}
