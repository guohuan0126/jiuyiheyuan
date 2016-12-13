package com.duanrong.business.riskfund.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.duanrong.business.riskfund.dao.RiskfundInfoDisclosureDao;
import com.duanrong.business.riskfund.model.RiskfundInfoDisclosure;
import com.duanrong.business.riskfund.service.RiskfundInfoDisclosureService;
@Service
public class RiskfundInfoDisclosureServiceImpl implements RiskfundInfoDisclosureService {
	@Resource
	RiskfundInfoDisclosureDao disclosureDao;
	@Override
	public void insert(RiskfundInfoDisclosure infoDisclosure) {
		disclosureDao.insert(infoDisclosure);
		
	}

	@Override
	public List<RiskfundInfoDisclosure> readList(
			Map<String, Object> params) {
		return disclosureDao.findList(params);
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		return disclosureDao.deleteByPrimaryKey(id);
	}


}
