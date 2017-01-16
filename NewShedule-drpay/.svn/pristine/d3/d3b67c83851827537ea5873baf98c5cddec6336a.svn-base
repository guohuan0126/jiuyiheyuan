package com.duanrong.business.repay.service;

import java.util.Date;
import java.util.List;

import com.duanrong.business.repay.model.RepayMail;

import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.loan.model.Loan;
import com.duanrong.business.repay.model.Repay;

/**
 * @Description: 还款
 * @Author: 林志明
 * @CreateDate: Sep 15, 2014
 */
public interface RepayService {

	public Repay get(String id);

	public List<Repay> getWaitRepay();
	/**
	 * 获得按天计算情况下先息后本还款某一期应得的利息
	 * 
	 * @param money
	 *            投资金额
	 * @param rate
	 * @param period
	 * @param
	 * @return
	 */
	public double getRFCLInterestByPeriodDay(double money, double rate,
			int period);

	/**
	 * 计算投资所得收益
	 * 
	 * @param repayType
	 *            还款类型
	 * @param deadline
	 *            投资期限
	 * @param rate
	 *            利率
	 * @param money
	 *            投资金额
	 * @return
	 */
	public double calculateInvestInterset(String repayType, int deadline,
			double rate, double money);

	/**
	 * 
	 * @description 获取按月计息请款下:一次性到期还本付息的利息计算
	 * @author 孙铮
	 * @time 2014-8-8 下午3:24:01
	 * @param money
	 * @param rate
	 * @param
	 * @return
	 */
	public double getDQHBFXInterestByPeriod(double money, double rate, int month);

	/**
	 * 获取投资某一期应得的利息
	 * 
	 * @param repayType
	 *            还款类型
	 * @param rate
	 *            利率
	 * @param money
	 *            投资金额
	 * @param period
	 *            第几期
	 * @param deadline
	 *            投资期限
	 * @return
	 */
	public double getInterestByPeriod(String repayType, double rate,
			double money, int period, int deadline);

	public void updateByLoanId(Repay repay);
	/**
	 * 
	 * @description 生成还款计划
	 * @author 孙铮
	 * @time 2015-3-20 下午4:28:53
	 * @param
	 */
	public void generateRepay(Loan loan);

	/**
	 * 
	 * @description 生成本地还款计划(主要用于生成还款计划失败时)
	 * @author 孙铮
	 * @time 2015-3-24 上午10:24:23
	 * @param invests
	 * @param loan
	 * @param date
	 */
	public void generateRepay(List<Invest> invests, Loan loan, Date date);


	/**
	 * 
	 * @description 项目还款记录
	 * @author 孙铮
	 * @time 2015-3-24 上午11:56:30
	 * @param loanId
	 */
	public List<Repay> getRepayByLoan(String loanId);

	/**
	 * 
	 * @description 项目还款记录
	 * @author 孙铮
	 * @time 2015-3-24 上午11:56:30
	 * @param
	 */
	public void update(Repay repay);
	
	public Repay getRepayById(String id);

	
	/**
	 * 获取一周以内的还款记录
	 * @return
	 */
	public List<Repay> getRepayList(Date date);
	
	
	/**
	 * 查询days天以后需要还款的用户
	 * @param days
	 * @return
	 */
	public List<RepayMail> getInterval(int days);

	/**
	 * 查询days天后需要还款的用户
	 * @param days
	 * @param userId
	 * @return
	 */
	public List<RepayMail> getRepayInterval(String userId, int days);
	
	
	/**
	 * 查询days天前完成的还款
	 * @param days
	 * @param userId
	 * @return
	 */
	public List<RepayMail> getRepayFinishInterval(int days);
}
