package com.duanrong.drpay.business.loan.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import com.duanrong.drpay.business.invest.model.Invest;
import com.duanrong.drpay.business.invest.model.InvestSubLoan;
import com.duanrong.drpay.business.invest.service.InvestService;
import com.duanrong.drpay.business.loan.LoanConstants;
import com.duanrong.drpay.business.loan.model.Loan;
import com.duanrong.drpay.business.loan.model.SubLoan;
import com.duanrong.drpay.business.loan.service.LoanService;
import com.duanrong.drpay.business.loan.service.SubloanForkService;

import util.Log;

/**
 * 理财计划子标匹配算法抽象类，
 * 定义抽象算法接口
 * @author xiao
 * @date 2017年1月11日 上午9:14:27
 */
public abstract class AbstractSubloanForkServiceImpl implements SubloanForkService{

	
	@Resource
	InvestService investService;
	
	@Resource
	LoanService loanService;
	
	@Resource
	Log log;
	
	/**
	 * 持久化分配数据
	 * 
	 * @param invest
	 * @param investSubloans
	 * @param subloans
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	protected void saveInvestSubLoan(Invest invest,
			List<InvestSubLoan> investSubloans, List<SubLoan> subloans)
			throws Exception {
		Loan loan = invest.getLoan();
		// 插入分配记录
		investService.insertBatchInvestSubloan(investSubloans);
		// 更新subloan可分配金额
		if (LoanConstants.Type.VEHICLE.equals(loan.getLoanType())) {
			loanService.updateBatchVehicle(subloans);
		} else if (LoanConstants.Type.AGRICULTURE.equals(loan.getLoanType())) {
			loanService.updateBatchAgricultureForkLoans(subloans);
		} else {
			log.errLog("放款匹配金额失败", "未知的理财理财计划类型loanId: " + loan.getId()
					+ ", loanType: " + loan.getLoanType());
		}
		// 更新invest匹配状态和预期收益
		investService.update(invest);
	}
}