package com.duanrong.drpay.business.account.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.duanrong.drpay.business.account.dao.FreezeDao;
import com.duanrong.drpay.business.account.model.Freeze;
import com.duanrong.drpay.business.account.service.FreezeService;

@Service
public class FreezeServiceImpl implements FreezeService {

	@Resource
	FreezeDao freezeDao;

	@Override
	public void save(Freeze freeze) {
		freezeDao.insert(freeze);
	}

	/**
	 * 获取冻结记录
	 * author guohuan
	 * 
	 * @return 2016年12月20日
	 */
	@Override
	public Freeze get(String id) {
		return freezeDao.get(id);
	}

	/**
	  * 更新冻结记录
	  * author guohuan
	  * @return
	  * 2016年12月20日
	  */
	@Override
	public void update(Freeze freeze) {
		freezeDao.update(freeze);
	}

}
