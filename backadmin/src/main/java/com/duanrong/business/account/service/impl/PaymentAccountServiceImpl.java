package com.duanrong.business.account.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

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
import com.duanrong.business.account.PaymentEnum;
import com.duanrong.business.account.dao.PaymentAccountDao;
import com.duanrong.business.account.model.PaymentAccount;
import com.duanrong.business.account.model.PaymentBill;
import com.duanrong.business.account.model.PaymentChannel;
import com.duanrong.business.account.service.PaymentAccountService;

@Service
public class PaymentAccountServiceImpl implements PaymentAccountService {

	@Resource
	PaymentAccountDao paymentAccountDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void transferIn(PaymentEnum paymentId, double money,
			BusinessEnum businessType, String typeInfo, String requestNo) {
		PaymentAccount account = paymentAccountDao
				.getWithLock(paymentId.name());
		account.setBalance(account.getBalance() + money);
		account.setAvailableBalance(account.getAvailableBalance() + money);
		account.setTime(new Date());
		paymentAccountDao.update(account);
		this.insertPaymentBill(paymentId, money, account.getBalance(),
				account.getFreezeAmount(), AccountEnum.ti_balance,
				businessType, requestNo, typeInfo);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void transferOut(PaymentEnum paymentId, double money,
			BusinessEnum businessType, String typeInfo, String requestNo)
			throws InsufficientBalance {
		PaymentAccount account = paymentAccountDao
				.getWithLock(paymentId.name());
		account.setAvailableBalance(account.getAvailableBalance() - money);
		account.setBalance(account.getBalance() - money);
		account.setTime(new Date());
		paymentAccountDao.update(account);
		this.insertPaymentBill(paymentId, money, account.getBalance(),
				account.getFreezeAmount(), AccountEnum.to_balance,
				businessType, requestNo, typeInfo);
	}
	
	
	
	/**
	 * 插入支付流水
	 * 
	 * @param pamenyId
	 * @param money
	 * @param balance
	 * @param freezeAmount
	 * @param accountType
	 * @param businessType
	 * @param requestNo
	 * @param operatorInfo
	 */
	private void insertPaymentBill(PaymentEnum paymeny, double money,
			double balance, double freezeAmount, AccountEnum accountType,
			BusinessEnum businessType, String requestNo, String operatorInfo) {
		PaymentBill paymentBill = new PaymentBill();
		paymentBill.setBalance(balance);
		paymentBill.setChannel(paymeny.name());
		paymentBill.setMoney(money);
		paymentBill.setRequestNo(requestNo);
		paymentBill.setBusinessType(businessType.toString());
		paymentBill.setType(accountType.name());
		paymentBill.setTypeInfo(operatorInfo);
		paymentBill.setFreezeAmount(freezeAmount);
		paymentAccountDao.insert(paymentBill);
	}
	

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void freeze(PaymentEnum paymentId, double money,
			BusinessEnum businessType, String typeInfo, String requestNo)
			throws InsufficientFreezeAmountException {
		PaymentAccount account = paymentAccountDao
				.getWithLock(paymentId.name());
		account.setAvailableBalance(account.getAvailableBalance() - money);
		account.setFreezeAmount(account.getFreezeAmount() + money);
		account.setTime(new Date());
		paymentAccountDao.update(account);
		this.insertPaymentBill(paymentId, money, account.getBalance(),
				account.getFreezeAmount(), AccountEnum.freeze,
				businessType, requestNo, typeInfo);
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void unfreeze(PaymentEnum paymentId, double money,
			BusinessEnum businessType, String typeInfo, String requestNo)
			throws InsufficientFreeze {
		PaymentAccount account = paymentAccountDao
				.getWithLock(paymentId.name());
		account.setAvailableBalance(account.getAvailableBalance() + money);
		account.setFreezeAmount(account.getFreezeAmount() - money);
		account.setTime(new Date());
		paymentAccountDao.update(account);
		this.insertPaymentBill(paymentId, money, account.getBalance(),
				account.getFreezeAmount(), AccountEnum.unfreeze,
				businessType, requestNo, typeInfo);
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void tofreeze(PaymentEnum paymentId, double money,
			BusinessEnum businessType, String typeInfo, String requestNo)
			throws InsufficientFreeze {
		PaymentAccount account = paymentAccountDao
				.getWithLock(paymentId.name());
		account.setBalance(account.getBalance() - money);
		account.setFreezeAmount(account.getFreezeAmount() + money);
		account.setTime(new Date());
		paymentAccountDao.update(account);
		this.insertPaymentBill(paymentId, money, account.getBalance(),
				account.getFreezeAmount(), AccountEnum.to_frozen,
				businessType, requestNo, typeInfo);
		
	}
	
	/**
	 * 第三方账户信息
	 * 
	 * @return
	 */
	@Override
	public List<PaymentAccount> readPaymentAccount() {
		return paymentAccountDao.findAll();
	}

	/**
	 * 第三方账户流水
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param paymentBill
	 * @return
	 * @throws QueryTimeTooLongException
	 * @throws OutOfDateException
	 */
	@Override
	public PageInfo<PaymentBill> readPageLite(int pageNo, int pageSize,
			PaymentBill paymentBill) throws QueryTimeTooLongException {
		if (paymentBill.getBeginTime() != null
				&& paymentBill.getEndTime() != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(paymentBill.getBeginTime());
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
			Date nextMonth = calendar.getTime();
			if (nextMonth.getTime() < paymentBill.getEndTime().getTime()) {
				throw new QueryTimeTooLongException("查询时间间隔超过一个月！");
			}
		}
		if (paymentBill.getBeginTime() == null
				&& paymentBill.getEndTime() == null) {
			Calendar c1 = new GregorianCalendar();
			c1.set(Calendar.HOUR_OF_DAY, 0);
			c1.set(Calendar.MINUTE, 0);
			c1.set(Calendar.SECOND, 0);
			paymentBill.setBeginTime(c1.getTime());
			Calendar c2 = new GregorianCalendar();
			c2.set(Calendar.HOUR_OF_DAY, 23);
			c2.set(Calendar.MINUTE, 59);
			c2.set(Calendar.SECOND, 59);
			paymentBill.setEndTime(c2.getTime());
		}
		if (paymentBill.getBeginTime() != null
				&& paymentBill.getEndTime() == null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(paymentBill.getBeginTime());
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
			Date nextMonth = calendar.getTime();
			paymentBill.setEndTime(nextMonth);
		}
		if (paymentBill.getBeginTime() == null
				&& paymentBill.getEndTime() != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(paymentBill.getEndTime());
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
			Date preMonth = calendar.getTime();
			paymentBill.setEndTime(preMonth);
		}
		return paymentAccountDao.pageLite(pageNo, pageSize, paymentBill);
	}

	@Override
	public PaymentChannel readChannelByCode(String code) {
		return paymentAccountDao.getChannelByCode(code);
	}

	@Override
	public PaymentAccount readPaymentAccountByPaymentId(String pamenyId) {
		return paymentAccountDao.get(pamenyId);
	}

}
