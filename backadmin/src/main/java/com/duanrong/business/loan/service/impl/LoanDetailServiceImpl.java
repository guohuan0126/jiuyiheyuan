package com.duanrong.business.loan.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;









import com.duanrong.business.invest.dao.InvestDao;
import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.loan.dao.LoanDao;
import com.duanrong.business.loan.dao.LoanDetailDao;
import com.duanrong.business.loan.model.Enterprise;
import com.duanrong.business.loan.model.House;
import com.duanrong.business.loan.model.Loan;
import com.duanrong.business.loan.model.RuralfinanceLoaners;
import com.duanrong.business.loan.model.Supplychain;
import com.duanrong.business.loan.model.Vehicle;
import com.duanrong.business.loan.service.LoanDetailService;
import com.duanrong.business.ruralfinance.model.AgricultureForkLoans;
import com.duanrong.business.ruralfinance.model.LoanRuralfinance;
import com.duanrong.util.ArithUtil;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2015-3-19 下午4:04:21
 * @Description : NewAdmin com.duanrong.business.loan.service.impl
 *              LoanDetailServiceImpl.java
 * 
 */
@Service
public class LoanDetailServiceImpl implements LoanDetailService {

	@Resource
	LoanDetailDao loanDetailDao;
	
	@Resource
	LoanDao loanDao;
	
	@Resource
	InvestDao investDao;

	@Override
	public Double readRemainingInvestmentAmount() {
		return loanDetailDao.getRemainingInvestmentAmount();
	}

	@Override
	public Vehicle readVehicleDetail(String loanId) {
		return loanDetailDao.getVehicleDetail(loanId);
	}

	@Override
	public House readHouseDetail(String loanId) {
		return loanDetailDao.getHouseDetail(loanId);
	}

	@Override
	public Enterprise readEnterpriseDetail(String loanId) {
		return loanDetailDao.getEnterpriseDetail(loanId);
	}

	@Override
	public Supplychain readSupplychainDetail(String loanId) {
		
		return loanDetailDao.getSupplychainDetail(loanId);
	}

	@Override
	public LoanRuralfinance readRuralfinanceDetail(String loanId) {
		return loanDetailDao.getRuralfinanceDetail(loanId);
	}
	@Override
	public List<RuralfinanceLoaners> readRuralfinanceLoanersDetail(String loanId) {
		
		return loanDetailDao.getRuralfinanceLoanersDetail(loanId);
	}
	@Override
	public RuralfinanceLoaners readRuralfinanceLoanerDetail(String id) {
		// TODO Auto-generated method stub
		return loanDetailDao.getRuralfinanceLoanerDetail(id);
	}
	@Override
	public RuralfinanceLoaners readRuralfinanceLoanerID(RuralfinanceLoaners ruralfinanceLoaners) {
		// TODO Auto-generated method stub
		return loanDetailDao.getRuralfinanceLoanerID(ruralfinanceLoaners);
	}
	@Override
	public Double readAllMoney(String loanType) {
		return loanDetailDao.getAllMoney(loanType);
	}

	@Override
	public Double readSumMoney(String loanType) {
		return loanDetailDao.getSumMoney(loanType);
	}

	@Override
	public Double readInvestMoney(String loanType) {
		return loanDetailDao.getInvestMoney(loanType);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void insertVehicle(Vehicle vehicle) {
		loanDetailDao.insertVehicle(vehicle);
	}
	
	@Override
	@Transactional
	public void insertVehicle(Object obj, Loan loan) {		
		loanDetailDao.insertVehicle((List<Vehicle>)obj);
		if(loan.getCompanyno().equals("1") && loan.getTotalmoney() > 0){
			loanDao.update(loan);
			if(loan.getOrganizationExclusive().equals("是")){
				Invest invest = new Invest();
				invest.setMoney(loan.getTotalmoney());
				invest.setLoanId(loan.getId());
				investDao.updateMoneyByLoanId(invest);
			}
		}
	}

	@Override
	@Transactional
	public void updateVehicle(Object obj, Loan loan) {
		if(loan.getCompanyno().equals("1")){
			List<Vehicle> vehicles = (List<Vehicle>)obj;
			if(vehicles.size() > 1 || (vehicles.get(0).getBrand()!=null 
					&& !vehicles.get(0).getBrand().equals(""))){
				loanDetailDao.insertVehicle((List<Vehicle>)obj);
			}	
			Vehicle v = ((List<Vehicle>)obj).get(0);
			Vehicle v2 = new Vehicle();
			v2.setLoanId(loan.getId());
			v2.setGuaranteeType(v.getGuaranteeType());
			v2.setGuaranteeRate(v.getGuaranteeRate());
			v2.setReminderInfo(v.getReminderInfo());
			v2.setMeasuresInfo(v.getMeasuresInfo());
			v2.setOverdueInfo(v.getOverdueInfo());
			v2.setMortgagee(v.getMortgagee());
			v2.setSupervisionMode(v.getSupervisionMode());
			v2.setOverdueHandling(v.getOverdueHandling());
			v2.setOtherLoanInfo(v.getOtherLoanInfo());
			if(v.getMoney() == null || v.getMoney() <= 0){
				v2.setItemAddress(v.getItemAddress());
				v2.setBorrowerName(v.getBorrowerName());
				v2.setBorrowerIdCard(v.getBorrowerIdCard());
				v2.setRemark(v.getRemark());
				v2.setYaCarAndGPS(v.getYaCarAndGPS());
			}
			loanDetailDao.updateVehicleByLoanId(v2);
			loanDao.update(loan);
		}else{
			loanDetailDao.updateVehicle(((List<Vehicle>)obj).get(0));
		}
	}
	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void insertHouse(House house) {
		loanDetailDao.insertHouse(house);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void insertEnterprise(Enterprise enterprise) {
		loanDetailDao.insertEnterprise(enterprise);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void updateVehicle(Vehicle vehicle) {
		loanDetailDao.updateVehicle(vehicle);
	}
	
	


	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void updateHouse(House house) {
		loanDetailDao.updateHouse(house);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void updateEnterprise(Enterprise enterprise) {
		loanDetailDao.updateEnterprise(enterprise);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void insertSupplychain(Supplychain supplychain) {
		loanDetailDao.insertSupplychain(supplychain);
		
	}
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void insertRuralfinanceLoaners(RuralfinanceLoaners ruralfinanceLoaners) {
		 loanDetailDao.insertRuralfinanceLoaners(ruralfinanceLoaners);
		
	}
	@Override
	public void insertRuralfinance(LoanRuralfinance ruralfinance) {
		loanDetailDao.insertRuralfinance(ruralfinance);
		
	}

	@Override
	@Transactional(readOnly = false,rollbackFor=Exception.class)
	public void updateSupplychain(Supplychain supplychain) {
		loanDetailDao.updateSupplychain(supplychain);
		
	}
	@Override
	@Transactional(readOnly = false,rollbackFor=Exception.class)
	public void updateRuralfinanceLoaners(RuralfinanceLoaners ruralfinanceLoaners) {
		loanDetailDao.updateRuralfinanceLoaners(ruralfinanceLoaners);
		
	}

	@Override
	@Transactional(readOnly = false,rollbackFor=Exception.class)
	public void updateRuralfinance(LoanRuralfinance ruralfinance) {
		loanDetailDao.updateRuralfinance(ruralfinance);
		
	}

	@Override
	@Transactional(readOnly = false,rollbackFor=Exception.class)
	public int deleteRuralfinanceLoaner(String id, String loanId) {		
		return loanDetailDao.deleteRuralfinanceLoaner(id, loanId);
	}



	@Override
	@Transactional(readOnly = false,rollbackFor=Exception.class)
	public void updateRuralfinanceLoanerID(RuralfinanceLoaners ruralfinanceLoaners) {
		loanDetailDao.updateRuralfinanceLoanerID(ruralfinanceLoaners);
		
	}



	@Override
	public List<AgricultureForkLoans> readAgricultureForkLoans(Map<String, Object> params) {
		return loanDetailDao.readAgricultureForkLoans(params);
	}

	@Override
	public List<Vehicle> readVehicleList(String loanId) {
		// TODO Auto-generated method stub
		return loanDetailDao.getVehiclesList(loanId);
	}

	@Override
	public void deleteVehicle(Vehicle vehicle) {
		loanDetailDao.deleteVehicle(vehicle);
		if(vehicle.getMoney() > 0){
			Loan loan = loanDao.get(vehicle.getLoanId());
			if(loan != null){
				loan.setId(vehicle.getLoanId());
				loan.setTotalmoney(ArithUtil.round(loan.getTotalmoney() - vehicle.getMoney(), 0));
				loanDao.update(loan);	
			}
		}

	}

}
