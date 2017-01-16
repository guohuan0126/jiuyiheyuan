package com.duanrong.drpay.trusteeship.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.duanrong.drpay.business.account.model.BankCard;
import com.duanrong.drpay.business.payment.model.Recharge;
import com.duanrong.drpay.business.payment.model.WithdrawCash;
import com.duanrong.drpay.business.payment.service.RechargeService;
import com.duanrong.drpay.business.payment.service.WithdrawCashService;
import com.duanrong.drpay.config.BusinessEnum;
import com.duanrong.drpay.trusteeship.constants.TrusteeshipServer;
import com.duanrong.drpay.trusteeship.helper.model.Generator;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorDetailJSON;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorJSON;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorPlatformJSON;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorPreTransactionJSON;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorRechargeJSON;
import com.duanrong.drpay.trusteeship.helper.model.GeneratorWithdrawJSON;
import com.duanrong.drpay.trusteeship.helper.model.TransactionType;
import com.duanrong.drpay.trusteeship.helper.service.TrusteeshipService;
import com.duanrong.drpay.trusteeship.service.TrusteeshipInvestService;
import com.duanrong.drpay.trusteeship.service.TrusteeshipPaymentService;
import com.duanrong.drpay.trusteeship.service.TrusteeshipTransactionQueryService;

import base.error.ErrorCode;
import base.exception.AccountException;
import base.exception.TradeException;
import util.Log;

@Service
public class TrusteeshipTransactionQueryServiceImpl implements TrusteeshipTransactionQueryService {

	@Resource
	TrusteeshipService trusteeshipService;

	@Resource
	TrusteeshipPaymentService trusteeshipPaymentService;

	@Resource
	TrusteeshipInvestService trusteeshipInvestService;

	@Resource
	RechargeService rechargeService;

	@Resource
	WithdrawCashService withdrawCashService;

	@Resource
	Log log;

	/**
	 * 单笔业务查询 流水号、类型
	 * 
	 * @throws SchedulerException
	 * @throws AccountException
	 * @throws TradeException
	 */
	@Override
	public Generator queryTransaction(String requestNo, BusinessEnum type) throws TradeException, AccountException {
		return queryTransaction(requestNo, type, 0);
	}

	/**
	 * 单笔业务查询 流水号、类型
	 * 
	 * @throws SchedulerException
	 * @throws AccountException
	 * @throws TradeException
	 */
	@Override
	public Generator queryTransaction(String requestNo, BusinessEnum type, int handle)
			throws TradeException, AccountException {
		GeneratorJSON json = new GeneratorJSON();
		json.setRequestNo(requestNo);
		String transactionType = null;
		if (type == BusinessEnum.recharge) {
			transactionType = TransactionType.RECHARGE.name();
		} else if (type == BusinessEnum.withdraw_cash) {
			transactionType = TransactionType.WITHDRAW.name();
		} else if (type == BusinessEnum.invest || type == BusinessEnum.repay || type == BusinessEnum.reward
				|| type == BusinessEnum.demand_out || type == BusinessEnum.allowance) {
			transactionType = TransactionType.PRETRANSACTION.name();
		} else if (type == BusinessEnum.repay_confirm) {
			transactionType = TransactionType.TRANSACTION.name();
		} else if (type == BusinessEnum.freeze) {
			transactionType = TransactionType.FREEZE.name();
		} else if (type == BusinessEnum.unfreeze) {
			transactionType = TransactionType.UNFREEZE.name();
		}
		json.setTransactionType(transactionType);
		Generator generator = trusteeshipService.execute(json, TrusteeshipServer.QUERY_TRANSACTION,
				GeneratorJSON.class);
		if (handle == 1) {
			handleLocalBusiness(generator, requestNo, type);
		}
		return generator;
	}

	/**
	 * 根据类型处理本地业务
	 * 
	 * @param generator
	 * @param requestNo
	 * @param type
	 * @throws SchedulerException
	 * @throws AccountException
	 * @throws TradeException
	 */
	private void handleLocalBusiness(Generator generator, String requestNo, BusinessEnum type)
			throws TradeException, AccountException {
		GeneratorJSON data = generator.getRespData();
		List<GeneratorDetailJSON> details = data.getRecords();
		String status = null;
		if (type == BusinessEnum.invest) {
			GeneratorPreTransactionJSON json = new GeneratorPreTransactionJSON();
			json.setStatus(status);
			json.setRequestNo(requestNo);
			json.setCode(data.getCode());
			trusteeshipInvestService.investCallback(json, requestNo);
		}
		if ("0".equals(data.getCode()) && !CollectionUtils.isEmpty(details)) {
			GeneratorDetailJSON detail = details.get(0);
			status = detail.getStatus();
			if (type == BusinessEnum.recharge) {
				GeneratorRechargeJSON json = new GeneratorRechargeJSON();
				json.setStatus(status);
				json.setRequestNo(requestNo);
				json.setCode(data.getCode());
				trusteeshipPaymentService.rechargeCallback(json);
			} else if (type == BusinessEnum.withdraw_cash) {
				GeneratorWithdrawJSON json = new GeneratorWithdrawJSON();
				json.setStatus(status);
				json.setRequestNo(requestNo);
				json.setCode(data.getCode());
				trusteeshipPaymentService.withdrawCallback(json);
			}
		} else if ("100007".equals(data.getCode())) {
			if (type == BusinessEnum.recharge) {
				Recharge recharge = new Recharge();
				recharge.setId(requestNo);
				recharge.setStatus("fail");
				rechargeService.update(recharge);
			} else if (type == BusinessEnum.withdraw_cash) {
				WithdrawCash withdrawCash = new WithdrawCash();
				withdrawCash.setId(requestNo);
				withdrawCash.setStatus("fail");
				withdrawCash.setBankCard(new BankCard());
				withdrawCashService.update(withdrawCash);
			}
		} else {
			Date date = DateUtils.addMinutes(new Date(), -60);
			if (type == BusinessEnum.recharge) {
				Recharge recharge = rechargeService.get(requestNo);
				if (date.getTime() > recharge.getTime().getTime()) {
					recharge.setId(requestNo);
					recharge.setStatus("fail");
					rechargeService.update(recharge);
					log.errLog("充值单笔业务查询", "requestNo:" + requestNo + "超过1小时没有充值结果，置为fail", 1);
				}
			} else if (type == BusinessEnum.withdraw_cash) {
				WithdrawCash withdrawCash = withdrawCashService.get(requestNo);
				if (date.getTime() > withdrawCash.getTime().getTime()) {
					withdrawCash.setId(requestNo);
					withdrawCash.setStatus("fail");
					withdrawCash.setBankCard(new BankCard());
					withdrawCashService.update(withdrawCash);
					log.errLog("提现单笔业务查询", "requestNo:" + requestNo + "超过1小时没有提现结果，置为fail", 1);
				}
			}
			log.errLog("查询状态不为0", "流水号：" + requestNo + ",respData:" + data.toJSON());
			throw new TradeException(ErrorCode.CgtCodeError);
		}
	}

	@Override
	public GeneratorJSON queryPlatformInfo() {
		GeneratorJSON json = new GeneratorJSON();
		Generator generator = trusteeshipService.execute(json, TrusteeshipServer.QUERY_PLATFORM_INFORMATION,
				GeneratorPlatformJSON.class);
		return generator.getRespData();
	}

	@Override
	public GeneratorPreTransactionJSON queryProjectOrder(String requestNo) {
		GeneratorJSON json = new GeneratorJSON();
		json.setRequestNo(requestNo);
		Generator generator = trusteeshipService.execute(json, TrusteeshipServer.QUERY_INTELLIGENT_PROJECT_ORDER,
				GeneratorJSON.class);
		return (GeneratorPreTransactionJSON) generator.getRespData();
	}
}
