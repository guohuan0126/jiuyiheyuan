package com.duanrong.drpay.business.user.service;

import java.util.List;

import base.exception.TradeException;

import com.duanrong.drpay.business.invest.model.Invest;
import com.duanrong.drpay.business.user.model.RedPacket;

/**
 * @author xiao
 * @date 2015年1月22日 下午3:21:10
 */
public interface RedPacketService {

	/**
	 * 投资时校验红包规则
	 * @param invest
	 * @throws TradeException
	 */
	public void verifyRedPacket(Invest invest)throws TradeException;
	/**
	 * 使用红包
	 * @param invest
	 * @throws RedPacketUseException 
	 * @throws Exception 
	 */
	public void userRedPacket(Invest invest, String type) throws TradeException;
	
	/**
	 * 查询按条件查询
	 * @param redPacket 查询条件
	 * @return 
	 */
	public List<RedPacket> operateRedPacketDetails(RedPacket redPacket);
	/**
	 * 修改红包
	 * @param redPacket 查询条件
	 * @return 
	 */
	public void updateRedPacket(RedPacket redPacket);

	/**
	 * 修改红包手机号
	 * @param oldNmber
	 * @param newNumber
	 */
	public void updateDetailMobileNumber(String oldNumber ,String newNumber);

	/**
	 * 修改分享表中的手机号
	 */
	public void updateShareMobileNumber(String oldNumber, String newNumber);
}
