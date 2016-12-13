package com.duanrong.business.account.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.exception.NoOpenAccountException;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.business.account.dao.UserAccountDao;
import com.duanrong.business.account.model.UserAccount;
import com.duanrong.business.account.model.UserBill;

@Repository
public class UserAccountDaoImpl extends BaseDaoImpl<UserAccount> implements
		UserAccountDao {

	public UserAccountDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.account.mapper.UserAccountMapper");
	}

	@Override
	public UserAccount get(String userId) throws NoOpenAccountException {
		UserAccount userAccount = this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".get", userId);
		if (userAccount == null)
			throw new NoOpenAccountException("userId: " + userId
					+ " 未开户");
		userAccount.setPassword(null);
		return userAccount;
	}
	
	@Override
	public UserAccount getWithLock(String userId) throws NoOpenAccountException {
		UserAccount userAccount = this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getWithLock", userId);
		if (userAccount == null)
			throw new NoOpenAccountException("userId: " + userId
					+ " 未开户");
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

	public PageInfo<UserAccount> getUserAccounts(int pageNo, int pageSize,Map params) {
		PageHelper.startPage(pageNo, pageSize);

		List<UserAccount> list = this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getUserAccounts", params);
		PageInfo<UserAccount> pageInfo = new PageInfo<>(list);
		if (pageNo > pageInfo.getLastPage()) {
			pageInfo.setResults(null);
		}
		return pageInfo;
	}

	@Override
	public List<UserBill> getUserBills(String userId) {
		return this.getSqlSession().selectList(this.getMapperNameSpace()+".getUserBills", userId);
	}

	@Override
	public List<UserBill> getUserBillsByWhere(UserBill userBill) {
		
		return this.getSqlSession().selectList(this.getMapperNameSpace()+".getUserBillsByWhere", userBill);
	}

	@Override
	public void updateUserBill(UserBill userBill) {
		this.getSqlSession().update(this.getMapperNameSpace()+".updateUserBill", userBill);	
	}

}
