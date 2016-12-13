package com.duanrong.yeepay.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpException;
import org.springframework.transaction.annotation.Transactional;

import base.exception.ExceedMoneyNeedRaised;
import base.exception.InsufficientBalance;

import com.duanrong.business.autoinvest.model.AutoInvest;
import com.duanrong.business.loan.model.Loan;

/**
 * 自动投标
 * 
 * @author Lin Zhiming
 * @version Mar 2, 2015 10:55:03 AM
 */
public interface TrusteeshipAutoInvestService {

	/**
	 * 自动投标1.0
	 * 
	 * @param autoInvest
	 * @param loan
	 * @param investMoney
	 * @throws IOException
	 * @throws HttpException
	 * @throws InsufficientBalance
	 * @throws ExceedMoneyNeedRaised
	 */
	/*@Transactional
	public void autoInvest(AutoInvest autoInvest, Loan loan, double investMoney)
			throws HttpException, IOException, InsufficientBalance,
			ExceedMoneyNeedRaised;*/
	
	/**
	 * 自动投标2.0
	 * 
	 * @param autoInvest
	 * @param loan
	 * @param investMoney
	 * @throws IOException
	 * @throws HttpException
	 * @throws InsufficientBalance
	 * @throws ExceedMoneyNeedRaised
	 */
	public void autoInvestV2(AutoInvest autoInvest, Loan loan, double investMoney)
			throws HttpException, IOException, InsufficientBalance,
			ExceedMoneyNeedRaised;
	
	
	/**
	 * 自动投标 notify
	 * @param request
	 * @param response
	 */
	@Transactional
	public void S2SCallback(HttpServletRequest request, HttpServletResponse response);
}
