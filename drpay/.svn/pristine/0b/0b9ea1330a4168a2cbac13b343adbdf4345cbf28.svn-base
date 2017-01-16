package com.duanrong.drpay.business.payment.service;

import java.util.Date;

import org.springframework.transaction.annotation.Transactional;

import base.exception.UserAccountException;

import com.duanrong.drpay.business.payment.model.WithdrawCash;

public interface WithdrawCashService {

	WithdrawCash get(String id);

	Date getArrivalDate(Date date);

	void insert(WithdrawCash withdrawCash);

	@Transactional(rollbackFor = Exception.class)
	void successWithdraw(WithdrawCash withdrawCash)throws UserAccountException;

	void update(WithdrawCash withdrawCash);

	WithdrawCash getWithLock(String id);

}
