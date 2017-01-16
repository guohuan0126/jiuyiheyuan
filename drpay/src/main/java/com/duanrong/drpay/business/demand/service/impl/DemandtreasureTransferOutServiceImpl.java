package com.duanrong.drpay.business.demand.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.ArithUtil;
import util.Log;
import base.error.ErrorCode;
import base.exception.TradeException;
import com.duanrong.drpay.business.account.service.UserAccountBusinessService;
import com.duanrong.drpay.business.account.service.UserAccountService;
import com.duanrong.drpay.business.demand.DemandtreasureConstants;
import com.duanrong.drpay.business.demand.dao.DemandTreasureBillDao;
import com.duanrong.drpay.business.demand.dao.DemandtreasureGuOutDao;
import com.duanrong.drpay.business.demand.dao.DemandtreasureGuOutDetailDao;
import com.duanrong.drpay.business.demand.dao.DemandtreasureTransferOutDao;
import com.duanrong.drpay.business.demand.model.DemandtreasureGuOut;
import com.duanrong.drpay.business.demand.model.DemandtreasureGuOutDetail;
import com.duanrong.drpay.business.demand.model.DemandtreasureTransferOut;
import com.duanrong.drpay.business.demand.service.DemandTreasureBillService;
import com.duanrong.drpay.business.demand.service.DemandtreasureTransferOutService;

@Service
public class DemandtreasureTransferOutServiceImpl implements
		DemandtreasureTransferOutService {

	@Resource
	DemandtreasureTransferOutDao demandtreasureTransferOutDao;

	@Resource
	DemandtreasureGuOutDetailDao demandtreasureGuOutDetailDao;

	@Resource
	DemandtreasureGuOutDao demandtreasureGuOutDao;

	@Resource
	UserAccountService userAccountService;

	@Resource
	DemandTreasureBillDao demandTreasureBillDao;

	@Resource
	UserAccountBusinessService userAccountBusinessService;

	@Resource
	DemandTreasureBillService demandTreasureBillService;
	
	@Resource
	Log log;

	@Override
	public void insert(DemandtreasureTransferOut demandOut) {
		demandtreasureTransferOutDao.insert(demandOut);
	}

	@Override
	public void update(DemandtreasureTransferOut demandOut) {
		demandtreasureTransferOutDao.update(demandOut);

	}

	@Override
	public Double getOutMoneySumByStatus(String userId, String status) {
		return ArithUtil.round(demandtreasureTransferOutDao
				.getOutMoneySumByStatus(userId, status), 2);
	}

	@Override
	public Integer getValidOutCount(String userId) {
		return demandtreasureTransferOutDao.getValidOutCount(userId);
	}


	@Override
	public Double getOutInterestSumByStatus(String userId, String status) {
		return ArithUtil.round(demandtreasureTransferOutDao
				.getOutInterestSumByStatus(userId, status), 2);
	}

	@Override
	public double getValidOutSumMoney(String userId) {
		return demandtreasureTransferOutDao.getValidOutSumMoney(userId);
	}

	@Transactional
	private void insertGuoutAndDetail(String userId, String requestNo)
			throws TradeException {
		List<DemandtreasureTransferOut> list = demandtreasureTransferOutDao
				.findAll();
		if (list != null && list.size() != 0) {
			List<DemandtreasureGuOutDetail> details = new ArrayList<DemandtreasureGuOutDetail>();
			double totalMoney = 0D;
			for (DemandtreasureTransferOut tad : list) {
				DemandtreasureGuOutDetail detail = new DemandtreasureGuOutDetail();
				detail.setSendedTime(new Date());
				detail.setId(requestNo);
				detail.setGuOutId(requestNo);
				detail.setTransferOutId(tad.getId());
				detail.setStatus(DemandtreasureConstants.TransferInStatus.SENDED);
				detail.setMoney(tad.getMoney());
				detail.setUserId(tad.getUserId());
				detail.setInterest(tad.getInterest());
				detail.setPrincipal(tad.getPrincipal());
				details.add(detail);
				totalMoney += tad.getMoney();
			}
			DemandtreasureGuOut guOut = new DemandtreasureGuOut();
			guOut.setId(requestNo);
			guOut.setMoney(ArithUtil.round(totalMoney, 2));
			guOut.setRequestXml("");
			guOut.setSendedTime(new Date());
			guOut.setUserId(userId);
			guOut.setStatus(DemandtreasureConstants.TransferInStatus.SENDED);
			demandtreasureGuOutDao.insert(guOut);
			demandtreasureGuOutDetailDao.insertBatch(details);

		} else {
			log.infoLog("活期宝转出", "没有可转出的用户");
//			throw new TradeException(ErrorCode.DemandTreasureOutNoUsers);
		}
	}

	@Override
	public void enhanceValidateTransferOut() throws TradeException {
		List<DemandtreasureTransferOut> trOut = demandtreasureTransferOutDao
				.getTotalUser();
		for (DemandtreasureTransferOut t : trOut) {
			double money = demandTreasureBillService.getDemandTreasureMoney(t
					.getUserId());
			// 第一步验证用户转出资金是否超过该用户的活期宝账户资金
			if (t.getSummoney() > money) {
				log.errLog("活期宝转出增强型校验", "用户：" + t.getUserId() + "转出金额："
						+ t.getSummoney() + "，超过他活期宝当前账户资金：" + money);
				throw new TradeException(ErrorCode.DemandBalanceToLow);
			}
		}
	}
}
