package com.duanrong.business.riskfund.dao;

import base.dao.BaseDao;

import com.duanrong.business.riskfund.model.Riskfund;

/**
 * @Description: 保障金
 * @Author: wangjing
 * @CreateDate: Nov 22, 2014
 */
public interface RiskfundDao extends BaseDao<Riskfund> {
	public void updateRisk();
}