package com.duanrong.business.user.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import base.exception.InsufficientBalance;
import base.exception.NoOpenAccountException;
import base.model.PageData;
import base.pagehelper.PageInfo;

import com.duanrong.business.account.BusinessEnum;
import com.duanrong.business.account.model.UserAccount;
import com.duanrong.business.account.service.UserAccountService;
import com.duanrong.business.user.UserConstants;
import com.duanrong.business.user.dao.UserMoneyDao;
import com.duanrong.business.user.model.UserMoney;
import com.duanrong.business.user.service.UserMoneyService;
import com.duanrong.util.ArithUtil;

/**
 * 用户资金实现类
 * 
 * @author 尹逊志
 * @date 2014-8-29下午1:32:07
 */
@Service
public class UserMoneyServiceImpl implements UserMoneyService {
	@Resource
	UserMoneyDao userMoneyDao;
	
	@Resource
	UserAccountService userAccountService;

	/*public Double getBalance(String userId) {
		double freeze = getSumByType(userId, UserConstants.UserMoneyType.FREEZE);
		double transferIntoBalance = getSumByType(userId,
				UserConstants.UserMoneyType.TI_BALANCE);
		double transferOutFromBalance = getSumByType(userId,
				UserConstants.UserMoneyType.TO_BALANCE);
		double platformTransferBalance = getSumByType(userId,
				UserConstants.UserMoneyType.PT_BALANCE);
		double unfreeze = getSumByType(userId,
				UserConstants.UserMoneyType.UNFREEZE);
		double balance = ArithUtil.add(ArithUtil.sub(ArithUtil.sub(
				ArithUtil.add(transferIntoBalance, platformTransferBalance),
				transferOutFromBalance), freeze), unfreeze);
		return ArithUtil.round(balance, 2);
	}

	public Double getFrozenMoney(String userId) {
		double freeze = getSumByType(userId, UserConstants.UserMoneyType.FREEZE);
		double transferOutFromFrozen = getSumByType(userId,
				UserConstants.UserMoneyType.TO_FROZEN);
		double unfreeze = getSumByType(userId,
				UserConstants.UserMoneyType.UNFREEZE);
		double frozenMoney = ArithUtil.sub(ArithUtil.sub(freeze, unfreeze),
				transferOutFromFrozen);
		return ArithUtil.round(frozenMoney, 2);
	}
*/
	/**
	 * 根据用户Id和资金类型获取资金总额
	 * 
	 * @param userId
	 * @param tiBalance
	 * @return
	 * @throws Exception 
	 */
//	private double getSumByType(String userId, String type) {
//		return userMoneyDao.getMoneyByType(userId, type) == null ? 0
//				: userMoneyDao.getMoneyByType(userId, type);
//	}

	/*@Override
	public void freezeMoney(String userId, double money, String operatorInfo,
			String operatorDetail) throws Exception {
		try {
			userAccountService.transferIn(userId, money, BusinessEnum.reward, operatorInfo, operatorDetail, "");
		
			//UserAccount ua = userAccountService.getUserAccount(userId);
			//System.out.println(ua.toString());
		
			
			
		} catch (NoOpenAccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

	/*@Override
	public void unfreezeMoney(String userId, double money, String operatorInfo,
			String operatorDetail) throws InsufficientBalance {
		insertUserMoneyUnFreezeOrFreeze(userId, money, operatorInfo,
				operatorDetail, "unfreezeMoney");
	}*/

	/*private void insertUserMoneyUnFreezeOrFreeze(String userId, double money,
			String operatorInfo, String operatorDetail, String type)
			throws InsufficientBalance {
		UserMoney userMoney = new UserMoney();

		if (StringUtils.equals("freezeMoney", type)) {
			double balance = getBalance(userId);
			if (balance < money) {
				throw new InsufficientBalance("freeze money:" + money
						+ ", balance:" + balance);
			}
		} else if (StringUtils.equals("unfreezeMoney", type)) {
			double frozen = getFrozenMoney(userId);
			if (frozen < money) {
				throw new InsufficientBalance("unfreeze money:" + money
						+ ", frozen money:" + frozen);
			}
		}

		userMoney.setId(IdGenerator.randomUUID());
		userMoney.setMoney(money);
		userMoney.setTime(new Date());
		userMoney.setDetail(operatorDetail);
		if (StringUtils.equals("freezeMoney", type)) {
			userMoney.setType(UserConstants.UserMoneyType.FREEZE);
		} else if (StringUtils.equals("unfreezeMoney", type)) {
			userMoney.setType(UserConstants.UserMoneyType.UNFREEZE);
		}
		userMoney.setTypeInfo(operatorInfo);
		userMoney.setUserId(userId);
		if (UserMoneyLastest == null) {
			userMoney.setSeqNum(1L);
		} else {
			userMoney.setSeqNum(UserMoneyLastest.getSeqNum() + 1);
		}
		userMoneyDao.insert(userMoney);
	}*/

//	public UserMoney getLastestUserMoney(String userId) {
//		/*
//		 * DetachedCriteria criteria =
//		 * DetachedCriteria.forClass(UserBill.class);
//		 * criteria.addOrder(Order.desc("seqNum"));
//		 * criteria.setLockMode(LockMode.UPGRADE);
//		 * criteria.add(Restrictions.eq("user.id", userId)); List<UserBill> ibs
//		 * = ht.findByCriteria(criteria, 0, 1); if (ibs.size() > 0) { return
//		 * ibs.get(0); }
//		 */
//
//		// 如何加锁的问题
//		List<UserMoney> userMoneyList = userMoneyDao
//				.getLastestUserMoney(userId);
//		UserMoney userMoney;
//		if (userMoneyList != null && userMoneyList.size() > 0) {
//			userMoney = userMoneyList.get(0);
//			return userMoney;
//		}
//
//		return null;
//	}

	/*public Double getAwardTotalMoney(String userId) {
		Double awardTotalMoney = userMoneyDao.getAwardTotalMoney(userId);
		if (awardTotalMoney == null) {
			return 0.00;
		}

		return ArithUtil.round(awardTotalMoney.doubleValue(), 2);
	}*/

	/*public List<UserMoney> getAwardDetail(String userId) {
		return userMoneyDao.getAwardDetail(userId);
	}*/

	/**
	 * 分页查询
	 * 
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页显示的记录数
	 */
//	public PageData<UserMoney> findPaging(int pageNo, int pageSize, UserMoney um) {
//		return userMoneyDao.findPaging(pageNo, pageSize, um);
//	}
	//@Override
	/*public void transferOutFromBalance(String userId, double money,
			String operatorInfo, String operatorDetail)
			throws InsufficientBalance {
		UserMoney ibLastest = getLastestUserMoney(userId);
		UserMoney userMoney = new UserMoney();
		double balance = getBalance(userId);
		if (balance < money) {
			throw new InsufficientBalance("transfer out money:" + money
					+ ",balance:" + balance);
		} else {
			saveUserMoney(userId, money, operatorInfo, operatorDetail,
					ibLastest, userMoney,
					UserConstants.UserMoneyType.TO_BALANCE);
		}

	}*/

	/*public void saveUserMoney(String userId, double money, String operatorInfo,
			String operatorDetail, UserMoney ibLastest, UserMoney userMoney,
			String type) {
		userMoney.setId(IdGenerator.randomUUID());
		userMoney.setMoney(money);
		userMoney.setTime(new Date());
		userMoney.setDetail(operatorDetail);
		userMoney.setType(type);
		userMoney.setTypeInfo(operatorInfo);
		userMoney.setUserId(userId);
		if (ibLastest == null) {
			userMoney.setSeqNum(1L);
		} else {
			userMoney.setSeqNum(ibLastest.getSeqNum() + 1);
		}
		userMoneyDao.insert(userMoney);
	}*/

	/*@Override
	public void transferIntoBalance(String userId, double money,
			String operatorInfo, String operatorDetail) {
		UserMoney ibLastest = getLastestUserMoney(userId);
		UserMoney userMoney = new UserMoney();
		saveUserMoney(userId, money, operatorInfo, operatorDetail, ibLastest,
				userMoney, UserConstants.UserMoneyType.TI_BALANCE);
	}

	@Override
	public void platformTransferIntoBalance(String userId, double money,
			String operatorInfo, String operatorDetail) {
		UserMoney ibLastest = getLastestUserMoney(userId);
		UserMoney userMoney = new UserMoney();
		saveUserMoney(userId, money, operatorInfo, operatorDetail, ibLastest,
				userMoney, UserConstants.UserMoneyType.PT_BALANCE);
	}*/

	/*@Override
	public PageInfo<UserMoney> findPageInfo(int pageNo, int pageSize, Map map) {
		return userMoneyDao.pageInfo(pageNo, pageSize, map);
	}
*/
	/*@Override
	public void transferOutFrozenToManagementCost(String userId, double money,
			String operatorInfo, String operatorDetail) {
		UserMoney ibLastest = getLastestUserMoney(userId);
		UserMoney userMoney = new UserMoney();
		saveUserMoney(userId, money, operatorInfo, operatorDetail, ibLastest,
				userMoney, UserConstants.UserMoneyType.MANAGEMENT);
	}

	@Override
	public void transferOutFromFrozen(String userId, double money,
			String operatorInfo, String operatorDetail)
			throws InsufficientBalance {
		UserMoney ibLastest = getLastestUserMoney(userId);
		UserMoney userMoney = new UserMoney();
		double frozen = getFrozenMoney(userId);
		if (frozen < money) {
			throw new InsufficientBalance("transfer from frozen money:" + money
					+ ", frozen money:" + frozen);
		}
		saveUserMoney(userId, money, operatorInfo, operatorDetail, ibLastest,
				userMoney, UserConstants.UserMoneyType.TO_FROZEN);
	}

	@Override
	public void transferOutFromFrozen(String userId, double money,
			String operatorInfo, String operatorDetail, Date date)
			throws InsufficientBalance {
		UserMoney ibLastest = getLastestUserMoney(userId);
		UserMoney userMoney = new UserMoney();
		double frozen = getFrozenMoney(userId);
		if (frozen < money) {
			throw new InsufficientBalance("transfer from frozen money:" + money
					+ ", frozen money:" + frozen);
		}
		saveUserMoney(userId, money, operatorInfo, operatorDetail, ibLastest,
				userMoney, UserConstants.UserMoneyType.TO_FROZEN, date);

	}*/

	/*public void saveUserMoney(String userId, double money, String operatorInfo,
			String operatorDetail, UserMoney ibLastest, UserMoney userMoney,
			String type, Date date) {
		userMoney.setId(IdGenerator.randomUUID());
		userMoney.setMoney(money);
		userMoney.setTime(date);
		userMoney.setDetail(operatorDetail);
		userMoney.setType(type);
		userMoney.setTypeInfo(operatorInfo);
		userMoney.setUserId(userId);
		if (ibLastest == null) {
			userMoney.setSeqNum(1L);
		} else {
			userMoney.setSeqNum(ibLastest.getSeqNum() + 1);
		}
		userMoneyDao.insert(userMoney);
	}
*/
	/*@Override
	public void transferOutFrozenToManagementCost(String borrowMoneyUserID,
			Double loanGuranteeFee, String operatorInfo, String operatorDetail,
			Date date) {
		UserMoney ibLastest = getLastestUserMoney(borrowMoneyUserID);
		UserMoney userMoney = new UserMoney();
		saveUserMoney(borrowMoneyUserID, loanGuranteeFee, operatorInfo,
				operatorDetail, ibLastest, userMoney,
				UserConstants.UserMoneyType.MANAGEMENT, date);
	}

	@Override
	public void unfreezeMoney(String borrowMoneyUserID, double abs,
			String operatorInfo, String operatorDetail, Date date)
			throws InsufficientBalance {
		insertUserMoneyUnFreezeOrFreeze(borrowMoneyUserID, abs, operatorInfo,
				operatorDetail, "unfreezeMoney", date);
	}
*/
	/*private void insertUserMoneyUnFreezeOrFreeze(String userId, double money,
			String operatorInfo, String operatorDetail, String type, Date date)
			throws InsufficientBalance {
		UserMoney UserMoneyLastest = getLastestUserMoney(userId);
		UserMoney userMoney = new UserMoney();

		if (StringUtils.equals("freezeMoney", type)) {
			double balance = getBalance(userId);
			if (balance < money) {
				throw new InsufficientBalance("freeze money:" + money
						+ ", balance:" + balance);
			}
		} else if (StringUtils.equals("unfreezeMoney", type)) {
			double frozen = getFrozenMoney(userId);
			if (frozen < money) {
				throw new InsufficientBalance("unfreeze money:" + money
						+ ", frozen money:" + frozen);
			}
		}

		userMoney.setId(IdGenerator.randomUUID());
		userMoney.setMoney(money);
		userMoney.setTime(date);
		userMoney.setDetail(operatorDetail);
		if (StringUtils.equals("freezeMoney", type)) {
			userMoney.setType(UserConstants.UserMoneyType.FREEZE);
		} else if (StringUtils.equals("unfreezeMoney", type)) {
			userMoney.setType(UserConstants.UserMoneyType.UNFREEZE);
		}
		userMoney.setTypeInfo(operatorInfo);
		userMoney.setUserId(userId);
		if (UserMoneyLastest == null) {
			userMoney.setSeqNum(1L);
		} else {
			userMoney.setSeqNum(UserMoneyLastest.getSeqNum() + 1);
		}
		userMoneyDao.insert(userMoney);
	}
*/
	/*@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void transferIntoBalance(String userId, double money,
			String operatorInfo, String operatorDetail, Date date) {
		UserMoney ibLastest = getLastestUserMoney(userId);
		UserMoney userMoney = new UserMoney();
		saveUserMoney(userId, money, operatorInfo, operatorDetail, ibLastest,
				userMoney, UserConstants.UserMoneyType.TI_BALANCE, date);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void transferOutFromBalance(String userId, double money,
			String operatorInfo, String operatorDetail, Date date)
			throws InsufficientBalance {
		UserMoney ibLastest = getLastestUserMoney(userId);
		UserMoney userMoney = new UserMoney();
		double balance = getBalance(userId);
		if (balance < money) {
			throw new InsufficientBalance("transfer out money:" + money
					+ ",balance:" + balance);
		} else {
			saveUserMoney(userId, money, operatorInfo, operatorDetail,
					ibLastest, userMoney,
					UserConstants.UserMoneyType.TO_BALANCE, date);
		}
	}*/

	@Override
	public Map<String, Object> queryUserMoney(String userId) {
		return userMoneyDao.queryUserMoney(userId);
	}
}
