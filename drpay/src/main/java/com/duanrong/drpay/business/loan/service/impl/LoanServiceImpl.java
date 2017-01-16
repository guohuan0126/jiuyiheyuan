package com.duanrong.drpay.business.loan.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import util.ArithUtil;
import util.DateUtil;
import util.FastJsonUtil;
import util.Log;
import util.SmsHttpClient;
import base.error.ErrorCode;
import base.exception.ErrorCodeException;
import base.exception.TradeException;

import com.duanrong.drpay.business.account.service.UserAccountService;
import com.duanrong.drpay.business.invest.InvestConstants;
import com.duanrong.drpay.business.invest.dao.InvestDao;
import com.duanrong.drpay.business.invest.model.Invest;
import com.duanrong.drpay.business.invest.model.InvestRedpacket;
import com.duanrong.drpay.business.invest.service.InvestService;
import com.duanrong.drpay.business.loan.LoanConstants;
import com.duanrong.drpay.business.loan.dao.LoanDao;
import com.duanrong.drpay.business.loan.model.Loan;
import com.duanrong.drpay.business.loan.model.SubLoan;
import com.duanrong.drpay.business.loan.service.LoanService;
import com.duanrong.drpay.business.repay.dao.RepayDao;
import com.duanrong.drpay.business.user.dao.RedPacketDao;
import com.duanrong.drpay.business.user.model.RedPacket;
import com.duanrong.drpay.business.user.model.User;
import com.duanrong.drpay.business.user.service.UserService;
import com.duanrong.drpay.config.BusinessEnum;
import com.duanrong.drpay.config.ConfigConstant;
import com.duanrong.drpay.config.IdUtil;
import com.duanrong.util.InterestUtil;
import com.duanrong.util.jedis.DRJedisMQ;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2014-8-28 下午12:34:09
 * @Description : drsoa com.duanrong.business.loan.service.impl
 *              LoanServiceImpl.java
 * 
 */
@Service
public class LoanServiceImpl implements LoanService {

	@Resource
	LoanDao loanDao;

	@Resource
	InvestDao investDao;

	@Resource
	InvestService investService;

	@Resource
	RepayDao repayDao;

	@Resource
	RedPacketDao redPacketDao;

	@Resource
	UserAccountService userAccountService;

	@Resource
	UserService userService;

	@Resource
	SmsHttpClient smsHttpClient;

	@Resource
	Log log;

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 
	 * @description 根据id查询单条借款记录
	 * @author 孙铮
	 * @time 2014-8-28 下午12:37:14
	 * @param id
	 * @return
	 */
	@Override
	public Loan get(String id) {
		return loanDao.get(id);
	}

	@Override
	public List<SubLoan> getVehicle(String loanId) {
		return loanDao.getVehicle(loanId);
	}

	@Override
	public List<SubLoan> getAgricultureForkLoans(String loanId) {
		return loanDao.getAgricultureForkLoans(loanId);
	}

	@Override
	public List<SubLoan> getAgricultureByGroupCondition(String loanId, int drpayStatus) {
		return loanDao.getAgricultureByGroupCondition(loanId, drpayStatus);
	}

	@Override
	public List<SubLoan> getVehicleByGroupCondition(String loanId, int drpayStatus) {
		return loanDao.getVehicleByGroupCondition(loanId, drpayStatus);
	}

	@Override
	public void updateBatchVehicle(List<SubLoan> subloans) {
		loanDao.updateBatchVehicle(subloans);
	}

	@Override
	public void updateBatchAgricultureForkLoans(List<SubLoan> subloans) {
		loanDao.updateBatchAgricultureForkLoans(subloans);
	}

	/**
	 * 
	 * @description 根据组合条件查询所有借款记录
	 * @author 孙铮
	 * @time 2014-8-28 下午5:29:29
	 * @param userID
	 * @return
	 */
	@Override
	public List<Loan> getLoansByGroupCondition(Loan loan) {
		if (loan == null) {
			return null;
		} else {
			return loanDao.getLoansByGroupCondition(loan);
		}
	}

	@Override
	public void update(Loan loan) {
		loanDao.update(loan);
	}

	@Override
	public void dealRaiseComplete(String loanId) throws ErrorCodeException {
		if (calculateMoneyNeedRaised(loanId) <= 0) {
			// 项目募集完成
			Loan loan = get(loanId);
			if (StringUtils.equals(loan.getStatus(),
					LoanConstants.LoanStatus.RAISING)) {
				loan.setStatus(LoanConstants.LoanStatus.RECHECK);
				update(loan);
			}
		}
	}

	@Override
	public double calculateMoneyNeedRaised(String loanId) {
		Loan loan = get(loanId);
		if (loan == null) {
			return 0;
		}
		// 统计所有的此借款的投资信息，求和做减法，得出尚未募集到的金额。
		double validSumMoney = investDao.getInvestSumMoneyByLoan(loanId);
		double remain = ArithUtil.sub(loan.getTotalmoney(), validSumMoney);
		return remain < 0 ? 0 : remain;
	}

	/**
	 * 
	 * @description 根据组合条件查询所有借款记录
	 * @author 孙铮
	 * @time 2014-9-1 下午12:02:28
	 * @param invest
	 * @return
	 */
	@Override
	public List<Invest> getInvestsByGroupCondition(Invest invest) {
		if (invest == null) {
			return null;
		} else {
			List<Invest> invests = loanDao.getInvestsByGroupCondition(invest);
			return invests;
		}
	}

	@Override
	public Loan prepare(String loanId) throws TradeException {
		Loan loan = get(loanId);
		if (loan == null) {
			log.errLog("放款失败", "项目loanId: " + loanId + ", 项目不存在", 1);
			throw new TradeException(ErrorCode.LoanNotFind);
		}
		if (!LoanConstants.LoanStatus.RECHECK.equals(loan.getStatus())) {
			log.errLog("放款失败",
					"项目loanId: " + loanId + ", 状态为：" + loan.getStatus(), 1);
			throw new TradeException(ErrorCode.LoanStatusError);

		}
		if (loan.getDrpayStatus() == 0) {
			log.errLog("放款失败", "项目loanId: " + loanId + ", 未在存管注册", 1);
			throw new TradeException(ErrorCode.LoanStatusError);

		}
		if (LoanConstants.LoanType.PROJECT.equals(loan.getStandardOrProject())
				&& (LoanConstants.Type.VEHICLE.equals(loan.getLoanType())
						&& !getVehicleByGroupCondition(loanId, 0).isEmpty() 
						|| LoanConstants.Type.AGRICULTURE.equals(loan.getLoanType())
						&& !this.getAgricultureByGroupCondition(loanId, 0)
								.isEmpty())) {
			log.errLog("放款失败", "理财计划loanId: " + loanId + ", 子标未全部在存管通注册", 1);
			throw new TradeException(ErrorCode.LoanStatusError);
		}
		Invest invest = new Invest();
		invest.setLoanId(loanId);
		invest.setStatus("等待确认");
		List<Invest> invests = investService.getInvestLoan(invest);
		if (!invests.isEmpty()) {
			log.infoLog("放款失败", "项目loanId: " + loanId + ", 有等待确认的投资记录", 1);
			throw new TradeException(ErrorCode.InvestStatusError);
		}
		invest.setStatus("还款中,投标成功");
		invests = investService.getInvestLoan(invest);
		if (invests == null || invests.isEmpty()) {
			log.infoLog("放款失败", "项目loanId: " + loanId + ", 没有有效投资人", 1);
			throw new TradeException(ErrorCode.LoanNoInvestor);
		}
		//只拿未放款的记录
		invest.setStatus("投标成功");
		invests = investService.getInvestLoan(invest);
		double sumMoney = investService.getInvestSumMoneyByLoan(loanId);
		if (sumMoney != loan.getTotalmoney()) {
			log.infoLog("放款失败", "项目loanId: " + loanId
					+ ", 投资总金额不等于借款总金额, investtotalmoney: " + sumMoney
					+ ", loanMoney: " + loan.getTotalmoney(), 1);
			throw new TradeException(ErrorCode.MoneyRaiseNoTotal);
		}
		if (loan.getGiveMoneyOperationTime() == null
				|| loan.getGiveMoneyTime() == null) {
			Date date = new Date();
			// 设置计息日期
			Date addDay = DateUtil.addDay(date, 2);
			loan.setGiveMoneyTime(addDay);
			// 设置放款操作日期
			loan.setGiveMoneyOperationTime(date);
			// 设置项目完成时间
			if (loan.getOperationType().equals("月")) {
				loan.setFinishTime(DateUtil.addMonth(addDay, loan.getDeadline()));
			} else {
				loan.setFinishTime(DateUtil.addDay(date, loan.getDay()));
			}
			this.update(loan);
		}
		loan.setInvests(invests);
		return loan;
	}

	@Override
	public boolean giveMoneyToBorrower(Invest invest) {
		if (InvestConstants.InvestStatus.BID_SUCCESS.equals(invest.getStatus())) {
			Loan loan = invest.getLoan();
			Double rate = loan.getRate();
			Date date = loan.getGiveMoneyOperationTime() == null ? new Date()
					: loan.getGiveMoneyOperationTime();
			try {
				Double money = invest.getMoney();
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
				// 更改投资状态
				invest.setStatus(InvestConstants.InvestStatus.REPAYING);
				investDao.update(invest);
				userAccountService.tofreeze(invest.getInvestUserID(),
						invest.getMoney(), BusinessEnum.give_money_to_borrower,
						"投资" + loan.getName() + "成功", "借款ID：" + loan.getId(),
						loan.getId());
				User user = userService.get(invest.getInvestUserID());
				smsHttpClient.sendSms(
						user.getMobileNumber(),
						DateUtil.getDate(invest.getTime()) + ","
								+ loan.getName() + ","
								+ ArithUtil.round(invest.getMoney(), 2),
						"give_money_to_borrower_to_investor");
				try {
					createPacketInvest(invest);
				} catch (Exception e) {
					log.errLog(
							"放款生成补息奖励金额失败",
							"investId: " + invest.getId() + ", loanId: "
									+ loan.getId()
									+ ExceptionUtils.getMessage(e), 1);
				}

				// cps推送消息
				try {
					Map<String, Object> hash = new HashMap<>();
					hash.put("investId", invest.getId());
					hash.put("loanId", loan.getId());
					hash.put("pushTime", new Date());
					DRJedisMQ.product("pushinvest",
							FastJsonUtil.objToJson(hash));
				} catch (Exception e) {
					log.errLog("cps推送消息--放款", e);
				}
				return true;
			} catch (Exception e) {
				log.errLog(
						"放款单笔处理失败",
						"investId: " + invest.getId() + ", loanId: "
								+ loan.getId() + ExceptionUtils.getMessage(e),
						1);
				return false;
			}
		} else {
			return false;
		}

	}

	/**
	 * 生成补息和加息券奖励金额
	 * 
	 * @param invest
	 */
	private void createPacketInvest(Invest invest) {
		double d = 0;
		double money = invest.getMoney();
		Loan loan = invest.getLoan();
		// 大于0表示该笔投资使用加息券
		if (invest.getRedpacketId() > 0) {
			RedPacket packet = redPacketDao.get(invest.getRedpacketId());
			String type = packet.getType();
			String status = packet.getSendStatus();
			double rate = packet.getRate();
			if (23 == packet.getRuleId()) {// 如果红包规则是23则金额最高计算上限是5万
				money = money > 50000 ? 50000 : money;
			}
			if ("rate".equals(type) && "used".equals(status)) {
				int periods = 0;
				if ("天".equals(loan.getOperationType())) {
					periods = loan.getDay();
				} else {
					periods = loan.getDeadline();
				}
				d = InterestUtil.getInterestByPeriod(money, rate, periods,
						loan.getOperationType(), loan.getRepayType());
			} else if ("money".equals(type) && "used".equals(status)) {
				// 固定金额奖励的情况下
				d = packet.getMoney();
			}
		}
		// 如果补息或者奖励金额大于0,则 创建补息和奖励记录
		if (invest.getInvestAllowanceInterest() > 0 || d > 0) {
			InvestRedpacket ir = new InvestRedpacket();
			ir.setId(IdUtil.randomUUID());
			ir.setInvestId(invest.getId());
			ir.setLoanId(loan.getId());
			ir.setAllowanceOrder(ConfigConstant.SERVERS + "TZBX"
					+ invest.getId());
			ir.setRewardMoney(d);
			ir.setInvestAllowanceInterest(invest.getInvestAllowanceInterest());
			ir.setSendAllowanceStatus(0);
			ir.setRepackedOrder(ConfigConstant.SERVERS + "JXJL"
					+ invest.getId());
			ir.setSendRedpacketStatus(0);
			ir.setRepackedId(invest.getRedpacketId());
			ir.setCreateTime(new Date());
			ir.setUserId(invest.getInvestUserID());
			investDao.insertInvestRedpacket(ir);
		}

	}

	@Override
	public Loan queryWithLock(String id) {
		return loanDao.getWithLock(id);
	}


	@Override
	public void updateVehicle(SubLoan subloan) {
		loanDao.updateVehicle(subloan);		
	}

	@Override
	public void updateAgricultureForkLoans(SubLoan subloan) {
		loanDao.updateAgricultureForkLoans(subloan);		
	}

}
