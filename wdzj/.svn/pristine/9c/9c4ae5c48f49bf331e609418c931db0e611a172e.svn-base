package com.duanrong.thirdPartyInterface.util;

import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;

import base.model.Invest;
import base.model.Loan;

import com.duanrong.thirdPartyInterface.model.WDTYInvest;
import com.duanrong.thirdPartyInterface.model.WDTYLoan;
import com.duanrong.util.MD5Encry;

public class LoanUtil {
	/**
	 * 转换Loan为网贷天眼使用的Loan
	 * 
	 * @author:yinxunzhi
	 * @time:2014-11-13上午11:29:44
	 * @param loan
	 * @return
	 */
	public static WDTYLoan convertToWDTYLoan(Loan loan) {
		WDTYLoan wdtyLoan = new WDTYLoan();
		wdtyLoan.setId(loan.getId());
		wdtyLoan.setPlatform_name("短融网");
		wdtyLoan.setUsername(MD5Encry.Encry(loan.getId()));
		wdtyLoan.setUserid(MD5Encry.Encry(loan.getId()));
		wdtyLoan.setUrl("http://www.duanrong.com/loan/" + loan.getId());
		wdtyLoan.setTitle(loan.getName());
		wdtyLoan.setStatus(LoanUtil.covertLoanStatus(loan.getStatus()));
		wdtyLoan.setC_type(2);
		wdtyLoan.setAmount(loan.getTotalmoney());
		wdtyLoan.setRate(loan.getRate());
		if ("天".equals(loan.getOperationType())) {
			wdtyLoan.setP_type(0);
			Integer day = loan.getDay();
			if (day == null) {
				day = 0;
			}
			Integer symbol = loan.getSymbol();
			if (symbol == null) {
				symbol = 0;
			}
			day = day + symbol;
			wdtyLoan.setPeriod(day);
		} else {
			wdtyLoan.setP_type(1);
			wdtyLoan.setPeriod(loan.getDeadline());
		}

		wdtyLoan.setPay_way(convertLoanType(loan.getRepayType(),
				loan.getOperationType()));
		if (loan.getAwardMoneyRate() != null) {
			wdtyLoan.setReward(loan.getAwardMoneyRate());
		} else {
			wdtyLoan.setReward(0);
		}
		wdtyLoan.setGuarantee(0);
		wdtyLoan.setStart_time(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
				.format(loan.getCommitTime()));
		if (loan.getGiveMoneyTime() != null) {
			wdtyLoan.setEnd_time(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
					.format(loan.getGiveMoneyOperationTime()));
		}
		wdtyLoan.setC_reward(0);
		return wdtyLoan;
	}

	/**
	 * 转换借款款类型
	 * 
	 * @author:yinxunzhi
	 * @time:2014-11-13上午10:27:02
	 * @param repayType
	 * @param operationType
	 * @return
	 */
	private static int convertLoanType(String repayType, String operationType) {
		if ("按月付息到期还本".equals(repayType)) {
			return 2;
		} else if ("一次性到期还本付息".equals(repayType)) {
			if ("天" == operationType) {
				return 3;
			} else if ("月".equals(operationType)) {
				return 4;
			} else {
				return 6;
			}
		} else {
			return 6;
		}
	}

	public static int covertLoanStatus(String loanStatus) {
		int status = 0;
		if ("筹款中".equals(loanStatus) || "贷前公告".equals(loanStatus)) {
			status = 0;
		} else if ("还款中".equals(loanStatus) || "完成".equals(loanStatus)
				|| "等待复核".equals(loanStatus)) {
			status = 1;
		}
		return status;
	}

	/**
	 * 转换投资
	 * 
	 * @author:yinxunzhi
	 * @time:2014-11-25上午11:24:12
	 * @param invest
	 * @return
	 */
	public static WDTYInvest convertToWDTYInvest(Invest invest) {
		WDTYInvest wdtyInvest = new WDTYInvest();
		wdtyInvest.setId(invest.getLoanId());
		wdtyInvest.setMoney(invest.getMoney());
		wdtyInvest.setAccount(invest.getMoney());
		if (!invest.getIsAutoInvest()) {
			wdtyInvest.setType("手动");
		} else {
			wdtyInvest.setType("自动");
		}
		wdtyInvest
				.setLink("http://www.duanrong.com/loan/" + invest.getLoanId());
		wdtyInvest.setUseraddress(null);
		wdtyInvest.setAdd_time(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
				.format(invest.getTime()));
		wdtyInvest.setUserid(MD5Encry.Encry(invest.getInvestUserID()));
		wdtyInvest.setStatus(invest.getStatus());
		wdtyInvest.setUsername(MD5Encry.Encry(invest.getInvestUserID()));
		return wdtyInvest;
	}
}
