package com.duanrong.business.invest.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import util.MyStringUtils;
import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.business.invest.dao.InvestDao;
import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.invest.model.InvestConfirmation;
import com.duanrong.business.invest.model.InvestRedpacket;
import com.duanrong.business.invest.model.InvestSubLoan;
import com.duanrong.business.invest.model.PassThrough;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2014-9-1 上午11:29:38
 * @Description : drsoa Maven Webapp com.duanrong.business.invest.dao.impl
 *              InvestDaoImpl.java
 * 
 */
@Repository
public class InvestDaoImpl extends BaseDaoImpl<Invest> implements InvestDao {

	public InvestDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.invest.mapper.InvestMapper");
	}

	public Double getInvestTotalMoney(String userId) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getInvestTotalMoneyByUserId",
				userId);
	}


	public List<Invest> getInvestByLoan(String loanId) {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getInvestByLoan", loanId);
	}

	
	public List<Invest> getInvestByLoanSortAsc(Invest invest) {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getInvestByLoanSortAsc", invest);
	}
	
	public List<Invest> getInvestByLoanSortDesc(Invest invest) {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getInvestByLoanSortDesc", invest);
	}

	/**
	 * 得到某个标的的有效募集金额（排除流标）
	 * 
	 * @param loanId
	 * @return
	 */
	public Double getValidInvestSumByLoan(String loanId) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getValidInvestSumByLoan", loanId);
	}

	public Long GetInvestCountByLoanId(String loanId, String status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loanId", loanId);
		map.put("status", status);
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getInvestCountByLoan", map);
	}


	public Double getInvestsTotalInterest(String userId) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getInvestsTotalInterest", userId);
	}

	public List<Invest> getInvestInterestDetail(String userId) {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getInvestDetail", userId);
	}

	public List<Invest> getInvestLoan(Invest invest) {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getInvestLoan", invest);
	}

	public Double getInvestLoanTotalMoney(Invest invest) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getInvestLoanTotalMoney", invest);
	}

	public Double getInvestLoanTotalInterest(Invest invest) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getInvestLoanTotalInterest",
				invest);
	}

	public Double getInvestLoanTotalPaidInterest(Invest invest) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getInvestLoanTotalPaidInterest",
				invest);
	}

	public Double getInvestMoney(Invest invest) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getInvestMoney", invest);
	}

	@Override
	public Double getPersonalInvestCeiling(String userId, String loanId) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("loanId", loanId);
		return this
				.getSqlSession()
				.selectOne(
						this.getMapperNameSpace() + ".getPersonalInvestCeiling",
						params);
	}

	@Override
	public List<Invest> getInvestDynamic(Long recordNumber) {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getInvestDynamic", recordNumber);
	}

	@Override
	public Double getAvgRate(String userId) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getAvgRate", userId);
	}

	@Override
	public void updateInvestStatusByLoanId(String loanId,
			String originalStatus, String toStatus) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("loanId", loanId);
		params.put("originalStatus", originalStatus);
		params.put("toStatus", toStatus);
		this.getSqlSession().update(
				this.getMapperNameSpace() + ".updateInvestStatusByLoanId",
				params);
	}

	@Override
	public Double getCurrentInvestMoney(String userId) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getCurrentInvestMoney", userId);
	}

	@Override
	public Date getInvestDateLastOne(String loanId) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getInvestDateLastOne", loanId);
	}

	@Override
	public PageInfo<Invest> getInvestByLoan(int pageNo, int pageSize,
			Map<String, Object> map) {
		String nameSpace = "";
		PageHelper.startPage(pageNo, pageSize);
		String merge = (String) map.get("merge");
		if (MyStringUtils.isNotAnyBlank(merge) && merge.equals("on")) {
			nameSpace = ".getInvestByLoanGroup";
		} else {
			nameSpace = ".getInvestByLoan";
		}
		List<Invest> list = getSqlSession().selectList(
				this.getMapperNameSpace() + nameSpace, map);
		PageInfo<Invest> pageInfo = new PageInfo<Invest>(list);
		return pageInfo;
	}

	@Override
	public PageInfo<Invest> getInvestByUser(int pageNo, int pageSize,
			String userId) {
		PageHelper.startPage(pageNo, pageSize);
		List<Invest> list = getSqlSession().selectList(
				this.getMapperNameSpace() + ".getInvestByUser", userId);
		PageInfo<Invest> pageInfo = new PageInfo<Invest>(list);
		return pageInfo;
	}

	@Override
	public List<Invest> getInvestByLoan(Map<String, Object> map) {
		return getSqlSession().selectList(
				this.getMapperNameSpace() + ".getInvestByLoan", map);
	}

	@Override
	public List<InvestConfirmation> getInvestConfirmation(String loanId) {
		return getSqlSession().selectList(
				this.getMapperNameSpace() + ".getInvestConfimation", loanId);
	}

	@Override
	public List<Invest> getInvestByUserAndLoan(Map<String, Object> map) {
		return getSqlSession().selectList(
				this.getMapperNameSpace() + ".getInvestByLoanAndUser", map);
	}

	@Override
	public Long getSuccessInvestRecordNumber() {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getSuccessInvestRecordNumber");
	}

	@Override
	public void updateInvestRecord4ConfigTable(Long investRecord) {
		this.getSqlSession().update(
				this.getMapperNameSpace() + ".updateInvestRecord4ConfigTable",
				investRecord);
	}

	@Override
	public Invest getInvest(String id) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getInvest", id);
	}

	@Override
	public double getLoanInvestMoneyByUser(String userId, String loanId) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("loanId", loanId);
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getLoanInvestMoneyByUser", map);
	}

	@Override
	public String getLoanStatus(String id) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getLoanStatus", id);
	}

	@Override
	public List<Invest> getInvestNum(Map map) {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getInvestNum", map);
	}

	@Override
	public int getAutoInvestByLoan(String loanId) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getAutoInvestByLoan", loanId);
	}

	@Override
	public PageInfo<Invest> pageInfo(int pageNo, int pageSize, Map map) {
		PageHelper.startPage(pageNo, pageSize);
		List<Invest> list = getSqlSession().selectList(
				getMapperNameSpace() + ".pageInfo", map);
		PageInfo<Invest> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	public List<Invest> getInvests(String loanId) {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getInvestByLoan", loanId);
	}

	@Override
	public List<Invest> getInvests(Map<String, Object> map) {
		return getSqlSession().selectList(
				this.getMapperNameSpace() + ".pageInfo", map);
	}

	public Double getTotalMoney(Map map) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getTotalMoney", map);
	}

	@Override
	public Invest getCountByNewbieEnjoy(Invest i) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getCountByNewbieEnjoy", i);
	}

	@Override
	public Long getInvestNums() {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getInvestNums");
	}

	@Override
	public List<Invest> getInvestByDate(Date date) {

		return getSqlSession().selectList(
				this.getMapperNameSpace() + ".getInvestByDate", date);
	}

	@Override
	public int getCountInvestByRedpacketForVisitor(String userId) {
		Integer count = getSqlSession().selectOne(
				getMapperNameSpace() + ".getCountInvestByRedpacketForVisitor",
				userId);
		return count == null ? 0 : count;
	}

	@Override
	public Date getFirstAvilidInvestTimeByUserId(String userId) {
		return getSqlSession().selectOne(
				getMapperNameSpace() + ".getFirstAvilidInvestTimeByUserId",
				userId);

	}

	@Override
	public double getAutoInvestMoneyByLoanId(String loanId) {
		
		return getSqlSession().selectOne(
				getMapperNameSpace() + ".getAutoInvestMoney",
				loanId);
	}

	
	@Override
	public void insertInvestRedpacket(InvestRedpacket investRedpacket) {
		this.getSqlSession().insert(
				getMapperNameSpace() + ".insertInvestRedpacket",
				investRedpacket);
	}

	@Override
	public List<InvestRedpacket> getInvestRedpacketList() {
		return getSqlSession().selectList(
				getMapperNameSpace() + ".getInvestRedpacketList");
	}

	@Override
	public void updatetInvestRedpacket(InvestRedpacket investRedpacket) {
		this.getSqlSession().update(
				getMapperNameSpace() + ".updateInvestRedpacket",
				investRedpacket);
	}

	@Override
	public int getInvestSeccessByLoanId(String loanId) {
		return getSqlSession().selectOne(
				getMapperNameSpace() + ".getInvestSeccessByLoanId", loanId);
	}

	@Override
	public List<Invest> getNationalActive(Date date) {
		return getSqlSession().selectList(
				getMapperNameSpace() + ".getNationalActive", date);
	}

	@Override
	public List<String> getNewInvestUser() {
		return getSqlSession().selectList(
				getMapperNameSpace() + ".getNewInvestUser");
	}

	@Override
	public Double getUserInvestMoney(String userId) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getUserInvestMoney", userId);
	}

	@Override
	public void update4DoubleEleven(PassThrough passThrough) {
		this.getSqlSession().update(
				this.getMapperNameSpace() + ".update4DoubleEleven", passThrough);
	}

	@Override
	public int getCurrentInvestMoney4DoubleElevec(String userId,String createTime) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("userId", userId);
		param.put("time", createTime);
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getCurrentInvestMoney4DoubleElevec", param);
	}

	@Override
	public int getCurrentInvestMoney4DoubleElevec1(String userId) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getCurrentInvestMoney4DoubleElevec1", userId);
	}


	/**
	 * 完成首次投资后，第N天
	 */
	@Override
	public List<Invest> getFirstInvestAfter(Map<String, Object> param) {
		
		return this.getSqlSession().selectList(this.getMapperNameSpace() + ".getFirstInvestAfter", param);
	}

	@Override
	public List<Invest> getInvestBy(Map<String, Object> param) {
		
		return this.getSqlSession().selectList(this.getMapperNameSpace() +".getInvestBy",param);
	}


	@Override
	public List<String> getHasRepayUserId() {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getHasRepayUserId");
	}

	@Override
	public List<Invest> getInvestByStatus() {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getInvestByStatus");
	}

	@Override
	public List<InvestSubLoan> getInvestSubloanByStatus() {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getInvestSubloanByStatus");
	}

	@Override
	public int getSumInvestRedpacketByOrderId(InvestRedpacket ir) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getSumInvestRedpacketByOrderId", ir);
	}
	
}
