package com.duanrong.drpay.trusteeship.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.ArithUtil;
import util.DateUtil;
import util.Log;
import util.SmsHttpClient;
import base.error.ErrorCode;
import base.exception.DataAlreadExistException;
import base.exception.DemandException;
import base.exception.PlatformAccountException;
import base.exception.TradeException;
import base.exception.UserAccountException;
import base.exception.UserInfoException;

import com.duanrong.drpay.business.account.PlatformAccountEnum;
import com.duanrong.drpay.business.account.model.PlatformAccount;
import com.duanrong.drpay.business.account.service.PlatformAccountService;
import com.duanrong.drpay.business.demand.DemandtreasureConstants;
import com.duanrong.drpay.business.demand.DemandtreasureConstants.DemandBillStatus;
import com.duanrong.drpay.business.demand.dao.DemandTreasureBillDao;
import com.duanrong.drpay.business.demand.dao.DemandtreasureGuOutDao;
import com.duanrong.drpay.business.demand.dao.DemandtreasureGuOutDetailDao;
import com.duanrong.drpay.business.demand.dao.DemandtreasureTransferOutDao;
import com.duanrong.drpay.business.demand.model.DemandTreasureBill;
import com.duanrong.drpay.business.demand.model.Demandtreasure;
import com.duanrong.drpay.business.demand.model.DemandtreasureGuOut;
import com.duanrong.drpay.business.demand.model.DemandtreasureGuOutDetail;
import com.duanrong.drpay.business.demand.model.DemandtreasureTransferOut;
import com.duanrong.drpay.business.demand.service.DemandTreasureBillService;
import com.duanrong.drpay.business.demand.service.DemandtreasureService;
import com.duanrong.drpay.business.demand.service.DemandtreasureTransferOutService;
import com.duanrong.drpay.business.user.model.User;
import com.duanrong.drpay.business.user.service.UserService;
import com.duanrong.drpay.config.BusinessEnum;
import com.duanrong.drpay.config.IdUtil;
import com.duanrong.drpay.config.ToType;
import com.duanrong.drpay.trusteeship.helper.model.PlatformType;
import com.duanrong.drpay.trusteeship.service.TrusteeshipDemandService;
import com.duanrong.drpay.trusteeship.service.TrusteeshipFundTransferService;
import com.duanrong.util.jedis.DRJedisDLock;
@Service
public class TrusteeshipDemandServiceImpl implements  TrusteeshipDemandService{

	@Resource
	DemandtreasureService demandtreasureService;
	
	@Resource
	DemandtreasureTransferOutService demandtreasureTransferOutService;
	
	@Resource
	TrusteeshipFundTransferService trusteeshipFundTransferService;
	
	@Resource
	DemandtreasureTransferOutDao demandtreasureTransferOutDao;

	@Resource
	DemandtreasureGuOutDao demandtreasureGuOutDao;

	@Resource
	DemandTreasureBillService demandTreasureBillService;

	@Resource
	DemandtreasureGuOutDetailDao demandtreasureGuOutDetailDao;

	@Resource
	DemandTreasureBillDao demandTreasureBillDao;
	
	@Resource
	SmsHttpClient smsHttpClient;
	
	@Resource
	UserService userService;
	
	@Resource
	PlatformAccountService platformAccountService;
	
	@Resource
	Log log;

	private static final String DEMAND_TRANSFEROUT_APPLY = "demandTransferOutApply";
	private static final String DEMAND_OUT = "demandOut";
	
	@Override
	public void demandTransferOutCheck() throws TradeException {
		demandtreasureTransferOutService.enhanceValidateTransferOut();
	}

	@Override
	public void demandTransferOutApply() throws TradeException,
			UserAccountException, PlatformAccountException,
			DataAlreadExistException {
		if (DRJedisDLock.getDLock(DEMAND_TRANSFEROUT_APPLY, "demand", 180)) {
			try {
				
				PlatformAccount platformAccount = platformAccountService.getPlatformAccount(PlatformAccountEnum.PLATFORM_MARKETING);
				// 计算demand_treasure_transferout表中状态为sended的用户总数和总金额
				DemandtreasureTransferOut transferOutTotal = demandtreasureTransferOutDao
						.getTotal();
				if (transferOutTotal == null) {
					log.infoLog("活期宝转出申请", "没有可转出的用户");
					throw new TradeException(ErrorCode.DemandTransferOutNoUser);
				}
				// 营销账户可用余额与待转出总金额比较
				if (platformAccount.getAvailableBalance() < transferOutTotal.getSummoney()) {
					log.errLog("活期宝转出申请", "营销账户可用余额不足", 1);
					throw new TradeException(ErrorCode.PlatformBalanceToLow);
				}
				// 计算demand_treasure_GuOut表中状态为sended的数据个数
				double num = demandtreasureGuOutDao.getGuCount();
				if (num > 0) {
					log.errLog("活期宝转出申请", "存在未处理完成的转出");
					throw new TradeException(ErrorCode.DemandTransferOutNotComplete);
				}
				// 计算demand_treasure_transferout表中状态为sended的每个userId的转出总金额
				List<DemandtreasureTransferOut> transferOutUsers = demandtreasureTransferOutDao
						.getTotalUser();
				for (DemandtreasureTransferOut transferOutUser : transferOutUsers) {
					// 获取用户活期宝总金额（总转入-总转出+总收益-转出收益）
					double money = demandTreasureBillService
							.getDemandTreasureMoney(transferOutUser.getUserId());
					if (transferOutUser.getSummoney() > money) {
						log.errLog("活期宝转出申请",
								"userId:" + transferOutUser.getUserId()
										+ "转出金额大于其活期宝金额");
						throw new TradeException(ErrorCode.DemandBalanceToLow);
					}
				}
				String requestNo = IdUtil.generateId(ToType.RCUT);
				// 查询demand_treasure_transferout表状态为sended的所有数据
				List<DemandtreasureTransferOut> list = demandtreasureTransferOutDao
						.findAll();
				if (list != null && list.size() != 0) {
					List<DemandtreasureGuOutDetail> details = new ArrayList<DemandtreasureGuOutDetail>();
					double totalMoney = 0D;
					for (DemandtreasureTransferOut transferOut : list) {
						// 将transferOut记录插入detail
						DemandtreasureGuOutDetail detail = new DemandtreasureGuOutDetail();
						String id = IdUtil.randomUUID();
						detail.setSendedTime(new Date());
						detail.setId(id);
						detail.setGuOutId(requestNo);
						detail.setTransferOutId(transferOut.getId());
						detail.setStatus(DemandtreasureConstants.TransferInStatus.SENDED);
						detail.setMoney(transferOut.getMoney());
						detail.setUserId(transferOut.getUserId());
						detail.setInterest(transferOut.getInterest());
						detail.setPrincipal(transferOut.getPrincipal());
						details.add(detail);
						totalMoney += transferOut.getMoney();
					}
					// 插入一条GuOut总记录
					DemandtreasureGuOut guOut = new DemandtreasureGuOut();
					guOut.setId(requestNo);
					guOut.setMoney(ArithUtil.round(totalMoney, 2));
					guOut.setRequestXml("");
					guOut.setSendedTime(new Date());
					guOut.setUserId(PlatformType.SYS_GENERATE_002.name());
					guOut.setStatus(DemandtreasureConstants.TransferInStatus.SENDED);
					demandtreasureGuOutDao.insert(guOut);
					demandtreasureGuOutDetailDao.insertBatch(details);
					// 在确认处理中针对每一条赎回记录，冻结一次，确认一次。
				} else {
					log.infoLog("活期宝转出申请", "没有可转出的用户");
					throw new TradeException(ErrorCode.DemandTransferOutNoUser);
				}
			} finally {
				DRJedisDLock.releaseDLock(DEMAND_TRANSFEROUT_APPLY, "demand");
			}
		}
	}
	
	@Override
	public void demandTransferOutConfirm(String guOutId) throws TradeException {
		//获取guOut记录
		DemandtreasureGuOut guOut = demandtreasureGuOutDao.get(guOutId);
		//guOut状态为sended
		if (guOut != null && guOut.getStatus().equals("sended")) {
			DemandtreasureGuOutDetail detail = new DemandtreasureGuOutDetail();
			detail.setGuOutId(guOutId);
			//查询guOut对应的所有sended或freeze状态的detail记录
			List<DemandtreasureGuOutDetail> deList = demandtreasureGuOutDetailDao
					.findGuOutDetails(detail);
			for (DemandtreasureGuOutDetail guOutDetail : deList) {
				try {
					transferOutLocal(guOutDetail);
				} catch (Exception e) {
					log.errLog("活期宝转出异常", "guOutDetail:" + guOutDetail.getTransferOutId() ,1);
				}
			}
			List<DemandtreasureGuOutDetail> deListAll = demandtreasureGuOutDetailDao
					.findGuOutDetails(detail);
			if (deListAll == null || deListAll.size() == 0) {
				DemandtreasureGuOut out = new DemandtreasureGuOut();
				out.setStatus(DemandtreasureConstants.TransferInStatus.CONFIRM);
				out.setId(guOutId);
				out.setConfirmTime(new Date());
				demandtreasureGuOutDao.update(out);
			}
		} else {
			log.errLog("活期宝转出确认", "guOutId:" + guOutId + "GuOut记录状态不为sended");
			throw new TradeException(ErrorCode.DemandTransferOutGuOutNotSended);
		}
	}

	@Transactional
	private void transferOutLocal(DemandtreasureGuOutDetail guOutDetail) throws Exception {
		//判断detail记录是否为sended或freeze
		if ("sended".equals(guOutDetail.getStatus()) || "freeze".equals(guOutDetail.getStatus())) {
			String result = trusteeshipFundTransferService.sendRewardDirect(guOutDetail.getUserId(), 
					guOutDetail.getTransferOutId(), guOutDetail.getMoney(), BusinessEnum.demand_out, "", 
					"活期宝赎回到账", "userId：" + guOutDetail.getUserId() + "，转出ID"+guOutDetail.getTransferOutId()
					+"预处理流水号：PRET"+guOutDetail.getTransferOutId());
			if(result.equals("success"))
			{
				DemandtreasureGuOutDetail dg = new DemandtreasureGuOutDetail();
				dg.setId(guOutDetail.getId());
				dg.setConfirmTime(new Date());
				dg.setStatus(DemandtreasureConstants.TransferInStatus.CONFIRM);
				demandtreasureGuOutDetailDao.update(dg);
				DemandtreasureTransferOut tOut = demandtreasureTransferOutDao
						.get(guOutDetail.getTransferOutId());
				if (guOutDetail.getInterest() != null
						&& guOutDetail.getInterest() > 0) {
					DemandTreasureBill bill = new DemandTreasureBill();
					bill.setCreateTime(new Date());
					bill.setDetail("用户：" + guOutDetail.getUserId() + "天天赚转让利息成功!");
					bill.setId(UUID.randomUUID().toString().replaceAll("-", ""));
					bill.setMoney(guOutDetail.getInterest());
					bill.setType(DemandBillStatus.OUTINTEREST);
					bill.setTranferWay(tOut.getTransferWay());
					bill.setUserId(guOutDetail.getUserId());
					demandTreasureBillDao.insert(bill);
				}
				if (guOutDetail.getPrincipal() != null
						&& guOutDetail.getPrincipal() > 0) {
					DemandTreasureBill bill = new DemandTreasureBill();
					bill.setCreateTime(new Date());
					bill.setDetail("用户：" + guOutDetail.getUserId() + "天天赚转让本金成功!");
					bill.setId(UUID.randomUUID().toString().replaceAll("-", ""));
					bill.setMoney(guOutDetail.getPrincipal());
					bill.setType(DemandBillStatus.TRANOUT);
					bill.setTranferWay(tOut.getTransferWay());
					bill.setUserId(guOutDetail.getUserId());
					demandTreasureBillDao.insert(bill);
				}
				tOut.setStatus(DemandBillStatus.SUCCESS);
				tOut.setSuccessTime(new Date());
				demandtreasureTransferOutDao.update(tOut);
				log.infoLog("活期宝转出确认", "userId:" + guOutDetail.getUserId()
						+ "活期宝转出成功");
				// 发短信
				try {	
					User user = userService.get(guOutDetail.getUserId());
					String type = "demand_out";
					if (tOut.getTransferWay() != null
							&& tOut.getTransferWay().equals("expires")) {
						type = "demand_out_expires";
					}
					smsHttpClient.sendSms(user.getMobileNumber(), ""+ArithUtil.round(guOutDetail.getMoney(), 2), type);
				} catch (Exception e) {
					log.errLog("活期宝赎回短信发送异常", e.getMessage());
				}
			} else if(result.equals("fail")) {
				//修改状态为失败
				DemandtreasureGuOutDetail dg = new DemandtreasureGuOutDetail();
				dg.setId(guOutDetail.getId());
				dg.setConfirmTime(new Date());
				dg.setStatus(DemandtreasureConstants.TransferInStatus.FAIL);
				demandtreasureGuOutDetailDao.update(dg);
				//生成新流水号的转出记录
				DemandtreasureGuOutDetail detail = new DemandtreasureGuOutDetail();
				String id = IdUtil.randomUUID();
				detail.setSendedTime(guOutDetail.getSendedTime());
				detail.setId(id);
				detail.setGuOutId(guOutDetail.getGuOutId());
				detail.setTransferOutId(guOutDetail.getTransferOutId());
				detail.setStatus(DemandtreasureConstants.TransferInStatus.SENDED);
				detail.setMoney(guOutDetail.getMoney());
				detail.setUserId(guOutDetail.getUserId());
				detail.setInterest(guOutDetail.getInterest());
				detail.setPrincipal(guOutDetail.getPrincipal());
				demandtreasureGuOutDetailDao.insert(detail);
			} else if(result.equals("sended")) {
				DemandtreasureGuOutDetail dg = new DemandtreasureGuOutDetail();
				dg.setId(guOutDetail.getId());
				dg.setConfirmTime(new Date());
				dg.setStatus(DemandtreasureConstants.TransferInStatus.FREEZE);
				demandtreasureGuOutDetailDao.update(dg);
				log.errLog("活期宝转出确认", "userId:" + guOutDetail.getUserId()
						+ "活期宝转出状态为sended");
			}
		} else {
			log.errLog("活期宝转出确认",
					"活期宝单个用户转出状态异常，userId：" + guOutDetail.getUserId()
							+ ",status:" + guOutDetail.getStatus());
		}
	}
	
	@Override
	public void demandOut(String userId, Double money) throws UserInfoException {
		if (DRJedisDLock.getDLock(DEMAND_OUT, userId, 180)) {
			try {
				//  这个锁没作用，应该加for update锁
				// 转出金额限制
				if (money <= 0 || 2 < ArithUtil.getDecimals(money)) {
					log.errLog("活期宝转出demandOut", "转出金额不能低于0元或大于两位小数，OUT Money:"
							+ money);
					throw new UserInfoException(ErrorCode.DemandTransferOutOutMoney);
				}
				// 获取用户当天请求次数
				Integer countToday = demandtreasureTransferOutService
						.getValidOutCount(userId);
				// 获取活期宝基本表
				Demandtreasure demandtreasure = demandtreasureService
						.queryAll().get(0);
				if (countToday >= demandtreasure.getOutNumber()) {
					log.errLog("活期宝转出demandOut", "转出次数超过限制,userId" + userId);
					throw new UserInfoException(ErrorCode.DemandTransferOutTooManyTimes);
				}
				// 判断当日转出限额
				double validOutMoney = demandtreasureTransferOutService
						.getValidOutSumMoney(userId);
				if (demandtreasure.getOutMaxMoney() != null
						&& demandtreasure.getOutMaxMoney() > 0
						&& money + validOutMoney > demandtreasure
								.getOutMaxMoney()) {
					log.errLog("活期宝转出demandOut", "转出金额大于今日转出上限,userId" + userId
							+ ", 今日已转出金额：" + validOutMoney);
					throw new UserInfoException(ErrorCode.DemandTransferOutOutMaxMoney);
				}
				// 判断转出金额是否大于用户总资产
				double balance = demandTreasureBillService
						.getDemandTreasureMoney(userId);
				// 正在转出的
				double sended = demandtreasureTransferOutService
						.getOutMoneySumByStatus(
								userId,
								DemandtreasureConstants.TransferOutStatus.SENDED);

				if (money > ArithUtil.round((balance - sended), 2)) {
					log.errLog("活期宝转出demandOut", "转出金额不能大于用户总资产,总资产：" + balance
							+ ",刚申请的：" + money);
					throw new UserInfoException(ErrorCode.DemandBalanceToLow);
				}
				// 用户可用利息=派息-转出利息-正在转出的利息
				double interest = demandTreasureBillService
						.getDemandAvlidInterestMoney(userId)
						- demandtreasureTransferOutService
								.getOutInterestSumByStatus(
										userId,
										DemandtreasureConstants.TransferOutStatus.SENDED);
				if (interest < 0)
					interest = 0.0;
				// 计算用户转出的本金、利息
				String id = IdUtil.randomUUID();
				DemandtreasureTransferOut demandOut = new DemandtreasureTransferOut();
				demandOut.setUserId(userId);
				demandOut.setMoney(money);
				demandOut.setId(id);
				demandOut.setStatus(DemandtreasureConstants.TransferOutStatus.SENDED);
				demandOut.setSendedTime(new Date());
				demandOut.setInterest(ArithUtil.round(interest, 2));
				demandOut.setPrincipal(ArithUtil.round(money, 2));
				demandOut.setMoney(ArithUtil.round(interest + money, 2));// 转让金额+可提利息
				demandtreasureTransferOutService.insert(demandOut);
			} finally {
				DRJedisDLock.releaseDLock(DEMAND_OUT, userId);
			}
		}
	}

	@Override
	public String demandOutMessage(String userId, Double money)
			throws UserInfoException {
		// 转出金额限制
		if (money <= 0 || 2 < ArithUtil.getDecimals(money)) {
			log.errLog("活期宝转出demandOut", "转出金额不能低于0元或大于两位小数，OUT Money:" + money);
			throw new UserInfoException(ErrorCode.DemandTransferOutOutMoney);
		}
		// 获取用户当天请求次数
		Integer countToday = demandtreasureTransferOutService
				.getValidOutCount(userId);
		// 获取活期宝基本表
		Demandtreasure demandtreasure = demandtreasureService.queryAll().get(0);
		if (countToday >= demandtreasure.getOutNumber()) {
			log.errLog("活期宝转出demandOut", "转出次数超过限制,userId" + userId);
			throw new UserInfoException(ErrorCode.DemandTransferOutTooManyTimes);
		}
		Date date = new Date();
		if (DateUtil.getHour(date) < demandtreasure.getStartTime()
				|| DateUtil.getHour(date) >= demandtreasure.getEndTime()) {
			log.errLog(
					"活期宝转出demandOut",
					"提现时间不在指定范围内,用户ID："
							+ userId
							+ ",actualMoney:"
							+ money
							+ ",时间:"
							+ DateUtil
									.DateToString(date, "yyyy-MM-dd HH:mm:ss"));
			throw new UserInfoException(ErrorCode.DemandTransferOutNotInTime);
		}
		// 判断当日转出限额
		double validOutMoney = demandtreasureTransferOutService
				.getValidOutSumMoney(userId);
		if (demandtreasure.getOutMaxMoney() != null
				&& demandtreasure.getOutMaxMoney() > 0
				&& money + validOutMoney > demandtreasure.getOutMaxMoney()) {
			log.errLog("活期宝转出demandOut", "转出金额大于今日转出上限,userId" + userId
					+ ", 今日已转出金额：" + validOutMoney);
			throw new UserInfoException(ErrorCode.DemandTransferOutOutMaxMoney);
		}
		// 判断转出金额是否大于用户总资产
		double balance = demandTreasureBillService
				.getDemandTreasureMoney(userId);
		// 正在转出的
		double sended = demandtreasureTransferOutService
				.getOutMoneySumByStatus(userId,
						DemandtreasureConstants.TransferOutStatus.SENDED);

		if (money > ArithUtil.round((balance - sended), 2)) {
			log.errLog("活期宝转出demandOut", "转出金额不能大于用户总资产,总资产：" + balance
					+ ",刚申请的：" + money);
			throw new UserInfoException(ErrorCode.DemandBalanceToLow);
		}
		// 用户可用利息=派息-转出利息-正在转出的利息
		double interest = demandTreasureBillService
				.getDemandAvlidInterestMoney(userId)
				- demandtreasureTransferOutService.getOutInterestSumByStatus(
						userId,
						DemandtreasureConstants.TransferOutStatus.SENDED);
		if (interest < 0)
			interest = 0.0;
		DecimalFormat df = new DecimalFormat("##0.##");
		return "您本次转让本金￥" + df.format(money) + "，计提收益￥" + df.format(interest)
				+ "，系统将为您发起债权转让，转让成功后预计到账总额共计￥" + df.format(money + interest)
				+ "。";
	}

	@Override
	public void demandOutCancel(String guOutId) throws DemandException {
		List<DemandtreasureGuOutDetail> deList = demandtreasureGuOutDetailDao
				.getDemandtreasureGuOutDetails(guOutId);
		for (DemandtreasureGuOutDetail guOutDetail : deList) {
			if (!"sended".equals(guOutDetail.getStatus())) {
				log.errLog("活期宝转出撤销", "userId:"+guOutDetail.getUserId()+"已进行转出操作，不能撤销");
				throw new DemandException(ErrorCode.DemandTransferOutGuOutNotSended);
			}
		}
		DemandtreasureGuOut gu = new DemandtreasureGuOut();
		gu.setId(guOutId);
		gu.setStatus("revoke");
		demandtreasureGuOutDao.update(gu);
	}

}
