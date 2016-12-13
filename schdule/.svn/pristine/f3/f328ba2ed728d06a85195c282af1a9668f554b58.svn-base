package com.duanrong.business.payment.dao.impl;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.business.payment.dao.PaymentUserRelationDao;
import com.duanrong.business.payment.model.PaymentUserRelation;

@Repository
public class PaymentUserRelationDaoImpl extends BaseDaoImpl<PaymentUserRelation>  implements PaymentUserRelationDao {

	
	public PaymentUserRelationDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.payment.mapper.PaymentUserRelationMapper");
	}
	
	@Override
	public PaymentUserRelation getUserToken(PaymentUserRelation paymentUserRelation) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getUserToken", paymentUserRelation);
	}

	
}
