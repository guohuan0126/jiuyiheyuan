package com.duanrong.business.withdraw.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import util.Log;
import base.pagehelper.PageInfo;

import com.duanrong.business.account.BusinessEnum;
import com.duanrong.business.account.PaymentEnum;
import com.duanrong.business.account.service.PaymentAccountService;
import com.duanrong.business.account.service.PlatformAccountService;
import com.duanrong.business.account.service.UserAccountService;
import com.duanrong.business.sms.service.SmsService;
import com.duanrong.business.trusteeship.model.TrusteeshipOperation;
import com.duanrong.business.trusteeship.service.TrusteeshipOperationService;
import com.duanrong.business.user.service.UserService;
import com.duanrong.business.withdraw.dao.PaymentWithdrawRecordDao;
import com.duanrong.business.withdraw.model.PaymentWithdrawRecord;
import com.duanrong.business.withdraw.model.WithdrawCash;
import com.duanrong.business.withdraw.service.PaymentWithdrawRecordService;
import com.duanrong.business.withdraw.service.WithdrawCashService;
import com.duanrong.util.ArithUtil;
import com.duanrong.util.jedis.DRJedisDLock;
import com.duanrong.yeepay.service.TrusteeshipTransferService;

@Service
public class PaymentWithdrawRecordServiceImpl implements
		PaymentWithdrawRecordService {

	@Resource
	PaymentWithdrawRecordDao paymentWithdrawRecordDao;

	@Resource
	WithdrawCashService withdrawCashService;

	@Resource
	TrusteeshipOperationService trusteeshipOperationService;

	@Resource
	SmsService smsService;

	@Resource
	TrusteeshipTransferService trusteeshipTransferService;

	@Resource
	UserService userService;

	@Resource
	UserAccountService userAccountService;

	@Resource
	PlatformAccountService platformAccountService;

	@Resource
	PaymentAccountService paymentAccountService;

	@Resource
	Log log;

	@Override
	public PaymentWithdrawRecord read(String markId, String operator,
			String paymentId) {
		List<PaymentWithdrawRecord> list = paymentWithdrawRecordDao.get(markId,
				operator, paymentId);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void update(PaymentWithdrawRecord record) {
		paymentWithdrawRecordDao.update(record);
	}

	@Override
	public PageInfo<PaymentWithdrawRecord> readPageLite(int pageNo,
			int pageSize, PaymentWithdrawRecord paymentWithdrawRecord) {
		return paymentWithdrawRecordDao.pageLite(pageNo, pageSize,
				paymentWithdrawRecord);
	}

	/**
	 * 取消转账
	 * 
	 * @param loan
	 * @param invests
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@Override
	public String transferCancle(String requestNo) throws Exception {
		TrusteeshipOperation to = trusteeshipOperationService.read("transfer",
				requestNo, requestNo, null);
		Map<String, String> map = trusteeshipTransferService.transfer(
				requestNo, "CANCEL", to);
		String code = map.get("code");
		if (code.equals("1")) {
			to.setStatus("cancle");
			trusteeshipOperationService.update(to);
			PaymentWithdrawRecord record = read(requestNo, requestNo, "JDpay");
			record.setConfirmResponseData(to.getRequestData());
			record.setConfirmResponseTime(new Date());
			record.setStatus("refused");
			// FIXME 提现退款
			/*if (DRJedisDLock.getDLock("withdraw" + requestNo, requestNo)) {*/
				try {
					// 退款，提现记录跟新，退还用户金额
					WithdrawCash withdrawCash = withdrawCashService.read(to
							.getMarkId());
					if (withdrawCash != null
							&& "success".equals(withdrawCash.getStatus())) {
						withdrawCash.setStatus("fail");
						withdrawCashService.update(withdrawCash);
					}
					userAccountService.transferIn(record.getUserId(),
							record.getMoney(), BusinessEnum.refund, "提现失败",
							"提现失败退款，userId："+record.getUserId()+"，流水号："+requestNo, requestNo);
					paymentAccountService.transferIn(PaymentEnum.JDpay,
							record.getMoney(), BusinessEnum.refund, "提现失败，userId："+record.getUserId()+"，流水号："+requestNo,
							requestNo);
					paymentAccountService.transferIn(PaymentEnum.JDpay, 1.5,
							BusinessEnum.refund, "提现失败，退还手续费，userId："+record.getUserId()+"，流水号："+requestNo, requestNo);
					platformAccountService.transferIn(1.5, BusinessEnum.refund,
							"提现失败，退还手续费，userId："+record.getUserId()+"，流水号："+requestNo, requestNo);
					paymentWithdrawRecordDao.update(record);
					// 代付失败，提现退款
					smsService.sendSms(
							record.getUserId(),
							"尊敬的投资人，您于"
									+ new SimpleDateFormat(
											"yyyy-MM-dd HH:mm:ss")
											.format(record.getRequestTime())
									+ "发起提现"
									+ ArithUtil.round(record.getMoney(), 2)
									+ "元，提现处理失败，全部款项已退还您的帐户。", null);
				} catch (Exception e) {
					log.errLog("提现退款失败", e);
					throw e;
				} /*finally {
					DRJedisDLock
							.releaseDLock("withdraw" + requestNo, requestNo);
				}

			}
*/
			return "success";
		}
		return map.get("description");
	}

	/**
	 * 确认转账
	 * 
	 * @param loan
	 * @param invests
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@Override
	public String transferConfirm(String requestNo) throws Exception {
		TrusteeshipOperation to = trusteeshipOperationService.read("transfer",
				requestNo, requestNo, null);
		Map<String, String> map = trusteeshipTransferService.transfer(
				requestNo, "CONFIRM", to);
		String code = map.get("code");
		if (code.equals("1")) {
			to.setStatus("confirm");
			trusteeshipOperationService.update(to);
			PaymentWithdrawRecord record = read(to.getMarkId(), to.getMarkId(),
					"JDpay");

			record.setStatus("confirm");
			record.setConfirmResponseData(to.getResponseData());
			record.setResponseTime(new Date());
			paymentWithdrawRecordDao.update(record);
			return "success";

		}
		return map.get("description");
	}

	@Override
	public PaymentWithdrawRecord read(String id) {
		return paymentWithdrawRecordDao.get(id);
	}

	@Override
	public double readWithdrawMoneyPerDay(PaymentWithdrawRecord record) {
		return paymentWithdrawRecordDao.getWithdrawMoneyPerDay(record);
	}

	@Override
	public int readWithdrawCountPerDay(PaymentWithdrawRecord record) {
		return paymentWithdrawRecordDao.readWithdrawCuntPerDay(record);
	}

	@Override
	public List<PaymentWithdrawRecord> readWithdrawRecords(
			PaymentWithdrawRecord paymentWithdrawRecord) {
		return paymentWithdrawRecordDao
				.getWithdrawRecords(paymentWithdrawRecord);
	}

	@Override
	public List<PaymentWithdrawRecord> readRepeatRecords() {
		return paymentWithdrawRecordDao.getRepeatRecords();
	}

	@Override
	public PaymentWithdrawRecord getPaymentWithdrawRecord(String transNo) {
		return paymentWithdrawRecordDao.getPaymentWithdrawRecord(transNo);
	}
	
}
