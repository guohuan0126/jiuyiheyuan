package com.duanrong.cps.business.loan.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import base.dao.BaseDao;
import base.pagehelper.PageInfo;









import com.duanrong.cps.business.invest.model.Invest;
import com.duanrong.cps.business.loan.model.BannerPicture;
import com.duanrong.cps.business.loan.model.FixedBorrowers;
import com.duanrong.cps.business.loan.model.Loan;
import com.duanrong.cps.business.netloaneye.model.NetLoanModel;
import com.duanrong.cps.business.user.model.User;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : lxw
 * 
 */
public interface LoanDao extends BaseDao<Loan> {

	
	

	/**
	 * 查询可以推送网贷天眼的项目列表
	 * @param loan
	 * @return
	 */
	public List<Loan> getWaitPushLoanList(Loan loan);

	public List<Loan> getPushRecords(Loan loan);

	public Loan getP2pEyeLoanByLoanId(String id);

	public NetLoanModel getNetLoanCount(Loan loan);

	public NetLoanModel getNetLoanSum(Loan loan);
	public int updateLoan(Loan loan);
	
	/**
	 * 比搜易
	 */
	public List<Loan> getBsyWaitPushLoanList(Loan loan);
	public List<Loan> getBsyLoanByLoanId(List idList);
	
	/**
	 * 查询推送到比搜益的项目列表
	 * @param pageNo
	 * @param loan
	 * @return
	 */
	public List<Loan>getBsyLoanHistory(Map<String,Object>paramMap);

	/**
	 * 给风车理财查询当日借款人数量
	 * @param date
	 * @return
	 */
	public int getBorrowCount(String date);

	/**
	 * 风车理财统计投资总额
	 * @param date
	 * @return
	 */
	public Double getInvestAllMoney(String date);

	/**
	 * 风车理财统计待收总额
	 */
	public Double getAllWaitBackMoney(String date);

	public Loan getLoanInfo(String loanId);
	

}
