package com.duanrong.business.riskfund.service;

import java.util.List;

import com.duanrong.business.riskfund.model.Riskfund;
import com.duanrong.business.riskfund.model.Riskfunddetail;



/**
 * @Description: 保障金
 * @Author: wangjing
 * @CreateDate: Nov 22, 2014
 */
public interface RiskfundService {

	public List<Riskfund> getAll();
	public Riskfund get(int id);
	public void updateRiskfund(Riskfund r,List<Riskfunddetail> rf);
	public void update(Riskfund r);

}