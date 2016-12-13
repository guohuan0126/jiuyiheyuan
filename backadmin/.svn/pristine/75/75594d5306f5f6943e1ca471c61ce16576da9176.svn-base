package com.duanrong.business.loan.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import util.RedisCacheKey;
import base.dao.impl.BaseDaoImpl;







import com.duanrong.business.loan.dao.LoanDetailDao;
import com.duanrong.business.loan.model.Enterprise;
import com.duanrong.business.loan.model.House;
import com.duanrong.business.loan.model.Loan;
import com.duanrong.business.loan.model.RuralfinanceLoaners;
import com.duanrong.business.loan.model.Supplychain;
import com.duanrong.business.loan.model.Vehicle;
import com.duanrong.business.ruralfinance.model.AgricultureForkLoans;
import com.duanrong.business.ruralfinance.model.LoanRuralfinance;
import com.duanrong.util.jedis.DRJedisCacheUtil;

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
	public LoanRuralfinance getRuralfinanceDetail(String loanId) {
		
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
		DRJedisCacheUtil.hdel(RedisCacheKey.LOAN + vehicle.getLoanId(), RedisCacheKey.LOAN_DETAIL);
		this.getSqlSession().insert(
				this.getMapperNameSpace() + ".insertVehicle", vehicle);
	}

	@Override
	public void insertHouse(House house) {
		DRJedisCacheUtil.hdel(RedisCacheKey.LOAN + house.getLoanId(), RedisCacheKey.LOAN_DETAIL);
		this.getSqlSession().insert(this.getMapperNameSpace() + ".insertHouse",
				house);
	}

	@Override
	public void insertEnterprise(Enterprise enterprise) {
		DRJedisCacheUtil.hdel(RedisCacheKey.LOAN + enterprise.getLoanId(), RedisCacheKey.LOAN_DETAIL);
		this.getSqlSession().insert(
				this.getMapperNameSpace() + ".insertEnterprise", enterprise);
	}
	@Override
	public void  insertRuralfinanceLoaners(RuralfinanceLoaners ruralfinanceLoaners){
		DRJedisCacheUtil.del(RedisCacheKey.LOAN + ruralfinanceLoaners.getLoanId());
		this.getSqlSession().insert(
				this.getMapperNameSpace() + ".insertRuralfinanceLoaners", ruralfinanceLoaners);
		
	}
	@Override
	public void insertRuralfinance(LoanRuralfinance ruralfinance){
		DRJedisCacheUtil.hdel(RedisCacheKey.LOAN + ruralfinance.getLoanId(), RedisCacheKey.LOAN_DETAIL);
		this.getSqlSession().insert(
				this.getMapperNameSpace() + ".insertRuralfinance",ruralfinance);
		
	}
	@Override
	public void updateVehicle(Vehicle vehicle) {
		DRJedisCacheUtil.hdel(RedisCacheKey.LOAN + vehicle.getLoanId(), RedisCacheKey.LOAN_DETAIL);
		this.getSqlSession().update(
				this.getMapperNameSpace() + ".updateVehicle", vehicle);
	}

	@Override
	public void updateHouse(House house) {
		DRJedisCacheUtil.hdel(RedisCacheKey.LOAN + house.getLoanId(), RedisCacheKey.LOAN_DETAIL);
		this.getSqlSession().update(this.getMapperNameSpace() + ".updateHouse",
				house);
	}

	@Override
	public void updateEnterprise(Enterprise enterprise) {
		DRJedisCacheUtil.hdel(RedisCacheKey.LOAN + enterprise.getLoanId(), RedisCacheKey.LOAN_DETAIL);
		this.getSqlSession().update(this.getMapperNameSpace() + ".updateEnterprise",
				enterprise);
		
	}

	@Override
	public void insertSupplychain(Supplychain supplychain) {
		DRJedisCacheUtil.hdel(RedisCacheKey.LOAN + supplychain.getLoanId(), RedisCacheKey.LOAN_DETAIL);
		this.getSqlSession().insert(
				this.getMapperNameSpace() + ".insertSupplychain", supplychain);
	}

	@Override
	public void updateSupplychain(Supplychain supplychain) {
		DRJedisCacheUtil.hdel(RedisCacheKey.LOAN + supplychain.getLoanId(), RedisCacheKey.LOAN_DETAIL);
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
		DRJedisCacheUtil.del(RedisCacheKey.LOAN + ruralfinanceLoaners.getLoanId());
		this.getSqlSession().update(this.getMapperNameSpace() + ".updateRuralfinanceLoaners",
				ruralfinanceLoaners);
	}

	@Override
	public void updateRuralfinance(LoanRuralfinance ruralfinance) {
		DRJedisCacheUtil.hdel(RedisCacheKey.LOAN + ruralfinance.getLoanId(), RedisCacheKey.LOAN_DETAIL);
		this.getSqlSession().update(this.getMapperNameSpace() + ".updateRuralfinance",
				ruralfinance);
		
	}

	@Override
	public RuralfinanceLoaners getRuralfinanceLoanerDetail(String id) {
		 return this.getSqlSession().selectOne(
					this.getMapperNameSpace() + ".getRuralfinanceLoaner", id);
	}

	@Override
	public int deleteRuralfinanceLoaner(String id, String loanId) {
		DRJedisCacheUtil.del(RedisCacheKey.LOAN + loanId);
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
		DRJedisCacheUtil.del(RedisCacheKey.LOAN + ruralfinanceLoaners.getLoanId());
		this.getSqlSession().update(this.getMapperNameSpace() + ".updateRuralfinanceLoanerID",
				ruralfinanceLoaners);
	}



	@Override
	public List<AgricultureForkLoans> readAgricultureForkLoans(Map<String, Object> params) {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".readAgricultureForkLoans", params);
	}

	@Override
	public List<Vehicle> getVehiclesList(String loanId) {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getVehicleListByLoanId", loanId);
	}

	@Override
	public void deleteVehicle(Vehicle vehicle) {
		DRJedisCacheUtil.hdel(RedisCacheKey.LOAN + vehicle.getLoanId(), RedisCacheKey.LOAN_DETAIL);
		this.getSqlSession().update(
				this.getMapperNameSpace() + ".deleteVehicle", vehicle.getId());
		
	}

	@Override
	public void insertVehicle(List<Vehicle> vehicles) {	
		if(vehicles != null && !vehicles.isEmpty()){
			Vehicle v = vehicles.get(0);
			DRJedisCacheUtil.hdel(RedisCacheKey.LOAN + v.getLoanId(), RedisCacheKey.LOAN_DETAIL);
		}
		this.getSqlSession().insert(
				this.getMapperNameSpace() + ".insertVehicleBatch", vehicles);
	}

	@Override
	public void updateVehicleByLoanId(Vehicle vehicle) {
		DRJedisCacheUtil.hdel(RedisCacheKey.LOAN + vehicle.getLoanId(), RedisCacheKey.LOAN_DETAIL);
		this.getSqlSession().insert(
				this.getMapperNameSpace() + ".updateVehicleByLoanId", vehicle);	
	}


}
