package com.duanrong.business.bankcard.service.impl;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import util.Log;
import util.MyStringUtils;
import base.pagehelper.PageInfo;

import com.duanrong.business.bankcard.dao.BankCardDao;
import com.duanrong.business.bankcard.model.BankCard;
import com.duanrong.business.bankcard.service.BankCardService;
import com.duanrong.business.sms.model.Sms;

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
	
	@Resource
	Log log;
	
	@Override
	public List<BankCard> getBankCardsByGroupCondition(BankCard bankCard) {
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
	public List<BankCard> getValidBankCardByUserId(String userId,
			boolean containsVerifying) {
		return bankCardDao.getValidBankCardByUserId(userId, containsVerifying);
	}

	@Override
	public PageInfo<BankCard> findPageInfo(int pageNo, int pageSize, Map map) {
		return bankCardDao.pageInfo(pageNo, pageSize, map);
	}

	@Override
	public BankCard get(String id) {
		return bankCardDao.get(id);
	}
	
	/**
	 * 重新绑定银行卡
	 * 会将用户原来绑定的银行卡覆盖掉
	 * 参数：bankCard
	 * 
	 */
	@Override
	public void quickBindingCard(BankCard bankCard) {
		//参数校验
		if (null == bankCard
				|| MyStringUtils.isBlank(bankCard.getUserId())
				|| MyStringUtils.isBlank(bankCard.getCardNo())
				|| StringUtils.isBlank(bankCard.getBank())
				|| !StringUtils.equals("VERIFIED", bankCard.getStatus())) {
			log.errLog("绑卡失败","参数校验失败 ：" + bankCard.toString() );
			return;
		}
		try{
			// 通过用户ID找到该用的银行卡,状态为:审核中、审核通过
			List<BankCard> list = bankCardDao.getValidBankCardByUserId(
					bankCard.getUserId(), true);
			if(!list.isEmpty()&&list.size()==1){
				BankCard card = list.get(0);
				if(bankCard.getCardNo().equals(card.getCardNo())&&bankCard.getBank().equals(card.getBank())){
					return ;
				}
				//将状态改为失败
				card.setDeleteBankCard("delete");
				DateFormat df = DateFormat.getDateTimeInstance();
				String cancelBandDingTime = df.format(new Date());
				card.setCancelBandDingTime(cancelBandDingTime);
				card.setCancelStatus("被新的绑卡覆盖");
				//正常情况下是一条数据,没有采用批量更新
				bankCardDao.update(card);
			}
			bankCardDao.insert(bankCard);
		}catch (Exception e) {
			log.errLog("绑卡失败",bankCard.toString() +"Exception:" + e);
		}
		
	}
	
	@Override
	public List<BankCard> getCancelTheTieCard() {
		return bankCardDao.getCancelTheTieCard();
	}

	@Override
	public void updateUserCardNo(String platformUserNo, String cardNo) {
		bankCardDao.updateUserCardNo(platformUserNo,cardNo);
	}

	
}
