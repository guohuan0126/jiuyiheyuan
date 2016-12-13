package com.duanrong.business.user.dao.impl;



import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.business.user.dao.RedPacketDao;
import com.duanrong.business.user.model.RedPacket;
import com.duanrong.business.user.model.Rule;

/**
 * @author xiao
 * @date 2015年1月22日 下午3:06:54
 */

@Repository
public class PecketDaoImpl extends BaseDaoImpl<RedPacket> implements RedPacketDao {

	
	public PecketDaoImpl() {
		this.setMapperNameSpace("com.duanrong.business.user.mapper.RedPacketMapper");
	}
	
	@Override
	public Rule getRuleById(int id) {
		
		return this.getSqlSession().selectOne(this.getMapperNameSpace() + ".getRuleById", id);
	}

	@Override
	public int getRedPacketByRuleId(String mobile) {
		Integer count = getSqlSession().selectOne(getMapperNameSpace() + ".getRedPacketByRuleId", mobile);
		return count == null ? 0 : count;
	}
	
	@Override
	public Map<String,String> getRedPacketByMobile(String mobile) {
		return  getSqlSession().selectOne(getMapperNameSpace() + ".getRedPacketByMobile", mobile);
		
	}
	@Override
	public void updatePacketByMobile(Map<String,String> mobilemap) {
		getSqlSession().selectOne(getMapperNameSpace() + ".updateRedPacketByMobile", mobilemap);
		
	}
	@Override
	public void delRedPacketByMobile(String mobile) {
		getSqlSession().selectOne(getMapperNameSpace() + ".delRedPacketByMobile", mobile);
		
	}

}
