package com.duanrong.business.bankcard.service;

import java.util.List;
import java.util.Map;

import base.pagehelper.PageInfo;

import com.duanrong.business.bankcard.model.BankCard;
import com.duanrong.business.sms.model.Sms;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2014-9-11 下午2:51:59
 * @Description : drsoa Maven Webapp com.duanrong.business.bankcard.service
 *              BankCardService.java
 * 
 */
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
	 * 分页
	 * @param map
	 * @return
	 */
	 public PageInfo<BankCard> findPageInfo(int pageNo, int pageSize,
			 Map map);

	public BankCard get(String id);
	
	/**
	 * 快捷绑卡
	 */
	public void quickBindingCard(BankCard bankCard);
	
	/**
	 * 取消绑卡中的数据
	 * 
	 * @return
	 */
	public List<BankCard> getCancelTheTieCard();

	/**
	 * 更新易宝绑卡，进行加密
	 * @param platformUserNo
	 * @param cardNo
	 */
	public void updateUserCardNo(String platformUserNo, String cardNo);


}
