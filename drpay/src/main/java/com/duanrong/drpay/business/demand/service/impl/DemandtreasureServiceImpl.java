package com.duanrong.drpay.business.demand.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.duanrong.drpay.business.demand.dao.DemandTreasureBillDao;
import com.duanrong.drpay.business.demand.dao.DemandtreasureDao;
import com.duanrong.drpay.business.demand.model.Demandtreasure;
import com.duanrong.drpay.business.demand.model.DemandtreasureTransferOut;
import com.duanrong.drpay.business.demand.service.AvailableMoneyRecordService;
import com.duanrong.drpay.business.demand.service.DemandTreasureBillService;
import com.duanrong.drpay.business.demand.service.DemandtreasureService;
import com.duanrong.drpay.business.demand.service.DemandtreasureTransferOutService;

import util.ArithUtil;


@Service
public class DemandtreasureServiceImpl implements DemandtreasureService {

	@Resource
	DemandtreasureDao demandtreasureDao;

	@Resource
	DemandTreasureBillService demandTreasureBillService;

	@Resource
	DemandTreasureBillDao demandTreasureBillDao;

	@Resource
	AvailableMoneyRecordService availableMoneyRecordService;

	@Resource
	DemandtreasureTransferOutService demandtreasureTransferOutService;

	@Override
	public List<Demandtreasure> queryAll() {
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
	public double[] getTreasuerAccount(String userId) {
		double[] treasuerAccount = new double[7];
		// 可转出总金额
		treasuerAccount[0] = demandTreasureBillService
				.getDemandTreasureMoney(userId);
		// 昨日收益
		treasuerAccount[1] = demandTreasureBillService
				.getDemandLaterInterest(userId);
		// 累计收益
		treasuerAccount[2] = demandTreasureBillService
				.getDemandTreasureInterestMoney(userId);

		DemandtreasureTransferOut out = demandTreasureBillService.getDemandOutTreasureMoney(userId);
				
		double avlidIterest = demandTreasureBillService
				.getDemandAvlidInterestMoney(userId);

		double avalidMoney = demandTreasureBillService
				.getDemandAvlidTreasureMoney(userId);
		double money = 0.0;
		double principal = 0.0;
		double interest  = 0.0;
		if(null!=out){
			principal = out.getPrincipal();
			interest = out.getInterest();
			money = out.getMoney();
		}
		// 本金
		treasuerAccount[3] = ArithUtil.round(avalidMoney - principal,2);

		// 可提利息
		treasuerAccount[4] = ArithUtil.round(avlidIterest - interest,2);

		// 申赎中的金额
		treasuerAccount[5] = ArithUtil.round(money,2);

		// 转入中的金额
		treasuerAccount[6] = demandTreasureBillService
				.getDemandInTreasureMoney(userId);
		return treasuerAccount;
	}

	@Override
	public Demandtreasure queryDemandtreasure() {
		Demandtreasure demandtreasure = queryAll().get(0);
		demandtreasure.setBillMoney(demandTreasureBillService
				.getDemandTreasureMoney("in"));
		demandtreasure.setBillInterest(demandTreasureBillDao
				.getDemandTreasureMoney("interest"));
		demandtreasure.setUserNum(demandTreasureBillService
				.getDemandTreasureUser().size());
		demandtreasure.setPresentMoney(availableMoneyRecordService.getEndLine()
				.getMoney());
		return demandtreasure;
	}

}
