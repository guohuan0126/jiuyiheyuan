package com.duanrong.business.yeepay.service;


import org.springframework.transaction.annotation.Transactional;

import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.trusteeship.model.TrusteeshipOperation;

import base.exception.InsufficientBalance;



/**
 * @Description: 冻结
 * @Author: wangjing
 * @CreateDate: Nov 24, 2014
 */
public interface FailLoanService {

	public void failLoan2(Invest invest) throws InsufficientBalance;
	
	public String failLoan(Invest invest) throws InsufficientBalance;
	
	
	@Transactional
	public void failLoan(Invest invest, TrusteeshipOperation to) throws InsufficientBalance;

	String sendV2(String id);
}