package com.duanrong.business.paymentInstitution.dao;

import java.util.List;
import java.util.Map;

import base.pagehelper.PageInfo;

import com.duanrong.business.payMentChannel.model.PayMentChannel;
import com.duanrong.business.paymentInstitution.model.CompanyYeepayTransferUserYeepay;
import com.duanrong.business.paymentInstitution.model.PaymentAdvancefund;
import com.duanrong.business.paymentInstitution.model.PaymentAdvancefundRecord;
import com.duanrong.business.paymentInstitution.model.PaymentCompany;
import com.duanrong.business.paymentInstitution.model.PaymentRechargeRecord;
import com.duanrong.business.paymentInstitution.model.PaymentUserRelation;

public interface PaymentCompanyDao {

	PageInfo<PaymentCompany> readPaymentInstitutionInfo(int pageNo,
			int pageSize, Map<String, Object> params);

	PageInfo<PaymentRechargeRecord> readPaymentRechargeRecordInfo(int pageNo,
			int pageSize, Map<String, Object> params);

	PageInfo<CompanyYeepayTransferUserYeepay> readCompanyYeepayTransferUserYeepay(
			int pageNo, int pageSize, Map<String, Object> params);

	PageInfo<PaymentUserRelation> readUserPayment(int pageNo, int pageSize,
			Map<String, Object> params);

	PageInfo<PaymentAdvancefundRecord> readAdvancefundRecord(int pageNo,
			int pageSize, Map<String, Object> params);

	PaymentAdvancefund readPaymentAdvancefund();

	int updateAdvancefundByNewMoney(Map<String, Object> moneyMap);

	int insertAdvancefundRecord(Map<String, Object> params);

	int updateAdvancefundUpdateWarnMoney(double warnMoney);

	List<PaymentRechargeRecord> get(String markId, String operator);

	int updatePaymentRechargeRecord(PaymentRechargeRecord record);

	int insertYeepayTransferUser(
			CompanyYeepayTransferUserYeepay companyYeepayTransferUserYeepay);

	PayMentChannel getPaymentCompany(String payMentId);

	void updateAdvancefundMoney(double money);

	List<PayMentChannel> readPayMent();

	PaymentRechargeRecord readPaymentRechargeRecord(Map<String, Object> params);

	String readFuiouOrderId(String orderId);
	
	CompanyYeepayTransferUserYeepay getByRequestNo(String paymentNo);

	int updateYeepayTransferUser(
			CompanyYeepayTransferUserYeepay companyYeepayTransferUserYeepay);


}
