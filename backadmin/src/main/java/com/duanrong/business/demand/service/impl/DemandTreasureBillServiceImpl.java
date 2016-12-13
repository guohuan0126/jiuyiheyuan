package com.duanrong.business.demand.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import base.pagehelper.PageInfo;

import com.duanrong.business.demand.DemandtreasureConstants;
import com.duanrong.business.demand.dao.DemandTreasureBillDao;
import com.duanrong.business.demand.dao.DemandtreasureTransferInDao;
import com.duanrong.business.demand.dao.DemandtreasureTransferOutDao;
import com.duanrong.business.demand.model.DemandTreasureAccount;
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
	public double readDemandTreasureInMoney(String userId) {

		return demandTreasureBillDao.readDemandTreasureMoneyByType(userId, "in");
	}

	@Override
	public double readDemandTreasureOutMoney(String userId) {
		return demandTreasureBillDao
				.readDemandTreasureMoneyByType(userId, "out");
	}

	@Override
	public double readDemandTreasureInterestMoney(String userId) {
		return demandTreasureBillDao.readDemandTreasureMoneyByType(userId,
				"interest");
	}
	@Override
	public double readDemandTreasureOutInterestMoney(String userId) {
		return demandTreasureBillDao.readDemandTreasureMoneyByType(userId,
				"outinterest");
	}
	
	@Override
	public double readDemandTreasureInvalidMoney(String userId, String type,
			Date date) {
		return demandTreasureBillDao.readDemandTreasureInvalidMoney(userId,
				type, date);
	}

	@Override
	public List<String> readDemandTreasureUser() {

		return demandTreasureBillDao.readDemandTreasureUser();
	}

	@Override
	public double readDemandTreasureValidMoney(String userId) {
		double inMoney =  readDemandTreasureInMoney(userId);
		double outMoney = readDemandTreasureOutMoney(userId);
		double invalidMoney = readDemandTreasureInvalidMoney(userId, "in", new Date());
		double money = inMoney - outMoney - invalidMoney;
		return money > 0 ? money : 0;
	}

	@Override
	public double readDemandTreasureValidMoney(String userId, Date date) {
		double money = readDemandTreasureInMoney(userId)
				+ readDemandTreasureInvalidMoney(userId, "out", date)
				- readDemandTreasureOutMoney(userId)
				- readDemandTreasureInvalidMoney(userId, "in", date);
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
	public PageInfo<DemandTreasureBill> readPageLite(int pageNo, int pageSize,
			DemandTreasureBill bill) {
		return demandTreasureBillDao.pageLite(pageNo, pageSize, bill);
	}

	@Override
	public void insert(DemandTreasureBill bill) {
		demandTreasureBillDao.insert(bill);
	}

	@Override
	public double[] readTreasuerAccount() {
		double[] account = new double[3];
		account[0] = demandTreasureBillDao.readDemandTreasureMoneyByType("in")
				+ demandTreasureBillDao
						.readDemandTreasureMoneyByType("interest")
				- demandTreasureBillDao.readDemandTreasureMoneyByType("out")
				- demandTreasureBillDao
						.readDemandTreasureMoneyByType("outinterest");
		account[1] = demandTreasureBillDao
				.readDemandTreasureMoneyByType("interest");

		account[2] = demandTreasureBillDao.readDemandTreasureUser().size();
		return account;
	}

	
	@Override
	public List<DemandTreasureBill> readAccount(int days, String type) {
		return demandTreasureBillDao.readAccount(days, type);
	}
	public double readDemandTreasureRateMoney(String userId) {
		double money =ArithUtil.sub(demandTreasureBillDao
				.readDemandTreasureMoneyByType(userId,"interest"), demandTreasureBillDao
				.readDemandTreasureMoneyByType(userId,"outinterest")) 
				;
		return money > 0 ? money : 0;
	}

	@Override
	public PageInfo<DemandTreasureBill> readPageLite02(int pageNo, int pageSize,
			DemandTreasureBill bill) {
		// TODO Auto-generated method stub
		return demandTreasureBillDao.readPageLite02(pageNo, pageSize, bill);
	}

	@Override
	public int insertBill(DemandTreasureBill bill) {
		return demandTreasureBillDao.insertBill(bill);
	}

	@Override
	public List<String> readInterestUser() {
		// TODO Auto-generated method stub
		return demandTreasureBillDao.readInterestUser();
	}

	@Override
	public List<String> readInterestToday() {
		// TODO Auto-generated method stub
		return demandTreasureBillDao.readInterestToday();
	}
	@Override
	public double readDemandTreasureMoney(String userId) {
		return ArithUtil.round(readDemandTreasureInMoney(userId)
				- readDemandTreasureOutMoney(userId)
				+ readDemandTreasureInterestMoney(userId)
				- readDemandTreasureOutInterestMoney(userId), 2);
	}

	@Override
	public int readCount(String billId) {
		return demandTreasureBillDao.readCount(billId);
	}
	public double[] readTreasuerAccount(String userId) {
		double[] treasuerAccount = new double[12];
		// 总金额(包含利息)
		treasuerAccount[0] = readDemandTreasureMoney(userId);
		// 昨日收益
		treasuerAccount[1] = demandTreasureBillDao.readDemandLaterInterest(userId);
		// 累计收益
		treasuerAccount[2] = readDemandTreasureInterestMoney(userId);

		DemandtreasureTransferOut out = readDemandOutTreasureMoney(userId);
		//活期宝总利息		
		double avlidIterest = readDemandAvlidInterestMoney(userId);
       //活期宝总本金
		double avalidMoney = readDemandAvlidTreasureMoney(userId);
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
		treasuerAccount[6] = readDemandInTreasureMoney(userId);
		
		
		//transferIn中累计转入成功的金额
		treasuerAccount[7] = demandtreasureTransferInDao.readTransferInAllMoney(userId);
		//transferOut中累计转出成功的金额
		treasuerAccount[8] = demandtreasureTransferOutDao.getTransferOutAllMoney(userId, "success");
		//demandbill中中累计转入成功的金额
		treasuerAccount[9] = demandTreasureBillDao.readDemandBillAllMoneyByStatus(userId, "'in','interest'");
		//demandbill中中累计转出成功的金额
		treasuerAccount[10] = demandTreasureBillDao.readDemandBillAllMoneyByStatus(userId, "'out','outinterest'");
		// 活期宝总本金
		treasuerAccount[11] = avalidMoney;
		//System.out.println(treasuerAccount[11]+"avalidMoney");
		return treasuerAccount;
	}
	public DemandtreasureTransferOut readDemandOutTreasureMoney(String userId) {
		return demandtreasureTransferOutDao.queryOutSumMoney(
				userId, DemandtreasureConstants.TransferInStatus.SENDED);
	}
	@Override
	public double readDemandAvlidTreasureMoney(String userId) {
		return ArithUtil.round(readDemandTreasureInMoney(userId)
				- readDemandTreasureOutMoney(userId), 2);
		
	}

	@Override
	public double readDemandAvlidInterestMoney(String userId) {
		return ArithUtil.round(readDemandTreasureInterestMoney(userId)
				- readDemandTreasureOutInterestMoney(userId), 2);
	}

	public double readDemandInTreasureMoney(String userId) {
		return ArithUtil.round(
				demandtreasureTransferInDao.readUserDemandInSumMoney(userId), 2);
	}

	@Override
	public List<String> readInterestToday(Date date, String userId) {
	
		return demandTreasureBillDao.readInterestToday(date, userId);
	}

	@Override
	public boolean readIsDemandUser(String userId) {
		
		return demandTreasureBillDao.readDemandBillCountByUserId(userId) > 0 ? true : false;
	}

	@Override
	public DemandTreasureAccount readDemandAccount(String userId, Date date) {
		
		return demandTreasureBillDao.readDemandAccount(userId, date);
	}
	@Override
	public double readUserDemandTreasure(String userId) {
		double money =ArithUtil.sub(demandTreasureBillDao
				.readDemandTreasureMoneyByType(userId,"in"), demandTreasureBillDao
				.readDemandTreasureMoneyByType(userId,"out")) 
				;
		return money > 0 ? money : 0;
	}
}
