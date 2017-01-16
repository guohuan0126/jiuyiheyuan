package com.duanrong.drpay.business.loan.service;

import java.util.List;

import base.exception.ErrorCodeException;
import base.exception.TradeException;

import com.duanrong.drpay.business.invest.model.Invest;
import com.duanrong.drpay.business.loan.model.Loan;
import com.duanrong.drpay.business.loan.model.SubLoan;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2014-8-28 下午12:33:30
 * @Description : drsoa com.duanrong.business.loan.service LoanService.java
 * 
 */
public interface LoanService {

	/**
	 * 
	 * @description 根据id查询单条借款记录
	 * @author 孙铮
	 * @time 2014-8-28 下午12:37:14
	 * @param id
	 * @return
	 */
	public Loan get(String id);
	
	/**
	 * 查询理财计划车贷子标
	 * @param loanId
	 * @return
	 */
	List<SubLoan> getVehicle(String loanId);
	
	/**
	 * 查询理财计划农贷子标
	 * @param loanId
	 * @return
	 */
	List<SubLoan> getAgricultureForkLoans(String loanId);
	
	
	/**
	 * 查询理财计划车贷子标
	 * @param loanId
	 * @return
	 */
	void updateBatchVehicle(List<SubLoan> subloans);
	
	/**
	 * 查询理财计划农贷子标
	 * @param loanId
	 * @return
	 */
	void updateBatchAgricultureForkLoans(List<SubLoan> subloans);
	
	/**
	 * 查询理财计划车贷子标
	 * @param loanId
	 * @return
	 */
	void updateVehicle(SubLoan subloan);
	
	/**
	 * 查询理财计划农贷子标
	 * @param loanId
	 * @return
	 */
	void updateAgricultureForkLoans(SubLoan subloan);
	
	/**
	 * 
	 * @description 根据id查询单条借款记录(加锁)
	 * @author 孙铮
	 * @time 2014-8-28 下午12:37:14
	 * @param id
	 * @return
	 */
	public Loan queryWithLock(String id);

	/**
	 * 
	 * @description 根据组合条件查询所有借款记录
	 * @author 孙铮
	 * @time 2014-8-28 下午5:29:29
	 * @param 将查询条件封装到loan对象中
	 * @return
	 */
	public List<Loan> getLoansByGroupCondition(Loan loan);

	/**
	 * 查询理财计划农贷子标
	 * @param loanId
	 * @return
	 */
	List<SubLoan> getAgricultureByGroupCondition(String loanId, int drpayStatus);
	
	/**
	 * 查询理财计划车贷子标
	 * @param loanId
	 * @return
	 */
	List<SubLoan> getVehicleByGroupCondition(String loanId, int drpayStatus);
	
	/**
	 * 更新
	 * 
	 * @param loan
	 */
	public void update(Loan loan);

	/**
	 * 处理借款募集完成
	 * 
	 * @param loanId
	 *            借款ID
	 */
	public void dealRaiseComplete(String loanId)
			throws ErrorCodeException;

	/**
	 * 计算借款尚未募集的金额
	 * 
	 * @param loanId
	 *            借款ID
	 * @return 尚未募集的金额
	 */
	public double calculateMoneyNeedRaised(String loanId);

	/**
	 * 
	 * @description 根据组合条件查询所有借款记录
	 * @author 孙铮
	 * @time 2014-9-1 下午12:02:28
	 * @param invest
	 * @return
	 */
	public List<Invest> getInvestsByGroupCondition(Invest invest);

	
	/**
	 * 放款预处理
	 * 校验项目信息是否可以放款,处理预放款信息
	 * @param loanId
	 * @return
	 * @throws TradeException
	 */
	public Loan prepare(String loanId) throws TradeException;
	

	/**
	 * 放款
	 * @param loanId
	 * @return
	 * @throws TradeException 
	 */
	public boolean giveMoneyToBorrower(Invest invest);
	

}
