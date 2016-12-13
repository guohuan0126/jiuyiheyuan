package com.duanrong.business.repay.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.business.repay.dao.RepayInvestDao;
import com.duanrong.business.repay.model.RepayInvest;

@Repository
public class RepayInvestDaoImpl extends BaseDaoImpl<RepayInvest> implements
		RepayInvestDao {

	public RepayInvestDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.repay.mapper.RepayInvestMapper");
	}

	public RepayInvest getByCondition(RepayInvest repayInvest) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getByCondition", repayInvest);
	}

	@Override
	public List<RepayInvest> getByRepayId(String repayId) {
		
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getByRepayId", repayId);
	}

	@Override
	public void updateBatch(RepayInvest repayInvest) {
		this.getSqlSession().update(
				this.getMapperNameSpace() + ".updateBatch", repayInvest);
		
	}

}
