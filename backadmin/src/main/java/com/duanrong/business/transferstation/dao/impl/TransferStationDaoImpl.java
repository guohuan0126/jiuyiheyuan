package com.duanrong.business.transferstation.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.business.loan.model.Loan;
import com.duanrong.business.transferstation.dao.TransferStationDao;
import com.duanrong.business.transferstation.model.TransferStation;
import com.duanrong.business.transferstation.model.TransferStationForkLoans;


@Repository
public class TransferStationDaoImpl extends BaseDaoImpl<Loan> implements TransferStationDao {

	public TransferStationDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.transferstation.mapper.TransferStationMapper");
	}
	
	@Override
	public PageInfo<TransferStation> findTransferStation(int pageNo,
			int pageSize, Map<String, Object> params) {
		PageHelper.startPage(pageNo, pageSize);
		List<TransferStation> list = getSqlSession().selectList(
				getMapperNameSpace() + ".readpageInfo",params);
		PageInfo<TransferStation> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public int delVeIntermediaries(String loanId) {
		return  this.getSqlSession().delete(this.getMapperNameSpace()+".delVeIntermediaries", loanId);		
	}

	@Override
	public int delPicsIntermediaries(String loanId) {
		return  this.getSqlSession().delete(this.getMapperNameSpace()+".delPicsIntermediaries", loanId);		
	}

	@Override
	public TransferStation findTransferStationById(Map<String, Object> params) {		
		return this.getSqlSession().selectOne(this.getMapperNameSpace()+".findTransferStationById", params);
	}

	@Override
	public int updateTransferStation(Map<String, Object> params) {
		return this.getSqlSession().update(this.getMapperNameSpace()+".updateTransferStation",params);
	}

	@Override
	public int updateBatchTransferStation(Map<String, Object> params) {
		return this.getSqlSession().update(this.getMapperNameSpace()+".updateBatchTransferStation",params);
	}

	@Override
	public int updateIntermediaries(String id) {		
		return this.getSqlSession().update(this.getMapperNameSpace()+".updateIntermediaries",id);
	}

	@Override
	public void insert(TransferStationForkLoans transferStationForkLoans) {
		this.getSqlSession().insert(this.getMapperNameSpace()+".insertForkLoans",transferStationForkLoans);

	}

	@Override
	public PageInfo<TransferStationForkLoans> readTransferStationForkLoans(
			int pageNo, int pageSize, Map<String, Object> params) {
		PageHelper.startPage(pageNo, pageSize);
		List<TransferStationForkLoans> list = getSqlSession().selectList(
				getMapperNameSpace() + ".readTransferStationForkLoans",params);
		PageInfo<TransferStationForkLoans> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public TransferStation findTransferStByforkId(Map<String, Object> params) {
		return this.getSqlSession().selectOne(this.getMapperNameSpace()+".findTransferStByforkId", params);
	}

	@Override
	public int updateForkIntermediaries(Map<String, Object> params) {		
		return this.getSqlSession().update(this.getMapperNameSpace()+".updateForkIntermediaries",params);
	}

	@Override
	public List<TransferStation> readTransferStationinfo(
			Map<String, Object> params) {
		List<TransferStation> list = getSqlSession().selectList(
				getMapperNameSpace() + ".readpageInfo",params);
		return list;
	}

	@Override
	public List<TransferStationForkLoans> readLoanForkIntermediariesIdByLoadId(String loanId) {
		return this.getSqlSession().selectList(this.getMapperNameSpace()+".readLoanForkIntermediariesIdByLoadId",loanId);
	}

	@Override
	public int updateBatchForkIntermediaries(List<TransferStationForkLoans> list) {
		return this.getSqlSession().update(this.getMapperNameSpace()+".updateBatchForkIntermediaries",list);
	}

	@Override
	public int updateBatchIntermediaries(List<TransferStation> list) {
		return this.getSqlSession().update(this.getMapperNameSpace()+".updateBatchIntermediaries",list);
	}

	@Override
	public List<TransferStation> readLoanTransferIntermediariesIdByLoadId(
			String loanId) {
		return this.getSqlSession().selectList(this.getMapperNameSpace()+".readLoanTransferIntermediariesIdByLoadId",loanId);
	}

	}
