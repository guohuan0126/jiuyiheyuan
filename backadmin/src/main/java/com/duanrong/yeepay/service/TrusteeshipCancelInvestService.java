package com.duanrong.yeepay.service;

import org.springframework.transaction.annotation.Transactional;

import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.trusteeship.model.TrusteeshipOperation;

import base.exception.InsufficientBalance;

/**
 * 
 * Copyright : duanrong.com.cn All Rights Reserved
 * Company : 久亿财富（北京）投资有限公司
 * @Author : SunZ
 * @CreateTime : 2015-4-2 下午11:16:06 
 * @Description : NewAdmin com.duanrong.yeepay.service TrusteeshipCancelInvestService.java 
 *
 */
public interface TrusteeshipCancelInvestService {

	/**
	 * 
	 * @description 易宝流标
	 * @author 孙铮
	 * @time 2015-4-2 下午11:15:37
	 * @param userId 操作人
	 * @param loanId 借款项目id
	 * @return 流标结果
	 * @throws InsufficientBalance 
	 */
	public String cancelInvest(String userId, String loanId) throws InsufficientBalance;
	
	@Transactional
	public void cancelInvest(Invest invest, TrusteeshipOperation to, String loanName) throws InsufficientBalance;
}
