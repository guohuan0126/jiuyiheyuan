package com.duanrong.business.recharge.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import util.Log;
import util.MyStringUtils;
import base.model.PageData;
import base.pagehelper.PageInfo;

import com.duanrong.business.account.BusinessEnum;
import com.duanrong.business.account.PaymentEnum;
import com.duanrong.business.account.service.PaymentAccountService;
import com.duanrong.business.account.service.PlatformAccountService;
import com.duanrong.business.account.service.UserAccountService;
import com.duanrong.business.payMentChannel.dao.PayMentChannelDao;
import com.duanrong.business.payMentChannel.model.PayMentChannel;
import com.duanrong.business.paymentInstitution.dao.PaymentCompanyDao;
import com.duanrong.business.recharge.dao.RechargeDao;
import com.duanrong.business.recharge.model.Recharge;
import com.duanrong.business.recharge.service.RechargeService;
import com.duanrong.business.risk.service.SystemBillService;
import com.duanrong.business.user.UserConstants;
import com.duanrong.business.user.service.UserMoneyService;
import com.duanrong.newadmin.utility.AESUtil;
import com.duanrong.util.ArithUtil;
import com.duanrong.util.jedis.DRJedisDLock;

@Service
public class RechargeServiceImpl implements RechargeService{

	@Resource
	RechargeDao rechargeDao;

	@Resource
	UserMoneyService userMoneyService;

	@Resource
	SystemBillService systemBillService;

	@Resource
	PaymentCompanyDao paymentCompanyDao;

	@Resource
	UserAccountService userAccountService;

	@Resource
	PaymentAccountService paymentAccountService;

	@Resource
	PlatformAccountService platformAccountService;

	@Resource
	Log log;
	
	@Resource
	PayMentChannelDao payMentChannelDao;

	@Override
	public List<Recharge> readByCondition(Recharge recharge) {
		return rechargeDao.getByCondition(recharge);
	}

	@Override
	public Double readTotalRecharge(Recharge recharge) {
		Double d = 0d;
		d = rechargeDao.getTotalRecharge(recharge);
		if (d != null)
			return d;
		else
			return (double) 0;
	}

	@Override
	public PageData<Recharge> readPaging(int pageNo, int pageSize,
			Recharge recharge) {
		return rechargeDao.findPaging(pageNo, pageSize, recharge);
	}

	@Override
	public Long getRechargeCount(Recharge recharge) {
		return rechargeDao.getCount(recharge);
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
	public void rechargeSuccess(String rechargeId) {
		if (DRJedisDLock.getDLock("recharge" + rechargeId, rechargeId)) {
			try {
				Recharge recharge = get(rechargeId);
				if (StringUtils.equals(recharge.getStatus(),
						UserConstants.RechargeStatus.WAIT_PAY)) {
					// 修改状态为成功
					recharge.setStatus(UserConstants.RechargeStatus.SUCCESS);
					// 设置充值成功时间
					recharge.setSuccessTime(new Date());
					
					String userId = recharge.getUserId();
					Double money = recharge.getActualMoney();
					String operatorInfo = "充值成功";
					// 往user_bill表中插入值并计算余额
					/*
					 * userMoneyService.transferIntoBalance(userId, money,
					 * operatorInfo, null);
					 */

					// 计算充值手续费
					double fee = 0;
					try {
						double rate = 0.0;
						PayMentChannel channel = payMentChannelDao.getChannelByCode(recharge.getPayMentId());
						if (MyStringUtils.equalsIgnoreCaseAnyString(
								recharge.getType(), "quick", null)) {
							rate = channel.getRateQuick();
						} else {
							rate = channel.getRateGateway();
						}
						fee = ArithUtil.round(recharge.getActualMoney() * rate,
								2);
						if(recharge.getPayMentId().equals(PaymentEnum.Baofoo.toString())&&recharge.getType().equals("quick")) fee = fee < 2 ? 2 : fee;
						recharge.setFee(fee);
						// 更新
						rechargeDao.update(recharge);
						
					} catch (Exception ex) {
						log.errLog("充值，系统账户操作", ex);
					}
					// FIXME 新账户充值
					userAccountService.transferIn(userId, money,
							BusinessEnum.recharge, operatorInfo, operatorInfo,
							rechargeId);
					
					// 记录充值手续费
					platformAccountService.transferOut(fee,
							BusinessEnum.fee, "充值手续费，userId："+userId+"，rechargeId："+rechargeId, rechargeId);

					
					
					// 非易宝充值，需要更新充值账户
					if (recharge.getPayMentId() != null) {
						try {
							switch (recharge.getPayMentId()) {
							case "Baofoo":
								paymentAccountService.transferIn(
										PaymentEnum.Baofoo, money,
										BusinessEnum.recharge, "充值成功",
										rechargeId);
								paymentAccountService.transferOut(
										PaymentEnum.Baofoo, fee,
										BusinessEnum.fee, "充值手续费",
										rechargeId);
								break;
							case "Fuiou":
								paymentAccountService.transferIn(
										PaymentEnum.Fuiou, money,
										BusinessEnum.recharge, "充值成功",
										rechargeId);
								paymentAccountService.transferOut(
										PaymentEnum.Fuiou, fee,
										BusinessEnum.fee, "充值手续费",
										rechargeId);
								break;
							case "JDpay":
								paymentAccountService.transferIn(
										PaymentEnum.JDpay, money,
										BusinessEnum.recharge, "充值成功",
										rechargeId);
								paymentAccountService.transferOut(
										PaymentEnum.JDpay, fee,
										BusinessEnum.fee, "充值手续费",
										rechargeId);
								break;
							}
						} catch (Exception e) {
							e.printStackTrace();
							log.errLog("支付账户充值失败", e);
						}
					}
				}
			} catch (Exception e) {
				log.errLog("处理充值成功", e);
			} finally {
				DRJedisDLock.releaseDLock("recharge" + rechargeId, rechargeId);
			}
		}

	}
public static void main(String[] args) {
	
double fee = ArithUtil.round(4000 * 0.0012,
			2);
	if("Baofoo".equals(PaymentEnum.Baofoo.toString())&&"quick".equals("quick")) fee = fee < 2 ? 2 : fee;
//	recharge.setFee(fee);
	System.out.println(fee);
}
	@Override
	public void update(Recharge recharge) {
		rechargeDao.update(recharge);
	}

	@Override
	public PageInfo<Recharge> readAllRecharge(int pageNo, int pageSize,
			Recharge recharge) {
		PageInfo<Recharge> pageInfo = rechargeDao.pageLite(pageNo, pageSize,
				recharge);
		List<Recharge> resultList = pageInfo.getResults();
		for (Recharge recharge2 : resultList) {
			String cardNo = "";
			try {
				cardNo = AESUtil.decode(recharge2.getCardNo());
				recharge2.setCardNo(cardNo.substring(0, 4) + "********"
						+ cardNo.substring(cardNo.length() - 4));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		pageInfo.setResults(resultList);
		return pageInfo;
	}

	@Override
	public Double readTotalFee(Recharge recharge) {
		Double d = 0d;
		d = rechargeDao.getTotalFee(recharge);
		if (d != null)
			return d;
		else
			return (double) 0;
	}

	@Override
	public double readRechargeMoneyPerDay(Map<String, Object> params) {
		return rechargeDao.getRechargeMoneyPerDay(params);
	}

	@Override
	public List<Recharge> readRechargeNum(Map map) {
		return rechargeDao.getRechargeNum(map);
	}

	@Override
	public void save(Recharge recharge) {
		rechargeDao.save(recharge);
	}

	/**
	 * 【去除固定借款人】总充值金额
	 */
	@Override
	public BigDecimal readExcludeFixedBorrowerFee(Recharge recharge) {
		return rechargeDao.getExcludeFixedBorrowerFee(recharge);
	}


	@Override
	public int readRechargeSuccessPeople(Recharge recharge) {
		return rechargeDao.getRechargeSuccessPeople(recharge);
	}

	@Override
	public BigDecimal readExcludeFixedBorrowerFeeFail(Recharge recharge) {
		return rechargeDao.getExcludeFixedBorrowerFeeFail(recharge);
	}

	@Override
	public int readRechargeFailPeople(Recharge recharge) {
		return rechargeDao.getRechargeFailPeople(recharge);
	}

}