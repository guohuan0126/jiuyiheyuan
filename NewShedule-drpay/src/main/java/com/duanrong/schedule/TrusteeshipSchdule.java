package com.duanrong.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import util.HttpUtil;
import util.Log;

import com.alibaba.fastjson.JSONObject;
import com.duanrong.business.account.BusinessEnum;
import com.duanrong.business.account.service.UserAccountService;
import com.duanrong.business.recharge.model.Recharge;
import com.duanrong.business.recharge.service.RechargeService;
import com.duanrong.business.trusteeship.model.TrusteeshipOperation;
import com.duanrong.business.trusteeship.service.TrusteeshipOperationService;
import com.duanrong.business.withdraw.model.WithdrawCash;
import com.duanrong.business.withdraw.service.WithdrawCashService;

@Component
public class TrusteeshipSchdule {

	@Resource
	TrusteeshipOperationService trusteeshipOperationService;

	@Resource
	RechargeService rechargeService;

	@Resource
	WithdrawCashService withdrawCashService;
	
	@Resource
	UserAccountService userAccountService;
	
	@Resource
	Log log;


	@Scheduled(cron="0 0/5 * * * ?")
	public void handleSendedOperations() {
		// 查找请求表里面，等待返回的数据，且请求时间在十分钟以外。
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.infoLog("Trusteeship-轮询开始", sdf.format(new Date()));
		List<TrusteeshipOperation> tos = trusteeshipOperationService
				.getUnCallbackOperations(10);
		if (CollectionUtils.isEmpty(tos)) {
			log.infoLog("Trusteeship-轮询结束", "没有可以轮询的数据");
			return;
		}
		for (TrusteeshipOperation to : tos) {
			try {
				if ("create_account".equals(to.getType())) {
					String url = "/account/query.do";
					JSONObject param = new JSONObject();
					param.put("userId",to.getUserId());
					param.put("type",to.getType());
					param.put("handle",1);
					JSONObject result = HttpUtil.sendDRPayPost(param, url);
					if(result != null && result.getInteger("code") == 1){
						JSONObject data = result.getJSONObject("data");
						JSONObject respData = data.getJSONObject("respData");
						if(respData != null && respData.getInteger("code") == 0){
							to.setStatus("PASSED");
						}else{
							to.setStatus("REFUSED");
						}
						to.setResponseTime(new Date());
						to.setResponseData(respData.toJSONString());
						trusteeshipOperationService.update(to);
					}else{
						log.errLog("Trusteeship-轮询异常", "调用drpay服务异常"+result.getString("msg"));
					}
				} else {
					String url = "/trade/query.do";
					JSONObject param = new JSONObject();
					param.put("requestNo",to.getMarkId());
					param.put("type",to.getType());
					param.put("handle",1);
					JSONObject result = HttpUtil.sendDRPayPost(param, url);
					if(result != null && result.getInteger("code") == 1){
						JSONObject data = result.getJSONObject("data");
						JSONObject respData = data.getJSONObject("respData");
						if(respData != null && respData.getInteger("code") == 0){
							to.setStatus("PASSED");
						}else{
							to.setStatus("REFUSED");
						}
						to.setResponseTime(new Date());
						to.setResponseData(respData.toJSONString());
						trusteeshipOperationService.update(to);
					}else{
						log.errLog("Trusteeship-轮询异常", "调用drpay服务异常"+result.getString("msg"));
					}
				}
			} catch (Exception e) {
				log.errLog("Trusteeship-轮询异常", e);
			}
		}
		log.infoLog("Trusteeship-轮询结束", sdf.format(new Date()));
	}
	
	@Scheduled(cron="0 0/10 * * * ?")
	public void rechargeSchedule() {
		// 轮训recharge表，轮训间隔10分钟，查询10分之前，2小时之内的数据，查询状态为wait_pay的充值记录
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.infoLog("rechargeSchedule-轮询开始", sdf.format(new Date()));
		Map<String, Object> map = new HashMap<>();
		map.put("preDate", DateUtils.addMinutes(new Date(), -120));
		map.put("nextDate", DateUtils.addMinutes(new Date(), -10));
		map.put("status", "wait_pay");
		List<Recharge> recharges = rechargeService.getRechargeByDateAndStatus(map);
		if (CollectionUtils.isEmpty(recharges)) {
			log.infoLog("rechargeSchedule-轮询结束", "没有可以轮询的数据");
			return;
		}
		for (Recharge recharge : recharges) {
			try {
				String url = "/trade/query.do";
				JSONObject param = new JSONObject();
				param.put("requestNo", recharge.getId());
				param.put("type", BusinessEnum.recharge);
				param.put("handle", 1);
				JSONObject result = HttpUtil.sendDRPayPost(param, url);
				if (result != null && result.getInteger("code") == 1) {
					JSONObject data = result.getJSONObject("data");
					JSONObject respData = null;
					if (data != null) {
						respData = data.getJSONObject("respData");
					}
					if (respData != null ) {
						log.infoLog("rechargeSchedule-轮询", "充值流水号："+recharge.getId()+"单笔查询结果为："+respData);
					} else {
						log.errLog("rechargeSchedule-轮询异常", "drpay单笔业务查询respData为空");
					}
				} else {
					log.errLog("rechargeSchedule-轮询异常",
							"调用drpay服务异常" + result.getString("msg"));
				}
			} catch (Exception e) {
				log.errLog("rechargeSchedule-轮询异常", e);
			}
		}
		log.infoLog("rechargeSchedule-轮询结束", sdf.format(new Date()));
	}
	
	@Scheduled(cron="0 0/10 * * * ?")
	public void withdrawSchedule() {
		//10分钟轮训，10分钟之前，2小时之内的数据，状态是wait的数据记录
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.infoLog("withdrawSchedule-轮询开始", sdf.format(new Date()));
		Map<String, Object> map = new HashMap<>();
		map.put("preDate", DateUtils.addMinutes(new Date(), -120));
		map.put("nextDate", DateUtils.addMinutes(new Date(), -10));
		map.put("status", "wait_verify");
		List<WithdrawCash> withdrawCashs = withdrawCashService.getWithdrawCashByDateAndStatus(map);
		if (CollectionUtils.isEmpty(withdrawCashs)) {
			log.infoLog("withdrawSchedule-轮询结束", "没有可以轮询的数据");
			return;
		}
		for (WithdrawCash withdrawCash : withdrawCashs) {
			try {
				String url = "/trade/query.do";
				JSONObject param = new JSONObject();
				param.put("requestNo", withdrawCash.getId());
				param.put("type", BusinessEnum.withdraw_cash);
				param.put("handle", 1);
				JSONObject result = HttpUtil.sendDRPayPost(param, url);
				if (result != null && result.getInteger("code") == 1) {
					JSONObject data = result.getJSONObject("data");
					JSONObject respData = null;
					if (data != null) {
						respData = data.getJSONObject("respData");
					}
					if (respData != null ) {
						log.infoLog("withdrawSchedule-轮询", "提现流水号："+withdrawCash.getId()+"单笔查询结果为："+respData);
					} else {
						log.errLog("withdrawSchedule-轮询异常", "drpay单笔业务查询respData为空");
					}
				} else {
					log.errLog("withdrawSchedule-轮询异常",
							"调用drpay服务异常" + result.getString("msg"));
				}
			} catch (Exception e) {
				log.errLog("withdrawSchedule-轮询异常", e);
			}
		}
		log.infoLog("withdrawSchedule-轮询结束", sdf.format(new Date()));
	}
	
	@Scheduled(cron="0 11 * * * ?")
	public void withdrawFailSchedule() {
		//T+1日后的11点（出款以后），轮训昨天的所有提现记录，如果状态是fail提现失败，需要做提现回退接口，把资金回退给用户账号。
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.infoLog("withdrawFailSchedule-轮询开始", sdf.format(new Date()));
		Map<String, Object> map = new HashMap<>();
		map.put("preDate", DateUtils.addMinutes(new Date(), -(60 * 11 + 60 * 24)));
		map.put("nextDate", DateUtils.addMinutes(new Date(), -(60 * 11)));
		map.put("status", "wait_verify");
		List<WithdrawCash> withdrawCashs = withdrawCashService.getWithdrawCashByDateAndStatus(map);
		if (CollectionUtils.isEmpty(withdrawCashs)) {
			log.infoLog("withdrawFailSchedule-轮询结束", "没有可以轮询的数据");
			return;
		}
		for (WithdrawCash withdrawCash : withdrawCashs) {
			try {
				String url = "/trade/query.do";
				JSONObject param = new JSONObject();
				param.put("requestNo", withdrawCash.getId());
				param.put("type", BusinessEnum.withdraw_cash);
				param.put("handle", 1);
				JSONObject result = HttpUtil.sendDRPayPost(param, url);
				if (result != null && result.getInteger("code") == 1) {
					JSONObject data = result.getJSONObject("data");
					JSONObject respData = null;
					if (data != null) {
						respData = data.getJSONObject("respData");
					}
					if (respData != null && respData.getInteger("code") == 0) {
						//|| "ACCEPT_FAIL".equals(respData.getString("status"))
						if ("FAIL".equals(respData.getString("status")) ) {
							String userId = withdrawCash.getUserId();
							Double actualMoney = withdrawCash.getActualMoney();
							//存管通资金回退充值
							String rollbackUrl = "/payment/rechargeWithRollback.do";
							JSONObject rollbackParam = new JSONObject();
							rollbackParam.put("requestNo", withdrawCash.getId());
							JSONObject rollbackResult = HttpUtil.sendDRPayPost(rollbackParam, rollbackUrl);
							if (rollbackResult != null && rollbackResult.getInteger("code") == 1) {
								JSONObject rollbackData = rollbackResult.getJSONObject("data");
								JSONObject rollbackRespData = null;
								if (data != null) {
									rollbackRespData = rollbackData.getJSONObject("respData");
								}
								//存管通资金回退充值成功
								if (rollbackRespData != null && rollbackRespData.getInteger("code") == 0) {
									//本地提现退款账户转入
									userAccountService.transferIn(userId, actualMoney,
											BusinessEnum.refund, "提现退款", "提现退款",
											withdrawCash.getId());
								} else {
									log.errLog("withdrawFailSchedule-轮询", "充值流水号："+withdrawCash.getId()+"drpay资金回退充值失败");
								}
							}
						}
						log.infoLog("withdrawFailSchedule-轮询", "提现失败回退，流水号："+withdrawCash.getId()+"单笔查询结果为："+respData);
					} else {
						log.errLog("withdrawFailSchedule-轮询异常", "drpay单笔业务查询respData为空");
					}
				} else {
					log.errLog("withdrawFailSchedule-轮询异常",
							"调用drpay服务异常" + result.getString("msg"));
				}
			} catch (Exception e) {
				log.errLog("withdrawFailSchedule-轮询异常", e);
			}
		}
		log.infoLog("withdrawFailSchedule-轮询结束", sdf.format(new Date()));
	}
}
