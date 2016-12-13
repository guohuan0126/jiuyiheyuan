package com.duanrong.business.ruralfinance.dao;

import java.util.List;
import java.util.Map;

import base.pagehelper.PageInfo;

import com.duanrong.business.ruralfinance.model.AgricultureForkLoans;
import com.duanrong.business.ruralfinance.model.AgricultureLoaninfo;
import com.duanrong.business.ruralfinance.model.AgricultureRepaymentPlan;
import com.duanrong.business.ruralfinance.model.LoanerinfoExport;
import com.duanrong.business.ruralfinance.model.Template;

public interface AgricultureLoanerInfoDao {

	PageInfo<AgricultureLoaninfo> readAgricultureLoaninfo(int pageNo,
			int pageSize, Map<String, Object> params);

	PageInfo<AgricultureForkLoans> readAgricultureForkLoans(int pageNo,
			int pageSize, Map<String, Object> params);

	PageInfo<AgricultureRepaymentPlan> readRuralfinanceRepaymentPlan(
			int pageNo, int pageSize, Map<String, Object> params);
	List<AgricultureRepaymentPlan> findAllPlan();
	AgricultureLoaninfo readAgricultureLoaninfoById(String id);

	void updateStatusById(Map<String, Object> params);

	PageInfo<AgricultureForkLoans> readAgricultureForkLoansById(int pageNo,
			int pageSize, String id);

	void updateForkStatusById(Map<String, Object> params);

	void renewalLatePenalty(Map<String, Object> map);
	
	void updateRepaymentplanStatus(Map<String, Object> params);

	AgricultureLoaninfo readAgricultureLoaninfoByIdAndFlag(String id);

	double readneedRepayMoney(String loanDataId);

	List<AgricultureRepaymentPlan> readRuralfinanceFailRepaymentPlan();

	void updateFlag(Map<String, Object> map);

	List<Template> readTemplate(Map<String, Object> params);
	//提前，正常，逾期还款明细
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
	 * 根据合同ID查询出所有的子标
	 * @param pageNo
	 * @param pageSize
	 * @param params
	 * @return
	 */
	public List<AgricultureForkLoans> readAgricultureForkLoans(Map<String, Object> params);

}