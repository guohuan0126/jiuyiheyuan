package com.duanrong.drpay.business.transaction.dao.impl;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.drpay.business.transaction.dao.TransactionAuthorizationDetailDao;
import com.duanrong.drpay.business.transaction.model.TransactionAuthorizationDetail;


@Repository
public class TransactionAuthorizationDetailDaoImpl extends
		BaseDaoImpl<TransactionAuthorizationDetail> implements
		TransactionAuthorizationDetailDao {

	public TransactionAuthorizationDetailDaoImpl() {
		this.setMapperNameSpace("com.duanrong.drpay.business.transaction.mapper.TransactionAuthorizationDetailMapper");
	}



}
