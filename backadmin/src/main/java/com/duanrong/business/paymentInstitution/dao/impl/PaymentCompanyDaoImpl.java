package com.duanrong.business.paymentInstitution.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.business.payMentChannel.model.PayMentChannel;
import com.duanrong.business.paymentInstitution.dao.PaymentCompanyDao;
import com.duanrong.business.paymentInstitution.model.CompanyYeepayTransferUserYeepay;
import com.duanrong.business.paymentInstitution.model.PaymentAdvancefund;
import com.duanrong.business.paymentInstitution.model.PaymentAdvancefundRecord;
import com.duanrong.business.paymentInstitution.model.PaymentCompany;
import com.duanrong.business.paymentInstitution.model.PaymentRechargeRecord;
import com.duanrong.business.paymentInstitution.model.PaymentUserRelation;

@Repository
public class PaymentCompanyDaoImpl extends BaseDaoImpl<PaymentCompany>  implements PaymentCompanyDao{

	public PaymentCompanyDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.paymentInstitution.mapper.PayMentMapper");
	}
	
	
	@Override
	public PageInfo<PaymentCompany> readPaymentInstitutionInfo(int pageNo,
			int pageSize, Map<String, Object> params) {
		PageHelper.startPage(pageNo, pageSize);
		List<PaymentCompany> list = getSqlSession().selectList(
				getMapperNameSpace()+".readPaymentInstitutionInfo", params);		
		PageInfo<PaymentCompany> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}


	@Override
	public PageInfo<PaymentRechargeRecord> readPaymentRechargeRecordInfo(
			int pageNo, int pageSize, Map<String, Object> params) {
		PageHelper.startPage(pageNo, pageSize);
		List<PaymentRechargeRecord> list = getSqlSession().selectList(
				getMapperNameSpace()+".readPaymentRechargeRecordInfo", params);		
		PageInfo<PaymentRechargeRecord> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}


	@Override
	public PageInfo<CompanyYeepayTransferUserYeepay> readCompanyYeepayTransferUserYeepay(
			int pageNo, int pageSize, Map<String, Object> params) {
		PageHelper.startPage(pageNo, pageSize);
		List<CompanyYeepayTransferUserYeepay> list = getSqlSession().selectList(
				getMapperNameSpace()+".readCompanyYeepayTransferUserYeepay", params);		
		PageInfo<CompanyYeepayTransferUserYeepay> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}


	@Override
	public PageInfo<PaymentUserRelation> readUserPayment(int pageNo,
			int pageSize, Map<String, Object> params) {
		PageHelper.startPage(pageNo, pageSize);
		List<PaymentUserRelation> list = getSqlSession().selectList(
				getMapperNameSpace()+".readUserPayment", params);		
		PageInfo<PaymentUserRelation> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}


	@Override
	public PageInfo<PaymentAdvancefundRecord> readAdvancefundRecord(int pageNo,
			int pageSize, Map<String, Object> params) {
		PageHelper.startPage(pageNo, pageSize);
		List<PaymentAdvancefundRecord> list = getSqlSession().selectList(
				getMapperNameSpace()+".readAdvancefundRecord", params);		
		PageInfo<PaymentAdvancefundRecord> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}


	@Override
	public PaymentAdvancefund readPaymentAdvancefund() {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne(this.getMapperNameSpace()+".readPaymentAdvancefund");
	}


	@Override
	public int updateAdvancefundByNewMoney(Map<String, Object> moneyMap) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update(this.getMapperNameSpace()+".updateAdvancefundByNewMoney", moneyMap);
	}


	@Override
	public int insertAdvancefundRecord(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.getSqlSession().insert(this.getMapperNameSpace()+".insertAdvancefundRecord", params);
	}


	@Override
	public int updateAdvancefundUpdateWarnMoney(double warnMoney) {
		// TODO Auto-generated method stub
		return this.getSqlSession().update(this.getMapperNameSpace()+".updateAdvancefundUpdateWarnMoney", warnMoney);
	}

	@Override
	public List<PaymentRechargeRecord> get(String markId, String operator) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("markId", markId);
		params.put("operator", operator);
		return this.getSqlSession().selectList(
				this.getMapperNameSpace() + ".getByCondition", params);
	}


	@Override
	public int updatePaymentRechargeRecord(PaymentRechargeRecord record) {
		return this.getSqlSession().update(this.getMapperNameSpace()+".updatePaymentRechargeRecord", record);
	}


	@Override
	public int insertYeepayTransferUser(
			CompanyYeepayTransferUserYeepay companyYeepayTransferUserYeepay) {
		return this.getSqlSession().insert(this.getMapperNameSpace()+".insertYeepayTransferUser", companyYeepayTransferUserYeepay);
	}


	@Override
	public PayMentChannel getPaymentCompany(String payMentId) {
		return this.getSqlSession().selectOne(this.getMapperNameSpace()+".getPaymentCompany",payMentId);
	}


	@Override
	public void updateAdvancefundMoney(double money) {
		this.getSqlSession().update(this.getMapperNameSpace()+".updateAdvancefundMoney", money);
	}


	@Override
	public List<PayMentChannel> readPayMent() {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(this.getMapperNameSpace()+".readPayMent");
	}


	@Override
	public PaymentRechargeRecord readPaymentRechargeRecord(
			Map<String, Object> params) {
		return this.getSqlSession().selectOne(this.getMapperNameSpace()+".readPaymentRechargeRecord",params);
	}


	@Override
	public String readFuiouOrderId(String orderId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne(this.getMapperNameSpace()+".readFuiouOrderId", orderId);
	}

	@Override
	public CompanyYeepayTransferUserYeepay getByRequestNo(String paymentNo) {
		return this.getSqlSession().selectOne(this.getMapperNameSpace() + ".getByRequestNo",paymentNo);
	}


	@Override
	public int updateYeepayTransferUser(
			CompanyYeepayTransferUserYeepay companyYeepayTransferUserYeepay) {
		return this.getSqlSession().insert(this.getMapperNameSpace()+".updateYeepayTransferUser", companyYeepayTransferUserYeepay);
	}
	
	
}
