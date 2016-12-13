package com.duanrong.business.demand.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import base.pagehelper.PageInfo;

import com.duanrong.business.demand.dao.DateRateDao;
import com.duanrong.business.demand.model.DateRate;
import com.duanrong.business.demand.service.DateRateService;
import com.duanrong.newadmin.utility.ArithUtil;
import com.duanrong.newadmin.utility.DateUtil;
import com.duanrong.newadmin.utility.IdGenerator;

@Service
public class DateRateServiceImpl implements DateRateService {

	@Resource
	DateRateDao dateRateDao;

	@Override
	public PageInfo<DateRate> readPageInfo(int pageNo, int pageSize, Map map) {
		return dateRateDao.readPageInfo(pageNo, pageSize, map);
	}

	@Override
	public void addRate(String year, String month,double rate) {
		int y=Integer.parseInt(year);
		int m=Integer.parseInt(month);
		int max=DateUtil.getMaxDay(y,m);
		for(int i=1;i<=max;i++){
			Calendar c = Calendar.getInstance();
			c.set(y, m-1, i,0,0,0);
			double interest = ArithUtil
					.round(10000 * rate / 365 * 1, 2);
			Date date=c.getTime();
			List<DateRate> list=dateRateDao.readRateList(date);
			for(DateRate r:list){
				DateRate ra=new DateRate();
				ra.setId(r.getId());
				ra.setStatus(0);
				dateRateDao.update(ra);
			}
			DateRate dr=new DateRate();
			
			dr.setDate(date);
			dr.setId(IdGenerator.randomUUID());
			dr.setRate(rate);
			dr.setInterest(interest);
			dr.setTime(new Date());
			dateRateDao.insert(dr);
		}
		
	}

	@Override
	public DateRate read(String id) {
		return dateRateDao.get(id);
	}

	@Override
	public void update(DateRate dateRate) {
		dateRateDao.update(dateRate);
	}
	
	@Override
	public double readRate(Date date) {
		return dateRateDao.readRate(date);
	}

	@Override
	public List<DateRate> readRateList(Date date) {
		return dateRateDao.readRateList(date);
	}

}
