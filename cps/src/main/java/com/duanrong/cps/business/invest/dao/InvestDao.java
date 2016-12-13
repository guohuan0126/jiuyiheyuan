package com.duanrong.cps.business.invest.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import base.dao.BaseDao;
import base.pagehelper.PageInfo;

import com.duanrong.cps.business.fengchelicai.model.FengchelicaiRepayment;
import com.duanrong.cps.business.fengchelicai.model.Repay;
import com.duanrong.cps.business.invest.model.Invest;
import com.duanrong.cps.business.invest.model.InvestConfirmation;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2014-9-1 上午11:26:11
 * @Description : drsoa Maven Webapp com.duanrong.business.invest.dao
 *              InvestDao.java
 * 
 */
public interface InvestDao extends BaseDao<Invest> {

	/**
	 * 获得投资成功的记录数
	 */
	public Long getSuccessInvestRecordNumber();

	/**
	 * 更新config表的成交笔数
	 */
	public void updateInvestRecord4ConfigTable(Long investRecord);

	/**
	 * 获得项目投标满额的时间
	 * 
	 * @param loanId
	 *            项目ID
	 * @return
	 */
	public Date getInvestDateLastOne(String loanId);

	/**
	 * 获取用户当前投资金额
	 * 
	 * @param userId
	 *            用户ID
	 * @return
	 */
	public Double getCurrentInvestMoney(String userId);

	/**
	 * 获取用户平均年化收益率
	 * 
	 * @param userId
	 *            用户ID
	 * @return
	 */
	public Double getAvgRate(String userId);

	/**
	 * 获取投资动态
	 * 
	 * @return
	 */
	public List<Invest> getInvestDynamic(Long recordNumber);

	/**
	 * 查询个人单个项目的投资总金额
	 * 
	 * @param userId
	 *            用户ID
	 * @param loanId
	 *            项目ID
	 * @return
	 */
	public Double getPersonalInvestCeiling(String userId, String loanId);

	/**
	 * 根据条件查询用户投资的项目
	 * 
	 * @return
	 */
	public List<Invest> getInvestLoan(Invest invest);

	/**
	 * 根据条件查询用户投资项目总额
	 * 
	 * @return
	 */
	public Double getInvestLoanTotalMoney(Invest invest);

	/**
	 * 根据条件查询用户投资项目预计收益
	 * 
	 * @param invest
	 * @return
	 */
	public Double getInvestLoanTotalInterest(Invest invest);

	/**
	 * 根据条件查询用户投资项目总收益
	 * 
	 * @param invest
	 * @return
	 */
	public Double getInvestLoanTotalPaidInterest(Invest invest);

	/**
	 * 获得用户投资总额（成功）
	 * 
	 * @param userId
	 * @return
	 */
	public Double getInvestTotalMoney(String userId);

	/**
	 * 得到某个项目标的的有效投资金额（排除流标的）
	 * 
	 * @param loanID
	 * @return
	 */
	public Double getValidInvestSumByLoan(String loanID);

	/**
	 * 根据loan获得invest
	 * 
	 * @param loanId
	 * @return
	 */
	public List<Invest> getInvestByLoan(String loanId);

	/**
	 * 根据loanid获得invest集合,status!=流标,sor=asc
	 * 
	 * @param loanId
	 * @return
	 */
	public List<Invest> getInvestByLoanSortAsc(String loanId);

	public Long GetInvestCountByLoanId(String loanId, String status);

	public PageInfo<Invest> GetPagerInvestByLoanId(int pageIndex, int pageSize,
			String loanId, String status);

	/**
	 * 获得用户投资总收益
	 * 
	 * @param userId
	 * @return
	 */
	public Double getInvestsTotalInterest(String userId);

	/**
	 * 获得用户投资收益详情
	 * 
	 * @param userId
	 * @return
	 */
	public List<Invest> getInvestInterestDetail(String userId);

	/**
	 * 根据条件获得用户投资金额
	 * 
	 * @param invest
	 * @return
	 */
	public Double getInvestMoney(Invest invest);

	/**
	 * 根据项目ID对指定投资状态进行更新
	 * 
	 * @param loanId
	 *            项目ID
	 * @param originalStatus
	 *            当前投资状态
	 * @param toStatus
	 *            更新后的状态
	 */
	public void updateInvestStatusByLoanId(String loanId,
			String originalStatus, String toStatus);

	/**  **/

	/**
	 * 根据借款项投资记录（分页）
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param loanId
	 * @return
	 */
	public PageInfo<Invest> getInvestByLoan(int pageNo, int pageSize,
			Map<String, Object> map);

	/**
	 * 根据借款项投资记录（分页）
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param loanId
	 * @return
	 */
	public List<Invest> getInvestByLoan(Map<String, Object> map);

	/**
	 * 根据借款项投资记录（分页）
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param loanId
	 * @return
	 */
	public PageInfo<Invest> getInvestByUser(int pageNo, int pageSize,
			String userId);

	/**
	 * 获取投资确认函信息
	 * 
	 * @param loanId
	 * @return
	 */
	public List<InvestConfirmation> getInvestConfirmation(String loanId);

	/**
	 * 获取投资投资用户的项目投资信息
	 * 
	 * @param loanId
	 * @param userId
	 * @return
	 */
	public List<Invest> getInvestByUserAndLoan(Map<String, Object> map);

	public Invest getInvest(String id);

	/**
	 * 获取用户的项目投资金额
	 * 
	 * @param map
	 * @return
	 */
	public double getLoanInvestMoneyByUser(String userId, String loanId);

	public String getLoanStatus(String id);

	public List<Invest> getInvestNum(Map map);

	/**
	 * 项目自动投标记录数
	 * 
	 * @param loanId
	 * @return
	 */
	public int getAutoInvestByLoan(String loanId);

	PageInfo<Invest> pageInfo(int pageNo, int pageSize, Map map);

	public List<Invest> getInvests(Map<String, Object> map);

	public Double getTotalMoney(Map map);

	/**
	 * @description 查询用户是否为新手(除掉该比投资以后,该用户是否还有其他投资,如果有则不是新手,如果没有则是新手)
	 * @date 2015-5-21
	 * @time 上午10:34:04
	 * @author SunZ
	 * @param i
	 */
	public Invest getCountByNewbieEnjoy(Invest i);

	public Long getInvestNums();

	/**
	 * 查询当天投资成功的记录
	 * @param date
	 * @return
	 */
	public List<Invest> getInvestByDate(Date date);
	
	
	public int getCountInvestByRedpacketForVisitor(String userId);
	
	/**
	 * 获取用户第一次有效投资时间
	 * @param userId
	 * @return
	 */
	public Date getFirstAvilidInvestTimeByUserId(String userId);
	
	/**
	 * 获取项目自动投标金额
	 * @param loanId
	 * @return
	 */
	public double getAutoInvestMoneyByLoanId(String loanId);

	/**
	 * 查询投资信息
	 * @param investId
	 * @return
	 */
	public Invest getInvestById(String investId);

	/**
	 * 更新加息券使用
	 * @param invest
	 * @return
	 */
	public int updateForPacketUse(Invest invest);

	/**
	 * 投资记录（只显示天眼用户的投资记录）
	 * @param pageNo
	 * @param pageSize
	 * @param map
	 * @return
	 */
	public PageInfo<Invest> getInvestRecordsNetLoanEye(int pageNo, int pageSize, Map<String, Object> map);

	public BigDecimal getTotalMoneyForNetLoanEye(Map<String, Object> map);

	public int getTotalNumForNetLoanEye(Map<String, Object> map);

	public List<Invest> getExportInvestInfo(Map<String, Object> map);

	/**
	 * 根据项目ID查询第一个有效投标的时间
	 * @param loanId
	 * @return
	 */
	public Date getInvestDateFirstOne(String loanId);

	public Object getTotalCount(String loanId);

	public List<Invest> getInvestRecords(String loanId);

	/**
	 * 根据日期查询当日投资总人数
	 * @param date
	 * @return
	 */
	public int getLendCount(String date);

	public List<Invest> getAccInvest(String userId, String startTime,
			String endTime, String investStatus, String offset, String limit,
			String investRecordId);

	public List<Repay> getRepays(String id, String userId);

	public Repay getNextRepay(String id, String userId);

	public FengchelicaiRepayment getRepayMentRecordsList(
			Map<String, Object> map);

	//获取已募集的金额
	public int getAmountMoney(String loanId);

	//获取西财用户投资总金额
	public Double getTotalMoney4XiCai(String userId);

	public List<Invest> getInvestInfo(String startdate, String enddate,
			int start, String pagesize);

	public Double getTotalMoney4AlreadyFundraising(String id);

	public int getTotalInvest(String startdate, String enddate);

}
