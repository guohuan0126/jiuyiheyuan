package com.duanrong.business.ruralfinance.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import base.pagehelper.PageInfo;

import com.duanrong.business.ruralfinance.model.AgricultureForkLoans;
import com.duanrong.business.ruralfinance.model.AgricultureLoaninfo;
import com.duanrong.business.ruralfinance.model.AgricultureRepaymentPlan;
import com.duanrong.business.ruralfinance.model.LoanerinfoExport;
import com.duanrong.business.ruralfinance.model.Template;

public interface AgricultureLoanerInfoService {

	PageInfo<AgricultureLoaninfo> readAgricultureLoaninfo(int pageNo,
			int pageSize, Map<String, Object> params);

	PageInfo<AgricultureForkLoans> readAgricultureForkLoans(int pageNo,
			int pageSize, Map<String, Object> params);

	PageInfo<AgricultureRepaymentPlan> readRuralfinanceRepaymentPlan(
			int pageNo, int pageSize, Map<String, Object> params);
	List<AgricultureRepaymentPlan> findAllPlan();
	AgricultureLoaninfo readAgricultureLoaninfoById(String id);
	
	AgricultureLoaninfo readAgricultureLoaninfoByIdAndFlag(String id);

	void updateStatusById(Map<String, Object> params);

	PageInfo<AgricultureForkLoans> readAgricultureForkLoansById(int pageNo,
			int pageSize, String id);

	void updateForkStatusById(Map<String, Object> params);

	void renewalLatePenalty(Map<String, Object> map);
	
	void updateRepaymentplanStatus(Map<String, Object> params);

	double readneedRepayMoney(String loanDataId);

	List<AgricultureRepaymentPlan> readRuralfinanceFailRepaymentPlan();
	
	void updateFlag(Map<String, Object> map);

	List<Template> readTemplate(Map<String, Object> params);
	List<Template> readTimeTemplate(Map<String, Object> params);
	public  List<LoanerinfoExport> readLoanerinfoExport();
	
	public List<AgricultureLoaninfo> readAgricultureLoaninfo(Map<String, Object> params);
	/**
	 * 查询还款计划并导出
	 * @param params
	 * @return
	 */
	public List<AgricultureRepaymentPlan> readRuralfinanceRepaymentPlan(Map<String, Object> params);
	/**
	 * 查询先息后本未打包的项目
	 * @param params
	 * @return
	 */
	public List<AgricultureLoaninfo> readAgriculturePacking(Map<String, Object> params);
	
	/**
	 * 根据农贷id查询借款人信息
	 * @param id
	 * @return
	 */
	public AgricultureLoaninfo readAgricultureByLoanerinfoId(String id);

	/**
	 * 批量修改农贷项目的打包状态
	 * 
	 */
	public void updatePackStatus(Map<String, Object> params);
	/**
	 * 修改农贷项目的提前还款数据
	 * 
	 */
	public void updateTimelyLoaninfo(Map<String, Object> params);
	
	/**
	 * 根据合同编号查询还款计划
	 * @param params
	 * @return
	 */
	public List<AgricultureRepaymentPlan> readTimelyloansRepaymentPlan(String id);

	/**
	 *根据身份证号或者合同编号查询借款人信息
	 * @param params
	 * @return
	 */
	public AgricultureLoaninfo readAgricultureBycontractId(Map<String, Object> params);
	public List<AgricultureRepaymentPlan> findBySubContractId(String subContractId);
	/**
	 * 手动修改农贷客户的银行卡
	 * 
	 */
	public void updateLoaninfobank(Map<String, Object> params);
	/**
	 * 根据每条还款计划的id查询每条还款计划
	 * @param id
	 * @return
	 */
	public AgricultureRepaymentPlan readRepaymentPlanById(String id);
	
	/**
	 *  计算逾期罚息
	 * @param deadlineTime
	 */
	public void calculationFaxi(String deadlineTime);
	/**
	 * 根据合同ID查询出所有的子标
	 * @param pageNo
	 * @param pageSize
	 * @param params
	 * @return
	 */
	public List<AgricultureForkLoans> readAgricultureForkLoans(Map<String, Object> params);
	/**
	 * 处理农贷等额本息提前还款
	 * @param contractId
	 * @param loanTerm
	 * @param settle
	 * @return
	 */
	public boolean editLeekPrepayment(String contractId,String loanTerm,String settle);
	/**
	 * 导出报表需要调用的函数
	 */
}
