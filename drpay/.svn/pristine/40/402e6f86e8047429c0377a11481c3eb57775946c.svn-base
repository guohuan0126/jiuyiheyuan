package com.duanrong.drpay.business.loan.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.duanrong.drpay.business.invest.InvestConstants.InvestStatus;
import com.duanrong.drpay.business.invest.model.Invest;
import com.duanrong.drpay.business.invest.model.InvestSubLoan;
import com.duanrong.drpay.business.loan.LoanConstants;
import com.duanrong.drpay.business.loan.model.Loan;
import com.duanrong.drpay.business.loan.model.SubLoan;
import com.duanrong.drpay.config.IdUtil;
import com.duanrong.drpay.config.ToType;
import com.duanrong.util.InterestUtil;

import util.ArithUtil;

/**
 * 理财计划资产匹配默认实现
 * @author xiao
 * @date 2017年1月11日 上午9:20:24
 */
public class DefaultSubloanForkServiceImpl extends AbstractSubloanForkServiceImpl{


	@Override
	public void fork(Loan loan) throws Exception {
		List<Invest> invests = loan.getInvests();
		for (Invest invest : invests) {
			invest.setLoan(loan);
			if (invest.getFork() == 0) {
				this.forkSubLoan(invest);
			}
		}
	}

	
	/**
	 * 理财计划单笔投资匹配
	 * 
	 * @param invest
	 * @throws Exception
	 */
	private void forkSubLoan(Invest invest) throws Exception {
		Loan loan = invest.getLoan();
		// 剩余需要分配的金额
		double residueMoney = invest.getMoney();
		List<SubLoan> subLoans = new ArrayList<>();
		List<SubLoan> subLoans2 = new ArrayList<>();
		if (LoanConstants.Type.VEHICLE.equals(loan.getLoanType())) {
			subLoans = loanService.getVehicle(loan.getId());
		} else if (LoanConstants.Type.AGRICULTURE.equals(loan.getLoanType())) {
			subLoans = loanService.getAgricultureForkLoans(loan.getId());
		} else {
			log.errLog("放款匹配金额失败", "未知的理财理财计划类型loanId: " + loan.getId()
					+ ", loanType: " + loan.getLoanType());
			return;
		}
		List<InvestSubLoan> investSubloans = new ArrayList<>();
		// 理财计划预期收益
		double interest = 0.0;
		for (SubLoan subLoan : subLoans) {
			InvestSubLoan investSubLoan = new InvestSubLoan();
			if (ArithUtil.round(residueMoney - subLoan.getAllocationMoney(), 2) > 0) {
				residueMoney -= subLoan.getAllocationMoney();
				investSubLoan.setMoney(subLoan.getAllocationMoney());
				subLoan.setAllocationMoney(0);			
			} else {
				subLoan.setAllocationMoney(subLoan.getAllocationMoney()
						- residueMoney);
				investSubLoan.setMoney(residueMoney);
				residueMoney = 0;
			}
			residueMoney = ArithUtil.round(residueMoney, 2);
			investSubLoan.setInvestId(invest.getId());
			investSubLoan.setInvestSubloanId(IdUtil.generateId(ToType.IVSB));
			investSubLoan.setSubloanId(subLoan.getSubloanId());
			investSubLoan.setUserId(invest.getInvestUserID());
			investSubLoan.setInvestTime(new Date());
			investSubLoan.setPaidMoney(0);
			investSubLoan.setPaidInterest(0);
			investSubLoan.setStatus(InvestStatus.BID_SUCCESS);
			// 计算子标预期利息
			int periods = 0;
			if ("天".equals(loan.getOperationType())) {
				periods = loan.getDay();
			} else {
				periods = loan.getDeadline();
			}
			investSubLoan.setInterest(InterestUtil.getInterestByPeriod(
					investSubLoan.getMoney(), loan.getRate(), periods,
					loan.getOperationType(), loan.getRepayType()));
			if(investSubLoan.getMoney() > 0){
				investSubloans.add(investSubLoan);
			}		
			subLoans2.add(subLoan);
			// 累加投资理财计划预期收益
			interest += investSubLoan.getInterest();
			if (residueMoney == 0)
				break;
		}
		invest.setInterest(interest);
		invest.setFork(1);
		this.saveInvestSubLoan(invest, investSubloans, subLoans2);
	}

	
}
