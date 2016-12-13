package com.duanrong.business.yeepay.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.quartz.impl.StdScheduler;
import org.springframework.stereotype.Service;

import util.Log;
import util.MyStringUtils;
import util.XMLUtil;
import base.exception.InsufficientBalance;
import base.exception.InsufficientBalanceException;
import base.exception.InsufficientFreezeAmountException;
import base.exception.NoOpenAccountException;
import base.pagehelper.PageInfo;

import com.duanrong.business.account.BusinessEnum;
import com.duanrong.business.account.service.UserAccountService;
import com.duanrong.business.trusteeship.model.TrusteeshipOperation;
import com.duanrong.business.trusteeship.model.TrusteeshipYeepayConstants;
import com.duanrong.business.trusteeship.service.TrusteeshipOperationService;
import com.duanrong.business.user.service.UserMoneyService;
import com.duanrong.business.yeepay.dao.FreezeDao;
import com.duanrong.business.yeepay.model.Freeze;
import com.duanrong.business.yeepay.service.FreezeService;
import com.duanrong.newadmin.constants.FundConstants;
import com.duanrong.newadmin.utility.Dom4jUtil;
import com.duanrong.newadmin.utility.GeneratorXML;
import com.duanrong.newadmin.utility.IdGenerator;
import com.duanrong.yeepaysign.CFCASignUtil;

@Service
public class FreezeServiceImpl implements FreezeService {

	@Resource
	FreezeDao freezeDao;
	
	@Resource
	UserMoneyService userMoneyService;
	
	@Resource
	TrusteeshipOperationService trusteeshipOperationService;
	
	@Resource
	Log log;
	
	@Resource
	StdScheduler scheduler;
	
	@Resource
	UserAccountService userAccountService;

	@Override
	public String freeze(Freeze fundFreeze) throws InsufficientBalance {

		// 当前余额是否可以支付冻结金额
		double balance = userAccountService.readUserAccount(fundFreeze.getUserId()).getAvailableBalance();
		if (fundFreeze.getMoney() > balance) {
			throw new InsufficientBalance("freeze money:"
					+ fundFreeze.getMoney() + ", balance:" + balance);
		}
		/*********************** XML拼接 ***********************/
		String expired = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(fundFreeze.getExpiredTime());
		// 参数拼接
		GeneratorXML xml = new GeneratorXML();
		xml.setPlatformNo(TrusteeshipYeepayConstants.Config.MER_CODE);
		xml.setPlatformUserNo(fundFreeze.getUserId());
		xml.setRequestNo(fundFreeze.getId());
		xml.setAmount(fundFreeze.getMoney());
		xml.setExpired(expired);
		String content = null;
		try {
			content = XMLUtil.getXML(xml);
		} catch (Exception e) {
			log.errLog("冻结XML拼接异常", e);
			throw new InsufficientBalance();
		}

		HttpClient client = new HttpClient();

		/* 创建一个post方法 */
		PostMethod postMethod = new PostMethod(
				TrusteeshipYeepayConstants.RequestUrl.RequestDirectUrl);
		postMethod.setParameter("req", content.toString());
		String sign = CFCASignUtil.sign(content.toString());
		postMethod.setParameter("sign", sign);
		postMethod.setParameter("service", "FREEZE");

		/* 执行post方法 */
		int statusCode;
		try {
			statusCode = client.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				log.infoLog("FreezeServiceImpl",
						"Method failed: " + postMethod.getStatusLine());
				return "fail";
			}
			/* 获得返回的结果 */
			byte[] responseBody = postMethod.getResponseBody();
			log.infoLog("FreezeServiceImpl", new String(responseBody, "UTF-8"));
			@SuppressWarnings("unchecked")
			Map<String, String> respMap = Dom4jUtil.xmltoMap(new String(
					responseBody, "UTF-8"));

			String code = respMap.get("code");
			//String description = respMap.get("description");

			/**
			 * 发送前本地保存记录到to表中
			 */
			TrusteeshipOperation to = new TrusteeshipOperation();
			to.setId(IdGenerator.randomUUID());
			to.setMarkId(fundFreeze.getId());
			to.setOperator(fundFreeze.getId());
			to.setRequestUrl(TrusteeshipYeepayConstants.RequestUrl.RequestDirectUrl);
			to.setRequestData(content.toString());
			to.setRequestTime(new Date());
			to.setType(TrusteeshipYeepayConstants.OperationType.FREEZE);
			to.setTrusteeship("yeepay");
			to.setResponseData(new String(responseBody, "UTF-8"));
			to.setResponseTime(new Date());
			to.setUserId(fundFreeze.getUserId());

			if (code.equals("1")) {
				if (fundFreeze.getStatus().equals(FundConstants.Status.UNFROZE)) {
					fundFreeze.setStatus(FundConstants.Status.FROZE);
					to.setStatus(TrusteeshipYeepayConstants.Status.PASSED);
					trusteeshipOperationService.insert(to);
				
					try {
						userAccountService.freeze(fundFreeze.getUserId(), fundFreeze.getMoney(), BusinessEnum.freeze, "管理员干预冻结", "冻结:"
								+ fundFreeze.getUserId() + "金额:"
								+ fundFreeze.getMoney(), fundFreeze.getId());
					} catch (NoOpenAccountException e) {
						log.errLog("冻结失败:", e);
						e.printStackTrace();
						return "faild";
					} catch (InsufficientBalanceException e) {
						log.errLog("冻结失败:", e);
						e.printStackTrace();
						return "faild";
					}
					/*try {
						SimpleTrigger trigger = (SimpleTrigger) scheduler
								.getTrigger(TriggerKey.triggerKey(
										fundFreeze.getId(),
										ScheduleConstants.TriggerGroup.CHECK_FREEZE_OVER_EXPECT_TIME));
						if (trigger != null) {
							Trigger newTrigger = trigger
									.getTriggerBuilder()
									.withSchedule(
											SimpleScheduleBuilder.simpleSchedule())
									.startAt(fundFreeze.getExpiredTime()).build();
							scheduler.rescheduleJob(trigger.getKey(), newTrigger);
						} else {
							JobDetail jobDetail = JobBuilder
									.newJob(CheckFreezeOverExpectTime.class)
									.withIdentity(
											fundFreeze.getId(),
											ScheduleConstants.JobGroup.CHECK_FREEZE_OVER_EXPECT_TIME)
									.build();// 任务名，任务组，任务执行类
							jobDetail.getJobDataMap().put(
									CheckFreezeOverExpectTime.FREEZE_ID, fundFreeze.getId());
							trigger = TriggerBuilder
									.newTrigger()
									.forJob(jobDetail)
									.startAt(fundFreeze.getExpiredTime())
									.withSchedule(
											SimpleScheduleBuilder.simpleSchedule())
									.withIdentity(
											fundFreeze.getId(),
											ScheduleConstants.TriggerGroup.CHECK_FREEZE_OVER_EXPECT_TIME)
									.build();
							scheduler.scheduleJob(jobDetail, trigger);
						}
					} catch (SchedulerException e) {
						log.errLog("自动解冻:", e);
						throw new RuntimeException(e);
					}*/
				}
			} else {
				to.setStatus(TrusteeshipYeepayConstants.Status.REFUSED);
				trusteeshipOperationService.insert(to);
				return "fail";
			}
		} catch (HttpException e) {
			log.errLog("Fatal protocol violation:", e);
			e.printStackTrace();
			return "fail";
		} catch (IOException e) {
			log.errLog("Fatal protocol violation:", e);
			e.printStackTrace();
			return "fail";
		} finally {
			/* Release the connection. */
			postMethod.releaseConnection();
		}
		freezeDao.save(fundFreeze);
		return "ok";
	}

	@Override
	public PageInfo<Freeze> readPageInfo(int pageNo, int pageSize) {
		return freezeDao.pageInfo(pageNo, pageSize);
	}

	/**
	 * 根据id获取freeze
	 */
	public Freeze read(String id) {
		return freezeDao.get(id);
	}

	@Override
	public String unfreeze(Freeze fundFreeze) throws InsufficientBalance {
		// 参数拼接
		GeneratorXML xml = new GeneratorXML();
		xml.setPlatformNo(TrusteeshipYeepayConstants.Config.MER_CODE);
		xml.setFreezeRequestNo(fundFreeze.getId());

		String content = null;
		try {
			content = XMLUtil.getXML(xml);
		} catch (Exception e) {
			log.errLog("解冻XML拼接异常", e);
			throw new InsufficientBalance();
		}

		HttpClient client = new HttpClient();

		/* 创建一个post方法 */
		PostMethod postMethod = new PostMethod(
				TrusteeshipYeepayConstants.RequestUrl.RequestDirectUrl);
		postMethod.setParameter("req", content.toString());
		String sign = CFCASignUtil.sign(content.toString());
		postMethod.setParameter("sign", sign);
		postMethod.setParameter("service", "UNFREEZE");

		/* 执行post方法 */
		int statusCode;
		try {
			statusCode = client.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				log.infoLog("FreezeServiceImpl",
						"Method failed: " + postMethod.getStatusLine());
				return "fail";
			}
			/* 获得返回的结果 */
			byte[] responseBody = postMethod.getResponseBody();
			log.infoLog("FreezeServiceImpl", new String(responseBody, "UTF-8"));
			@SuppressWarnings("unchecked")
			Map<String, String> respMap = Dom4jUtil.xmltoMap(new String(
					responseBody, "UTF-8"));

			String code = respMap.get("code");
			String description = respMap.get("description");

			/**
			 * 发送前本地保存记录到to表中
			 */
			TrusteeshipOperation to = new TrusteeshipOperation();
			to.setId(IdGenerator.randomUUID());
			to.setMarkId(fundFreeze.getId());
			to.setOperator(fundFreeze.getUserId());
			to.setRequestUrl(TrusteeshipYeepayConstants.RequestUrl.RequestDirectUrl);
			to.setRequestData(content.toString());
			to.setRequestTime(new Date());
			to.setType(TrusteeshipYeepayConstants.OperationType.UNFREEZE);
			to.setTrusteeship("yeepay");
			to.setResponseData(new String(responseBody, "UTF-8"));
			to.setResponseTime(new Date());

			if (code.equals("1")) {
				if (fundFreeze.getStatus().equals(FundConstants.Status.FROZE)) {
					fundFreeze.setStatus(FundConstants.Status.UNFROZESUCCESS);
					to.setStatus(TrusteeshipYeepayConstants.Status.PASSED);
					trusteeshipOperationService.insert(to);
					try {
						userAccountService.unfreeze(fundFreeze.getUserId(), fundFreeze.getMoney(), BusinessEnum.unfreeze, "管理员干预解冻", "解冻:"
										+ fundFreeze.getUserId() + "金额:"
										+ fundFreeze.getMoney(), fundFreeze.getId());
					} catch ( NoOpenAccountException | InsufficientFreezeAmountException e) {
						log.errLog("解冻失败", e);
						return "fail";
					}
				}
			} else {
				to.setStatus(TrusteeshipYeepayConstants.Status.REFUSED);
				trusteeshipOperationService.insert(to);
				return description;
			}
		} catch (HttpException e) {
			log.errLog("Fatal protocol violation:", e);
			e.printStackTrace();
			return "fail";
		} catch (IOException e) {
			log.errLog("Fatal protocol violation:", e);
			e.printStackTrace();
			return "fail";
		} finally {
			/* Release the connection. */
			postMethod.releaseConnection();
		}
		freezeDao.update(fundFreeze);
		return "ok";
	}

	@Override
	public void update(Freeze freeze) {
		freezeDao.update(freeze);
	}

	@Override
	public void dealOverExpectTime(String freezeId) {
		if (StringUtils.isBlank(freezeId)) {
			log.errLog("到期自动解冻调度", "解冻编号不存在");
			return;
		}

		Freeze freeze = freezeDao.get(freezeId);
		String userId = freeze.getUserId();
		Double money = freeze.getMoney();

		if (isOverExpectTime(freeze) && !freeze.getStatus().equals(FundConstants.Status.UNFROZESUCCESS)) {
			try {				
				userAccountService.unfreeze(userId, money, BusinessEnum.unfreeze, "到期自动解冻", "到期自动解冻", freezeId);
				freeze.setStatus(FundConstants.Status.UNFROZESUCCESS);
				freezeDao.update(freeze);
			} catch (NoOpenAccountException | InsufficientFreezeAmountException e) {
				log.errLog("到期自动解冻", e);
			}
			log.infoLog("到期自动解冻", MyStringUtils.append("ID：", freezeId,
					"用户ID：", userId, "金额：", money));
		}

	}

	public boolean isOverExpectTime(Freeze freeze) {
		if (freeze == null) {
			return false;
		}

		if (new Date().before(freeze.getExpiredTime())) {
			return false;
		}
		return true;
	}

}
