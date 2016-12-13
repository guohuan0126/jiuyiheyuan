package com.duanrong.business.followInvestAward.service;

import java.util.Date;

import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.withdraw.model.WithdrawCash;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2014-8-25 上午9:41:19
 * @Description : jdp2p com.esoft.duanrong.activity FollowInvestAward.java
 * 
 */
public interface FollowInvestAwardService {
	/**
	 * 
	 * @description 获取用户跟投截至时间
	 * @author 孙铮
	 * @time 2014-8-14 下午1:26:21
	 * @param userId
	 * @return
	 */
	public Date getFollowRechargeTime(String userId, Date endTime);

	/**
	 * 
	 * @description 根据用户名注册时间开始获取一段时间内,用户总充值金额
	 * @author 孙铮
	 * @time 2014-8-14 下午12:59:05
	 * @param userId
	 * @param registerTime
	 * @param addRegisterTime
	 * @return
	 */
	public Double getRecharges(String userId, Date registerTime,
			Date addRegisterTime);

	/**
	 * 
	 * @description 根据用户名注册时间开始获取一段时间内,用户提现金额
	 * @author 孙铮
	 * @time 2014-8-14 下午1:02:17
	 * @param userId
	 * @param registerTime
	 * @param addRegisterTime
	 * @return
	 */
	public Double getWithdrawCashs(String userId, Date registerTime,
			Date addRegisterTime);

	/**
	 * 
	 * @description 得到某用户需要回池的金额
	 * @author 孙铮
	 * @time 2014-8-22 下午1:59:31
	 * @param userID
	 * @return
	 */
	public Double getReturnPondByY(String userID);

	/**
	 * 
	 * @description 得到有效期内用户获得的投资所得利息
	 * @author 孙铮
	 * @time 2014-8-15 上午11:27:45
	 * @param userId
	 * @param registerTime
	 * @param addRegisterTime
	 * @return
	 */
	public Double getPaidInterest(String userId, Date registerTime,
			Date addRegisterTime);

	/**
	 * 
	 * @description 获取有效时间内用户通过平台划款方式得到的收入
	 * @author 孙铮
	 * @time 2014-8-15 上午11:33:38
	 * @param userId
	 * @param registerTime
	 * @param addRegisterTime
	 * @return
	 */
	public Double getPlatformTransferByUsername(String userId,
			Date registerTime, Date addRegisterTime);

	/**
	 * 更新某个用户总投资额及跟投回炉金额
	 * 
	 * @param userId
	 *            用户ID
	 * @param currentMoney
	 *            本次投资金额
	 */
	public void updateInvestMoneyTotalAndInvestMoneyTotal1ByUserID(Invest invest);
	
	/**
	 * 
	 * @description 判断该笔投资是否需要回池
	 * @author 孙铮
	 * @time 2014-8-26 上午9:49:19
	 * @param invest
	 */
	public void investReturnPond(Invest invest,String userID);
	
	
	/**
	 * 
	 * @description 该用户如果在有效期内发生提现,并且池中有金额,那么对应池中金额要相应扣除
	 * @author 孙铮
	 * @time 2014-10-10 上午11:12:51
	 * @param withdrawCash
	 */
	public void updateInvestMoneyTotal1(WithdrawCash withdrawCash);
	
}
