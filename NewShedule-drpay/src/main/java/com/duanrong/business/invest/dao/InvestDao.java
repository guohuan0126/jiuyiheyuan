package com.duanrong.business.invest.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import base.dao.BaseDao;
import base.pagehelper.PageInfo;

import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.invest.model.InvestConfirmation;
import com.duanrong.business.invest.model.InvestRedpacket;
import com.duanrong.business.invest.model.InvestSubLoan;
import com.duanrong.business.invest.model.PassThrough;

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
	public List<Invest> getInvestByLoanSortAsc(Invest invest);
	
	public List<Invest> getInvestByLoanSortDesc(Invest invest);

	public Long GetInvestCountByLoanId(String loanId, String status);

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
	 * 插入补息和奖励金额
	 * @param investRedpacket
	 */
	void insertInvestRedpacket(InvestRedpacket investRedpacket);

	/**
	 * 插入补息和奖励金额
	 * @param investRedpacket
	 */
	void updatetInvestRedpacket(InvestRedpacket investRedpacket);
	
	/**
	 * 获取investRedpacket 轮询数据
	 * @return
	 */
	List<InvestRedpacket> getInvestRedpacketList();
	
	/**
	 * 根据项目id查询投资成功的的数量
	 * @param loanId
	 * @return
	 */
	int getInvestSeccessByLoanId(String loanId);
	
	/**
	 * 十一活动
	 * @param date
	 * @return
	 */
	List<Invest> getNationalActive(Date date);
	//得到每日新增的投资用户id
	public List<String> getNewInvestUser();
	/**
	 * 查询用户当前在投金额（不包括等额本息中已还的）
	 * @param userId
	 * @return
	 */
	public Double getUserInvestMoney(String userId);

	public void update4DoubleEleven(PassThrough passThrough);

	public int getCurrentInvestMoney4DoubleElevec(String userId,String createTime);

	public int getCurrentInvestMoney4DoubleElevec1(String userId);

	
	/**
	 * 完成首次投资后，第N天
	 * @param param
	 * @return
	 */
	public List<Invest>getFirstInvestAfter(Map<String,Object>param);
	

	//查询当天有还款的用户id
	public List<String> getHasRepayUserId();

	public List<Invest> getInvestBy(Map<String,Object>param);

	/**
	 * 等待支付轮询数据
	 * @return
	 */
	List<Invest> getInvestByStatus();
	
	/**
	 * 等待支付轮询数据
	 * @return
	 */
	List<InvestSubLoan> getInvestSubloanByStatus();
	
	
	int getSumInvestRedpacketByOrderId(InvestRedpacket ir);
}
