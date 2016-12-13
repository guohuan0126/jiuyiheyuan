package com.duanrong.yeepay.service;


/**
 * 单笔业务查询
 * @author xiao
 */
public interface TrusteeshipQueryBusinessService {

	/**
	 * 单笔业务查询
	 * @param platformUserNo
	 *        用户ID
	 * @return
	 * @throws Exception
	 */
	public String query(String requestNo) throws Exception;
}
