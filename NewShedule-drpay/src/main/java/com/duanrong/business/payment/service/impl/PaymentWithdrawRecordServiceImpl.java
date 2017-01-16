package com.duanrong.business.payment.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import util.Log;
import util.MyCollectionUtils;

import com.duanrong.business.account.BusinessEnum;
import com.duanrong.business.account.PaymentEnum;
import com.duanrong.business.account.service.PaymentAccountService;
import com.duanrong.business.account.service.PlatformAccountService;
import com.duanrong.business.account.service.UserAccountService;
import com.duanrong.business.payment.dao.PaymentWithdrawRecordDao;
import com.duanrong.business.payment.model.PaymentWithdrawRecord;
import com.duanrong.business.payment.service.PaymentWithdrawRecordService;
import com.duanrong.business.sms.SmsConstants;
import com.duanrong.business.sms.service.SmsService;
import com.duanrong.business.trusteeship.model.TrusteeshipConstants;
import com.duanrong.business.trusteeship.model.TrusteeshipOperation;
import com.duanrong.business.trusteeship.service.TrusteeshipOperationService;
import com.duanrong.business.withdraw.dao.WithdrawCashDao;
import com.duanrong.business.withdraw.model.WithdrawCash;
import com.duanrong.business.withdraw.service.WithdrawCashService;
import com.duanrong.payment.jd.defraypay.RequestUtil;
import com.duanrong.util.DateUtil;
import com.duanrong.util.Week;

@Service
public class PaymentWithdrawRecordServiceImpl implements
		PaymentWithdrawRecordService {

	@Resource
	PaymentWithdrawRecordDao paymentWithdrawRecordDao;

	@Resource
	WithdrawCashService withdrawCashService;

	@Resource
	WithdrawCashDao withdrawCashDao;

	@Resource
	TrusteeshipOperationService trusteeshipOperationService;

	@Resource
	SmsService smsService;

	@Resource
	UserAccountService userAccountService;

	@Resource
	PlatformAccountService platformAccountService;

	@Resource
	PaymentAccountService paymentAccountService;

	@Resource
	Log log;

	/**
	 * 如果markId和operator是唯一的，就将查询到的记录更新
	 */
	public void dealSameRecord(PaymentWithdrawRecord record) {
		List<PaymentWithdrawRecord> records = paymentWithdrawRecordDao
				.get(record.getMarkId(), record.getOperator(),
						record.getPaymentId());

		if (MyCollectionUtils.isNotBlank(records)) {
			Map<String, Object> params = new HashMap<>();
			params.put("operator", "recode：" + record.getId());
			params.put("status", "recode：" + record.getId());
			params.put("records", records);
			paymentWithdrawRecordDao.updateBatch(params);
		}
	}

	@Override
	public PaymentWithdrawRecord query(String markId, String operator,
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
	public List<PaymentWithdrawRecord> getUnCallbackOperations(int minute) {
		Date date = DateUtils.addMinutes(new Date(), -minute);
		PaymentWithdrawRecord paymentWithdrawRecord = new PaymentWithdrawRecord();
		paymentWithdrawRecord.setStatus(TrusteeshipConstants.Status.SENDED);
		paymentWithdrawRecord.setRequestTime(date);
		return paymentWithdrawRecordDao
				.getUnCallbackOperations(paymentWithdrawRecord);

	}

	@Override
	public List<PaymentWithdrawRecord> getYeeapyOperations(int minute) {
		Date date = DateUtils.addMinutes(new Date(), -minute);
		return paymentWithdrawRecordDao.getYeeapyOperations(date);

	}

	@Override
	public void transferLocal(TrusteeshipOperation to,
			PaymentWithdrawRecord record) {
		to.setStatus("confirm");
		trusteeshipOperationService.update(to);
		record.setStatus("confirm");
		record.setConfirmResponseData(to.getResponseData());
		record.setConfirmResponseTime(new Date());
		paymentWithdrawRecordDao.update(record);

	}

	@Override
	public void transferFrozeLocal(TrusteeshipOperation to,
			PaymentWithdrawRecord record) {
		to.setStatus("froze");
		trusteeshipOperationService.update(to);
		record.setStatus("frozen");
		record.setRequestData(to.getResponseData());
		record.setResponseTime(new Date());
		paymentWithdrawRecordDao.update(record);
		/*if (DRJedisDLock.getDLock("withdraw" + to.getMarkId(), to.getMarkId())) {
			try {*/
				WithdrawCash withdrawCash = withdrawCashService.queryWithLock(to
						.getMarkId());
				if (withdrawCash != null
						&& "wait_verify".equals(withdrawCash.getStatus())) {
					withdrawCash.setStatus("success");
					withdrawCashService.update(withdrawCash);
				}
				try {
					userAccountService.transferOut(record.getUserId(),
							record.getMoney(), BusinessEnum.withdraw_cash,
							"提现成功", "提现成功", record.getId());
					platformAccountService.transferOut(1.5,
							BusinessEnum.withdraw_fee, "提现手续费", record.getId());
					paymentAccountService.transferOut(PaymentEnum.JDpay,
							record.getMoney(), BusinessEnum.withdraw_cash,
							"提现成功", record.getId());
					paymentAccountService.transferOut(PaymentEnum.JDpay, 1.5,
							BusinessEnum.withdraw_fee, "提现手续费", record.getId());
				} catch (Exception e) {
					log.errLog("京东提现轮询异常", e);
				}
			/*} catch (Exception e) {
				log.errLog("京东提现轮询异常", e);
				e.printStackTrace();
			} finally {
				DRJedisDLock.releaseDLock("withdraw" + to.getMarkId(),
						to.getMarkId());
			}

		}*/
		Date date = getArrivalDate(new Date());
		String dateStr = DateUtil.DateToString(date, "M月d日");
		String content = "有一段时光，铭记于心，有一份祝福，如影随形。您已成功申请提现，提现金额#{money}元，预计#{date}到账！";
		content = StringUtils.replace(content, "#{money}",
				"" + record.getMoney());
		content = StringUtils.replace(content, "#{date}", dateStr);
		smsService.sendSms(record.getUserId(), content,
				SmsConstants.WITHDRAWCASH);

	}

	private Date getArrivalDate(Date date) {
		Date tomorrow = DateUtil.addDay(date, 2);// 明天
		Integer i = withdrawCashDao.getHolidayDate(tomorrow, "holiday");
		if (Week.SATURDAY != DateUtil.getWeek(tomorrow)
				&& Week.SUNDAY != DateUtil.getWeek(tomorrow) && i == 0) {
			return tomorrow;
		}
		// 法定上班日
		Integer j = withdrawCashDao.getHolidayDate(tomorrow, "work");
		if (j > 0) {
			return tomorrow;
		}
		return getArrivalDate(tomorrow);
	}

	@Override
	public List<PaymentWithdrawRecord> getDefraypayOperations(int minute) {
		Date date = DateUtils.addMinutes(new Date(), -minute);
		return paymentWithdrawRecordDao.getDefraypayOperations(date);
	}

	public Map<String, String> tradeQuery(String orderId) {
		Map<String, String> map = new HashMap<>();
		try {
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("customer_no", "360080004000838246");// 提交者会员号
			Date date = new Date();
			String dateTime = DateUtil.DateToString(date, "yyyyMMdd") + "T"
					+ DateUtil.DateToString(date, "HHmmss");
			paramMap.put("request_datetime", dateTime);// 请求时间
			paramMap.put("out_trade_no", orderId);// 商户订单号
			RequestUtil demoUtil = new RequestUtil();
			log.infoLog("京东代付", "单笔业务查询参数:" + paramMap.toString());
			String responseText = demoUtil.tradeRequestSSL(paramMap,
					"https://mapi.jdpay.com/npp10/trade_query", null);
			log.infoLog("京东代付", "单笔业务查询结果:" + responseText);
			map = demoUtil.verifySingReturnData(responseText);
		} catch (Exception e) {
			log.infoLog("京东代付", "单笔业务查询结果:" + e);
		}
		return map;
	}
}