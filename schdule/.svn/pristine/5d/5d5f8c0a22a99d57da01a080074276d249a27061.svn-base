package com.duanrong.business.loan.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.business.loan.dao.LoanDetailDao;
import com.duanrong.business.loan.model.Enterprise;
import com.duanrong.business.loan.model.House;
import com.duanrong.business.loan.model.Loan;
import com.duanrong.business.loan.model.Ruralfinance;
import com.duanrong.business.loan.model.RuralfinanceLoaners;
import com.duanrong.business.loan.model.Supplychain;
import com.duanrong.business.loan.model.Vehicle;

@Repository
public class LoanDetailDaoImpl extends BaseDaoImpl<Loan> implements
		LoanDetailDao {

	public LoanDetailDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.loan.mapper.LoanDetailMapper");
	}

	@Override
	public Vehicle getVehicleDetail(String loanId) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getVehicleDetail", loanId);
	}

	@Override
	public House getHouseDetail(String loanId) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getHouseDetail", loanId);
	}

	@Override
	public Enterprise getEnterpriseDetail(String loanId) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getEnterpriseDetail", loanId);
	}
	@Override
	public Ruralfinance getRuralfinanceDetail(String loanId) {
		
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getRuralfinanceDetail", loanId);
	}
	@Override
	public Supplychain getSupplychainDetail(String loanId) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getSupplychainDetail", loanId);
	}
	@Override
	public Double getAllMoney(String loanType) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getAllMoney", loanType);
	}

	@Override
	public Double getSumMoney(String loanType) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getSumMoney", loanType);
	}

	@Override
	public Double getInvestMoney(String loanType) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getInvestMoney", loanType);
	}

	@Override
	public Double getRemainingInvestmentAmount() {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getRemainingInvestmentAmount");
	}

	@Override
	public void insertVehicle(Vehicle vehicle) {
		this.getSqlSession().insert(
				this.getMapperNameSpace() + ".insertVehicle", vehicle);
	}

	@Override
	public void insertHouse(House house) {
		this.getSqlSession().insert(this.getMapperNameSpace() + ".insertHouse",
				house);
	}

	@Override
	public void insertEnterprise(Enterprise enterprise) {
		this.getSqlSession().insert(
				this.getMapperNameSpace() + ".insertEnterprise", enterprise);
	}
	@Override
	public void  insertRuralfinanceLoaners(RuralfinanceLoaners ruralfinanceLoaners){
		 this.getSqlSession().insert(
				this.getMapperNameSpace() + ".insertRuralfinanceLoaners", ruralfinanceLoaners);
		
	}
	@Override
	public void insertRuralfinance(Ruralfinance ruralfinance){
		this.getSqlSession().insert(
				this.getMapperNameSpace() + ".insertRuralfinance",ruralfinance);
		
	}
	@Override
	public void updateVehicle(Vehicle vehicle) {
		this.getSqlSession().update(
				this.getMapperNameSpace() + ".updateVehicle", vehicle);
	}

	@Override
	public void updateHouse(House house) {
		this.getSqlSession().update(this.getMapperNameSpace() + ".updateHouse",
				house);
	}

	@Override
	public void updateEnterprise(Enterprise enterprise) {
		this.getSqlSession().update(this.getMapperNameSpace() + ".updateEnterprise",
				enterprise);
		
	}

	@Override
	public void insertSupplychain(Supplychain supplychain) {
		this.getSqlSession().insert(
				this.getMapperNameSpace() + ".insertSupplychain", supplychain);
	}

	@Override
	public void updateSupplychain(Supplychain supplychain) {
		this.getSqlSession().update(this.getMapperNameSpace() + ".updateSupplychain",
				supplychain);
		
	}

	@Override
	public List<RuralfinanceLoaners> getRuralfinanceLoanersDetail(String loanId) {
		
		 return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getRuralfinanceLoanersDetail", loanId);
	}

	@Override
	public void updateRuralfinanceLoaners(
			RuralfinanceLoaners ruralfinanceLoaners) {
		this.getSqlSession().update(this.getMapperNameSpace() + ".updateRuralfinanceLoaners",
				ruralfinanceLoaners);
	}

	@Override
	public void updateRuralfinance(Ruralfinance ruralfinance) {
		this.getSqlSession().update(this.getMapperNameSpace() + ".updateRuralfinance",
				ruralfinance);
		
	}

	@Override
	public RuralfinanceLoaners getRuralfinanceLoanerDetail(String id) {
		 return this.getSqlSession().selectOne(
					this.getMapperNameSpace() + ".getRuralfinanceLoaner", id);
	}

	@Override
	public int deleteRuralfinanceLoaner(String id) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update(this.getMapperNameSpace()+ ".deleteRuralfinanceLoaner",
				id);
	}
	@Override
	public RuralfinanceLoaners getRuralfinanceLoanerID(RuralfinanceLoaners ruralfinanceLoaners) {

		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getRuralfinanceLoanerID", ruralfinanceLoaners);
	}

	@Override
	public void updateRuralfinanceLoanerID(RuralfinanceLoaners ruralfinanceLoaners) {
		this.getSqlSession().update(this.getMapperNameSpace() + ".updateRuralfinanceLoanerID",
				ruralfinanceLoaners);
	}

	

	

	
}
