package com.duanrong.drpay.business.autoinvest.service;

import org.springframework.transaction.annotation.Transactional;

import com.duanrong.drpay.business.autoinvest.model.AutoInvest;

public interface AutoInvestService {
	/**
	 * 保存用户自动投标设置信息
	 * 
	 * @param autoInvest
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void settingAutoInvest(AutoInvest autoInvest);

	public AutoInvest query(String id);

	public void update(AutoInvest autoInvest);

	public AutoInvest get(String id);

}