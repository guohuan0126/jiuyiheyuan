package com.duanrong.drpay.business.payment.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.drpay.business.payment.dao.PaymentBankChannelDao;
import com.duanrong.drpay.business.payment.model.PaymentBankChannel;
import com.duanrong.drpay.business.payment.model.PaymentBankInfo;
import com.duanrong.drpay.business.payment.model.PaymentChannel;

/**
 * 银行渠道信息Dao实现
 * @author 邱飞虎
 *
 */
@Repository
public class PaymentBankChannelDaoImpl extends BaseDaoImpl<PaymentBankChannel>  implements PaymentBankChannelDao {

	public PaymentBankChannelDaoImpl() {
		this.setMapperNameSpace("com.duanrong.drpay.business.payment.mapper.PaymentBankChannelMapper");
	}


    /**
     * 根据银行渠道综合查询
     * @param bankName 银行名
     * @param tiecard 是否支持绑卡：1.支持 2.不支持
     * @param quickRecharge 是否支持快捷支付：1.支持 2.不支持
     * @param source 来源：PC，Mobile,ios+version,android+version
     * @return
     */
	@Override
	public List<PaymentBankChannel> findByBankChannel(String bankName,Integer tiecard,Integer quickRecharge,String source) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("bankName",bankName);
		param.put("tiecard",tiecard);
		param.put("quickRecharge",quickRecharge);
		if(source != null && source.equals("pc")){
			param.put("pcUsable",1);
		}else{
			param.put("mobUsable",1);
		}
		return this.getSqlSession().selectList(this.getMapperNameSpace() + 
				".findByBankChannel",param);
	}

	/**
	 * 根据银行名称查询银行信息
	 * @param bankName
	 * @return
	 */
	@Override
	public List<PaymentBankInfo> findBankInfoByName(String bankName) {
		return this.getSqlSession().selectList(this.getMapperNameSpace() + 
				".findBankInfoByName", bankName);
	}

	/**
	 * 查询易宝支持的网银银行
	 * @return
	 */
	@Override
	public List<String> findOnlineBank() {
		return this.getSqlSession().selectList(this.getMapperNameSpace() + 
				".findOnlineBank");
	}


	@Override
	public PaymentChannel getChannelByCode(String code) {
		return this.getSqlSession().selectOne(this.getMapperNameSpace() + 
				".getChannelByCode", code);
	}


	@Override
	public List<PaymentChannel> getValidChannels() {
		return this.getSqlSession().selectList(this.getMapperNameSpace() + ".getValidChannels");
	}

}