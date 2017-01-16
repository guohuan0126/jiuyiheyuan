package com.duanrong.drpay.business.account.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.error.ErrorCode;
import base.exception.UserAccountException;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.drpay.business.account.dao.UserAccountDao;
import com.duanrong.drpay.business.account.model.UserAccount;
import com.duanrong.drpay.business.account.model.UserBill;

@Repository
public class UserAccountDaoImpl extends BaseDaoImpl<UserAccount> implements
		UserAccountDao {

	public UserAccountDaoImpl() {
		this.setMapperNameSpace("com.duanrong.drpay.business.account.mapper.UserAccountMapper");
	}

	@Override
	public UserAccount get(String id) throws UserAccountException {
		UserAccount userAccount = super.get(id);
		return userAccount;
	}

	@Override
	public UserAccount getWithLock(String userId) throws UserAccountException {
		UserAccount userAccount = this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getWithLock", userId);
		if (userAccount == null) {
			throw new UserAccountException(ErrorCode.UserAccountNoOpened);
		}
		userAccount.setPassword(null);
		return userAccount;
	}

	
	@Override
	public UserBill getLastUserBillByUserId(String userId) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getLastUserBillByUserId", userId);
	}

	@Override
	public void insertUserBill(UserBill userBill) {
		this.getSqlSession().insert(
				this.getMapperNameSpace() + ".insertUserBill", userBill);
	}

	@Override
	public void freezeAccount(String userId) {
		this.getSqlSession().update(this.getMapperNameSpace() + ".freezeAccount", userId);
		
	}


	@Override
	public void unfreezeAccount(String userId) {
		this.getSqlSession().update(this.getMapperNameSpace() + ".unfreezeAccount", userId);
	}

	
	@Override
	public PageInfo<UserBill> pageLite(int pageNo, int pageSize,
			UserBill userBill) {
		PageHelper.startPage(pageNo, pageSize);
		List<UserBill> list = getSqlSession().selectList(
				this.getMapperNameSpace() + ".pageLite", userBill);
		PageInfo<UserBill> pageInfo = new PageInfo<>(list);
		if (pageNo > pageInfo.getLastPage()) {
			pageInfo.setResults(null);
		}
		return pageInfo;
	}
	
	
	@Override
	public UserAccount getUserMoney(String userId) {		
		Map<String, Object> map = new HashMap<>();
		UserAccount userAccount = new UserAccount();
		userAccount.setUserId(userId);
		map.put("userId", userId);
		map.put("balance", 0.0);
		map.put("forzen", 0.0);
		getSqlSession().selectOne(this.getMapperNameSpace() + ".getBalance", map);
		userAccount.setAvailableBalance((double)map.get("balance"));
		userAccount.setFreezeAmount((double)map.get("forzen"));
		userAccount.setBalance(userAccount.getAvailableBalance() + userAccount.getFreezeAmount());
		return userAccount;
	}

}