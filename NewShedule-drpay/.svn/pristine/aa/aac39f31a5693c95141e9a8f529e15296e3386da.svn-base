package com.duanrong.business.account.service.impl;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import base.exception.InsufficientBalanceException;
import base.exception.InsufficientFreezeAmountException;
import base.exception.NoOpenAccountException;
import base.pagehelper.PageInfo;

import com.duanrong.business.account.AccountEnum;
import com.duanrong.business.account.BusinessEnum;
import com.duanrong.business.account.dao.UserAccountDao;
import com.duanrong.business.account.model.UserAccount;
import com.duanrong.business.account.model.UserBill;
import com.duanrong.business.account.service.UserAccountService;
import com.duanrong.util.ArithUtil;
import com.duanrong.util.IdUtil;

@Service
public class UserAccountServiceImpl implements UserAccountService {

	@Resource
	UserAccountDao userAccountDao;

	@Override
	public UserAccount getUserAccount(String userId)
			throws NoOpenAccountException {
		return userAccountDao.get(userId);
	}
	
	@Override
	public UserAccount queryUserAccountWithLock(String userId)
			throws NoOpenAccountException {
		return userAccountDao.getWithLock(userId);
	}

	@Override
	public PageInfo<UserBill> pageLite(int pageNo, int pageSize,
			UserBill userBill) {
		return userAccountDao.pageLite(pageNo, pageSize, userBill);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void transferIn(String userId, double money,
			BusinessEnum businessType, String operatorInfo,
			String operatorDetail, String requestNo)
			throws NoOpenAccountException {
		// TODO 需要分布式锁来控制用户账户
		UserAccount userAccount = userAccountDao.getWithLock(userId);
		if (userAccount == null)
			throw new NoOpenAccountException("userId: " + userId + " 未开户");
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
			throws NoOpenAccountException {
		UserAccount userAccount = userAccountDao.getWithLock(userId);
		if (userAccount == null)
			throw new NoOpenAccountException("userId: " + userId + " 未开户");
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
			throws NoOpenAccountException, InsufficientBalanceException {
		UserAccount userAccount = userAccountDao.getWithLock(userId);
		if (userAccount == null)
			throw new NoOpenAccountException("userId: " + userId + " 未开户");
		if (ArithUtil.round(userAccount.getAvailableBalance(), 2) < ArithUtil.round(money, 2))
			throw new InsufficientBalanceException("userId: " + userId
					+ "可用余额不足");
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
			throws NoOpenAccountException, InsufficientBalanceException {
		UserAccount userAccount = userAccountDao.getWithLock(userId);
		if (userAccount == null)
			throw new NoOpenAccountException("userId: " + userId + " 未开户");
		if (ArithUtil.round(userAccount.getAvailableBalance(), 2) < ArithUtil.round(money, 2))
			throw new InsufficientBalanceException("userId: " + userId
					+ "可用余额不足");
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
			throws NoOpenAccountException, InsufficientFreezeAmountException {
		UserAccount userAccount = userAccountDao.getWithLock(userId);
		if (userAccount == null)
			throw new NoOpenAccountException("userId: " + userId + " 未开户");
		if (ArithUtil.round(userAccount.getFreezeAmount(), 2) < ArithUtil.round(money, 2))
			throw new InsufficientFreezeAmountException();
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
			throws NoOpenAccountException, InsufficientFreezeAmountException {
		UserAccount userAccount = userAccountDao.getWithLock(userId);
		if (userAccount == null)
			throw new NoOpenAccountException("userId: " + userId + " 未开户");
		if (ArithUtil.round(userAccount.getFreezeAmount(), 2) < ArithUtil.round(money, 2))
			throw new InsufficientFreezeAmountException();
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
	public void createUserAccount(String userId, String password) {
		UserAccount userAccount = new UserAccount();
		userAccount.setUserId(userId);
		userAccount.setAutoInvest(0);
		userAccount.setAutoRepay(0);
		userAccount.setPassword(password);
		userAccount.setAvailableBalance(0D);
		userAccount.setBalance(0D);
		userAccount.setFreezeAmount(0D);
		userAccount.setTime(new Date());
		userAccountDao.insert(userAccount);
	}

	@Override
	public void initUserAccount(String userId, double balance, double freeze) {
		UserAccount userAccount = new UserAccount();
		userAccount.setUserId(userId);
		userAccount.setAutoInvest(0);
		userAccount.setAutoRepay(0);
		userAccount.setPassword(null);
		userAccount.setAvailableBalance(balance);
		userAccount.setBalance(balance + freeze);
		userAccount.setFreezeAmount(freeze);
		userAccount.setTime(new Date());
		userAccountDao.insert(userAccount);
	}

	@Override
	public PageInfo<UserAccount> getUserAccounts(int pageNo, int pageSize,
			Map<String, Object> map) {
		return userAccountDao.getUserAccounts(pageNo, pageSize, map);
	}
}
