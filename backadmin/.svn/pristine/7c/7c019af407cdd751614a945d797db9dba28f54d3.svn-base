package com.duanrong.business.repay.dao.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import util.MyCollectionUtils;
import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.loan.model.Loan;
import com.duanrong.business.repay.dao.RepayDao;
import com.duanrong.business.repay.model.Repay;

@Repository
public class RepayDaoImpl extends BaseDaoImpl<Repay> implements RepayDao {

	public RepayDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.repay.mapper.RepayMapper");
	}

	public List<Repay> getByCondition(Repay repay) {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getByCondition", repay);
	}

	@Override
	public Repay get(Serializable id) {
		Repay repay = super.get(id);
		if (repay != null) {
			Loan loan = repay.getLoan();
			loan.setId(repay.getLoanId());
			if (loan != null) {
				List<Invest> invests = loan.getInvests();
				if (MyCollectionUtils.isNotBlank(invests)) {
					for (Invest invest : invests) {
						invest.setLoan(loan);
						invest.setRate(loan.getRate());
						invest.setLoanName(loan.getName());
					}
					loan.setInvests(invests);
					repay.setLoan(loan);
				}
			}
		}
		return repay;
	}

	@Override
	public Date getCompleteDate(String loanId) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getCompleteDate", loanId);
	}

	@Override
	public List<Repay> getRepayLoan(String loanId) {
		
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getRepayByLoan", loanId);
	}

	@Override
	public Repay getRepayById(String id) {
		
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getById", id);
	}

	@Override
	public Repay getDownRepay(Repay repay) {		
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getDownRepay", repay);
	}

	@Override
	public PageInfo<Repay> pageInfo(int pageNo, int pageSize, Map map) {
		PageHelper.startPage(pageNo, pageSize);
		List<Repay> list = getSqlSession().selectList(
				getMapperNameSpace() + ".pageInfo", map);
		PageInfo<Repay> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public List<Repay> findRepay(Map map) {
		List<Repay> list = getSqlSession().selectList(
				getMapperNameSpace() + ".pageInfo", map);
		return list;
	}

	@Override
	public List<Repay> getRepayList(Date date) {		
		return getSqlSession().selectList(
				getMapperNameSpace() + ".getRapayList", date);
	}

	@Override
	public void updateByLoanId(Repay repay) {
		this.getSqlSession().update(getMapperNameSpace() + ".updateByLoanId", repay);
		
	}

	@Override
	public Repay getTotalMoney(Map map) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getTotalMoney", map);
	}

	@Override
	public Repay getCurrentPeriodByLoanId(String loanId) {	
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getCurrentPeriodByLoanId", loanId);
	}

	@Override
	public void updateBefoRepay(Repay repay) {
		this.getSqlSession().update(
				this.getMapperNameSpace() + ".updateBefoRepay", repay);		
	}

}
