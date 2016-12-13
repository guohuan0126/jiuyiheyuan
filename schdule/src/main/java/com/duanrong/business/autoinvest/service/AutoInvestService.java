package com.duanrong.business.autoinvest.service;

import org.springframework.transaction.annotation.Transactional;

import base.pagehelper.PageInfo;

import com.duanrong.business.autoinvest.model.AutoInvest;

/**
 * @Description: 自动投标
 * @Author: 林志明
 * @CreateDate: Nov 27, 2014
 */
public interface AutoInvestService {
	public void insert(AutoInvest autoInvest);

	/**
	 * 查询开启自动投标的总数量
	 */
	public Long getAutoInvestConut();

	/**
	 * 保存用户自动投标设置信息
	 * 
	 * @param autoInvest
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void settingAutoInvest(AutoInvest autoInvest);

	public AutoInvest get(String id);

	public void update(AutoInvest autoInvest);

	/**
	 * 获取自动投标用户排队号
	 * 
	 * @param userId
	 * @return
	 */
	public Long getQueueNumber(String userId);
	/**
	 * 分页
	 * @param withcash
	 * @return
	 */
	
	PageInfo<AutoInvest> findPageInfo(int pageNo, int pageSize,
			AutoInvest autoInvest);
	
	/**
	 * 自动投标
	 * @param loanId
	 *//*
	public void autoInvest(String loanId);*/
	
	/**
	 * 
	 * @param loanId
	 * @param userId
	 */
	//public void autoInvest(String loanId, String userId, double investMoney, String isAutoInvest);
}
