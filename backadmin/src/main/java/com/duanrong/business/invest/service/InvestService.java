package com.duanrong.business.invest.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import base.exception.ExceedMaxAcceptableRate;
import base.exception.ExceedMoneyNeedRaised;
import base.exception.InsufficientBalance;
import base.exception.InvestMoneyException;
import base.exception.InvestorsAndFinanciersIDException;
import base.model.PageData;
import base.pagehelper.PageInfo;

import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.invest.model.InvestRedpacket;
import com.duanrong.business.repay.model.repayMent;
import com.duanrong.business.user.model.User;
import com.duanrong.business.user.model.newInvestUser;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2014-9-1 上午11:23:16
 * @Description : drsoa Maven Webapp com.duanrong.business.invest.service
 *              InvestService.java
 * 
 */
public interface InvestService {

	/**
	 * 更新config表的成交笔数
	 */
	public void updateInvestRecord4ConfigTable();

	/**
	 * 获得项目投标满额的时间
	 * 
	 * @param loanId
	 *            项目ID
	 * @return
	 */
	public Date readInvestDateLastOne(String loanId);

	/**
	 * 今日收益
	 * 
	 * @param userId
	 *            用户ID
	 * @return
	 */
	public Double readTodayInvestmentIncome(String userId);

	/**
	 * 获取用户当前投资金额
	 * 
	 * @param userId
	 *            用户ID
	 * @return
	 */
	public Double readCurrentInvestMoney(String userId);

	/**
	 * 获取用户平均年化收益率
	 * 
	 * @param userId
	 *            用户ID
	 * @return
	 */
	public Double readAvgRate(String userId);

	/**
	 * 获取投资动态
	 * 
	 * @return
	 */
	public List<Invest> readInvestDynamic(Long recordNumber);

	/**
	 * 查询个人单个项目的投资总金额
	 * 
	 * @param userId
	 *            用户ID
	 * @param loanId
	 *            项目ID
	 * @return
	 */
	public Double readPersonalInvestCeiling(String userId, String loanId);

	/**
	 * 分页查询
	 * 
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页显示的记录数
	 */
	public PageData<Invest> readPaging(int pageNo, int pageSize, Invest invest);

	/**
	 * 根据条件查询用户投资的项目
	 * 
	 * @return
	 */
	public List<Invest> readInvestLoan(Invest invest);

	/**
	 * 根据条件查询用户投资项目总额
	 * 
	 * @return
	 */
	public Double readInvestLoanTotalMoney(Invest invest);

	/**
	 * 根据条件查询用户投资项目预计收益
	 * 
	 * @param invest
	 * @return
	 */
	public Double readInvestLoanTotalInterest(Invest invest);

	/**
	 * 根据条件查询用户投资项目总收益
	 * 
	 * @param invest
	 * @return
	 */
	public Double readInvestLoanTotalPaidInterest(Invest invest);

	/**
	 * 获得用户投资总额（成功）
	 * 
	 * @param userId
	 * @return
	 */
	public Double readInvestTotalMoney(String userId);

	/**
	 * 获得用户投资总收益
	 * 
	 * @param userId
	 * @return
	 */
	public Double readInvestsTotalInterest(String userId);

	/**
	 * 获得用户投资收益详情
	 * 
	 * @param userId
	 * @return
	 */
	public List<Invest> readInvestInterestDetail(String userId);

	/**
	 * 
	 * @description 查询所有投资记录
	 * @author 孙铮
	 * @return
	 */
	public List<Invest> readAll();

	/**
	 * 
	 * @description 根据id查询单条投资记录
	 * @author 孙铮
	 * @time 2014-9-1 下午12:07:41
	 * @param id
	 * @return
	 */
	public Invest read(String id);

	/**
	 * 
	 * @description 根据组合条件查询所有借款记录(该方法已经移动到LoanService中)
	 * @author 孙铮
	 * @time 2014-9-1 下午12:02:28
	 * @param invest
	 * @return
	 */
	// public List<Invest> getInvestsByGroupCondition(Invest invest);

	/**
	 * 创建Invest
	 * 
	 * @param invest
	 * @throws InvestMoneyException
	 * @throws InvestorsAndFinanciersIDException
	 */
	public void create(Invest invest) throws InsufficientBalance,
			ExceedMaxAcceptableRate, InvestMoneyException,
			InvestorsAndFinanciersIDException;

	/**
	 * 更新
	 * 
	 * @param invest
	 */
	public void update(Invest invest);

	/**
	 * 根据loan获得invest
	 * 
	 * @param loanId
	 * @return
	 */
	public List<Invest> readInvestByLoan(String loanId);

	public Long readInvestCountByloanId(String loanId, String status);

	public PageData<Invest> readPagerInvestByLoanId(int pageIndex, int pageSize,
			String loanId, String status);

	/**
	 * 根据条件获得用户投资金额
	 * 
	 * @param invest
	 * @return
	 */
	public Double readInvestMoney(Invest invest);

	/**
	 * 获取用户个人可投剩余金额
	 * 
	 * @param loanId
	 *            项目ID
	 * @param userId
	 *            用户ID
	 * @return
	 */
	public Double acquirePersonalRemainInvestMoney(String loanId, String userId);

	/**
	 * 
	 * @description 最终判断项目是否超募,冻结金额,修改项目状态,保存invest表数据
	 * @author 孙铮
	 * @time 2014-12-5 下午2:45:43
	 * @param loan
	 * @param invest
	 */
	/*@Deprecated
	public void judgeUpdateSaveInvest(Invest invest)
			throws ExceedMoneyNeedRaised, InsufficientBalance;*/

	/**
	 * 
	 * @description 如果保存了invest数据,并且也冻结了金额,修改了项目状态,出现异常后要修改相关操作
	 * @author 孙铮
	 * @time 2014-12-5 下午4:24:51
	 * @param invest
	 */
	/*public void exceptionDispose(Invest invest);*/

	@Transactional(rollbackFor = Exception.class)
	public void syncInvest(Invest invest, String type)
			throws ExceedMoneyNeedRaised, InsufficientBalance;

	/**
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param loanId
	 * @return
	 */
	public PageInfo<Invest> readInvestByLoan(int pageNo, int pageSize,
			Map<String, Object> map);

	/**
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param loanId
	 * @return
	 */
	public List<Invest> readInvestByLoan(Map<String, Object> map);

	/**
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param loanId
	 * @return
	 */
	public PageInfo<Invest> readInvestByUser(int pageNo, int pageSize,
			String userId);

	/**
	 * 创建投资确认函
	 * 
	 * @param loanId
	 * @return
	 */
	public String createInvestConfimcation(String loanId, String createUserId);

	/**
	 * 获取用户项目投资信息
	 * 
	 * @param userId
	 * @param loanId
	 * @return
	 */
	public List<Invest> readInvestByUserAndLoan(String userId, String loanId);
	
	public Invest readInvest(String id);
	
	/**
	 * 得到某个项目标的的有效投资金额（排除流标的）
	 * 
	 * @param loanID
	 * @return
	 */
	public Double readValidInvestSumByLoan(String loanID);
	
	
	/**
	 * 查询用户基本账户信息
	 * @param user
	 * @return
	 */
	public User readInvestUserById(String userId);
	public List<Invest> readInvestNum(Map map);
	
	/**
	 * 项目自动投标记录数
	 * @param loanId
	 * @return
	 */
	public int readAutoInvestByLoan(String loanId);
	
	/**
	 * 发送投资确认函
	 * @param loanId
	 * @return
	 */
	public String sendInvestInfomation(String loanId, String userId);
	public PageInfo<Invest> readPageInfo(int pageNo, int pageSize, Map map);
	public List<Invest> readInvests(Map<String, Object> map);
	public Double readTotalMoney(Map map);
	public Long readInvestNums();
	

	public List<Invest> readInvestByDate(Date date);
	
	public int readCountInvestByRedpacketForVisitor(String userId);
	
	/**
	 * 获取用户第一次有效投资时间
	 * @param userId
	 * @return
	 */
	public Date readFirstAvilidInvestTimeByUserId(String userId);
	
	/**
	 * 获取项目自动投标
	 * @param userId
	 * @return
	 */
	public double readAutoInvestMoneyByLoanId(String loanId);

	/**
	 * 投资记录（只显示天眼用户的投资记录）
	 * @param pageNo
	 * @param pageSize
	 * @param map
	 * @return
	 */
	public PageInfo<Invest> readInvestRecordsNetLoanEye(int pageNo, int pageSize, Map<String, Object> map);

	public BigDecimal readTotalMoneyForNetLoanEye(Map<String, Object> map);

	public int readTotalNumForNetLoanEye(Map<String, Object> map);

	public List<Invest> readExportInvestInfo(Map<String, Object> map);
	
	
	/**
	 * 已用红包分页
	 * @param pageNo
	 * @param pageSize
	 * @param investRedpacket
	 * @return
	 */
	PageInfo<InvestRedpacket> readPageLiteInvestredpacket(int pageNo, int pageSize, InvestRedpacket investRedpacket);
	/**
	 * 根据条件查询个人的投资信息
	 * @param map
	 * @return
	 */	
	public List<Invest> readUserInvest(Map<String,Object> map);

	public int readTotalPeople(Map<String, Object> map);

	public BigDecimal readFirstInvestMoney(Map<String, Object> map);

	public int readFirstInvestPeople(Map<String, Object> map);

	public int readCheckFirstInvest(String userId, Date time);

	public PageInfo<repayMent> readRepayMentRecords(int pageNo, int pageSize,
			Map<String, Object> map,String end);

	public int readInvestCountByUserId(String userId);

	public double readRedpacketUableMoeny(String userId);

	public double readRedpacketUableRate(String userId);

	public List<repayMent> readRepayMentRecordsList(Map<String, Object> map);
	
}
