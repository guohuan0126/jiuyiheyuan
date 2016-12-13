package com.duanrong.cps.business.activity.dao.impl;

import org.springframework.stereotype.Repository;

import com.duanrong.cps.business.activity.dao.RedPacketRuleDao;
import com.duanrong.cps.business.activity.model.RedPacketRule;

import base.dao.impl.BaseDaoImpl;

@Repository
public class RedPacketRuleDaoImpl extends BaseDaoImpl<RedPacketRule> implements RedPacketRuleDao{
   
	   public RedPacketRuleDaoImpl(){
		   this.setMapperNameSpace("com.duanrong.business.activity.mapper.redPacketRuleMapper");
	   }
}
