package com.duanrong.drpay.business.account.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.drpay.business.account.dao.PaymentAccountDao;
import com.duanrong.drpay.business.account.model.PaymentAccount;
import com.duanrong.drpay.business.account.model.PaymentBill;

@Repository
public class PaymentAccountDaoImpl extends BaseDaoImpl<PaymentAccount>
		implements PaymentAccountDao {

	public PaymentAccountDaoImpl(){
		this.setMapperNameSpace("com.duanrong.drpay.business.account.mapper.PaymentAccountMapper");
	}

	@Override
	public PaymentAccount getWithLock(String pamenyId) {
		return this.getSqlSession().selectOne(this.getMapperNameSpace() + ".getWithLock",
				pamenyId);
	}
	
	@Override
	public void insertBill(PaymentBill paymentBill) {
		this.getSqlSession().insert(this.getMapperNameSpace() + ".insertPaymentBill",
				paymentBill);
	}

	@Override
	public PageInfo<PaymentBill> pageLite(int pageNo, int pageSize,
			PaymentBill paymentBill) {
		PageHelper.startPage(pageNo, pageSize);
		List<PaymentBill> list = getSqlSession().selectList(
				getMapperNameSpace() + ".pageLite", paymentBill);
		PageInfo<PaymentBill> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
}