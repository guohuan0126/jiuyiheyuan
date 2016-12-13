package com.duanrong.business.loan.dao;

import java.util.List;
import java.util.Map;

import base.dao.BaseDao;


import com.duanrong.business.loan.model.Enterprise;
import com.duanrong.business.loan.model.House;
import com.duanrong.business.loan.model.Loan;
import com.duanrong.business.loan.model.RuralfinanceLoaners;
import com.duanrong.business.loan.model.Supplychain;
import com.duanrong.business.loan.model.Vehicle;
import com.duanrong.business.ruralfinance.model.AgricultureForkLoans;
import com.duanrong.business.ruralfinance.model.LoanRuralfinance;

/**
 * @Description: 项目详细信息
 * @Author: 林志明
 * @CreateDate: Dec 24, 2014
 */
public interface LoanDetailDao extends BaseDao<Loan> {

	/**
	 * 今日可投金额
	 * 
	 * @return
	 */
	public Double getRemainingInvestmentAmount();

	/**
	 * 车押宝详细信息
	 * 
	 * @param loanId
	 * @return
	 */
	public Vehicle getVehicleDetail(String loanId);

	/**
	 * 删除
	 * @param id
	 */
	void deleteVehicle(Vehicle vehicle);
	
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
	 * 供应链详细信息
	 * @param loanId
	 * @return
	 */
	public Supplychain getSupplychainDetail(String loanId);
	/**
	 * 农金宝详细信息
	 * @param loanId
	 * @return
	 */
    public LoanRuralfinance getRuralfinanceDetail(String loanId);
	/**
	 * 农金宝借款人详细信息
	 * 
	 * @param loanId
	 * @return
	 */
	public List<RuralfinanceLoaners> getRuralfinanceLoanersDetail(String loanId);

	/**
	 * 农金宝借款人详情信息
	 * 
	 * @param loanId
	 * @return
	 */
	public RuralfinanceLoaners getRuralfinanceLoanerDetail(String id);

	/**
	 * 农金宝借款人详情信息
	 * 
	 * @param loanId
	 * @return
	 */
	public RuralfinanceLoaners getRuralfinanceLoanerID(RuralfinanceLoaners ruralfinanceLoaners); 
	/**
	 * 根据项目类型获取项目总金额
	 * 
	 * @param loanType
	 *            项目类型
	 * @return
	 */
	public Double getAllMoney(String loanType);

	/**
	 * 根据项目类型获取项目筹款中的总金额
	 * 
	 * @param loanType
	 *            项目类型
	 * @return
	 */
	public Double getSumMoney(String loanType);

	/**
	 * 根据项目类型获取项目已投资的总金额
	 * 
	 * @param loanType
	 *            项目类型
	 * @return
	 */
	public Double getInvestMoney(String loanType);

	/**
	 * @description 保存项目附加信息(车贷)
	 * @author 孙铮
	 * @time 2015-3-13 下午3:34:28
	 * @param o
	 */
	public void insertVehicle(Vehicle vehicle);
	
	/**
	 * @description 保存项目附加信息(车贷)
	 * @author 孙铮
	 * @time 2015-3-13 下午3:34:28
	 * @param o
	 */
	public void insertVehicle(List<Vehicle> vehicles);

	/**
	 * @description 保存项目附加信息(房贷)
	 * @author 孙铮
	 * @time 2015-3-13 下午4:41:55
	 * @param o
	 */
	public void insertHouse(House house);

	/**
	 * 
	 * @description 保存项目附加信息(企业贷)
	 * @author 孙铮
	 * @time 2015-3-17 下午1:04:20
	 * @param house
	 */
	public void insertEnterprise(Enterprise enterprise);
	
	/**
	 * 
	 * @description 保存项目附加信息(供应宝)
	 * @author YUMIN
	 * @time 2015-12-04 下午3:04:20
	 * @param supplychain
	 */
	public void insertSupplychain(Supplychain supplychain);
	
	/**
	 * 
	 * @description 保存项目附加信息(金农宝借款人)
	 * @author YUMIN
	 * @time 2015-12-04 下午3:04:20
	 * @param supplychain
	 */
	public void insertRuralfinanceLoaners(RuralfinanceLoaners ruralfinanceLoaners);
	
	/**
	 * 
	 * @description 保存项目附加信息(金农宝详情)
	 * @author YUMIN
	 * @time 2015-12-04 下午3:04:20
	 * @param supplychain
	 */
	public void insertRuralfinance(LoanRuralfinance ruralfinance);
	

	/**
	 * 
	 * @description 更新(车贷)
	 * @author 孙铮
	 * @time 2015-3-19 下午9:57:26
	 * @param o
	 */
	public void updateVehicle(Vehicle vehicle);
	
	/**
	 * 
	 * @description 更新(车贷)
	 * @author 孙铮
	 * @time 2015-3-19 下午9:57:26
	 * @param o
	 */
	public void updateVehicleByLoanId(Vehicle vehicle);

	/**
	 * 
	 * @description 更新(房贷)
	 * @author 孙铮
	 * @time 2015-3-19 下午9:58:17
	 * @param o
	 */
	public void updateHouse(House house);

	/**
	 * 
	 * @description 更新(企业)
	 * @author 孙铮
	 * @time 2015-3-19 下午9:58:40
	 * @param enterprise
	 */
	public void updateEnterprise(Enterprise enterprise);

	/**
	 * 
	 * @description 更新(供应宝)
	 * @author YUMIN
	 * @time 2015-12-04 下午3:04:20
	 * @param supplychain
	 */
	public void updateSupplychain(Supplychain supplychain);

		/**
	 * 
	 * @description 更新(金农宝)
	 * @author YUMIN
	 * @time 2015-12-06 下午6:06:20
	 * @param ruralfinanceLoaners
	 */
	public void updateRuralfinanceLoaners(RuralfinanceLoaners ruralfinanceLoaners);
	/**
	 * 
	 * @description 更新(金农宝详情)
	 * @author YUMIN
	 * @time 2015-12-06 下午6:06:20
	 * @param ruralfinanceLoaners
	 */
	public void updateRuralfinance(LoanRuralfinance ruralfinance);
	
	/**
	 * 删除农金宝借款人详情信息
	 * 
	 * @param id
	 * @return
	 */
	public int deleteRuralfinanceLoaner(String id, String loanId);
	/**
	 * 
	 * @description 更新(金农宝)按身份证和项目id
	 * @author YUMIN
	 * @time 2015-12-06 下午6:06:20
	 * @param ruralfinanceLoaners
	 */
	public void updateRuralfinanceLoanerID(RuralfinanceLoaners ruralfinanceLoaners);



	/**
	 * 农金宝借款项目打包查询详情信息V2.0
	 * 
	 * @param loanId
	 * @return
	 */
	
	public List<AgricultureForkLoans> readAgricultureForkLoans(Map<String, Object> params);
	
	/**
	 * 根据项目id获取车贷列表
	 * @param loanId
	 * @return
	 */
	List<Vehicle> getVehiclesList(String loanId);
}
