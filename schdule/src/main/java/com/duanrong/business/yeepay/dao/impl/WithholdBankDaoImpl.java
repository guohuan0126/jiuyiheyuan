package com.duanrong.business.yeepay.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.business.yeepay.dao.WithholdBankDao;
import com.duanrong.business.yeepay.model.WithholdBank;


@Repository
public class WithholdBankDaoImpl extends BaseDaoImpl<WithholdBank> implements WithholdBankDao{

	public WithholdBankDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.yeepay.mapper.WithholdBankMapper");
	}

	@Override
	public List<WithholdBank> findList(WithholdBank withholdBank) {
		List<WithholdBank> list = getSqlSession().selectList(
				getMapperNameSpace() + ".findList",withholdBank);
		return list;
	}

	@Override
	public void deleteByUserId(String userId) {
		 this.getSqlSession().update(getMapperNameSpace()+".deleteByUserId", userId);
	}
}
