package com.duanrong.business.account.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.business.account.dao.PaymentAccountDao;
import com.duanrong.business.account.model.PaymentAccount;
import com.duanrong.business.account.model.PaymentBill;
import com.duanrong.business.account.model.PaymentChannel;

@Repository
public class PaymentAccountDaoImpl extends BaseDaoImpl<PaymentAccount>
		implements PaymentAccountDao {

	public PaymentAccountDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.account.mapper.PaymentAccountMapper");
	}

	@Override
	public void insert(PaymentBill paymentBill) {
		this.getSqlSession().insert(
				this.getMapperNameSpace() + ".insertPaymentBill", paymentBill);
	}
	
	@Override
	public PaymentAccount getWithLock(String pamenyId) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getWithLock", pamenyId);
	}


	@Override
	public PaymentChannel getChannelByCode(String code) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getChannelByCode", code);
	}

	@Override
	public PageInfo<PaymentBill> pageLite(int pageNo, int pageSize,
			PaymentBill paymentBill) {
		PageHelper.startPage(pageNo, pageSize);
		List<PaymentBill> list = getSqlSession().selectList(
				this.getMapperNameSpace() + ".pageLite", paymentBill);
		PageInfo<PaymentBill> pageInfo = new PageInfo<>(list);
		if (pageNo > pageInfo.getLastPage()) {
			pageInfo.setResults(null);
		}
		return pageInfo;
	}
}
