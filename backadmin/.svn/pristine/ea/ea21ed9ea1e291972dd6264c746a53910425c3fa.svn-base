package com.duanrong.business.bankcard.dao;

import java.util.List;
import java.util.Map;

import base.dao.BaseDao;
import base.pagehelper.PageInfo;

import com.duanrong.business.bankcard.model.BankCard;
import com.duanrong.business.sms.model.Sms;

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
	public void updateAllValid4Cancel(BankCard bankCard);
	
	PageInfo<BankCard> pageInfo(int pageNo, int pageSize, Map map);
}
