package com.duanrong.drpay.business.platformtransfer.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import base.error.ErrorCode;
import base.exception.DataAlreadExistException;
import base.exception.TradeException;

import com.duanrong.drpay.business.platformtransfer.dao.PlatformTransferDao;
import com.duanrong.drpay.business.platformtransfer.model.PlatformTransfer;
import com.duanrong.drpay.business.platformtransfer.service.PlatformTransferService;
import com.duanrong.drpay.config.IdUtil;

@Service
public class PlatformTransferServiceImpl implements PlatformTransferService {

	@Resource
	PlatformTransferDao platformTransferDao;

	@Override
	public void insertBatch(List<PlatformTransfer> list) {
		platformTransferDao.insertBatch(list);
	}

	@Override
	public void insert(PlatformTransfer platformTransfer) {
		platformTransferDao.insert(platformTransfer);
	}

	@Override
	public void update(PlatformTransfer platformTransfer) {
		platformTransferDao.update(platformTransfer);
	}

	@Override
	public List<PlatformTransfer> getPlatformTransfer(
			PlatformTransfer platformTransfer) {
		return platformTransferDao.getPlatformTransfer(platformTransfer);
	}

	@Override
	public PlatformTransfer prepare(String requestNo, String userId,
			double rewardMoney, String rewardType, String loanId, String remarks)
			throws DataAlreadExistException, TradeException {
		PlatformTransfer platformTransfer = new PlatformTransfer();
		platformTransfer.setUsername(userId);
		platformTransfer.setOrderId(requestNo);
		platformTransfer.setType(rewardType);
		List<PlatformTransfer> transfers = platformTransferDao
				.getPlatformTransfer(platformTransfer);
		if (!transfers.isEmpty()){
			platformTransfer = transfers.get(0);
			if(platformTransfer.getStatus().equals("平台划款成功")) throw new DataAlreadExistException(ErrorCode.RewardExist);
			else if(platformTransfer.getStatus().equals("平台划款失败")) throw new TradeException(ErrorCode.RewardError);
		}else{
			platformTransfer.setActualMoney(rewardMoney);
			platformTransfer.setRemarks(remarks);
			platformTransfer.setId(IdUtil.randomUUID());
			platformTransfer.setTime(new Date());
			platformTransfer.setSuccessTime(new Date());
			platformTransfer.setLoanId(loanId);
			platformTransfer.setStatus("等待平台划款");
			platformTransferDao.insert(platformTransfer);
		}
		return platformTransfer;
	}

}