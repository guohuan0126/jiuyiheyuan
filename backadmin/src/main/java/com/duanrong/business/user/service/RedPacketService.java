package com.duanrong.business.user.service;

import java.util.Map;

import base.model.PageData;

import com.duanrong.business.user.model.RedPacket;

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
	public PageData<RedPacket> readPaging(int pageNo, int pageSize,
			RedPacket redPacket);
	
	/**
	 * 加息券活动
	 * @param userId
	 */
	public void create(String userId);
	
	public Map<String,String> readRedPacketByMobile(String mobile);
	public void delRedPacketByMobile(String mobile);
	public void updatePacketByMobile(Map<String,String> mobilemap);
}