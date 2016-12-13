package com.duanrong.business.autoinvest.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpException;
import org.springframework.stereotype.Service;

import util.Log;
import base.exception.InsufficientBalance;
import base.pagehelper.PageInfo;

import com.duanrong.business.account.model.UserAccount;
import com.duanrong.business.account.service.UserAccountService;
import com.duanrong.business.autoinvest.dao.AutoInvestDao;
import com.duanrong.business.autoinvest.model.AutoInvest;
import com.duanrong.business.autoinvest.service.AutoInvestService;
import com.duanrong.business.invest.InvestConstants;
import com.duanrong.business.invest.dao.InvestDao;
import com.duanrong.business.invest.service.InvestService;
import com.duanrong.business.loan.dao.LoanDao;
import com.duanrong.business.loan.model.Loan;
import com.duanrong.util.ArithUtil;
import com.duanrong.yeepay.service.TrusteeshipAutoInvestService;

@Service
public class AutoInvestServiceImpl implements AutoInvestService {

	@Resource
	AutoInvestDao autoInvestDao;

	@Resource
	LoanDao loanDao;

	@Resource
	InvestDao investDao;

	@Resource
	Log log;

	@Resource
	UserAccountService userAccountService;

	@Resource
	TrusteeshipAutoInvestService trusteeshipAutoInvestService;

	@Resource
	InvestService invesrService;

	@Override
	public void insert(AutoInvest autoInvest) {
		autoInvestDao.insert(autoInvest);
	}

	@Override
	public void settingAutoInvest(AutoInvest autoInvest) {

		AutoInvest tempAI = autoInvestDao.get(autoInvest.getUserId());

		if (tempAI != null) {

			// FIXME：是否有问题？
			autoInvest.setLastAutoInvestTime(new Date());
			autoInvest.setStatus(InvestConstants.AutoInvest.Status.OFF);
			autoInvestDao.update(autoInvest);
		} else {
			autoInvest.setLastAutoInvestTime(new Date());
			autoInvestDao.insert(autoInvest);
		}

	}

	@Override
	public AutoInvest read(String id) {
		return autoInvestDao.get(id);
	}

	@Override
	public void update(AutoInvest autoInvest) {
		autoInvestDao.update(autoInvest);
	}

	@Override
	public Long readQueueNumber(String userId) {
		return autoInvestDao.getQueueNumber(userId);
	}

	@Override
	public Long readAutoInvestConut() {
		return autoInvestDao.getAutoInvestConut();
	}

	@Override
	public PageInfo<AutoInvest>  readPageInfo(int pageNo, int pageSize,
			AutoInvest autoInvest) {
		return autoInvestDao.pageLite(pageNo, pageSize, autoInvest);
	}

	@Override
	public synchronized void autoInvest(String loanId) {

		Loan loan = loanDao.get(loanId);
		/*
		 * if(investDao.getAutoInvestByLoan(loanId) > 0){ log.infoLog("自动投标关闭",
		 * "自动投标项目："+loanId+"已开启过自动投标，不能重复开启"); return ; }
		 */
		List<AutoInvest> autoInvests = getConsistAutoInvest(loan);

		log.infoLog("自动投标开启",
				"自动投标项目：" + loanId + ",开启自动投标用户数量：" + autoInvests.size());
		// 自动投标金额等于0, 不能自动投标
		if (loan.getAutoInvestMoneyTotal() == 0) {
			log.infoLog("自动投标关闭", "自动投标项目：" + loanId + "自动投标上限金额为0,自动投标已关闭");
			return;
		}
		/*boolean flag = false;
		try {
			flag = DateUtil.calculateIntervalDays(new SimpleDateFormat("yyyy-MM-dd").parse("2016-06-14"),
					loan.getCommitTime()) > 0;
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		// 累计自动投标金额
		double autoInvestedTotalMoney = invesrService
				.readAutoInvestMoneyByLoanId(loanId);
		for (AutoInvest autoInvest : autoInvests) {
			log.infoLog("自动投标", "用户：" + autoInvest.getUserId() + "开启自动投标");
			// 用户实际自动投资金额
			double investMoney = ArithUtil.round(
					getFinalInvestMoney(autoInvest, loan,
							autoInvestedTotalMoney), 2);
			autoInvest.setAutoInvest(true);
			if (autoInvestedTotalMoney >= loan.getAutoInvestMoneyTotal()) {
				return;
			}
			log.infoLog("自动投标", "用户：" + autoInvest.getUserId() + "用户实际投资金额："
					+ investMoney + "元");
			// 用户
			if (investMoney == -1) {
				continue;
			}
			if (investMoney == 0) {
				log.infoLog("发起自动投标", "用户ID" + autoInvest.getUserId()
						+ "investMoney小于最低投资金额" + investMoney);
				autoInvest.setLastAutoInvestTime(new Date());
				autoInvestDao.update(autoInvest);
				continue;
			} else {
				log.infoLog("发起自动投标", "用户ID"
						+ autoInvest.getUserId() + "自动投标金额investMoney="
						+ investMoney + "自动投标已投金额autoInvestedTotalMoney="
						+ autoInvestedTotalMoney);
			}
			try {
				/*if(flag){*/
					//自动投标 2.0
					trusteeshipAutoInvestService.autoInvestV2(autoInvest, loan,
							investMoney);
				/*}else{
					trusteeshipAutoInvestService.autoInvest(autoInvest, loan,
							investMoney);
				}*/
				autoInvest.setLastAutoInvestTime(new Date());
				autoInvestDao.update(autoInvest);
				autoInvestedTotalMoney += investMoney;
				
				log.infoLog("自动投标", "用户ID"+autoInvest.getUserId()+"自动投标"+investMoney+"元，已投资成功，项目自动投标已投："+autoInvestedTotalMoney+"元");
			} catch (HttpException e) {
				log.errLog("开始自动投标错误.用户",
						autoInvest.getUserId() + "投资ID" + loan.getId()
								+ "reason:HttpException." + e.getMessage());
				continue;
			} catch (InsufficientBalance e) {
				log.errLog("开始自动投标错误.用户", autoInvest.getUserId() + "投资ID"
						+ loan.getId() + "reason:余额不足.");
				continue;
			} catch (IOException e) {
				log.errLog("开始自动投标错误.用户", autoInvest.getUserId() + "投资ID"
						+ loan.getId() + "reason:IOException.");
				continue;
			} catch (Exception e) {
				log.errLog("开始自动投标错误.用户", autoInvest.getUserId() + "投资ID"
						+ loan.getId() + "reason:IOException.");
			}

		}
	}

	@Override
	public void autoInvest(String loanId, String userId, double investMoney,
			String isAutoInvest) {
		Loan loan = loanDao.get(loanId);

		/*boolean flag = false;
		try {
			flag = DateUtil.calculateIntervalDays(new SimpleDateFormat("yyyy-MM-dd").parse("2016-06-14"),
					loan.getCommitTime()) > 0;
		} catch (ParseException e) {
			log.errLog("自动投标错误", e);
		}*/
		
		AutoInvest autoInvest = autoInvestDao.get(userId);
		if (autoInvest == null || autoInvest.getStatus().equals("off")) {
			log.infoLog("发起自动投标", "用户ID" + userId + "未开启自动投标");
			return;
		}

		if (isAutoInvest != null && isAutoInvest.equals("1")) {
			autoInvest.setAutoInvest(true);
		} else {
			autoInvest.setAutoInvest(false);
		}

		// 用户
		if (investMoney <= 0) {
			log.infoLog("发起自动投标", "用户ID" + autoInvest.getUserId()
					+ "investMoney小于最低投资金额" + investMoney);
			autoInvest.setLastAutoInvestTime(new Date());
			autoInvestDao.update(autoInvest);
		} else {
			log.infoLog("发起自动投标", "用户ID" + autoInvest.getUserId()
					+ "自动投标金额investMoney=" + investMoney);
		}
		try {
			/*if(flag){*/
				//自动投标 2.0
				trusteeshipAutoInvestService.autoInvestV2(autoInvest, loan,
						investMoney);
			/*}else{
				trusteeshipAutoInvestService.autoInvest(autoInvest, loan,
						investMoney);
			}*/
			autoInvest.setLastAutoInvestTime(new Date());
			autoInvestDao.update(autoInvest);
		} catch (HttpException e) {
			log.errLog("开始自动投标错误.用户",
					autoInvest.getUserId() + "投资ID" + loan.getId()
							+ "reason:HttpException." + e.getMessage());

		} catch (InsufficientBalance e) {
			log.errLog("开始自动投标错误.用户",
					autoInvest.getUserId() + "投资ID" + loan.getId()
							+ "reason:余额不足.");

		} catch (IOException e) {
			log.errLog("开始自动投标错误.用户",
					autoInvest.getUserId() + "投资ID" + loan.getId()
							+ "reason:IOException.");

		} catch (Exception e) {
			log.errLog("开始自动投标错误.用户",
					autoInvest.getUserId() + "投资ID" + loan.getId()
							+ "reason:IOException.");
		}

	}

	/**
	 * 获取符合项目条件的自动投标用户
	 * 
	 * @param loanId
	 * @return
	 */
	private List<AutoInvest> getConsistAutoInvest(Loan loan) {
		if (null != loan) {
			Map<String, Object> map = new HashMap<>();
			map.put("rate", loan.getRate());
			map.put("line", loan.getDeadline());
			map.put("type", loan.getLoanType());
			map.put("investMoney", loan.getInvestOriginMoney());
			map.put("loanUserId", loan.getBorrowMoneyUserID());
			return autoInvestDao.getConsistAutoInvest(map);
		}
		return new ArrayList<AutoInvest>();
	}

	/**
	 * 根据自动投标设置条件和项目内容设置最终自动投标金额
	 * 
	 * @author:xiao
	 * @param autoInvest
	 * @param loan
	 * @return
	 */
	private double getFinalInvestMoney(AutoInvest autoInvest, Loan loan,
			double autoInvestedTotalMoney) {

		try {
			StringBuffer logsb = new StringBuffer();
			// 自动投标金额
			double investMoney = autoInvest.getInvestMoney();
			logsb.append("用户设置自动投标金额 ： " + investMoney);

			// 项目最大投资金额
			double maxInvestMoney = loan.getMaxInvestMoney() == null ? 0D
					: loan.getMaxInvestMoney();

			// 项目起始投资金额
			double minInvestMoney = loan.getInvestOriginMoney() == null ? 100D
					: loan.getInvestOriginMoney();
			// 项目投资递增金额
			double ascInvestMoney = loan.getIncreaseMoney() == null ? 100D
					: loan.getIncreaseMoney();

			// 用户可用金额
			 UserAccount userAccount = userAccountService.readUserAccount(autoInvest.getUserId());
			 Double balance = userAccount == null ? 0 : userAccount.getAvailableBalance();
			 logsb.append("用户可用余额 ： " + balance);

			// 1.判断自动投标金额是否大于项目设置的最大投资金额(如果设置)，大于重新赋值为项目最大投资金额，小于跳过
			if (maxInvestMoney > 0 && investMoney > maxInvestMoney) {
				investMoney = maxInvestMoney;
				logsb.append("自动投标金额大于项目最大投资金额(" + maxInvestMoney + ")="
						+ investMoney);
			}

			// 2.判断是否大于项目设置的可以自动投标金额的剩余金额
			if (loan.getAutoInvestMoneyTotal() != null
					&& loan.getAutoInvestMoneyTotal() > 0
					&& ArithUtil.sub(investMoney, ArithUtil.sub(
							loan.getAutoInvestMoneyTotal(),
							autoInvestedTotalMoney)) >= 0) {
				investMoney = ArithUtil.sub(loan.getAutoInvestMoneyTotal(),
						autoInvestedTotalMoney);
				logsb.append("自动投标金额大于项目自动投标剩余金额,项目自动投标金额"
						+ loan.getAutoInvestMoneyTotal() + ") and 自动投标已投("
						+ autoInvestedTotalMoney + ")=" + investMoney);
			}
			// 3.判断是否大于项目金额的50%,大于重新赋值,不大于跳过
			if (ArithUtil.sub(investMoney, loan.getTotalmoney() * 0.5) > 0) {
				investMoney = loan.getTotalmoney() * 0.5;
				logsb.append("自动投标金额大于项目投资金额的50%(" + loan.getTotalmoney()
						+ ") * 0.5 = " + investMoney);
			}
			// 4.判断投资金额是否大于用户设置的最小投资金额
			if (investMoney < autoInvest.getMinMoney()) {
				logsb.append("，用户最终投资金额("+investMoney+"元),小于用户设置的最小投资金额("+autoInvest.getMinMoney()+"元)");
				return -1;
			}

			if (balance < minInvestMoney || balance < autoInvest.getMinMoney()) {
				logsb.append("，账户余额("+balance+"元),小于用户设置的最小投资金额("+autoInvest.getMinMoney()+"元)或者小于项目起始投资金额("+minInvestMoney+"元)");
				return 0D;
			}
			// 4.判断是否大于账户余额,符合条件赋值为账户余额,小于跳过
			if (investMoney > balance) {
				investMoney = balance;
				logsb.append("自动投标金额大于账户余额(" + balance + ") = " + investMoney);
			}
			// 5. 取整
			if ((investMoney - minInvestMoney) % ascInvestMoney != 0) {
				investMoney = Math.floor((investMoney - minInvestMoney)
						/ ascInvestMoney)
						* ascInvestMoney + minInvestMoney;
				logsb.append("投资金额取整(" + ascInvestMoney + ") = " + investMoney);
			}
			log.infoLog("自动投标", "userId:" + autoInvest.getUserId() + "自动投标金额:"+investMoney + logsb.toString());
			return investMoney;
		} catch (Exception e) {
			log.errLog("Class AutoInvestServiceImpl.getFinalInvestMoney",
					e.getMessage());
			return -1;
		}
	}

}