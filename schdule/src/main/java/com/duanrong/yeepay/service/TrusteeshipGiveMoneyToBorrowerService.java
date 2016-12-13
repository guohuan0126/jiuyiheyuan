package com.duanrong.yeepay.service;

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
public interface TrusteeshipGiveMoneyToBorrowerService{

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
	 */
	public String giveMoneyToBorrower(String userId, String loanId)  throws Exception;

	
}
