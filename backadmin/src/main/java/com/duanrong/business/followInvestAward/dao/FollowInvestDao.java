package com.duanrong.business.followInvestAward.dao;

import java.util.Date;

import org.springframework.stereotype.Repository;

import base.dao.BaseDao;

import com.duanrong.business.followInvestAward.model.FollowInvestActivity;

/**
 * 
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2014-12-1 下午7:31:51
 * @Description : drsoa Maven Webapp com.duanrong.business.followInvestAward.dao
 *              FollowInvestDao.java
 * 
 */
@Repository
public interface FollowInvestDao extends BaseDao<FollowInvestActivity> {

	/**
	 * 
	 * @description 跟投奖励查询:查询有效期内最后一笔投资成功时间
	 * @author 孙铮
	 * @time 2014-12-1 下午8:11:09
	 * @param userId
	 * @param endTime
	 * @return
	 */
	public Date getFollowRechargeTime(String userId, Date endTime);

	/**
	 * 
	 * @description 跟投奖励查询:查询用户从注册之日起-有效期内的充值总金额
	 * @author 孙铮
	 * @time 2014-12-1 下午8:11:52
	 * @param userId
	 * @param registerTime
	 * @param addRegisterTime
	 * @return
	 */
	public Double getRecharges(String userId, Date registerTime,
			Date addRegisterTime);

	/**
	 * 
	 * @description 根据用户名注册时间开始获取一段时间内,用户总提现金额
	 * @author 孙铮
	 * @time 2014-12-2 上午10:10:05
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
	 * @time 2014-12-2 上午10:14:55
	 * @param userID
	 * @return
	 */
	public Double getReturnPondByY(String userID);

	/**
	 * 
	 * @description 得到有效期内用户获得的投资所得利息
	 * @author 孙铮
	 * @time 2014-12-2 上午10:19:03
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
	 * @time 2014-12-2 上午10:22:23
	 * @param userId
	 * @param registerTime
	 * @param addRegisterTime
	 * @return
	 */
	public Double getPlatformTransferByUsername(String userId,
			Date registerTime, Date addRegisterTime);
	
	
}
