package com.duanrong.business.trusteeship.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duanrong.business.trusteeship.dao.TrusteeshipAccountDao;
import com.duanrong.business.trusteeship.model.TrusteeshipAccount;
import com.duanrong.business.trusteeship.service.TrusteeshipAccountService;

@Service
public class TrusteeshipAccountServiceImpl implements TrusteeshipAccountService {

	@Autowired
	TrusteeshipAccountDao trusteeshipAccountDao;

	public void insert(TrusteeshipAccount ta) {
		trusteeshipAccountDao.insert(ta);
	}

	public TrusteeshipAccount get(String id) {
		TrusteeshipAccount trusteeshipAccount = trusteeshipAccountDao.get(id);
		return trusteeshipAccount;
	}

	public void update(TrusteeshipAccount ta) {
		trusteeshipAccountDao.update(ta);
	}


}
