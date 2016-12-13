package com.duanrong.business.account.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import base.exception.InsufficientBalance;
import base.exception.InsufficientFreeze;
import base.exception.InsufficientFreezeAmountException;
import base.pagehelper.PageInfo;

import com.duanrong.business.account.AccountEnum;
import com.duanrong.business.account.BusinessEnum;
import com.duanrong.business.account.PaymentEnum;
import com.duanrong.business.account.dao.PaymentAccountDao;
import com.duanrong.business.account.model.PaymentAccount;
import com.duanrong.business.account.model.PaymentBill;
import com.duanrong.business.account.service.PaymentAccountService;

@Service
public class PaymentAccountServiceImpl implements PaymentAccountService {

	@Resource
	PaymentAccountDao paymentAccountDao;
	
	@Override
	public List<PaymentAccount> getPaymentAccounts() {
		return paymentAccountDao.findAll();
	}

	@Override
	public PaymentAccount getPaymentAccount(PaymentEnum paymentType) {
		return paymentAccountDao.get(paymentType.name());
	}

	@Override
	public PaymentAccount getPaymentAccountwithLock(PaymentEnum paymentType) {
		return paymentAccountDao.getWithLock(paymentType.name());
	}

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
	public PageInfo<PaymentBill> pageLite(int pageNo, int pageSize,
			PaymentBill paymentBill) {
		return null;
	}

	
}