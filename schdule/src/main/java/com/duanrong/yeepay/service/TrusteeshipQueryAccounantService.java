package com.duanrong.yeepay.service;

import org.springframework.transaction.annotation.Transactional;

import com.duanrong.business.user.model.AccountChecking;

/**
 * 账户查询
 * 
 * @author Lin Zhiming
 * @version Mar 2, 2015 10:55:03 AM
 */
public interface TrusteeshipQueryAccounantService {

	/**
	 * 使用AccountChecking封装账户查询信息
	 * 
	 * @param platformUserNo
	 *            用户ID
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public AccountChecking queryAccount(String platformUserNo) throws Exception;
}
