package com.duanrong.drpay.business.payment.dao;

import java.util.List;

import com.duanrong.drpay.business.payment.model.PaymentBankChannel;
import com.duanrong.drpay.business.payment.model.PaymentBankInfo;
import com.duanrong.drpay.business.payment.model.PaymentChannel;

/**
 * 银行渠道信息Dao
 * @author 邱飞虎
 *
 */
public interface PaymentBankChannelDao {

    /**
     * 根据银行渠道综合查询
     * @param bankName 银行名
     * @param tiecard 是否支持绑卡：1.支持 2.不支持
     * @param quickRecharge 是否支持快捷支付：1.支持 2.不支持
     * @param source 来源：PC，Mobile,ios+version,android+version
     * @return
     */
	public List<PaymentBankChannel> findByBankChannel(String bankName, Integer tiecard, Integer quickRecharge, String source);
	
	
    /**
     * 根据银行名称查询银行信息
     * @param bankName
     * @return
     */
	public List<PaymentBankInfo> findBankInfoByName(String bankName);
	
	
	/**
	 * 查询易宝支持的网银银行
	 * @return
	 */
	public List<String> findOnlineBank();


	public PaymentChannel getChannelByCode(String code);

	/**
	 * 获取有效的渠道（PC或移动端可用）
	 * @return
	 */
	public List<PaymentChannel> getValidChannels();
	
	
}