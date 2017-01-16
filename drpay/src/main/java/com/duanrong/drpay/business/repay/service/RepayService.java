package com.duanrong.drpay.business.repay.service;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import base.exception.TradeException;
import base.exception.UserAccountException;

import com.duanrong.drpay.business.invest.model.Invest;
import com.duanrong.drpay.business.loan.model.Loan;
import com.duanrong.drpay.business.loan.model.SubLoan;
import com.duanrong.drpay.business.repay.model.Repay;
import com.duanrong.drpay.business.repay.model.RepayInvest;

/**
 * @Description: 还款
 * @Author: 林志明
 * @CreateDate: Sep 15, 2014
 */
public interface RepayService {

	/**
	 * 本地还款
	 * @param repay
	 * @param beforeRepay
	 * @throws UserAccountException
	 */
	public void normalRepay(Repay repay, List<RepayInvest> repayInvests)throws UserAccountException;

	public Repay get(String id);
	/**
	 * 还款接口预处理
	 * 
	 * @param repayId
	 *            还款ID
	 * @throws UserAccountException 
	 * @throws RepayException
	 */
	public Repay prepare(String repayId) throws TradeException, UserAccountException;

	/**
	 * 获得需要还款金额
	 * 
	 * @param invest
	 * @param repay
	 * @return
	 */
	public Double queryRepayMoney(Invest invest, Repay repay, Date now, boolean isBeforeRepay);


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
	 * 生成还款计划(逻辑比较复杂, 谨慎修改)
	 * @param loan
	 */
	void saveRepay(Loan loan);
	
	/**
	 * 生成理财计划还款计划(逻辑比较复杂, 谨慎修改)
	 * @param loan
	 */
	void saveProjectRepay(Loan loan);
	
	/**
	 * 生成子标还款计划(逻辑比较复杂, 谨慎修改)
	 * @param loan
	 */
	void saveSubLoanRepay(SubLoan subloan);

	
	public void deleteRepayInvests(String id);

	/**
	 * 通过还款ID 查询每个用户的还款记录
	 * @param requestNo
	 * @return
	 */
	public List<RepayInvest> getRepayInvests(String repayId);

	/**
	 * 创建还款明细
	 * @param repayId
	 * @param beforeRepay
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public Repay createRepayInvest(Repay repay)throws UserAccountException,TradeException;

	public void updateRepay(Repay repay);

	public void insertRepay(Repay repay);

	public List<Repay> getByCondition(Repay repay);

}
