package com.duanrong.cps.business.bsy.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.cps.business.bsy.dao.BsyDao;
import com.duanrong.cps.business.bsy.model.BsyInterest;
import com.duanrong.cps.business.bsy.model.BsyInvest;
import com.duanrong.cps.business.bsy.model.BsyChangeInvestStatus;
import com.duanrong.cps.business.bsy.model.BsyInterest;
import com.duanrong.cps.business.bsy.model.BsyInvest;
import com.duanrong.cps.business.bsy.model.BsyPushInterest;
import com.duanrong.cps.business.bsy.model.BsyPushLoad;
import com.duanrong.cps.business.bsy.model.BsyPushRepayMent;
import com.duanrong.cps.business.bsy.model.BsyPushedInvest;
import com.duanrong.cps.business.bsy.model.BsyRepayMoney;
import com.duanrong.cps.business.bsy.model.InvestByBsy;
import com.duanrong.cps.business.bsy.model.PlatformUserRelation;

@Repository
public class BsyDaoImpl extends BaseDaoImpl<BsyPushLoad> implements BsyDao{

	public BsyDaoImpl(){
		this.setMapperNameSpace("com.duanrong.cps.business.bsy.mapper.bsyPushLoanMapper");
	}

	@Override
	public int insertBsyPushLoad(List<BsyPushLoad> list) {
		return this.getSqlSession().insert(this.getMapperNameSpace()+".insertBsyPushLoad", list);
	}

	@Override
	public Map getVehicle(String loanId) {
		return this.getSqlSession().selectOne(getMapperNameSpace()+".getVehicle",loanId);
	}

	@Override
	public Map getHouse(String loanId) {
		return this.getSqlSession().selectOne(getMapperNameSpace()+".getHouse",loanId);
	}

	@Override
	public Map getEnterprise(String loanId) {
		return this.getSqlSession().selectOne(getMapperNameSpace()+".getEnterprise",loanId);
	}

	@Override
	public Map getSupplychain(String loanId) {
		return this.getSqlSession().selectOne(getMapperNameSpace()+".getSupplychain",loanId);
	}

	@Override
	public List<Map> getChangeBsyLoan() {
		return this.getSqlSession().selectList(getMapperNameSpace()+".getChangeBsyLoan");
	}

	@Override
	public void updateByPrimaryKeySelective(BsyPushLoad bsyPushLoad) {
		 this.getSqlSession().update(this.getMapperNameSpace()+".updateByPrimaryKeySelective", bsyPushLoad);
	}


	/**
	 * 得到比搜益用户所投标的有起息日期的记录,只查找投资成功的记录
	 * */
	@Override
	public List<BsyInvest> getBsyInterest() {
		
		return this.getSqlSession().selectList("com.duanrong.cps.business.bsy.mapper.bsyInvestMapper.getBsyInterest");
	}
	
	/**
	 * 插入比搜益起息状态表
	 * */
	@Override
	public void insertInterest(BsyInterest bsyInterest) {
		
 		this.getSqlSession().insert("com.duanrong.cps.business.bsy.mapper.bsyInvestMapper.insertBsyInterest", bsyInterest);
		
	}


	/**
	 * 查询要推送的回款状态记录
	 * */
	@Override
	public List<BsyRepayMoney> getBsyRepayMoney() {
		
		return this.getSqlSession().selectList("com.duanrong.cps.business.bsy.mapper.bsyInvestMapper.getBsyBackMoney");
	}

	/**
	 * 插入推送的回款状态记录
	 * */
	@Override
	public void insertRepayMoney(BsyRepayMoney bsyRepayMoney) {
		this.getSqlSession().insert("com.duanrong.cps.business.bsy.mapper.bsyInvestMapper.insertBsyRepayMoney", bsyRepayMoney);
		
	}


	@Override
	public List<BsyInvest> getPushInvest() {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(this.getMapperNameSpace()+".getPushInvest");
	}

	@Override
	public void insertBsyPushInvest(HashMap<String,Object> params) {
		this.getSqlSession().insert(this.getMapperNameSpace()+".insertBsyPushInvest", params);
		
	}

	@Override
	public List<BsyChangeInvestStatus> getpushChangeInvestStatus() {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(this.getMapperNameSpace()+".getpushChangeInvestStatus");
	}

	@Override
	public void updatePushInvestStatus(HashMap<String, Object> params) {
		
		this.getSqlSession().update(this.getMapperNameSpace()+".updatePushInvestStatus", params);
		
	}

	/**
	 * 查询推送过的交易记录
	 */
	@Override
	public PageInfo<BsyPushedInvest> getInvestStatus(int pageNo, int pageSize,
			Map<String, Object> params) {
		PageHelper.startPage(pageNo, pageSize);
		List<BsyPushedInvest> list = getSqlSession().selectList(
				getMapperNameSpace() + ".getInvestStatus",params);
		PageInfo<BsyPushedInvest> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public PageInfo<InvestByBsy> getInvestRecordsByBsy(int pageNo,
			int pageSize, Map<String, Object> params) {
		PageHelper.startPage(pageNo, pageSize);
		List<InvestByBsy> list = getSqlSession().selectList(
				getMapperNameSpace() + ".getInvestRecordsByBsy",params);
		PageInfo<InvestByBsy> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public PageInfo<BsyPushInterest> getbsyPushInterest(int pageNo,
			int pageSize, Map<String, Object> params) {
		PageHelper.startPage(pageNo, pageSize);
		List<BsyPushInterest> list = getSqlSession().selectList(
				getMapperNameSpace() + ".getbsyPushInterest",params);
		PageInfo<BsyPushInterest> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public PageInfo<BsyPushRepayMent> getbsyPushRepayMent(int pageNo,
			int pageSize, Map<String, Object> params) {
		PageHelper.startPage(pageNo, pageSize);
		List<BsyPushRepayMent> list = getSqlSession().selectList(
				getMapperNameSpace() + ".getbsyPushRepayMent",params);
		PageInfo<BsyPushRepayMent> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public PageInfo<PlatformUserRelation> getBsyUserInfo(int pageNo,
			int pageSize, Map<String, Object> params) {
		PageHelper.startPage(pageNo, pageSize);
		List<PlatformUserRelation> list = getSqlSession().selectList(
				getMapperNameSpace() + ".getBsyUserInfo",params);
		PageInfo<PlatformUserRelation> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public PageInfo<InvestByBsy> getInvestRecordsByBjs(int pageNo,
			int pageSize, Map<String, Object> params) {
		PageHelper.startPage(pageNo, pageSize);
		List<InvestByBsy> list =new ArrayList<InvestByBsy>();
		if(params.get("userType")!=null){			
		   list = getSqlSession().selectList(
						getMapperNameSpace() + ".getInvestRecordsByBjs",params);
		}
		else{
		  list= getSqlSession().selectList(
					getMapperNameSpace() + ".getInvestRecordsByBjsAll",params);			
		}
		PageInfo<InvestByBsy> pageInfo = new PageInfo<>(list);
 		return pageInfo;
	}

}
