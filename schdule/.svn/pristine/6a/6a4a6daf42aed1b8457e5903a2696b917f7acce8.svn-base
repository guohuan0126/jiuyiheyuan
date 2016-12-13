package com.duanrong.business.withdraw.service.impl;

import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.Log;
import com.duanrong.business.account.BusinessEnum;
import com.duanrong.business.account.service.PaymentAccountService;
import com.duanrong.business.account.service.PlatformAccountService;
import com.duanrong.business.account.service.UserAccountService;
import com.duanrong.business.sms.SmsConstants;
import com.duanrong.business.sms.service.SmsService;
import com.duanrong.business.user.UserConstants;
import com.duanrong.business.withdraw.dao.WithdrawCashDao;
import com.duanrong.business.withdraw.model.WithdrawCash;

/**
 * @Description: 提现（易宝）
 * @Author: 林志明
 * @CreateDate: Oct 9, 2014
 */
@Service
public class WithdrawCashBO {

	@Resource
	WithdrawCashDao withdrawCashDao;


	@Resource
	Log log;
	
	@Resource
	SmsService smsService;
	
	@Resource
	UserAccountService userAccountService;

	@Resource
	PlatformAccountService platformAccountService;

	@Resource
	PaymentAccountService paymentAccountService;
	
	
	@Transactional(rollbackFor = Exception.class)
	public void passWithdrawCashApply(String withdrawCashId) throws Exception {
/*		if (DRJedisDLock.getDLock("withdraw" + withdrawCashId, withdrawCashId)) {*/
			try {
				WithdrawCash withdrawCash = withdrawCashDao.getWithLock(withdrawCashId);
				if (StringUtils.equals(withdrawCash.getStatus(),
						UserConstants.WithdrawStatus.WAIT_VERIFY)) {
					// 从余额中取，系统账户也要记录
					withdrawCash.setStatus(UserConstants.WithdrawStatus.SUCCESS);
					// 更新withdraw_cash表，放在user_bill插入操作之后，以免出现事物不同步的问题
					withdrawCashDao.update(withdrawCash);

					String userId = withdrawCash.getUserId();
					Double actualMoney = withdrawCash.getActualMoney();
					// FIXME 新账户提现
					userAccountService.transferOut(userId, actualMoney,
							BusinessEnum.withdraw_cash, "提现成功", "提现成功",
							withdrawCashId);
					// 易宝提现
					// 加急提现
					if ("URGENT".equals(withdrawCash.getWithdrawType())) {
						userAccountService.transferOut(userId,
								withdrawCash.getFee(),
								BusinessEnum.withdraw_fee, "加急提现手续费",
								"提现ID:" + withdrawCashId,
								withdrawCashId);
					} else {
						platformAccountService.transferOut(2,
								BusinessEnum.withdraw_fee, "提现手续费",
								withdrawCashId);
					}

					try {
						if("URGENT".equals(withdrawCash.getWithdrawType())){
							String content = smsService
									.operateTemplate(SmsConstants.WITHDRAWCASHURGENT);
							content = StringUtils.replace(content,
									"#{money}", actualMoney.toString());
							content = StringUtils.replace(content,
									"#{fee}", withdrawCash.getFee().toString());
							smsService.sendSms(userId, content,
									SmsConstants.WITHDRAWCASHURGENT);
						}else{
							String content = smsService
									.operateTemplate(SmsConstants.WITHDRAWCASH);
							content = StringUtils.replace(content,
									"#{money}", actualMoney.toString());
							smsService.sendSms(userId, content,
									SmsConstants.WITHDRAWCASH);
						}										
					} catch (Exception e) {
						e.printStackTrace();
						log.errLog("发送短信或跟投", e);
					}
				}
			} catch (Exception ex) {
				log.errLog("处理提现失败", ex);
				throw ex;
			} /*finally {
				DRJedisDLock.releaseDLock("withdraw" + withdrawCashId,
						withdrawCashId);
			}
		}*/
	}

}
