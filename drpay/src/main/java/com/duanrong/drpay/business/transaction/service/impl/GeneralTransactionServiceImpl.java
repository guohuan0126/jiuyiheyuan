package com.duanrong.drpay.business.transaction.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.duanrong.drpay.business.platformtransfer.model.PlatformTransfer;
import com.duanrong.drpay.business.platformtransfer.service.PlatformTransferService;
import com.duanrong.drpay.business.transaction.constants.GeneralTransferConstants;
import com.duanrong.drpay.business.transaction.dao.TransactionAuthorizationDao;
import com.duanrong.drpay.business.transaction.dao.TransactionAuthorizationDetailDao;
import com.duanrong.drpay.business.transaction.model.TransactionAuthorization;
import com.duanrong.drpay.business.transaction.model.TransactionAuthorizationDetail;
import com.duanrong.drpay.business.transaction.service.GeneralTransactionService;
import com.duanrong.drpay.config.IdUtil;
import com.duanrong.drpay.config.ToType;

import util.DateUtil;
import util.Log;
import base.error.ErrorCode;
import base.exception.TradeException;

@Service
public class GeneralTransactionServiceImpl implements GeneralTransactionService {

	@Resource
	TransactionAuthorizationDao transactionAuthorizationDao;

	@Resource
	TransactionAuthorizationDetailDao transactionAuthorizationDetailDao;

	@Resource
	Log log;

	@Resource
	PlatformTransferService platformTransferService;

	@Override
	@Transactional
	public TransactionAuthorization prepare(String userId,
			double money) {
		TransactionAuthorization transactionAuthorization = new TransactionAuthorization();
		transactionAuthorization.setId(IdUtil.generateId(ToType.TSCA));
		transactionAuthorization.setUserId(userId);
		Date d = new Date();
		transactionAuthorization.setCommitTime(d);
		transactionAuthorization.setExpired(DateUtil.addMinute(d, 5));
		transactionAuthorization
				.setStatus(GeneralTransferConstants.TransferStatus.WAIT);
		transactionAuthorization.setAmount(money);
		/*TransactionAuthorizationDetail detail = new TransactionAuthorizationDetail();
		detail.setTransactionAuthorizationId(transactionAuthorization.getId());
		detail.setUserId(targetUserId);
		detail.setAmount(money);
		detail.setStatus(GeneralTransferConstants.TransferStatus.WAIT);*/
		insertGeneralTransaction(transactionAuthorization);
		// 添加平台划款入账记录
		PlatformTransfer platformTransfer = new PlatformTransfer();
		platformTransfer.setActualMoney(money);
		platformTransfer.setBillType("in");
		platformTransfer.setId(IdUtil.randomUUID());
		platformTransfer.setOrderId(transactionAuthorization.getId());
		platformTransfer.setType("transfer");
		platformTransfer.setUsername(userId);
		platformTransfer.setTime(d);
		platformTransfer.setStatus("等待平台划款");
		platformTransfer.setRemarks("用户向平台转账user：" + userId);
		platformTransferService.insert(platformTransfer);
		return transactionAuthorization;
	}

	@Override
	public void insertGeneralTransaction(
			TransactionAuthorization transactionAuthorization) {
		transactionAuthorizationDao.insert(transactionAuthorization);
		/*TransactionAuthorizationDetail detail = transactionAuthorization
				.getTransactionAuthorizationDetail();*/
		/*if (detail != null) {
			transactionAuthorizationDetailDao.insert(detail);
		}*/
	}
	
	@Override
	public void updateGeneralTransaction(
			TransactionAuthorization transactionAuthorization) {
		transactionAuthorizationDao.update(transactionAuthorization);
		/*TransactionAuthorizationDetail detail = transactionAuthorization
				.getTransactionAuthorizationDetail();*/
		/*if (detail != null) {
			transactionAuthorizationDetailDao.insert(detail);
		}*/
	}

	@Override
	@Transactional
	public 	TransactionAuthorization transferLocal(String tranid) throws TradeException {
		TransactionAuthorization tran = transactionAuthorizationDao.get(tranid);
		if (tran == null) {
			log.errLog("转账回调错误", "tranId: " + tranid + "记录不存在");
			throw new TradeException(ErrorCode.TargetUserNotFind);
		}
		if (!GeneralTransferConstants.TransferStatus.WAIT.equals(tran
				.getStatus())) {
			log.errLog("转账回调错误", "tranid： " + tranid + "， 转账主表状态不正确， status："
					+ tran.getStatus());
			throw new TradeException(ErrorCode.TransferStatusError);
		}
		tran.setStatus(GeneralTransferConstants.TransferStatus.CONFIRM);
		updateGeneralTransaction(tran);
		/*TransactionAuthorizationDetail detail = new TransactionAuthorizationDetail();
		List<TransactionAuthorizationDetail> list = transactionAuthorizationDetailDao
				.find(detail);
		if (list.isEmpty()) {
			log.errLog("转账回调错误", "tranId: " + tranid + "明细不存在");
			throw new TradeException(ErrorCode.TargetUserNotFind);
		}
		detail = list.get(0);
		detail.setStatus(GeneralTransferConstants.TransferStatus.PREAUTH);*/
		/*transactionAuthorizationDetailDao.update(detail);
		tran.setTransactionAuthorizationDetail(detail);*/
		// 添加平台划款入账记录
		PlatformTransfer platformTransfer = new PlatformTransfer();
		platformTransfer.setOrderId(tranid);
		platformTransfer.setType("transfer");
		platformTransfer.setUsername(tran.getUserId());
		platformTransfer.setStatus("'等待平台划款'");
		List<PlatformTransfer> transfers = platformTransferService.getPlatformTransfer(platformTransfer);
		if(transfers != null && !transfers.isEmpty()){
			platformTransfer = transfers.get(0);
			platformTransfer.setStatus("平台划款成功");
			platformTransferService.update(platformTransfer);
		}
		return tran;
	}

	@Override
	public void transferConfirm(String tranid) throws TradeException {
		TransactionAuthorization tran = transactionAuthorizationDao.get(tranid);
		if (tran == null) {
			log.errLog("转账回调错误", "tranId: " + tranid + "记录不存在");
			throw new TradeException(ErrorCode.TargetUserNotFind);
		}
		if (GeneralTransferConstants.TransferStatus.PREAUTH.equals(tran
				.getStatus())) {
			log.errLog("转账回调错误", "tranid： " + tranid + "， 转账主表状态不正确， status："
					+ tran.getStatus());
			throw new TradeException(ErrorCode.TransferStatusError);
		}
		tran.setStatus(GeneralTransferConstants.TransferStatus.CONFIRM);
		transactionAuthorizationDao.update(tran);
		TransactionAuthorizationDetail detail = new TransactionAuthorizationDetail();
		List<TransactionAuthorizationDetail> list = transactionAuthorizationDetailDao
				.find(detail);
		if (list.isEmpty()) {
			log.errLog("转账回调错误", "tranId: " + tranid + "明细不存在");
			throw new TradeException(ErrorCode.TargetUserNotFind);
		}
		detail = list.get(0);
		detail.setStatus(GeneralTransferConstants.TransferStatus.CONFIRM);
		transactionAuthorizationDetailDao.update(detail);
		tran.setTransactionAuthorizationDetail(detail);
	}

	
}