package com.duanrong.cps.business.bjs.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.duanrong.cps.business.bjs.dao.BjsDao;
import com.duanrong.cps.business.bjs.model.BjsProductType;
import com.duanrong.cps.business.invest.model.Invest;
import com.duanrong.cps.business.loan.model.Loan;
import com.duanrong.cps.business.repay.model.Repay;
import com.duanrong.cps.business.user.model.UserBill;

import base.dao.impl.BaseDaoImpl;

@Repository
public class BjsDaoImpl extends BaseDaoImpl<BjsProductType> implements BjsDao {

	
	public BjsDaoImpl(){
		this.setMapperNameSpace("com.duanrong.cps.business.bjs.mapper.bjsProductMapper");
	}
	
	/**
	 * 查询产品信息
	 */
	@Override
	public List<Loan> queryLoan(Map<String, Object> params) {
		
		return this.getSqlSession().selectList(this.getMapperNameSpace()+".getProductInfo",params);
	}

	/**
	 * 查询产品总数量
	 */
	@Override
	public int queryLoanAccount(Map<String, Object> params) {
		
		return this.getSqlSession().selectOne(this.getMapperNameSpace()+".getProductAccount", params);
	}

	@Override
	public void logInsertBjsRequestLog(Map<String, Object> params) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
		String nowDate=sdf.format(new Date());
		nowDate=nowDate.replace("-", "");
		params.put("tableName", "bjs_requestlog_"+nowDate);
		this.getSqlSession().insert(this.getMapperNameSpace()+".insertBjsRequestLog", params);
		
	}

	@Override
	public Map<String,Object> queryBjsUserInfo(String userId) {
		Map<String,Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("enableMoney", 0);
		map.put("frozen", 0);
		this.getSqlSession().selectOne(this.getMapperNameSpace()+".queryBjsUserInfo", map);
		return map;
	}

	@Override
	public Map<String, Object> getPlatFormUserRelation(String userId) {
		Map<String,Object>map=new HashMap<String,Object>();
		map.put("userId", userId);
		return this.getSqlSession().selectMap(this.getMapperNameSpace()+".getPlatFormUserRelation",map, "WDTY_id");
	}

	/**
	 * 查询用户的总收益
	 */
	@Override
	public Double getUserTotalInterest(String userId) {
		
		return this.getSqlSession().selectOne(this.getMapperNameSpace()+".getUserTotalInterest", userId);
	}
	/**
	 * 用户交易流水查询接口
	 * 
	 * @return
	 */
	@Override
	public List<UserBill>queryCapitalFlow(Map<String,Object>param){
		
		return this.getSqlSession().selectList(this.getMapperNameSpace()+".queryCapitalFlow", param);
	}
	/**
	 * 用户交易流水个数查询
	 * @param param
	 * @return
	 */
	@Override
	public int queryCapitalFlowAccount(Map<String,Object>param){
		return this.getSqlSession().selectOne(this.getMapperNameSpace()+".queryCapitalFlowAccount",param);
	}
	
	/**
	 * 投资查询接口
	 * @param param
	 * @return
	 */
	@Override
	public List<Invest>queryInvestByUserId(Map<String,Object>param){
		return this.getSqlSession().selectList(this.getMapperNameSpace()+".queryInvestByUserId",param);
	}
	/**
	 * 查询用户投资的总记录数
	 * @param param
	 * @return
	 */
	@Override
	public int queryInvestAccount(Map<String,Object>param){
		return this.getSqlSession().selectOne(this.getMapperNameSpace()+".queryInvestAccount",param);
	}

	/**
	 * 收益相关查询接口
	 * @param param
	 * @return
	 */
	@Override
	public List<Invest>queryInterest(Map<String,Object>param){
		
		return this.getSqlSession().selectList(this.getMapperNameSpace()+".queryInterest",param);
	}
	
	/**
	 * 查询还款表
	 * @param param
	 * @return
	 */
	@Override
	public List<Repay>queryRepay(String loanId){
		return this.getSqlSession().selectList(this.getMapperNameSpace()+".queryRepay",loanId);
	}
	/**
	 * 查询收益的总记录数
	 * @param params
	 * @return
	 */
	@Override
	public int queryInterestAccount(Map<String,Object>params){
		return this.getSqlSession().selectOne(this.getMapperNameSpace()+".queryInterestAccount",params);
	}
}
