package com.duanrong.business.account.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.duanrong.util.ArithUtil;
import com.duanrong.util.IdUtil;

import base.exception.InsufficientBalanceException;
import base.exception.InsufficientFreezeAmountException;
import base.exception.NoOpenAccountException;
import base.exception.QueryTimeTooLongException;
import base.pagehelper.PageInfo;

import com.duanrong.business.account.AccountEnum;
import com.duanrong.business.account.BusinessEnum;
import com.duanrong.business.account.dao.UserAccountDao;
import com.duanrong.business.account.model.UserAccount;
import com.duanrong.business.account.model.UserBill;
import com.duanrong.business.account.service.UserAccountService;

@Service
public class UserAccountServiceImpl implements UserAccountService {

	@Resource
	UserAccountDao userAccountDao;

	@Override
	public UserAccount readUserAccount(String userId) {
		UserAccount userAccount;
		try {
			userAccount = userAccountDao.get(userId);
		} catch (NoOpenAccountException e) {
			return null;
		}
		userAccount.setPassword(null);
		return userAccount;
	}

	@Override
	public PageInfo<UserBill> readPageLite(int pageNo, int pageSize,
			UserBill userBill) throws QueryTimeTooLongException {
		if (userBill.getBeginTime() != null && userBill.getEndTime() != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(userBill.getBeginTime());
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
			Date nextMonth = calendar.getTime();
			if (nextMonth.getTime() < userBill.getEndTime().getTime()) {
				throw new QueryTimeTooLongException("查询时间间隔超过一个月！");
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
		if (ArithUtil.round(userAccount.getAvailableBalance(), 2) < ArithUtil
				.round(money, 2))
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
		if (ArithUtil.round(userAccount.getAvailableBalance(), 2) < ArithUtil
				.round(money, 2))
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
		if (ArithUtil.round(userAccount.getFreezeAmount(), 2) < ArithUtil
				.round(money, 2))
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
		if (ArithUtil.round(userAccount.getFreezeAmount(), 2) < ArithUtil
				.round(money, 2))
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

	public PageInfo<UserAccount> readUserAccounts(int pageNo, int pageSize,
			Map params) {
		return userAccountDao.getUserAccounts(pageNo, pageSize, params);
	}

	@Override
	public List<UserBill> readUserBills(String userId) {
		return userAccountDao.getUserBills(userId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void transfer(String userId, int seqNum) throws Exception {
		UserAccount userAccount = userAccountDao.getWithLock(userId);
		if (userAccount == null)
			throw new NoOpenAccountException("userId: " + userId + " 未开户");
		UserBill bill = new UserBill();
		bill.setUserId(userId);
		bill.setSeqNum((long) seqNum);
		List<UserBill> bills = userAccountDao.getUserBillsByWhere(bill);
		if (!bills.isEmpty()) {
			UserBill b2 = userAccountDao.getLastUserBillByUserId(userId);
			for (int i = 0; i < bills.size() - 1; i++) {
				UserBill b1 = bills.get(i);
				b2 = bills.get(i + 1);
				switch (b2.getType()) {
				// 入账
				case "ti_balance":
					b2.setBalance(b1.getBalance() + b2.getMoney());
					b2.setFreezeAmount(b1.getFreezeAmount());
					break;
				// 出账
				case "to_balance":
					b2.setBalance(b1.getBalance() - b2.getMoney());
					b2.setFreezeAmount(b1.getFreezeAmount());
					break;
				// 奖励
				case "pt_balance":
					b2.setBalance(b1.getBalance() + b2.getMoney());
					b2.setFreezeAmount(b1.getFreezeAmount());
					break;
				// 冻结转出
				case "to_frozen":
					b2.setBalance(b1.getBalance() - b2.getMoney());
					b2.setFreezeAmount(b1.getFreezeAmount() - b2.getMoney());
					break;
				// 冻结
				case "freeze":
					b2.setFreezeAmount(b1.getFreezeAmount() + b2.getMoney());
					b2.setBalance(b1.getBalance());
					break;
				// 解冻
				case "unfreeze":
					b2.setFreezeAmount(b1.getFreezeAmount() - b2.getMoney());
					b2.setBalance(b1.getBalance());
					break;
				default:
					break;
				}
				userAccountDao.updateUserBill(b2);
			}
			userAccount.setBalance(b2.getBalance());
			userAccount.setFreezeAmount(b2.getFreezeAmount());
			userAccount.setAvailableBalance(userAccount.getBalance()
					- userAccount.getFreezeAmount());
			userAccountDao.update(userAccount);
		}

	}

}
