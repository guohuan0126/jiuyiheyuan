package com.duanrong.drpay.business.account.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import util.ArithUtil;
import base.error.ErrorCode;
import base.exception.ParameterException;
import base.exception.UserAccountException;
import base.pagehelper.PageInfo;

import com.duanrong.drpay.business.account.AccountEnum;
import com.duanrong.drpay.business.account.dao.UserAccountDao;
import com.duanrong.drpay.business.account.model.UserAccount;
import com.duanrong.drpay.business.account.model.UserBill;
import com.duanrong.drpay.business.account.service.UserAccountService;
import com.duanrong.drpay.config.BusinessEnum;
import com.duanrong.drpay.config.IdUtil;

@Service
public class UserAccountServiceImpl implements UserAccountService {

	@Resource
	UserAccountDao userAccountDao;

	@Override
	public UserAccount getUserAccount(String userId)
			throws UserAccountException {
		return userAccountDao.get(userId);
	}

	@Override
	public UserAccount queryUserAccountWithLock(String userId)
			throws UserAccountException {
		return userAccountDao.getWithLock(userId);
	}

	@Override
	public PageInfo<UserBill> pageLite(int pageNo, int pageSize,
			UserBill userBill) throws ParameterException {
		if (userBill.getBeginTime() != null && userBill.getEndTime() != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(userBill.getBeginTime());
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
			Date nextMonth = calendar.getTime();
			if (nextMonth.getTime() < userBill.getEndTime().getTime()) {
				throw new ParameterException(ErrorCode.QueryTimeTooLong);
			}
		}
		if (userBill.getBeginTime() == null && userBill.getEndTime() == null) {
			Calendar c1 = new GregorianCalendar();
			c1.set(Calendar.HOUR_OF_DAY, 0);
			c1.set(Calendar.MINUTE, 0);
			c1.set(Calendar.SECOND, 0);
			userBill.setBeginTime(c1.getTime());
			Calendar c2 = new GregorianCalendar();
			c2.set(Calendar.HOUR_OF_DAY, 23);
			c2.set(Calendar.MINUTE, 59);
			c2.set(Calendar.SECOND, 59);
			userBill.setEndTime(c2.getTime());
		}
		if (userBill.getBeginTime() != null && userBill.getEndTime() == null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(userBill.getBeginTime());
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
			Date nextMonth = calendar.getTime();
			userBill.setEndTime(nextMonth);
		}
		if (userBill.getBeginTime() == null && userBill.getEndTime() != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(userBill.getEndTime());
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
			Date preMonth = calendar.getTime();
			userBill.setEndTime(preMonth);
		}
		return userAccountDao.pageLite(pageNo, pageSize, userBill);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void transferIn(String userId, double money,
			BusinessEnum businessType, String operatorInfo,
			String operatorDetail, String requestNo)
			throws UserAccountException {
		UserAccount userAccount = userAccountDao.getWithLock(userId);
		// 更新可用余额
		userAccount.setAvailableBalance(userAccount.getAvailableBalance()
				+ money);
		// 更新总金额
		userAccount.setBalance(userAccount.getBalance() + money);
		// 设置最后更新时间
		userAccount.setTime(new Date());
		userAccountDao.update(userAccount);
		this.insertUserBill(userId, money, userAccount.getBalance(),
				userAccount.getFreezeAmount(), AccountEnum.ti_balance,
				businessType, requestNo, operatorDetail, operatorInfo);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void ptTransferIn(String userId, double money,
			BusinessEnum businessType, String operatorInfo,
			String operatorDetail, String requestNo)
			throws UserAccountException {
		UserAccount userAccount = userAccountDao.getWithLock(userId);
		// 更新可用余额
		userAccount.setAvailableBalance(userAccount.getAvailableBalance()
				+ money);
		// 更新总金额
		userAccount.setBalance(userAccount.getBalance() + money);
		// 设置最后更新时间
		userAccount.setTime(new Date());
		userAccountDao.update(userAccount);
		this.insertUserBill(userId, money, userAccount.getBalance(),
				userAccount.getFreezeAmount(), AccountEnum.pt_balance,
				businessType, requestNo, operatorDetail, operatorInfo);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void transferOut(String userId, double money,
			BusinessEnum businessType, String operatorInfo,
			String operatorDetail, String requestNo)
			throws UserAccountException {
		UserAccount userAccount = userAccountDao.get(userId);
		if (ArithUtil.round(userAccount.getAvailableBalance(), 2) < ArithUtil.round(money, 2))
			throw new UserAccountException(ErrorCode.BalanceToLow);
		// 更新可用余额
		userAccount.setAvailableBalance(userAccount.getAvailableBalance()
				- money);
		// 更新总金额
		userAccount.setBalance(userAccount.getBalance() - money);
		// 设置最后更新时间
		userAccount.setTime(new Date());
		userAccountDao.update(userAccount);
		this.insertUserBill(userId, money, userAccount.getBalance(),
				userAccount.getFreezeAmount(), AccountEnum.to_balance,
				businessType, requestNo, operatorDetail, operatorInfo);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void freeze(String userId, double money, BusinessEnum businessType,
			String operatorInfo, String operatorDetail, String requestNo)
			throws UserAccountException {
		UserAccount userAccount = userAccountDao.getWithLock(userId);
		if (ArithUtil.round(userAccount.getAvailableBalance(), 2) < ArithUtil.round(money, 2))
			throw new UserAccountException(ErrorCode.BalanceToLow);
		// 更新可用余额
		userAccount.setAvailableBalance(userAccount.getAvailableBalance()
				- money);
		// 更新冻结金额
		userAccount.setFreezeAmount(userAccount.getFreezeAmount() + money);
		// 设置最后更新时间
		userAccount.setTime(new Date());
		userAccountDao.update(userAccount);
		this.insertUserBill(userId, money, userAccount.getBalance(),
				userAccount.getFreezeAmount(), AccountEnum.freeze,
				businessType, requestNo, operatorDetail, operatorInfo);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void unfreeze(String userId, double money,
			BusinessEnum businessType, String operatorInfo,
			String operatorDetail, String requestNo)
			throws UserAccountException {
		UserAccount userAccount = userAccountDao.getWithLock(userId);
		if (ArithUtil.round(userAccount.getFreezeAmount(), 2) < ArithUtil.round(money, 2))
			throw new UserAccountException(ErrorCode.FrozeToLow);
		// 更新可用余额
		userAccount.setAvailableBalance(userAccount.getAvailableBalance()
				+ money);
		// 更新冻结金额
		userAccount.setFreezeAmount(userAccount.getFreezeAmount() - money);
		// 设置最后更新时间
		userAccount.setTime(new Date());
		userAccountDao.update(userAccount);
		this.insertUserBill(userId, money, userAccount.getBalance(),
				userAccount.getFreezeAmount(), AccountEnum.unfreeze,
				businessType, requestNo, operatorDetail, operatorInfo);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void tofreeze(String userId, double money,
			BusinessEnum businessType, String operatorInfo,
			String operatorDetail, String requestNo)
			throws UserAccountException {
		UserAccount userAccount = userAccountDao.get(userId);
		if (ArithUtil.round(userAccount.getFreezeAmount(), 2) < ArithUtil.round(money, 2))
			throw new UserAccountException(ErrorCode.FrozeToLow);
		// 更新总金额
		userAccount.setBalance(userAccount.getBalance() - money);
		// 更新冻结金额
		userAccount.setFreezeAmount(userAccount.getFreezeAmount() - money);
		// 设置最后更新时间
		userAccount.setTime(new Date());
		userAccountDao.update(userAccount);
		this.insertUserBill(userId, money, userAccount.getBalance(),
				userAccount.getFreezeAmount(), AccountEnum.to_frozen,
				businessType, requestNo, operatorDetail, operatorInfo);
	}

	/**
	 * 插入资金流水
	 * 
	 * @param userId
	 *            用户id
	 * @param money
	 *            变动资金
	 * @param balance
	 *            账户余额
	 * @param freezeAmount
	 *            冻结金额
	 * @param accountType
	 *            变动类型
	 * @param businessType
	 *            业务类型
	 * @param requestNo
	 *            流水号
	 * @param operatorDetail
	 *            描述
	 * @param operatorInfo
	 *            描述(用户看)
	 */
	private void insertUserBill(String userId, double money, double balance,
			double freezeAmount, AccountEnum accountType,
			BusinessEnum businessType, String requestNo, String operatorDetail,
			String operatorInfo) {
		// 插入资金流水
		UserBill userBill = new UserBill();
		userBill.setId(IdUtil.randomUUID());
		userBill.setMoney(money);
		userBill.setBalance(balance);
		userBill.setFreezeAmount(freezeAmount);
		userBill.setDetail(operatorDetail);
		userBill.setType(accountType.toString());
		userBill.setTypeInfo(operatorInfo);
		userBill.setBusinessType(businessType.toString());
		userBill.setRequestNo(requestNo);
		userBill.setUserId(userId);
		userBill.setTime(new Date());
		long seqNum = 1L;
		UserBill ub = userAccountDao.getLastUserBillByUserId(userId);
		if (ub != null)
			seqNum += ub.getSeqNum();
		userBill.setSeqNum(seqNum);
		userAccountDao.insertUserBill(userBill);
	}

	@Override
	public void createUserAccount(String userId, String password, String authorization)
			throws UserAccountException {
		UserAccount userAccount = userAccountDao.get(userId);
		if (userAccount != null)
			throw new UserAccountException(ErrorCode.UserAccountOpened);
		userAccount = new UserAccount();
		userAccount.setUserId(userId);
		userAccount.setAutoInvest(0);
		userAccount.setAutoRepay(0);
		userAccount.setPassword(password);
		userAccount.setAvailableBalance(0D);
		userAccount.setBalance(0D);
		userAccount.setFreezeAmount(0D);
		if ("autoInvest".equals(authorization)) {
			userAccount.setAutoInvest(1);
		} else if ("autoRepay".equals(authorization)) {
			userAccount.setAutoRepay(1);
			userAccount.setAutoRecharge(1);
		}
		userAccount.setStatus(1);
		userAccount.setTime(new Date());
		userAccountDao.insert(userAccount);
	}

	@Override
	public UserBill getLastUserBillByUserId(String userId) {
		return userAccountDao.getLastUserBillByUserId(userId);
	}

	@Override
	public UserAccount getUserMoney(String userId) {
		return userAccountDao.getUserMoney(userId);
	}

	@Override
	public void updateUserAccount(UserAccount userAccount) {
		userAccountDao.update(userAccount);
	}

}