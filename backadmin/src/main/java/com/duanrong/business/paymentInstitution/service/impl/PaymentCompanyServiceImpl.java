package com.duanrong.business.paymentInstitution.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import base.pagehelper.PageInfo;

import com.duanrong.business.payMentChannel.model.PayMentChannel;
import com.duanrong.business.paymentInstitution.dao.PaymentCompanyDao;
import com.duanrong.business.paymentInstitution.model.CompanyYeepayTransferUserYeepay;
import com.duanrong.business.paymentInstitution.model.PaymentAdvancefund;
import com.duanrong.business.paymentInstitution.model.PaymentAdvancefundRecord;
import com.duanrong.business.paymentInstitution.model.PaymentCompany;
import com.duanrong.business.paymentInstitution.model.PaymentRechargeRecord;
import com.duanrong.business.paymentInstitution.model.PaymentUserRelation;
import com.duanrong.business.paymentInstitution.service.PaymentCompanyService;
@Service
public class PaymentCompanyServiceImpl implements PaymentCompanyService {

	@Resource
	PaymentCompanyDao paymentCompanyDao;
	@Override
	public PageInfo<PaymentCompany> readPaymentInstitutionInfo(int pageNo,
			int pageSize, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return paymentCompanyDao.readPaymentInstitutionInfo(pageNo,pageSize,params);
	}
	@Override
	public PageInfo<PaymentRechargeRecord> readPaymentRechargeRecordInfo(
			int pageNo, int pageSize, Map<String, Object> params) {
		
		return paymentCompanyDao.readPaymentRechargeRecordInfo(pageNo,pageSize,params);
	}
	@Override
	public PageInfo<CompanyYeepayTransferUserYeepay> readCompanyYeepayTransferUserYeepay(
			int pageNo, int pageSize, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return paymentCompanyDao.readCompanyYeepayTransferUserYeepay(pageNo,pageSize,params);
	}
	@Override
	public PageInfo<PaymentUserRelation> readUserPayment(int pageNo,
			int pageSize, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return paymentCompanyDao.readUserPayment(pageNo,pageSize,params);
	}
	@Override
	public PageInfo<PaymentAdvancefundRecord> readAdvancefundRecord(int pageNo,
			int pageSize, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return paymentCompanyDao.readAdvancefundRecord(pageNo,pageSize,params);
	}
	@Override
	public PaymentAdvancefund readPaymentAdvancefund() {
		// TODO Auto-generated method stub
		return paymentCompanyDao.readPaymentAdvancefund();
	}
	@Override
	public int updateAdvancefundByNewMoney(Map<String, Object> moneyMap) {
		
		return paymentCompanyDao.updateAdvancefundByNewMoney(moneyMap);
	}
	@Override
	public int insertAdvancefundRecord(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return paymentCompanyDao.insertAdvancefundRecord(params);
	}
	@Override
	public int updateAdvancefundUpdateWarnMoney(double warnMoney) {
		// TODO Auto-generated method stub
		return paymentCompanyDao.updateAdvancefundUpdateWarnMoney(warnMoney);
	}
	
	@Override
	public List<PaymentRechargeRecord> query(String markId, String operator) {
		return paymentCompanyDao.get(markId, operator);
	}
	@Override
	public int updatePaymentRechargeRecord(PaymentRechargeRecord record) {
		return paymentCompanyDao.updatePaymentRechargeRecord(record);
	}
	@Override
	public int insertYeepayTransferUser(
			CompanyYeepayTransferUserYeepay companyYeepayTransferUserYeepay) {
		CompanyYeepayTransferUserYeepay transferUserYeepay =paymentCompanyDao.getByRequestNo(companyYeepayTransferUserYeepay.getRequetsNo());
		if(transferUserYeepay==null){
			return paymentCompanyDao.insertYeepayTransferUser(companyYeepayTransferUserYeepay);
		}else{
			return paymentCompanyDao.updateYeepayTransferUser(companyYeepayTransferUserYeepay);
		}
	}
	@Override
	public void updateAdvancefundMoney(double money) {
		paymentCompanyDao.updateAdvancefundMoney(money);
	}
	@Override
	public List<PayMentChannel> readPayMent() {
		// TODO Auto-generated method stub
		return paymentCompanyDao.readPayMent();
	}
	@Override
	public PaymentRechargeRecord readPaymentRechargeRecord(String orderId,
			String orderId2, String payMentId) {
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("orderId", orderId);
		params.put("orderId2", orderId2);
		params.put("payMentId", payMentId);
		return paymentCompanyDao.readPaymentRechargeRecord(params);
	}
	@Override
	public String readFuiouOrderId(String orderId) {
		// TODO Auto-generated method stub
		return paymentCompanyDao.readFuiouOrderId(orderId);
	}
	public CompanyYeepayTransferUserYeepay readByRequestNo(String paymentNo){
		return paymentCompanyDao.getByRequestNo(paymentNo);
	}
}