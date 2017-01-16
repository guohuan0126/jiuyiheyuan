package com.duanrong.drpay.business.payment.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.duanrong.drpay.business.payment.dao.PaymentUserRelationDao;
import com.duanrong.drpay.business.payment.model.PaymentUserRelation;
import com.duanrong.drpay.business.payment.service.PaymentUserRelationService;

@Service
public class PaymentUserRelationServiceImpl implements PaymentUserRelationService {

	@Resource
	PaymentUserRelationDao paymentUserRelationDao;
	
	@Override
	public PaymentUserRelation queryUserToken(PaymentUserRelation paymentUserRelation) {
		return paymentUserRelationDao.getUserToken(paymentUserRelation);
	}

	@Override
	public void insert(PaymentUserRelation paymentUserRelation) {
		paymentUserRelationDao.insert(paymentUserRelation);;
	}

	@Override
	public void update(PaymentUserRelation paymentUserRelation) {
		paymentUserRelationDao.update(paymentUserRelation);
	}

	@Override
	public void saveOrUpdate(String userId, String paymentId, String token) {
		PaymentUserRelation paymentUserRelation = new PaymentUserRelation();
		paymentUserRelation.setUserId(userId);
		paymentUserRelation.setPaymentId(paymentId);
		PaymentUserRelation userRelation = queryUserToken(paymentUserRelation);
		if(userRelation==null){
			paymentUserRelation.setToken(token);
			insert(paymentUserRelation);
		}else{
			if(!token.equals(userRelation.getToken())){
//				//一般情况下Token不会修改
//				update(paymentUserRelation);
			}
		}
	}

	
}
