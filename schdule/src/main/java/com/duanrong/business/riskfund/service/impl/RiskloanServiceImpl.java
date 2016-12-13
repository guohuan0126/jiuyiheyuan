package com.duanrong.business.riskfund.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.duanrong.business.activity.model.Activity;
import com.duanrong.business.riskfund.dao.RiskloanDao;
import com.duanrong.business.riskfund.model.Riskloan;
import com.duanrong.business.riskfund.service.RiskloanService;
import com.duanrong.business.user.model.UserVisitInfo;



@Service
public class RiskloanServiceImpl implements RiskloanService {

	@Resource
	RiskloanDao riskloanDao;

	@Override
	public List<Riskloan> findList(int id) {
		return riskloanDao.findList(id);
	}

	
	@Override
	public void delete(int id) {
		Riskloan t=new Riskloan();
		t.setId(id);
		riskloanDao.update(t);
	}
	public void insert(Riskloan riskloan) {
		this.riskloanDao.insert(riskloan);
	}
	
}
