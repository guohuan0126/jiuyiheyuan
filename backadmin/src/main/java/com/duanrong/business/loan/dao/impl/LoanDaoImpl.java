package com.duanrong.business.loan.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import util.RedisCacheKey;
import base.dao.impl.BaseDaoImpl;
import base.model.PageData;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.loan.dao.LoanDao;
import com.duanrong.business.loan.model.BannerPicture;
import com.duanrong.business.loan.model.FixedBorrowers;
import com.duanrong.business.loan.model.Loan;
import com.duanrong.business.netloaneye.model.NetLoanModel;
import com.duanrong.business.user.model.User;
import com.duanrong.util.jedis.DRJedisCacheUtil;

/**
 * Copyright : duanrong.com.cn All Rights Reserved Company : 久亿财富（北京）投资有限公司
 * 
 * @Author : 孙铮
 * @CreateTime : 2014-8-28 下午12:46:30
 * @Description : drsoa com.duanrong.business.loan.dao.impl LoanDaoImpl.java
 * 
 */
@Repository
public class LoanDaoImpl extends BaseDaoImpl<Loan> implements LoanDao {

	public LoanDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.loan.mapper.LoanMapper");
	}

	/**
	 * 
	 * @description 根据组合条件查询所有借款记录
	 * @author 孙铮
	 * @time 2014-8-28 下午5:29:29
	 * @param userID
	 * @return
	 */
	public List<Loan> getLoansByGroupCondition(Loan loan) {
		List<Loan> loans = this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getLoansByGroupCondition", loan);
		return loans;
	}

	/**
	 * 
	 * @description 根据loanID查询对应借款人信息
	 * @author 孙铮
	 * @time 2014-8-28 下午5:40:44
	 * @param loanID
	 * @return
	 */
	public User getUserByLoanID(String loanID) {
		User user = this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getUserByloanID", loanID);
		return user;
	}

	/**
	 * 
	 * @description 根据loanID获取项目图片资料
	 * @author 孙铮
	 * @time 2014-8-30 上午10:51:37
	 * @param loanID
	 * @return
	 */
	public List<BannerPicture> getLoanInfoPics(String loanID) {
		List<BannerPicture> bplips = this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getLoanInfoPics", loanID);
		return bplips;
	}

	/**
	 * 
	 * @description 根据loanID获取抵押物图片
	 * @author 孙铮
	 * @time 2014-8-30 上午10:51:37
	 * @param loanID
	 * @return
	 */
	public List<BannerPicture> getLoanGuaranteePics(String loanID) {
		List<BannerPicture> bplgps = this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getLoanGuaranteePics", loanID);
		return bplgps;
	}

	/**
	 * 
	 * @description 根据组合条件查询所有借款记录
	 * @author 孙铮
	 * @time 2014-9-1 下午12:01:22
	 * @param invest
	 * @return
	 */
	public List<Invest> getInvestsByGroupCondition(Invest invest) {
		List<Invest> invests = this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getInvestsByGroupCondition",
				invest);
		return invests;
	}

	@Override
	public PageData<Loan> findPaging4Personal(int pageNo, int pageSize,
			Loan loan) {
		PageData<Loan> pageData = new PageData<>(pageNo, pageSize);
		pageData.getParams().put("entity", loan);
		List<Loan> results = getSqlSession().selectList(
				this.getMapperNameSpace() + ".findPaging4Personal", pageData);
		pageData.setResults(results);
		return pageData;
	}

	@Override
	public void insertBannerPicture(BannerPicture bannerPicture) {
		this.getSqlSession().insert(
				this.getMapperNameSpace() + ".insertBannerPicture",
				bannerPicture);
	}

	@Override
	public List<Loan> generateId(String str) {
		List<Loan> loans = this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".generateId", str);
		return loans;
	}

	@Override
	public void insertLoanInfoPics(String bpId, String loanId) {
		HashMap<String, String> maps = new HashMap<String, String>();
		maps.put("bpId", bpId);
		maps.put("loanId", loanId);
		DRJedisCacheUtil.hdel(RedisCacheKey.LOAN + loanId, RedisCacheKey.LOAN_PICS);
		this.getSqlSession().insert(
				this.getMapperNameSpace() + ".insertLoanInfoPics", maps);
	}

	@Override
	public BannerPicture getBannerPictureById(String picId) {
		BannerPicture bannerPicture = this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getBannerPictureById", picId);
		return bannerPicture;
	}

	@Override
	public void updateBannerPicture(BannerPicture bannerPicture, String loanId) {
		DRJedisCacheUtil.hdel(RedisCacheKey.LOAN + loanId, RedisCacheKey.LOAN_PICS);
		this.getSqlSession().update(
				this.getMapperNameSpace() + ".updateBannerPicture",
				bannerPicture);
	}

	@Override
	public List<FixedBorrowers> findAllFixedBorrowers() {
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".findAllFixedBorrowers");
	}

	@Override
	public void insertFixedBorrower(String userId, String email) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("email", email);
		this.getSqlSession().insert(
				this.getMapperNameSpace() + ".insertFixedBorrower", params);
	}

	@Override
	public void alterFixedBorrowersStatus(String borrower, String status, String email) {
		HashMap<String, String> maps = new HashMap<String, String>();
		maps.put("borrower", borrower);
		maps.put("status", status);
		maps.put("email", email);
		this.getSqlSession().update(
				this.getMapperNameSpace() + ".alterFixedBorrowersStatus", maps);
	}

	@Override
	public List<Loan> getLoansByGroupCondition1(Map<String, Object> map) {			
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getLoansByGroupCondition1", map);
	}

	@Override
	public Double getTotalMoney(Map map) {
		return this.getSqlSession().selectOne(
				this.getMapperNameSpace() + ".getTotalMoney",map);
	}
	/**
	 * 查询可以推送网贷天眼的项目列表
	 * @param loan
	 * @return
	 */
	public List<Loan> getWaitPushLoanList(Loan loan){
		return this.getSqlSession().selectList(this.getMapperNameSpace()+".getWaitPushLoanList", loan);
	}
	
	/**
	 * 查询推送记录
	 */
	public List<Loan> getPushRecords(Loan loan){
		return this.getSqlSession().selectList(this.getMapperNameSpace()+".getPushRecords",loan);
	}
	
	public Loan getP2pEyeLoanByLoanId(String id){
		return this.getSqlSession().selectOne(getMapperNameSpace()+".getP2pEyeLoanByLoanId",id);
	}
	
	public NetLoanModel getNetLoanCount(Loan loan){
		return this.getSqlSession().selectOne(getMapperNameSpace()+".getNetLoanCount");
	}
	
	public NetLoanModel getNetLoanSum(Loan loan){
		return this.getSqlSession().selectOne(getMapperNameSpace()+".getNetLoanSum");
	}

	@Override
	public int updateLoan(Loan loan) {
		DRJedisCacheUtil.hdel(RedisCacheKey.LOAN + loan.getId(), RedisCacheKey.LOAN_INFO);
		return this.getSqlSession().update(
				this.getMapperNameSpace() + ".update", loan);
	}

	@Override
	public PageInfo<Loan> pageList(int pageNo, int pageSize, Loan loan) {
		PageHelper.startPage(pageNo, pageSize);
		List<Loan> list = getSqlSession().selectList(
				this.getMapperNameSpace() + ".pageLite1", loan);
		PageInfo<Loan> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	
	@Override
	public String getLoanName() {
		return this.getSqlSession().selectOne(getMapperNameSpace()+".getLoanName");
	}
	
	@Override
	public void update(Loan loan){
		DRJedisCacheUtil.hdel(RedisCacheKey.LOAN + loan.getId(), RedisCacheKey.LOAN_INFO);
		super.update(loan);
	}
	@Override
	public int updateBatchPicTitle(Map<String, Object> params, String loadId) {
		DRJedisCacheUtil.hdel(RedisCacheKey.LOAN + loadId, RedisCacheKey.LOAN_PICS);
		return this.getSqlSession().update(this.getMapperNameSpace()+".updateBatchPicTitle",params);
	}
	@Override
	public void updateSortNum(Loan loan) {
		DRJedisCacheUtil.hdel(RedisCacheKey.LOAN + loan.getId(), RedisCacheKey.LOAN_INFO);
		this.getSqlSession().update(this.getMapperNameSpace()+".updateSortNum",loan);
	}

	@Override
	public List<Loan> getSubLoan(String subLoanId) {
		return this.getSqlSession().selectList(this.getMapperNameSpace()+".getSubLoan",subLoanId);
	}

	@Override
	public void updateBatchLoan(List<Loan> loans) {
		for (Loan loan2 : loans) {
			DRJedisCacheUtil.hdel(RedisCacheKey.LOAN + loan2.getId(), RedisCacheKey.LOAN_INFO);
			this.getSqlSession().update(this.getMapperNameSpace()+".updateBatchLoan",loan2);
		}
		
	}

}