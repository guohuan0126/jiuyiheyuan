package com.duanrong.yeepay.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

import util.Log;
import util.MyStringUtils;
import base.exception.InsufficientBalance;

import com.duanrong.business.invest.InvestConstants;
import com.duanrong.business.invest.dao.InvestDao;
import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.invest.model.InvestRedpacket;
import com.duanrong.business.loan.LoanConstants;
import com.duanrong.business.loan.dao.LoanDao;
import com.duanrong.business.loan.model.Loan;
import com.duanrong.business.platformtransfer.model.PlatformTransfer;
import com.duanrong.business.platformtransfer.service.PlatformTransferService;
import com.duanrong.business.repay.service.RepayService;
import com.duanrong.business.risk.service.SystemBillService;
import com.duanrong.business.sms.SmsConstants;
import com.duanrong.business.sms.service.SmsService;
import com.duanrong.business.trusteeship.model.TrusteeshipOperation;
import com.duanrong.business.trusteeship.model.TrusteeshipYeepayConstants;
import com.duanrong.business.trusteeship.service.TrusteeshipOperationService;
import com.duanrong.business.user.dao.RedPacketDao;
import com.duanrong.business.user.dao.UserDao;
import com.duanrong.business.user.model.RedPacket;
import com.duanrong.business.user.model.User;
import com.duanrong.business.user.service.InformationService;
import com.duanrong.business.user.service.UserMoneyService;
import com.duanrong.newadmin.utility.ArithUtil;
import com.duanrong.newadmin.utility.DateUtil;
import com.duanrong.newadmin.utility.Dom4jUtil;
import com.duanrong.newadmin.utility.LoadConstantProterties2;
import com.duanrong.util.IdGenerator;
import com.duanrong.util.InterestUtil;
import com.duanrong.util.client.DRHTTPSClient;
import com.duanrong.yeepay.service.TrusteeshipGiveMoneyToBorrowerService;
import com.duanrong.yeepay.service.TrusteeshipPlatformTransferService;
import com.duanrong.yeepaysign.CFCASignUtil;

/**
 * 
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : SunZ
 * @CreateTime : 2015-4-2 下午11:16:18
 * @Description : NewAdmin com.duanrong.yeepay.service.impl
 *              TrusteeshipGiveMoneyToBorrowerServiceImpl.java
 * 
 */
@Service
public class TrusteeshipGiveMoneyToBorrowerServiceImpl implements
		TrusteeshipGiveMoneyToBorrowerService {

	@Resource
	Log log;

	@Resource
	RepayService repayService;

	@Resource
	LoanDao loanDao;

	@Resource
	InvestDao investDao;

	@Resource
	UserMoneyService userMoneyService;

	@Resource
	TrusteeshipOperationService trusteeshipOperationService;

	@Resource
	UserDao userDao;

	@Resource
	SystemBillService systemBillService;

	@Resource
	SmsService smsService;

	@Resource
	RedPacketDao redPacketDao;

	@Resource
	InformationService informationService;
	
	@Resource
	PlatformTransferService platformTransferService;
	
	@Resource
	TrusteeshipPlatformTransferService trusteeshipPlatformTransferService;
	
	byte[] lock = new byte[0];

	//@Override
	/*public String giveMoneyToBorrower(String userId, String loanId)
			throws Exception {
		if (StringUtils.isBlank(loanId)) {
			log.infoLog("放款失败", userId + "在执行放款时失败,借款项目id错误:" + loanId);
			return "放款失败:借款项目id错误";
		}
		Loan loan = loanDao.get(loanId);
		if (loan == null) {
			log.infoLog("放款失败", userId + "在执行放款时失败,借款项目不存在");
			return "放款失败:借款项目不存在";
		}
		if (!LoanConstants.LoanStatus.RECHECK.equals(loan.getStatus())) {
			log.infoLog("放款失败", userId + "在执行放款时失败,借款项目状态不是等待复核");
			return "放款失败:借款项目状态不是等待复核";
		}

		Invest invest = new Invest();
		invest.setStatus(InvestConstants.InvestStatus.WAIT_AFFIRM);
		invest.setLoanId(loanId);
		List<Invest> invests = investDao.getInvestLoan(invest);
		if (invests != null && !invests.isEmpty()) {
			log.infoLog("放款失败", userId + "在执行放款时失败,有" + invests.size()
					+ "人投资状态为等待确认");
			return "放款失败:有" + invests.size() + "人投资状态为等待确认";
		}

		invest.setStatus("还款中,投标成功");
		invests = investDao.getInvestLoan(invest);
		if (invests == null || invests.isEmpty()) {
			log.infoLog("放款失败", userId + "在执行放款时失败,没有有效投资人");
			return "放款失败:没有有效投资人";
		}

		Double sumMoney = investDao.getValidInvestSumByLoan(loanId);
		if (!sumMoney.equals(loan.getTotalmoney())) {
			log.infoLog("放款失败", userId + "在执行放款时失败,投资总金额不等于借款总金额");
			return "放款失败:投资人总金额不等于借款总金额";
		}
		//return giveMoneyToBorrower(loan, invests, userId);
		String result = "";
		synchronized (lock){
			 result = giveMoneyToBorrowerFromInvest(loan, invests, userId);
		}	
		return result;
		
	}

	private String createXMLRequestData(String requestNo) {
		StringBuffer content = new StringBuffer();
		content.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
		// platformNo:商户编号
		content.append("<request platformNo=\""
				+ TrusteeshipYeepayConstants.Config.MER_CODE + "\">");

		content.append("<requestNo>" + requestNo + "</requestNo>");
		content.append("<mode>CONFIRM</mode>");
		content.append("<notifyUrl>"
				+ TrusteeshipYeepayConstants.ResponseS2SUrl.PRE_RESPONSE_URL
				+ "complete_transaction_invest" + "</notifyUrl>");
		content.append("</request>");
		return content.toString();
	}
*/
	/*******************************************************************************/
	/******************************** 易宝2.0放款 ************************************/
	/*******************************************************************************/
	/**
	 * 易宝放款
	 * 
	 * @param loan
	 * @param invests
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	/*private String giveMoneyToBorrowerFromInvest(Loan loan,
			List<Invest> invests, String userId) {

		double money = 0D;// 本次放款金额
		double managementExpense = 0D; // 本次放款勾出管理费

		// 对每一个投资记录放款
		for (Invest invest : invests) {
			if (invest.getStatus().equals("投标成功")) {

				String xml = "";
				try {
					xml = createXMLRequestData(invest.getId());// 放款xml数据
				} catch (Exception e) {
					log.errLog("放款失败：investID："+invest.getId(), e);
					continue;
				}
				String type = TrusteeshipYeepayConstants.OperationType.INVEST;
				if (invest.getIsAutoInvest()) {
					type = TrusteeshipYeepayConstants.OperationType.AUTOINVEST;
				}
				String sign = "";
				try{
					sign = CFCASignUtil.sign(xml);
				}catch(Exception e){
					log.errLog("放款失败，签名错误， investId"+invest.getId(), e);
					continue;
				}
				TrusteeshipOperation to = trusteeshipOperationService.read(null,
						invest.getId(), invest.getId(), "yeepay");
				to.setRequestData(to.getRequestData() + "\nsign=" + sign
						+ ",req=" + xml);
				List<NameValuePair> params = new ArrayList<>();
				params.add(new BasicNameValuePair("req", xml));
				params.add(new BasicNameValuePair("sign", sign));
				params.add(new BasicNameValuePair("service",
						"COMPLETE_TRANSACTION"));
				String responseBody = "";
				try {
					responseBody = DRHTTPSClient.sendHTTPRequestPostToString(TrusteeshipYeepayConstants.RequestUrl.RequestDirectUrl,
							new BasicHeader[0], params);
				} catch (IOException e) {
					log.errLog("放款失败，请求发送失败， investId"+invest.getId(), e);
					continue;
				}
				try {
					log.infoLog("投标放款", "loanId: " + loan.getId() + "investId:"
							+ invest.getId() + responseBody);
					@SuppressWarnings("unchecked")
					Map<String, String> respMap = Dom4jUtil
							.xmltoMap(responseBody);
					String code = respMap.get("code");
					to.setResponseData(to.getResponseData() + responseBody);
					if (code.equals("1")) {
						if (loan.getStatus().equals(
								LoanConstants.LoanStatus.RECHECK)) {
							try {
								giveMoneyToBorrowerLocalDispose(loan, invest);// 放款成功后本地处理
								to.setStatus("confirm");
								trusteeshipOperationService.update(to);							
								money += invest.getMoney();
								managementExpense += invest
										.getManagementExpense();
							} catch (Exception e) {
								log.errLog("投标放款", e);
							}			
						}
					} else {
						to.setStatus(TrusteeshipYeepayConstants.Status.REFUSED);
						trusteeshipOperationService.update(to);
					}
				} catch (Exception e) {
					log.errLog("投标放款,loanId:" + loan.getId() + ", investId:"
							+ invest.getId() + "放款人:" + userId, e);
				}
			}
		}
		// 根据借款期限产生还款信息
		try {

			// 所有的投资记录全部转账成功则生成还夸计划，修改项目状态
			if (investDao.getInvestSeccessByLoanId(loan.getId()) == 0) {
				loan.setInvests(invests);
				repayService.generateRepay(invests, loan);
				loan.setStatus("还款中");
				
				if (loan.getSpecialType() != null
						&& "olympic".equals(loan.getSpecialType())) {
					olympicAward(loan);
				}
			}

			if (managementExpense >= 0) {
				userMoneyService.transferOutFrozenToManagementCost(
						loan.getBorrowMoneyUserID(), managementExpense,
						"借款成功，取出借款管理费", "借款ID：" + loan.getId());
				money = ArithUtil.sub(money, managementExpense);
				// 把借款手续费转入系统账户
				systemBillService.transferInto(
						managementExpense,
						"扣除借款手续费",
						"借款ID：" + loan.getId() + "........借款项目名称:"
								+ loan.getName());

				// 把借款转给借款人账户
				userMoneyService.transferIntoBalance(
						loan.getBorrowMoneyUserID(), money,
						"借款项目:" + loan.getName() + ",已经到账!",
						"借款ID：" + loan.getId());
			}
			loanDao.update(loan);
			User user = userDao.get(loan.getBorrowMoneyUserID());
			if (money > 0) {
				String sendContent = user.getRealname() + "，您好！您融资的项目："
						+ loan.getName() + "，现已放款，放款金额：" + money
						+ "元，已经扣除借款管理费" + managementExpense + "元。";
				smsService.sendSms(loan.getBorrowMoneyUserID(), sendContent,
						SmsConstants.GIVEMOENYTOBORROWER);
				informationService.sendInformation(loan.getBorrowMoneyUserID(),
						"放款通知", sendContent);
			}
			return "放款成功";
		} catch (Exception e) {
			log.errLog("放款异常", e);
		}
		return null;
	}*/

	
	/*private void olympicAward(Loan loan) {
		double money = 28;
		Invest invest = new Invest();
		invest.setLoanId(loan.getId());
		invest.setIsAutoInvest(false);
		List<Invest> invests = investDao.getInvestByLoanSortDesc(invest);
		int size = invests.size();
		if (size > 0) {
			try {
				invest = invests.get(0);
				PlatformTransfer platformTransfer = new PlatformTransfer();
				platformTransfer.setOrderId("OYMCSTAR" + invest.getId());
				platformTransfer.setType("olympic");
				platformTransfer.setStatus("平台划款成功");
				List<PlatformTransfer> ps = platformTransferService
						.getList(platformTransfer);
				if (ps.isEmpty()) {	
					String remarks = "恭喜您在奥运竞技赛中投资" + loan.getId() + "项目，投资id:"
							+ invest.getId() + "获得“终结者奖”， " + money
							+ "元现金已经发送到您的账户";
					String status = trusteeshipPlatformTransferService
							.platformTransferTrusteeship(
									"OYMCSTAR" + invest.getId(),
									invest.getInvestUserID(), money, remarks,
									"olympic", loan.getId(), null);
					if ("success".equals(status)) {
						userMoneyService.transferIntoBalance(
								invest.getInvestUserID(), money, "奥运竞技赛奖金",
								"loanId:" + loan.getId() + ", investId:"
										+ invest.getId() + ", 终结者奖：" + money
										+ "元");
						smsService
								.sendSms1(
										invest.getInvestUserID(),
										"太棒啦，您获得奥运标项目的“终结者奖”，"
												+ money
												+ "元现金已经发送到您的账户，点击  t.cn/R4L4Usl 查看，回复TD退订。",
										null);
						informationService.sendInformation(
								invest.getInvestUserID(), "终结者奖",
								"太棒啦，恭喜您在奥运竞技赛中获得“终结者奖”，" + money
										+ "元领先奖现金已经发送到您的账户，请注意查收。");
					}
				}
			} catch (Exception e) {
				log.errLog("终结者奖发送失败" + "investId:" + invest.getId(), e);
			}

			money = 18;
			try {
				invest = invests.get(size - 1);
				PlatformTransfer platformTransfer = new PlatformTransfer();
				platformTransfer.setOrderId("OYMCEND" + invest.getId());
				platformTransfer.setType("olympic");
				platformTransfer.setStatus("平台划款成功");
				List<PlatformTransfer> ps = platformTransferService
						.getList(platformTransfer);
				if (ps.isEmpty()) {		
					String remarks = "恭喜您在奥运竞技赛中投资" + loan.getId() + "项目，投资id:"
							+ invest.getId() + "获得“快投手奖”， " + money
							+ "元现金已经发送到您的账户";
					String status = trusteeshipPlatformTransferService
							.platformTransferTrusteeship(
									"OYMCEND" + invest.getId(),
									invest.getInvestUserID(), money, remarks,
									"olympic", loan.getId(), null);
					if ("success".equals(status)) {
						userMoneyService.transferIntoBalance(
								invest.getInvestUserID(), money, "奥运竞技赛奖金",
								"loanId:" + loan.getId() + ", investId:"
										+ invest.getId() + ", 快投手奖：" + money
										+ "元");
						smsService
								.sendSms1(
										invest.getInvestUserID(),
										"太棒啦，您获得奥运标项目的“快投手奖”，"
												+ money
												+ "元现金已经送达您的账户，点击  t.cn/R4L4Usl 查看，回复TD退订。",
										null);
						informationService.sendInformation(
								invest.getInvestUserID(), "快投手奖",
								"太棒啦，恭喜您在奥运竞技赛中获得“快投手奖”，" + money
										+ "元现金已经送达您的账户，请注意查收。");

					}
				}
			} catch (Exception e) {
				log.errLog("快投手奖发送失败" + "investId:" + invest.getId(), e);
			}

		}
	}*/
	
	/**
	 * 
	 * @description 放款成功后本地处理
	 * @author 孙铮
	 * @time 2015-3-20 下午3:25:56
	 * @param id
	 */
	/*@Override
	public void giveMoneyToBorrowerLocalDispose(Loan loan, Invest invest)
			throws Exception {

		Date date = new Date();
		if (loan.getGiveMoneyOperationTime() == null
				|| loan.getGiveMoneyTime() == null) {
			// 设置计息日期
			Date addDay = DateUtil.addDay(date, 1);
			loan.setGiveMoneyTime(addDay);
			// 设置放款操作日期
			loan.setGiveMoneyOperationTime(date);
			// 设置还款时间
			if (loan.getOperationType().equals("月")) {
				loan.setFinishTime(DateUtil.addMonth(addDay, loan.getDeadline()));
			} else {
				loan.setFinishTime(DateUtil.addDay(date, loan.getDay()));
			}
		}

		Double rate = loan.getRate();
		// 实际到借款账户的金额
		double actualMoney = 0D;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Double money = invest.getMoney();
			if (LoanConstants.InterestRule.INVEST_NEXT_DAY.equals(loan
					.getInterestRule())) {
				Integer dayDifference = DateUtil.dayDifference(
						sdf.format(invest.getTime()), sdf.format(date));// 补多少天的利息
				if (dayDifference > 0) {
					double investAllowanceInterest = InterestUtil
							.getInterestByPeriodDay(money, rate, dayDifference);
					invest.setInvestAllowanceInterest(investAllowanceInterest);// 设置补息金额
					invest.setInterest(ArithUtil.round(invest.getInterest()
							+ investAllowanceInterest, 2));// 对预计收益和补息相加
					loan.setLoanAllowanceInterest(ArithUtil.round(
							loan.getLoanAllowanceInterest()
									+ investAllowanceInterest, 2));// 对所有补息做累加,保存loan
				}

			}
			try {
				userMoneyService
						.transferOutFromFrozen(invest.getInvestUserID(),
								invest.getMoney(),
								"投资" + loan.getName() + "成功",
								"借款ID：" + loan.getId());
				actualMoney = ArithUtil.add(actualMoney, invest.getMoney());
				informationService.sendInformation(invest.getInvestUserID(),
						"放款通知", "尊敬的投资人：您投资的"+loan.getName()+"项目，投资金额"+ArithUtil.round(invest.getMoney(), 2)+"元，已经放款给借款人，感谢您的投资。");
			} catch (InsufficientBalance e) {
				log.errLog("本地处理放款冻结投资金额异常,loanId:" + loan.getId() + "userId:"
						+ invest.getInvestUserID(), e);
			}
			// 更改投资状态
			invest.setStatus(InvestConstants.InvestStatus.REPAYING);
			investDao.update(invest);

			// 计算加息卷金额
			double d = 0;
			if (invest.getRedpacketId() > 0) {
				d = redPacketRepay(invest.getRedpacketId(),
						loan.getOperationType(), loan.getRepayType(), money,
						loan.getDay(), loan.getDeadline());
			}
			log.infoLog("计算加息奖励信息：", "投资明细ID" + invest.getId() + "加息券ID"
					+ invest.getRedpacketId() + "奖励金额：" + d);

			// 如果补息或者奖励金额大于0,则 创建补息和奖励记录
			if (invest.getInvestAllowanceInterest() > 0 || d > 0) {
				InvestRedpacket ir = new InvestRedpacket();
				ir.setId(IdGenerator.randomUUID());
				ir.setInvestId(invest.getId());
				ir.setLoanId(loan.getId());
				ir.setAllowanceOrder(LoadConstantProterties2
						.getValueByDefaultPro(null) + "TZBX" + invest.getId());
				ir.setRewardMoney(d);
				ir.setInvestAllowanceInterest(invest
						.getInvestAllowanceInterest());
				ir.setSendAllowanceStatus(0);
				ir.setRepackedOrder(LoadConstantProterties2
						.getValueByDefaultPro(null) + "JXJL" + invest.getId());
				ir.setSendRedpacketStatus(0);
				ir.setRepackedId(invest.getRedpacketId());
				ir.setCreateTime(new Date());
				ir.setUserId(invest.getInvestUserID());
				investDao.insertInvestRedpacket(ir);
			}
		} catch (Exception e) {
			throw e;
		}
	}*/

	/*****************************************************************************/
	/***************************** 易宝1.0放款 ************************************/
	/*****************************************************************************/
	/**
	 * 
	 * @param loan
	 * @param invests
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	/*private String giveMoneyToBorrower(Loan loan, List<Invest> invests,
			String userId) throws Exception {
		String xml = createXMLRequestData(invests, loan);// 放款xml数据
		HttpClient client = new HttpClient();
		PostMethod postMethod = new PostMethod(
				TrusteeshipYeepayConstants.RequestUrl.RequestDirectUrl);
		postMethod.setParameter("req", xml);
		String sign = CFCASignUtil.sign(xml);
		postMethod.setParameter("sign", sign);
		postMethod.setParameter("service", "LOAN");

		*//**
		 * 发送前本地保存放款记录到to表中
		 *//*
		TrusteeshipOperation to = new TrusteeshipOperation();
		to.setId(IdGenerator.randomUUID());
		to.setMarkId(loan.getId());
		to.setOperator(loan.getId());
		to.setRequestUrl(TrusteeshipYeepayConstants.RequestUrl.RequestDirectUrl);
		to.setRequestData(xml);
		to.setRequestTime(new Date());
		to.setStatus(TrusteeshipYeepayConstants.Status.SENDED);
		to.setType(TrusteeshipYeepayConstants.OperationType.GIVE_MOENY_TO_BORROWER);
		to.setTrusteeship("yeepay");
		to.setUserId(userId);
		trusteeshipOperationService.insert(to);

		try {
			int statusCode = client.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				log.infoLog("放款失败", userId + "在执行放款时失败,httpStatus错误:"
						+ HttpStatus.SC_OK + postMethod.getStatusLine());
			}
			// 获得返回的结果 
			byte[] responseBody = postMethod.getResponseBody();

			log.infoLog("放款", new String(responseBody, "UTF-8"));
			@SuppressWarnings("unchecked")
			Map<String, String> respMap = Dom4jUtil.xmltoMap(new String(
					responseBody, "UTF-8"));

			String code = respMap.get("code");
			String description = respMap.get("description");

			to.setResponseData(new String(responseBody, "UTF-8"));
			to.setResponseTime(new Date());

			if (code.equals("1")) {
				if (loan.getStatus().equals(LoanConstants.LoanStatus.RECHECK)) {
					giveMoneyToBorrowerLocalDispose(loan.getId());// 放款成功后本地处理
					// 给借款人发送短信和站内信
					to.setStatus(TrusteeshipYeepayConstants.Status.PASSED);
					trusteeshipOperationService.update(to);
					try {
						User user = userDao.get(loan.getBorrowMoneyUserID());
						String sendContent = user.getRealname() + "，您好！您融资的项目："
								+ loan.getName() + "，现已放款，放款金额："
								+ loan.getTotalmoney() + "元，已经扣除借款管理费"
								+ loan.getLoanGuranteeFee() + "元。";
						smsService.sendSms(loan.getBorrowMoneyUserID(),
								sendContent, SmsConstants.GIVEMOENYTOBORROWER);
						informationService.sendInformation(
								loan.getBorrowMoneyUserID(), "放款通知",
								sendContent);
					} catch (Exception ex) {
						log.errLog(loan.getId() + "放款短信和站内信发送失败", ex);
					}
					try {
						OcsRemoveUtil.removeLoanInfo(loan.getId());
					} catch (Exception e) {
						log.errLog("清空缓存", e);
					}

					return "放款成功";
				}
			} else {
				to.setStatus(TrusteeshipYeepayConstants.Status.REFUSED);
				trusteeshipOperationService.update(to);
				return "放款失败:" + description;
			}
		} catch (Exception e) {
			log.errLog("放款失败,loanId:" + loan.getId() + "放款人:" + userId, e);
			throw e;

		} finally {
			postMethod.releaseConnection();
		}
		return null;
	}
*/
	/**
	 * 
	 * @description 拼接放款请求数据
	 * @author 孙铮
	 * @time 2015-4-2 下午10:59:27
	 * @param invests
	 *            投资人集合
	 * @param loan
	 *            借款项目
	 * @return 拼接好的xml字符串
	 */
	/*private String createXMLRequestData(List<Invest> invests, Loan loan) {
		StringBuffer content = new StringBuffer();
		content.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
		// platformNo:商户编号
		content.append("<request platformNo=\""
				+ TrusteeshipYeepayConstants.Config.MER_CODE + "\">");
		// 标的号
		content.append("<orderNo>" + loan.getId() + "</orderNo>");
		// 平台方收取费用
		content.append("<fee>" + loan.getLoanGuranteeFee() + "</fee>");
		content.append("<transfers>");
		for (Invest invest : invests) {
			content.append("<transfer>");
			// 之前转账请求时，转账请求流水号
			content.append("<requestNo>" + invest.getId() + "</requestNo>");
			// 转账请求转账金额
			content.append("<transferAmount>" + invest.getMoney()
					+ "</transferAmount>");
			// 投资人会员类型
			content.append("<sourceUserType>MEMBER</sourceUserType>");
			// 投资人会员编号
			content.append("<sourcePlatformUserNo>" + invest.getInvestUserID()
					+ "</sourcePlatformUserNo>");
			// 借款人会员类型
			content.append("<targetUserType>MEMBER</targetUserType>");
			// 借款人会员编号
			content.append("<targetPlatformUserNo>"
					+ loan.getBorrowMoneyUserID() + "</targetPlatformUserNo>");
			content.append("</transfer>");
		}
		content.append("</transfers>");
		// 服务器通知 URL
		content.append("<notifyUrl>"
				+ TrusteeshipYeepayConstants.ResponseS2SUrl.PRE_RESPONSE_URL
				+ TrusteeshipYeepayConstants.OperationType.GIVE_MOENY_TO_BORROWER
				+ "</notifyUrl>");
		content.append("</request>");
		return content.toString();
	}

	*//**
	 * 
	 * @description 放款成功后本地处理
	 * @author 孙铮
	 * @time 2015-3-20 下午3:25:56
	 * @param id
	 *//*
	public void giveMoneyToBorrowerLocalDispose(String loanId) {

		Loan loan = loanDao.get(loanId);
		// 更改项目状态，放款。
		loan.setStatus(LoanConstants.LoanStatus.REPAYING);
		Date date = new Date();
		// 设置计息日期
		Date addDay = DateUtil.addDay(date, 1);
		loan.setGiveMoneyTime(addDay);
		// 设置放款操作日期
		loan.setGiveMoneyOperationTime(date);
		// 设置还款时间
		if (loan.getOperationType().equals("月")) {
			loan.setFinishTime(DateUtil.addMonth(addDay, loan.getDeadline()));
		} else {
			loan.setFinishTime(DateUtil.addDay(date, loan.getDay()));
		}

		Double rate = loan.getRate();
		// 实际到借款账户的金额
		double actualMoney = 0D;
		// 所人投资人补息之和
		double loanAllowanceInterest = loan.getLoanAllowanceInterest();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Invest i = new Invest();
		i.setStatus(InvestConstants.InvestStatus.BID_SUCCESS);
		i.setLoanId(loanId);
		List<Invest> invests = investDao.getInvestLoan(i);
		for (Invest invest : invests) {
			try {
				Double money = invest.getMoney();
				double investAllowanceInterest = invest
						.getInvestAllowanceInterest();
				if (LoanConstants.InterestRule.INVEST_NEXT_DAY.equals(loan
						.getInterestRule())) {
					Integer dayDifference = DateUtil.dayDifference(
							sdf.format(invest.getTime()), sdf.format(date));// 补多少天的利息
					if (dayDifference > 0) {
						investAllowanceInterest = InterestUtil
								.getInterestByPeriodDay(money, rate,
										dayDifference);
						invest.setInvestAllowanceInterest(investAllowanceInterest);// 设置补息金额
						invest.setInterest(ArithUtil.round(invest.getInterest()
								+ investAllowanceInterest, 2));// 对预计收益和补息相加
						loanAllowanceInterest += investAllowanceInterest;// 对所有补息做累加,保存loan
					}
				}
				try {
					userMoneyService.transferOutFromFrozen(
							invest.getInvestUserID(), invest.getMoney(), "投资"
									+ loan.getName() + "成功",
							"借款ID：" + loan.getId());
					actualMoney = ArithUtil.add(actualMoney, invest.getMoney());
					
				} catch (InsufficientBalance e) {
					log.errLog("本地处理放款冻结投资金额异常,loanId:" + loan.getId()
							+ "userId:" + invest.getInvestUserID(), e);
				}
				// 更改投资状态
				invest.setStatus(InvestConstants.InvestStatus.REPAYING);
				investDao.update(invest);

				// 计算加息卷金额
				double d = 0;
				if (invest.getRedpacketId() > 0) {
					d = redPacketRepay(invest.getRedpacketId(),
							loan.getOperationType(), loan.getRepayType(),
							money, loan.getDay(), loan.getDeadline());
				}
				log.infoLog("计算加息奖励信息：", "投资明细ID" + invest.getId() + "加息券ID"
						+ invest.getRedpacketId() + "奖励金额：" + d);

				// 如果补息或者奖励金额大于0,则 创建补息和奖励记录
				if (investAllowanceInterest > 0 || d > 0) {
					InvestRedpacket ir = new InvestRedpacket();
					ir.setId(IdGenerator.randomUUID());
					ir.setInvestId(invest.getId());
					ir.setLoanId(loanId);
					ir.setAllowanceOrder(LoadConstantProterties2
							.getValueByDefaultPro(null)
							+ "TZBX"
							+ invest.getId());
					ir.setRewardMoney(d);
					ir.setInvestAllowanceInterest(investAllowanceInterest);
					ir.setSendAllowanceStatus(0);
					ir.setRepackedOrder(LoadConstantProterties2
							.getValueByDefaultPro(null)
							+ "JXJL"
							+ invest.getId());
					ir.setSendRedpacketStatus(0);
					ir.setRepackedId(invest.getRedpacketId());
					ir.setCreateTime(new Date());
					ir.setUserId(invest.getInvestUserID());
					investDao.insertInvestRedpacket(ir);
				}

				// 发送短信和站内信
				try {
					String sendContent = "尊敬的短融网用户您好，您投资的" + loan.getName()
							+ "项目，金额" + invest.getMoney()
							+ "元，已经放款给借款人，谢谢您的投资。";
					informationService.sendInformation(
							invest.getInvestUserID(), "放款通知", sendContent);
				} catch (Exception ex) {
					log.errLog("本地处理放款投资人短信和站内信发送失败,loanId:" + loan.getId()
							+ "userId:" + invest.getInvestUserID(), ex);
				}
			} catch (Exception e) {
				log.errLog("放款异常", e);
			}

		}
		loan.setLoanAllowanceInterest(ArithUtil.round(loanAllowanceInterest, 2));
		loan.setInvests(invests);
		// 根据借款期限产生还款信息
		try {
			repayService.generateRepay(invests, loan);
			Double loanGuranteeFee = loan.getLoanGuranteeFee();
			try {
				if (loanGuranteeFee >= 0) {
					userMoneyService.transferOutFrozenToManagementCost(
							loan.getBorrowMoneyUserID(), loanGuranteeFee,
							"借款成功，取出借款管理费", "借款ID：" + loan.getId());
					actualMoney = ArithUtil.sub(actualMoney, loanGuranteeFee);
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

			// 把借款手续费转入系统账户
			systemBillService.transferInto(loanGuranteeFee, "扣除借款手续费", "借款ID："
					+ loan.getId() + "........借款项目名称:" + loan.getName());

			// 把借款转给借款人账户
			userMoneyService.transferIntoBalance(loan.getBorrowMoneyUserID(),
					actualMoney, "借款项目:" + loan.getName() + ",已经到账!", "借款ID："
							+ loan.getId());

			loanDao.update(loan);
		} catch (Exception e) {
			log.errLog("放款异常", e);
		}

	}

	*//**
	 * 生成加息券金额
	 * 
	 * @param id
	 *//*
	private double redPacketRepay(int redId, String operationType,
			String repayType, double money, int days, int deadLine) {
		double d = 0;
		// 大于0表示该笔投资使用加息券
		if (redId > 0) {
			RedPacket packet = redPacketDao.get(redId);
			String type = packet.getType();
			String status = packet.getSendStatus();
			double rate = packet.getRate();
			if (23 == packet.getRuleId()) {// 如果红包规则是23则金额最高计算上限是5万
				money = money > 50000 ? 50000 : money;
			}
			if ("rate".equals(type) && "used".equals(status)) {
				int periods = 0;
				if (MyStringUtils.equals("天", operationType)) {
					periods = days;
				} else {
					periods = deadLine;
				}
				d = InterestUtil.getInterestByPeriod(money, rate, periods,
						operationType, repayType);
			} else if ("money".equals(type) && "used".equals(status)) {
				// 固定金额奖励的情况下
				d = packet.getMoney();
			}
		}
		return ArithUtil.round(d, 2);
	}

	*//**
	 * 易宝单笔放款
	 * 
	 * @param loan
	 * @param invests
	 * @param userId
	 * @return
	 * @throws Exception
	 *//*
	public String giveMoneyToBorrowerFromInvest(Loan loan, Invest invest,
			String userId) throws Exception {

		double money = 0D;// 本次放款金额
		double managementExpense = 0D; // 本次放款勾出管理费
		if (invest.getStatus().equals("投标成功")) {
			String xml = createXMLRequestData(invest.getId());// 放款xml数据
			String type = TrusteeshipYeepayConstants.OperationType.INVEST;
			if (invest.getIsAutoInvest()) {
				type = TrusteeshipYeepayConstants.OperationType.AUTOINVEST;
			}
			String sign = CFCASignUtil.sign(xml);
			TrusteeshipOperation to = trusteeshipOperationService.read(type,
					invest.getId(), invest.getId(), "yeepay");
			to.setRequestData(to.getRequestData() + "\nsign=" + sign + ",req="
					+ xml);
			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("req", xml));
			params.add(new BasicNameValuePair("sign", sign));
			params.add(new BasicNameValuePair("service", "COMPLETE_TRANSACTION"));
			String responseBody = DRHTTPSClient.sendHTTPRequestPostToString(TrusteeshipYeepayConstants.RequestUrl.RequestDirectUrl,
					new BasicHeader[0], params);
			try {
				log.infoLog("投标放款", "loanId: " + loan.getId() + "investId:"
						+ invest.getId() + responseBody);
				@SuppressWarnings("unchecked")
				Map<String, String> respMap = Dom4jUtil.xmltoMap(responseBody);
				String code = respMap.get("code");
				to.setResponseData(to.getResponseData() + responseBody);
				if (code.equals("1")) {
					if (loan.getStatus().equals(
							LoanConstants.LoanStatus.RECHECK)) {
						try {
							giveMoneyToBorrowerLocalDispose(loan, invest);// 放款成功后本地处理
							to.setStatus("confirm");
							trusteeshipOperationService.update(to);
							money += invest.getMoney();
							managementExpense += invest.getManagementExpense();
						} catch (Exception e) {
							log.errLog("投标放款", e);
						}
						
					}
				} else {
					to.setStatus(TrusteeshipYeepayConstants.Status.REFUSED);
					trusteeshipOperationService.update(to);
				}
			} catch (Exception e) {
				log.errLog("投标放款,loanId:" + loan.getId() + ", investId:"
						+ invest.getId() + "放款人:" + userId, e);
			}
		}

		// 根据借款期限产生还款信息
		try {

			// 所有的投资记录全部转账成功则生成还夸计划，修改项目状态
			if (investDao.getInvestSeccessByLoanId(loan.getId()) == 0) {
				Invest iv = new Invest();
				iv.setStatus("还款中");
				iv.setLoanId(loan.getId());
				List<Invest> invests = investDao.getInvestLoan(iv);
				loan.setInvests(invests);
				repayService.generateRepay(invests, loan);
				loan.setStatus("还款中");
			}

			if (managementExpense >= 0) {
				userMoneyService.transferOutFrozenToManagementCost(
						loan.getBorrowMoneyUserID(), managementExpense,
						"借款成功，取出借款管理费", "借款ID：" + loan.getId());
				money = ArithUtil.sub(money, managementExpense);
				// 把借款手续费转入系统账户
				systemBillService.transferInto(
						managementExpense,
						"扣除借款手续费",
						"借款ID：" + loan.getId() + "........借款项目名称:"
								+ loan.getName());

				// 把借款转给借款人账户
				userMoneyService.transferIntoBalance(
						loan.getBorrowMoneyUserID(), money,
						"借款项目:" + loan.getName() + ",已经到账!",
						"借款ID：" + loan.getId());
			}
			loanDao.update(loan);
			User user = userDao.get(loan.getBorrowMoneyUserID());
			if (money > 0) {
				String sendContent = user.getRealname() + "，您好！您融资的项目："
						+ loan.getName() + "，现已放款，放款金额：" + money
						+ "元，已经扣除借款管理费" + managementExpense + "元。";
				smsService.sendSms(loan.getBorrowMoneyUserID(), sendContent,
						SmsConstants.GIVEMOENYTOBORROWER);
				informationService.sendInformation(loan.getBorrowMoneyUserID(),
						"放款通知", sendContent);
			}
		
			return "放款成功";
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog("放款异常", e);
		}
		return null;
	}
*/
	@Override
	public void S2SCallback(HttpServletRequest request,
			HttpServletResponse response) {
		String notifyXML = request.getParameter("notify");

		/********** 参数解析和对象获取 **********/
		Map<String, String> resultMap = Dom4jUtil.xmltoMap(notifyXML);
		String code = resultMap.get("code");
		String requestNo = resultMap.get("requestNo");

		TrusteeshipOperation to = trusteeshipOperationService.read(null,
				requestNo, requestNo, "yeepay");
		to.setResponseTime(new Date());
		to.setResponseData(to.getResponseData() + "\n" + notifyXML);
		
		log.infoLog("转张授权resp", code);
		/********** 授权成功 **********/
		if (StringUtils.equals("1", code)) {			
			to.setStatus("confirm");			
		}else{
			to.setStatus("refused");
		}
		trusteeshipOperationService.update(to);
	}

}
