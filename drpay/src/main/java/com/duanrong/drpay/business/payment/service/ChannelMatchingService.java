package com.duanrong.drpay.business.payment.service;

import java.util.Map;

/**
 * 匹配最佳充值渠道Service
 * @author Today
 *
 */
public interface ChannelMatchingService {

	/**
	 * 根据用户ID和金额匹配
	 * @param userId
	 * @param Money
	 * @return
	 */
	public  Map<String,Object> findChannelByMoney(String userId, Double money, String source);
	
	/**
	 * 根据银行卡匹配
	 * @param bankCard
	 * @return
	 */
	public  Map<String,Object> findChannelByBankCard(String bankCard, String source);
}
