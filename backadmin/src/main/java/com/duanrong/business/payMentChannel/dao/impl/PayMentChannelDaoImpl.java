package com.duanrong.business.payMentChannel.dao.impl;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;
import base.pagehelper.PageHelper;
import base.pagehelper.PageInfo;

import com.duanrong.business.account.model.PaymentChannel;
import com.duanrong.business.node.model.CategoryTerm;
import com.duanrong.business.payMentChannel.dao.PayMentChannelDao;
import com.duanrong.business.payMentChannel.model.PayMentChannel;
import com.duanrong.business.payMentChannel.model.PaymentBankChannel;
import com.duanrong.business.payMentChannel.model.PaymentBankInfo;

@Repository
public class PayMentChannelDaoImpl extends BaseDaoImpl<PayMentChannel> implements PayMentChannelDao {
	public PayMentChannelDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.payMentChannel.mapper.payMentChannelMapper");
	}

	@Override
	public PageInfo<PayMentChannel> readPayMentChannel(int pageNo,
			int pageSize, Map<String, Object> params) {
		
		PageHelper.startPage(pageNo, pageSize);
		List<PayMentChannel> list = getSqlSession().selectList(
				getMapperNameSpace()+".readPayMentChannel", params);		
		PageInfo<PayMentChannel> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public void insertPaymentChannel(PayMentChannel paymentChannel) {
		this.getSqlSession().insert(this.getMapperNameSpace()+".insertPaymentChannel", paymentChannel);
	}

	@Override
	public void updatePaymentChannel(Map<String, Object> params) {
		this.getSqlSession().update(this.getMapperNameSpace()+".updatePaymentChannel",params);
	}

	@Override
	public PayMentChannel readPayMentChannelById(String id) {
		
		return this.getSqlSession().selectOne(this.getMapperNameSpace()+".readPayMentChannelById", id);
	}

	@Override
	public PageInfo<PaymentBankInfo> readPaymentBankInfo(int pageNo,
			int pageSize, Map<String, Object> params) {
		PageHelper.startPage(pageNo, pageSize);
		List<PaymentBankInfo> list = getSqlSession().selectList(
				getMapperNameSpace()+".readPaymentBankInfo", params);		
		PageInfo<PaymentBankInfo> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public void insertPayMentBankInfo(PaymentBankInfo paymentBankInfo) {

		this.getSqlSession().insert(this.getMapperNameSpace()+".insertPayMentBankInfo", paymentBankInfo);
	}

	@Override
	public PaymentBankInfo readPaymentBankInfoById(String id) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne(this.getMapperNameSpace()+".readPaymentBankInfoById", id);
	}

	@Override
	public void updatePaymentBankInfo(Map<String, Object> params) {
		
		this.getSqlSession().update(this.getMapperNameSpace()+".updatePaymentBankInfo", params);
		
	}

	@Override
	public PageInfo<PaymentBankChannel> readPaymentBankChannel(int pageNo,
			int pageSize, Map<String, Object> params) {
		PageHelper.startPage(pageNo, pageSize);
		List<PaymentBankChannel> list = getSqlSession().selectList(
				getMapperNameSpace()+".readPaymentBankChannel", params);		
		PageInfo<PaymentBankChannel> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	@Override
	public List<PaymentBankInfo> readBankList() {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(this.getMapperNameSpace()+".readBankList");
	}

	@Override
	public List<PayMentChannel> readChannelList() {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(this.getMapperNameSpace()+".readChannelList");
	}

	@Override
	public void insertPaymentBankChannel(PaymentBankChannel paymentBankChannel) {
		this.getSqlSession().insert(this.getMapperNameSpace()+".insertPaymentBankChannel", paymentBankChannel);
		
	}

	@Override
	public PaymentBankChannel readPaymentBankChannelById(String id) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne(this.getMapperNameSpace()+".readPaymentBankChannelById", id);
	}

	@Override
	public void updatePayMentBankChannel(Map<String, Object> params) {
		this.getSqlSession().update(this.getMapperNameSpace()+".updatePayMentBankChannel", params);
		
	}

	@Override
	public PaymentBankChannel readPaymentBankChannelByBankIdAndChannelId(String bankName,
			String channelName) {
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("bankName", bankName);
		params.put("channelName", channelName);
		return this.getSqlSession().selectOne(this.getMapperNameSpace()+".readPaymentBankChannelByBankIdAndChannelId", params);
	}

	@Override
	public PayMentChannel getChannelByCode(String payMentId) {
			
		return	this.getSqlSession().selectOne(this.getMapperNameSpace()+".getChannelByCode", payMentId);
	}

	

	
}