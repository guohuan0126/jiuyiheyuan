package com.duanrong.thirdPartyInterface.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import base.dao.BaseDao;
import base.model.Invest;
import base.model.Loan;

import com.duanrong.thirdPartyInterface.model.WDZJInvest;
import com.duanrong.thirdPartyInterface.model.WDZJLoan;
import com.duanrong.thirdPartyInterface.service.WDZJService;
import com.duanrong.util.ArithUtil;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2014-11-12 上午10:49:09
 * @Description : template WDZJServiceImpl WDZJServiceImpl.java
 * 
 */
@Service
public class WDZJServiceImpl implements WDZJService {

	@Resource
	BaseDao<Loan> baseDaoLoan;

	@Resource
	BaseDao<Invest> baseDaoInvest;

	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = Exception.class)
	public List<WDZJLoan> getProjects(Loan loan) {
		baseDaoLoan
				.setMapperNameSpace("com.duanrong.thirdPartyInterface.mapper.LoanMapper.");
		List<Loan> loans = baseDaoLoan.find(loan);
		if (loans == null || loans.size() == 0) {
			return null;
		}
		List<WDZJLoan> wdzjLoans = new ArrayList<WDZJLoan>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (Loan l : loans) {
			WDZJLoan wdzjLoan = new WDZJLoan();
			// 项目主键(唯一)
			wdzjLoan.setProjectId(l.getId());
			// 标题
			wdzjLoan.setTitle(l.getName());
			// 借款金额
			wdzjLoan.setAmount(l.getTotalmoney());
			// 投资人数据
			Object[] objArray = getInvestsByLoanID(l.getId());
			wdzjLoan.setSubscribes((List<WDZJInvest>) objArray[0]);
			// 进度
			Double calculateMoneyNeedRaised = l.getCalculateMoneyNeedRaised();
			if (calculateMoneyNeedRaised == null) {
				calculateMoneyNeedRaised = 0D;
			}
			double calculateRaiseCompletedRate = calculateRaiseCompletedRate(l,
					(Double) objArray[1]);
			wdzjLoan.setSchedule(calculateRaiseCompletedRate + "%");
			// 利率
			wdzjLoan.setInterestRate(l.getRate().toString());
			// 借款期限
			wdzjLoan.setDeadline(l.getDeadline());
			// 期限单位
			wdzjLoan.setDeadlineUnit(l.getOperationType());
			Date giveMoneyTime = l.getGiveMoneyOperationTime();
			if (giveMoneyTime != null) {
				wdzjLoan.setSuccessTime(sdf.format(giveMoneyTime));
			}
			// 奖励
			Double awardMoneyRate = l.getAwardMoneyRate();
			if (awardMoneyRate != null) {
				double sum = ArithUtil.add(awardMoneyRate, l.getRate());
				wdzjLoan.setInterestRate(Double.toString(sum));
			} 
			wdzjLoan.setReward(0D);
			
			// 标的类型
			wdzjLoan.setType(l.getLoanType());
			if ("按月付息到期还本".equals(l.getRepayType())) {
				wdzjLoan.setRepaymentType(5);
			} else if ("一次性到期还本付息".equals(l.getRepayType())) {
				wdzjLoan.setRepaymentType(1);
			}
			// 发标人ID
			wdzjLoan.setUserName(l.getBorrowMoneyUserID());
			wdzjLoan.setLoanUrl("http://www.duanrong.com/loan/" + l.getId());
			wdzjLoans.add(wdzjLoan);
		}
		return wdzjLoans;
	}

	public Object[] getInvestsByLoanID(String loanID) {
		baseDaoInvest
				.setMapperNameSpace("com.duanrong.thirdPartyInterface.mapper.InvestMapper.");
		List<Invest> invests = baseDaoInvest.getInvestsByLoanID(loanID);
		Object[] obj = new Object[2];
		List<WDZJInvest> wdzjInvests = new ArrayList<WDZJInvest>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		double totalMoney = 0D;
		for (Invest invest : invests) {
			WDZJInvest wdzjInvest = new WDZJInvest();
			wdzjInvest.setAddDate(sdf.format(invest.getTime()));
			wdzjInvest.setAmount(invest.getMoney());
			wdzjInvest.setStatus(1);
			wdzjInvest.setSubscribeUserName(invest.getInvestUserID());
			Boolean isAutoInvest = invest.getIsAutoInvest();
			if (isAutoInvest) {
				wdzjInvest.setType(1);
			} else {
				wdzjInvest.setType(0);
			}
			totalMoney += invest.getMoney();
			wdzjInvest.setValidAmount(invest.getMoney());
			wdzjInvests.add(wdzjInvest);
		}
		obj[0] = wdzjInvests;
		obj[1] = totalMoney;
		return obj;
	}

	/**
	 * 投资进度(页面显示投资进度 百分比)
	 */
	public double calculateRaiseCompletedRate(Loan loan, Double money) {
		// double remainMoney = calculateMoneyNeedRaised2(loan, money);
		double loanMoney = loan.getTotalmoney();
		double round = ArithUtil.round(money / loanMoney * 100, 2);
		return round;
	}

	/**
	 * 计算项目剩余金额
	 */
	public double calculateMoneyNeedRaised2(Loan loan, Double money) {
		double remain = ArithUtil.sub(loan.getTotalmoney(), money);
		return remain < 0D ? 0D : remain;
	}
}
