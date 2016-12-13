package com.duanrong.business.account.dao.impl;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.business.account.dao.PaymentAccountDao;
import com.duanrong.business.account.model.PaymentAccount;
import com.duanrong.business.account.model.PaymentBill;

@Repository
public class PaymentAccountDaoImpl extends BaseDaoImpl<PaymentAccount>
		implements PaymentAccountDao {

	public PaymentAccountDaoImpl(){
		this.setMapperNameSpace("com.duanrong.business.account.mapper.PaymentAccountMapper");
	}
	
	@Override
	public PaymentAccount getWithLock(String pamenyId) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getWithLock", pamenyId);
	}

	@Override
	public void insert(PaymentBill paymentBill) {
		this.getSqlSession().insert(this.getMapperNameSpace() + ".insertPaymentBill",
				paymentBill);
	}

}
