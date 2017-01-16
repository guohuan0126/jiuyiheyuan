package com.duanrong.business.loan.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import base.exception.InsufficientBalance;
import base.exception.InvestMoneyException;
import base.exception.NoMatchingObjectsException;
import base.model.PageData;
import base.pagehelper.PageInfo;

import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.loan.model.ApplyEnterpriseLoan;
import com.duanrong.business.loan.model.BannerPicture;
import com.duanrong.business.loan.model.Enterprise;
import com.duanrong.business.loan.model.FixedBorrowers;
import com.duanrong.business.loan.model.House;
import com.duanrong.business.loan.model.Loan;
import com.duanrong.business.loan.model.LoanProgress;
import com.duanrong.business.loan.model.Vehicle;
import com.duanrong.business.user.model.User;
import com.duanrong.newadmin.exception.InvalidExpectTimeException;

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
	 * 借款到预计执行时间，处理借款
	 * 
	 * @param loanId
	 *            项目ID
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void dealOverExpectTime(String loanId);


	/**
	 * 根据项目类型获得项目融资进度
	 * 
	 * @param loanType
	 *            项目类型
	 * @return
	 */
	public LoanProgress getLoanProgress(String loanType);

	/**
	 * 今日可投金额
	 * 
	 * @return
	 */
	public Double getRemainingInvestmentAmount();

	/**
	 * 项目详细信息
	 * 
	 * @param loan
	 * @return
	 */
	public Loan getLoanDetail(Loan loan);

	/**
	 * 车押宝详细信息
	 * 
	 * @param loanId
	 * @return
	 */
	public Vehicle getVehicleDetail(String loanId);

	/**
	 * 房押宝详细信息
	 * 
	 * @param loanId
	 * @return
	 */
	public House getHouseDetail(String loanId);

	/**
	 * 车押宝详细信息
	 * 
	 * @param loanId
	 * @return
	 */
	public Enterprise getEnterpriseDetail(String loanId);

	/**
	 * 申请企业借款
	 * 
	 * @param applyEnterpriseLoan
	 *            企业借款对象
	 */
	public void insertEnterpriseLoan(ApplyEnterpriseLoan applyEnterpriseLoan);

	/**
	 * 验证投资金额是否符合要求，主要是递增金额，其次是起点金额
	 * 
	 * @param loanId
	 * @param money
	 * @throws InvestMoneyException
	 */
	public void verifyInvestMoney(String loanId, Double money)
			throws InvestMoneyException;

	

	/**
	 * 
	 * @description 查询所有借款记录
	 * @author 孙铮
	 * @time 2014-8-28 下午12:36:56
	 * @param loan
	 * @return
	 */
	public List<Loan> findAll();

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
	 * 
	 * @description 根据组合条件查询所有借款记录
	 * @author 孙铮
	 * @time 2014-8-28 下午5:29:29
	 * @param 将查询条件封装到loan对象中
	 * @return
	 */
	public List<Loan> getLoansByGroupCondition(Loan loan);

	
	/**
	 * 
	 * @description 根据组合条件查询所有借款记录
	 * @author xiao
	 * @time 2014-8-28 下午5:29:29
	 * @param 将查询条件封装到loan对象中
	 * @return
	 */
	public List<Loan> getLoansByGroupCondition1(Map<String, Object> map);

	
	/**
	 * 
	 * @description 根据loanID查询对应借款人信息
	 * @author 孙铮
	 * @time 2014-8-28 下午5:40:44
	 * @param loanID
	 * @return
	 */
	public User getUserByLoanID(String loanID);

	/**
	 * 
	 * @description 根据loanID获取项目图片
	 * @author 孙铮
	 * @time 2014-8-30 上午10:51:37
	 * @param loanID
	 * @return
	 */
	public List<BannerPicture> getLoanInfoPics(String loanID);

	/**
	 * 
	 * @description 根据loanID获取抵押物图片
	 * @author 孙铮
	 * @time 2014-8-30 上午10:51:37
	 * @param loanID
	 * @return
	 */
	public List<BannerPicture> getLoanGuaranteePics(String loanID);

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
			throws NoMatchingObjectsException;

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
	 * @description 页面投资进度
	 * @author 孙铮
	 * @time 2014-9-3 下午5:43:50
	 * @param loanId
	 * @return
	 */
	public double calculateRaiseCompletedRate(String loanId)
			throws NoMatchingObjectsException;

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
	 * 个人项目分页查询
	 * 
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页显示的记录数
	 * @param entity
	 *            条件
	 */
	public PageData<Loan> findPaging4Personal(int pageNo, int pageSize,
			Loan loan);

	/**
	 * 检查借款是否到预计执行时间
	 * 
	 * @param loanId
	 *            借款ID
	 */
	public boolean isOverExpectTime(String loanId);

	/**
	 * 分页
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param loan
	 * @return
	 */
	public PageInfo<Loan> pageLite(int pageNo, int pageSize, Loan loan);

	/**
	 * 检查借款是否到预计执行时间
	 * 
	 * @param loan
	 */
	public boolean isOverExpectTime(Loan loan);


	public double getTotalMoney(Map map);
	public List<Loan> findLoan(Loan loan);
	
	/**
	 * 查询等待放款的项目
	 * @return
	 */
	public List<Loan> getLoanForGaveMoneyToBorrower(int days);
}
