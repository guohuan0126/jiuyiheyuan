package com.duanrong.business.demand.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import base.pagehelper.PageInfo;

import com.duanrong.business.demand.DemandtreasureConstants;
import com.duanrong.business.demand.dao.DemandTreasureBillDao;
import com.duanrong.business.demand.dao.DemandtreasureTransferInDao;
import com.duanrong.business.demand.dao.DemandtreasureTransferOutDao;
import com.duanrong.business.demand.model.DemandTreasureBill;
import com.duanrong.business.demand.model.DemandtreasureTransferOut;
import com.duanrong.business.demand.service.DemandTreasureBillService;
import com.duanrong.util.ArithUtil;

/**
 * @author xiao
 * @date 2015年7月20日下午2:41:22
 */
@Service
public class DemandTreasureBillServiceImpl implements DemandTreasureBillService {

	@Resource
	DemandTreasureBillDao demandTreasureBillDao;
	@Resource
	DemandtreasureTransferOutDao demandtreasureTransferOutDao;
	@Resource
	DemandtreasureTransferInDao demandtreasureTransferInDao;

	@Override
	public double getDemandTreasureInMoney(String userId) {

		return demandTreasureBillDao.getDemandTreasureMoneyByType(userId, "in");
	}

	@Override
	public double getDemandTreasureOutMoney(String userId) {
		return demandTreasureBillDao
				.getDemandTreasureMoneyByType(userId, "out");
	}

	@Override
	public double getDemandTreasureInterestMoney(String userId) {
		return demandTreasureBillDao.getDemandTreasureMoneyByType(userId,
				"interest");
	}
	@Override
	public double getDemandTreasureOutInterestMoney(String userId) {
		return demandTreasureBillDao.getDemandTreasureMoneyByType(userId,
				"outinterest");
	}
	
	@Override
	public double getDemandTreasureInvalidMoney(String userId, String type,
			Date date) {
		return demandTreasureBillDao.getDemandTreasureInvalidMoney(userId,
				type, date);
	}

	@Override
	public List<String> getDemandTreasureUser() {

		return demandTreasureBillDao.getDemandTreasureUser();
	}

	@Override
	public double getDemandTreasureValidMoney(String userId) {
		double inMoney =  getDemandTreasureInMoney(userId);
		double outMoney = getDemandTreasureOutMoney(userId);
		double invalidMoney = getDemandTreasureInvalidMoney(userId, "in", new Date());
		double money = inMoney - outMoney - invalidMoney;
		return money > 0 ? money : 0;
	}

	@Override
	public double getDemandTreasureValidMoney(String userId, Date date) {
		double money = getDemandTreasureInMoney(userId)
				+ getDemandTreasureInvalidMoney(userId, "out", date)
				- getDemandTreasureOutMoney(userId)
				- getDemandTreasureInvalidMoney(userId, "in", date);
		return money > 0 ? money : 0;
	}

	@Override
	public int insertInterestBatch(List<DemandTreasureBill> list) {
		if (list.isEmpty()) {
			return 0;
		}
		return demandTreasureBillDao.insertInterestBatch(list);
	}

	@Override
	public PageInfo<DemandTreasureBill> pageLite(int pageNo, int pageSize,
			DemandTreasureBill bill) {
		return demandTreasureBillDao.pageLite(pageNo, pageSize, bill);
	}

	@Override
	public void insert(DemandTreasureBill bill) {
		demandTreasureBillDao.insert(bill);
	}

	@Override
	public double[] getTreasuerAccount() {
		double[] account = new double[3];
		account[0] = demandTreasureBillDao.getDemandTreasureMoneyByType("in")
				+ demandTreasureBillDao
						.getDemandTreasureMoneyByType("interest")
				- demandTreasureBillDao.getDemandTreasureMoneyByType("out")
				- demandTreasureBillDao
						.getDemandTreasureMoneyByType("outinterest");
		account[1] = demandTreasureBillDao
				.getDemandTreasureMoneyByType("interest");

		account[2] = demandTreasureBillDao.getDemandTreasureUser().size();
		return account;
	}

	
	@Override
	public List<DemandTreasureBill> getAccount(int days, String type) {
		return demandTreasureBillDao.getAccount(days, type);
	}
	public double getDemandTreasureRateMoney(String userId) {
		double money =ArithUtil.sub(demandTreasureBillDao
				.getDemandTreasureMoneyByType(userId,"interest"), demandTreasureBillDao
				.getDemandTreasureMoneyByType(userId,"outinterest")) 
				;
		return money > 0 ? money : 0;
	}

	@Override
	public PageInfo<DemandTreasureBill> pageLite02(int pageNo, int pageSize,
			DemandTreasureBill bill) {
		// TODO Auto-generated method stub
		return demandTreasureBillDao.pageLite02(pageNo, pageSize, bill);
	}

	@Override
	public int insertBill(DemandTreasureBill bill) {
		return demandTreasureBillDao.insertBill(bill);
	}

	@Override
	public List<String> getInterestUser() {
		// TODO Auto-generated method stub
		return demandTreasureBillDao.getInterestUser();
	}

	@Override
	public List<String> getInterestToday() {
		// TODO Auto-generated method stub
		return demandTreasureBillDao.getInterestToday();
	}
	@Override
	public double getDemandTreasureMoney(String userId) {
		return ArithUtil.round(getDemandTreasureInMoney(userId)
				- getDemandTreasureOutMoney(userId)
				+ getDemandTreasureInterestMoney(userId)
				- getDemandTreasureOutInterestMoney(userId), 2);
	}

	@Override
	public int getCount(String billId) {
		return demandTreasureBillDao.getCount(billId);
	}
	public double[] getTreasuerAccount(String userId) {
		double[] treasuerAccount = new double[11];
		// 可转出总金额
		treasuerAccount[0] = getDemandTreasureMoney(userId);
		// 昨日收益
		treasuerAccount[1] = demandTreasureBillDao.getDemandLaterInterest(userId);
		// 累计收益
		treasuerAccount[2] = getDemandTreasureInterestMoney(userId);

		DemandtreasureTransferOut out = queryDemandOutTreasureMoney(userId);
				
		double avlidIterest = getDemandAvlidInterestMoney(userId);

		double avalidMoney = getDemandAvlidTreasureMoney(userId);
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
		treasuerAccount[6] = getDemandInTreasureMoney(userId);
		
		
		//transferIn中累计转入成功的金额
		treasuerAccount[7] = demandtreasureTransferInDao.getTransferInAllMoney(userId);
		//transferOut中累计转出成功的金额
		treasuerAccount[8] = demandtreasureTransferOutDao.getTransferOutAllMoney(userId, "success");
		//demandbill中中累计转入成功的金额
		treasuerAccount[9] = demandTreasureBillDao.getDemandBillAllMoneyByStatus(userId, "'in','interest'");
		//demandbill中中累计转出成功的金额
		treasuerAccount[10] = demandTreasureBillDao.getDemandBillAllMoneyByStatus(userId, "'out','outinterest'");
		
		return treasuerAccount;
	}
	public DemandtreasureTransferOut queryDemandOutTreasureMoney(String userId) {
		return demandtreasureTransferOutDao.queryOutSumMoney(
				userId, DemandtreasureConstants.TransferInStatus.SENDED);
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

	public double getDemandInTreasureMoney(String userId) {
		return ArithUtil.round(
				demandtreasureTransferInDao.getuserDemandInSumMoney(userId), 2);
	}

	@Override
	public List<String> getInterestToday(Date date) {
	
		return demandTreasureBillDao.getInterestToday(date);
	}
}
