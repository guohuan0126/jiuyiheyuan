package com.duanrong.drpay.business.user.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.drpay.business.user.dao.RedPacketDao;
import com.duanrong.drpay.business.user.model.RedPacket;

/**
 * @author xiao
 * @date 2015年1月22日 下午3:06:54
 */

@Repository
public class PecketDaoImpl extends BaseDaoImpl<RedPacket> implements RedPacketDao {

	
	public PecketDaoImpl() {
		this.setMapperNameSpace("com.duanrong.drpay.business.user.mapper.RedPacketMapper");
	}

	@Override
	public List<RedPacket> getRedPacketDetails(RedPacket redPacket) {
		return this.getSqlSession().selectList(this.getMapperNameSpace() + ".getRedPacketDetails", redPacket);	
	}

	@Override
	public void updateDetailMobileNumber(String oldNumber, String newNumber) {
		Map<String, Object> params = new HashMap<>();
		params.put("oldNumber", oldNumber);
		params.put("newNumber", newNumber);
		this.getSqlSession().update(this.getMapperNameSpace() + ".updateDetailMobileNumber", params);
	}
	
	@Override
	public void updateShareMobileNumber(String oldNumber, String newNumber) {
		Map<String, Object> params = new HashMap<>();
		params.put("oldNumber", oldNumber);
		params.put("newNumber", newNumber);
		this.getSqlSession().update(this.getMapperNameSpace() + ".updateShareMobileNumber", params);
	}

}
