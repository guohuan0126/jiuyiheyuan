package com.duanrong.business.user.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.business.user.dao.AccountCheckingDao;
import com.duanrong.business.user.model.AccountChecking;

@Repository
public class AccountCheckingDaoImpl extends BaseDaoImpl<AccountChecking>
		implements AccountCheckingDao {
	public AccountCheckingDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.user.mapper.AccountCheckingMapper"); // 设置命名空间
	}

	@Override
	public List<AccountChecking> getAccountListByWhere() {
		return getSqlSession().selectList(
				this.getMapperNameSpace() + ".getAccountListByWhere");
	}

}
