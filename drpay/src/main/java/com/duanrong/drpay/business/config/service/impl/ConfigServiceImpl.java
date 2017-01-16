package com.duanrong.drpay.business.config.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.duanrong.drpay.business.config.dao.ConfigDao;
import com.duanrong.drpay.business.config.model.Config;
import com.duanrong.drpay.business.config.service.ConfigService;

@Service
public class ConfigServiceImpl implements ConfigService {

	@Resource
	ConfigDao configDao;
	
	@Override
	public Config get(String id) {
		return configDao.get(id);
	}
	
}
