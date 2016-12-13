package com.duanrong.payment.service;

import org.springframework.transaction.annotation.Transactional;

import com.duanrong.payment.jd.model.QueryResultTradeEntity;
import com.duanrong.payment.model.PayOrderInfo;


/**
 * @Description JD充值
 * @author JD
 * @CreateDate 2016-4-5 17:03:52
 */
public interface JDRechargeService extends OperationService {
	/**
	 * 参数拼接
	 * @param recharge
	 * @param callbackURL
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public Object createRechargeOrder(PayOrderInfo payOrderInfo)throws Exception;

	QueryResultTradeEntity queryOrderInfo(String tradeNum, String operationType);
}
