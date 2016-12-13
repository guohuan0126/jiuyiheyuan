package com.duanrong.business.loan.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.duanrong.business.loan.dao.LoanDetailDao;
import com.duanrong.business.loan.model.Enterprise;
import com.duanrong.business.loan.model.House;
import com.duanrong.business.loan.model.Ruralfinance;
import com.duanrong.business.loan.model.RuralfinanceLoaners;
import com.duanrong.business.loan.model.Supplychain;
import com.duanrong.business.loan.model.Vehicle;
import com.duanrong.business.loan.service.LoanDetailService;

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

	@Override
	public Double getRemainingInvestmentAmount() {
		return loanDetailDao.getRemainingInvestmentAmount();
	}

	@Override
	public Vehicle getVehicleDetail(String loanId) {
		return loanDetailDao.getVehicleDetail(loanId);
	}

	@Override
	public House getHouseDetail(String loanId) {
		return loanDetailDao.getHouseDetail(loanId);
	}

	@Override
	public Enterprise getEnterpriseDetail(String loanId) {
		return loanDetailDao.getEnterpriseDetail(loanId);
	}

	@Override
	public Supplychain getSupplychainDetail(String loanId) {
		
		return loanDetailDao.getSupplychainDetail(loanId);
	}

	@Override
	public Ruralfinance getRuralfinanceDetail(String loanId) {
		return loanDetailDao.getRuralfinanceDetail(loanId);
	}
	@Override
	public List<RuralfinanceLoaners> getRuralfinanceLoanersDetail(String loanId) {
		
		return loanDetailDao.getRuralfinanceLoanersDetail(loanId);
	}
	@Override
	public RuralfinanceLoaners getRuralfinanceLoanerDetail(String id) {
		// TODO Auto-generated method stub
		return loanDetailDao.getRuralfinanceLoanerDetail(id);
	}
	@Override
	public RuralfinanceLoaners getRuralfinanceLoanerID(RuralfinanceLoaners ruralfinanceLoaners) {
		// TODO Auto-generated method stub
		return loanDetailDao.getRuralfinanceLoanerID(ruralfinanceLoaners);
	}
	@Override
	public Double getAllMoney(String loanType) {
		return loanDetailDao.getAllMoney(loanType);
	}

	@Override
	public Double getSumMoney(String loanType) {
		return loanDetailDao.getSumMoney(loanType);
	}

	@Override
	public Double getInvestMoney(String loanType) {
		return loanDetailDao.getInvestMoney(loanType);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void insertVehicle(Vehicle vehicle) {
		loanDetailDao.insertVehicle(vehicle);
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
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void insertRuralfinance(Ruralfinance ruralfinance) {
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
	public void updateRuralfinance(Ruralfinance ruralfinance) {
		loanDetailDao.updateRuralfinance(ruralfinance);
		
	}

	@Override
	@Transactional(readOnly = false,rollbackFor=Exception.class)
	public int deleteRuralfinanceLoaner(String id) {
		
		return loanDetailDao.deleteRuralfinanceLoaner(id);
	}



	@Override
	@Transactional(readOnly = false,rollbackFor=Exception.class)
	public void updateRuralfinanceLoanerID(RuralfinanceLoaners ruralfinanceLoaners) {
		loanDetailDao.updateRuralfinanceLoanerID(ruralfinanceLoaners);
		
	}



	

}
