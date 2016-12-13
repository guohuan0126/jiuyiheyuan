package com.duanrong.business.riskfund.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.duanrong.business.riskfund.dao.RiskfundDao;
import com.duanrong.business.riskfund.dao.RiskfunddetailDao;
import com.duanrong.business.riskfund.model.Riskfund;
import com.duanrong.business.riskfund.model.Riskfunddetail;
import com.duanrong.business.riskfund.service.RiskfundService;

@Service
public class RiskfundServiceImpl implements RiskfundService {

	@Resource
	RiskfundDao riskfundDao;
	@Resource
	RiskfunddetailDao riskfunddetailDao;

	@Override
	public List<Riskfund> readAll() {
		return riskfundDao.findAll();
	}

	@Override
	public Riskfund read(int id) {
		return riskfundDao.get(id);
	}

	@Override
	public void updateRiskfund(Riskfund r, List<Riskfunddetail> rf) {
		for(Riskfunddetail d:rf){
			riskfunddetailDao.update(d);
		}
		riskfundDao.update(r);
		riskfundDao.updateRisk();
	}

	@Override
	public void update(Riskfund r) {
		riskfundDao.update(r);
	}
	
}