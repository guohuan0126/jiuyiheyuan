package com.duanrong.drpay.business.payment.service.impl;

import base.exception.UserAccountException;

import com.duanrong.drpay.business.account.BankName;
import com.duanrong.drpay.business.account.PaymentEnum;
import com.duanrong.drpay.business.account.PlatformAccountEnum;
import com.duanrong.drpay.business.account.service.PaymentAccountService;
import com.duanrong.drpay.business.account.service.PlatformAccountService;
import com.duanrong.drpay.business.account.service.UserAccountService;
import com.duanrong.drpay.business.payment.BankMapUtil;
import com.duanrong.drpay.business.payment.RechargeWay;
import com.duanrong.drpay.business.payment.dao.PaymentBankChannelDao;
import com.duanrong.drpay.business.payment.dao.RechargeDao;
import com.duanrong.drpay.business.payment.model.PaymentChannel;
import com.duanrong.drpay.business.payment.model.Recharge;
import com.duanrong.drpay.business.payment.service.RechargeService;
import com.duanrong.drpay.business.user.UserConstants;
import com.duanrong.drpay.config.BusinessEnum;
import com.duanrong.drpay.trusteeship.helper.model.ExpectPayCompany;
import com.duanrong.drpay.trusteeship.helper.model.PlatformType;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.AESUtil;
import util.ArithUtil;
import util.Log;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class RechargeServiceImpl implements RechargeService {

	@Resource
	RechargeDao rechargeDao;
	
	@Resource
	PaymentBankChannelDao paymentBankChannelDao;

	@Resource
	UserAccountService userAccountService;

	@Resource
	PaymentAccountService paymentAccountService;

	@Resource
	PlatformAccountService platformAccountService;
	
	@Resource
	Log log;

	@Override
	public List<Recharge> getByCondition(Recharge recharge) {
		return rechargeDao.getByCondition(recharge);
	}

	@Override
	public void insert(Recharge recharge) {
		rechargeDao.insert(recharge);
	}

	@Override
	public Recharge get(String id) {
		return rechargeDao.get(id);
	}

	@Override
	public void rechargeSuccess(String rechargeId) throws UserAccountException {
		// 充值时Callback返回的没有充值类型
		rechargeSuccess(rechargeId, null);
	}
	org.apache.commons.logging.Log logger = LogFactory.getLog(RechargeServiceImpl.class);
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void rechargeSuccess(String rechargeId, String type) throws UserAccountException {
		Recharge recharge = rechargeDao.getWithLock(rechargeId);
		if (StringUtils.equals(recharge.getStatus(),
				UserConstants.RechargeStatus.WAIT_PAY)) {
			// 修改状态为成功
			recharge.setStatus(UserConstants.RechargeStatus.SUCCESS);
			// 设置充值成功时间
			recharge.setSuccessTime(new Date());
			String userId = recharge.getUserId();
			Double money = recharge.getActualMoney();
			String operatorInfo = "充值成功";
			double fee = 0.0;
			try {
				double rate = 0.0;
				PaymentChannel channel = paymentBankChannelDao
						.getChannelByCode(recharge.getPaymentId());
				if (StringUtils.equalsIgnoreCase(recharge.getType(), "quick")) {
					rate = channel.getRateQuick();
				} else {
					rate = channel.getRateGateway();
					Map<String, Object> cardInfoMap = BankMapUtil
							.findBankInfo(AESUtil.decode(recharge.getCardNo()));
					String cardName = (String) cardInfoMap.get("name");
					String bankCode = BankName.getBankCode(cardName);// 银行简称
					if (ExpectPayCompany.UMPAY.name().equals(recharge.getType())
							&& !"COMM".equals(bankCode)
							&& !"SZDB".equals(bankCode)) {
						// 0.12 % (工、农、中、民生、华夏) 0.13 % (建行、广发、中信、招行、 邮储)
						// 0.2% (其他银行，除交通和平安)
						if ("ICBK".equals(bankCode) || "ABOC".equals(bankCode)
								|| "BKCH".equals(bankCode)
								|| "HXBK".equals(bankCode)
								|| "MSBC".equals(bankCode)) {
							rate = 0.0012;
						} else if ("PCBC".equals(bankCode)
								|| "CMBC".equals(bankCode)
								|| "PSBC".equals(bankCode)
								|| "GDBK".equals(bankCode)
								|| "CIBK".equals(bankCode)) {
							rate = 0.0013;
						} else {
							rate = 0.002;
						}
					}
				}
				fee = ArithUtil.round(recharge.getActualMoney() * rate, 2);
				if (RechargeWay.quick.name().equals(recharge.getType())) {
					if (fee < channel.getQuickMinFee()) {
						fee = channel.getQuickMinFee();
					}
				}
				recharge.setFee(fee);
			} catch (Exception e) {
				e.printStackTrace();
				log.errLog("计算充值手续费失败", recharge.toString() + ",Excetpion:" + e);
			}
			rechargeDao.update(recharge);
			// xmbank记录充值手续费，每笔万分之4.5
			try {
				platformAccountService.transferOut(
						PlatformAccountEnum.PLATFORM_SYS, ArithUtil.round(recharge.getActualMoney() * 0.00045, 2),
						BusinessEnum.fee, "充值手续费", rechargeId);
			} catch (Exception e) {
				e.printStackTrace();
				log.errLog("转出充值手续费失败", "userId:"+userId+","+e);
			}
			// 充值渠道支付手续费
			if (recharge.getPaymentId() != null) {
				try {
					paymentAccountService.transferOut(
							PaymentEnum.valueOf(recharge.getPaymentId()), fee,
							BusinessEnum.fee, "充值手续费", rechargeId);
					System.out.println("****************充值通道完成："
							+ recharge.getPaymentId());
				} catch (Exception e) {
					e.printStackTrace();
					log.errLog("支付账户充值失败", "userId:"+userId+","+e);
				}
			}
			// 账户充值
			if (userId.equals(PlatformType.SYS_GENERATE_001.name())) {
				platformAccountService.transferIn(PlatformAccountEnum.PLATFORM_COMPENSATORY, money, BusinessEnum.recharge,
					operatorInfo, rechargeId);
			} else if (userId.equals(PlatformType.SYS_GENERATE_002.name())) {
				platformAccountService.transferIn(PlatformAccountEnum.PLATFORM_MARKETING, money, BusinessEnum.recharge,
					operatorInfo, rechargeId);
			} else {
				userAccountService.transferIn(userId, money, BusinessEnum.recharge,
						operatorInfo, operatorInfo, rechargeId);
			}
		}
	}

	@Override
	public void update(Recharge recharge) {
		rechargeDao.update(recharge);
	}
}