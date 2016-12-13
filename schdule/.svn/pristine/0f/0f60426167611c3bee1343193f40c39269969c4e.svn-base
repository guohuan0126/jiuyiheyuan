package com.duanrong.business.loan.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import util.Log;
import base.exception.InvestMoneyException;
import base.exception.NoMatchingObjectsException;
import base.model.PageData;
import base.pagehelper.PageInfo;
import com.duanrong.business.invest.dao.InvestDao;
import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.loan.LoanConstants;
import com.duanrong.business.loan.dao.ApplyEnterpriseLoanDao;
import com.duanrong.business.loan.dao.LoanDao;
import com.duanrong.business.loan.model.ApplyEnterpriseLoan;
import com.duanrong.business.loan.model.BannerPicture;
import com.duanrong.business.loan.model.Enterprise;
import com.duanrong.business.loan.model.House;
import com.duanrong.business.loan.model.Loan;
import com.duanrong.business.loan.model.LoanProgress;
import com.duanrong.business.loan.model.Vehicle;
import com.duanrong.business.loan.service.LoanDetailService;
import com.duanrong.business.loan.service.LoanService;
import com.duanrong.business.repay.dao.RepayDao;
import com.duanrong.business.repay.service.RepayService;
import com.duanrong.business.sms.service.SmsService;
import com.duanrong.business.trusteeship.service.TrusteeshipOperationService;
import com.duanrong.business.user.dao.UserDao;
import com.duanrong.business.user.model.User;
import com.duanrong.business.user.service.InformationService;
import com.duanrong.newadmin.utility.ArithUtil;
import com.duanrong.newadmin.utility.IdGenerator;

/**
 * 
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2015-3-11 下午2:03:03
 * @Description : NewAdmin com.duanrong.business.loan.service.impl
 *              LoanServiceImpl.java
 * 
 */
@Service("loanService")
public class LoanServiceImpl implements LoanService {
	
	@Resource
	LoanDao loanDao;

	@Resource
	InvestDao investDao;

	@Resource
	RepayService repayService;

	@Resource
	SmsService smsService;

	@Resource
	RepayDao repayDao;

	@Resource
	LoanDetailService loanDetailService;

	@Resource
	TrusteeshipOperationService trusteeshipOperationService;

	@Resource
	InformationService informationService;

	@Resource
	ApplyEnterpriseLoanDao applyEnterpriseLoanDao;

	@Resource
	UserDao userDao;
	
	@Resource
	Log log;


	/**
	 * 
	 * @description 查询所有借款记录
	 * @author 孙铮
	 * @time 2014-8-28 下午12:36:56
	 * @param loan
	 * @return
	 */
	public List<Loan> findAll() {
		List<Loan> loans = loanDao.findAll();
		return loans;
	}

	/**
	 * 
	 * @description 根据id查询单条借款记录
	 * @author 孙铮
	 * @time 2014-8-28 下午12:37:14
	 * @param id
	 * @return
	 */
	public Loan get(String id) {
		Loan loan = loanDao.get(id);
		return loan;
	}

	/**
	 * 
	 * @description 根据组合条件查询所有借款记录
	 * @author 孙铮
	 * @time 2014-8-28 下午5:29:29
	 * @param userID
	 * @return
	 */
	public List<Loan> getLoansByGroupCondition(Loan loan) {
		if (loan == null) {
			return null;
		} else {
			List<Loan> loans = loanDao.getLoansByGroupCondition(loan);
			return loans;
		}
	}

	/**
	 * 
	 * @description 根据组合条件查询所有借款记录
	 * @author xiao
	 * @time 2014-8-28 下午5:29:29
	 * @param userID
	 * @return
	 */
	public List<Loan> getLoansByGroupCondition1(Map<String, Object> map) {

		return loanDao.getLoansByGroupCondition1(map);

	}

	/**
	 * 
	 * @description 根据loanID查询对应借款人信息
	 * @author 孙铮
	 * @time 2014-8-28 下午5:40:44
	 * @param loanID
	 * @return
	 */
	public User getUserByLoanID(String loanID) {
		User user = loanDao.getUserByLoanID(loanID);
		return user;
	}

	/**
	 * 
	 * @description 根据loanID获取项目图片资料
	 * @author 孙铮
	 * @time 2014-8-30 上午10:51:37
	 * @param loanID
	 * @return
	 */
	public List<BannerPicture> getLoanInfoPics(String loanID) {
		List<BannerPicture> loanInfoPics = loanDao.getLoanInfoPics(loanID);
		return loanInfoPics;
	}

	/**
	 * 
	 * @description 根据loanID获取抵押物图片
	 * @author 孙铮
	 * @time 2014-8-30 上午10:51:37
	 * @param loanID
	 * @return
	 */
	public List<BannerPicture> getLoanGuaranteePics(String loanID) {
		List<BannerPicture> loanGuaranteePics = loanDao
				.getLoanGuaranteePics(loanID);
		return loanGuaranteePics;
	}

	public void update(Loan loan) {
		loanDao.update(loan);
	}

	public void dealRaiseComplete(String loanId)
			throws NoMatchingObjectsException {
		if (calculateMoneyNeedRaised(loanId) <= 0) {
			// 项目募集完成
			Loan loan = get(loanId);
			if (StringUtils.equals(loan.getStatus(),
					LoanConstants.LoanStatus.RAISING)) {
				loan.setStatus(LoanConstants.LoanStatus.RECHECK);
				update(loan);
			}
		}
	}

	public void verifyInvestMoney(String loanId, Double money)
			throws InvestMoneyException {
		Loan loan = this.get(loanId);
		Double investOriginMoney = loan.getInvestOriginMoney();
		Double increaseMoney = loan.getIncreaseMoney();

		if (money < investOriginMoney) {
			throw new InvestMoneyException("投资金额不能小于起点金额");
		}

		if (money % increaseMoney != 0) {
			throw new InvestMoneyException("投资金额与递增金额不符");
		}
	}

	public double calculateMoneyNeedRaised(String loanId) {
		Loan loan = this.get(loanId);
		if (loan == null) {
			return 0;
		}

		// 统计所有的此借款的投资信息，求和做减法，得出尚未募集到的金额。
		double validSumMoney = investDao.getValidInvestSumByLoan(loanId);
		double remain = ArithUtil.sub(loan.getTotalmoney(), validSumMoney);
		return remain < 0 ? 0 : remain;
	}

	/**
	 * 投资进度
	 */
	public double calculateRaiseCompletedRate(String loanId)
			throws NoMatchingObjectsException {
		double remainMoney = calculateMoneyNeedRaised(loanId);
		Loan loan = loanDao.get(loanId);
		double loanMoney = loan.getTotalmoney();
		return ArithUtil.round((loanMoney - remainMoney) / loanMoney * 100, 2);
	}

	/**
	 * 
	 * @description 根据组合条件查询所有借款记录
	 * @author 孙铮
	 * @time 2014-9-1 下午12:02:28
	 * @param invest
	 * @return
	 */
	public List<Invest> getInvestsByGroupCondition(Invest invest) {
		if (invest == null) {
			return null;
		} else {
			List<Invest> invests = loanDao.getInvestsByGroupCondition(invest);
			return invests;
		}
	}

	@Override
	public void insertEnterpriseLoan(ApplyEnterpriseLoan applyEnterpriseLoan) {
		applyEnterpriseLoan.setId(IdGenerator.randomUUID());
		applyEnterpriseLoan.setType("郑州车贷");
		applyEnterpriseLoan
				.setStatus(LoanConstants.ApplyEnterpriseLoanStatus.WAITING_VERIFY);
		applyEnterpriseLoan.setApplyTime(new Date());
		applyEnterpriseLoanDao.insert(applyEnterpriseLoan);
	}

	@Override
	public PageData<Loan> findPaging4Personal(int pageNo, int pageSize,
			Loan loan) {
		return loanDao.findPaging4Personal(pageNo, pageSize, loan);
	}

	@Override
	public Vehicle getVehicleDetail(String loanId) {
		return loanDetailService.getVehicleDetail(loanId);
	}

	@Override
	public House getHouseDetail(String loanId) {
		return loanDetailService.getHouseDetail(loanId);
	}

	@Override
	public Enterprise getEnterpriseDetail(String loanId) {
		return loanDetailService.getEnterpriseDetail(loanId);
	}

	@Override
	public Loan getLoanDetail(Loan loan) {
		String loanId = loan.getId();
		loan = loanDao.get(loanId);
		if (loan != null) {
			String loanType = loan.getLoanType();
			if (StringUtils.equals(loanType, LoanConstants.Type4Loan.VEHICLE)) {
				loan.setVehicle(getVehicleDetail(loanId));
			} else if (StringUtils.equals(loanType,
					LoanConstants.Type4Loan.HOUSE)) {
				loan.setHouse(getHouseDetail(loanId));
			} else if (StringUtils.equals(loanType,
					LoanConstants.Type4Loan.ENTERPRISE)) {
				loan.setEnterprise(getEnterpriseDetail(loanId));
			}

			return loan;
		}
		return null;
	}

	@Override
	public LoanProgress getLoanProgress(String loanType) {
		Double allMoney = loanDetailService.getAllMoney(loanType);
		Double sumMoney = loanDetailService.getSumMoney(loanType);
		Double investMoney = loanDetailService.getInvestMoney(loanType);
		LoanProgress loanProgress = new LoanProgress();
		loanProgress.setAllMoney(allMoney);
		loanProgress.setSumMoney(sumMoney);
		loanProgress.setInvestMoney(investMoney);
		if (sumMoney == 0) {
			loanProgress.setUnderway(false);
		} else {
			loanProgress.setUnderway(true);
		}
		return loanProgress;
	}

	@Override
	public Double getRemainingInvestmentAmount() {
		return loanDetailService.getRemainingInvestmentAmount();
	}

	@Override
	public void dealOverExpectTime(String loanId) {
		if (StringUtils.isBlank(loanId)) {
			log.errLog("项目过期调度", "项目编号不存在");
			return;
		}
		try {
			Loan loan = loanDao.get(loanId);
			log.infoLog("项目过期调度", loanId + ":" + LoanConstants.LoanStatus.DQGS);
			if (isOverExpectTime(loan)) {
				if (LoanConstants.LoanStatus.RAISING.equals(loan.getStatus())) {
					// 只有筹款中的借款，才能通过调度改成等待复核
					// loan.setStatus(LoanConstants.LoanStatus.RECHECK);
				} else if (LoanConstants.LoanStatus.DQGS.equals(loan
						.getStatus())) {
					// 只有贷前公示中的借款，才能通过调度改成筹款中
					loan.setStatus(LoanConstants.LoanStatus.RAISING);
				}
			}
			try {
				loanDao.update(loan);
			} catch (Exception e) {
				log.errLog("项目过期调度", e.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.errLog("项目过期调度", e);
		}
	}

	@Override
	public boolean isOverExpectTime(String loanId) {
		Loan loan = loanDao.get(loanId);
		if (loan == null) {
			return false;
		}

		if (new Date().before(loan.getExpectTime())) {
			return false;
		}
		return true;
	}

	@Override
	public PageInfo<Loan> pageLite(int pageNo, int pageSize, Loan loan) {
		return loanDao.pageLite(pageNo, pageSize, loan);
	}

	@Override
	public boolean isOverExpectTime(Loan loan) {
		if (loan == null) {
			return false;
		}

		if (new Date().before(loan.getExpectTime())) {
			return false;
		}
		return true;
	}

	@Override
	public double getTotalMoney(Map map) {
		if (loanDao.getTotalMoney(map) == null)
			return 0;
		else
			return loanDao.getTotalMoney(map);
	}

	@Override
	public List<Loan> findLoan(Loan loan) {
		return loanDao.find(loan);
	}

	@Override
	public List<Loan> getLoanForGaveMoneyToBorrower(int days) {
		
		return loanDao.getLoanForGaveMoneyToBorrower(days);
	}

	
}
