package com.duanrong.business.invest.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import util.Log;
import base.exception.ExceedMoneyNeedRaised;
import base.exception.InsufficientBalance;
import base.exception.InvestMoneyException;
import base.exception.RedPacketUseException;
import base.model.PageData;

import com.duanrong.business.account.BusinessEnum;
import com.duanrong.business.account.service.UserAccountService;
import com.duanrong.business.autoinvest.service.AutoInvestService;
import com.duanrong.business.invest.InvestConstants;
import com.duanrong.business.invest.dao.InvestDao;
import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.invest.model.InvestRedpacket;
import com.duanrong.business.invest.model.InvestSubLoan;
import com.duanrong.business.invest.model.PassThrough;
import com.duanrong.business.invest.service.InvestService;
import com.duanrong.business.loan.LoanConstants;
import com.duanrong.business.loan.dao.LoanDao;
import com.duanrong.business.loan.model.Loan;
import com.duanrong.business.loan.service.LoanService;
import com.duanrong.business.msg.service.EmailService;
import com.duanrong.business.repay.service.RepayService;
import com.duanrong.business.user.service.RedPacketService;
import com.duanrong.business.user.service.UserService;
import com.duanrong.util.ArithUtil;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2014-9-1 上午11:31:26
 * @Description : drsoa Maven Webapp com.duanrong.business.invest.service.impl
 *              InvestServiceImpl.java
 * 
 */
@Service
public class InvestServiceImpl implements InvestService {

	final Lock lock = new ReentrantLock();

	@Resource
	InvestDao investDao;

	@Resource
	LoanDao loanDao;

	@Resource
	LoanService loanService;

	@Resource
	RepayService repayService;

	@Resource
	Log log;

	@Resource
	EmailService emailService;

	@Resource
	UserService userService;

	@Resource
	RedPacketService redpacketService;

	@Resource
	AutoInvestService autoInvestService;

	@Resource
	UserAccountService userAccountService;
	/**
	 * 分页查询
	 * 
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页显示的记录数
	 */
	public PageData<Invest> findPaging(int pageNo, int pageSize, Invest invest) {
		return investDao.findPaging(pageNo, pageSize, invest);
	}

	public Double getInvestTotalMoney(String userId) {
		Double investTotalMoney = investDao.getInvestTotalMoney(userId);
		if (investTotalMoney == null) {
			return 0.00;
		}

		return ArithUtil.round(investTotalMoney.doubleValue(), 2);
	}

	public Double getInvestsTotalInterest(String userId) {
		Double InvestsTotalInterest = investDao.getInvestsTotalInterest(userId);
		if (InvestsTotalInterest == null) {
			return 0.00;
		}

		return ArithUtil.round(InvestsTotalInterest.doubleValue(), 2);
	}

	
	@Override
	public Double getPersonalInvestCeiling(String userId, String loanId) {
		return investDao.getPersonalInvestCeiling(userId, loanId);
	}
	
	@Override
	public Long getInvestNums() {
		Long d = 0l;
		d = investDao.getInvestNums();
		if (d != null)
			return d;
		else
			return (long) 0;
	}

	/**
	 * 
	 * @description 查询所有投资记录
	 * @author 孙铮
	 * @time 2014-9-1 下午12:05:44
	 * @return
	 */
	public List<Invest> findAll() {
		List<Invest> invests = investDao.findAll();
		return invests;
	}

	/**
	 * 
	 * @description 根据id查询单条投资记录
	 * @author 孙铮
	 * @time 2014-9-1 下午12:07:41
	 * @param id
	 * @return
	 */
	public Invest get(String id) {
		Invest invest = investDao.get(id);
		return invest;
	}

	public void update(Invest invest) {
		investDao.update(invest);
	}

	public List<Invest> getInvestByLoan(Invest invest) {
		return investDao.getInvestByLoanSortAsc(invest);
	}

	public Long getInvestCountByloanId(String loanId, String status) {
		return investDao.GetInvestCountByLoanId(loanId, status);

	}

	public List<Invest> getInvestInterestDetail(String userId) {
		return investDao.getInvestInterestDetail(userId);
	}

	public List<Invest> getInvestLoan(Invest invest) {
		return investDao.getInvestLoan(invest);
	}

	public Double getInvestLoanTotalMoney(Invest invest) {
		return investDao.getInvestLoanTotalMoney(invest);
	}

	public Double getInvestLoanTotalInterest(Invest invest) {
		return investDao.getInvestLoanTotalInterest(invest);
	}

	public Double getInvestLoanTotalPaidInterest(Invest invest) {
		return investDao.getInvestLoanTotalPaidInterest(invest);
	}

	public Double getInvestMoney(Invest invest) {

		if (!StringUtils.equals(invest.getStatus(), "!流标")) {
			String[] conditions = StringUtils.split(invest.getStatus(), ",");
			invest.setConditions(conditions);
		}

		Double money = investDao.getInvestMoney(invest);
		if (money == null) {
			return 0.00;
		}

		return money;
	}

	@Override
	public void syncInvest(Invest invest, String type)
			throws ExceedMoneyNeedRaised, InsufficientBalance,
			RedPacketUseException, InvestMoneyException {
		synchronized (lock) {
			if (StringUtils.equals(type, "S2SSuccess")) {
				// 必须保证invest表的投资状态改为投资成功
				invest.setStatus(InvestConstants.InvestStatus.BID_SUCCESS);
				update(invest);
				return;
			}
			Loan loan = invest.getLoan();
		
			String loanStatus = loan.getStatus();
			String userId = invest.getInvestUserID();
			if (StringUtils.equals(type, "S2SFail")) {
				invest.setStatus(InvestConstants.InvestStatus.CANCEL);

				try {
					log.infoLog("加息券返还", "loanId：" + loan.getId()
							+ ", investId：" + invest.getId() + "redpacketId："
							+ invest.getRedpacketId() + "userId：" + userId);
					redpacketService.userRedPacket(invest, "fail");
					invest.setRedpacketId(0); // 红包使用失败，跟新invest表为未使用红包
				} catch (RedPacketUseException e) {
					throw e;
				}
				investDao.update(invest);
				// 改项目状态，项目如果是等待复核的状态，改为筹款中
				if (StringUtils.equals(loanStatus,
						LoanConstants.LoanStatus.RECHECK)) {
					loan.setStatus(LoanConstants.LoanStatus.RAISING);

					loanService.update(loan);
				}

				// 解冻投资金额
				try {
					userAccountService.unfreeze(invest.getInvestUserID(),
							invest.getMoney(), BusinessEnum.bidders, "解冻：投资"
									+ invest.getLoan().getName(), "借款ID："
									+ invest.getLoan().getId(), invest.getId());
				} catch (Exception e) {
					log.errLog("解冻投资金额失败", e);
				}
				return;
			}

		}
	}

	@Override
	public List<Invest> getInvestByUserAndLoan(String userId, String loanId) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("loanId", loanId);
		return investDao.getInvestByUserAndLoan(map);
	}

	@Override
	public Invest getInvest(String id) {
		return investDao.getInvest(id);
	}

	@Override
	public List<Invest> getInvestByDate(Date date) {

		return investDao.getInvestByDate(date);
	}

	@Override
	public int getCountInvestByRedpacketForVisitor(String userId) {

		return investDao.getCountInvestByRedpacketForVisitor(userId);
	}

	@Override
	public Date getFirstAvilidInvestTimeByUserId(String userId) {

		return investDao.getFirstAvilidInvestTimeByUserId(userId);
	}

	@Override
	public List<InvestRedpacket> getInvestRedpacketList() {
		return investDao.getInvestRedpacketList();
	}

	@Override
	public void updatetInvestRedpacket(InvestRedpacket investRedpacket) {
		investDao.updatetInvestRedpacket(investRedpacket);

	}

	@Override
	public Double getCurrentInvestMoney(String userId) {
		return this.investDao.getCurrentInvestMoney(userId);
	}

	@Override
	public List<String> getNewInvestUser() {
		return this.investDao.getNewInvestUser();
	}

	@Override
	public Double getUserInvestMoney(String userId) {
		return this.investDao.getUserInvestMoney(userId);
	}

	@Override
	public int getCurrentInvestMoney4DoubleElevec(String userId,String createTime) {
		return this.investDao.getCurrentInvestMoney4DoubleElevec(userId,createTime);
	}

	@Override
	public void update4DoubleEleven(PassThrough passThrough) {
		this.investDao.update4DoubleEleven(passThrough);
		
	}

	@Override
	public int getCurrentInvestMoney4DoubleElevec1(String userId) {
		return this.investDao.getCurrentInvestMoney4DoubleElevec1(userId);
	}

	@Override
	public List<String> getHasRepayUserId() {
		return investDao.getHasRepayUserId();
	}

	@Override
	public List<Invest> getInvestByStatus() {
		return investDao.getInvestByStatus();
	}
	
	@Override
	public List<InvestSubLoan> getInvestSubloanByStatus() {
		return investDao.getInvestSubloanByStatus();
	}

}
