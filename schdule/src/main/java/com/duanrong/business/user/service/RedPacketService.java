package com.duanrong.business.user.service;

import java.util.List;

import base.exception.RedPacketUseException;
import base.model.PageData;

import com.duanrong.business.invest.model.Invest;
import com.duanrong.business.user.model.RedPacket;
import com.duanrong.business.user.model.User;

/**
 * @author xiao
 * @date 2015年1月22日 下午3:21:10
 */
public interface RedPacketService {

	/**
	 * 红包分页查询
	 * @param pageNo 页码
	 * @param pageSize 页面
	 * @param redPacket 查询条件
	 * @return 分页对象
	 */
	public PageData<RedPacket> findPaging(int pageNo, int pageSize,
			RedPacket redPacket);
	
	/**
	 * 加息券活动
	 * @param userId
	 */
	public void create(String userId);
	
	/**
	 * 使用加息券
	 * @param invest
	 * @throws RedPacketUseException 
	 * @throws Exception 
	 */
	public void userRedPacket(Invest invest, String type) throws RedPacketUseException;
	
	/**
	 * 查询未使用的红包
	 * @return
	 */
	public void updateExpired();
	

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
    
	/**
	 * 用户生日发送红包
	 * @param user
	 */
	public void create4Birthday(User user);
}