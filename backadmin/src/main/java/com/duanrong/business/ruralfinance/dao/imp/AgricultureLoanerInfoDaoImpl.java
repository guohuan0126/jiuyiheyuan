package com.duanrong.business.ruralfinance.dao.imp;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.business.ruralfinance.dao.AgricultureLoanerInfoDao;
import com.duanrong.business.ruralfinance.model.AgricultureForkLoans;
import com.duanrong.business.ruralfinance.model.AgricultureLoaninfo;
import com.duanrong.business.ruralfinance.model.AgricultureRepaymentPlan;
import com.duanrong.business.ruralfinance.model.LoanerinfoExport;
import com.duanrong.business.ruralfinance.model.Template;
@Repository
public class AgricultureLoanerInfoDaoImpl extends BaseDaoImpl<AgricultureLoaninfo>  implements AgricultureLoanerInfoDao {

	public AgricultureLoanerInfoDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.ruralfinance.mapper.AgricultureLoanerInfoMapper");
	}
	
	
	@Override
	public PageInfo<AgricultureLoaninfo> readAgricultureLoaninfo(int pageNo,
			int pageSize, Map<String, Object> params) {
		PageHelper.startPage(pageNo, pageSize);
		List<AgricultureLoaninfo> list = getSqlSession().selectList(
				getMapperNameSpace() + ".readAgricultureLoaninfoPageInfo",params);
		PageInfo<AgricultureLoaninfo> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	@Override
	public PageInfo<AgricultureForkLoans> readAgricultureForkLoans(int pageNo,
			int pageSize, Map<String, Object> params) {
		PageHelper.startPage(pageNo, pageSize);
		List<AgricultureForkLoans> list = getSqlSession().selectList(
				getMapperNameSpace() + ".readAgricultureForkLoansPageInfo",params);
		PageInfo<AgricultureForkLoans> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}


	@Override
	public PageInfo<AgricultureRepaymentPlan> readRuralfinanceRepaymentPlan(
			int pageNo, int pageSize, Map<String, Object> params) {
		PageHelper.startPage(pageNo, pageSize);
		List<AgricultureRepaymentPlan> list = getSqlSession().selectList(
				getMapperNameSpace() + ".readRuralfinanceRepaymentPlan",params);
		PageInfo<AgricultureRepaymentPlan> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}


	@Override
	public AgricultureLoaninfo readAgricultureLoaninfoById(String id) {
		return this.getSqlSession().selectOne(this.getMapperNameSpace()+".readAgricultureLoaninfoById", id);
	}

	
	@Override
	public void updateStatusById(Map<String, Object> params) {
		this.getSqlSession().update(this.getMapperNameSpace()+".updateStatusById", params);
		
	}


	@Override
	public PageInfo<AgricultureForkLoans> readAgricultureForkLoansById(
			int pageNo, int pageSize, String id) {
		PageHelper.startPage(pageNo, pageSize);
		List<AgricultureForkLoans> list = getSqlSession().selectList(
				getMapperNameSpace() + ".readAgricultureForkLoansById",id);
		PageInfo<AgricultureForkLoans> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}


	@Override
	public void updateForkStatusById(Map<String, Object> params) {
		this.getSqlSession().update(this.getMapperNameSpace()+".updateForkStatusById", params);
		
	}


	@Override
	public void renewalLatePenalty(Map<String, Object> map) {
		this.getSqlSession().update(this.getMapperNameSpace()+".renewalLatePenalty",map);
		
	}
	

	@Override
	public void updateRepaymentplanStatus(Map<String, Object> params) {
		this.getSqlSession().update(this.getMapperNameSpace()+".updateRepaymentplanStatus",params);
		
	}


	@Override
	public AgricultureLoaninfo readAgricultureLoaninfoByIdAndFlag(String id) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne(this.getMapperNameSpace()+".readAgricultureLoaninfoByIdAndFlag", id);
	}


	@Override
	public double readneedRepayMoney(String loanDataId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne(this.getMapperNameSpace()+".readneedRepayMoney", loanDataId);
	}


	@Override
	public List<AgricultureRepaymentPlan> readRuralfinanceFailRepaymentPlan() {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(this.getMapperNameSpace()+".readRuralfinanceFailRepaymentPlan");
	}
	@Override
	public List<AgricultureRepaymentPlan> findAllPlan() {
		return this.getSqlSession().selectList(this.getMapperNameSpace()+".findAllPlan");
	}

	@Override
	public void updateFlag(Map<String, Object> map) {
		this.getSqlSession().update(this.getMapperNameSpace()+".updateFlag",map);
		
	}


	@Override
	public List<Template> readTemplate(Map<String, Object> params) {
		return this.getSqlSession().selectList(this.getMapperNameSpace()+".readTemplate", params);
	}
	@Override
	public List<Template> readTimeTemplate(Map<String, Object> params) {
		return this.getSqlSession().selectList(this.getMapperNameSpace()+".readTimeTemplate", params);
	}

	@Override
	public List<LoanerinfoExport> readLoanerinfoExport() {
		List<LoanerinfoExport> list = getSqlSession().selectList(
				getMapperNameSpace() + ".readLoanerinfoExport");
		return list;
	}


	@Override
	public List<AgricultureLoaninfo> readAgricultureLoaninfo(
			Map<String, Object> params) {
		List<AgricultureLoaninfo> list = getSqlSession().selectList(
				getMapperNameSpace() + ".readAgricultureLoaninfoPageInfo",params);
		return list;
	}


	@Override
	public List<AgricultureRepaymentPlan> readRuralfinanceRepaymentPlan(
			Map<String, Object> params) {
		List<AgricultureRepaymentPlan> list = getSqlSession().selectList(
				getMapperNameSpace() + ".readRuralfinanceRepaymentPlan",params);
		return list;
	}


	@Override
	public List<AgricultureLoaninfo> readAgriculturePacking(
			Map<String, Object> params) {
		List<AgricultureLoaninfo> list = getSqlSession().selectList(
				getMapperNameSpace() + ".readAgriculturePacking",params);
		return list;
	}


	@Override
	public AgricultureLoaninfo readAgricultureByLoanerinfoId(String id) {
		return this.getSqlSession().selectOne(this.getMapperNameSpace()+".readAgricultureByLoanerinfoId", id);
	}


	@Override
	public void updatePackStatus(Map<String, Object> params) {
		this.getSqlSession().update(this.getMapperNameSpace() +".updatePackStatus",params);	
		
	}


	@Override
	public void updateTimelyLoaninfo(Map<String, Object> params) {
		this.getSqlSession().update(this.getMapperNameSpace() +".updateTimelyLoaninfo",params);	
		
	}


	@Override
	public List<AgricultureRepaymentPlan> readTimelyloansRepaymentPlan(String id) {
		List<AgricultureRepaymentPlan> list = this.getSqlSession().selectList(
				getMapperNameSpace() + ".readTimelyloansRepaymentPlan",id);
		return list;
	}


	@Override
	public AgricultureLoaninfo readAgricultureBycontractId(
			Map<String, Object> params) {
		return  this.getSqlSession().selectOne(
				getMapperNameSpace() + ".readAgricultureBycontractId",params);
	}
	@Override
	public List<AgricultureRepaymentPlan> findBySubContractId(String subContractId) {
		
		return this.getSqlSession().selectList(
				getMapperNameSpace() + ".findBySubContractId",subContractId);
	}

	@Override
	public void updateLoaninfobank(Map<String, Object> params) {
		this.getSqlSession().update(this.getMapperNameSpace()+".updateLoaninfobank", params);
		
	}


	@Override
	public AgricultureRepaymentPlan readRepaymentPlanById(String id) {
		return this.getSqlSession().selectOne(getMapperNameSpace() + ".readRepaymentPlanById",id);	
	}


	@Override
	public List<AgricultureForkLoans> readAgricultureForkLoans(
			Map<String, Object> params) {
		List<AgricultureForkLoans> list = getSqlSession().selectList(
				getMapperNameSpace() + ".readAgricultureForkLoansPageInfo",params);
		return list;
	}

}