package com.duanrong.business.demand.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.duanrong.business.demand.dao.DemandtreasureDao;
import com.duanrong.business.demand.model.DemandTreasureBill;
import com.duanrong.business.demand.model.Demandtreasure;
import com.duanrong.business.demand.service.DateRateService;
import com.duanrong.business.demand.service.DemandTreasureBillService;
import com.duanrong.business.demand.service.DemandtreasureService;
import com.duanrong.newadmin.utility.DateUtil;
import com.duanrong.util.ArithUtil;
import com.duanrong.util.IdGenerator;

@Service
public class DemandtreasureServiceImpl implements DemandtreasureService {

	@Resource
	DemandtreasureDao demandtreasureDao;

	@Resource
	DemandTreasureBillService demandTreasureBillService;
	
	@Resource
	DateRateService dateRateService;
	
	@Override
	public List<Demandtreasure> findAll() {
		return demandtreasureDao.findAll();
	}

	@Override
	public void insert(Demandtreasure demandtreasure) {
		demandtreasureDao.insert(demandtreasure);
	}

	@Override
	public void update(Demandtreasure demandtreasure) {
		demandtreasureDao.update(demandtreasure);
	}

	
	@Override
	public int insertDayDate(Date now) {
		List<String> userIds = demandTreasureBillService.getDemandTreasureUser();
		List<DemandTreasureBill> list = new ArrayList<>();		
		for (String userId : userIds) {
			DemandTreasureBill bill = new DemandTreasureBill();
			bill.setId(IdGenerator.randomUUID());
			bill.setMoney(getInterest(userId, now));
			bill.setCreateTime(new Date());
			bill.setType("interest");
			bill.setTranferWay("pc");
			bill.setUserId(userId);
			bill.setStatus("success");
			bill.setDetail(DateUtil.DifferenceDay1(new Date(), 1) + " ： 利息");
			list.add(bill);
		}
		return demandTreasureBillService.insertInterestBatch(list);		
	}
	
	/**
	 * 计算用户当日利息
	 * 
	 * @param userId
	 * @return
	 */
	private double getInterest(String userId, Date date) {
		return ArithUtil.round(
				demandTreasureBillService.getDemandTreasureValidMoney(userId, date)
						* dateRateService.getRate(new Date()) / 365, 2);
	}
	
	
	
}
