package com.duanrong.business.demand.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import java.util.Map;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.business.demand.dao.DemandtreasureInvestDao;
import com.duanrong.business.demand.model.DemandTreasureInvest;
import com.duanrong.business.demand.model.DemandTreasureOpration;


@Repository
public class DemandtreasureInvestDaoImpl extends BaseDaoImpl<DemandTreasureInvest> implements
		DemandtreasureInvestDao {

	public DemandtreasureInvestDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.demand.mapper.DemandtreasureInvestMapper");
	}

	@Override
	public void insertBatchOpration(List<DemandTreasureOpration> oprations) {
		
		this.getSqlSession().insert(getMapperNameSpace()+".batchInsertOpration", oprations);
	}

	@Override
	public void insertBatch(List<DemandTreasureInvest> invests) {
		
		this.getSqlSession().insert(getMapperNameSpace()+".batchInsert", invests);
	}
	@Override
	public List<DemandTreasureInvest> findAllOfUser(Map map) {
		List<DemandTreasureInvest> invests1 
		=  getSqlSession().selectList(getMapperNameSpace() + ".findAllOfUser1",map);
		List<DemandTreasureInvest> invests2 
		=  getSqlSession().selectList(getMapperNameSpace() + ".findAllOfUser2",map);
		invests1.addAll(invests2);	
		return invests1;
 	}

	@Override
	public int updateMoney(List list) {
		return getSqlSession().update(getMapperNameSpace() + ".batchUpdateMoney", list);
		
	}

	@Override
	public List<DemandTreasureInvest> getInvestByLoan() {
		
		return getSqlSession().selectList(getMapperNameSpace() + ".getInvestByLoan");
	}

	@Override
	public void deleteByMoney(Map<String, Object> map) {
		getSqlSession().update(getMapperNameSpace() + ".deleteByMoney", map);
		
	}

	@Override
	public void insertOpration(DemandTreasureOpration opration) {
		getSqlSession().insert(getMapperNameSpace() + ".insertOpration", opration);
	}

	@Override
	public List<DemandTreasureInvest> getInvestMoneyByLoan() {
		return getSqlSession().selectList(getMapperNameSpace() + ".getInvesMoneytByLoan");
	}

}
