package com.duanrong.cps.business.platform.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.cps.business.invest.model.Invest;
import com.duanrong.cps.business.loan.model.Loan;
import com.duanrong.cps.business.netloaneye.model.PushLoan;
import com.duanrong.cps.business.platform.dao.PlatformPushDao;
import com.duanrong.cps.business.platform.model.PlatformUserRelation;
import com.duanrong.cps.business.platform.model.PushInvest;
import com.duanrong.cps.business.platform.model.PushUserAccount;

@Repository
public class PlatformPushDaoImpl extends BaseDaoImpl<Loan> implements PlatformPushDao {

	
	public PlatformPushDaoImpl(){
		this.setMapperNameSpace("com.duanrong.cps.business.thirdPlatform.mapper.PushThirdPlatformMapper");
	}
	
	
	
	/**
	 * 查询可以推送到相应第三方平台的项目信息
	 */
	@Override
	public PageInfo<Loan> queryWaitPushLoanList(int pageNo, int pageSize,
			JSONObject json) {
		PageHelper.startPage(pageNo, pageSize);
		Map<String,String> param = new HashMap<String,String>();
		param.put("thirdType",json.getString("thirdType"));
		List<Loan> list = getSqlSession().selectList(
				getMapperNameSpace() + ".getWaitPushLoanList",param);
		PageInfo<Loan> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}


	/**
	 *得到项目信息 
	 */
	@Override
	public List<Loan> getLoanByLoanId(Map<String,Object>params) {
		return this.getSqlSession().selectList(getMapperNameSpace()+".getLoanByLoanId",params);
	}

	/**
	 * 插入到push_loan表
	 */

	@Override
	public int insertPushLoan(PushLoan pushLoan) {
		
		return this.getSqlSession().insert(getMapperNameSpace()+".insertPushLoan", pushLoan);
	}


	/**
	 * 查询向第三方平台推送过的标的信息
	 * 
	 */
	@Override
	public PageInfo<Loan> getLoanHistory(int pageNo, int pageSize,
			Map<String, Object> params) {
		PageHelper.startPage(pageNo, pageSize);
		List<Loan> list = getSqlSession().selectList(
				getMapperNameSpace() + ".getLoanHistory",params);
		PageInfo<Loan> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}


	/**
	 * 推送的投资记录查入数据库
	 */
	@Override
	public int insertPushInvest(PushInvest pushInvest) {
		
		return this.getSqlSession().insert(getMapperNameSpace()+".insertPushInvestors", pushInvest);
	}



	@Override
	public List<PushLoan> getPushLoanInfo(Map<String, Object> params) {
		return this.getSqlSession().selectList(getMapperNameSpace()+".getPushLoanInfo",params);
	}


	/**
	 * 修改push_loan表
	 */
	@Override
	public int updatePushLoan(PushLoan pushLoan) {
		
		return this.getSqlSession().update("updatePushLoan", pushLoan);
	}


	/**
	 * 查询push_investors表
	 */
	@Override
	public List<PushInvest> getPushInvestorsInfo(Map<String, Object> params) {
		return this.getSqlSession().selectList(getMapperNameSpace()+".getPushInvestorsInfo",params);
	}

	/**
	 * 查询推送到第三方平台的投资记录
	 */
	@Override
	public PageInfo<Invest> getInvestHistory(int pageNo, int pageSize,
			Map<String, Object> params) {
		PageHelper.startPage(pageNo, pageSize);
		List<Invest> list = getSqlSession().selectList(
				getMapperNameSpace() + ".getInvestHistory",params);
		PageInfo<Invest> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}


	/**
	 * 推送用户资产记录插入数据库
	 */
	@Override
	public int insertPushUserAccount(PushUserAccount userAccount) {
		return this.getSqlSession().insert(getMapperNameSpace()+".insertPushUserAccount", userAccount);
	}
	
	

}
