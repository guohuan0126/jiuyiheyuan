package com.duanrong.business.ruralfinance.service.imp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.poi.ExcelConvertor;
import base.pagehelper.PageInfo;

import com.duanrong.business.demand.dao.DemandTreasureLoanDao;
import com.duanrong.business.demand.model.DemandtreasureLoan;
import com.duanrong.business.ruralfinance.dao.AgricultureLoanerInfoDao;
import com.duanrong.business.ruralfinance.dao.AgricultureRepaymentPlanDao;
import com.duanrong.business.ruralfinance.model.AgricultureForkLoans;
import com.duanrong.business.ruralfinance.model.AgricultureLoaninfo;
import com.duanrong.business.ruralfinance.model.AgricultureRepaymentPlan;
import com.duanrong.business.ruralfinance.model.AgricultureZhongjinbank;
import com.duanrong.business.ruralfinance.model.LoanerinfoExport;
import com.duanrong.business.ruralfinance.model.Template;
import com.duanrong.business.ruralfinance.service.AgricultureLoanerInfoService;
import com.duanrong.business.ruralfinance.service.AgricultureRepaymentPlanService;
import com.duanrong.business.ruralfinance.service.AgricultureZhongjinbankService;
import com.duanrong.newadmin.utility.ArithUtil;
import com.duanrong.newadmin.utility.DateUtil;
import com.duanrong.newadmin.utility.ForkloansCompute;
import com.duanrong.newadmin.utility.InterestAccrual;
import com.duanrong.newadmin.utility.IdUtil;


@Service
public class AgricultureLoanerInfoServiceImpl implements
		AgricultureLoanerInfoService {

	@Resource
	private AgricultureLoanerInfoDao loanerInfoDao;
	@Resource
	private AgricultureRepaymentPlanDao agricultureRepaymentPlanDao;
	@Resource
	private DemandTreasureLoanDao demandTreasureLoandao;

	@Override
	public PageInfo<AgricultureLoaninfo> readAgricultureLoaninfo(int pageNo,
			int pageSize, Map<String, Object> params) {
		return loanerInfoDao.readAgricultureLoaninfo(pageNo, pageSize, params);
	}

	@Override
	public List<AgricultureRepaymentPlan> findAllPlan() {

		return loanerInfoDao.findAllPlan();
	}

	@Override
	public PageInfo<AgricultureForkLoans> readAgricultureForkLoans(int pageNo,
			int pageSize, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return loanerInfoDao.readAgricultureForkLoans(pageNo, pageSize, params);
	}

	@Override
	public PageInfo<AgricultureRepaymentPlan> readRuralfinanceRepaymentPlan(
			int pageNo, int pageSize, Map<String, Object> params) {
		PageInfo<AgricultureRepaymentPlan> page = loanerInfoDao
				.readRuralfinanceRepaymentPlan(pageNo, pageSize, params);
		// TODO Auto-generated method stub
		return page;
	}

	@Override
	public AgricultureLoaninfo readAgricultureLoaninfoById(String id) {
		// TODO Auto-generated method stub
		return loanerInfoDao.readAgricultureLoaninfoById(id);
	}

	@Override
	public void updateStatusById(Map<String, Object> params) {
		loanerInfoDao.updateStatusById(params);

	}

	@Override
	public PageInfo<AgricultureForkLoans> readAgricultureForkLoansById(
			int pageNo, int pageSize, String id) {
		// TODO Auto-generated method stub
		return loanerInfoDao.readAgricultureForkLoansById(pageNo, pageSize, id);
	}

	@Override
	public void updateForkStatusById(Map<String, Object> params) {
		loanerInfoDao.updateForkStatusById(params);

	}

	@Override
	public void renewalLatePenalty(Map<String, Object> map) {
		loanerInfoDao.renewalLatePenalty(map);

	}

	@Override
	public void updateRepaymentplanStatus(Map<String, Object> params) {
		loanerInfoDao.updateRepaymentplanStatus(params);

	}

	@Override
	public AgricultureLoaninfo readAgricultureLoaninfoByIdAndFlag(String id) {
		// TODO Auto-generated method stub
		return loanerInfoDao.readAgricultureLoaninfoByIdAndFlag(id);
	}

	@Override
	public double readneedRepayMoney(String loanDataId) {
		// TODO Auto-generated method stub
		return loanerInfoDao.readneedRepayMoney(loanDataId);
	}

	@Override
	public List<AgricultureRepaymentPlan> readRuralfinanceFailRepaymentPlan() {
		// TODO Auto-generated method stub
		return loanerInfoDao.readRuralfinanceFailRepaymentPlan();
	}

	@Override
	public void updateFlag(Map<String, Object> map) {
		loanerInfoDao.updateFlag(map);

	}

	@Override
	public List<Template> readTemplate(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return loanerInfoDao.readTemplate(params);
	}
	@Override
	public List<Template> readTimeTemplate(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return loanerInfoDao.readTimeTemplate(params);
	}
	@Override
	public List<LoanerinfoExport> readLoanerinfoExport() {
		return loanerInfoDao.readLoanerinfoExport();
	}

	@Override
	public List<AgricultureLoaninfo> readAgricultureLoaninfo(
			Map<String, Object> params) {
		// TODO Auto-generated method stub
		return loanerInfoDao.readAgricultureLoaninfo(params);
	}

	@Override
	public List<AgricultureRepaymentPlan> readRuralfinanceRepaymentPlan(
			Map<String, Object> params) {

		return loanerInfoDao.readRuralfinanceRepaymentPlan(params);
	}

	@Override
	public List<AgricultureLoaninfo> readAgriculturePacking(
			Map<String, Object> params) {
		return loanerInfoDao.readAgriculturePacking(params);
	}

	@Override
	public AgricultureLoaninfo readAgricultureByLoanerinfoId(String id) {

		return loanerInfoDao.readAgricultureByLoanerinfoId(id);
	}

	@Override
	public void updatePackStatus(Map<String, Object> params) {
		loanerInfoDao.updatePackStatus(params);
	}

	@Override
	public void updateTimelyLoaninfo(Map<String, Object> params) {
		loanerInfoDao.updateTimelyLoaninfo(params);

	}

	@Override
	public List<AgricultureRepaymentPlan> readTimelyloansRepaymentPlan(String id) {

		return loanerInfoDao.readTimelyloansRepaymentPlan(id);
	}

	@Override
	public AgricultureLoaninfo readAgricultureBycontractId(
			Map<String, Object> params) {
		// TODO Auto-generated method stub
		return loanerInfoDao.readAgricultureBycontractId(params);
	}

	@Override
	public List<AgricultureRepaymentPlan> findBySubContractId(
			String subContractId) {

		return loanerInfoDao.findBySubContractId(subContractId);
	}

	@Override
	public void updateLoaninfobank(Map<String, Object> params) {
		loanerInfoDao.updateLoaninfobank(params);

	}

	@Override
	public AgricultureRepaymentPlan readRepaymentPlanById(String id) {

		return loanerInfoDao.readRepaymentPlanById(id);
	}

	@Override
	public List<AgricultureForkLoans> readAgricultureForkLoans(
			Map<String, Object> params) {
		return loanerInfoDao.readAgricultureForkLoans(params);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean editLeekPrepayment(String contractId, String loanTerm,
			String settle) {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			List<AgricultureRepaymentPlan> repaymentlist = new ArrayList<>();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", contractId);
			List<AgricultureRepaymentPlan> planList = loanerInfoDao.readRuralfinanceRepaymentPlan(params);
			if (planList != null && planList.size() > 0) {
				Date endtime = DateUtil.addMonth(
						planList.get(0).getRepayDate(),
						Integer.parseInt(loanTerm) - 1);
				String nowDate = df.format(new Date());
				String parentDate = df.format(endtime);
				double shengyu = 0.0;
				// 要结束的还款时间要大于当前时间
				if (DateUtil.dayDifference(nowDate, parentDate) > 0) {
					for (int i = 0; i < planList.size(); i++) {
						AgricultureRepaymentPlan repaymentPlan = new AgricultureRepaymentPlan();
						String agriculturalType = planList.get(i).getAgriculturalType();
						double corpus = planList.get(i).getCorpus();
						shengyu += corpus;// 前n期的本金和
						if (i + 1 == Integer.parseInt(loanTerm)) {							
							// 如果线下已经还清处理还款计划和还款数据
							if ("是".equals(settle)) {
								repaymentPlan.setOperationTime(endtime);
								repaymentPlan.setRepayStatus("finish");
								if (i + 1 == planList.size()) {
									repaymentPlan.setMonthMoney(planList.get(i)
											.getMonthMoney());
									repaymentPlan.setCorpus(planList.get(i)
											.getCorpus());
									repaymentPlan.setRealrepayMoney(planList
											.get(i).getMonthMoney());
									repaymentPlan.setServiceFee(planList.get(i)
											.getServiceFee());//服务费
								} else {
									double serviceMoney = InterestAccrual.getServiceMoney(planList.get(i).getMoney(), planList.get(i).getServiceMoney(), planList.get(i).getLoanTerm(), Integer.parseInt(loanTerm));
									//如果是韭农贷或者惠牧贷测不退还服务费
									if(agriculturalType!=null && ("韭农贷".equals(agriculturalType)||"惠牧贷".equals(agriculturalType))){
									 serviceMoney = 0.0;
									}
									//提前还款的金额=合同金额-前n月还款的本金-退还的服务费
									double repayMoney=planList.get(i).getMoney() - shengyu-serviceMoney;
									repaymentPlan
											.setMonthMoney(ForkloansCompute.round(planList.get(i).getMonthMoney()+ repayMoney,2));
									repaymentPlan
											.setCorpus(ForkloansCompute.round(planList.get(i).getCorpus()+ repayMoney,2));
									repaymentPlan
											.setRealrepayMoney(ForkloansCompute.round(planList.get(i).getMonthMoney()+ repayMoney,2));
									repaymentPlan.setServiceFee(planList.get(i)
											.getServiceFee());//服务费
									repaymentPlan.setReturnMoney(serviceMoney);//应退还服务费
									repaymentPlan.setEarlyRepayment(1);//是否提前还款
								}
								repaymentPlan.setIntereat(planList.get(i)
										.getIntereat());
								repaymentPlan.setLatePenalty(planList.get(i)
										.getLatePenalty());
							 }else {
								// 如果没有还清走中金的扣款流程，处理还款计划和还款数据
								// repaymentPlan.setOperationTime(endtime);
								if (i + 1 == planList.size()) {
									repaymentPlan.setMonthMoney(planList.get(i)
											.getMonthMoney());
									repaymentPlan.setCorpus(planList.get(i)
											.getCorpus());
									repaymentPlan.setServiceFee(planList.get(i)
											.getServiceFee());//服务费
								} else {
									double serviceMoney = InterestAccrual.getServiceMoney(planList.get(i).getMoney(), planList.get(i).getServiceMoney(), planList.get(i).getLoanTerm(), Integer.parseInt(loanTerm));
									//如果是韭农贷或者惠牧贷测不退还服务费
									if(agriculturalType!=null && ("韭农贷".equals(agriculturalType)||"惠牧贷".equals(agriculturalType))){
									 serviceMoney = 0.0;
									}
									//提前还款的金额=合同金额-前n月还款的本金-退还的服务费
									double repayMoney=planList.get(i).getMoney() - shengyu-serviceMoney;
										repaymentPlan
											.setMonthMoney(ForkloansCompute.round(planList.get(i).getMonthMoney()+ repayMoney,2));
									repaymentPlan
											.setCorpus(ForkloansCompute.round(planList.get(i).getCorpus()+ repayMoney,2));
									repaymentPlan.setServiceFee(planList.get(i)
											.getServiceFee());//服务费
									repaymentPlan.setReturnMoney(serviceMoney);//应退还服务费
									repaymentPlan.setEarlyRepayment(1);//是否提前还款
								}
								repaymentPlan.setIntereat(planList.get(i)
										.getIntereat());
								repaymentPlan.setRealrepayMoney(planList.get(i)
										.getRealrepayMoney());
								repaymentPlan.setLatePenalty(planList.get(i)
										.getLatePenalty());
							}
							repaymentPlan.setId(planList.get(i).getId());
							agricultureRepaymentPlanDao
									.updateTimlyRepaymentplan(repaymentPlan);
						} else if (i + 1 > Integer.parseInt(loanTerm)) {
							repaymentPlan.setId(planList.get(i).getId());
							repaymentPlan.setOperationTime(endtime);
							repaymentPlan.setMonthMoney(0);
							repaymentPlan.setCorpus(0);
							repaymentPlan.setIntereat(0);
							repaymentPlan.setLatePenalty(0);
							repaymentPlan.setRealrepayMoney(0);
							repaymentPlan.setRepayStatus("finish");
							agricultureRepaymentPlanDao
									.updateTimlyRepaymentplan(repaymentPlan);
						}
					}
					// 2.更新主标借款信息的还款信息
					Map<String, Object> loanerinfoprm = new HashMap<>();
					loanerinfoprm.put("contractId", contractId);
					loanerinfoprm.put("whetherEarlyRepayment", "1");
					loanerinfoprm.put("actualLoanTerm", loanTerm);
					loanerinfoprm.put("actualEndTime", endtime);
					if ("是".equals(settle)) {
						loanerinfoprm.put("status", "finish");
					}
					loanerInfoDao.updateTimelyLoaninfo(loanerinfoprm);
					// 3.修改线上的项目结束时间
					// 根据合同编号查询所有的子标信息，循环子标，并判断子标id是否在活期宝资产中存在，存在并且项目没有结束的更改项目结束日期
					Map<String, Object> ForkLoansParam = new HashMap<>();
					ForkLoansParam.put("contractId", contractId);
					List<AgricultureForkLoans> ForkLoansList = loanerInfoDao
							.readAgricultureForkLoans(ForkLoansParam);
					for (int j = 0; j < ForkLoansList.size(); j++) {
						String forkId = ForkLoansList.get(j).getId();
						DemandtreasureLoan demand = demandTreasureLoandao
								.readByForkId(forkId);
						if (demand != null) {
							Map<String, Object> demandTimePara = new HashMap<>();
							demandTimePara.put("forkId", forkId);
							demandTimePara.put("finishTime", endtime);
							demandTreasureLoandao
									.updatedemandFinishTime(demandTimePara);
						}
					}
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	// 逾期罚息用到的方法，试用于农贷，及时贷,惠牧贷
	@Override
	public void calculationFaxi(String deadlineTime) {
		// 本金和利息,畜牧贷时引用
		double totalMoney = 0.0;
		double restMoney = 0.0;
		List<AgricultureRepaymentPlan> list2 = loanerInfoDao.findAllPlan();

		int yuqiTime;
		// 罚息
		double money = 0.0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
		try {
			for (AgricultureRepaymentPlan agricultureRepaymentPlan : list2) {
				// 惠牧贷合同中包含"_"
				if (agricultureRepaymentPlan.getContractId().contains("_")) {
					String subContractId = agricultureRepaymentPlan
							.getContractId().substring(
									0,
									agricultureRepaymentPlan.getContractId()
											.indexOf("_"));
					List<AgricultureRepaymentPlan> list4 = loanerInfoDao
							.findBySubContractId(subContractId);
					for (AgricultureRepaymentPlan agricultureRepaymentPlan2 : list4) {
						totalMoney += agricultureRepaymentPlan2.getMonthMoney();
						if (agricultureRepaymentPlan2.getRepayStatus() != null
								&& (agricultureRepaymentPlan2.getRepayStatus()
										.equals("unrepay") || agricultureRepaymentPlan2
										.getRepayStatus().equals("repayfail"))) {
							restMoney += agricultureRepaymentPlan2
									.getMonthMoney();
						}
					}
				}

				// 应还日期
				String repayDate = sdf.format(agricultureRepaymentPlan
						.getRepayDate());
				// 当前时间
				String today = sdf.format(new Date());
				if (!StringUtils.isBlank(deadlineTime)) {
					today = deadlineTime;
				}
				yuqiTime = DateUtil.DayDifference(today, repayDate);
				if (yuqiTime > 0) {
					if (agricultureRepaymentPlan.getRepayStatus().equals(
							"unrepay")
							|| agricultureRepaymentPlan.getRepayStatus()
									.equals("repayfail")) {
						if (agricultureRepaymentPlan.getAgriculturalType() != null
								&& !agricultureRepaymentPlan
										.getAgriculturalType().equals("")) {
							if (agricultureRepaymentPlan.getAgriculturalType()
									.equals("三农")
									&& "等额本息".equals(agricultureRepaymentPlan
											.getRepayType())) {
								if (yuqiTime <= 3) {
									money = ArithUtil
											.round(agricultureRepaymentPlan
													.getMonthMoney() * 0.002, 2)
											* yuqiTime;
								} else if (yuqiTime > 3) {
									money = ArithUtil.round(
											(agricultureRepaymentPlan
													.getLoanTerm()
													- agricultureRepaymentPlan
															.getPeriod() + 1)
													* agricultureRepaymentPlan
															.getMonthMoney()
													* 0.002, 2)
											* yuqiTime;

								}

							}

							else if (agricultureRepaymentPlan
									.getAgriculturalType().equals("及时贷")
									&& "先息后本".equals(agricultureRepaymentPlan
											.getRepayType())) {
								if (yuqiTime <= 3) {
									// 3天以内实际还款数应为合同金额的万分之五
									money = ArithUtil
											.round(agricultureRepaymentPlan
													.getMoney() * 0.0005, 2)
											* yuqiTime;
								} else if (yuqiTime > 3) {
									// 3天以上实际还款数应为合同金额的千分之二
									money = ArithUtil
											.round(agricultureRepaymentPlan
													.getMoney() * 0.002, 2)
											* yuqiTime;
								}

							} else if (agricultureRepaymentPlan
									.getAgriculturalType().equals("惠牧贷")) {

								if (yuqiTime <= 3)

								{
									// 3天以内按照本息的1/3乘以0.2%
									money = ArithUtil.round(
											(totalMoney / 3) * 0.002, 2)
											* yuqiTime;
								} else if (yuqiTime > 3) {
									// 罚息按照剩余本息的0.2%
									money = ArithUtil.round(restMoney * 0.002,
											2) * yuqiTime;
								}
							}
							if (money > 0) {
								Map<String, Object> map = new HashMap<String, Object>();
								map.put("id", agricultureRepaymentPlan.getId());
								map.put("latePenalty", money);
								loanerInfoDao.renewalLatePenalty(map);
							}

						}

					}
				}
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

}
