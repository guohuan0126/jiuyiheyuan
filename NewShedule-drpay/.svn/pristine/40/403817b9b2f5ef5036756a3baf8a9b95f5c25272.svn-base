package com.duanrong.yeepay.service;


/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2015-3-3 上午10:01:50
 * @Description : NewAdmin com.duanrong.business.platformtransfer.service
 *              PlatformTransferService.java
 * 
 */
public interface TrusteeshipPlatformTransferService {
	/**
	 * 
	 * @description 发起第三方平台划款
	 * @author 孙铮
	 * @time 2015-3-3 上午10:02:18
	 * @param userId
	 *            用户ID
	 * @param actualMoney
	 *            划款金额
	 * @param remarks
	 *            描述
	 * @return
	 */
	public String platformTransferTrusteeship(String Id, String userId,
			Double actualMoney, String remarks, String type, String loanId, String repayId);
	

	/**
	 * 转张授权操作
	 * @param reqNo
	 * @param Type
	 * @return
	 * @throws Exception 
	 */
	public String confirmTransferTrusteeship(String reqNo, String type) throws Exception;
}
