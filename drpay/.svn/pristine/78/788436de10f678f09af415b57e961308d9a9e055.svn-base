package com.duanrong.drpay.business.demand.service.impl;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.duanrong.drpay.business.demand.dao.DateRateDao;
import com.duanrong.drpay.business.demand.model.DateRate;
import com.duanrong.drpay.business.demand.service.DateRateService;

@Service
public class DateRateServiceImpl implements DateRateService {

	@Resource
	DateRateDao dateRateDao;

	@Override
	public List<DateRate> getRateDays(int days) {
		
		return dateRateDao.getRateDays(days);
	}

}
