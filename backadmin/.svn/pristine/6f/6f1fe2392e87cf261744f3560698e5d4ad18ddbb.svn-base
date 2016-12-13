package com.duanrong.yeepay.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Transactional;

import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.loan.model.Loan;
import com.duanrong.business.trusteeship.model.TrusteeshipOperation;

/**
 * 
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : SunZ
 * @CreateTime : 2015-4-2 下午10:53:40
 * @Description : NewAdmin com.duanrong.yeepay.service
 *              TrusteeshipGiveMoneyToBorrowerService.java
 * 
 */
public interface TrusteeshipGiveMoneyToBorrowerService {

	/**
	 * 
	 * @description 执行正常放款操作
	 * @author 孙铮
	 * @time 2015-4-2 下午10:54:55
	 * @param userId
	 *            执行放款操作的用户
	 * @param loanId
	 *            借款项目id
	 * @return 放款结果
	 * @throws Exception 
	 */
	//public String giveMoneyToBorrower(String userId, String loanId) throws Exception;

	
	/**
	 * 单笔放款
	 * @param loan
	 * @param invest
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	//@Transactional
	//public String giveMoneyToBorrowerFromInvest(Loan loan, Invest invest,
	//		String userId) throws Exception;
	
	
	//@Transactional
	//public void giveMoneyToBorrowerLocalDispose(Loan loan, Invest invest)
	//		throws Exception;
	
	public void S2SCallback(HttpServletRequest request,HttpServletResponse response);
}
