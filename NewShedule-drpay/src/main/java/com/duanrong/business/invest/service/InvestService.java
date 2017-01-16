package com.duanrong.business.invest.service;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import base.exception.ExceedMoneyNeedRaised;
import base.exception.InsufficientBalance;
import base.exception.InvestMoneyException;
import base.exception.RedPacketUseException;

import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.invest.model.InvestRedpacket;
import com.duanrong.business.invest.model.InvestSubLoan;
import com.duanrong.business.invest.model.PassThrough;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2014-9-1 上午11:23:16
 * @Description : drsoa Maven Webapp com.duanrong.business.invest.service
 *              InvestService.java
 * 
 */
public interface InvestService {

	/**
	 * 
	 * @description 根据id查询单条投资记录
	 * @author 孙铮
	 * @time 2014-9-1 下午12:07:41
	 * @param id
	 * @return
	 */
	public Invest get(String id);


	public Double getPersonalInvestCeiling(String userId, String loanId);
	
	/**
	 * 更新
	 * 
	 * @param invest
	 */
	public void update(Invest invest);

	/**
	 * 根据loan获得invest
	 * 
	 * @param loanId
	 * @return
	 */
	public List<Invest> getInvestByLoan(Invest invest);

	/**
	 * 根据条件获得用户投资金额
	 * 
	 * @param invest
	 * @return
	 */
	public Double getInvestMoney(Invest invest);


	public Long getInvestNums();
	
	
	@Transactional(rollbackFor = Exception.class)
	public void syncInvest(Invest invest, String type)
			throws ExceedMoneyNeedRaised, InsufficientBalance,
			RedPacketUseException, InvestMoneyException;

	/**
	 * 获取用户项目投资信息
	 * 
	 * @param userId
	 * @param loanId
	 * @return
	 */
	public List<Invest> getInvestByUserAndLoan(String userId, String loanId);
	
	public Invest getInvest(String id);
	


	public List<Invest> getInvestByDate(Date date);
	
	public int getCountInvestByRedpacketForVisitor(String userId);
	
	/**
	 * 获取用户第一次有效投资时间
	 * @param userId
	 * @return
	 */
	public Date getFirstAvilidInvestTimeByUserId(String userId);
	
	/**
	 * 
	 * @return
	 */
	List<InvestRedpacket> getInvestRedpacketList();
	
	/**
	 * 插入补息和奖励金额
	 * @param investRedpacket
	 */
	void updatetInvestRedpacket(InvestRedpacket investRedpacket);
	/**
	 * 获取用户当前投资金额
	 * @param userId
	 * @return
	 */
	public Double getCurrentInvestMoney(String userId);
	/**
	 * 查询昨日有投资的用户id
	 * @return
	 */
	public List<String> getNewInvestUser();
	/**
	 * 查询用户当前在投金额（不包括等额本息中已还的）
	 * @param userId
	 * @return
	 */
	public Double getUserInvestMoney(String userId);
    /**
     * 获取用户活动期间的投资额
     * @param userId
     * @return
     */
	public int getCurrentInvestMoney4DoubleElevec(String userId,String createTime);

	/**
	 * 更新活动获奖数据
	 * @param passThrough
	 */
	public void update4DoubleEleven(PassThrough passThrough);

	public int getCurrentInvestMoney4DoubleElevec1(String userId);
	
	//查询当天有还款的用户id
	public List<String> getHasRepayUserId();
	
	/**
	 * 等待支付轮询数据
	 * @return
	 */
	List<Invest> getInvestByStatus();
	
	/**
	 * 等待支付轮询数据
	 * @return
	 */
	List<InvestSubLoan> getInvestSubloanByStatus();

}
