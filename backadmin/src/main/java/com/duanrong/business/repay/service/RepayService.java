package com.duanrong.business.repay.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import base.exception.InsufficientBalance;
import base.exception.RepayException;
import base.pagehelper.PageInfo;

import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.loan.model.Loan;
import com.duanrong.business.repay.model.Repay;
import com.duanrong.business.repay.model.RepayInvest;

/**
 * @Description: 还款
 * @Author: 林志明
 * @CreateDate: Sep 15, 2014
 */
public interface RepayService {

	/**
	 * 获得项目还款完成（结束）时间
	 * 
	 * @param loanId
	 *            项目ID
	 * @return
	 */
	public Date readCompleteDate(String loanId);

	/**
	 * 获取用户还款计划
	 * 
	 * @param invest
	 * @return
	 */
	public List<Repay> readRepayPlan(Invest invest);


	public Repay read(String id);

	/**
	 * 根据条件进行组合查询
	 * 
	 * @param repay
	 * @return
	 */
	public List<Repay> readByCondition(Repay repay);

	/**
	 * 获得按天计算情况下先息后本还款某一期应得的利息
	 * 
	 * @param money
	 *            投资金额
	 * @param rate
	 * @param period
	 * @param deadline
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
	 * @param period
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

	/**
	 * 
	 * @description 根据投资ID查询对应的还款信息(该方法暂时适用于还款跟踪(该还款信息中包含(投资人ID,某比投资应得的利息和本金等)))
	 * @author 孙铮
	 * @time 2014-9-16 下午12:22:16
	 * @param invest
	 * @return
	 */
	public List<Repay> getRepaysByInvestId(Invest invest);

	/**
	 * 
	 * @description 生成还款计划
	 * @author 孙铮
	 * @time 2015-3-20 下午4:28:53
	 * @param id
	 */
	public void generateRepay(List<Invest> invests, Loan loan);

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
	 * @description 本地还款
	 * @author 孙铮
	 * @time 2015-3-24 上午11:56:30
	 * @param userId
	 * @param loanId
	 * @param actionTime
	 * @param actionMessage
	 * @param repayId
	 */
	public String localRepay(String userId, String loanId, String actionTime,
			String actionMessage, String repayId, String befoRepay)throws InsufficientBalance,
			RepayException;

	/**
	 * 
	 * @description 项目还款记录
	 * @author 孙铮
	 * @time 2015-3-24 上午11:56:30
	 * @param loanId
	 */
	public List<Repay> readRepayByLoan(String loanId);

	/**
	 * 
	 * @description 项目还款记录
	 * @author 孙铮
	 * @time 2015-3-24 上午11:56:30
	 * @param loanId
	 */
	public void update(Repay repay);
	
	public Repay readRepayById(String id);
	
	/**
	 * 编辑项目还款计划
	 * @param repay
	 */
	public void alertRapay(Repay repay);
	public PageInfo<Repay> readPageInfo(int pageNo, int pageSize, Map map);
	public List<Repay> readRepay(Map map);
	public List<RepayInvest> readByReapyId(String id);
	/**
	 * 获取一周以内的还款记录
	 * @return
	 */
	public List<Repay> readRepayList(Date date);
	
	public void updateBacthRepayInvest(RepayInvest repayInvest);
	
	public void updateByLoanId(Repay repay);
	/**
	 * 还款计划表里的本金和和利息和
	 * @param map
	 * @return
	 */
	public Repay readTotalMoney(Map map);
}