package com.duanrong.drpay.business.account.service;

import java.util.List;

import com.duanrong.drpay.business.account.model.BankCard;
import com.duanrong.drpay.business.account.model.UnbindCardInfo;
import com.duanrong.drpay.business.payment.model.PaymentBankInfo;


public interface BankCardService {
	/**
	 * 
	 * @description 组合查询银行卡
	 * @author 孙铮
	 * @time 2014-9-11 下午2:53:01
	 * @param loan
	 * @return
	 */
	public List<BankCard> getBankCardsByGroupCondition(BankCard bankCard);

	public void insert(BankCard bankCard);

	public void update(BankCard bankCard);

	/**
	 * 根据用户ID获取有效的银行卡
	 * 
	 * @param userId
	 *            用户ID
	 * @param containsVerifying
	 *            是否包含认证中
	 * @return
	 */
	public List<BankCard> getValidBankCardByUserId(String userId,
												   boolean containsVerifying);

	/**
	 * 根据用户ID获取绑卡中的银行卡
	 * 
	 * @param userId
	 *            用户ID
	 */
	public List<BankCard> getVerifyingBankCardByUserId(String userId);

	/**
	 * 取消绑卡中的数据
	 * 
	 * @return
	 */
	public List<BankCard> getCancelTheTieCard();
	public BankCard getUnbindingCard(String userId);

	/**
	 * 快捷绑卡
	 */
	public void quickBindingCard(BankCard bankCard);

	public BankCard get(String id);

	public BankCard getByPaymentNo(String paymentNo);
	/**
	 * 更新用户银行预留手机号
	 * @param mobile
	 * @param userId
	 */
	public void updateBankMobile(String mobile, String userId);

	public String getMobile(String userId, String bankNo);

	public List<PaymentBankInfo> findBankInfoByName(String bankName);
	/**
	 * 根据用户编号查询正在绑卡中的 银行卡
	 * @param userId
	 * @return
	 */
	public BankCard getBankCardVerifyingByUserId(String userId);
	/**
	 * 查询 已绑卡 的银行卡
	 * @param userId
	 * @return
	 */
	public BankCard getBankCardVerifedByUserId(String userId);
	/**
	 * 查询宝付支持的银行卡
	 * @param bankCode
	 * @return
	 */
	public List<String> getBankCardUsableByBaoFoo();
	/**
	 * 查询存管通支持的银行卡
	 * @param bankCode
	 * @return
	 */
	public List<String> getBankCardUsableByCgt();
	/**
	 * 根据渠道返回 该银行是否支持
	 * @param string
	 * @return
	 */
	public boolean booleanbankSupport(String paymentId,String bankCode);

	/**
	 * 更新userId下的所有有效银行卡
	 * @param bankCard
	 */
	public void updateAllValidCard(BankCard bankCard);

	/**
	 * 根据bankcode获取payment_bankinfo
	 * @param bank
	 * @return
	 */
	public PaymentBankInfo getBankInfoByCode(String bank);
	
	/**
	 * 插入解绑卡申请信息
	 * @param unbindCardInfo
	 */
	public void insertUnbindCardInfo(UnbindCardInfo unbindCardInfo);
	
	/**
	 * 插入解绑卡申请信息
	 * @param unbindCardInfo
	 */
	public List<UnbindCardInfo> getUnbindCardInfo(String userId, int status);
}