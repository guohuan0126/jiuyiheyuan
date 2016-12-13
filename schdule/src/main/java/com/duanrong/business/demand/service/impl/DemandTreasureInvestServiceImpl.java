package com.duanrong.business.demand.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.duanrong.business.demand.dao.DemandTreasureLoanDao;
import com.duanrong.business.demand.dao.DemandtreasureInvestDao;
import com.duanrong.business.demand.dao.DemandtreasureTransferInDao;
import com.duanrong.business.demand.dao.DemandtreasureTransferOutDao;
import com.duanrong.business.demand.model.DemandTransferFork;
import com.duanrong.business.demand.model.DemandTreasureInvest;
import com.duanrong.business.demand.model.DemandTreasureOpration;
import com.duanrong.business.demand.model.DemandtreasureLoan;
import com.duanrong.business.demand.model.DemandtreasureTransferIn;
import com.duanrong.business.demand.model.DemandtreasureTransferOut;
import com.duanrong.business.demand.service.DemandTreasureBillService;
import com.duanrong.business.demand.service.DemandTreasureInvestService;
import com.duanrong.business.sms.service.SmsService;
import com.duanrong.business.user.service.InformationService;
import com.duanrong.newadmin.utility.IdUtil;
import com.duanrong.util.ArithUtil;

@Service
public class DemandTreasureInvestServiceImpl implements
		DemandTreasureInvestService {

	@Resource
	DemandtreasureInvestDao demandtreasureInvestDao;
	@Resource
	DemandTreasureLoanDao demandTreasureLoanDao;
	@Resource
	DemandtreasureTransferOutDao demandtreasureTransferOutDao;

	@Resource
	DemandtreasureTransferInDao demandtreasureTransferInDao;

	@Resource
	DemandTreasureBillService demandTreasureBillService;
	
	@Resource
	SmsService smsService;
	
	@Resource
	InformationService informationService;
	
	@Override
	public void transferInFork(Map<String, DemandTransferFork> forks,
			String id, String type, double money, String userId) {
		try {
			if (!forks.isEmpty()) {
				List<DemandtreasureLoan> loans = new ArrayList<>();
				List<DemandTreasureInvest> invests = new ArrayList<>();
				List<DemandTreasureOpration> oprations = new ArrayList<>();
				for (Map.Entry<String, DemandTransferFork> entry : forks
						.entrySet()) {
					DemandtreasureLoan loan = new DemandtreasureLoan();
					loan.setId(entry.getKey());
					loan.setValidMoney(entry.getValue().getValidMoney());
					loans.add(loan);
					invests.add(entry.getValue().getInvest());
					oprations.add(entry.getValue().getOpration());
				}
				demandTreasureLoanDao.updateBatchValidMoney(loans);
				demandtreasureInvestDao.insertBatch(invests);
				demandtreasureInvestDao.insertBatchOpration(oprations);
				if (type.equals("in")) {
					DemandtreasureTransferIn in = new DemandtreasureTransferIn();
					in.setId(id);
					in.setFork(1);
					demandtreasureTransferInDao.update(in);
				} else if (type.equals("transfer")) {		
					Map<String, Object> map = new HashMap<>();
					map.put("id", id);
					map.put("money", money);
					demandtreasureInvestDao.deleteByMoney(map);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<DemandTreasureInvest> findAllOfUser(Map map) {
		return demandtreasureInvestDao.findAllOfUser(map);
	}

	@Override
	public void updateOutInvest(List list, DemandtreasureTransferOut outObj,
			List<DemandTreasureOpration> oprations) {
		if (!list.isEmpty()) {
			demandtreasureInvestDao.updateMoney(list);
			demandTreasureLoanDao.updateOpenAmount(list);
			demandtreasureInvestDao.insertBatchOpration(oprations);
			demandtreasureTransferOutDao.update(outObj);
		}

	}

	@Override
	public List<DemandTreasureInvest> getInvestByLoan() {

		return demandtreasureInvestDao.getInvestByLoan();
	}

	@Override
	public void expired(double money, String userId) {
		DemandtreasureTransferOut o = demandtreasureTransferOutDao
				.queryOutSumMoney(userId, "sended");
		if (o.getPrincipal() < money) {
			money -= o.getPrincipal();
			double interest = demandTreasureBillService
					.getDemandTreasureInterestMoney(userId)
					- demandTreasureBillService
							.getDemandTreasureOutInterestMoney(userId)
					- o.getInterest();
			// 退出，添加一条转出申请的记录
			DemandtreasureTransferOut out = new DemandtreasureTransferOut();
			String outId = IdUtil.randomUUID();
			out.setId(outId);
			out.setUserId(userId);
			out.setSendedTime(new Date());
			out.setTransferWay("expires");
			out.setStatus("sended");
			out.setFork(0);
			out.setDetail("项目到期, 金额强制退出");
			out.setInterest(ArithUtil.round(interest, 2));
			out.setPrincipal(ArithUtil.round(money, 2));
			out.setMoney(ArithUtil.round(money + interest, 2));
			demandtreasureTransferOutDao.insert(out);

			DemandTreasureOpration opration = new DemandTreasureOpration();
			opration.setId(IdUtil.randomUUID());
			opration.setDemandLoanId("");
			opration.setDemandTransferId(outId);
			opration.setDemandTreasureInvestId("");
			opration.setMoney(money);
			opration.setType("expired");
			demandtreasureInvestDao.insertOpration(opration);
			
			//String sms = "您的天天赚账户部分项目到期未成功续投，退还本息共计"+ArithUtil.round(money + interest, 2)+"元，已发放至您的资金托管账户。";
			//smsService.sendSms(userId, sms, null);
			//informationService.sendInformation(userId, "天天赚退出通知", sms);
		}
		
	}

	@Override
	public List<DemandTreasureInvest> getInvestMoneyByLoan() {
		
		return demandtreasureInvestDao.getInvestMoneyByLoan();
	}

}
