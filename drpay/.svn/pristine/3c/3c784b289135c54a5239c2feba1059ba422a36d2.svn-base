package com.duanrong.drpay.business.demand.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.duanrong.drpay.business.account.service.UserAccountService;
import com.duanrong.drpay.business.demand.DemandtreasureConstants;
import com.duanrong.drpay.business.demand.dao.DemandtreasureDao;
import com.duanrong.drpay.business.demand.dao.DemandtreasureTransferInDao;
import com.duanrong.drpay.business.demand.model.Demandtreasure;
import com.duanrong.drpay.business.demand.model.DemandtreasureTransferIn;
import com.duanrong.drpay.business.demand.service.AvailableMoneyRecordService;
import com.duanrong.drpay.business.demand.service.DemandTreasureBillService;
import com.duanrong.drpay.business.demand.service.DemandtreasureTransferInService;
import com.duanrong.drpay.config.BusinessEnum;
import com.duanrong.drpay.config.IdUtil;
import com.duanrong.drpay.config.ToType;

import util.ArithUtil;
import util.DateUtil;
import util.Log;
import base.exception.ErrorCodeException;
import base.exception.TradeException;
import base.pagehelper.PageInfo;

@Service
public class DemandtreasureTransferInServiceImpl implements
		DemandtreasureTransferInService {

	@Resource
	AvailableMoneyRecordService availableMoneyRecordService;

	@Resource
	DemandtreasureTransferInDao demandtreasureTransferInDao;

	@Resource
	DemandtreasureDao demandtreasureDao;

	@Resource
	DemandTreasureBillService demandTreasureBillService;

	@Resource
	UserAccountService userAccountService;

	@Resource
	Log log;

	@Override
	public PageInfo<DemandtreasureTransferIn> findPageInfo(int pageNo,
			int pageSize, DemandtreasureTransferIn entity) {
		return demandtreasureTransferInDao.findPageInfo(pageNo, pageSize,
				entity);
	}

	@Override
	public void insert(DemandtreasureTransferIn demandtreasureTransferIn) {
		demandtreasureTransferInDao.insert(demandtreasureTransferIn);
	}

	@Override
	public void update(DemandtreasureTransferIn demandtreasureTransferIn) {
		demandtreasureTransferInDao.update(demandtreasureTransferIn);
	}


	@Override
	@Transactional(rollbackFor = Exception.class)
	public void syncDemandIn(String userId, double money, String userSource)
			throws ErrorCodeException {
		/*********************** 参数校验 ***********************/

		Demandtreasure demandtreasure = demandtreasureDao.getWithLock();
		Date date = new Date();
		if (DateUtil.getHour(date) < demandtreasure.getStartTime()
				|| DateUtil.getHour(date) >= demandtreasure.getEndTime()) {
			throw new TradeException("不在投资时间范围内");
		}
		
		if (ArithUtil.round(demandtreasure.getAvailableMoney(), 2) < ArithUtil.round(money, 2)) {
			throw new TradeException("期宝转入金额大于项目剩余募集的金额");
		}
		if (ArithUtil.round(demandtreasure.getAvailableMoney(), 2) == ArithUtil.round(money, 2)) {
			// UPDATE Demand_treasure_availableMoneyRecord SET endtime=NOW()
			// WHERE STATUS=1 ORDER BY beginTIme DESC LIMIT 1;
//			availableMoneyRecordService.update(availableMoneyRecord);
		}
		// 修改可投金额
		demandtreasure.setAvailableMoney(demandtreasure.getAvailableMoney()-money);
		demandtreasureDao.update(demandtreasure);
		DemandtreasureTransferIn demandIn = new DemandtreasureTransferIn();
		String id = IdUtil.generateId(ToType.RCIN);
		demandIn.setId(id);
		demandIn.setSendedTime(new Date());
		demandIn.setFreezeTime(new Date());
		demandIn.setUserId(userId);
		demandIn.setMoney(money);
		demandIn.setTransferWay(userSource);
		demandIn.setStatus(DemandtreasureConstants.TransferInStatus.FREEZE);
		// 插入活期宝转入表
		demandtreasureTransferInDao.insert(demandIn);
		// 更新用户账户，加锁
		userAccountService.freeze(userId, money, BusinessEnum.demand_in,
				"冻结：投资天天赚", "活期宝投资冻结", id);
	}

	@Override
	public DemandtreasureTransferIn get(String id) {
		return demandtreasureTransferInDao.get(id);
	}

	@Override
	public PageInfo<DemandtreasureTransferIn> pageLite(int pageNo,
			int pageSize, DemandtreasureTransferIn in) {
		return demandtreasureTransferInDao.pageLite(pageNo, pageSize, in);
	}

}
