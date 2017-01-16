package com.duanrong.drpay.business.demand.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.duanrong.drpay.business.demand.DemandtreasureConstants;
import com.duanrong.drpay.business.demand.dao.DemandTreasureBillDao;
import com.duanrong.drpay.business.demand.dao.DemandtreasureTransferInDao;
import com.duanrong.drpay.business.demand.dao.DemandtreasureTransferOutDao;
import com.duanrong.drpay.business.demand.model.DemandtreasureTransferOut;
import com.duanrong.drpay.business.demand.service.DemandTreasureBillService;

import util.ArithUtil;



/**
 * @author xiao
 * @date 2015年7月20日下午2:41:22
 */
@Service
public class DemandTreasureBillServiceImpl implements DemandTreasureBillService {

	@Resource
	DemandTreasureBillDao demandTreasureBillDao;

	@Resource
	DemandtreasureTransferInDao demandtreasureTransferInDao;

	@Resource
	DemandtreasureTransferOutDao demandtreasureTransferOutDao;

	@Override
	public double getDemandTreasureInMoney(String userId) {

		return demandTreasureBillDao.getDemandTreasureMoneyByType(userId,
				DemandtreasureConstants.DemandBillStatus.TRANIN);
	}

	@Override
	public double getDemandTreasureOutMoney(String userId) {
		return demandTreasureBillDao.getDemandTreasureMoneyByType(userId,
				DemandtreasureConstants.DemandBillStatus.TRANOUT);
	}

	@Override
	public double getDemandTreasureInterestMoney(String userId) {
		return demandTreasureBillDao.getDemandTreasureMoneyByType(userId,
				DemandtreasureConstants.DemandBillStatus.INTEREST);
	}

	@Override
	public double getDemandTreasureOutInterestMoney(String userId) {
		return demandTreasureBillDao.getDemandTreasureMoneyByType(userId,
				DemandtreasureConstants.DemandBillStatus.OUTINTEREST);
	}

	@Override
	public List<String> getDemandTreasureUser() {

		return demandTreasureBillDao.getDemandTreasureUser();
	}


	@Override
	public double getDemandTreasureMoney(String userId) {
		return ArithUtil.round(getDemandTreasureInMoney(userId)
				- getDemandTreasureOutMoney(userId)
				+ getDemandTreasureInterestMoney(userId)
				- getDemandTreasureOutInterestMoney(userId), 2);
	}

	@Override
	public double getDemandLaterInterest(String userId) {
		return ArithUtil.round(
				demandTreasureBillDao.getDemandLaterInterest(userId), 2);
	}

	@Override
	public double getUserDemandInSumMoney(String userId) {

		return ArithUtil.round(getDemandTreasureInMoney(userId)
				- getDemandTreasureOutMoney(userId)
				+ demandtreasureTransferInDao.getuserDemandInSumMoney(userId),
				2);
	}

	@Override
	public double getDemandAvlidTreasureMoney(String userId) {
		return ArithUtil.round(getDemandTreasureInMoney(userId)
				- getDemandTreasureOutMoney(userId), 2);
	}

	@Override
	public double getDemandAvlidInterestMoney(String userId) {
		return ArithUtil.round(getDemandTreasureInterestMoney(userId)
				- getDemandTreasureOutInterestMoney(userId), 2);
	}

	@Override
	public DemandtreasureTransferOut getDemandOutTreasureMoney(String userId) {

		return demandtreasureTransferOutDao.getOutSumMoney(
				userId, DemandtreasureConstants.TransferOutStatus.SENDED);
	}

	@Override
	public double getDemandInTreasureMoney(String userId) {

		return ArithUtil.round(
				demandtreasureTransferInDao.getuserDemandInSumMoney(userId), 2);
	}

}
