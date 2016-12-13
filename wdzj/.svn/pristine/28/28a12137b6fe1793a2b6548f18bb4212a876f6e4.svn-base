package com.duanrong.thirdPartyInterface.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import base.dao.BaseDao;
import base.model.Invest;
import base.model.Loan;

import com.duanrong.thirdPartyInterface.model.YHBYLoan;
import com.duanrong.thirdPartyInterface.service.YHBYService;
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
public class YHBYServiceImpl implements YHBYService {

	@Resource
	BaseDao<Loan> baseDaoLoan;

	@Resource
	BaseDao<Invest> baseDaoInvest;

	@Transactional(rollbackFor = Exception.class)
	public List<YHBYLoan> getProjects() {
		baseDaoLoan
				.setMapperNameSpace("com.duanrong.thirdPartyInterface.mapper.LoanMapper.");
		List<Loan> loans = baseDaoLoan.findAllByYHBY();
		if (loans == null || loans.size() == 0) {
			return null;
		}
		List<YHBYLoan> yhbyLoans = new ArrayList<YHBYLoan>();
		for (Loan loan : loans) {
			YHBYLoan yhbyLoan = new YHBYLoan();
			yhbyLoan.setCompanyLogo("http://demoa2.duanrong.net/images/Public/Logo.png");
			yhbyLoan.setCompanyName("短融网");
			String investmentPeriod = null;
			if ("月".equals(loan.getOperationType())) {
				investmentPeriod = loan.getDeadline().toString() + "个月";
			} else if ("天".equals(loan.getOperationType())) {
				if ("是".equals(loan.getBeforeRepay())) {
					investmentPeriod = loan.getDay() + loan.getSymbol() + "天";
				} else {
					investmentPeriod = loan.getDay() + "天";
				}
			}
			yhbyLoan.setInvestmentPeriod(investmentPeriod);
			yhbyLoan.setLoanMoney(loan.getTotalmoney().toString());
			yhbyLoan.setLoanpurpose("无");
			Double calculateMoneyNeedRaised = loan
					.getCalculateMoneyNeedRaised();
			if (calculateMoneyNeedRaised == null) {
				calculateMoneyNeedRaised = 0D;
			}
			Double calculateRaiseCompletedRate = calculateRaiseCompletedRate(
					loan, calculateMoneyNeedRaised);
			yhbyLoan.setProgress(calculateRaiseCompletedRate + "%");
			yhbyLoan.setProjectName(loan.getName());
			String loanType = null;
			if ("房贷".equals(loan.getLoanType())) {
				loanType = "house";
			} else if ("车贷".equals(loan.getLoanType())) {
				loanType = "vehicel";
			} else if ("企业贷".equals(loan.getLoanType())) {
				loanType = "enterprise";
			}
			yhbyLoan.setProjectUrl("http://demoa2.duanrong.net/loanDetail/"
					+ loanType + "?loanId=" + loan.getId() + "&all=a");
			yhbyLoan.setRepayModel(loan.getRepayType());
			yhbyLoan.setStartInvestmentMoney(loan.getInvestOriginMoney()
					.toString());
			Double rate = ArithUtil.round(loan.getRate() * 100, 0);
			yhbyLoan.setYearRate(rate.toString());
			yhbyLoans.add(yhbyLoan);
		}
		return yhbyLoans;
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
