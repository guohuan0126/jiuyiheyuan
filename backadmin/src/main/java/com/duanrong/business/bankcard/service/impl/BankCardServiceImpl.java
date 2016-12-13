package com.duanrong.business.bankcard.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import util.MyCollectionUtils;
import base.pagehelper.PageInfo;

import com.duanrong.business.bankcard.dao.BankCardDao;
import com.duanrong.business.bankcard.model.BankCard;
import com.duanrong.business.bankcard.service.BankCardService;
import com.duanrong.newadmin.utility.AESUtil;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2014-9-11 下午2:54:51
 * @Description : drsoa Maven Webapp com.duanrong.business.bankcard.service.impl
 *              BankCardServiceImpl.java
 * 
 */
@Service
public class BankCardServiceImpl implements BankCardService {
	@Resource
	BankCardDao bankCardDao;

	@Override
	public List<BankCard> readBankCardsByGroupCondition(BankCard bankCard) {
		List<BankCard> bcs = bankCardDao.getBankCardsByGroupCondition(bankCard);
		return bcs;
	}

	@Override
	public void insert(BankCard bankCard) {
		bankCardDao.insert(bankCard);
	}

	@Override
	public void update(BankCard bankCard) {
		bankCardDao.update(bankCard);
	}

	@Override
	public List<BankCard> readValidBankCardByUserId(String userId,
			boolean containsVerifying) {
		return bankCardDao.getValidBankCardByUserId(userId, containsVerifying);
	}

	@Override
	public PageInfo<BankCard> readPageInfo(int pageNo, int pageSize, Map map) {
		PageInfo<BankCard> page =  bankCardDao.pageInfo(pageNo, pageSize, map);
		List<BankCard> list =page.getResults();
		if (MyCollectionUtils.isNotBlank(list)) {
			for(BankCard bankCard : list){
				if(StringUtils.isNotBlank(bankCard.getCardNo())){
				try { 
					String cardNo = AESUtil.decode(bankCard.getCardNo());
					bankCard.setCardNo(cardNo.substring(0,4)+"********"+cardNo.substring(cardNo.length()-4));
				} catch (Exception e) {
					// TODO: handle exception
				}			
				}
			}
			page.setResults(list);
		}
		return page;
	}

	@Override
	public BankCard read(String id) {
		BankCard bankCard = bankCardDao.get(id);
		if(StringUtils.isNotBlank(bankCard.getCardNo())){
			try {
				String cardNo = AESUtil.decode(bankCard.getCardNo());
				bankCard.setCardNo(cardNo);
			} catch (Exception e) {
				bankCard.setCardNo(bankCard.getCardNo());
			}
		}
		return bankCard;
	}
}
