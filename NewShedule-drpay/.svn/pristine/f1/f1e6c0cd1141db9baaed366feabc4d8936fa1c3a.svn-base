package com.duanrong.yeepay.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.Log;
import util.MyStringUtils;

import com.duanrong.business.account.BusinessEnum;
import com.duanrong.business.account.service.PlatformAccountService;
import com.duanrong.business.account.service.UserAccountService;
import com.duanrong.business.invest.InvestConstants;
import com.duanrong.business.invest.dao.InvestDao;
import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.invest.model.InvestRedpacket;
import com.duanrong.business.loan.LoanConstants;
import com.duanrong.business.loan.dao.LoanDao;
import com.duanrong.business.loan.model.Loan;
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
import com.duanrong.newadmin.utility.ArithUtil;
import com.duanrong.newadmin.utility.DateUtil;
import com.duanrong.newadmin.utility.Dom4jUtil;
import com.duanrong.newadmin.utility.LoadConstantProterties2;
import com.duanrong.util.IdGenerator;
import com.duanrong.util.InterestUtil;
import com.duanrong.util.client.DRHTTPSClient;
import com.duanrong.util.jedis.DRJedisMQ;
import com.duanrong.util.json.FastJsonUtil;
import com.duanrong.yeepay.service.TrusteeshipGiveMoneyToBorrowerService;
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
	UserAccountService userAccountService;

	@Resource
	TrusteeshipOperationService trusteeshipOperationService;

	@Resource
	PlatformAccountService platformAccountService;

	@Resource
	UserDao userDao;

	@Resource
	SystemBillService systemBillService;

	@Resource
	SmsService smsService;

	@Resource
	InformationService informationService;

	@Resource
	RedPacketDao redPacketDao;

	@Override
	public String giveMoneyToBorrower(String userId, String loanId)
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
		return giveMoneyToBorrowerFromInvest(loan, invests, userId);		
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
				+ TrusteeshipYeepayConstants.OperationType.COMPLETE_TRANSACTION_INVEST
				+ "</notifyUrl>");
		content.append("</request>");
		return content.toString();
	}

	private String giveMoneyToBorrowerFromInvest(Loan loan,
			List<Invest> invests, String userId) throws Exception {
		StringBuffer sb = new StringBuffer();
		double money = 0D;// 本次放款金额
		double managementExpense = 0D; // 本次放款勾出管理费
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
			loanDao.update(loan);
		}

		// 对每一个投资记录放款
		for (Invest invest : invests) {
			if (invest.getStatus().equals("投标成功")) {

				String xml = "";
				try {
					xml = createXMLRequestData(invest.getId());// 放款xml数据
				} catch (Exception e) {
					log.errLog("自动放款失败：investID：" + invest.getId()
							+ ", loanId:" + loan.getId(), e);
					sb.append("[自动放款失败：investID：" + invest.getId()
							+ ", loanId:" + loan.getId() + e.getMessage() + "]");
					continue;
				}
				String sign = "";
				try {
					sign = CFCASignUtil.sign(xml);
				} catch (Exception e) {
					log.errLog("自动放款失败：investID：" + invest.getId()
							+ ", loanId:" + loan.getId(), e);
					sb.append("[自动放款失败：investID：" + invest.getId()
							+ ", loanId:" + loan.getId() + e.getMessage() + "]");
					continue;
				}
				TrusteeshipOperation to = trusteeshipOperationService.get(null,
						invest.getId(), invest.getId(), "yeepay");
				if(to == null) to = new TrusteeshipOperation();
				to.setRequestData(to.getRequestData() + "\nsign=" + sign
						+ ",req=" + xml);
				List<NameValuePair> params = new ArrayList<>();
				params.add(new BasicNameValuePair("req", xml));
				params.add(new BasicNameValuePair("sign", sign));
				params.add(new BasicNameValuePair("service",
						"COMPLETE_TRANSACTION"));
				String responseBody = "";
				try {
					responseBody = DRHTTPSClient
							.sendHTTPRequestPostToString(
									TrusteeshipYeepayConstants.RequestUrl.RequestDirectUrl,
									new BasicHeader[] {}, params);
				} catch (Exception e) {
					log.errLog("自动放款失败：investID：" + invest.getId()
							+ ", loanId:" + loan.getId(), e);
					sb.append("[自动放款失败：investID：" + invest.getId()
							+ ", loanId:" + loan.getId() + e.getMessage() + "]");
					continue;
				}
				try {
					@SuppressWarnings("unchecked")
					Map<String, String> respMap = Dom4jUtil
							.xmltoMap(responseBody);
					String code = respMap.get("code");
					String description = respMap.get("description");
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

								// cps推送消息
								try {
									Map<String, Object> hash = new HashMap<>();
									hash.put("investId", invest.getId());
									hash.put("loanId", loan.getId());
									hash.put("pushTime", new Date());
									DRJedisMQ.product("pushinvest",
											FastJsonUtil.objToJson(hash));
								} catch (Exception e) {
									log.errLog("自动放款", e);
								}

							} catch (Exception e) {
								sb.append("[自动放款失败：investID：" + invest.getId()
										+ ", loanId:" + loan.getId()
										+ e.getMessage() + "]");
								log.errLog("投标放款", e);
							}
						}
					} else {
						to.setStatus(TrusteeshipYeepayConstants.Status.REFUSED);
						trusteeshipOperationService.update(to);
						sb.append("[自动放款失败：investID：" + invest.getId()
								+ ", loanId:" + loan.getId() + ", yeepay返回值：+ "
								+ code + "(" + description + ")]");
					}
				} catch (Exception e) {
					log.errLog("投标放款,loanId:" + loan.getId() + ", investId:"
							+ invest.getId() + "放款人:" + userId, e);
					sb.append("[自动放款失败：investID：" + invest.getId()
							+ ", loanId:" + loan.getId() + e.getMessage() + "]");

				}
			}
		}
		// 根据借款期限产生还款信息
		try {

			// 所有的投资记录全部转账成功则生成还夸计划，修改项目状态
			if (investDao.getInvestSeccessByLoanId(loan.getId()) == 0) {
				loan.setInvests(invests);
				repayService.generateRepay(loan);
				loan.setStatus("还款中");
			}
			userAccountService.transferIn(loan.getBorrowMoneyUserID(), money,
					BusinessEnum.give_money_to_borrower,
					"借款项目:" + loan.getName() + ",已经到账!",
					"借款ID：" + loan.getId(), loan.getId());
			if (managementExpense > 0) {
				userAccountService.transferOut(loan.getBorrowMoneyUserID(),
						managementExpense, BusinessEnum.give_money_to_borrower,
						"借款成功，取出借款管理费", "借款ID：" + loan.getId(), loan.getId());
				platformAccountService.transferIn(managementExpense, BusinessEnum.give_money_to_borrower, "借款管理费", loan.getId());
			}
			money = ArithUtil.sub(money, managementExpense);
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
			if (sb != null && sb.toString().trim().length() > 0) {
				return sb.toString();
			} else {
				return "放款成功";
			}
		} catch (Exception e) {
			log.errLog("放款异常", e);
			return "放款异常" + e.getMessage();
		}
	}

	/**
	 * 
	 * @description 放款成功后本地处理
	 * @author 孙铮
	 * @time 2015-3-20 下午3:25:56
	 * @param id
	 */
	@Transactional
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
				userAccountService.tofreeze(invest.getInvestUserID(),
						invest.getMoney(), BusinessEnum.give_money_to_borrower,
						"投资" + loan.getName() + "成功", "借款ID：" + loan.getId(),
						loan.getId());
				informationService.sendInformation(invest.getInvestUserID(),
						"项目放款通知", "您好：您"+sdf.format(invest.getTime()	)+"投资的"+loan.getName()+"项目，金额"+ArithUtil.round(invest.getMoney(), 2)+"元，已经放款给借款人，感谢您的投资。");
			} catch (Exception e) {
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
	}

	/**
	 * 生成加息券金额
	 * 
	 * @param id
	 */
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
}
