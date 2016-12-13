package com.duanrong.business.demand.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import base.pagehelper.PageInfo;

import com.duanrong.business.demand.dao.DemandTreasureLoanDao;
import com.duanrong.business.demand.model.DemandtreasureLoan;
import com.duanrong.business.demand.service.DemandTreasureLoanService;
import com.duanrong.business.ruralfinance.model.AgricultureForkLoans;
import com.duanrong.business.ruralfinance.model.AgricultureLoaninfo;
import com.duanrong.business.ruralfinance.service.AgricultureForkLoansService;
import com.duanrong.business.ruralfinance.service.AgricultureLoanerInfoService;
import com.duanrong.newadmin.model.UserCookie;
import com.duanrong.newadmin.utility.DateUtil;
import com.duanrong.newadmin.utility.ForkloansCompute;
import com.duanrong.util.IdGenerator;

@Service
public class DemandTreasureLoanServiceImpl implements DemandTreasureLoanService {
	@Resource
	DemandTreasureLoanDao demandTreasureLoanDao;

	@Resource
	AgricultureForkLoansService agricultureForkLoansService;
	@Resource
	AgricultureLoanerInfoService agricultureLoanerInfoService;

	private static final byte[] lock = new byte[0];

	@Override
	public PageInfo<DemandtreasureLoan> readPageInfo(int pageNo, int pageSize,
			DemandtreasureLoan d) {
		return demandTreasureLoanDao.readPageInfo(pageNo, pageSize, d);
	}

	@Override
	public void update(DemandtreasureLoan demandtreasureLoan) {
		demandTreasureLoanDao.update(demandtreasureLoan);
	}

	@Override
	public void insert(DemandtreasureLoan demandtreasureLoan) {
		demandTreasureLoanDao.insert(demandtreasureLoan);
	}

	@Override
	public DemandtreasureLoan read(String id) {
		return demandTreasureLoanDao.get(id);
	}

	@Override
	public void updateLoan(DemandtreasureLoan d) {
		demandTreasureLoanDao.updateLoan(d);
	}

	@Override
	public List<DemandtreasureLoan> readexportList(DemandtreasureLoan d) {
		// TODO Auto-generated method stub
		return demandTreasureLoanDao.exportList(d);

	}

	@Override
	public int insertBatch(List<DemandtreasureLoan> list) {
		return demandTreasureLoanDao.insertBatch(list);

	}

	@Override
	public DemandtreasureLoan readByForkId(String id) {

		return demandTreasureLoanDao.readByForkId(id);
	}

	@Override
	public double readMoneyBydate(String date) {

		return demandTreasureLoanDao.readMoneyBydate(date);
	}

	@Override
	public double readAssignmentMoney() {
		// TODO Auto-generated method stub
		return demandTreasureLoanDao.readAssignmentMoney();
	}

	@Override
	public void updateAssignmentMoneyById(Map<String, Object> params) {
		// TODO Auto-generated method stub
		demandTreasureLoanDao.updateAssignmentMoneyById(params);
	}

	@Override
	public List<DemandtreasureLoan> readDemandtreasureLoan(
			Map<String, Object> params) {
		// TODO Auto-generated method stub
		return demandTreasureLoanDao.readDemandtreasureLoan(params);
	}

	@Override
	public List<DemandtreasureLoan> readDemandtreasureLoanIds() {
		// TODO Auto-generated method stub
		return demandTreasureLoanDao.readDemandtreasureLoanIds();
	}

	@Override
	public void updateAssignmentStatusByIds(Map<String, Object> params) {
		demandTreasureLoanDao.updateAssignmentStatusByIds(params);
	}

	@Override
	public void updateAssignmentStatusById(Map<String, Object> params) {
		demandTreasureLoanDao.updateAssignmentStatusById(params);
	}

	@Override
	public List<DemandtreasureLoan> readByAvailableId(Map<String, Object> params) {

		return demandTreasureLoanDao.readByAvailableId(params);
	}

	@Override
	public List<DemandtreasureLoan> readByAvailableIdNew(
			Map<String, Object> params) {
		return demandTreasureLoanDao.readByAvailableIdNew(params);
	}

	/**
	 * 把农贷等额本息的子标批量添加到活期宝资产里面，要么都成功要么都失败
	 */
	@Override
	public String insertTreasureLoan(String ids, String userId) {
		List<DemandtreasureLoan> list = new ArrayList<DemandtreasureLoan>();
		String[] arr = ids.split(",");
		synchronized (lock) {
			for (String id : arr) {
				DemandtreasureLoan demand = this.readByForkId(id);
				if (demand == null) {
					AgricultureForkLoans agforkLoans = agricultureForkLoansService
							.readByForkId(id);
					// System.out.println(ids[id]);
					DemandtreasureLoan treasureLoan = new DemandtreasureLoan();
					treasureLoan.setId(IdGenerator.randomUUID());
					treasureLoan.setLoanStatus("repay");
					treasureLoan.setLoanType("金农宝");
					treasureLoan.setTotalMoney(agforkLoans.getMoney());
					int loanTrem = agforkLoans.getLoanTerm();
					Date createTime = new Date();
					treasureLoan.setStartTime(createTime);
					Date endDate = DateUtil.addMonth(createTime, loanTrem);
					treasureLoan.setFinishTime(endDate);
					treasureLoan.setCreateTime(createTime);
					treasureLoan.setCreator(userId);
					treasureLoan.setOperationType("月");
					treasureLoan.setMonth(loanTrem);
					treasureLoan.setBorrower(agforkLoans.getLoaninfo()
							.getName());
					treasureLoan.setIdCard(agforkLoans.getLoaninfo()
							.getIdCard());
					treasureLoan.setMaritalStatus(String.valueOf(agforkLoans
							.getLoaninfo().getMaritalStatus()));
					treasureLoan.setLocation(agforkLoans.getLoaninfo()
							.getAddress());
					treasureLoan.setForkId(id);
					treasureLoan.setBorrowingPurposes(agforkLoans.getLoaninfo()
							.getLoanApplication());
					treasureLoan.setSourceOfRepayment(agforkLoans.getLoaninfo()
							.getRepaymentSource());
					treasureLoan.setOpenAmount(agforkLoans.getMoney());
					treasureLoan.setLoanAddr(agforkLoans.getLoaninfo()
							.getProvince()
							+ agforkLoans.getLoaninfo().getCity());
					//把核算公司添加到活期宝资产里便于统计(后来添加)
					treasureLoan.setAccountingDepartment(agforkLoans.getLoaninfo().getAccountingDepartment());
					treasureLoan.setLoaninfoId("");
					list.add(treasureLoan);
				}
			}

			try {
				if (list != null && list.size() > 0) {
					insertBatch(list);
					Map<String, Object> params = new HashMap<>();
					params.put("packing", 1);
					params.put("flag", "demand");
					params.put("arr", arr);
					agricultureForkLoansService.updatePackStatus(params);
				}
				return "success";
			} catch (Exception e) {
				e.printStackTrace();
				return "error";
			}
		}

	}

	@Override
	public List<DemandtreasureLoan> readTransferDemandtreasureLoan() {
		return demandTreasureLoanDao.readTransferDemandtreasureLoan();
	}

	/**
	 * 把农贷及时贷先息后本的项目批量添加到活期宝资产里面，要么都成功要么都失败
	 * 
	 */
	@Override
	public String insertTreasureLoanTimely(String ids, String userId) {
		List<DemandtreasureLoan> list = new ArrayList<DemandtreasureLoan>();
		String[] arr = ids.split(",");
		synchronized (lock) {
			for (String id : arr) {
				DemandtreasureLoan demand = this.readByLoaninfoId(id);
				if (demand == null) {
					AgricultureLoaninfo loanerinfo = agricultureLoanerInfoService
							.readAgricultureByLoanerinfoId(id);
					// System.out.println(ids[id]);
					DemandtreasureLoan treasureLoan = new DemandtreasureLoan();
					treasureLoan.setId(IdGenerator.randomUUID());
					treasureLoan.setLoanStatus("repay");
					treasureLoan.setLoanType("金农宝");
					treasureLoan.setTotalMoney(loanerinfo.getMoney());
					int loanTrem = loanerinfo.getLoanTerm();
					Date createTime = new Date();
					treasureLoan.setStartTime(createTime);
					Date endDate = DateUtil.addMonth(createTime, loanTrem);
					treasureLoan.setFinishTime(endDate);
					treasureLoan.setCreateTime(createTime);
					treasureLoan.setCreator(userId);
					treasureLoan.setOperationType("月");
					treasureLoan.setMonth(loanTrem);
					treasureLoan.setBorrower(loanerinfo.getName());
					treasureLoan.setIdCard(loanerinfo.getIdCard());
					treasureLoan.setMaritalStatus(String.valueOf(loanerinfo
							.getMaritalStatus()));
					treasureLoan.setLocation(loanerinfo.getAddress());
					treasureLoan.setForkId("");
					treasureLoan.setBorrowingPurposes(loanerinfo
							.getLoanApplication());
					treasureLoan.setSourceOfRepayment(loanerinfo
							.getRepaymentSource());
					treasureLoan.setOpenAmount(loanerinfo.getMoney());
					treasureLoan.setLoanAddr(loanerinfo.getProvince()
							+ loanerinfo.getCity());
					treasureLoan.setLoaninfoId(id);
					//添加农贷的核算公司便于统计
					treasureLoan.setAccountingDepartment(loanerinfo.getAccountingDepartment());
					list.add(treasureLoan);
				}
			}
			try {
				if (list != null && list.size() > 0) {
					insertBatch(list);
					Map<String, Object> params = new HashMap<>();
					params.put("packingStatus", "1");
					params.put("arr", arr);
					agricultureLoanerInfoService.updatePackStatus(params);
				}
				return "success";
			} catch (Exception e) {
				e.printStackTrace();
				return "error";
			}
		}
	}

	@Override
	public DemandtreasureLoan readByLoaninfoId(String id) {
		return demandTreasureLoanDao.readByLoaninfoId(id);
	}

	@Override
	public int updateLoanerinfoTime(Map<String, Object> params) {
		return demandTreasureLoanDao.updateLoanerinfoTime(params);

	}

	@Override
	public List<DemandtreasureLoan> readDemadfork(String id) {
		return demandTreasureLoanDao.readDemadfork(id);
	}

	@Override
	public int updateDemandDisplay(Map<String, Object> params) {
		return demandTreasureLoanDao.updateDemandDisplay(params);
	}

	@Override
	public String addLoanCommon(DemandtreasureLoan demandtreasureLoan,
			UserCookie getLoginUser) {
		try {
			demandtreasureLoan.setId(IdGenerator.randomUUID());
			demandtreasureLoan.setCreateTime(new Date());
			demandtreasureLoan.setCreator(getLoginUser.getUserId());
			if (demandtreasureLoan.getSbuyTime() != "") {
				demandtreasureLoan.setBuyTime(DateUtil
						.StringToDate(demandtreasureLoan.getSbuyTime()));
			}
			if (demandtreasureLoan.getSfinishTime() != "") {
				demandtreasureLoan.setFinishTime(DateUtil
						.StringToDate(demandtreasureLoan.getSfinishTime()));
			}
			if (demandtreasureLoan.getSstartTime() != "") {
				demandtreasureLoan.setStartTime(DateUtil
						.StringToDate(demandtreasureLoan.getSstartTime()));
			}
			if (demandtreasureLoan.getOperationType().equals("月")) {
				int month = demandtreasureLoan.getMonth();
				demandtreasureLoan.setFinishTime(DateUtil.addMonth(
						demandtreasureLoan.getStartTime(), month));
			} else {
				int days = demandtreasureLoan.getDay();
				demandtreasureLoan.setFinishTime(DateUtil.addDay(
						demandtreasureLoan.getStartTime(), days));
			}
			this.insert(demandtreasureLoan);
			if ("车押宝".equals(demandtreasureLoan.getLoanType())
					&& "等额本息".equals(demandtreasureLoan.getRepayType())) {
				if (demandtreasureLoan.getMonth() != null
						&& demandtreasureLoan.getTotalMoney() != null) {
					int loan_term = demandtreasureLoan.getMonth();// 借款期限
					double money = demandtreasureLoan.getTotalMoney();// 借款总金额
					double rate = 0.18;
					double benjin = 0.0;
					double benjinhe = 0.0;
					String parentId = demandtreasureLoan.getId();
					List<DemandtreasureLoan> list = new ArrayList<DemandtreasureLoan>();
					for (int i = 1; i <= loan_term; i++) {
						DemandtreasureLoan demandforkLoans = new DemandtreasureLoan();
						demandforkLoans.setId(IdGenerator.randomUUID());
						//取100的整数倍
						/*benjin = Math.floor(ForkloansCompute.corpus(money,
								rate, loan_term, i) / 100) * 100;*/
						//取整，舍小数
						benjin = Math.floor(ForkloansCompute.corpus(money,
								rate, loan_term, i));
						if (i == loan_term) {
							benjin = money - benjinhe;
						}
						benjinhe += benjin;
						/*原等本金拆分
						 * demandforkLoans.setTotalMoney(ForkloansCompute.
						 * remainmoney(money, loan_term, i));
						 */
						demandforkLoans.setTotalMoney(benjin);
						demandforkLoans.setOperationType("月");
						demandforkLoans.setMonth(i);
						demandforkLoans.setLoanType(demandtreasureLoan
								.getLoanType());
						demandforkLoans.setLoanStatus(demandtreasureLoan
								.getLoanStatus());
						demandforkLoans.setRepayType("等额本息");
						demandforkLoans.setLoanAddr(demandtreasureLoan
								.getLoanAddr());
						demandforkLoans.setStartTime(demandtreasureLoan
								.getStartTime());
						demandforkLoans.setFinishTime(DateUtil.addMonth(
								demandtreasureLoan.getStartTime(), i));
						demandforkLoans.setBorrower(demandtreasureLoan
								.getBorrower());
						demandforkLoans.setIdCard(demandtreasureLoan
								.getIdCard());
						demandforkLoans.setBrand(demandtreasureLoan.getBrand());
						demandforkLoans
								.setLicensePlateNumber(demandtreasureLoan
										.getLicensePlateNumber());
						demandforkLoans.setBuyTime(demandtreasureLoan
								.getBuyTime());
						demandforkLoans.setKilometreAmount(demandtreasureLoan
								.getKilometreAmount());
						demandforkLoans.setAssessPrice(demandtreasureLoan
								.getAssessPrice());
						demandforkLoans.setGuaranteeType(demandtreasureLoan
								.getGuaranteeType());
						demandforkLoans.setGuaranteeRate(demandtreasureLoan
								.getGuaranteeRate());
						demandforkLoans.setOtherInfo(demandtreasureLoan
								.getOtherInfo());
						demandforkLoans.setCreator(getLoginUser.getUserId());
						demandforkLoans.setParentId(parentId);
						demandforkLoans.setContractId(demandtreasureLoan.getContractId());
						demandforkLoans.setSourceRemark(demandtreasureLoan.getSourceRemark());
						demandforkLoans.setAccountingDepartment(demandtreasureLoan.getAccountingDepartment());
						demandforkLoans.setCreateTime(new Date());
						list.add(demandforkLoans);
					}
					this.insertBatch(list);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
         e.printStackTrace();
         return "fail";
		}
		return "sucess";
	}

	@Override
	public int updatedemandFinishTime(Map<String, Object> params) {		
		return demandTreasureLoanDao.updatedemandFinishTime(params);
	}
}
