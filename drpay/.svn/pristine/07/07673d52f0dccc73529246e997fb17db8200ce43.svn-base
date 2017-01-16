package com.duanrong.drpay.business.account.dao;

import java.util.List;

import base.dao.BaseDao;

import com.duanrong.drpay.business.account.model.BankCard;
import com.duanrong.drpay.business.account.model.UnbindCardInfo;
import com.duanrong.drpay.business.payment.model.PaymentBankInfo;


/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2014-9-11 下午2:56:31
 * @Description : drsoa Maven Webapp com.duanrong.business.bankcard.dao
 *              BankCardDao.java
 * 
 */
public interface BankCardDao extends BaseDao<BankCard> {
	/**
	 * 
	 * @description 组合查询银行卡
	 * @author 孙铮
	 * @time 2014-9-11 下午2:53:01
	 * @param loan
	 * @return
	 */
	public List<BankCard> getBankCardsByGroupCondition(BankCard bankCard);

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
	 * 更新指定用户下所有有效的银行卡
	 * 
	 * @param userId
	 *            用户ID
	 */
	public void updateAllValidCard(BankCard bankCard);

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
	/**
	 * 取消绑卡中的数据
	 * 
	 * @return
	 */
	public BankCard getUnbindingCard(String userId);

	public BankCard getByPaymentNo(String paymentNo);
	/**
	 * 更新用户银行预留手机号
	 * @param mobile
	 * @param userId
	 */
	public void updateBankMobile(String mobile, String userId);

	public String getmobile(String userId, String bankNo);

	public List<PaymentBankInfo> findBankInfoByName(String bankName);

	public BankCard getBankCardVerifyingByUserId(String userId);

	public BankCard getBankCardVerifedByUserId(String userId);

	public List<String> getBankCardUsableByBaoFoo();

	public List<String> getBankCardUsableByPaymentId(String paymentId);

	public List<String> getBankCardUsableByCgt();

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
	 * 获取解绑卡申请信息
	 * @param userId
	 * @return
	 */
	public List<UnbindCardInfo> getUnbindCardInfo(String userId, int status);
}
