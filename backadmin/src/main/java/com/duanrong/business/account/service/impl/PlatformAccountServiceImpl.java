package com.duanrong.business.account.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import base.exception.InsufficientBalance;
import base.exception.InsufficientFreeze;
import base.exception.InsufficientFreezeAmountException;
import base.exception.OutOfDateException;
import base.exception.QueryTimeTooLongException;
import base.pagehelper.PageInfo;

import com.duanrong.business.account.AccountEnum;
import com.duanrong.business.account.BusinessEnum;
import com.duanrong.business.account.dao.PlatformAccountDao;
import com.duanrong.business.account.model.PlatformAccount;
import com.duanrong.business.account.model.PlatformBill;
import com.duanrong.business.account.service.PlatformAccountService;
import com.duanrong.util.ArithUtil;

@Service
public class PlatformAccountServiceImpl implements PlatformAccountService {

	@Resource
	PlatformAccountDao platformAccountDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void transferIn(double money, BusinessEnum businessType,
			String typeInfo, String requestNo) {
		PlatformAccount account = platformAccountDao.getWithLock();
		account.setBalance(account.getBalance() + money);
		account.setAvailableBalance(account.getAvailableBalance() + money);
		account.setTime(new Date());
		platformAccountDao.update(account);
		this.insertPlatformBill(money, account.getBalance(),
				account.getFreezeAmount(), AccountEnum.ti_balance,
				businessType, requestNo, typeInfo);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void transferOut(double money, BusinessEnum businessType,
			String typeInfo, String requestNo) throws InsufficientBalance {
		PlatformAccount account = platformAccountDao.getWithLock();
		account.setBalance(account.getBalance() - money);
		account.setAvailableBalance(account.getAvailableBalance() - money);
		account.setTime(new Date());
		platformAccountDao.update(account);
		this.insertPlatformBill(money, account.getBalance(),
				account.getFreezeAmount(), AccountEnum.to_balance,
				businessType, requestNo, typeInfo);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void freeze(double money, BusinessEnum businessType,
			String typeInfo, String requestNo)
			throws InsufficientFreezeAmountException {
		PlatformAccount account = platformAccountDao.getWithLock();
		account.setAvailableBalance(account.getAvailableBalance() - money);
		account.setFreezeAmount(account.getFreezeAmount() + money);
		account.setTime(new Date());
		platformAccountDao.update(account);
		this.insertPlatformBill(money, account.getBalance(),
				account.getFreezeAmount(), AccountEnum.freeze, businessType,
				requestNo, typeInfo);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void unfreeze(double money, BusinessEnum businessType,
			String typeInfo, String requestNo) throws InsufficientFreeze {
		PlatformAccount account = platformAccountDao.getWithLock();
		if (ArithUtil.round(account.getFreezeAmount(), 2) < ArithUtil.round(money, 2)) {
			throw new InsufficientFreeze("冻结金额不足！");
		}
		account.setAvailableBalance(account.getAvailableBalance() + money);
		account.setFreezeAmount(account.getFreezeAmount() - money);
		account.setTime(new Date());
		platformAccountDao.update(account);
		this.insertPlatformBill(money, account.getBalance(),
				account.getFreezeAmount(), AccountEnum.unfreeze, businessType,
				requestNo, typeInfo);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void tofreeze(double money, BusinessEnum businessType,
			String typeInfo, String requestNo) throws InsufficientFreeze {
		PlatformAccount account = platformAccountDao.getWithLock();
		if (ArithUtil.round(account.getFreezeAmount(), 2) < ArithUtil.round(money, 2)) {
			throw new InsufficientFreeze("冻结金额不足！");
		}
		account.setBalance(account.getBalance() - money);
		account.setFreezeAmount(account.getFreezeAmount() - money);
		account.setTime(new Date());
		platformAccountDao.update(account);
		this.insertPlatformBill(money, account.getBalance(),
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
	private void insertPlatformBill(double money, double balance,
			double freezeAmount, AccountEnum accountType,
			BusinessEnum businessType, String requestNo, String operatorInfo) {
		// 插入资金流水
		PlatformBill bill = new PlatformBill();
		bill.setBalance(balance);
		bill.setMoney(money);
		bill.setRequestNo(requestNo);
		bill.setType(accountType.toString());
		bill.setTypeInfo(operatorInfo);
		bill.setFreezeAmount(freezeAmount);
		bill.setBusinessType(businessType.toString());
		bill.setTime(new Date());
		platformAccountDao.insert(bill);
	}

	/**
	 * 查询平台账户
	 * 
	 * @return
	 */
	@Override
	public PlatformAccount readPlatformAccount() {
		return platformAccountDao.get();
	}

	/**
	 * 平台账户流水分页，默认查询当天流水，按时间短查询，时间前后差不能超过一个月
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param platformBill
	 * @return
	 * @throws OutOfDateException
	 * @throws QueryTimeTooLongException
	 */
	@Override
	public PageInfo<PlatformBill> readPageLite(int pageNo, int pageSize,
			PlatformBill platformBill) throws QueryTimeTooLongException {
		if (platformBill.getBeginTime() != null
				&& platformBill.getEndTime() != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(platformBill.getBeginTime());
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
			Date nextMonth = calendar.getTime();
			if (nextMonth.getTime() < platformBill.getEndTime().getTime()) {
				throw new QueryTimeTooLongException("查询时间间隔超过一个月！");
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

}
