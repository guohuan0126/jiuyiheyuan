package com.duanrong.business.riskfund.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.duanrong.business.riskfund.dao.RiskfunddetailDao;
import com.duanrong.business.riskfund.model.Riskfunddetail;
import com.duanrong.business.riskfund.service.RiskfunddetailService;

@Service
public class RiskfunddetailServiceImpl implements RiskfunddetailService {

	@Resource
	RiskfunddetailDao riskfunddetailDao;

	@Override
	public List<Riskfunddetail> read(int id) {
		Riskfunddetail r=new Riskfunddetail();
		r.setFundid(id);
		return riskfunddetailDao.find(r);
	}

	
}
