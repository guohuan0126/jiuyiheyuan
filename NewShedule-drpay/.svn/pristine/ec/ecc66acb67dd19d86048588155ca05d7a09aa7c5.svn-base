package com.duanrong.business.user.dao.impl;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import base.dao.impl.BaseDaoImpl;

import com.duanrong.business.user.dao.RedPacketDao;
import com.duanrong.business.user.model.RedPacket;
import com.duanrong.business.user.model.Rule;
import com.duanrong.business.user.model.User;

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
	public void updateExpired() {
		getSqlSession().update(getMapperNameSpace() + ".updateExpired");
		
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

	@Override
	public void batchInsert(List<RedPacket> list) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		this.getSqlSession().insert(this.getMapperNameSpace() + ".batchInsert",
				map);
	}

	/**
	 * 查询领取红包，未注册的用户
	 */
	@Override
	public List<String> getSmsSericeNoRegist(Integer day, Integer ruleId) {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("day", day);
		param.put("ruleId", ruleId);
		return this.getSqlSession().selectList(this.getMapperNameSpace() + ".getSmsSericeNoRegist", param);
	}
}
