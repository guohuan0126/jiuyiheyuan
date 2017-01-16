package com.duanrong.drpay.business.repay.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.drpay.business.repay.dao.RepayInvestDao;
import com.duanrong.drpay.business.repay.model.RepayInvest;

@Repository
public class RepayInvestDaoImpl extends BaseDaoImpl<RepayInvest> implements
		RepayInvestDao {

	public RepayInvestDaoImpl() {
		this.setMapperNameSpace("com.duanrong.drpay.business.repay.mapper.RepayInvestMapper");
	}

	public RepayInvest getByCondition(RepayInvest repayInvest) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getByCondition", repayInvest);
	}

	@Override
	public void deleteRepayInvests(String repayId) {
		this.getSqlSession().delete(
				this.getMapperNameSpace() + ".deleteRepayInvests", repayId);
	}

	@Override
	public int getRepayInvestCountByRepayIdAndStatus(String repayId) {		
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getCountByRepayIdAndStatus", repayId);
	}

	@Override
	public List<RepayInvest> getRepayInvests(String repayId) {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getRepayInvests", repayId);
	}

}
